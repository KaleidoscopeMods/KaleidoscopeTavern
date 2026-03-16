package com.github.ysbbbbbb.kaleidoscopetavern.client.render.block;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco.BarStoolBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.client.model.deco.BarStoolBodyModel;
import com.github.ysbbbbbb.kaleidoscopetavern.entity.SitEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@OnlyIn(Dist.CLIENT)
public class BarStoolBlockEntityRender implements BlockEntityRenderer<BarStoolBlockEntity> {
    private static final Map<DyeColor, ResourceLocation> TEXTURES = buildTextures();
    private static final int MAX_ROT_CACHE_SIZE = 4096;
    private static final float MIN_SMOOTH_FACTOR = 0.20F;
    private static final float MAX_SMOOTH_FACTOR = 0.58F;
    private static final float SENSITIVITY_SCALE = 0.007F;
    private static final float PASSENGER_VELOCITY_BLEND = 0.22F;
    private static final float PASSENGER_PREDICT_TICKS = 0.18F;
    private static final float PASSENGER_MIN_STEP = 2.0F;
    private static final float PASSENGER_STEP_SCALE = 0.26F;
    private static final float RESTORE_SMOOTH_FACTOR = 0.35F;
    private final BarStoolBodyModel model;
    private final Map<Long, Float> renderRotCache = new ConcurrentHashMap<>();

    public BarStoolBlockEntityRender(BlockEntityRendererProvider.Context context) {
        this.model = new BarStoolBodyModel(context.bakeLayer(BarStoolBodyModel.LAYER_LOCATION));
    }

    @Override
    public void render(BarStoolBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, int packedOverlay) {
        float renderRot = getRenderRot(blockEntity, partialTick);
        ResourceLocation texture = getTexture(blockEntity.getColor());
        poseStack.pushPose();
        poseStack.translate(0.5, 1.5, 0.5);
        poseStack.mulPose(Axis.ZN.rotationDegrees(180.0F));
        poseStack.mulPose(Axis.YN.rotationDegrees(180.0F - renderRot));
        VertexConsumer consumer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(texture));
        this.model.renderToBuffer(poseStack, consumer, packedLight, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();
    }

    private static float getSampleTime(BarStoolBlockEntity blockEntity, float partialTick) {
        Level level = blockEntity.getLevel();
        if (level == null) {
            return partialTick;
        }
        return level.getGameTime() + partialTick;
    }

    private static float smoothPassengerRotation(float currentRot, float targetRot, float angularVelocity) {
        float velocityAbs = Math.abs(angularVelocity);
        float smoothFactor = Mth.clamp(MIN_SMOOTH_FACTOR + velocityAbs * SENSITIVITY_SCALE, MIN_SMOOTH_FACTOR, MAX_SMOOTH_FACTOR);
        float lerped = Mth.rotLerp(smoothFactor, currentRot, targetRot);
        float rawStep = Mth.wrapDegrees(lerped - currentRot);
        float maxStep = PASSENGER_MIN_STEP + velocityAbs * PASSENGER_STEP_SCALE;
        float clampedStep = Mth.clamp(rawStep, -maxStep, maxStep);
        return Mth.wrapDegrees(currentRot + clampedStep);
    }

    private static @Nullable LivingEntity resolvePassenger(BarStoolBlockEntity blockEntity) {
        Level level = blockEntity.getLevel();
        if (level == null) {
            return null;
        }
        for (SitEntity sitEntity : level.getEntitiesOfClass(SitEntity.class, new AABB(blockEntity.getBlockPos()))) {
            if (!sitEntity.isAlive() || sitEntity.getPassengers().isEmpty()) {
                continue;
            }
            Entity entity = sitEntity.getFirstPassenger();
            if (entity instanceof LivingEntity livingEntity) {
                return livingEntity;
            }
        }
        return null;
    }

    private float getRenderRot(BarStoolBlockEntity blockEntity, float partialTick) {
        if (this.renderRotCache.size() > MAX_ROT_CACHE_SIZE) {
            this.renderRotCache.clear();
        }
        long key = blockEntity.getBlockPos().asLong();
        float cachedRot = blockEntity.getCachedRot();
        float currentRot = this.renderRotCache.getOrDefault(key, cachedRot);
        LivingEntity passenger = resolvePassenger(blockEntity);
        if (passenger == null) {
            float restoredRot = Mth.rotLerp(RESTORE_SMOOTH_FACTOR, currentRot, cachedRot);
            if (Mth.degreesDifferenceAbs(restoredRot, cachedRot) < 0.5F) {
                this.renderRotCache.remove(key);
                return cachedRot;
            }
            this.renderRotCache.put(key, restoredRot);
            return restoredRot;
        }
        float bodyRot = Mth.rotLerp(partialTick, passenger.yBodyRotO, passenger.yBodyRot);
        float angularVelocity = Mth.wrapDegrees(passenger.yBodyRot - passenger.yBodyRotO);
        float predictedRot = bodyRot + angularVelocity * PASSENGER_PREDICT_TICKS;
        float wobble = Mth.sin(getSampleTime(blockEntity, partialTick) * SENSITIVITY_SCALE) * angularVelocity * PASSENGER_VELOCITY_BLEND;
        float targetRot = Mth.wrapDegrees(Mth.rotLerp(PASSENGER_VELOCITY_BLEND, bodyRot, predictedRot) + wobble);
        float smoothedRot = smoothPassengerRotation(currentRot, targetRot, angularVelocity);
        this.renderRotCache.put(key, smoothedRot);
        return smoothedRot;
    }

    private static ResourceLocation getTexture(DyeColor color) {
        return TEXTURES.getOrDefault(color, TEXTURES.get(DyeColor.WHITE));
    }

    private static Map<DyeColor, ResourceLocation> buildTextures() {
        Map<DyeColor, ResourceLocation> textures = new EnumMap<>(DyeColor.class);
        for (DyeColor color : DyeColor.values()) {
            textures.put(color, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "textures/entity/deco/bar_stool/" + color.getName() + ".png"));
        }
        return textures;
    }
}
