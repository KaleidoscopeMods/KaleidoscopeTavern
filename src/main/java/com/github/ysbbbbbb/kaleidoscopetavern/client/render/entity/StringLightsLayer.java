package com.github.ysbbbbbb.kaleidoscopetavern.client.render.entity;

import com.github.ysbbbbbb.kaleidoscopetavern.item.StringLightsBlockItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class StringLightsLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
    private final ItemInHandRenderer itemRenderer;

    public StringLightsLayer(RenderLayerParent<T, M> renderer) {
        super(renderer);
        this.itemRenderer = Minecraft.getInstance().gameRenderer.itemInHandRenderer;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T entity,
                       float limbSwing, float limbSwingAmount, float partialTick,
                       float ageInTicks, float netHeadYaw, float headPitch) {
        ItemStack stack = entity.getItemBySlot(EquipmentSlot.CHEST);
        if (stack.getItem() instanceof StringLightsBlockItem && this.getParentModel() instanceof HumanoidModel<?> humanoidModel) {
            poseStack.pushPose();
            humanoidModel.body.translateAndRotate(poseStack);
            poseStack.translate(0f, -0.1875f, -0.4375f);
            poseStack.mulPose(Axis.YP.rotationDegrees(180f));
            poseStack.scale(-0.625f, -0.625f, 0.625f);
            this.itemRenderer.renderItem(entity, stack, ItemDisplayContext.HEAD, false, poseStack, buffer, packedLight);
            poseStack.popPose();
        }
    }
}
