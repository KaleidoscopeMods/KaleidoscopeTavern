package com.github.ysbbbbbb.kaleidoscopetavern.effect;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class UpsideDownEffect extends BaseEffect {
    private static final double RANGE = 16.0;
    private static final Component GRUMM = Component.literal("Grumm");

    public UpsideDownEffect(int color) {
        super(MobEffectCategory.NEUTRAL, color);
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

        AABB area = user.getBoundingBox().inflate(RANGE);
        List<Mob> entities = level.getEntitiesOfClass(Mob.class, area, Entity::isAlive);

        for (Mob entity : entities) {
            entity.setCustomName(GRUMM);
            entity.setCustomNameVisible(false);
        }
    }
}
