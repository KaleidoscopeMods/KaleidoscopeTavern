package com.github.ysbbbbbb.kaleidoscopetavern.client.render.block;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.block.deco.BarStoolBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco.BarStoolBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.client.model.deco.BarStoolBodyModel;
import com.github.ysbbbbbb.kaleidoscopetavern.entity.SitEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Function;

@OnlyIn(Dist.CLIENT)
public class BarStoolBlockEntityRender implements BlockEntityRenderer<BarStoolBlockEntity> {
    private final BarStoolBodyModel model;
    private final Function<DyeColor, ResourceLocation> texture = Util.memoize(color -> {
        String path = "textures/entity/deco/bar_stool/%s.png".formatted(color.getName());
        return KaleidoscopeTavern.modLoc(path);
    });

    public BarStoolBlockEntityRender(BlockEntityRendererProvider.Context context) {
        this.model = new BarStoolBodyModel(context.bakeLayer(BarStoolBodyModel.LAYER_LOCATION));
    }

    @Override
    public void render(BarStoolBlockEntity blockEntity, float partialTick, PoseStack poseStack,
                       MultiBufferSource multiBufferSource, int packedLight, int packedOverlay) {
        float renderRot = blockEntity.getBlockState().getValue(BarStoolBlock.FACING).toYRot();
        SitEntity sit = blockEntity.getSitEntity();
        if (sit != null && sit.getFirstPassenger() instanceof LivingEntity passenger) {
            renderRot = Mth.wrapDegrees(Mth.lerp(partialTick, passenger.yBodyRotO, passenger.yBodyRot));
        }

        poseStack.pushPose();
        poseStack.translate(0.5, 1.5, 0.5);
        poseStack.mulPose(Axis.ZN.rotationDegrees(180.0F));
        poseStack.mulPose(Axis.YN.rotationDegrees(180.0F - renderRot));

        ResourceLocation texture = this.texture.apply(blockEntity.getColor());
        VertexConsumer consumer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(texture));
        this.model.renderToBuffer(poseStack, consumer, packedLight, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);

        poseStack.popPose();
    }
}
