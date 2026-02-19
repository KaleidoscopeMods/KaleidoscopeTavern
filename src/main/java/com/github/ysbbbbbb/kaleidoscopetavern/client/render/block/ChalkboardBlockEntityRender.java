package com.github.ysbbbbbb.kaleidoscopetavern.client.render.block;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco.ChalkboardBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.client.model.deco.LargeChalkboardModel;
import com.github.ysbbbbbb.kaleidoscopetavern.client.model.deco.SmallChalkboardModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

public class ChalkboardBlockEntityRender extends TextBlockEntityRender<ChalkboardBlockEntity> {
    private static final ResourceLocation SMALL_TEXTURE = KaleidoscopeTavern.modLoc("textures/entity/deco/small_chalkboard.png");
    private static final ResourceLocation LARGE_TEXTURE = KaleidoscopeTavern.modLoc("textures/entity/deco/large_chalkboard.png");

    private static final float TEXT_SCALE = 0.012f;
    private static final int LINE_HEIGHT = 12;
    private static final int MAX_LINES = 11;

    private final SmallChalkboardModel small;
    private final SmallChalkboardModel large;

    public ChalkboardBlockEntityRender(BlockEntityRendererProvider.Context context) {
        super(context);
        this.small = new SmallChalkboardModel(context.bakeLayer(SmallChalkboardModel.LAYER_LOCATION));
        this.large = new SmallChalkboardModel(context.bakeLayer(LargeChalkboardModel.LAYER_LOCATION));
    }

    @Override
    protected void renderModel(ChalkboardBlockEntity textBlock, PoseStack poseStack, MultiBufferSource buffer,
                               int packedLight, int packedOverlay, Direction facing) {
        poseStack.pushPose();

        poseStack.translate(0.5, 1.5, 0.5);
        poseStack.mulPose(Axis.ZN.rotationDegrees(180));
        poseStack.mulPose(Axis.YN.rotationDegrees(180 - facing.get2DDataValue() * 90));

        if (textBlock.isLarge()) {
            VertexConsumer consumer = buffer.getBuffer(RenderType.entityCutoutNoCull(LARGE_TEXTURE));
            large.renderToBuffer(poseStack, consumer, packedLight, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
        } else {
            VertexConsumer consumer = buffer.getBuffer(RenderType.entityCutoutNoCull(SMALL_TEXTURE));
            small.renderToBuffer(poseStack, consumer, packedLight, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
        }

        poseStack.popPose();
    }

    @Override
    protected void renderText(ChalkboardBlockEntity textBlock, PoseStack poseStack, MultiBufferSource buffer,
                              int packedLight, int packedOverlay, Direction facing) {
        poseStack.pushPose();

        if (facing == Direction.EAST) {
            poseStack.translate(0.0625, 1.535, 0.5);
        } else if (facing == Direction.WEST) {
            poseStack.translate(0.9375, 1.535, 0.5);
        } else if (facing == Direction.SOUTH) {
            poseStack.translate(0.5, 1.535, 0.0625);
        } else {
            poseStack.translate(0.5, 1.535, 0.9375);
        }

        poseStack.mulPose(Axis.YN.rotationDegrees(facing.get2DDataValue() * 90));

        int maxWidth = textBlock.isLarge() ? 232 : 63;
        super.doTextRender(textBlock, poseStack, buffer, packedLight, textBlock.getText(),
                maxWidth, TEXT_SCALE, MAX_LINES, LINE_HEIGHT);

        poseStack.popPose();
    }
}
