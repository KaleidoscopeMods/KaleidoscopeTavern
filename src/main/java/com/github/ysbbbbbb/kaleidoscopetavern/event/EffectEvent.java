package com.github.ysbbbbbb.kaleidoscopetavern.event;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Nullable;

@Mod.EventBusSubscriber(modid = KaleidoscopeTavern.MOD_ID)
public class EffectEvent {
    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (event.getEntity().level().isClientSide) {
            return;
        }

        LivingEntity target = event.getEntity();
        @Nullable Entity source = event.getSource().getEntity();

        if (!(source instanceof LivingEntity living) || !living.hasEffect(ModEffects.BLOODY_MARY.get())) {
            return;
        }
        if (target == living) {
            return;
        }
        int healAmount = (int) Math.floor(target.getMaxHealth() / 5.0f);
        if (healAmount > 0) {
            living.heal(healAmount);
        }
    }
}
