package com.github.ysbbbbbb.kaleidoscopetavern.datagen.datamap;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.datamap.data.DrinkEffectData;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModEffects;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.hash.Hashing;
import com.google.common.hash.HashingOutputStream;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonWriter;
import com.mojang.serialization.JsonOps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.Util;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.ToIntFunction;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class DrinkEffectDataProvider implements DataProvider {
    private static final int[] SLIGHTLY_TIPSY_DURATIONS = {45, 30, 30, 20, 10};

    private static final ToIntFunction<String> ORDER_FIELDS = Util.make(new Object2IntOpenHashMap<>(), map -> {
        map.put("item", 0);
        map.defaultReturnValue(1);
    });

    private final Map<String, DrinkEffectData> data = Maps.newLinkedHashMap();
    private final PackOutput output;

    public DrinkEffectDataProvider(PackOutput output) {
        this.output = output;
    }

    private void addEntry() {
        // 葡萄酒
        add(ModItems.WINE,
                List.of(effect(MobEffects.REGENERATION, 80, 0)),
                List.of(effect(MobEffects.REGENERATION, 240, 0)),
                List.of(effect(MobEffects.REGENERATION, 240, 1)),
                List.of(effect(MobEffects.REGENERATION, 540, 1))
        );

        // 樱花葡萄酒
        add(ModItems.SAKURA_WINE,
                List.of(effect(MobEffects.REGENERATION, 80, 0)),
                List.of(effect(MobEffects.REGENERATION, 240, 0)),
                List.of(effect(MobEffects.REGENERATION, 240, 1)),
                List.of(effect(MobEffects.REGENERATION, 540, 1))
        );

        // 香槟
        add(ModItems.CHAMPAGNE,
                List.of(effect(ModEffects.HIGH_HEELS.get(), 80, 0)),
                List.of(effect(ModEffects.HIGH_HEELS.get(), 240, 0)),
                List.of(effect(ModEffects.HIGH_HEELS.get(), 720, 0)),
                List.of(effect(ModEffects.HIGH_HEELS.get(), 2160, 0))
        );

        // 白兰地
        add(ModItems.BRANDY,
                List.of(effect(ModEffects.HIGH_HEELS.get(), 80, 0)),
                List.of(effect(ModEffects.HIGH_HEELS.get(), 240, 0)),
                List.of(effect(ModEffects.HIGH_HEELS.get(), 720, 0)),
                List.of(effect(ModEffects.HIGH_HEELS.get(), 2160, 0))
        );

        // 佳丽私酿
        add(ModItems.CARIGNAN,
                List.of(effect(MobEffects.HEAL, 0, 0)),
                List.of(effect(MobEffects.HEAL, 0, 1)),
                List.of(effect(MobEffects.HEAL, 0, 2)),
                List.of(effect(MobEffects.HEAL, 0, 3))
        );

        // 冰葡萄酒
        add(ModItems.ICE_WINE,
                List.of(effect(MobEffects.FIRE_RESISTANCE, 80, 0)),
                List.of(effect(MobEffects.FIRE_RESISTANCE, 240, 0)),
                List.of(effect(MobEffects.FIRE_RESISTANCE, 720, 0)),
                List.of(effect(MobEffects.FIRE_RESISTANCE, 2160, 0))
        );

        // 北极星甜白
        add(ModItems.POLARIS_SWEET_WHITE,
                List.of(effect(MobEffects.FIRE_RESISTANCE, 80, 0)),
                List.of(effect(MobEffects.FIRE_RESISTANCE, 240, 0)),
                List.of(effect(MobEffects.FIRE_RESISTANCE, 720, 0)),
                List.of(effect(MobEffects.FIRE_RESISTANCE, 2160, 0))
        );

        // 雪婆婆
        add(ModItems.MOTHER_SNOW,
                List.of(effect(MobEffects.FIRE_RESISTANCE, 80, 0)),
                List.of(effect(MobEffects.FIRE_RESISTANCE, 240, 0)),
                List.of(effect(MobEffects.FIRE_RESISTANCE, 720, 0)),
                List.of(effect(MobEffects.FIRE_RESISTANCE, 2160, 0))
        );

        // 雪莉
        add(ModItems.SHERRY,
                List.of(effect(MobEffects.FIRE_RESISTANCE, 80, 0)),
                List.of(effect(MobEffects.FIRE_RESISTANCE, 240, 0)),
                List.of(effect(MobEffects.FIRE_RESISTANCE, 720, 0)),
                List.of(effect(MobEffects.FIRE_RESISTANCE, 2160, 0))
        );

        // 梅酒
        add(ModItems.PLUM_WINE,
                List.of(effect(MobEffects.WATER_BREATHING, 80, 0)),
                List.of(effect(MobEffects.WATER_BREATHING, 240, 0)),
                List.of(effect(MobEffects.WATER_BREATHING, 720, 0)),
                List.of(effect(MobEffects.WATER_BREATHING, 2160, 0))
        );

        // 甜浆果酒
        add(ModItems.SWEET_BERRY_WINE,
                List.of(effect(ModEffects.BLOODY_MARY.get(), 40, 0)),
                List.of(effect(ModEffects.BLOODY_MARY.get(), 120, 0)),
                List.of(effect(ModEffects.BLOODY_MARY.get(), 240, 0)),
                List.of(effect(ModEffects.BLOODY_MARY.get(), 480, 0))
        );

        // 红皇后
        add(ModItems.RED_QUEEN,
                List.of(effect(ModEffects.BLOODY_MARY.get(), 40, 0)),
                List.of(effect(ModEffects.BLOODY_MARY.get(), 120, 0)),
                List.of(effect(ModEffects.BLOODY_MARY.get(), 240, 0)),
                List.of(effect(ModEffects.BLOODY_MARY.get(), 480, 0))
        );

        // 伏特加
        add(ModItems.VODKA,
                List.of(effect(MobEffects.DAMAGE_BOOST, 80, 0)),
                List.of(effect(MobEffects.DAMAGE_BOOST, 240, 0)),
                List.of(effect(MobEffects.DAMAGE_BOOST, 240, 1)),
                List.of(effect(MobEffects.DAMAGE_BOOST, 540, 1))
        );

        // 威士忌
        add(ModItems.WHISKEY,
                List.of(effect(MobEffects.DAMAGE_BOOST, 80, 0)),
                List.of(effect(MobEffects.DAMAGE_BOOST, 240, 0)),
                List.of(effect(MobEffects.DAMAGE_BOOST, 240, 1)),
                List.of(effect(MobEffects.DAMAGE_BOOST, 540, 1))
        );

        // 朗姆酒
        addWithLevel2Effects(ModItems.RUM,
                List.of(effect(MobEffects.BAD_OMEN, 1200, 0)),
                List.of(effect(MobEffects.BAD_OMEN, 1200, 1)),
                List.of(effect(MobEffects.BAD_OMEN, 1200, 2)),
                List.of(effect(MobEffects.BAD_OMEN, 1200, 3)),
                List.of(effect(MobEffects.BAD_OMEN, 1200, 4))
        );

        // 矿工之星
        add(ModItems.MINERS_STAR,
                List.of(effect(MobEffects.DIG_SPEED, 80, 0)),
                List.of(effect(MobEffects.DIG_SPEED, 240, 0)),
                List.of(effect(MobEffects.DIG_SPEED, 240, 1)),
                List.of(effect(MobEffects.DIG_SPEED, 720, 1))
        );

        // 蜂蜜葡萄酒
        add(ModItems.HONEY_WINE,
                List.of(effect(MobEffects.DAMAGE_RESISTANCE, 80, 0)),
                List.of(effect(MobEffects.DAMAGE_RESISTANCE, 240, 0)),
                List.of(effect(MobEffects.DAMAGE_RESISTANCE, 240, 1)),
                List.of(effect(MobEffects.DAMAGE_RESISTANCE, 540, 1))
        );

        // 奢香夫人
        add(ModItems.MADAME_SHEXIANG,
                List.of(effect(MobEffects.DAMAGE_RESISTANCE, 80, 0)),
                List.of(effect(MobEffects.DAMAGE_RESISTANCE, 240, 0)),
                List.of(effect(MobEffects.DAMAGE_RESISTANCE, 240, 1)),
                List.of(effect(MobEffects.DAMAGE_RESISTANCE, 540, 1))
        );

        // 落日余晖
        add(ModItems.SUNSET_GLOW,
                List.of(effect(MobEffects.DAMAGE_RESISTANCE, 80, 0)),
                List.of(effect(MobEffects.DAMAGE_RESISTANCE, 240, 0)),
                List.of(effect(MobEffects.DAMAGE_RESISTANCE, 240, 1)),
                List.of(effect(MobEffects.DAMAGE_RESISTANCE, 540, 1))
        );

        // 长相思干白
        add(ModItems.SAUVIGNON_BLANC_DRY_WHITE,
                List.of(effect(ModEffects.GRASS_STEALTH.get(), 80, 0)),
                List.of(effect(ModEffects.GRASS_STEALTH.get(), 160, 0)),
                List.of(effect(ModEffects.GRASS_STEALTH.get(), 240, 0)),
                List.of(effect(ModEffects.GRASS_STEALTH.get(), 480, 0))
        );

        // 雷司令干白
        add(ModItems.RIESLING_DRY_WHITE,
                List.of(effect(ModEffects.GRASS_STEALTH.get(), 80, 0)),
                List.of(effect(ModEffects.GRASS_STEALTH.get(), 160, 0)),
                List.of(effect(ModEffects.GRASS_STEALTH.get(), 240, 0)),
                List.of(effect(ModEffects.GRASS_STEALTH.get(), 480, 0))
        );

        // 夜光新娘
        add(ModItems.LUMINOUS_BRIDE,
                List.of(effect(MobEffects.NIGHT_VISION, 80, 0)),
                List.of(effect(MobEffects.NIGHT_VISION, 240, 0)),
                List.of(effect(MobEffects.NIGHT_VISION, 720, 0)),
                List.of(effect(MobEffects.NIGHT_VISION, 2160, 0))
        );

        // 萤花酿
        add(ModItems.GLOWFLOWER_BREW,
                List.of(effect(ModEffects.VISION.get(), 80, 0)),
                List.of(effect(ModEffects.VISION.get(), 180, 1)),
                List.of(effect(ModEffects.VISION.get(), 360, 1)),
                List.of(effect(ModEffects.VISION.get(), 720, 2))
        );

        // === 鸡尾酒 ===

        // 血腥玛丽
        addCocktail(ModItems.BLOODY_MARY, effect(ModEffects.BLOODY_MARY.get(), 1800, 0));

        // 翡翠
        addCocktail(ModItems.EMERALD, effect(ModEffects.LONG_REACH.get(), 2700, 0));

        // 绿色蚱蜢
        addCocktail(ModItems.GRASSHOPPER, effect(ModEffects.GRASS_STEALTH.get(), 900, 0));

        // 绒球葱花园
        addCocktail(ModItems.ALLIUM_GARDEN, effect(ModEffects.XP_DRAIN.get(), 1800, 0));

        // 深水炸弹
        addCocktail(ModItems.DEPTH_CHARGE, effect(ModEffects.ARDENT_HEAT.get(), 300, 0));

        // 螺丝起子
        addCocktail(ModItems.SCREWDRIVER, effect(ModEffects.UPSIDE_DOWN.get(), 0, 0));

        // 教父
        addCocktail(ModItems.GODFATHER, effect(ModEffects.ZENITH.get(), 0, 0));

        // 白色佳人
        addCocktail(ModItems.WHITE_LADY, effect(ModEffects.HIGH_HEELS.get(), 3600, 0));

        // 莫吉托
        addCocktail(ModItems.MOJITO, effect(ModEffects.VISION.get(), 1800, 0));

        // 黄铜心脏
        addCocktail(ModItems.BRASS_HEART, effect(ModEffects.ARDENT_HEAT.get(), 300, 0));

        // 下界特调
        addCocktail(ModItems.NETHER_SPECIAL, effect(ModEffects.TOMB_RAIDER.get(), 90, 0));

        // 幽匿特调
        addCocktail(ModItems.SCULK_SPECIAL, effect(ModEffects.SHRIEK_ATTACK.get(), 0, 0));

        // 迷之鸡尾酒，固定 3 分钟微醺
        addCocktail(ModItems.MYSTERY_COCKTAIL, effect(ModEffects.SLIGHTLY_TIPSY.get(), 180, 0));

        // 醋（失败产物）
        add(ModItems.VINEGAR,
                List.of(
                        new DrinkEffectData.Entry(MobEffects.BLINDNESS, 10, 0, 0.15f),
                        new DrinkEffectData.Entry(MobEffects.DIG_SLOWDOWN, 10, 0, 0.15f),
                        new DrinkEffectData.Entry(MobEffects.MOVEMENT_SPEED, 10, 0, 0.15f),
                        new DrinkEffectData.Entry(MobEffects.JUMP, 10, 0, 0.15f),
                        new DrinkEffectData.Entry(MobEffects.DIG_SPEED, 10, 0, 0.15f)
                ),
                List.of(
                        new DrinkEffectData.Entry(MobEffects.BLINDNESS, 30, 1, 0.15f),
                        new DrinkEffectData.Entry(MobEffects.DIG_SLOWDOWN, 30, 1, 0.15f),
                        new DrinkEffectData.Entry(MobEffects.MOVEMENT_SPEED, 30, 1, 0.15f),
                        new DrinkEffectData.Entry(MobEffects.JUMP, 30, 1, 0.15f),
                        new DrinkEffectData.Entry(MobEffects.DIG_SPEED, 30, 1, 0.15f)
                ),
                List.of(
                        new DrinkEffectData.Entry(MobEffects.BLINDNESS, 60, 2, 0.15f),
                        new DrinkEffectData.Entry(MobEffects.DIG_SLOWDOWN, 60, 2, 0.15f),
                        new DrinkEffectData.Entry(MobEffects.MOVEMENT_SPEED, 60, 2, 0.15f),
                        new DrinkEffectData.Entry(MobEffects.JUMP, 60, 2, 0.15f),
                        new DrinkEffectData.Entry(MobEffects.DIG_SPEED, 60, 2, 0.15f)
                ),
                List.of(
                        new DrinkEffectData.Entry(MobEffects.BLINDNESS, 100, 2, 0.15f),
                        new DrinkEffectData.Entry(MobEffects.DIG_SLOWDOWN, 100, 2, 0.15f),
                        new DrinkEffectData.Entry(MobEffects.MOVEMENT_SPEED, 100, 2, 0.15f),
                        new DrinkEffectData.Entry(MobEffects.JUMP, 100, 2, 0.15f),
                        new DrinkEffectData.Entry(MobEffects.DIG_SPEED, 100, 2, 0.15f)
                )
        );
    }

    /**
     * 使用物品注册名的 path 部分作为文件名
     */
    @SafeVarargs
    public final void add(RegistryObject<Item> key, List<DrinkEffectData.Entry>... levelAbove2) {
        var itemKey = ForgeRegistries.ITEMS.getKey(key.get());
        if (itemKey == null) {
            throw new IllegalArgumentException("Item not registered: " + key.getId());
        }
        this.add(itemKey.getPath(), key, levelAbove2);
    }

    private DrinkEffectData.Entry effect(MobEffect effect, int duration, int amplifier) {
        return new DrinkEffectData.Entry(effect, duration, amplifier, 1f);
    }

    /**
     * 鸡尾酒没有酿造等级，只有单层效果
     */
    private void addCocktail(RegistryObject<Item> key, DrinkEffectData.Entry... entries) {
        var itemKey = ForgeRegistries.ITEMS.getKey(key.get());
        if (itemKey == null) {
            throw new IllegalArgumentException("Item not registered: " + key.getId());
        }
        this.add(itemKey.getPath(), new DrinkEffectData(key.get(), List.of(List.of(entries))));
    }

    /**
     * 使用自定义文件名
     */
    @SafeVarargs
    public final void add(String fileName, RegistryObject<Item> key, List<DrinkEffectData.Entry>... levelAbove2) {
        int length = levelAbove2.length;
        if (length == 0) {
            throw new IllegalArgumentException("At least one level above 2 must be provided");
        }
        this.addWithLevel2Effects(fileName, key,
                List.of(),
                levelAbove2[0],
                levelAbove2[Math.min(1, length - 1)],
                levelAbove2[Math.min(2, length - 1)],
                levelAbove2[Math.min(3, length - 1)]
        );
    }

    @SafeVarargs
    private void addWithLevel2Effects(RegistryObject<Item> key, List<DrinkEffectData.Entry>... levelAbove1) {
        var itemKey = ForgeRegistries.ITEMS.getKey(key.get());
        if (itemKey == null) {
            throw new IllegalArgumentException("Item not registered: " + key.getId());
        }
        this.addWithLevel2Effects(itemKey.getPath(), key, levelAbove1);
    }

    @SafeVarargs
    private void addWithLevel2Effects(String fileName, RegistryObject<Item> key,
                                      List<DrinkEffectData.Entry>... levelAbove1) {
        int length = levelAbove1.length;
        if (length == 0) {
            throw new IllegalArgumentException("At least one level above 1 must be provided");
        }

        var effects = Lists.<List<DrinkEffectData.Entry>>newArrayListWithExpectedSize(SLIGHTLY_TIPSY_DURATIONS.length + 1);
        // 等级 1，固定为反胃 30s
        effects.add(List.of(new DrinkEffectData.Entry(MobEffects.CONFUSION, 30, 0, 1f)));
        // 等级 2-6，除难以下咽外，都会额外附带对应时长的微醺效果
        for (int i = 0; i < SLIGHTLY_TIPSY_DURATIONS.length; i++) {
            effects.add(withSlightlyTipsy(SLIGHTLY_TIPSY_DURATIONS[i], levelAbove1[Math.min(i, length - 1)]));
        }

        this.add(fileName, new DrinkEffectData(key.get(), effects));
    }

    private List<DrinkEffectData.Entry> withSlightlyTipsy(int duration, List<DrinkEffectData.Entry> entries) {
        var result = Lists.<DrinkEffectData.Entry>newArrayListWithExpectedSize(entries.size() + 1);
        result.add(effect(ModEffects.SLIGHTLY_TIPSY.get(), duration, 0));
        result.addAll(entries);
        return result;
    }

    public void add(String fileName, DrinkEffectData value) {
        this.data.put(fileName, value);
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        this.addEntry();

        List<CompletableFuture<?>> futures = Lists.newArrayList();
        var pathProvider = this.output.createPathProvider(PackOutput.Target.DATA_PACK, "datamap/drink_effect");

        for (var entry : data.entrySet()) {
            DrinkEffectData.CODEC
                    .encodeStart(JsonOps.INSTANCE, entry.getValue())
                    .resultOrPartial(KaleidoscopeTavern.LOGGER::error)
                    .ifPresent(json -> {
                        var filePath = pathProvider.json(KaleidoscopeTavern.modLoc(entry.getKey()));
                        var future = this.saveStable(cache, json, filePath);
                        futures.add(future);
                    });
        }

        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }

    @SuppressWarnings("all")
    private CompletableFuture<?> saveStable(CachedOutput output, JsonElement json, Path path) {
        return CompletableFuture.runAsync(() -> {
            try {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                HashingOutputStream hashing = new HashingOutputStream(Hashing.sha1(), stream);

                try (JsonWriter writer = new JsonWriter(new OutputStreamWriter(hashing, StandardCharsets.UTF_8))) {
                    writer.setSerializeNulls(false);
                    writer.setIndent("  ");
                    GsonHelper.writeValue(writer, json, Comparator.comparingInt(ORDER_FIELDS));
                }

                output.writeIfNeeded(path, stream.toByteArray(), hashing.hash());
            } catch (IOException ioexception) {
                KaleidoscopeTavern.LOGGER.error("Failed to save file to {}", path, ioexception);
            }
        }, Util.backgroundExecutor());
    }

    @Override
    public String getName() {
        return "Drink Effect Data";
    }
}
