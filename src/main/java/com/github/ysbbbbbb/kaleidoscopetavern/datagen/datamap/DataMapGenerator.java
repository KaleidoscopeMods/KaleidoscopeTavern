package com.github.ysbbbbbb.kaleidoscopetavern.datagen.datamap;


import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.FurnaceFuel;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import java.util.concurrent.CompletableFuture;

public class DataMapGenerator extends DataMapProvider {
    private final Builder<FurnaceFuel, Item> furnaceFuels;

    public DataMapGenerator(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
        this.furnaceFuels = builder(NeoForgeDataMaps.FURNACE_FUELS);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        furnaceFuels.add(ModItems.GRAPEVINE, new FurnaceFuel(200), false);
    }
}
