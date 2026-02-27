package com.github.ysbbbbbb.kaleidoscopetavern.item;

import com.github.ysbbbbbb.kaleidoscopetavern.api.blockentity.IBarrel;
import com.github.ysbbbbbb.kaleidoscopetavern.block.brew.DrinkBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.brew.DrinkBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.datamap.data.DrinkEffectData;
import com.github.ysbbbbbb.kaleidoscopetavern.datamap.resources.DrinkEffectDataReloadListener;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class DrinkBlockItem extends BottleBlockItem implements IHasContainer {
    public DrinkBlockItem(Block block) {
        super(block, new Properties()
                .stacksTo(16)
                .craftRemainder(ModItems.EMPTY_BOTTLE.get()));
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
        InteractionResult result = this.use(level, player, context.getHand()).getResult();
        return result == InteractionResult.CONSUME ? InteractionResult.CONSUME_PARTIAL : result;
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
        int brewLevel = BottleBlockItem.getBrewLevel(drink);
        if (brewLevel < IBarrel.BREWING_STARTED || brewLevel > effects.size()) {
            return;
        }
        // brew level 从 1 开始，所以要 -1 来获取对应的效果列表
        for (DrinkEffectData.Entry entry : effects.get(brewLevel - 1)) {
            if (!level.isClientSide && level.random.nextFloat() < entry.probability()) {
                MobEffect effect = entry.effect();
                // json 里的持续时间是秒，但是内部游戏是 tick，需要转化
                int duration = entry.duration() * 20;
                int amplifier = entry.amplifier();
                MobEffectInstance instance = new MobEffectInstance(effect, duration, amplifier);
                entity.addEffect(instance);
            }
        }
    }

    public void makeAreaOfEffectCloud(Level level, double x, double y, double z, int brewLevel, @Nullable Entity owner) {
        DrinkEffectData effectData = DrinkEffectDataReloadListener.INSTANCE.get(this);
        if (effectData == null) {
            return;
        }
        var effects = effectData.effects();
        if (brewLevel < IBarrel.BREWING_STARTED || brewLevel > effects.size()) {
            return;
        }

        AreaEffectCloud cloud = new AreaEffectCloud(level, x, y, z);
        if (owner instanceof LivingEntity livingEntity) {
            cloud.setOwner(livingEntity);
        }
        cloud.setRadius(brewLevel / 2f);
        cloud.setRadiusOnUse(-0.5F);
        cloud.setWaitTime(5);
        cloud.setRadiusPerTick(-cloud.getRadius() / (float) cloud.getDuration());

        // brew level 从 1 开始，所以要 -1 来获取对应的效果列表
        for (DrinkEffectData.Entry entry : effects.get(brewLevel - 1)) {
            if (level.random.nextFloat() < entry.probability()) {
                MobEffect effect = entry.effect();
                int duration = entry.duration() * 20;
                int amplifier = entry.amplifier();
                cloud.addEffect(new MobEffectInstance(effect, duration, amplifier));
            }
        }

        level.addFreshEntity(cloud);
    }

    @Override
    public Item getContainerItem() {
        return ModItems.EMPTY_BOTTLE.get();
    }
}