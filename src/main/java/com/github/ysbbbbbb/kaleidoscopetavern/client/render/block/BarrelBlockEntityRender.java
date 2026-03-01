package com.github.ysbbbbbb.kaleidoscopetavern.client.render.block;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.api.blockentity.IBarrel;
import com.github.ysbbbbbb.kaleidoscopetavern.block.brew.BarrelBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.brew.BarrelBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.client.model.brew.BarrelModel;
import com.github.ysbbbbbb.kaleidoscopetavern.util.RenderUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.ItemStackHandler;

import static com.github.ysbbbbbb.kaleidoscopetavern.util.RenderUtils.stableRandom;

public class BarrelBlockEntityRender implements BlockEntityRenderer<BarrelBlockEntity> {
    private static final ResourceLocation LARGE_TEXTURE = KaleidoscopeTavern.modLoc("textures/entity/brew/barrel.png");

    private final ItemRenderer itemRenderer;
    private final BarrelModel model;

    public BarrelBlockEntityRender(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
        this.model = new BarrelModel(context.bakeLayer(BarrelModel.LAYER_LOCATION));
    }

    @Override
    public void render(BarrelBlockEntity barrel, float partialTick, PoseStack poseStack,
                       MultiBufferSource buffer, int packedLight, int packedOverlay) {
        // 本体渲染
        this.renderBody(barrel, poseStack, buffer, packedLight, packedOverlay);

        // 开盖后才会渲染下面部分
        if (barrel.isOpen()) {
            // 如果有流体，渲染流体
            this.renderFluid(barrel, poseStack, buffer, packedLight);
            // 渲染物品
            this.renderItems(barrel, poseStack, buffer, packedLight, packedOverlay, partialTick);
        }
    }

    private void renderBody(BarrelBlockEntity barrel, PoseStack poseStack, MultiBufferSource buffer,
                            int packedLight, int packedOverlay) {
        Direction facing = barrel.getBlockState().getValue(BarrelBlock.FACING);

        poseStack.pushPose();
        poseStack.translate(0.5, 1.5, 0.5);
        poseStack.mulPose(Axis.ZN.rotationDegrees(180));
        poseStack.mulPose(Axis.YN.rotationDegrees(180 - facing.get2DDataValue() * 90));

        // 根据酒桶的开盖状态切换模型显示
        boolean open = barrel.isOpen();
        this.model.getOpen().visible = open;
        this.model.getClose().visible = !open;

        VertexConsumer consumer = buffer.getBuffer(RenderType.entityCutoutNoCull(LARGE_TEXTURE));
        model.renderToBuffer(poseStack, consumer, packedLight, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();
    }

    private void renderFluid(BarrelBlockEntity barrel, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        FluidTank fluidTank = barrel.getFluid();
        int fluidAmount = fluidTank.getFluidAmount();
        if (fluidAmount > 0) {
            poseStack.pushPose();
            poseStack.translate(0, 2, 0);

            float percent = fluidAmount / (float) IBarrel.MAX_FLUID_AMOUNT;
            float y = percent * 0.65f;
            Fluid fluid = fluidTank.getFluid().getFluid();
            RenderUtils.renderFluid(fluid, poseStack, buffer, packedLight, 16, y);

            poseStack.popPose();
        }
    }

    private void renderItems(BarrelBlockEntity barrel, PoseStack poseStack, MultiBufferSource buffer,
                             int packedLight, int packedOverlay, float partialTick) {
        if (barrel.getLevel() == null) {
            return;
        }

        float time = barrel.getLevel().getGameTime() + partialTick;
        long seed = barrel.getBlockPos().asLong();

        ItemStackHandler items = barrel.getIngredient();

        int globalIndex = 0;
        for (int index = 0; index < items.getSlots(); index++) {
            ItemStack stack = items.getStackInSlot(index);
            int count = stack.getCount() / 2 + 1;
            if (!stack.isEmpty()) {
                for (int i = 0; i < count; i++) {
                    poseStack.pushPose();

                    float x = stableRandom(seed, globalIndex, index + 1) * 0.4f;
                    float z = stableRandom(seed, globalIndex, index + 2) * 0.4f;
                    float y = (float) (globalIndex / 4) * 0.025f + stableRandom(seed, globalIndex, index + 3) * 0.05f;

                    // 添加一些上下浮动效果
                    y += (float) (Math.sin(time / 10f + globalIndex) * 0.02f);

                    float yRot = stableRandom(seed, globalIndex, index + 4) * 5f;
                    float zRot = stableRandom(seed, globalIndex, index + 5) * 360f;

                    poseStack.translate(0.5f + x, 2.7f + y, 0.5f + z);
                    poseStack.scale(0.5f, 0.5f, 0.5f);
                    poseStack.mulPose(Axis.XN.rotationDegrees(90));
                    poseStack.mulPose(Axis.YN.rotationDegrees(yRot));
                    poseStack.mulPose(Axis.ZN.rotationDegrees(zRot));

                    itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, packedLight,
                            packedOverlay, poseStack, buffer, barrel.getLevel(), 0);
                    poseStack.popPose();
                    globalIndex++;
                }
            }
        }
    }
}
