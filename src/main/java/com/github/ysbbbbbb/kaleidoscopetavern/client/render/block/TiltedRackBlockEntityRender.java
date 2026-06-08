package com.github.ysbbbbbb.kaleidoscopetavern.client.render.block;

import com.github.ysbbbbbb.kaleidoscopetavern.block.brew.BarCabinetBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco.TiltedRackBlockEntity;
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
public class TiltedRackBlockEntityRender implements BlockEntityRenderer<TiltedRackBlockEntity> {
    private final BlockRenderDispatcher blockRender;

    public TiltedRackBlockEntityRender(BlockEntityRendererProvider.Context context) {
        this.blockRender = context.getBlockRenderDispatcher();
    }

    @Override
    public void render(TiltedRackBlockEntity tiltedRack, float partialTick, PoseStack poseStack,
                       MultiBufferSource buffer, int packedLight, int packedOverlay) {
        Direction direction = tiltedRack.getBlockState().getValue(BarCabinetBlock.FACING);
        var items = tiltedRack.getItems();

        float scale = 0.9f;
        float angle = 180 - direction.get2DDataValue() * 90f;

        poseStack.pushPose();
        poseStack.translate(0.5, 0, 0.5);
        poseStack.mulPose(Axis.YP.rotationDegrees(angle));
        poseStack.translate(-0.5, 0, -0.5);

        for (int i = 0; i < items.getSlots(); i++) {
            ItemStack stack = items.getStackInSlot(i);
            if (stack.isEmpty() || (!(stack.getItem() instanceof BlockItem blockItem))) {
                continue;
            }

            poseStack.pushPose();
            BlockState state = blockItem.getBlock().defaultBlockState();
            poseStack.scale(scale, scale, scale);
            poseStack.translate(0.375 - 0.325 * i, 0.3125, 0.02);
            poseStack.mulPose(Axis.XP.rotationDegrees(22.5f));
            this.blockRender.renderSingleBlock(state, poseStack, buffer, packedLight, packedOverlay);
            poseStack.popPose();
        }

        poseStack.popPose();
    }
}
