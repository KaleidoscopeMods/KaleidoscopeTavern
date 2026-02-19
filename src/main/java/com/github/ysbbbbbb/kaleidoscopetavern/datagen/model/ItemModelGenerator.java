package com.github.ysbbbbbb.kaleidoscopetavern.datagen.model;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopetavern.util.ColorUtils;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class ItemModelGenerator extends ItemModelProvider {
    public ItemModelGenerator(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, KaleidoscopeTavern.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        for (String color : ColorUtils.COLORS) {
            sofa(color);
            barStool(color);
        }

        basicItem(ModItems.CHALKBOARD.get(), "deco/chalkboard");

        sandwichBoard("grass");
        sandwichBoard("allium");
        sandwichBoard("azure_bluet");
        sandwichBoard("cornflower");
        sandwichBoard("orchid");
        sandwichBoard("peony");
        sandwichBoard("pink_petals");
        sandwichBoard("pitcher_plant");
        sandwichBoard("poppy");
        sandwichBoard("sunflower");
        sandwichBoard("torchflower");
        sandwichBoard("tulip");
        sandwichBoard("wither_rose");

        stringLights("colorless");
        for (String color : ColorUtils.COLORS) {
            stringLights(color);
        }
    }

    private void sofa(String color) {
        String name = "item/%s_sofa".formatted(color);
        ResourceLocation parent = modLoc("block/deco/sofa/%s/single".formatted(color));
        withExistingParent(name, parent);
    }

    private void barStool(String color) {
        String name = "item/%s_bar_stool".formatted(color);
        ResourceLocation parent = modLoc("block/deco/bar_stool/%s".formatted(color));
        withExistingParent(name, parent);
    }

    private void sandwichBoard(String type) {
        String name = "item/%s_sandwich_board".formatted(type);
        withExistingParent(name, modLoc("item/deco_sandwich_board"))
                .texture("layer1", modLoc("block/deco/sandwich_board/%s".formatted(type)));
    }

    private void stringLights(String color) {
        String name = "item/string_lights_%s".formatted(color);
        ResourceLocation parent = modLoc("block/deco/string_lights/%s".formatted(color));
        withExistingParent(name, parent);
    }

    private ItemModelBuilder basicItem(Item item, String texture) {
        ResourceLocation key = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item));
        return getBuilder(key.toString())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", new ResourceLocation(key.getNamespace(), "item/" + texture));
    }
}
