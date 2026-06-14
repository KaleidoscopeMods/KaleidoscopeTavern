package com.github.ysbbbbbb.kaleidoscopetavern.event;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.github.ysbbbbbb.kaleidoscopetavern.effect.GrassStealthEffect.notInGrassStealthPlant;

@Mod.EventBusSubscriber(modid = KaleidoscopeTavern.MOD_ID)
public class ChangeTargetEvent {
    @SubscribeEvent
    public static void onTarget(LivingChangeTargetEvent event) {
        LivingEntity newTarget = event.getNewTarget();
        if (newTarget == null || !newTarget.isAlive()) {
            return;
        }

        // 穿草隐身
        if (newTarget.hasEffect(ModEffects.GRASS_STEALTH.get()) && newTarget.isShiftKeyDown()) {
            Level level = newTarget.level();
            BlockPos pos = newTarget.blockPosition();
            BlockPos abovePos = pos.above();
            if (notInGrassStealthPlant(level, pos) && notInGrassStealthPlant(level, abovePos)) {
                return;
            }
            event.setCanceled(true);
        }

        // 摸金校尉：猪灵/猪灵蛮兵不会仇恨玩家
        if (event.getEntity() instanceof AbstractPiglin && newTarget.hasEffect(ModEffects.TOMB_RAIDER.get())) {
            event.setCanceled(true);
        }
    }
}
