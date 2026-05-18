package com.github.ysbbbbbb.kaleidoscopetavern.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.consume_effects.RemoveStatusEffectsConsumeEffect;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;

import java.util.function.Supplier;

import static net.minecraft.world.item.component.Consumables.defaultDrink;

public class JuiceBucketItem extends BucketItem implements IHasContainer {
    public static final Consumable JUICE_BUCKET_DRINK = defaultDrink()
            .onConsume(new RemoveStatusEffectsConsumeEffect(MobEffects.POISON))
            .build();

    public JuiceBucketItem(Identifier id, Supplier<? extends Fluid> supplier) {
        super(supplier.get(), new Properties()
                .setId(ResourceKey.create(Registries.ITEM, id))
                .stacksTo(16)
                .component(DataComponents.CONSUMABLE, JUICE_BUCKET_DRINK)
                .craftRemainder(Items.BUCKET));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        super.finishUsingItem(stack, level, entity);
        return returnContainerToEntity(stack, level, entity);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 32;
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack stack) {
        return ItemUseAnimation.DRINK;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        return ItemUtils.startUsingInstantly(level, player, hand);
    }

    @Override
    public Item getContainerItem() {
        return Items.BUCKET;
    }
}
