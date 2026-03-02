package com.github.ysbbbbbb.kaleidoscopetavern.client.render.block;

import com.github.ysbbbbbb.kaleidoscopetavern.block.brew.BarCabinetBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.brew.BarCabinetBlockEntity;
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
public class BarCabinetBlockEntityRender implements BlockEntityRenderer<BarCabinetBlockEntity> {
    private final BlockRenderDispatcher blockRender;

    public BarCabinetBlockEntityRender(BlockEntityRendererProvider.Context context) {
        this.blockRender = context.getBlockRenderDispatcher();
    }

    @Override
    public void render(BarCabinetBlockEntity barCabinet, float partialTick, PoseStack poseStack,
                       MultiBufferSource buffer, int packedLight, int packedOverlay) {
        Direction direction = barCabinet.getBlockState().getValue(BarCabinetBlock.FACING);

        ItemStack leftStack = barCabinet.getLeftItem();
        ItemStack rightStack = barCabinet.getRightItem();

        float scale = 0.9f;
        float angle = 180 - direction.get2DDataValue() * 90f;

        if (barCabinet.isSingle()) {
            if (!leftStack.isEmpty() && leftStack.getItem() instanceof BlockItem blockItem) {
                poseStack.pushPose();
                BlockState state = blockItem.getBlock().defaultBlockState();
                poseStack.translate(0.5, 0, 0.5);
                poseStack.mulPose(Axis.YP.rotationDegrees(angle));
                poseStack.translate(0, 0.0625, 0);
                poseStack.scale(scale, scale, scale);
                poseStack.translate(-0.5, 0, -0.5);
                this.blockRender.renderSingleBlock(state, poseStack, buffer, packedLight, packedOverlay);
                poseStack.popPose();
            }
        } else {
            if (!leftStack.isEmpty() && leftStack.getItem() instanceof BlockItem blockItem) {
                poseStack.pushPose();
                BlockState state = blockItem.getBlock().defaultBlockState();
                poseStack.translate(0.5, 0, 0.5);
                poseStack.mulPose(Axis.YP.rotationDegrees(angle));
                poseStack.translate(direction.getAxis() == Direction.Axis.Z ? 0.25 : -0.25, 0.0625, 0);
                poseStack.scale(scale, scale, scale);
                poseStack.translate(-0.5, 0, -0.5);
                this.blockRender.renderSingleBlock(state, poseStack, buffer, packedLight, packedOverlay);
                poseStack.popPose();
            }

            if (!rightStack.isEmpty() && rightStack.getItem() instanceof BlockItem blockItem) {
                poseStack.pushPose();
                BlockState state = blockItem.getBlock().defaultBlockState();
                poseStack.translate(0.5, 0, 0.5);
                poseStack.mulPose(Axis.YP.rotationDegrees(angle));
                poseStack.translate(direction.getAxis() == Direction.Axis.Z ? -0.25 : 0.25, 0.0625, 0);
                poseStack.scale(scale, scale, scale);
                poseStack.translate(-0.5, 0, -0.5);
                this.blockRender.renderSingleBlock(state, poseStack, buffer, packedLight, packedOverlay);
                poseStack.popPose();
            }
        }
    }
}
