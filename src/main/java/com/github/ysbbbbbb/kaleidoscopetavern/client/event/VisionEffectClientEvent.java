package com.github.ysbbbbbb.kaleidoscopetavern.client.event;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModEffects;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModSounds;
import com.mojang.blaze3d.vertex.VertexConsumer;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.OutlineBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Mod.EventBusSubscriber(modid = KaleidoscopeTavern.MOD_ID, value = Dist.CLIENT)
public class VisionEffectClientEvent {
    public static final int MAX_LEVEL = 3; // 灵视生效的最大等级
    private static final int SCAN_CD = 50; // 扫描间隔
    private static final int COLOR = 0x408997;
    private static final int MARK_DURATION = 60; // 标记有效期
    private static final int MAX_ENTITIES = 96; // 最大标记数量
    private static final Int2IntOpenHashMap ENTITIES = new Int2IntOpenHashMap(); // 被灵视标记的实体集合

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        var minecraft = Minecraft.getInstance();
        var player = minecraft.player;
        var level = minecraft.level;
        if (player == null || level == null || !player.isAlive()) {
            ENTITIES.clear();
            return;
        }

        var vision = player.getEffect(ModEffects.VISION.get());
        if (vision == null) {
            ENTITIES.clear();
            return;
        }

        if (player.tickCount % SCAN_CD != 0) {
            return;
        }

        updateEntities(level, player, minecraft, vision.getAmplifier()); // 更新逻辑
    }

    @SubscribeEvent
    public static void onRenderLevelStage(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_ENTITIES || ENTITIES.isEmpty()) {
            return;
        }

        var minecraft = Minecraft.getInstance();
        var player = minecraft.player;
        var level = minecraft.level;
        if (player == null || level == null || !player.hasEffect(ModEffects.VISION.get())) {
            return;
        }

        var outlineBuffer = minecraft.renderBuffers().outlineBufferSource(); // 原版发光效果描边的buffer
        outlineBuffer.setColor(COLOR >> 16 & 0xFF, COLOR >> 8 & 0xFF, COLOR & 0xFF, 255);
        var outlineOnlyBuffer = new OutlineOnlyBufferSource(outlineBuffer); // 自定义包装

        var dispatcher = minecraft.getEntityRenderDispatcher();
        var camera = event.getCamera();
        var cameraPos = camera.getPosition();
        float partialTick = event.getPartialTick();
        boolean rendered = false;

        Iterator<Int2IntMap.Entry> iterator = ENTITIES.int2IntEntrySet().iterator();
        while (iterator.hasNext()) {
            int entityId = iterator.next().getIntKey();
            var entity = level.getEntity(entityId);
            if (!(entity instanceof LivingEntity living) || !living.isAlive()) {
                iterator.remove();
                continue;
            }

            if (minecraft.shouldEntityAppearGlowing(entity))
                continue; // 跳过原版发光实体

            if (!dispatcher.shouldRender(entity, event.getFrustum(), cameraPos.x(), cameraPos.y(), cameraPos.z()))
                continue; // 如果实体不在当前可见视锥内，就不额外渲染

            // 计算相机相对坐标
            double x = Mth.lerp(partialTick, entity.xOld, entity.getX()) - cameraPos.x();
            double y = Mth.lerp(partialTick, entity.yOld, entity.getY()) - cameraPos.y();
            double z = Mth.lerp(partialTick, entity.zOld, entity.getZ()) - cameraPos.z();
            float yaw = Mth.lerp(partialTick, entity.yRotO, entity.getYRot());

            // 让渲染器重新渲染一次目标实体，但传入的是 outlineOnlyBuffer，只渲染描边
            dispatcher.render(entity, x, y, z, yaw, partialTick, event.getPoseStack(),
                    outlineOnlyBuffer, LightTexture.FULL_BRIGHT);
            rendered = true;
        }

        if (rendered && !hasVanillaOutlineEntity(minecraft, level, player, dispatcher, event, cameraPos)) {
            outlineBuffer.endOutlineBatch();
            processOutline(event.getLevelRenderer(), partialTick); // 执行后处理
            minecraft.getMainRenderTarget().bindWrite(false);
        }
    }

    private static void updateEntities(ClientLevel level, LocalPlayer player, Minecraft minecraft, int amplifier) {
        ENTITIES.int2IntEntrySet().removeIf(entry -> entry.getIntValue() <= player.tickCount); // 清理过期标记

        double radius = Math.min(amplifier + 1, MAX_LEVEL) * 6;
        AABB range = player.getBoundingBox().inflate(radius);
        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, range,
                entity -> entity != player && entity.isAlive());

        boolean playSound = false;
        for (var entity : entities) {
            var id = entity.getId();
            boolean known = ENTITIES.containsKey(id);

            if (!known && ENTITIES.size() >= MAX_ENTITIES)
                continue;

            if (!known && !minecraft.shouldEntityAppearGlowing(entity))
                playSound = true; // 只有新的被标记的且没有发光效果的实体才会播放音效

            ENTITIES.put(id, player.tickCount + MARK_DURATION);
        }

        if (playSound) {
            player.playSound(ModSounds.EFFECT_VISION.get());
        }
    }

    /**
     * 检测本帧是否有原版的 outline 渲染
     */
    private static boolean hasVanillaOutlineEntity(Minecraft minecraft, ClientLevel level, LocalPlayer player,
                                                   EntityRenderDispatcher dispatcher, RenderLevelStageEvent event,
                                                   Vec3 cameraPos) {
        for (Entity entity : level.entitiesForRendering()) {
            if ((minecraft.shouldEntityAppearGlowing(entity) || entity.hasCustomOutlineRendering(player))
                    && dispatcher.shouldRender(entity, event.getFrustum(), cameraPos.x(), cameraPos.y(), cameraPos.z())) {
                return true;
            }
        }
        return false;
    }

    private static void processOutline(net.minecraft.client.renderer.LevelRenderer levelRenderer, float partialTick) {
        var entityEffect = levelRenderer.entityEffect;
        if (entityEffect != null) {
            entityEffect.process(partialTick);
        }
    }

    /**
     * 只渲染描边效果，避免重复渲染
     */
    private record OutlineOnlyBufferSource(OutlineBufferSource outlineBuffer) implements MultiBufferSource {
        private static final VertexConsumer EMPTY = new EmptyVertexConsumer();

        @Override
        public VertexConsumer getBuffer(RenderType renderType) {
            if (renderType.isOutline()) {
                return outlineBuffer.getBuffer(renderType);
            }

            Optional<RenderType> outline = renderType.outline();
            return outline.map(outlineBuffer::getBuffer).orElse(EMPTY);
        }
    }

    private static class EmptyVertexConsumer implements VertexConsumer {
        @Override
        public VertexConsumer vertex(double x, double y, double z) {
            return this;
        }

        @Override
        public VertexConsumer color(int red, int green, int blue, int alpha) {
            return this;
        }

        @Override
        public VertexConsumer uv(float u, float v) {
            return this;
        }

        @Override
        public VertexConsumer overlayCoords(int u, int v) {
            return this;
        }

        @Override
        public VertexConsumer uv2(int u, int v) {
            return this;
        }

        @Override
        public VertexConsumer normal(float x, float y, float z) {
            return this;
        }

        @Override
        public void endVertex() {
        }

        @Override
        public void defaultColor(int red, int green, int blue, int alpha) {
        }

        @Override
        public void unsetDefaultColor() {
        }
    }
}
