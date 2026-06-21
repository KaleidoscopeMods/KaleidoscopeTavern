package com.github.ysbbbbbb.kaleidoscopetavern.datagen.model;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopetavern.util.ColorUtils;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.loaders.SeparateTransformsModelBuilder;
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

        shaker();

        trellis(ModItems.TRELLIS);
        basicItem(ModItems.GRAPE.get());
        basicItem(ModItems.ICE_GRAPE.get());
        basicItem(ModItems.GOLD_GRAPE.get());
        basicItem(ModItems.GREEN_GRAPE.get());

        withExistingParent("item/pressing_tub", modLoc("block/brew/pressing_tub"));
        withExistingParent("item/bar_cabinet", modLoc("block/brew/bar_cabinet/single"));
        withExistingParent("item/glass_bar_cabinet", modLoc("block/brew/glass_bar_cabinet/single"));
        withExistingParent("item/cellar_cabinet", modLoc("block/brew/cellar_cabinet/single"));
        withExistingParent("item/tilted_rack", modLoc("block/deco/tilted_rack"));
        withExistingParent("item/circular_rack", modLoc("block/deco/circular_rack"));
        withExistingParent("item/glassware_holder", modLoc("block/deco/glassware_holder"));

        withExistingParent("item/table", modLoc("block/deco/table/single"));

        basicItem(ModItems.GRAPE_BUCKET.get());
        basicItem(ModItems.ICE_GRAPE_BUCKET.get());
        basicItem(ModItems.GOLD_GRAPE_BUCKET.get());
        basicItem(ModItems.GREEN_GRAPE_BUCKET.get());
        basicItem(ModItems.SWEET_BERRIES_BUCKET.get());
        basicItem(ModItems.GLOW_BERRIES_BUCKET.get());

        basicItem(ModItems.TAP.get());
        basicItem(ModItems.HOLDER.get());

        basicItem(ModItems.BELL_PENDANT_LAMP.get());
        basicItem(ModItems.YELLOW_PENDANT_LAMP.get());
        basicItem(ModItems.BLUE_PENDANT_LAMP.get());

        // 香薰
        basicItem(ModItems.SAKURA_INCENSE.get());
        basicItem(ModItems.PINE_INCENSE.get());
        basicItem(ModItems.GINKGO_INCENSE.get());
        basicItem(ModItems.SPORE_INCENSE.get());
        basicItem(ModItems.CATNIP_INCENSE.get());
        basicItem(ModItems.SNOW_INCENSE.get());
        basicItem(ModItems.BUTTERFLY_INCENSE.get());
        basicItem(ModItems.FIREFLY_INCENSE.get());

        basicItem(ModItems.EMPTY_BOTTLE.get());
        basicItem(ModItems.EMPTY_GLASSWARE.get());

        signatureCocktail();

        basicItem(ModItems.MYSTERY_COCKTAIL.get());
        basicItem(ModItems.WHITE_LADY.get());
        basicItem(ModItems.EMERALD.get());
        basicItem(ModItems.BRASS_HEART.get());
        basicItem(ModItems.GODFATHER.get());
        basicItem(ModItems.GRASSHOPPER.get());
        basicItem(ModItems.SCREWDRIVER.get());
        basicItem(ModItems.MOJITO.get());
        basicItem(ModItems.ALLIUM_GARDEN.get());
        basicItem(ModItems.DEPTH_CHARGE.get());
        basicItem(ModItems.NETHER_SPECIAL.get());
        basicItem(ModItems.BLOODY_MARY.get());
        basicItem(ModItems.SCULK_SPECIAL.get());

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
        basicItem(ModItems.POLARIS_SWEET_WHITE.get());
        basicItem(ModItems.HONEY_WINE.get());
        basicItem(ModItems.RED_QUEEN.get());
        basicItem(ModItems.MINERS_STAR.get());
        basicItem(ModItems.RUM.get());
        basicItem(ModItems.RIESLING_DRY_WHITE.get());
        basicItem(ModItems.SUNSET_GLOW.get());
        basicItem(ModItems.MADAME_SHEXIANG.get());
        basicItem(ModItems.SWEET_BERRY_WINE.get());
        basicItem(ModItems.SHERRY.get());
        basicItem(ModItems.MOTHER_SNOW.get());
        basicItem(ModItems.LUMINOUS_BRIDE.get());
        basicItem(ModItems.GLOWFLOWER_BREW.get());
        basicItem(ModItems.SAUVIGNON_BLANC_DRY_WHITE.get());
        basicItem(ModItems.VINEGAR.get());
        basicItem(ModItems.WATERMELON_JUICE.get());
    }

    private void signatureCocktail() {
        ResourceLocation key = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(ModItems.SIGNATURE_COCKTAIL.get()));
        getBuilder(key.toString())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", modLoc("item/signature_cocktail"))
                .texture("layer1", modLoc("item/signature_cocktail_tint"));
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
                .texture("particle", new ResourceLocation("block/%s_wool".formatted(color)))
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

    private void painting(RegistryObject<Item> item, String name) {
        ResourceLocation key = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item.get()));
        getBuilder(key.toString())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", modLoc("block/deco/painting/%s".formatted(name)));
    }

    private void barCounter(RegistryObject<Item> item) {
        ResourceLocation key = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item.get()));
        withExistingParent(key.toString(), modLoc("block/deco/bar_counter/single"));
    }

    private void trellis(RegistryObject<Item> item) {
        ResourceLocation key = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item.get()));
        withExistingParent(key.toString(), modLoc("block/plant/trellis/single"));
    }

    private void shaker() {
        ItemModelBuilder shakerItem = new ItemModelBuilder(modLoc("shaker"), this.existingFileHelper)
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", modLoc("item/shaker"));
        ItemModelBuilder shakerBlock = new ItemModelBuilder(modLoc("shaker"), this.existingFileHelper)
                .parent(new ModelFile.UncheckedModelFile(modLoc("item/shaker_3d")));
        getBuilder("shaker")
                .guiLight(BlockModel.GuiLight.FRONT)
                .customLoader(SeparateTransformsModelBuilder::begin).base(shakerBlock)
                .perspective(ItemDisplayContext.GUI, shakerItem)
                .perspective(ItemDisplayContext.FIXED, shakerItem);
    }
}
