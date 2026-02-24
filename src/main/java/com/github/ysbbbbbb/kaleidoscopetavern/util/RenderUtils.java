package com.github.ysbbbbbb.kaleidoscopetavern.util;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import org.joml.Matrix4f;

@OnlyIn(Dist.CLIENT)
public class RenderUtils {
    /**
     * 渲染流体的工具方法
     *
     * @param fluid     要渲染的流体
     * @param poseStack PoseStack
     * @param buffer    MultiBufferSource
     * @param light     PackedLight
     * @param size      流体平面贴图的大小（0-16），可以根据实际需要调整
     * @param y         流体平面贴图的高度，根据实际流体显示高度调整
     */
    public static void renderFluid(Fluid fluid, PoseStack poseStack, MultiBufferSource buffer, int light, int size, float y) {
        TextureAtlasSprite sprite = getStillFluidSprite(fluid);
        int color = getFluidColor(fluid);
        renderSurface(poseStack, buffer, sprite, color, light, Mth.clamp(size, 1, 16), y);
    }

    /**
     * 工具方法，用于渲染流体贴图
     *
     * @param poseStack PoseStack
     * @param buffer    MultiBufferSource
     * @param sprite    TextureAtlasSprite
     * @param color     流体附加着色
     * @param light     PackedLight
     * @param size      流体平面贴图的大小（0-16）
     * @param y         流体平面贴图的高度
     */
    public static void renderSurface(PoseStack poseStack, MultiBufferSource buffer, TextureAtlasSprite sprite,
                                     int color, int light, int size, float y) {
        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.translucentNoCrumbling());
        Matrix4f matrix = poseStack.last().pose();

        // 贴图的位置和大小
        int margin = (16 - size) / 2;
        float min = margin / 16f, max = 1 - margin / 16f;

        // 渲染一个平面
        vertexConsumer.vertex(matrix, min, y, min)
                .color(color)
                .uv(sprite.getU0(), sprite.getV0())
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(light)
                .normal(0, 1, 0)
                .endVertex();
        vertexConsumer.vertex(matrix, min, y, max)
                .color(color)
                .uv(sprite.getU0(), sprite.getV(size))
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(light)
                .normal(0, 1, 0)
                .endVertex();
        vertexConsumer.vertex(matrix, max, y, max)
                .color(color)
                .uv(sprite.getU(size), sprite.getV(size))
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(light)
                .normal(0, 1, 0)
                .endVertex();
        vertexConsumer.vertex(matrix, max, y, min)
                .color(color)
                .uv(sprite.getU(size), sprite.getV0())
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(light)
                .normal(0, 1, 0)
                .endVertex();
    }

    private static TextureAtlasSprite getStillFluidSprite(Fluid fluid) {
        IClientFluidTypeExtensions renderProperties = IClientFluidTypeExtensions.of(fluid);
        ResourceLocation fluidStill = renderProperties.getStillTexture();
        return Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(fluidStill);
    }

    private static int getFluidColor(Fluid fluid) {
        IClientFluidTypeExtensions ext = IClientFluidTypeExtensions.of(fluid);
        return ext.getTintColor();
    }
}
