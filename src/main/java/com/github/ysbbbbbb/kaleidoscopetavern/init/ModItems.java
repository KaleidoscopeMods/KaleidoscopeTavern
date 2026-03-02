package com.github.ysbbbbbb.kaleidoscopetavern.init;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.item.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public interface ModItems {
    DeferredRegister.Items ITEMS = DeferredRegister.createItems(KaleidoscopeTavern.MOD_ID);

    // 沙发
    DeferredItem<Item> WHITE_SOFA = ITEMS.register("white_sofa", () -> new SofaBlockItem(ModBlocks.WHITE_SOFA));
    DeferredItem<Item> LIGHT_GRAY_SOFA = ITEMS.register("light_gray_sofa", () -> new SofaBlockItem(ModBlocks.LIGHT_GRAY_SOFA));
    DeferredItem<Item> GRAY_SOFA = ITEMS.register("gray_sofa", () -> new SofaBlockItem(ModBlocks.GRAY_SOFA));
    DeferredItem<Item> BLACK_SOFA = ITEMS.register("black_sofa", () -> new SofaBlockItem(ModBlocks.BLACK_SOFA));
    DeferredItem<Item> BROWN_SOFA = ITEMS.register("brown_sofa", () -> new SofaBlockItem(ModBlocks.BROWN_SOFA));
    DeferredItem<Item> RED_SOFA = ITEMS.register("red_sofa", () -> new SofaBlockItem(ModBlocks.RED_SOFA));
    DeferredItem<Item> ORANGE_SOFA = ITEMS.register("orange_sofa", () -> new SofaBlockItem(ModBlocks.ORANGE_SOFA));
    DeferredItem<Item> YELLOW_SOFA = ITEMS.register("yellow_sofa", () -> new SofaBlockItem(ModBlocks.YELLOW_SOFA));
    DeferredItem<Item> LIME_SOFA = ITEMS.register("lime_sofa", () -> new SofaBlockItem(ModBlocks.LIME_SOFA));
    DeferredItem<Item> GREEN_SOFA = ITEMS.register("green_sofa", () -> new SofaBlockItem(ModBlocks.GREEN_SOFA));
    DeferredItem<Item> CYAN_SOFA = ITEMS.register("cyan_sofa", () -> new SofaBlockItem(ModBlocks.CYAN_SOFA));
    DeferredItem<Item> LIGHT_BLUE_SOFA = ITEMS.register("light_blue_sofa", () -> new SofaBlockItem(ModBlocks.LIGHT_BLUE_SOFA));
    DeferredItem<Item> BLUE_SOFA = ITEMS.register("blue_sofa", () -> new SofaBlockItem(ModBlocks.BLUE_SOFA));
    DeferredItem<Item> PURPLE_SOFA = ITEMS.register("purple_sofa", () -> new SofaBlockItem(ModBlocks.PURPLE_SOFA));
    DeferredItem<Item> MAGENTA_SOFA = ITEMS.register("magenta_sofa", () -> new SofaBlockItem(ModBlocks.MAGENTA_SOFA));
    DeferredItem<Item> PINK_SOFA = ITEMS.register("pink_sofa", () -> new SofaBlockItem(ModBlocks.PINK_SOFA));

    // 高脚凳
    DeferredItem<Item> WHITE_BAR_STOOL = ITEMS.register("white_bar_stool", () -> new BarStoolBlockItem(ModBlocks.WHITE_BAR_STOOL));
    DeferredItem<Item> LIGHT_GRAY_BAR_STOOL = ITEMS.register("light_gray_bar_stool", () -> new BarStoolBlockItem(ModBlocks.LIGHT_GRAY_BAR_STOOL));
    DeferredItem<Item> GRAY_BAR_STOOL = ITEMS.register("gray_bar_stool", () -> new BarStoolBlockItem(ModBlocks.GRAY_BAR_STOOL));
    DeferredItem<Item> BLACK_BAR_STOOL = ITEMS.register("black_bar_stool", () -> new BarStoolBlockItem(ModBlocks.BLACK_BAR_STOOL));
    DeferredItem<Item> BROWN_BAR_STOOL = ITEMS.register("brown_bar_stool", () -> new BarStoolBlockItem(ModBlocks.BROWN_BAR_STOOL));
    DeferredItem<Item> RED_BAR_STOOL = ITEMS.register("red_bar_stool", () -> new BarStoolBlockItem(ModBlocks.RED_BAR_STOOL));
    DeferredItem<Item> ORANGE_BAR_STOOL = ITEMS.register("orange_bar_stool", () -> new BarStoolBlockItem(ModBlocks.ORANGE_BAR_STOOL));
    DeferredItem<Item> YELLOW_BAR_STOOL = ITEMS.register("yellow_bar_stool", () -> new BarStoolBlockItem(ModBlocks.YELLOW_BAR_STOOL));
    DeferredItem<Item> LIME_BAR_STOOL = ITEMS.register("lime_bar_stool", () -> new BarStoolBlockItem(ModBlocks.LIME_BAR_STOOL));
    DeferredItem<Item> GREEN_BAR_STOOL = ITEMS.register("green_bar_stool", () -> new BarStoolBlockItem(ModBlocks.GREEN_BAR_STOOL));
    DeferredItem<Item> CYAN_BAR_STOOL = ITEMS.register("cyan_bar_stool", () -> new BarStoolBlockItem(ModBlocks.CYAN_BAR_STOOL));
    DeferredItem<Item> LIGHT_BLUE_BAR_STOOL = ITEMS.register("light_blue_bar_stool", () -> new BarStoolBlockItem(ModBlocks.LIGHT_BLUE_BAR_STOOL));
    DeferredItem<Item> BLUE_BAR_STOOL = ITEMS.register("blue_bar_stool", () -> new BarStoolBlockItem(ModBlocks.BLUE_BAR_STOOL));
    DeferredItem<Item> PURPLE_BAR_STOOL = ITEMS.register("purple_bar_stool", () -> new BarStoolBlockItem(ModBlocks.PURPLE_BAR_STOOL));
    DeferredItem<Item> MAGENTA_BAR_STOOL = ITEMS.register("magenta_bar_stool", () -> new BarStoolBlockItem(ModBlocks.MAGENTA_BAR_STOOL));
    DeferredItem<Item> PINK_BAR_STOOL = ITEMS.register("pink_bar_stool", () -> new BarStoolBlockItem(ModBlocks.PINK_BAR_STOOL));

    // 黑板
    DeferredItem<Item> CHALKBOARD = ITEMS.register("chalkboard", () -> new BlockItem(ModBlocks.CHALKBOARD.get(), new Item.Properties()));

    // 展板
    DeferredItem<Item> BASE_SANDWICH_BOARD = ITEMS.register("base_sandwich_board", () -> new SandwichBoardBlockItem(ModBlocks.BASE_SANDWICH_BOARD));
    DeferredItem<Item> GRASS_SANDWICH_BOARD = ITEMS.register("grass_sandwich_board", () -> new SandwichBoardBlockItem(ModBlocks.GRASS_SANDWICH_BOARD));
    DeferredItem<Item> ALLIUM_SANDWICH_BOARD = ITEMS.register("allium_sandwich_board", () -> new SandwichBoardBlockItem(ModBlocks.ALLIUM_SANDWICH_BOARD));
    DeferredItem<Item> AZURE_BLUET_SANDWICH_BOARD = ITEMS.register("azure_bluet_sandwich_board", () -> new SandwichBoardBlockItem(ModBlocks.AZURE_BLUET_SANDWICH_BOARD));
    DeferredItem<Item> CORNFLOWER_SANDWICH_BOARD = ITEMS.register("cornflower_sandwich_board", () -> new SandwichBoardBlockItem(ModBlocks.CORNFLOWER_SANDWICH_BOARD));
    DeferredItem<Item> ORCHID_SANDWICH_BOARD = ITEMS.register("orchid_sandwich_board", () -> new SandwichBoardBlockItem(ModBlocks.ORCHID_SANDWICH_BOARD));
    DeferredItem<Item> PEONY_SANDWICH_BOARD = ITEMS.register("peony_sandwich_board", () -> new SandwichBoardBlockItem(ModBlocks.PEONY_SANDWICH_BOARD));
    DeferredItem<Item> PINK_PETALS_SANDWICH_BOARD = ITEMS.register("pink_petals_sandwich_board", () -> new SandwichBoardBlockItem(ModBlocks.PINK_PETALS_SANDWICH_BOARD));
    DeferredItem<Item> PITCHER_PLANT_SANDWICH_BOARD = ITEMS.register("pitcher_plant_sandwich_board", () -> new SandwichBoardBlockItem(ModBlocks.PITCHER_PLANT_SANDWICH_BOARD));
    DeferredItem<Item> POPPY_SANDWICH_BOARD = ITEMS.register("poppy_sandwich_board", () -> new SandwichBoardBlockItem(ModBlocks.POPPY_SANDWICH_BOARD));
    DeferredItem<Item> SUNFLOWER_SANDWICH_BOARD = ITEMS.register("sunflower_sandwich_board", () -> new SandwichBoardBlockItem(ModBlocks.SUNFLOWER_SANDWICH_BOARD));
    DeferredItem<Item> TORCHFLOWER_SANDWICH_BOARD = ITEMS.register("torchflower_sandwich_board", () -> new SandwichBoardBlockItem(ModBlocks.TORCHFLOWER_SANDWICH_BOARD));
    DeferredItem<Item> TULIP_SANDWICH_BOARD = ITEMS.register("tulip_sandwich_board", () -> new SandwichBoardBlockItem(ModBlocks.TULIP_SANDWICH_BOARD));
    DeferredItem<Item> WITHER_ROSE_SANDWICH_BOARD = ITEMS.register("wither_rose_sandwich_board", () -> new SandwichBoardBlockItem(ModBlocks.WITHER_ROSE_SANDWICH_BOARD));

    // 彩灯
    DeferredItem<Item> STRING_LIGHTS_COLORLESS = ITEMS.register("string_lights_colorless", () -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_COLORLESS));
    DeferredItem<Item> STRING_LIGHTS_WHITE = ITEMS.register("string_lights_white", () -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_WHITE));
    DeferredItem<Item> STRING_LIGHTS_LIGHT_GRAY = ITEMS.register("string_lights_light_gray", () -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_LIGHT_GRAY));
    DeferredItem<Item> STRING_LIGHTS_GRAY = ITEMS.register("string_lights_gray", () -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_GRAY));
    DeferredItem<Item> STRING_LIGHTS_BLACK = ITEMS.register("string_lights_black", () -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_BLACK));
    DeferredItem<Item> STRING_LIGHTS_BROWN = ITEMS.register("string_lights_brown", () -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_BROWN));
    DeferredItem<Item> STRING_LIGHTS_RED = ITEMS.register("string_lights_red", () -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_RED));
    DeferredItem<Item> STRING_LIGHTS_ORANGE = ITEMS.register("string_lights_orange", () -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_ORANGE));
    DeferredItem<Item> STRING_LIGHTS_YELLOW = ITEMS.register("string_lights_yellow", () -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_YELLOW));
    DeferredItem<Item> STRING_LIGHTS_LIME = ITEMS.register("string_lights_lime", () -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_LIME));
    DeferredItem<Item> STRING_LIGHTS_GREEN = ITEMS.register("string_lights_green", () -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_GREEN));
    DeferredItem<Item> STRING_LIGHTS_CYAN = ITEMS.register("string_lights_cyan", () -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_CYAN));
    DeferredItem<Item> STRING_LIGHTS_LIGHT_BLUE = ITEMS.register("string_lights_light_blue", () -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_LIGHT_BLUE));
    DeferredItem<Item> STRING_LIGHTS_BLUE = ITEMS.register("string_lights_blue", () -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_BLUE));
    DeferredItem<Item> STRING_LIGHTS_PURPLE = ITEMS.register("string_lights_purple", () -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_PURPLE));
    DeferredItem<Item> STRING_LIGHTS_MAGENTA = ITEMS.register("string_lights_magenta", () -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_MAGENTA));
    DeferredItem<Item> STRING_LIGHTS_PINK = ITEMS.register("string_lights_pink", () -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_PINK));

    // 挂画
    DeferredItem<Item> YSBB_PAINTING = ITEMS.register("ysbb_painting", () -> new PaintingBlockItem(ModBlocks.YSBB_PAINTING));
    DeferredItem<Item> TARTARIC_ACID_PAINTING = ITEMS.register("tartaric_acid_painting", () -> new PaintingBlockItem(ModBlocks.TARTARIC_ACID_PAINTING));
    DeferredItem<Item> CR019_PAINTING = ITEMS.register("cr019_painting", () -> new PaintingBlockItem(ModBlocks.CR019_PAINTING));
    DeferredItem<Item> UNKNOWN_PAINTING = ITEMS.register("unknown_painting", () -> new PaintingBlockItem(ModBlocks.UNKNOWN_PAINTING));
    DeferredItem<Item> MASTER_MARISA_PAINTING = ITEMS.register("master_marisa_painting", () -> new PaintingBlockItem(ModBlocks.MASTER_MARISA_PAINTING));
    DeferredItem<Item> SON_OF_MAN_PAINTING = ITEMS.register("son_of_man_painting", () -> new PaintingBlockItem(ModBlocks.SON_OF_MAN_PAINTING));
    DeferredItem<Item> DAVID_PAINTING = ITEMS.register("david_painting", () -> new PaintingBlockItem(ModBlocks.DAVID_PAINTING));
    DeferredItem<Item> GIRL_WITH_PEARL_EARRING_PAINTING = ITEMS.register("girl_with_pearl_earring_painting", () -> new PaintingBlockItem(ModBlocks.GIRL_WITH_PEARL_EARRING_PAINTING));
    DeferredItem<Item> STARRY_NIGHT_PAINTING = ITEMS.register("starry_night_painting", () -> new PaintingBlockItem(ModBlocks.STARRY_NIGHT_PAINTING));
    DeferredItem<Item> VAN_GOGH_SELF_PORTRAIT_PAINTING = ITEMS.register("van_gogh_self_portrait_painting", () -> new PaintingBlockItem(ModBlocks.VAN_GOGH_SELF_PORTRAIT_PAINTING));
    DeferredItem<Item> FATHER_PAINTING = ITEMS.register("father_painting", () -> new PaintingBlockItem(ModBlocks.FATHER_PAINTING));
    DeferredItem<Item> GREAT_WAVE_PAINTING = ITEMS.register("great_wave_painting", () -> new PaintingBlockItem(ModBlocks.GREAT_WAVE_PAINTING));
    DeferredItem<Item> MONA_LISA_PAINTING = ITEMS.register("mona_lisa_painting", () -> new PaintingBlockItem(ModBlocks.MONA_LISA_PAINTING));
    DeferredItem<Item> MONDRIAN_PAINTING = ITEMS.register("mondrian_painting", () -> new PaintingBlockItem(ModBlocks.MONDRIAN_PAINTING));

    // 吧台
    DeferredItem<Item> BAR_COUNTER = ITEMS.register("bar_counter", () -> new BlockItem(ModBlocks.BAR_COUNTER.get(), new Item.Properties()));
    // 人字梯
    DeferredItem<Item> STEPLADDER = ITEMS.register("stepladder", () -> new BlockItem(ModBlocks.STEPLADDER.get(), new Item.Properties()));
    // 野生葡萄藤
    DeferredItem<Item> GRAPEVINE = ITEMS.register("grapevine", GrapevineItem::new);
    // 藤架
    DeferredItem<Item> TRELLIS = ITEMS.register("trellis", () -> new BlockItem(ModBlocks.TRELLIS.get(), new Item.Properties()));
    // 葡萄
    DeferredItem<Item> GRAPE = ITEMS.register("grape", () -> new Item(new Item.Properties().food(ModFoods.GRAPE)));

    // 果盆
    DeferredItem<Item> PRESSING_TUB = ITEMS.register("pressing_tub", () -> new BlockItem(ModBlocks.PRESSING_TUB.get(), new Item.Properties()));
    // 果汁桶
    DeferredItem<Item> GRAPE_BUCKET = ITEMS.register("grape_bucket", () -> new JuiceBucketItem(ModFluids.GRAPE_JUICE));
    DeferredItem<Item> SWEET_BERRIES_BUCKET = ITEMS.register("sweet_berries_bucket", () -> new JuiceBucketItem(ModFluids.SWEET_BERRIES_JUICE));
    DeferredItem<Item> GLOW_BERRIES_BUCKET = ITEMS.register("glow_berries_bucket", () -> new JuiceBucketItem(ModFluids.GLOW_BERRIES_JUICE));
    // 龙头
    DeferredItem<Item> TAP = ITEMS.register("tap", () -> new BlockItem(ModBlocks.TAP.get(), new Item.Properties()));
    // 酒桶
    DeferredItem<Item> BARREL = ITEMS.register("barrel", () -> new BlockItem(ModBlocks.BARREL.get(), new Item.Properties()));
    // 酒柜
    DeferredItem<Item> BAR_CABINET = ITEMS.register("bar_cabinet", () -> new BlockItem(ModBlocks.BAR_CABINET.get(), new Item.Properties()));
    DeferredItem<Item> GLASS_BAR_CABINET = ITEMS.register("glass_bar_cabinet", () -> new BlockItem(ModBlocks.GLASS_BAR_CABINET.get(), new Item.Properties()));

    // 空瓶
    DeferredItem<Item> EMPTY_BOTTLE = ITEMS.register("empty_bottle", () -> new BottleBlockItem(ModBlocks.EMPTY_BOTTLE.get()));
    // 燃烧瓶
    DeferredItem<Item> MOLOTOV = ITEMS.register("molotov", () -> new MolotovBlockItem(ModBlocks.MOLOTOV.get()));
    // 酒
    DeferredItem<Item> WINE = ITEMS.register("wine", () -> new DrinkBlockItem(ModBlocks.WINE.get()));
    DeferredItem<Item> CHAMPAGNE = ITEMS.register("champagne", () -> new DrinkBlockItem(ModBlocks.CHAMPAGNE.get()));
    DeferredItem<Item> VODKA = ITEMS.register("vodka", () -> new DrinkBlockItem(ModBlocks.VODKA.get()));
    DeferredItem<Item> BRANDY = ITEMS.register("brandy", () -> new DrinkBlockItem(ModBlocks.BRANDY.get()));
    DeferredItem<Item> CARIGNAN = ITEMS.register("carignan", () -> new DrinkBlockItem(ModBlocks.CARIGNAN.get()));
    DeferredItem<Item> SAKURA_WINE = ITEMS.register("sakura_wine", () -> new DrinkBlockItem(ModBlocks.SAKURA_WINE.get()));
    DeferredItem<Item> PLUM_WINE = ITEMS.register("plum_wine", () -> new DrinkBlockItem(ModBlocks.PLUM_WINE.get()));
    DeferredItem<Item> WHISKEY = ITEMS.register("whiskey", () -> new DrinkBlockItem(ModBlocks.WHISKEY.get()));
    DeferredItem<Item> ICE_WINE = ITEMS.register("ice_wine", () -> new DrinkBlockItem(ModBlocks.ICE_WINE.get()));
    DeferredItem<Item> VINEGAR = ITEMS.register("vinegar", () -> new DrinkBlockItem(ModBlocks.VINEGAR.get()));
}