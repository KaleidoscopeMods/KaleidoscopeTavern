package com.github.ysbbbbbb.kaleidoscopetavern.init;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.datamap.data.DrinkEffectData;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;

public final class ModDatapackRegistries {
    public static final ResourceKey<Registry<DrinkEffectData>> DRINK_EFFECT = ResourceKey.createRegistryKey(
            KaleidoscopeTavern.modLoc("drink_effect")
    );

    public static void register(IEventBus modEventBus) {
        modEventBus.addListener(ModDatapackRegistries::registerDatapackRegistries);
    }

    private static void registerDatapackRegistries(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(
                DRINK_EFFECT,
                DrinkEffectData.DIRECT_CODEC,
                DrinkEffectData.DIRECT_CODEC
        );
    }
}
