package com.github.ysbbbbbb.kaleidoscopetavern.client.animation;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class ShakerAnimation {
    public static final AnimationDefinition PUT = AnimationDefinition.Builder.withLength(0.375F)
            .addAnimation("root", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.0833F, KeyframeAnimations.degreeVec(0.0F, -4.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.1667F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("bone2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.0833F, KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.2083F, KeyframeAnimations.degreeVec(7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.375F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .addAnimation("bone2", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.0833F, KeyframeAnimations.posVec(0.0F, 2.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.2083F, KeyframeAnimations.posVec(0.0F, 1.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.2917F, KeyframeAnimations.posVec(0.0F, 0.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            )).build();

    public static final HumanoidModel.ArmPose SHAKING = HumanoidModel.ArmPose.create("KT_SHAKING", false,
            (model, entity, arm) -> {
                int remainingTicks = entity.getUseItemRemainingTicks();
                if (remainingTicks == 0) {
                    return;
                }
                float totalTicks = entity.tickCount + Minecraft.getInstance().getPartialTick();
                float rot = (float) Math.sin(totalTicks * 1.5f) * 0.25f;
                if (arm == HumanoidArm.RIGHT) {
                    model.rightArm.xRot = 1.375f * Mth.PI - Mth.PI * rot;
                    model.rightArm.zRot = -Mth.PI * 0.05f;
                } else {
                    model.leftArm.xRot = 1.375f * Mth.PI + Mth.PI * rot;
                    model.leftArm.zRot = Mth.PI * 0.05f;
                }
            });

    public static class ShakerExtensions implements IClientItemExtensions {
        @Override
        public HumanoidModel.ArmPose getArmPose(LivingEntity entity, InteractionHand hand, ItemStack stack) {
            if (!stack.isEmpty()) {
                return SHAKING;
            }
            return HumanoidModel.ArmPose.EMPTY;
        }

        @Override
        public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm, ItemStack itemInHand,
                                               float partialTick, float equipProcess, float swingProcess) {
            int remainingTicks = player.getUseItemRemainingTicks();
            if (remainingTicks == 0) {
                return false;
            }
            float totalTicks = player.tickCount + partialTick;
            double xOffset = arm == HumanoidArm.RIGHT ? 0.56 : -0.56;
            double offset = (float) Math.sin(totalTicks * 1.5) * 0.25;
            poseStack.translate(xOffset, -0.52 - offset * 0.6, -0.72);
            return true;
        }
    }

    /**
     * 没别的用，仅用于提前触发 SHAKING 的加载
     */
    public static void trigger() {
    }
}
