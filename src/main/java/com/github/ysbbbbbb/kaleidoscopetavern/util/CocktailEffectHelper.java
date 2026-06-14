package com.github.ysbbbbbb.kaleidoscopetavern.util;

import com.github.ysbbbbbb.kaleidoscopetavern.datamap.data.DrinkEffectData;
import com.github.ysbbbbbb.kaleidoscopetavern.datamap.resources.DrinkEffectDataReloadListener;
import com.github.ysbbbbbb.kaleidoscopetavern.item.BottleBlockItem;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.ChatFormatting;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.items.ItemStackHandler;

import java.util.List;
import java.util.Map;

/**
 * 鸡尾酒效果合成工具类。
 * 负责从雪克杯原料中收集效果/颜色，并合并同类效果。
 */
public class CocktailEffectHelper {
    /**
     * 相同效果叠加时的时长加成系数。
     * 当多个原料提供相同 MobEffect 时，合并后总时长乘以此系数作为奖励。
     */
    public static final float EFFECT_MERGE_MULTIPLIER = 1.2f;

    /**
     * 从 storage 中收集的原料数据（效果列表 + 颜色列表）。
     */
    public record CollectedData(List<DrinkEffectData.Entry> effects, List<ChatFormatting> colors) {
    }

    /**
     * 遍历雪克杯 storage 中的所有原料，提取效果和颜色。
     * <p>
     * 药水特判：视为白色，效果直接从药水数据提取。
     * 普通原料：根据 brewLevel 从 DrinkEffectDataReloadListener 获取对应等级效果。
     */
    public static CollectedData collectFromStorage(ItemStackHandler storage) {
        Map<Item, DrinkEffectData> dataMap = DrinkEffectDataReloadListener.INSTANCE;
        List<DrinkEffectData.Entry> effects = Lists.newArrayList();
        List<ChatFormatting> colors = Lists.newArrayList();

        for (int i = 0; i < storage.getSlots(); i++) {
            ItemStack ingredient = storage.getStackInSlot(i);
            if (ingredient.isEmpty()) {
                continue;
            }

            // 药水特判：颜色固定白色，效果从药水数据提取
            if (ingredient.getItem() instanceof PotionItem) {
                colors.add(ChatFormatting.WHITE);

                List<MobEffectInstance> potionEffects = PotionUtils.getMobEffects(ingredient);
                for (MobEffectInstance e : potionEffects) {
                    // 药水时长是 tick，DrinkEffectData.Entry 使用秒
                    int durationSeconds = e.getDuration() / 20;
                    effects.add(new DrinkEffectData.Entry(
                            e.getEffect(), durationSeconds,
                            e.getAmplifier(), 1.0F
                    ));
                }
                continue;
            }

            // 普通原料：通过 Tag 获取颜色
            ChatFormatting color = ColorUtils.ITEM_COLOR_CACHE.apply(ingredient.getItem());
            if (color != ChatFormatting.RESET) {
                colors.add(color);
            }

            // 根据 brewLevel 获取对应等级的效果
            int brewLevel = BottleBlockItem.getBrewLevel(ingredient);
            if (brewLevel > 0 && dataMap.containsKey(ingredient.getItem())) {
                DrinkEffectData data = dataMap.get(ingredient.getItem());
                List<List<DrinkEffectData.Entry>> effectsList = data.effects();
                brewLevel = Math.min(brewLevel, effectsList.size());
                effects.addAll(effectsList.get(brewLevel - 1));
            }
        }

        return new CollectedData(effects, colors);
    }

    /**
     * 合并同类效果：相同 MobEffect 的时长叠加，然后乘以 {@link #EFFECT_MERGE_MULTIPLIER}。
     * amplifier 和 probability 取各条目中的最大值。
     */
    public static List<DrinkEffectData.Entry> mergeEffects(List<DrinkEffectData.Entry> effects) {
        // 按 MobEffect 分组，保持插入顺序
        Map<MobEffect, List<DrinkEffectData.Entry>> grouped = Maps.newLinkedHashMap();
        for (DrinkEffectData.Entry entry : effects) {
            grouped.computeIfAbsent(entry.effect(), k -> Lists.newArrayList())
                    .add(entry);
        }

        List<DrinkEffectData.Entry> merged = Lists.newArrayList();
        for (Map.Entry<MobEffect, List<DrinkEffectData.Entry>> e : grouped.entrySet()) {
            int totalDuration = 0;
            int maxAmplifier = 0;
            float maxProbability = 0;

            for (DrinkEffectData.Entry entry : e.getValue()) {
                totalDuration += entry.duration();
                maxAmplifier = Math.max(maxAmplifier, entry.amplifier());
                maxProbability = Math.max(maxProbability, entry.probability());
            }

            // 同类效果叠加时长奖励
            totalDuration = (int) (totalDuration * EFFECT_MERGE_MULTIPLIER);
            merged.add(new DrinkEffectData.Entry(
                    e.getKey(), totalDuration,
                    maxAmplifier, maxProbability
            ));
        }

        return merged;
    }
}
