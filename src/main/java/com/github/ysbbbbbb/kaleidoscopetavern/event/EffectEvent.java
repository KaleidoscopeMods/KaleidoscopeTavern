package com.github.ysbbbbbb.kaleidoscopetavern.event;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraftforge.event.TickEvent;
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
        int healAmount = (int) Math.floor(target.getMaxHealth() / 3.0f);
        if (healAmount > 0) {
            living.heal(healAmount);
        }
    }

    /**
     * 醇热效果饥饿耗尽移除逻辑。
     * 不能在 applyEffectTick 内调用 removeEffect，因为 tickEffects() 正在用 Iterator 遍历
     * activeEffects map，直接 remove 会导致 ConcurrentModificationException。
     * 此处在 PlayerTickEvent 中安全执行移除。
     */
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        Player player = event.player;
        if (player.level().isClientSide()) {
            return;
        }

        MobEffectInstance ardentHeat = player.getEffect(ModEffects.ARDENT_HEAT.get());
        if (ardentHeat == null) {
            return;
        }

        // 饥饿值和饱和度都耗尽时，立刻移除醇热并给予 30 秒饥饿
        FoodData food = player.getFoodData();
        if (food.getFoodLevel() <= 0 && food.getSaturationLevel() <= 0.01F) {
            player.removeEffect(ModEffects.ARDENT_HEAT.get());
            player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 600, 0));
        }
    }
}
