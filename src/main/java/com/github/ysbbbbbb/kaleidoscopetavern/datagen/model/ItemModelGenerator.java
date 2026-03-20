package com.github.ysbbbbbb.kaleidoscopetavern.datagen.model;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopetavern.util.ColorUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.Objects;
import java.util.function.Supplier;

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

        basicItem(ModItems.CHALKBOARD.get());

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
        basicItem(ModItems.GRAPEVINE.get());

        trellis(ModItems.TRELLIS);
        basicItem(ModItems.GRAPE.get());

        withExistingParent("item/pressing_tub", modLoc("block/brew/pressing_tub"));
        withExistingParent("item/bar_cabinet", modLoc("block/brew/bar_cabinet/single"));
        withExistingParent("item/glass_bar_cabinet", modLoc("block/brew/glass_bar_cabinet/single"));

        basicItem(ModItems.GRAPE_BUCKET.get());
        basicItem(ModItems.SWEET_BERRIES_BUCKET.get());
        basicItem(ModItems.GLOW_BERRIES_BUCKET.get());

        basicItem(ModItems.TAP.get());
        //basicItem(ModItems.BARREL.get());

        basicItem(ModItems.EMPTY_BOTTLE.get());
        basicItem(ModItems.MOLOTOV.get());
        basicItem(ModItems.WINE.get());
        basicItem(ModItems.CHAMPAGNE.get());
        basicItem(ModItems.VODKA.get());
        basicItem(ModItems.BRANDY.get());
        basicItem(ModItems.CARIGNAN.get());
        basicItem(ModItems.SAKURA_WINE.get());
        basicItem(ModItems.PLUM_WINE.get());
        basicItem(ModItems.WHISKEY.get());
        basicItem(ModItems.ICE_WINE.get());
        basicItem(ModItems.VINEGAR.get());
    }

    private void sofa(String color) {
        String name = "item/%s_sofa".formatted(color);
        ResourceLocation parent = modLoc("block/deco/sofa/%s/single".formatted(color));
        withExistingParent(name, parent);
    }

    private void barStool(String color) {
        String name = "item/%s_bar_stool".formatted(color);
        ResourceLocation parent = modLoc("item/bar_stool_base");
        getBuilder(name)
                .parent(getExistingFile(parent))
                .texture("particle", ResourceLocation.withDefaultNamespace("block/%s_wool".formatted(color)))
                .texture("texture", modLoc("block/deco/bar_stool/%s".formatted(color)));
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

    private void painting(Supplier<? extends Item> item, String name) {
        ResourceLocation key = Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item.get()));
        getBuilder(key.toString())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", modLoc("block/deco/painting/%s".formatted(name)));
    }

    private void barCounter(Supplier<? extends Item> item) {
        ResourceLocation key = Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item.get()));
        withExistingParent(key.toString(), modLoc("block/deco/bar_counter/single"));
    }

    private void trellis(Supplier<? extends Item> item) {
        ResourceLocation key = Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item.get()));
        withExistingParent(key.toString(), modLoc("block/plant/trellis/single"));
    }
}
