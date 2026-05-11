package com.github.ysbbbbbb.kaleidoscopetavern.effect;

import com.github.ysbbbbbb.kaleidoscopetavern.init.ModSounds;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.function.Predicate;

public class VisionEffect extends BaseEffect {
    public VisionEffect(int color) {
        super(color);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration % 100 == 0;
    }

    @Override
    public void applyEffectTick(LivingEntity living, int amplifier) {
        Level level = living.level();
        double radius = Math.min(amplifier + 1, 3) * 6;
        AABB range = living.getBoundingBox().inflate(radius);
        Predicate<LivingEntity> filter = entity -> entity != living && entity.isAlive();

        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, range, filter);
        if (entities.isEmpty()) {
            return;
        }
        // 只有新生物附加了效果才播放声音，避免反复播放
        boolean apply = false;
        for (LivingEntity entity : entities) {
            if (!entity.hasEffect(MobEffects.GLOWING)) {
                apply = true;
            }
            entity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 120));
        }
        if (apply) {
            living.playSound(ModSounds.EFFECT_VISION.get());
        }
    }
}
