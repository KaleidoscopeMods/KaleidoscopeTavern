package com.github.ysbbbbbb.kaleidoscopetavern.client.render.block;

import com.github.ysbbbbbb.kaleidoscopetavern.block.deco.ChalkboardBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco.ChalkboardBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.client.model.deco.SmallChalkboardModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.StringUtils;
import org.joml.Matrix4f;

import java.util.List;

public class ChalkboardBlockEntityRender implements BlockEntityRenderer<ChalkboardBlockEntity> {
    private static final ResourceLocation SMALL_TEXTURE = new ResourceLocation("kaleidoscope_tavern", "textures/entity/deco/small_chalkboard.png");
    private static final int OUTLINE_RENDER_DISTANCE = Mth.square(16);

    private final SmallChalkboardModel small;
    private final Font font;

    public ChalkboardBlockEntityRender(BlockEntityRendererProvider.Context context) {
        this.small = new SmallChalkboardModel(context.bakeLayer(SmallChalkboardModel.LAYER_LOCATION));
        this.font = context.getFont();
    }

    @Override
    public void render(ChalkboardBlockEntity chalkboard, float partialTick, PoseStack poseStack,
                       MultiBufferSource buffer, int packedLight, int packedOverlay) {
        Direction facing = chalkboard.getBlockState().getValue(ChalkboardBlock.FACING);
        int facingDeg = facing.get2DDataValue() * 90;

        poseStack.pushPose();
        poseStack.translate(0.5, 1.5, 0.5);
        poseStack.mulPose(Axis.ZN.rotationDegrees(180));
        poseStack.mulPose(Axis.YN.rotationDegrees(180 - facingDeg));
        VertexConsumer checkerBoardBuff = buffer.getBuffer(RenderType.entityCutoutNoCull(SMALL_TEXTURE));
        small.renderToBuffer(poseStack, checkerBoardBuff, packedLight, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();

        // 渲染文本
        String text = chalkboard.getText();
        if (StringUtils.isNotBlank(text)) {
            renderText(chalkboard, poseStack, buffer, packedLight, facing, text);
        }
    }

    private void renderText(ChalkboardBlockEntity chalkboard, PoseStack poseStack, MultiBufferSource buffer, int packedLight, Direction facing, String text) {
        float scale = 0.01f;
        int lineHeight = 10;
        int maxWidth = 85;
        int maxLines = 15;
        int facingDeg = facing.get2DDataValue() * 90;

        poseStack.pushPose();

        if (facing == Direction.EAST) {
            poseStack.translate(0.0625, 1.535, 0.5);
        } else if (facing == Direction.WEST) {
            poseStack.translate(0.9375, 1.535, 0.5);
        } else if (facing == Direction.SOUTH) {
            poseStack.translate(0.5, 1.535, 0.0625);
        } else if (facing == Direction.NORTH) {
            poseStack.translate(0.5, 1.535, 0.9375);
        }

        poseStack.mulPose(Axis.YN.rotationDegrees(facingDeg));
        poseStack.scale(scale, -scale, scale);

        int darkColor = getDarkColor(chalkboard.getColor(), chalkboard.isGlowing());
        int textColor;
        boolean hasOutline;
        int light;

        if (chalkboard.isGlowing()) {
            textColor = chalkboard.getColor().getTextColor();
            hasOutline = isOutlineVisible(chalkboard.getBlockPos(), textColor);
            light = 0xf000f0;
        } else {
            textColor = darkColor;
            hasOutline = false;
            light = packedLight;
        }

        List<FormattedCharSequence> splitLines = font.split(Component.literal(text), maxWidth);
        int totalLines = Math.min(splitLines.size(), maxLines);

        for (int lineIndex = 0; lineIndex < totalLines; ++lineIndex) {
            FormattedCharSequence line = splitLines.get(lineIndex);

            float textX;
            if (chalkboard.getTextAlignment() == ChalkboardBlockEntity.TextAlignment.LEFT) {
                textX = -maxWidth / 2f;
            } else if (chalkboard.getTextAlignment() == ChalkboardBlockEntity.TextAlignment.RIGHT) {
                textX = maxWidth / 2f - font.width(line);
            } else {
                textX = -font.width(line) / 2f;
            }

            float textY = lineIndex * lineHeight - 20;
            Matrix4f pose = poseStack.last().pose();
            if (hasOutline) {
                font.drawInBatch8xOutline(line, textX, textY, textColor, darkColor, pose, buffer, light);
            } else {
                font.drawInBatch(line, textX, textY, textColor, false, pose, buffer, Font.DisplayMode.POLYGON_OFFSET, 0, light);
            }
        }

        poseStack.popPose();
    }

    private int getDarkColor(DyeColor color, boolean isGlowing) {
        int textColor = color.getTextColor();
        if (textColor == DyeColor.BLACK.getTextColor() && isGlowing) {
            return 0xff_f0ebcc;
        } else {
            double darknessFactor = 0.4;
            int red = (int) (FastColor.ARGB32.red(textColor) * darknessFactor);
            int green = (int) (FastColor.ARGB32.green(textColor) * darknessFactor);
            int blue = (int) (FastColor.ARGB32.blue(textColor) * darknessFactor);
            return FastColor.ARGB32.color(0, red, green, blue);
        }
    }

    private boolean isOutlineVisible(BlockPos blockPos, int textColor) {
        if (textColor == DyeColor.BLACK.getTextColor()) {
            return true;
        } else {
            Minecraft mc = Minecraft.getInstance();
            LocalPlayer player = mc.player;
            if (player != null && mc.options.getCameraType().isFirstPerson() && player.isScoping()) {
                return true;
            } else {
                Entity entity = mc.getCameraEntity();
                return entity != null && entity.distanceToSqr(Vec3.atCenterOf(blockPos)) < OUTLINE_RENDER_DISTANCE;
            }
        }
    }
}
