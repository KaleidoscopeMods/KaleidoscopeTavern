package com.github.ysbbbbbb.kaleidoscopetavern.effect;

import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class XpDrainEffect extends BaseEffect {
    private static final double PULL_RADIUS = 8.0;
    private static final double TOUCH_DISTANCE = 1.5;

    public XpDrainEffect(int color) {
        super(color);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if (!(livingEntity instanceof Player player)) {
            return;
        }
        Level level = player.level();
        if (level.isClientSide()) {
            return;
        }

        // 消除经验球拾取冷却
        player.takeXpDelay = 0;

        // 每 5 tick 检查一次
        if (player.tickCount % 5 != 0) {
            return;
        }

        // 吸引8格内的经验球
        AABB area = player.getBoundingBox().inflate(PULL_RADIUS);
        List<ExperienceOrb> orbs = level.getEntitiesOfClass(ExperienceOrb.class, area);

        for (ExperienceOrb orb : orbs) {
            if (!orb.isAlive()) {
                continue;
            }

            double dist = orb.distanceTo(player);
            if (dist < TOUCH_DISTANCE) {
                orb.playerTouch(player);
                // 确保经验球被吸引时能立即被拾取
                player.takeXpDelay = 0;
            } else {
                Vec3 direction = player.position().add(0, 0.5, 0).subtract(orb.position()).normalize();
                double speed = Math.min(0.5 + (1.0 / dist), 1.5);
                orb.setDeltaMovement(direction.scale(speed));
            }
        }
    }
}
