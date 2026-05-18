package com.github.ysbbbbbb.kaleidoscopetavern.item;

import com.github.ysbbbbbb.kaleidoscopetavern.api.blockentity.IBarrel;
import com.github.ysbbbbbb.kaleidoscopetavern.block.brew.DrinkBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.brew.DrinkBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.datamap.data.DrinkEffectData;
import com.github.ysbbbbbb.kaleidoscopetavern.datamap.resources.DrinkEffectDataReloadListener;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrownSplashPotion;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class DrinkBlockItem extends BottleBlockItem implements IHasContainer {
    public DrinkBlockItem(Identifier id, Block block) {
        super(block, new Properties()
                .stacksTo(16)
                .component(DataComponents.CONSUMABLE, Consumables.DEFAULT_DRINK)
                .setId(ResourceKey.create(Registries.ITEM, id))
                .craftRemainder(ModItems.EMPTY_BOTTLE.get()));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        BlockPos pos = context.getClickedPos();
        ItemStack stack = context.getItemInHand();

        BlockState state = level.getBlockState(pos);
        Block self = this.getBlock();

        // 只有潜行时才放置
        if (player == null || player.isShiftKeyDown()) {
            // 先检查能够添加数量
            if (player != null && tryIncreaseCount(self, state, level, pos, stack, player)) {
                return InteractionResult.SUCCESS;
            }
            return this.place(new BlockPlaceContext(context));
        }

        // 否则尝试喝下去
        return this.use(level, player, context.getHand());
    }

    private boolean tryIncreaseCount(Block self, BlockState state, Level level, BlockPos pos, ItemStack stack, Player player) {
        if (self instanceof DrinkBlock drink && state.is(self) && drink.tryIncreaseCount(level, pos, state, stack)) {
            SoundType soundType = state.getSoundType(level, pos, player);
            SoundEvent sound = this.getPlaceSound(state, level, pos, player);
            level.playSound(
                    player, pos, sound, SoundSource.BLOCKS,
                    (soundType.getVolume() + 1) / 2f,
                    soundType.getPitch() * 0.8f
            );
            if (!player.isCreative()) {
                stack.shrink(1);
            }
            return true;
        }
        return false;
    }

    @Override
    protected boolean updateCustomBlockEntityTag(BlockPos pos, Level level, @Nullable Player player, ItemStack stack, BlockState state) {
        // 首次放置需要添加物品信息
        if (level.getBlockEntity(pos) instanceof DrinkBlockEntity be && be.addItem(stack)) {
            be.refresh();
        }
        return super.updateCustomBlockEntityTag(pos, level, player, stack, state);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        this.addDrinkEffect(stack, level, entity);
        super.finishUsingItem(stack, level, entity);
        return returnContainerToEntity(stack, level, entity);
    }

    protected void addDrinkEffect(ItemStack drink, Level level, LivingEntity entity) {
        DrinkEffectData effectData = DrinkEffectDataReloadListener.get(drink);
        if (effectData == null) {
            return;
        }
        var effects = effectData.effects();
        if (effects.isEmpty()) {
            return;
        }
        int brewLevel = BottleBlockItem.getBrewLevel(drink);
        if (brewLevel < IBarrel.BREWING_STARTED) {
            return;
        }
        brewLevel = Math.min(brewLevel, effects.size());
        // brew level 从 1 开始，所以要 -1 来获取对应的效果列表
        for (DrinkEffectData.Entry entry : effects.get(brewLevel - 1)) {
            if (!level.isClientSide() && level.getRandom().nextFloat() < entry.probability()) {
                // json 里的持续时间是秒，但是内部游戏是 tick，需要转化
                int duration = entry.duration() * 20;
                int amplifier = entry.amplifier();
                MobEffectInstance instance = new MobEffectInstance(entry.effect(), duration, amplifier);
                entity.addEffect(instance);
            }
        }
    }

    public void makeThrownPotion(Level level, double x, double y, double z, int brewLevel, @Nullable Entity owner) {
        DrinkEffectData effectData = DrinkEffectDataReloadListener.get(this);
        if (effectData == null) {
            return;
        }
        var effects = effectData.effects();
        if (effects.isEmpty()) {
            return;
        }
        brewLevel = BottleBlockItem.clampBrewLevel(brewLevel);
        if (brewLevel < IBarrel.BREWING_STARTED) {
            return;
        }
        brewLevel = Math.min(brewLevel, effects.size());

        // brew level 从 1 开始，所以要 -1 来获取对应的效果列表
        List<MobEffectInstance> instances = Lists.newArrayList();
        for (DrinkEffectData.Entry entry : effects.get(brewLevel - 1)) {
            if (level.getRandom().nextFloat() < entry.probability()) {
                int duration = entry.duration() * 20;
                int amplifier = entry.amplifier();
                instances.add(new MobEffectInstance(entry.effect(), duration, amplifier));
            }
        }

        // 生成投掷药水实体（26.1 使用 ThrownSplashPotion）
        ItemStack stack = new ItemStack(this);
        PotionContents contents = new PotionContents(Optional.empty(), Optional.empty(), instances, Optional.empty());
        stack.set(DataComponents.POTION_CONTENTS, contents);

        ThrownSplashPotion potion = new ThrownSplashPotion(level, x, y, z, stack);
        if (owner instanceof LivingEntity livingEntity) {
            potion.setOwner(livingEntity);
        }

        level.addFreshEntity(potion);
    }

    @Override
    public Item getContainerItem() {
        return ModItems.EMPTY_BOTTLE.get();
    }
}