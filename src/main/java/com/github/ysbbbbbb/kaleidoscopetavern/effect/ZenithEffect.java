package com.github.ysbbbbbb.kaleidoscopetavern.effect;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import org.jetbrains.annotations.Nullable;

public class ZenithEffect extends BaseEffect {
    public ZenithEffect(int color) {
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

        BlockPos pos = user.blockPosition();
        int surfaceY = level.getHeight(Heightmap.Types.MOTION_BLOCKING, pos.getX(), pos.getZ());

        // 只在低于地面时传送（在洞穴里）
        if (pos.getY() >= surfaceY) {
            return;
        }

        double targetX = pos.getX() + 0.5;
        double targetZ = pos.getZ() + 0.5;

        // 传送前播放音效
        level.playSound(null, user.getX(), user.getY(), user.getZ(),
                SoundEvents.CHORUS_FRUIT_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.0F);

        user.teleportTo(targetX, surfaceY, targetZ);
        user.fallDistance = 0.0F;

        // 传送后在目标位置播放音效
        level.playSound(null, targetX, surfaceY, targetZ,
                SoundEvents.CHORUS_FRUIT_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.0F);

        // 给予 30s 饥饿 buff
        user.addEffect(new MobEffectInstance(MobEffects.HUNGER, 600, 0));
    }
}
