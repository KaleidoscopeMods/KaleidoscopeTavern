package com.github.ysbbbbbb.kaleidoscopetavern.client.render.block;

import com.github.ysbbbbbb.kaleidoscopetavern.block.deco.HolderBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco.HolderBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;

public class HolderBlockEntityRender extends StorageBlockEntityRender<HolderBlockEntity> {
    public HolderBlockEntityRender(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(HolderBlockEntity rack, float partialTick, PoseStack poseStack,
                       MultiBufferSource buffer, int packedLight, int packedOverlay) {
        Direction direction = rack.getBlockState().getValue(HolderBlock.FACING);
        ItemStack stack = rack.getItems().getStackInSlot(0);
        if (stack.isEmpty()) {
            return;
        }

        poseStack.pushPose();
        this.applyFacingRotation(direction, poseStack);
        this.renderStack(stack, poseStack, buffer, packedLight, packedOverlay,
                0.5, 0.125, 0.75, 0.95f, 0, -45);
        poseStack.popPose();
    }
}

