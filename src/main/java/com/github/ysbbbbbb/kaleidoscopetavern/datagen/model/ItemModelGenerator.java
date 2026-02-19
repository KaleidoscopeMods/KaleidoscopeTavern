package com.github.ysbbbbbb.kaleidoscopetavern.datagen.model;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopetavern.util.ColorUtils;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

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

        painting(ModItems.YSBB_PAINTING, "ysbb");
        painting(ModItems.TARTARIC_ACID_PAINTING, "tartaric_acid");
        painting(ModItems.CR019_PAINTING, "cr019");
        painting(ModItems.UNKNOWN_PAINTING, "unknown");
        painting(ModItems.MASTER_MARISA_PAINTING, "master_marisa");
        painting(ModItems.SON_OF_MAN_PAINTING, "son_of_man");
        painting(ModItems.DAVID_PAINTING, "david");
        painting(ModItems.GIRL_WITH_PEARL_EARRING_PAINTING, "girl_with_pearl_earring");
        painting(ModItems.STARRY_NIGHT_PAINTING, "starry_night");
        painting(ModItems.VAN_GOGH_SELF_PORTRAIT_PAINTING, "van_gogh_self_portrait");
        painting(ModItems.FATHER_PAINTING, "father");
        painting(ModItems.GREAT_WAVE_PAINTING, "great_wave");
        painting(ModItems.MONA_LISA_PAINTING, "mona_lisa");
        painting(ModItems.MONDRIAN_PAINTING, "mondrian");

        barCounter(ModItems.BAR_COUNTER);
        basicItem(ModItems.STEPLADDER.get());
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

    private void painting(RegistryObject<Item> item, String name) {
        ResourceLocation key = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item.get()));
        getBuilder(key.toString())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", new ResourceLocation(key.getNamespace(), "block/deco/painting/%s".formatted(name)));
    }

    private void basicItem(Item item, String texture) {
        ResourceLocation key = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item));
        getBuilder(key.toString())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", new ResourceLocation(key.getNamespace(), "item/" + texture));
    }

    private void barCounter(RegistryObject<Item> item) {
        ResourceLocation key = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item.get()));
        withExistingParent(key.toString(), modLoc("block/deco/bar_counter/single"));
    }
}
