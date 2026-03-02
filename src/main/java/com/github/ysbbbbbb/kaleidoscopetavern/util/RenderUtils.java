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
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
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
        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.translucent());
        Matrix4f matrix = poseStack.last().pose();

        // 贴图的位置和大小
        int margin = (16 - size) / 2;
        float min = margin / 16f, max = 1 - margin / 16f;
        float spriteSize = size / 16f;

        // 渲染一个平面
        vertexConsumer.addVertex(matrix, min, y, min)
                .setColor(color)
                .setUv(sprite.getU0(), sprite.getV0())
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(light)
                .setNormal(0, 1, 0);
        vertexConsumer.addVertex(matrix, min, y, max)
                .setColor(color)
                .setUv(sprite.getU0(), sprite.getV(spriteSize))
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(light)
                .setNormal(0, 1, 0);
        vertexConsumer.addVertex(matrix, max, y, max)
                .setColor(color)
                .setUv(sprite.getU(spriteSize), sprite.getV(spriteSize))
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(light)
                .setNormal(0, 1, 0);
        vertexConsumer.addVertex(matrix, max, y, min)
                .setColor(color)
                .setUv(sprite.getU(spriteSize), sprite.getV0())
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(light)
                .setNormal(0, 1, 0);
    }

    /**
     * 基于方块坐标、物品索引和通道号生成稳定的伪随机浮点数，范围 [-1, 1]。
     * <p>
     * 使用 64 位位混淆哈希（Splitmix64 变体），无对象分配，适合逐帧调用。
     *
     * @param posSeed 方块坐标的 long 表示，作为基础种子
     * @param index   物品在槽位中的索引，保证每个物品结果不同
     * @param channel 通道编号，保证同一物品的不同旋转轴结果不同
     * @return [-1, 1] 范围内的伪随机浮点数
     */
    public static float stableRandom(long posSeed, int index, int channel) {
        long h = posSeed ^ ((long) index * 0x9e3779b97f4a7c15L) ^ ((long) channel * 0x6c62272e07bb0142L);
        h = (h ^ (h >>> 30)) * 0xbf58476d1ce4e5b9L;
        h = (h ^ (h >>> 27)) * 0x94d049bb133111ebL;
        h ^= (h >>> 31);
        return (float) (int) h / (float) Integer.MAX_VALUE;
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
