package com.github.ysbbbbbb.kaleidoscopetavern.client.render.block;

import com.github.ysbbbbbb.kaleidoscopetavern.block.deco.CircularRackBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco.CircularRackBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;

public class CircularRackBlockEntityRender extends StorageBlockEntityRender<CircularRackBlockEntity> {
    public CircularRackBlockEntityRender(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(CircularRackBlockEntity rack, float partialTick, PoseStack poseStack,
                       MultiBufferSource buffer, int packedLight, int packedOverlay) {
        Direction direction = rack.getBlockState().getValue(CircularRackBlock.FACING);
        ItemStackHandler items = rack.getItems();

        poseStack.pushPose();
        this.applyFacingRotation(direction, poseStack);

        for (int i = 0; i < items.getSlots(); i++) {
            ItemStack stack = items.getStackInSlot(i);
            if (stack.isEmpty()) {
                continue;
            }

            double x = 0;
            double z = 0;
            double yRot = 0;

            switch (i) {
                case 0 -> {
                    x = 0.5;
                    z = 0.125;
                    yRot = 0;
                }
                case 1 -> {
                    x = 0.875;
                    z = 0.3125;
                    yRot = 22.5;
                }
                case 2 -> {
                    x = 0.875;
                    z = 0.6875;
                    yRot = -22.5;
                }
                case 3 -> {
                    x = 0.5;
                    z = 0.875;
                    yRot = 180;
                }
                case 4 -> {
                    x = 0.125;
                    z = 0.6875;
                    yRot = 157.5;
                }
                case 5 -> {
                    x = 0.125;
                    z = 0.3125;
                    yRot = -157.5;
                }
            }

            this.renderStack(stack, poseStack, buffer, packedLight, packedOverlay,
                    x, 0.125, z, 0.82F, yRot, 0);
        }
        poseStack.popPose();
    }
}

