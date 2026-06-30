package com.github.ysbbbbbb.kaleidoscopetavern.event;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModEffects;
import com.github.ysbbbbbb.kaleidoscopetavern.init.tag.TagMod;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.jetbrains.annotations.Nullable;

@EventBusSubscriber(modid = KaleidoscopeTavern.MOD_ID)
public class EffectEvent {
    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (event.getEntity().level().isClientSide) {
            return;
        }

        LivingEntity target = event.getEntity();
        @Nullable Entity source = event.getSource().getEntity();

        if (!(source instanceof LivingEntity living) || !living.hasEffect(ModEffects.BLOODY_MARY)) {
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
     * 摸金校尉：攻击指定类型的生物时，有 30% 概率将目标主手物品耐久降至 1 后卸下掉落。
     */
    @SubscribeEvent
    public static void onLivingHurt(LivingDamageEvent.Pre event) {
        if (event.getEntity().level().isClientSide) {
            return;
        }

        @Nullable Entity source = event.getSource().getEntity();
        if (!(source instanceof LivingEntity attacker) || !attacker.hasEffect(ModEffects.TOMB_RAIDER)) {
            return;
        }

        LivingEntity target = event.getEntity();
        if (!target.getType().is(TagMod.TOMB_RAIDER_DISARMABLE)) {
            return;
        }

        // 30% 概率触发卸装
        if (target.getRandom().nextFloat() >= 0.3F) {
            return;
        }

        ItemStack mainHand = target.getItemBySlot(EquipmentSlot.MAINHAND);
        if (mainHand.isEmpty()) {
            return;
        }

        // 将耐久降至1（如果物品有耐久度）
        if (mainHand.isDamageableItem()) {
            mainHand.setDamageValue(mainHand.getMaxDamage() - 1);
        }

        // 从主手卸下并掉落到地上
        target.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
        ItemEntity itemEntity = new ItemEntity(target.level(), target.getX(), target.getY(), target.getZ(), mainHand);
        itemEntity.setPickUpDelay(40);
        target.level().addFreshEntity(itemEntity);
    }

    /**
     * 醇热效果饥饿耗尽移除逻辑。
     * 不能在 applyEffectTick 内调用 removeEffect，因为 tickEffects() 正在用 Iterator 遍历
     * activeEffects map，直接 remove 会导致 ConcurrentModificationException。
     * 此处在 PlayerTickEvent 中安全执行移除。
     */
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().isClientSide()) {
            return;
        }

        MobEffectInstance ardentHeat = player.getEffect(ModEffects.ARDENT_HEAT);
        if (ardentHeat == null) {
            return;
        }

        // 饥饿值和饱和度都耗尽时，立刻移除醇热并给予 30 秒饥饿
        FoodData food = player.getFoodData();
        if (food.getFoodLevel() <= 0 && food.getSaturationLevel() <= 0.01F) {
            player.removeEffect(ModEffects.ARDENT_HEAT);
            player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 600, 0));
        }
    }
}
