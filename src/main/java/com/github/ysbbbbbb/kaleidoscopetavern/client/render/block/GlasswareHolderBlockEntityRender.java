package com.github.ysbbbbbb.kaleidoscopetavern.client.render.block;

import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco.GlasswareHolderBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.items.ItemStackHandler;

public class GlasswareHolderBlockEntityRender implements BlockEntityRenderer<GlasswareHolderBlockEntity> {
    private final BlockRenderDispatcher dispatcher;

    public GlasswareHolderBlockEntityRender(BlockEntityRendererProvider.Context context) {
        this.dispatcher = context.getBlockRenderDispatcher();
    }

    @Override
    public void render(GlasswareHolderBlockEntity holder, float partialTick, PoseStack poseStack,
                       MultiBufferSource buffer, int packedLight, int packedOverlay) {
        ItemStackHandler items = holder.getItems();
        for (int i = 0; i < items.getSlots(); i++) {
            ItemStack stack = items.getStackInSlot(i);
            if (!stack.isEmpty() && stack.getItem() instanceof BlockItem blockItem) {
                poseStack.pushPose();
                poseStack.translate(-0.25, 0.76, 0.75);
                poseStack.translate(0.5 * (i % 2), 0, 0.5 * (i / 2));
                poseStack.mulPose(Axis.XN.rotationDegrees(180));
                this.dispatcher.renderSingleBlock(blockItem.getBlock().defaultBlockState(), poseStack, buffer,
                        packedLight, packedOverlay, ModelData.EMPTY, RenderType.cutout());
                poseStack.popPose();
            }
        }
    }
}
