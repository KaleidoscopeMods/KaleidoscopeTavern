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
                List.of(effect(ModEffects.BLOODY_MARY.get(), 0, 0)),
                List.of(effect(ModEffects.BLOODY_MARY.get(), 120, 0)),
                List.of(effect(ModEffects.BLOODY_MARY.get(), 0, 0)),
                List.of(effect(ModEffects.BLOODY_MARY.get(), 480, 0))
        );

        // 红皇后
        add(ModItems.RED_QUEEN,
                List.of(effect(ModEffects.BLOODY_MARY.get(), 0, 0)),
                List.of(effect(ModEffects.BLOODY_MARY.get(), 120, 0)),
                List.of(effect(ModEffects.BLOODY_MARY.get(), 0, 0)),
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
        add(ModItems.RUM,
                List.of(effect(MobEffects.BAD_OMEN, 80, 0)),
                List.of(effect(MobEffects.BAD_OMEN, 240, 0)),
                List.of(effect(MobEffects.BAD_OMEN, 240, 1)),
                List.of(effect(MobEffects.BAD_OMEN, 540, 1))
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

        // 醋（失败产物）- 效果带有概率
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
     * 使用自定义文件名
     */
    @SafeVarargs
    public final void add(String fileName, RegistryObject<Item> key, List<DrinkEffectData.Entry>... levelAbove2) {
        int length = levelAbove2.length;
        if (length == 0) {
            throw new IllegalArgumentException("At least one level above 2 must be provided");
        }
        this.add(fileName, new DrinkEffectData(key.get(), List.of(
                // 等级 1，固定为反胃 30s
                List.of(new DrinkEffectData.Entry(MobEffects.CONFUSION, 30, 0, 1f)),
                // 等级 2，固定为微醺 45s
                List.of(new DrinkEffectData.Entry(ModEffects.SLIGHTLY_TIPSY.get(), 45, 0, 1f)),
                // 等级 3-6，由外部传入
                levelAbove2[0],
                levelAbove2[Math.min(1, length - 1)],
                levelAbove2[Math.min(2, length - 1)],
                levelAbove2[Math.min(3, length - 1)]
        )));
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
