package com.github.ysbbbbbb.kaleidoscopetavern.datagen.datamap;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.datamap.data.DrinkEffectData;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModDatapackRegistries;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModEffects;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@NullMarked
public final class DrinkEffectDataProvider {
    private static final int[] SLIGHTLY_TIPSY_DURATIONS = {45, 30, 30, 20, 10};

    public static void bootstrap(BootstrapContext<DrinkEffectData> context) {
        // 葡萄酒
        add(context, ModItems.WINE,
                List.of(effect(MobEffects.REGENERATION, 80, 0)),
                List.of(effect(MobEffects.REGENERATION, 240, 0)),
                List.of(effect(MobEffects.REGENERATION, 240, 1)),
                List.of(effect(MobEffects.REGENERATION, 540, 1))
        );

        // 樱花葡萄酒
        add(context, ModItems.SAKURA_WINE,
                List.of(effect(MobEffects.REGENERATION, 80, 0)),
                List.of(effect(MobEffects.REGENERATION, 240, 0)),
                List.of(effect(MobEffects.REGENERATION, 240, 1)),
                List.of(effect(MobEffects.REGENERATION, 540, 1))
        );

        // 香槟
        add(context, ModItems.CHAMPAGNE,
                List.of(effect(ModEffects.HIGH_HEELS, 80, 0)),
                List.of(effect(ModEffects.HIGH_HEELS, 240, 0)),
                List.of(effect(ModEffects.HIGH_HEELS, 720, 0)),
                List.of(effect(ModEffects.HIGH_HEELS, 2160, 0))
        );

        // 白兰地
        add(context, ModItems.BRANDY,
                List.of(effect(ModEffects.HIGH_HEELS, 80, 0)),
                List.of(effect(ModEffects.HIGH_HEELS, 240, 0)),
                List.of(effect(ModEffects.HIGH_HEELS, 720, 0)),
                List.of(effect(ModEffects.HIGH_HEELS, 2160, 0))
        );

        // 佳丽私酿
        add(context, ModItems.CARIGNAN,
                List.of(effect(MobEffects.INSTANT_HEALTH, 0, 0)),
                List.of(effect(MobEffects.INSTANT_HEALTH, 0, 1)),
                List.of(effect(MobEffects.INSTANT_HEALTH, 0, 2)),
                List.of(effect(MobEffects.INSTANT_HEALTH, 0, 3))
        );

        // 冰葡萄酒
        add(context, ModItems.ICE_WINE,
                List.of(effect(MobEffects.FIRE_RESISTANCE, 80, 0)),
                List.of(effect(MobEffects.FIRE_RESISTANCE, 240, 0)),
                List.of(effect(MobEffects.FIRE_RESISTANCE, 720, 0)),
                List.of(effect(MobEffects.FIRE_RESISTANCE, 2160, 0))
        );

        // 北极星甜白
        add(context, ModItems.POLARIS_SWEET_WHITE,
                List.of(effect(MobEffects.FIRE_RESISTANCE, 80, 0)),
                List.of(effect(MobEffects.FIRE_RESISTANCE, 240, 0)),
                List.of(effect(MobEffects.FIRE_RESISTANCE, 720, 0)),
                List.of(effect(MobEffects.FIRE_RESISTANCE, 2160, 0))
        );

        // 雪婆婆
        add(context, ModItems.MOTHER_SNOW,
                List.of(effect(MobEffects.FIRE_RESISTANCE, 80, 0)),
                List.of(effect(MobEffects.FIRE_RESISTANCE, 240, 0)),
                List.of(effect(MobEffects.FIRE_RESISTANCE, 720, 0)),
                List.of(effect(MobEffects.FIRE_RESISTANCE, 2160, 0))
        );

        // 雪莉
        add(context, ModItems.SHERRY,
                List.of(effect(MobEffects.FIRE_RESISTANCE, 80, 0)),
                List.of(effect(MobEffects.FIRE_RESISTANCE, 240, 0)),
                List.of(effect(MobEffects.FIRE_RESISTANCE, 720, 0)),
                List.of(effect(MobEffects.FIRE_RESISTANCE, 2160, 0))
        );

        // 梅酒
        add(context, ModItems.PLUM_WINE,
                List.of(effect(MobEffects.WATER_BREATHING, 80, 0)),
                List.of(effect(MobEffects.WATER_BREATHING, 240, 0)),
                List.of(effect(MobEffects.WATER_BREATHING, 720, 0)),
                List.of(effect(MobEffects.WATER_BREATHING, 2160, 0))
        );

        // 甜浆果酒
        add(context, ModItems.SWEET_BERRY_WINE,
                List.of(effect(ModEffects.BLOODY_MARY, 40, 0)),
                List.of(effect(ModEffects.BLOODY_MARY, 120, 0)),
                List.of(effect(ModEffects.BLOODY_MARY, 240, 0)),
                List.of(effect(ModEffects.BLOODY_MARY, 480, 0))
        );

        // 红皇后
        add(context, ModItems.RED_QUEEN,
                List.of(effect(ModEffects.BLOODY_MARY, 40, 0)),
                List.of(effect(ModEffects.BLOODY_MARY, 120, 0)),
                List.of(effect(ModEffects.BLOODY_MARY, 240, 0)),
                List.of(effect(ModEffects.BLOODY_MARY, 480, 0))
        );

        // 伏特加
        add(context, ModItems.VODKA,
                List.of(effect(MobEffects.STRENGTH, 80, 0)),
                List.of(effect(MobEffects.STRENGTH, 240, 0)),
                List.of(effect(MobEffects.STRENGTH, 240, 1)),
                List.of(effect(MobEffects.STRENGTH, 540, 1))
        );

        // 威士忌
        add(context, ModItems.WHISKEY,
                List.of(effect(MobEffects.STRENGTH, 80, 0)),
                List.of(effect(MobEffects.STRENGTH, 240, 0)),
                List.of(effect(MobEffects.STRENGTH, 240, 1)),
                List.of(effect(MobEffects.STRENGTH, 540, 1))
        );

        // 朗姆酒
        addWithLevel2Effects(context, ModItems.RUM,
                List.of(effect(MobEffects.BAD_OMEN, 1200, 0)),
                List.of(effect(MobEffects.BAD_OMEN, 1200, 1)),
                List.of(effect(MobEffects.BAD_OMEN, 1200, 2)),
                List.of(effect(MobEffects.BAD_OMEN, 1200, 3)),
                List.of(effect(MobEffects.BAD_OMEN, 1200, 4))
        );

        // 矿工之星
        add(context, ModItems.MINERS_STAR,
                List.of(effect(MobEffects.HASTE, 80, 0)),
                List.of(effect(MobEffects.HASTE, 240, 0)),
                List.of(effect(MobEffects.HASTE, 240, 1)),
                List.of(effect(MobEffects.HASTE, 720, 1))
        );

        // 蜂蜜葡萄酒
        add(context, ModItems.HONEY_WINE,
                List.of(effect(MobEffects.RESISTANCE, 80, 0)),
                List.of(effect(MobEffects.RESISTANCE, 240, 0)),
                List.of(effect(MobEffects.RESISTANCE, 240, 1)),
                List.of(effect(MobEffects.RESISTANCE, 540, 1))
        );

        // 奢香夫人
        add(context, ModItems.MADAME_SHEXIANG,
                List.of(effect(MobEffects.RESISTANCE, 80, 0)),
                List.of(effect(MobEffects.RESISTANCE, 240, 0)),
                List.of(effect(MobEffects.RESISTANCE, 240, 1)),
                List.of(effect(MobEffects.RESISTANCE, 540, 1))
        );

        // 落日余晖
        add(context, ModItems.SUNSET_GLOW,
                List.of(effect(MobEffects.RESISTANCE, 80, 0)),
                List.of(effect(MobEffects.RESISTANCE, 240, 0)),
                List.of(effect(MobEffects.RESISTANCE, 240, 1)),
                List.of(effect(MobEffects.RESISTANCE, 540, 1))
        );

        // 长相思干白
        add(context, ModItems.SAUVIGNON_BLANC_DRY_WHITE,
                List.of(effect(ModEffects.GRASS_STEALTH, 80, 0)),
                List.of(effect(ModEffects.GRASS_STEALTH, 160, 0)),
                List.of(effect(ModEffects.GRASS_STEALTH, 240, 0)),
                List.of(effect(ModEffects.GRASS_STEALTH, 480, 0))
        );

        // 雷司令干白
        add(context, ModItems.RIESLING_DRY_WHITE,
                List.of(effect(ModEffects.GRASS_STEALTH, 80, 0)),
                List.of(effect(ModEffects.GRASS_STEALTH, 160, 0)),
                List.of(effect(ModEffects.GRASS_STEALTH, 240, 0)),
                List.of(effect(ModEffects.GRASS_STEALTH, 480, 0))
        );

        // 夜光新娘
        add(context, ModItems.LUMINOUS_BRIDE,
                List.of(effect(MobEffects.NIGHT_VISION, 80, 0)),
                List.of(effect(MobEffects.NIGHT_VISION, 240, 0)),
                List.of(effect(MobEffects.NIGHT_VISION, 720, 0)),
                List.of(effect(MobEffects.NIGHT_VISION, 2160, 0))
        );

        // 萤花酿
        add(context, ModItems.GLOWFLOWER_BREW,
                List.of(effect(ModEffects.VISION, 80, 0)),
                List.of(effect(ModEffects.VISION, 180, 1)),
                List.of(effect(ModEffects.VISION, 360, 1)),
                List.of(effect(ModEffects.VISION, 720, 2))
        );

        // 醋（失败产物）
        add(context, ModItems.VINEGAR,
                List.of(
                        new DrinkEffectData.Entry(MobEffects.BLINDNESS, 10, 0, 0.15f),
                        new DrinkEffectData.Entry(MobEffects.MINING_FATIGUE, 10, 0, 0.15f),
                        new DrinkEffectData.Entry(MobEffects.SPEED, 10, 0, 0.15f),
                        new DrinkEffectData.Entry(MobEffects.JUMP_BOOST, 10, 0, 0.15f),
                        new DrinkEffectData.Entry(MobEffects.HASTE, 10, 0, 0.15f)
                ),
                List.of(
                        new DrinkEffectData.Entry(MobEffects.BLINDNESS, 30, 1, 0.15f),
                        new DrinkEffectData.Entry(MobEffects.MINING_FATIGUE, 30, 1, 0.15f),
                        new DrinkEffectData.Entry(MobEffects.SPEED, 30, 1, 0.15f),
                        new DrinkEffectData.Entry(MobEffects.JUMP_BOOST, 30, 1, 0.15f),
                        new DrinkEffectData.Entry(MobEffects.HASTE, 30, 1, 0.15f)
                ),
                List.of(
                        new DrinkEffectData.Entry(MobEffects.BLINDNESS, 60, 2, 0.15f),
                        new DrinkEffectData.Entry(MobEffects.MINING_FATIGUE, 60, 2, 0.15f),
                        new DrinkEffectData.Entry(MobEffects.SPEED, 60, 2, 0.15f),
                        new DrinkEffectData.Entry(MobEffects.JUMP_BOOST, 60, 2, 0.15f),
                        new DrinkEffectData.Entry(MobEffects.HASTE, 60, 2, 0.15f)
                ),
                List.of(
                        new DrinkEffectData.Entry(MobEffects.BLINDNESS, 100, 2, 0.15f),
                        new DrinkEffectData.Entry(MobEffects.MINING_FATIGUE, 100, 2, 0.15f),
                        new DrinkEffectData.Entry(MobEffects.SPEED, 100, 2, 0.15f),
                        new DrinkEffectData.Entry(MobEffects.JUMP_BOOST, 100, 2, 0.15f),
                        new DrinkEffectData.Entry(MobEffects.HASTE, 100, 2, 0.15f)
                )
        );
    }

    @SafeVarargs
    private static void add(
            BootstrapContext<DrinkEffectData> context,
            DeferredItem<Item> key,
            List<DrinkEffectData.Entry>... levelAbove2
    ) {
        var itemKey = BuiltInRegistries.ITEM.getKey(key.value());
        add(context, itemKey.getPath(), key, levelAbove2);
    }

    @SafeVarargs
    private static void add(
            BootstrapContext<DrinkEffectData> context,
            String fileName,
            DeferredItem<Item> key,
            List<DrinkEffectData.Entry>... levelAbove2
    ) {
        int length = levelAbove2.length;
        if (length == 0) {
            throw new IllegalArgumentException("At least one level above 2 must be provided");
        }
        addWithLevel2Effects(context, fileName, key,
                List.of(),
                levelAbove2[0],
                levelAbove2[Math.min(1, length - 1)],
                levelAbove2[Math.min(2, length - 1)],
                levelAbove2[Math.min(3, length - 1)]
        );
    }

    @SafeVarargs
    private static void addWithLevel2Effects(
            BootstrapContext<DrinkEffectData> context,
            DeferredItem<Item> key,
            List<DrinkEffectData.Entry>... levelAbove1
    ) {
        var itemKey = BuiltInRegistries.ITEM.getKey(key.get());
        addWithLevel2Effects(context, itemKey.getPath(), key, levelAbove1);
    }

    @SafeVarargs
    private static void addWithLevel2Effects(
            BootstrapContext<DrinkEffectData> context,
            String fileName,
            DeferredItem<Item> key,
            List<DrinkEffectData.Entry>... levelAbove1
    ) {
        int length = levelAbove1.length;
        if (length == 0) {
            throw new IllegalArgumentException("At least one level above 1 must be provided");
        }

        Map<Integer, List<DrinkEffectData.Entry>> effects = new LinkedHashMap<>();

        // 等级 1，固定为反胃 30s
        effects.put(1, List.of(effect(MobEffects.NAUSEA, 30, 0)));

        // 等级 2-6，除难以下咽外，都会额外附带对应时长的微醺效果
        for (int i = 0; i < SLIGHTLY_TIPSY_DURATIONS.length; i++) {
            List<DrinkEffectData.Entry> entries = levelAbove1[Math.min(i, length - 1)];
            entries = withSlightlyTipsy(SLIGHTLY_TIPSY_DURATIONS[i], entries);
            effects.put(i + 2, entries);
        }

        ItemStackTemplate template = new ItemStackTemplate(key.get());
        register(context, fileName, new DrinkEffectData(template, effects));
    }

    private static List<DrinkEffectData.Entry> withSlightlyTipsy(int duration, List<DrinkEffectData.Entry> entries) {
        List<DrinkEffectData.Entry> result = new ArrayList<>(entries.size() + 1);
        DrinkEffectData.Entry effect = effect(ModEffects.SLIGHTLY_TIPSY, duration, 0);
        result.add(effect);
        result.addAll(entries);
        return List.copyOf(result);
    }

    private static DrinkEffectData.Entry effect(Holder<MobEffect> effect, int duration, int amplifier) {
        return new DrinkEffectData.Entry(effect, duration, amplifier, 1f);
    }

    private static void register(BootstrapContext<DrinkEffectData> context, String fileName, DrinkEffectData value) {
        Identifier id = KaleidoscopeTavern.modLoc(fileName);
        var key = ResourceKey.create(ModDatapackRegistries.DRINK_EFFECT, id);
        context.register(key, value);
    }
}
