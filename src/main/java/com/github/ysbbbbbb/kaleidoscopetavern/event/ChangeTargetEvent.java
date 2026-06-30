package com.github.ysbbbbbb.kaleidoscopetavern.event;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;

import static com.github.ysbbbbbb.kaleidoscopetavern.effect.GrassStealthEffect.notInGrassStealthPlant;

@EventBusSubscriber(modid = KaleidoscopeTavern.MOD_ID)
public class ChangeTargetEvent {
    @SubscribeEvent
    public static void onTarget(LivingChangeTargetEvent event) {
        LivingEntity newTarget = event.getNewAboutToBeSetTarget();
        if (newTarget == null || !newTarget.isAlive()) {
            return;
        }

        // 穿草隐身
        if (newTarget.hasEffect(ModEffects.GRASS_STEALTH) && newTarget.isShiftKeyDown()) {
            Level level = newTarget.level();
            BlockPos pos = newTarget.blockPosition();
            BlockPos abovePos = pos.above();
            if (notInGrassStealthPlant(level, pos) && notInGrassStealthPlant(level, abovePos)) {
                return;
            }
            event.setCanceled(true);
        }
    }
}
