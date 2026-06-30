package com.github.ysbbbbbb.kaleidoscopetavern.effect;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ShriekAttackEffect extends BaseEffect {
    private static final double MAX_DISTANCE = 32.0;
    private static final double WAVE_THICKNESS = 1.0;
    // 大约 0.63 blocks/tick 初速度 ≈ 水平位移 7 格（摩擦系数 0.91）
    private static final double HORIZONTAL_SPEED = 0.63;
    // 大约 0.28 blocks/tick 初速度 ≈ 垂直位移 0.5 格
    private static final double VERTICAL_SPEED = 0.28;

    public ShriekAttackEffect(int color) {
        super(color);
    }

    @Override
    public boolean isInstantenous() {
        return true;
    }

    @Override
    public void applyInstantenousEffect(@Nullable Entity source, @Nullable Entity indirectSource,
                                        LivingEntity livingEntity, int amplifier, double health) {
        performEffect(livingEntity);
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
        performEffect(livingEntity);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration == 1;
    }

    private void performEffect(LivingEntity user) {
        Level level = user.level();
        if (level.isClientSide()) {
            return;
        }

        Vec3 eyePos = user.getEyePosition();
        Vec3 lookVec = user.getLookAngle();
        float damage = user.getHealth() * 1.2F;

        // 播放音波音效
        level.playSound(null, user.getX(), user.getY(), user.getZ(),
                SoundEvents.WARDEN_SONIC_BOOM, SoundSource.PLAYERS, 1.0F, 1.0F);

        // 搜索音波路径上的所有生物
        AABB searchArea = user.getBoundingBox().inflate(MAX_DISTANCE);
        List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class, searchArea,
                e -> e != user && e.isAlive());

        // 水平击退方向
        Vec3 horizComponent = new Vec3(lookVec.x, 0, lookVec.z);
        if (horizComponent.lengthSqr() < 0.001) {
            // 完全朝上或朝下看，没有水平方向
            horizComponent = Vec3.ZERO;
        } else {
            horizComponent = horizComponent.normalize();
        }

        for (LivingEntity target : targets) {
            Vec3 targetCenter = target.position().add(0, target.getBbHeight() / 2.0, 0);
            Vec3 toTarget = targetCenter.subtract(eyePos);
            double dot = toTarget.dot(lookVec);

            // 目标必须在视线前方且在最大距离内
            if (dot < 0 || dot > MAX_DISTANCE) {
                continue;
            }

            // 计算目标到音波射线的垂直距离
            Vec3 closestPoint = eyePos.add(lookVec.scale(dot));
            double perpDistance = closestPoint.distanceTo(targetCenter);

            if (perpDistance > WAVE_THICKNESS + target.getBbWidth() / 2.0) {
                continue;
            }

            // 造成伤害（使用 sonicBoom 绕过护甲和盾牌）
            target.hurt(level.damageSources().sonicBoom(user), damage);

            // 击退：水平 7 格、垂直 0.5 格
            target.setDeltaMovement(target.getDeltaMovement().add(
                    horizComponent.x * HORIZONTAL_SPEED,
                    VERTICAL_SPEED,
                    horizComponent.z * HORIZONTAL_SPEED
            ));
            target.hurtMarked = true;
        }

        // 发送音波粒子效果
        if (level instanceof ServerLevel serverLevel) {
            Vec3 particlePos = eyePos;
            for (int i = 0; i < (int) (MAX_DISTANCE / 2); i++) {
                particlePos = particlePos.add(lookVec.scale(2));
                serverLevel.sendParticles(
                        ParticleTypes.SONIC_BOOM,
                        particlePos.x, particlePos.y, particlePos.z,
                        1, 0, 0, 0, 0
                );
            }
        }
    }
}
