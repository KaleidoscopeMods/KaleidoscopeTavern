package com.github.ysbbbbbb.kaleidoscopetavern.client.render.entity;


import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.entity.SitEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class SitRenderer extends EntityRenderer<SitEntity> {
    private static final ResourceLocation EMPTY = KaleidoscopeTavern.modLoc("textures/entity/empty.png");

    public SitRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(SitEntity entitySit, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
    }

    @Override
    public ResourceLocation getTextureLocation(SitEntity entitySit) {
        return EMPTY;
    }
}
