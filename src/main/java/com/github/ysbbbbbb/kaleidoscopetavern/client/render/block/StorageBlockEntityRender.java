package com.github.ysbbbbbb.kaleidoscopetavern.client.render.block;

import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco.StorageBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

@SuppressWarnings("deprecation")
public abstract class StorageBlockEntityRender<T extends StorageBlockEntity> implements BlockEntityRenderer<T> {
    protected final BlockRenderDispatcher blockRender;

    protected StorageBlockEntityRender(BlockEntityRendererProvider.Context context) {
        this.blockRender = context.getBlockRenderDispatcher();
    }

    protected void applyFacingRotation(Direction direction, PoseStack poseStack) {
        float angle = 180 - direction.get2DDataValue() * 90f;
        poseStack.translate(0.5, 0, 0.5);
        poseStack.mulPose(Axis.YP.rotationDegrees(angle));
        poseStack.translate(-0.5, 0, -0.5);
    }

    protected void renderStack(ItemStack stack, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay,
                               double x, double y, double z, float scale, double yRot, double xRot) {
        if (stack.isEmpty() || !(stack.getItem() instanceof BlockItem blockItem)) {
            return;
        }

        poseStack.pushPose();
        BlockState state = blockItem.getBlock().defaultBlockState();
        poseStack.translate(x, y, z);
        poseStack.mulPose(Axis.YP.rotationDegrees((float) yRot));
        poseStack.mulPose(Axis.XP.rotationDegrees((float) xRot));
        poseStack.scale(scale, scale, scale);
        poseStack.translate(-0.5, 0, -0.5);
        this.blockRender.renderSingleBlock(state, poseStack, buffer, packedLight, packedOverlay);
        poseStack.popPose();
    }
}
