package com.github.ysbbbbbb.kaleidoscopetavern.init;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.datamap.data.DrinkEffectData;
import com.github.ysbbbbbb.kaleidoscopetavern.item.ShakerItem;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.function.Supplier;

public interface ModDataComponents {
    DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES = DeferredRegister.create(BuiltInRegistries.DATA_COMPONENT_TYPE, KaleidoscopeTavern.MOD_ID);

    Supplier<DataComponentType<Integer>> BREW_LEVEL = DATA_COMPONENT_TYPES.register("brew_level", () ->
            DataComponentType.<Integer>builder()
                    .persistent(Codec.INT)
                    .networkSynchronized(ByteBufCodecs.VAR_INT)
                    .build());

    Supplier<DataComponentType<ShakerItem.Result>> SHAKER_RESULT = DATA_COMPONENT_TYPES.register("shaker_result", () ->
            DataComponentType.<ShakerItem.Result>builder()
                    .persistent(ShakerItem.Result.CODEC)
                    .networkSynchronized(ShakerItem.Result.STREAM_CODEC)
                    .build());

    Supplier<DataComponentType<List<DrinkEffectData.Entry>>> SIGNATURE_COCKTAIL_EFFECTS = DATA_COMPONENT_TYPES.register("signature_cocktail_effects", () ->
            DataComponentType.<List<DrinkEffectData.Entry>>builder()
                    .persistent(Codec.list(DrinkEffectData.Entry.ENTRY_CODEC))
                    .networkSynchronized(DrinkEffectData.Entry.STREAM_CODEC.apply(ByteBufCodecs.list()))
                    .build());

    Supplier<DataComponentType<Integer>> SIGNATURE_COCKTAIL_COLOR = DATA_COMPONENT_TYPES.register("signature_cocktail_color", () ->
            DataComponentType.<Integer>builder()
                    .persistent(Codec.INT)
                    .networkSynchronized(ByteBufCodecs.VAR_INT)
                    .build());
}
