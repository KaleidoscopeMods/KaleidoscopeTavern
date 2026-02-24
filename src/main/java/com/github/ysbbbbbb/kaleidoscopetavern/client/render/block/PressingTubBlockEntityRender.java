package com.github.ysbbbbbb.kaleidoscopetavern.client.render.block;

import com.github.ysbbbbbb.kaleidoscopetavern.api.blockentity.IPressingTub;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.brew.PressingTubBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.util.RenderUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;

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
        ItemStack stack = pressingTub.getItems().getStackInSlot(0);
        int count = stack.getCount();

        if (count > 0) {
            long seed = pressingTub.getBlockPos().asLong();

            for (int i = 0; i < count; i++) {
                poseStack.pushPose();

                float x = ((i % 4) % 2 == 0) ? -0.15f : 0.15f + stableRandom(seed, i, 1) * 0.0625f;
                float z = ((i % 4) / 2 == 0) ? -0.15f : 0.15f + stableRandom(seed, i, 2) * 0.0625f;
                float y = (float) (i / 4) * 0.03125f + stableRandom(seed, i, 3) * 0.05f;

                float yRot = stableRandom(seed, i, 4) * 10f;
                float zRot = stableRandom(seed, i, 5) * 360f;

                poseStack.translate(0.5f + x, 0.125f + y, 0.5f + z);
                poseStack.scale(0.5f, 0.5f, 0.5f);
                poseStack.mulPose(Axis.XN.rotationDegrees(90));

                poseStack.mulPose(Axis.YN.rotationDegrees(yRot));
                poseStack.mulPose(Axis.ZN.rotationDegrees(zRot));

                itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, packedLight,
                        packedOverlay, poseStack, buffer, pressingTub.getLevel(), 0);
                poseStack.popPose();
            }
        }

        // 如果有流体，渲染流体
        int liquidAmount = pressingTub.getLiquidAmount();
        if (liquidAmount > 0) {
            float liquidPercent = liquidAmount / (float) IPressingTub.MAX_LIQUID_AMOUNT;
            float y = 0.125f + liquidPercent * 0.25f;
            Fluid fluid = pressingTub.getFluid().getFluid().getFluid();
            RenderUtils.renderFluid(fluid, poseStack, buffer, packedLight, 12, y);
        }
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
    private static float stableRandom(long posSeed, int index, int channel) {
        long h = posSeed ^ ((long) index * 0x9e3779b97f4a7c15L) ^ ((long) channel * 0x6c62272e07bb0142L);
        h = (h ^ (h >>> 30)) * 0xbf58476d1ce4e5b9L;
        h = (h ^ (h >>> 27)) * 0x94d049bb133111ebL;
        h ^= (h >>> 31);
        return (float) (int) h / (float) Integer.MAX_VALUE;
    }
}