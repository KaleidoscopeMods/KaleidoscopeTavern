package com.github.ysbbbbbb.kaleidoscopetavern.item;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.datamap.data.DrinkEffectData;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SignatureCocktailBlockItem extends CocktailBlockItem {
    private static final Codec<List<DrinkEffectData.Entry>> EFFECTS_CODEC = Codec.list(DrinkEffectData.Entry.ENTRY_CODEC);
    private static final String EFFECTS_TAG = "Effects";
    private static final String COLOR_TAG = "Color";

    public SignatureCocktailBlockItem(Block block) {
        super(block);
    }

    public static boolean hasEffects(ItemStack stack) {
        return stack.getOrCreateTag().contains(EFFECTS_TAG);
    }

    public static List<DrinkEffectData.Entry> getEffects(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        if (tag.contains(EFFECTS_TAG)) {
            return EFFECTS_CODEC.decode(NbtOps.INSTANCE, tag.get(EFFECTS_TAG))
                    .resultOrPartial(KaleidoscopeTavern.LOGGER::error)
                    .map(Pair::getFirst)
                    .orElse(List.of());
        }
        return List.of();
    }

    public static void setEffects(ItemStack stack, List<DrinkEffectData.Entry> effects) {
        CompoundTag tag = stack.getOrCreateTag();
        EFFECTS_CODEC.encodeStart(NbtOps.INSTANCE, effects)
                .result()
                .ifPresent(r -> tag.put(EFFECTS_TAG, r));
    }

    public static int getColor(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        return tag.contains(COLOR_TAG) ? tag.getInt(COLOR_TAG) : 0xFFFFFF;
    }

    public static void setColor(ItemStack stack, int color) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putInt(COLOR_TAG, color);
    }

    @Override
    protected void addDrinkEffect(ItemStack drink, Level level, LivingEntity entity) {
        List<DrinkEffectData.Entry> effects = getEffects(drink);
        for (DrinkEffectData.Entry entry : effects) {
            if (!level.isClientSide && level.random.nextFloat() < entry.probability()) {
                MobEffect effect = entry.effect();
                int amplifier = entry.amplifier();
                if (effect.isInstantenous()) {
                    effect.applyInstantenousEffect(entity, entity, entity, amplifier, 1.0);
                } else {
                    int duration = entry.duration() * 20;
                    MobEffectInstance instance = new MobEffectInstance(effect, duration, amplifier);
                    entity.addEffect(instance);
                }
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        List<DrinkEffectData.Entry> effects = getEffects(stack);

        List<MobEffectInstance> effectsShow = Lists.newArrayList();
        for (DrinkEffectData.Entry entry : effects) {
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
}
