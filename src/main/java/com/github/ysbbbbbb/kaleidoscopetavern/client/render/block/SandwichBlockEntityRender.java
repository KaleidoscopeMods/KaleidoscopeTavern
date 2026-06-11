package com.github.ysbbbbbb.kaleidoscopetavern.client.render.block;

import com.github.ysbbbbbb.kaleidoscopetavern.block.deco.SandwichBoardBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco.SandwichBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.network.chat.Component;
import org.joml.Quaternionf;

public class SandwichBlockEntityRender extends TextBlockEntityRender<SandwichBlockEntity> {
    private static final float TEXT_SCALE = 0.01f;
    private static final int LINE_HEIGHT = 10;
    private static final int MAX_LINES = 8;

    public SandwichBlockEntityRender(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void renderModel(SandwichBlockEntity textBlock, PoseStack poseStack, MultiBufferSource buffer,
                               int packedLight, int packedOverlay) {
        // 展板使用的是方块模型，不需要特殊渲染
    }

    @Override
    protected void renderText(SandwichBlockEntity textBlock, PoseStack poseStack, MultiBufferSource buffer,
                              int packedLight, int packedOverlay) {
        int rot = textBlock.getBlockState().getValue(SandwichBoardBlock.ROTATION);

        float angle = rot * 22.5f + 180;
        float radians = (float) Math.toRadians(angle);
        float xOffset = (float) (-Math.sin(radians) * 0.06f);
        float zOffset = (float) (Math.cos(radians) * 0.06f);
        float tiltAxisX = (float) -Math.cos(radians);
        float tiltAxisZ = (float) -Math.sin(radians);
        
        poseStack.pushPose();

        poseStack.translate(0.5 + xOffset, 1.06, 0.5 + zOffset);
        poseStack.mulPose(new Quaternionf().rotateAxis((float) Math.toRadians(22.5f), tiltAxisX, 0.0f, tiltAxisZ));
        poseStack.mulPose(Axis.YN.rotationDegrees(angle));

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
