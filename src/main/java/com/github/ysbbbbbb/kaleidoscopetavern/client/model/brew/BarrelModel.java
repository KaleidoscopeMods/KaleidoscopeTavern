package com.github.ysbbbbbb.kaleidoscopetavern.client.model.brew;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;

public class BarrelModel extends Model {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(KaleidoscopeTavern.modLoc("barrel"), "main");
    private final ModelPart root;
    private final ModelPart close;
    private final ModelPart open;
    private final ModelPart body;

    public BarrelModel(ModelPart root) {
        super(RenderType::entityCutoutNoCull);
        this.root = root.getChild("root");
        this.close = this.root.getChild("close");
        this.open = this.root.getChild("open");
        this.body = this.root.getChild("body");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(8.0F, 9.0F, -9.0F));

        PartDefinition close = root.addOrReplaceChild("close", CubeListBuilder.create().texOffs(102, 113).addBox(-16.0F, -33.0F, 1.0F, 16.0F, 2.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition open = root.addOrReplaceChild("open", CubeListBuilder.create(), PartPose.offsetAndRotation(-8.0F, -33.0F, -1.0F, 1.309F, 0.0F, 0.0F));

        PartDefinition open_r1 = open.addOrReplaceChild("open_r1", CubeListBuilder.create().texOffs(106, 114).mirror().addBox(0.0F, -4.0F, -12.0F, 0.0F, 2.0F, 20.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(7.0F, 9.7654F, 5.0978F, -0.6109F, 0.0F, 0.0F));

        PartDefinition open_r2 = open.addOrReplaceChild("open_r2", CubeListBuilder.create().texOffs(102, 113).addBox(-8.0F, -2.4F, -2.8F, 16.0F, 2.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 17.0F, 8.0F, 0.5672F, 0.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(28, 136).addBox(6.0F, 7.0F, -10.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(28, 136).addBox(-26.0F, 7.0F, -10.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(174, 118).addBox(-22.0F, 7.0F, -8.0F, 28.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(174, 118).addBox(-22.0F, 7.0F, 22.0F, 28.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(28, 136).addBox(6.0F, 7.0F, 22.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(28, 136).addBox(-26.0F, 7.0F, 22.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-28.0F, -33.0F, -15.0F, 40.0F, 40.0F, 48.0F, new CubeDeformation(0.0F))
                .texOffs(0, 160).addBox(-16.0F, -33.0F, 1.0F, 16.0F, 21.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 144).addBox(0.0F, -33.0F, 1.0F, 0.0F, 24.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(0, 144).addBox(-16.0F, -33.0F, 1.0F, 0.0F, 21.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(32, 160).addBox(-16.0F, -12.0F, 1.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(0, 160).addBox(-16.0F, -33.0F, 17.0F, 16.0F, 21.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        root.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }

    public ModelPart getClose() {
        return close;
    }

    public ModelPart getOpen() {
        return open;
    }
}
