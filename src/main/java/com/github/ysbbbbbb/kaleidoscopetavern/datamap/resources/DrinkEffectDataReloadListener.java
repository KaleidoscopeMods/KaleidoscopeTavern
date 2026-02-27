package com.github.ysbbbbbb.kaleidoscopetavern.datamap.resources;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.datamap.data.DrinkEffectData;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.Item;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Map;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class DrinkEffectDataReloadListener extends SimpleJsonResourceReloadListener {
    public static final Map<Item, DrinkEffectData> INSTANCE = Maps.newHashMap();
    private static final Gson GSON = new GsonBuilder().create();

    public DrinkEffectDataReloadListener() {
        super(GSON, "datamap/drink_effect");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> resources, ResourceManager resourceManager, ProfilerFiller profiler) {
        INSTANCE.clear();
        for (var entry : resources.entrySet()) {
            var result = DrinkEffectData.CODEC.parse(JsonOps.INSTANCE, entry.getValue());
            if (result.result().isPresent()) {
                DrinkEffectData data = result.result().get();
                INSTANCE.put(data.item(), data);
            } else if (result.error().isPresent()) {
                KaleidoscopeTavern.LOGGER.error("Failed to parse drink effect data from '{}': {}", entry.getKey(), result.error().get().message());
            }
        }
        KaleidoscopeTavern.LOGGER.info("Successfully loaded drink effect data with {} entries", INSTANCE.size());
    }
}
