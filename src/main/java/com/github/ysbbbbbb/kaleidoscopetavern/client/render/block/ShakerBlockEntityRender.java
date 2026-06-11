package com.github.ysbbbbbb.kaleidoscopetavern.client.render.block;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.mixology.ShakerBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.client.animation.ShakerAnimation;
import com.github.ysbbbbbb.kaleidoscopetavern.client.model.mixology.ShakerModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.level.Level;
import org.joml.Vector3f;

public class ShakerBlockEntityRender implements BlockEntityRenderer<ShakerBlockEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(KaleidoscopeTavern.MOD_ID, "textures/block/mixology/shaker.png");
    private static final Vector3f ANIMATION_VECTOR_CACHE = new Vector3f();
    private final BlockEntityRendererProvider.Context context;
    private final ShakerModel model;

    public ShakerBlockEntityRender(BlockEntityRendererProvider.Context context) {
        this.context = context;
        this.model = new ShakerModel(context.bakeLayer(ShakerModel.LAYER_LOCATION));
    }

    @Override
    public void render(ShakerBlockEntity shaker, float partialTick, PoseStack poseStack,
                       MultiBufferSource buffer, int packedLight, int packedOverlay) {
        Level level = shaker.getLevel();
        if (level == null) {
            return;
        }

        float ageInTicks = shaker.getLevel().getGameTime() + partialTick;

        this.model.root().getAllParts().forEach(ModelPart::resetPose);
        animate(shaker.putState, ShakerAnimation.PUT, ageInTicks);

        poseStack.pushPose();
        poseStack.translate(0.5, 1.5, 0.5);
        poseStack.mulPose(Axis.ZN.rotationDegrees(180));
        poseStack.mulPose(Axis.YN.rotationDegrees(180));
        VertexConsumer checkerBoardBuff = buffer.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
        model.renderToBuffer(poseStack, checkerBoardBuff, packedLight, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();
    }

    protected void animate(AnimationState animationState, AnimationDefinition definition, float ageInTicks) {
        animationState.updateTime(ageInTicks, 1.0f);
        animationState.ifStarted(state ->
                KeyframeAnimations.animate(this.model, definition, state.getAccumulatedTime(), 1.0F, ANIMATION_VECTOR_CACHE)
        );
    }
}
