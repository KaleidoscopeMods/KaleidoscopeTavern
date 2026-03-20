package com.github.ysbbbbbb.kaleidoscopetavern.client.model.deco;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class BarStoolBodyModel extends Model {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(KaleidoscopeTavern.modLoc("bar_stool"), "main");
    private final ModelPart bone;

    public BarStoolBodyModel(ModelPart root) {
        super(RenderType::entityCutoutNoCull);
        this.bone = root.getChild("bone");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(0, 18).addBox(-6.0F, -15.0F, -5.0F, 12.0F, 3.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-6.0F, -21.0F, 3.0F, 12.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cube_r1 = bone.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(46, 11).addBox(-1.0F, -0.5F, -3.5F, 2.0F, 4.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(46, 0).addBox(-13.0F, -0.5F, -3.5F, 2.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, -16.6F, 0.3F, 0.3927F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int i, int j, float f, float g, float h, float k) {
        this.bone.render(poseStack, vertexConsumer, i, j, f, g, h, k);
    }
}
