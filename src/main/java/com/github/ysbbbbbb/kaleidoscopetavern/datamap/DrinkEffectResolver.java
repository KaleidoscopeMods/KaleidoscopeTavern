package com.github.ysbbbbbb.kaleidoscopetavern.datamap;

import com.github.ysbbbbbb.kaleidoscopetavern.api.blockentity.IBarrel;
import com.github.ysbbbbbb.kaleidoscopetavern.datamap.data.DrinkEffectData;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModDatapackRegistries;
import net.minecraft.core.HolderLookup;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class DrinkEffectResolver {
    public static Optional<DrinkEffectData> get(HolderLookup.Provider registries, ItemStack stack) {
        return get(registries, new ItemStackTemplate(stack.getItem()));
    }

    public static Optional<DrinkEffectData> get(HolderLookup.Provider registries, Item item) {
        return get(registries, new ItemStackTemplate(item));
    }

    public static Optional<DrinkEffectData> get(HolderLookup.Provider registries, ItemStackTemplate template) {
        return registries.lookup(ModDatapackRegistries.DRINK_EFFECT).flatMap(lookup -> {
            var iterator = lookup.listElements().iterator();
            while (iterator.hasNext()) {
                DrinkEffectData data = iterator.next().value();
                if (data.item().equals(template)) {
                    return Optional.of(data);
                }
            }
            return Optional.empty();
        });
    }

    public static List<DrinkEffectData.Entry> entriesFor(HolderLookup.Provider registries, ItemStack stack, int brewLevel) {
        return get(registries, stack)
                .map(data -> data.entriesForLevel(clampActiveBrewLevel(brewLevel)))
                .orElse(List.of());
    }

    public static List<DrinkEffectData.Entry> entriesFor(HolderLookup.Provider registries, Item item, int brewLevel) {
        return get(registries, item)
                .map(data -> data.entriesForLevel(clampActiveBrewLevel(brewLevel)))
                .orElse(List.of());
    }

    public static List<MobEffectInstance> rollInstances(List<DrinkEffectData.Entry> entries, RandomSource random) {
        List<MobEffectInstance> instances = new ArrayList<>();
        for (DrinkEffectData.Entry entry : entries) {
            if (random.nextFloat() < entry.probability()) {
                instances.add(toInstance(entry));
            }
        }
        return instances;
    }

    public static List<MobEffectInstance> guaranteedInstances(List<DrinkEffectData.Entry> entries) {
        List<MobEffectInstance> instances = new ArrayList<>();
        for (DrinkEffectData.Entry entry : entries) {
            if (entry.probability() >= 1.0F) {
                instances.add(toInstance(entry));
            }
        }
        return instances;
    }

    private static int clampActiveBrewLevel(int brewLevel) {
        if (brewLevel < IBarrel.BREWING_STARTED) {
            return IBarrel.BREWING_NOT_STARTED;
        }
        return Math.min(brewLevel, IBarrel.BREWING_FINISHED);
    }

    private static MobEffectInstance toInstance(DrinkEffectData.Entry entry) {
        return new MobEffectInstance(entry.effect(), entry.durationTicks(), entry.amplifier());
    }
}
