package com.github.ysbbbbbb.kaleidoscopetavern.datagen;

import com.github.ysbbbbbb.kaleidoscopetavern.datagen.datamap.DataMapGenerator;
import com.github.ysbbbbbb.kaleidoscopetavern.datagen.datamap.DrinkEffectDataProvider;
import com.github.ysbbbbbb.kaleidoscopetavern.datagen.loottable.LootTableGenerator;
import com.github.ysbbbbbb.kaleidoscopetavern.datagen.model.BlockModelGenerator;
import com.github.ysbbbbbb.kaleidoscopetavern.datagen.model.BlockStateGenerator;
import com.github.ysbbbbbb.kaleidoscopetavern.datagen.model.ItemModelGenerator;
import com.github.ysbbbbbb.kaleidoscopetavern.datagen.recipe.ModRecipeGenerator;
import com.github.ysbbbbbb.kaleidoscopetavern.datagen.tag.TagBlock;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        var generator = event.getGenerator();
        var registries = event.getLookupProvider();
        var vanillaPack = generator.getVanillaPack(true);
        var helper = event.getExistingFileHelper();
        var pack = generator.getPackOutput();

        generator.addProvider(event.includeClient(), new BlockModelGenerator(pack, helper));
        generator.addProvider(event.includeClient(), new BlockStateGenerator(pack, helper));
        generator.addProvider(event.includeClient(), new ItemModelGenerator(pack, helper));
        generator.addProvider(event.includeServer(), new ModRecipeGenerator(pack, registries));

        generator.addProvider(event.includeServer(), new LootTableGenerator(pack, registries));

        generator.addProvider(event.includeServer(), new DrinkEffectDataProvider(pack));
        generator.addProvider(event.includeServer(), new DataMapGenerator(pack, registries));

        var block = vanillaPack.addProvider(packOutput -> new TagBlock(packOutput, registries, helper));
    }
}
