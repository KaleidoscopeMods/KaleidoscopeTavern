package com.github.ysbbbbbb.kaleidoscopetavern.client.render.block;

import com.github.ysbbbbbb.kaleidoscopetavern.api.blockentity.IPressingTub;
import com.github.ysbbbbbb.kaleidoscopetavern.block.brew.PressingTubBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.brew.PressingTubBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.util.RenderUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;

import static com.github.ysbbbbbb.kaleidoscopetavern.util.RenderUtils.stableRandom;

public class PressingTubBlockEntityRender implements BlockEntityRenderer<PressingTubBlockEntity> {
    private final ItemRenderer itemRenderer;

    public PressingTubBlockEntityRender(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(PressingTubBlockEntity pressingTub, float partialTick, PoseStack poseStack,
                       MultiBufferSource buffer, int packedLight, int packedOverlay) {
        Level level = pressingTub.getLevel();
        if (level == null) {
            return;
        }

        // 渲染四个位置的物品，多余的朝上堆叠
        Direction direction = pressingTub.getBlockState().getValue(PressingTubBlock.FACING);
        boolean tilt = pressingTub.getBlockState().getValue(PressingTubBlock.TILT);
        ItemStack stack = pressingTub.getItems().getStackInSlot(0);
        int count = stack.getCount();

        if (count > 0) {
            long seed = pressingTub.getBlockPos().asLong();

            if (tilt) {
                poseStack.pushPose();
                poseStack.translate(0.5, 0, 0.5);
                poseStack.mulPose(Axis.YN.rotationDegrees(180 - direction.get2DDataValue() * 90));
                poseStack.translate(-0.5, 0, -0.5);

                if (direction.getAxis() == Direction.Axis.X) {
                    poseStack.mulPose(Axis.XP.rotationDegrees(45));
                    poseStack.translate(0, 0.5f, -0.5);
                } else {
                    poseStack.mulPose(Axis.XN.rotationDegrees(45));
                    poseStack.translate(0, -0.25f, 0.25);
                }
            }

            for (int i = 0; i < count; i++) {
                poseStack.pushPose();

                float x = ((i % 4) % 2 == 0) ? -0.15f : 0.15f + stableRandom(seed, i, 1) * 0.0625f;
                float z = ((i % 4) / 2 == 0) ? -0.15f : 0.15f + stableRandom(seed, i, 2) * 0.0625f;
                float y = (float) (i / 4) * 0.03125f + stableRandom(seed, i, 3) * 0.05f;

                float yRot = stableRandom(seed, i, 4) * count / 10f;
                float zRot = stableRandom(seed, i, 5) * 360f;

                poseStack.translate(0.5f + x, 0.2f + y, 0.5f + z);
                poseStack.scale(0.5f, 0.5f, 0.5f);
                poseStack.mulPose(Axis.XN.rotationDegrees(90));

                poseStack.mulPose(Axis.YN.rotationDegrees(yRot));
                poseStack.mulPose(Axis.ZN.rotationDegrees(zRot));

                itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, packedLight,
                        packedOverlay, poseStack, buffer, pressingTub.getLevel(), 0);
                poseStack.popPose();
            }

            if (tilt) {
                poseStack.popPose();
            }
        }

        // 如果有流体，渲染流体
        int fluidAmount = pressingTub.getFluidAmount();
        if (fluidAmount > 0) {
            float percent = fluidAmount / (float) IPressingTub.MAX_FLUID_AMOUNT;
            float y = 0.125f + percent * 0.25f;
            Fluid fluid = pressingTub.getFluid().getFluid().getFluid();
            RenderUtils.renderFluid(fluid, poseStack, buffer, packedLight, 12, y);
        }
    }
}