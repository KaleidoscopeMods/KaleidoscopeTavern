package com.github.ysbbbbbb.kaleidoscopetavern.client.render.block;

import com.github.ysbbbbbb.kaleidoscopetavern.block.deco.ChalkboardBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco.TextBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.util.TextAlignment;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FastColor;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.StringUtils;
import org.joml.Matrix4f;

import java.util.List;

public abstract class TextBlockEntityRender<T extends TextBlockEntity> implements BlockEntityRenderer<T> {
    protected static final int OUTLINE_RENDER_DISTANCE = Mth.square(16);
    protected final Font font;

    public TextBlockEntityRender(BlockEntityRendererProvider.Context context) {
        this.font = context.getFont();
    }

    @Override
    public void render(T textBlock, float partialTick, PoseStack poseStack,
                       MultiBufferSource buffer, int packedLight, int packedOverlay) {
        Direction facing = textBlock.getBlockState().getValue(ChalkboardBlock.FACING);

        // 渲染模型本体
        this.renderModel(textBlock, poseStack, buffer, packedLight, packedOverlay, facing);

        // 渲染文本
        // 玩家视角超过 48 格就不渲染文字了，节省性能
        Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
        if (camera.getPosition().distanceTo(Vec3.atCenterOf(textBlock.getBlockPos())) <= 48) {
            String text = textBlock.getText();
            if (StringUtils.isNotBlank(text)) {
                this.renderText(textBlock, poseStack, buffer, packedLight, packedOverlay, facing);
            }
        }
    }

    /**
     * 渲染模型本体，子类需要实现这个方法来渲染具体的模型
     */
    protected abstract void renderModel(T textBlock, PoseStack poseStack, MultiBufferSource buffer,
                                        int packedLight, int packedOverlay, Direction facing);

    /**
     * 渲染文字，子类需要实现这个方法来渲染具体的文字
     */
    protected abstract void renderText(T textBlock, PoseStack poseStack, MultiBufferSource buffer,
                                       int packedLight, int packedOverlay, Direction facing);

    /**
     * 渲染文字的辅助方法，涉及到文字的旋转、缩放、颜色计算和分行等逻辑，子类可以调用这个方法来简化文字渲染的实现
     * <p>
     * 需要注意的是这个方法没有进行矩阵的 push 和 pop 操作 <br>
     * 调用这个方法之前需要做好矩阵状态的保存和恢复
     */
    protected void doTextRender(T textBlock, PoseStack poseStack, MultiBufferSource buffer, int packedLight,
                                String text, int maxWidth, float scale, int maxLines, int lineHeight) {
        poseStack.scale(scale, -scale, scale);

        int darkColor = this.getDarkColor(textBlock.getColor(), textBlock.isGlowing());
        int textColor;
        boolean hasOutline;
        int light;

        if (textBlock.isGlowing()) {
            textColor = textBlock.getColor().getTextColor();
            hasOutline = this.isOutlineVisible(textBlock.getBlockPos(), textColor);
            light = 0xf000f0;
        } else {
            textColor = darkColor;
            hasOutline = false;
            light = packedLight;
        }

        List<FormattedCharSequence> splitLines = font.split(Component.literal(text), maxWidth);
        int totalLines = Math.min(splitLines.size(), maxLines);

        for (int i = 0; i < totalLines; i++) {
            FormattedCharSequence line = splitLines.get(i);
            float posX = this.getPosX(textBlock, maxWidth, font.width(line));
            float posY = i * lineHeight - 19;
            Matrix4f pose = poseStack.last().pose();

            if (hasOutline) {
                font.drawInBatch8xOutline(line, posX, posY, textColor, darkColor, pose, buffer, light);
            } else {
                font.drawInBatch(line, posX, posY, textColor, false, pose,
                        buffer, Font.DisplayMode.POLYGON_OFFSET, 0, light);
            }
        }
    }

    protected float getPosX(T textBlock, int maxWidth, int lineWidth) {
        TextAlignment alignment = textBlock.getTextAlignment();
        if (alignment == TextAlignment.LEFT) {
            return -maxWidth / 2f;
        } else if (alignment == TextAlignment.RIGHT) {
            return maxWidth / 2f - lineWidth;
        } else {
            return -lineWidth / 2f;
        }
    }

    protected int getDarkColor(DyeColor color, boolean isGlowing) {
        int textColor = color.getTextColor();
        if (textColor == DyeColor.BLACK.getTextColor() && isGlowing) {
            return 0xff_f0ebcc;
        }
        double darknessFactor = 0.6;
        int red = (int) (FastColor.ARGB32.red(textColor) * darknessFactor);
        int green = (int) (FastColor.ARGB32.green(textColor) * darknessFactor);
        int blue = (int) (FastColor.ARGB32.blue(textColor) * darknessFactor);
        return FastColor.ARGB32.color(0, red, green, blue);
    }

    protected boolean isOutlineVisible(BlockPos blockPos, int textColor) {
        if (textColor == DyeColor.BLACK.getTextColor()) {
            return true;
        }
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player != null && mc.options.getCameraType().isFirstPerson() && player.isScoping()) {
            return true;
        }
        Entity entity = mc.getCameraEntity();
        return entity != null && entity.distanceToSqr(Vec3.atCenterOf(blockPos)) < OUTLINE_RENDER_DISTANCE;
    }
}
