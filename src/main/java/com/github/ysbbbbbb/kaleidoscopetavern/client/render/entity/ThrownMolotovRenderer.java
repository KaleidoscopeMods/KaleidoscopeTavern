package com.github.ysbbbbbb.kaleidoscopetavern.client.render.entity;

import com.github.ysbbbbbb.kaleidoscopetavern.entity.ThrownMolotovEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.block.state.BlockState;

@SuppressWarnings("deprecation")
public class ThrownMolotovRenderer extends EntityRenderer<ThrownMolotovEntity> {
    private final BlockRenderDispatcher renderer;

    public ThrownMolotovRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.renderer = context.getBlockRenderDispatcher();
    }

    @Override
    public void render(ThrownMolotovEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();

        // 缩小方块模型，使其适合投射物大小
        poseStack.scale(0.5F, 0.5F, 0.5F);
        poseStack.translate(-0.5, 0, -0.5);

        // 飞行时旋转
        float rotation = (entity.tickCount + partialTick) * 20.0F;
        poseStack.translate(0.5, 0.5, 0.5);
        poseStack.mulPose(Axis.YP.rotationDegrees(rotation));
        poseStack.mulPose(Axis.XP.rotationDegrees(rotation * 0.7F));
        poseStack.translate(-0.5, -0.5, -0.5);

        BlockState blockState = ModBlocks.MOLOTOV.get().defaultBlockState();
        renderer.renderSingleBlock(blockState, poseStack, buffer, packedLight, OverlayTexture.NO_OVERLAY);

        poseStack.popPose();

        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(ThrownMolotovEntity entity) {
        return InventoryMenu.BLOCK_ATLAS;
    }
}
