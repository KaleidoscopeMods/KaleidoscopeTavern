package com.github.ysbbbbbb.kaleidoscopetavern.client.render.block;

import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco.SandwichBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;

public class SandwichBlockEntityRender extends TextBlockEntityRender<SandwichBlockEntity> {
    private static final float TEXT_SCALE = 0.01f;
    private static final int LINE_HEIGHT = 10;
    private static final int MAX_LINES = 8;

    public SandwichBlockEntityRender(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void renderModel(SandwichBlockEntity textBlock, PoseStack poseStack, MultiBufferSource buffer,
                               int packedLight, int packedOverlay, Direction facing) {
        // 展板使用的是方块模型，不需要特殊渲染
    }

    @Override
    protected void renderText(SandwichBlockEntity textBlock, PoseStack poseStack, MultiBufferSource buffer,
                              int packedLight, int packedOverlay, Direction facing) {
        poseStack.pushPose();

        if (facing == Direction.SOUTH) {
            poseStack.translate(0.5, 1.06, 0.56);
            poseStack.mulPose(Axis.XN.rotationDegrees(22.5f));
        } else if (facing == Direction.NORTH) {
            poseStack.translate(0.5, 1.06, 0.44);
            poseStack.mulPose(Axis.XP.rotationDegrees(22.5f));
        } else if (facing == Direction.EAST) {
            poseStack.translate(0.56, 1.06, 0.5);
            poseStack.mulPose(Axis.ZP.rotationDegrees(22.5f));
        } else if (facing == Direction.WEST) {
            poseStack.translate(0.44, 1.06, 0.5);
            poseStack.mulPose(Axis.ZN.rotationDegrees(22.5f));
        }

        poseStack.mulPose(Axis.YN.rotationDegrees(facing.get2DDataValue() * 90));

        int maxWidth = 55;
        super.doTextRender(textBlock, poseStack, buffer, packedLight, textBlock.getText(),
                maxWidth, TEXT_SCALE, MAX_LINES, LINE_HEIGHT);

        poseStack.popPose();
    }

    @Override
    protected Component getRenderText(String text) {
        return Component.literal(text).withStyle(ChatFormatting.BOLD);
    }
}
