package com.github.ysbbbbbb.kaleidoscopetavern.datagen;

import com.github.ysbbbbbb.kaleidoscopetavern.datagen.datamap.DrinkEffectDataProvider;
import com.github.ysbbbbbb.kaleidoscopetavern.datagen.loottable.LootTableGenerator;
import com.github.ysbbbbbb.kaleidoscopetavern.datagen.model.BlockModelGenerator;
import com.github.ysbbbbbb.kaleidoscopetavern.datagen.model.BlockStateGenerator;
import com.github.ysbbbbbb.kaleidoscopetavern.datagen.model.ItemModelGenerator;
import com.github.ysbbbbbb.kaleidoscopetavern.datagen.recipe.ModRecipeGenerator;
import com.github.ysbbbbbb.kaleidoscopetavern.datagen.tag.TagBlock;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
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
        generator.addProvider(event.includeServer(), new ModRecipeGenerator(pack));

        generator.addProvider(event.includeServer(), new LootTableGenerator(pack));

        generator.addProvider(event.includeServer(), new DrinkEffectDataProvider(pack));

        var block = vanillaPack.addProvider(packOutput -> new TagBlock(packOutput, registries, helper));
    }
}
