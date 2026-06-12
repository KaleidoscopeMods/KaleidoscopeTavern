package com.github.ysbbbbbb.kaleidoscopetavern.item;

import com.github.ysbbbbbb.kaleidoscopetavern.datamap.data.DrinkEffectData;
import com.github.ysbbbbbb.kaleidoscopetavern.datamap.resources.DrinkEffectDataReloadListener;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import com.google.common.collect.Lists;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CocktailBlockItem extends GlasswareBlockItem implements IHasContainer {
    public CocktailBlockItem(Block block) {
        super(block);
    }

    public CocktailBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 32;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();

        // 只有潜行时才放置
        if (player == null || player.isShiftKeyDown()) {
            return this.place(new BlockPlaceContext(context));
        }

        // 否则尝试喝下去
        Level level = context.getLevel();
        InteractionResult result = this.use(level, player, context.getHand()).getResult();
        return result == InteractionResult.CONSUME ? InteractionResult.CONSUME_PARTIAL : result;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return ItemUtils.startUsingInstantly(level, player, hand);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (entity instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack);
            serverPlayer.awardStat(Stats.ITEM_USED.get(this));
        }
        this.addDrinkEffect(stack, level, entity);
        if (entity instanceof Player player && !player.isCreative()) {
            stack.shrink(1);
        }
        return returnContainerToEntity(stack, level, entity);
    }

    protected void addDrinkEffect(ItemStack drink, Level level, LivingEntity entity) {
        DrinkEffectData effectData = DrinkEffectDataReloadListener.INSTANCE.get(drink.getItem());
        if (effectData == null) {
            return;
        }
        var effects = effectData.effects();
        if (effects.isEmpty()) {
            return;
        }
        // 鸡尾酒没有酿造等级，直接取第一层效果
        for (DrinkEffectData.Entry entry : effects.get(0)) {
            if (!level.isClientSide && level.random.nextFloat() < entry.probability()) {
                MobEffect effect = entry.effect();
                int amplifier = entry.amplifier();
                if (effect.isInstantenous()) {
                    // 瞬时效果直接触发，不通过 addEffect
                    effect.applyInstantenousEffect(entity, entity, entity, amplifier, 1.0);
                } else {
                    // json 里的持续时间是秒，但是内部游戏是 tick，需要转化
                    int duration = entry.duration() * 20;
                    MobEffectInstance instance = new MobEffectInstance(effect, duration, amplifier);
                    entity.addEffect(instance);
                }
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        DrinkEffectData effectData = DrinkEffectDataReloadListener.INSTANCE.get(stack.getItem());
        if (effectData == null || effectData.effects().isEmpty()) {
            return;
        }

        List<MobEffectInstance> effectsShow = Lists.newArrayList();
        for (DrinkEffectData.Entry entry : effectData.effects().get(0)) {
            if (entry.probability() >= 1.0F) {
                MobEffect effect = entry.effect();
                int duration = entry.duration() * 20;
                int amplifier = entry.amplifier();
                effectsShow.add(new MobEffectInstance(effect, duration, amplifier));
            }
        }

        if (!effectsShow.isEmpty()) {
            PotionUtils.addPotionTooltip(effectsShow, tooltip, 1.0F);
        }
    }

    @Override
    public Item getContainerItem() {
        return ModItems.EMPTY_GLASSWARE.get();
    }
}
