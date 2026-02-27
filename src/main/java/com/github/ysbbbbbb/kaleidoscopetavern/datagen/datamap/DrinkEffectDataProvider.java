package com.github.ysbbbbbb.kaleidoscopetavern.datagen.datamap;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.datamap.data.DrinkEffectData;
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
        // 红葡萄酒
        add(ModItems.WINE,
                List.of(
                        new DrinkEffectData.Entry(MobEffects.HEAL, 0, 0, 1f),
                        new DrinkEffectData.Entry(MobEffects.HEALTH_BOOST, 10, 0, 1f)
                ),
                List.of(
                        new DrinkEffectData.Entry(MobEffects.HEAL, 0, 1, 1f),
                        new DrinkEffectData.Entry(MobEffects.HEALTH_BOOST, 20, 0, 1f)
                ),
                List.of(
                        new DrinkEffectData.Entry(MobEffects.HEAL, 0, 1, 1f),
                        new DrinkEffectData.Entry(MobEffects.HEALTH_BOOST, 40, 1, 1f)
                ),
                List.of(
                        new DrinkEffectData.Entry(MobEffects.HEAL, 0, 1, 1f),
                        new DrinkEffectData.Entry(MobEffects.HEALTH_BOOST, 80, 2, 1f)
                ),
                List.of(
                        new DrinkEffectData.Entry(MobEffects.HEAL, 0, 2, 1f),
                        new DrinkEffectData.Entry(MobEffects.HEALTH_BOOST, 160, 3, 1f)
                )
        );

        // 香槟
        add(ModItems.CHAMPAGNE,
                List.of(new DrinkEffectData.Entry(MobEffects.DAMAGE_RESISTANCE, 10, 0, 1f)),
                List.of(new DrinkEffectData.Entry(MobEffects.DAMAGE_RESISTANCE, 30, 1, 1f)),
                List.of(new DrinkEffectData.Entry(MobEffects.DAMAGE_RESISTANCE, 60, 2, 1f)),
                List.of(new DrinkEffectData.Entry(MobEffects.DAMAGE_RESISTANCE, 100, 3, 1f)),
                List.of(new DrinkEffectData.Entry(MobEffects.DAMAGE_RESISTANCE, 150, 5, 1f))
        );

        // 白兰地
        add(ModItems.BRANDY,
                List.of(new DrinkEffectData.Entry(MobEffects.DAMAGE_BOOST, 10, 0, 1f)),
                List.of(new DrinkEffectData.Entry(MobEffects.DAMAGE_BOOST, 30, 1, 1f)),
                List.of(new DrinkEffectData.Entry(MobEffects.DAMAGE_BOOST, 60, 2, 1f)),
                List.of(new DrinkEffectData.Entry(MobEffects.DAMAGE_BOOST, 100, 3, 1f)),
                List.of(new DrinkEffectData.Entry(MobEffects.DAMAGE_BOOST, 150, 5, 1f))
        );

        // 佳丽酿
        add(ModItems.CARIGNAN,
                List.of(new DrinkEffectData.Entry(MobEffects.MOVEMENT_SPEED, 10, 0, 1f)),
                List.of(new DrinkEffectData.Entry(MobEffects.MOVEMENT_SPEED, 30, 1, 1f)),
                List.of(new DrinkEffectData.Entry(MobEffects.MOVEMENT_SPEED, 60, 2, 1f)),
                List.of(
                        new DrinkEffectData.Entry(MobEffects.MOVEMENT_SPEED, 100, 3, 1f),
                        new DrinkEffectData.Entry(MobEffects.JUMP, 100, 0, 1f)
                ),
                List.of(
                        new DrinkEffectData.Entry(MobEffects.MOVEMENT_SPEED, 150, 5, 1f),
                        new DrinkEffectData.Entry(MobEffects.JUMP, 150, 1, 1f)
                )
        );

        // 樱花葡萄酒
        add(ModItems.SAKURA_WINE,
                List.of(new DrinkEffectData.Entry(MobEffects.NIGHT_VISION, 10, 0, 1f)),
                List.of(new DrinkEffectData.Entry(MobEffects.NIGHT_VISION, 40, 0, 1f)),
                List.of(new DrinkEffectData.Entry(MobEffects.NIGHT_VISION, 160, 0, 1f)),
                List.of(new DrinkEffectData.Entry(MobEffects.NIGHT_VISION, 640, 0, 1f)),
                List.of(new DrinkEffectData.Entry(MobEffects.NIGHT_VISION, 999999, 0, 1f))
        );

        // 冰葡萄酒
        add(ModItems.ICE_WINE,
                List.of(new DrinkEffectData.Entry(MobEffects.FIRE_RESISTANCE, 10, 0, 1f)),
                List.of(new DrinkEffectData.Entry(MobEffects.FIRE_RESISTANCE, 40, 0, 1f)),
                List.of(new DrinkEffectData.Entry(MobEffects.FIRE_RESISTANCE, 160, 0, 1f)),
                List.of(new DrinkEffectData.Entry(MobEffects.FIRE_RESISTANCE, 640, 0, 1f)),
                List.of(new DrinkEffectData.Entry(MobEffects.FIRE_RESISTANCE, 999999, 0, 1f))
        );

        // 梅酒
        add(ModItems.PLUM_WINE,
                List.of(new DrinkEffectData.Entry(MobEffects.WATER_BREATHING, 10, 0, 1f)),
                List.of(new DrinkEffectData.Entry(MobEffects.WATER_BREATHING, 40, 0, 1f)),
                List.of(new DrinkEffectData.Entry(MobEffects.WATER_BREATHING, 160, 0, 1f)),
                List.of(new DrinkEffectData.Entry(MobEffects.WATER_BREATHING, 640, 0, 1f)),
                List.of(new DrinkEffectData.Entry(MobEffects.WATER_BREATHING, 999999, 0, 1f))
        );

        // 伏特加
        add(ModItems.VODKA,
                List.of(new DrinkEffectData.Entry(MobEffects.DIG_SPEED, 10, 0, 1f)),
                List.of(new DrinkEffectData.Entry(MobEffects.DIG_SPEED, 30, 1, 1f)),
                List.of(new DrinkEffectData.Entry(MobEffects.DIG_SPEED, 90, 2, 1f)),
                List.of(new DrinkEffectData.Entry(MobEffects.DIG_SPEED, 270, 3, 1f)),
                List.of(new DrinkEffectData.Entry(MobEffects.DIG_SPEED, 480, 5, 1f))
        );

        // 威士忌
        add(ModItems.WHISKEY,
                List.of(new DrinkEffectData.Entry(MobEffects.DIG_SPEED, 10, 0, 1f)),
                List.of(new DrinkEffectData.Entry(MobEffects.DIG_SPEED, 30, 1, 1f)),
                List.of(new DrinkEffectData.Entry(MobEffects.DIG_SPEED, 90, 2, 1f)),
                List.of(new DrinkEffectData.Entry(MobEffects.DIG_SPEED, 270, 3, 1f)),
                List.of(new DrinkEffectData.Entry(MobEffects.DIG_SPEED, 480, 5, 1f))
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
                ),
                List.of(
                        new DrinkEffectData.Entry(MobEffects.BLINDNESS, 150, 3, 0.15f),
                        new DrinkEffectData.Entry(MobEffects.DIG_SLOWDOWN, 150, 3, 0.15f),
                        new DrinkEffectData.Entry(MobEffects.MOVEMENT_SPEED, 150, 3, 0.15f),
                        new DrinkEffectData.Entry(MobEffects.JUMP, 150, 3, 0.15f),
                        new DrinkEffectData.Entry(MobEffects.DIG_SPEED, 150, 3, 0.15f)
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
                // 等级 1，固定为恶心 35s
                List.of(new DrinkEffectData.Entry(MobEffects.CONFUSION, 35, 0, 1f)),
                // 等级 2，固定为恶心 10s
                List.of(new DrinkEffectData.Entry(MobEffects.CONFUSION, 10, 0, 1f)),
                // 等级 3-7，由外部传入
                levelAbove2[0],
                levelAbove2[Math.min(1, length - 1)],
                levelAbove2[Math.min(2, length - 1)],
                levelAbove2[Math.min(3, length - 1)],
                levelAbove2[Math.min(4, length - 1)]
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
                LOGGER.error("Failed to save file to {}", path, ioexception);
            }
        }, Util.backgroundExecutor());
    }

    @Override
    public String getName() {
        return "Drink Effect Data";
    }
}
