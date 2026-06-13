package com.github.ysbbbbbb.kaleidoscopetavern.init;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.item.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public interface ModItems {
    DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, KaleidoscopeTavern.MOD_ID);

    // 沙发
    RegistryObject<Item> WHITE_SOFA = ITEMS.register("white_sofa", () -> new SofaBlockItem(ModBlocks.WHITE_SOFA));
    RegistryObject<Item> LIGHT_GRAY_SOFA = ITEMS.register("light_gray_sofa", () -> new SofaBlockItem(ModBlocks.LIGHT_GRAY_SOFA));
    RegistryObject<Item> GRAY_SOFA = ITEMS.register("gray_sofa", () -> new SofaBlockItem(ModBlocks.GRAY_SOFA));
    RegistryObject<Item> BLACK_SOFA = ITEMS.register("black_sofa", () -> new SofaBlockItem(ModBlocks.BLACK_SOFA));
    RegistryObject<Item> BROWN_SOFA = ITEMS.register("brown_sofa", () -> new SofaBlockItem(ModBlocks.BROWN_SOFA));
    RegistryObject<Item> RED_SOFA = ITEMS.register("red_sofa", () -> new SofaBlockItem(ModBlocks.RED_SOFA));
    RegistryObject<Item> ORANGE_SOFA = ITEMS.register("orange_sofa", () -> new SofaBlockItem(ModBlocks.ORANGE_SOFA));
    RegistryObject<Item> YELLOW_SOFA = ITEMS.register("yellow_sofa", () -> new SofaBlockItem(ModBlocks.YELLOW_SOFA));
    RegistryObject<Item> LIME_SOFA = ITEMS.register("lime_sofa", () -> new SofaBlockItem(ModBlocks.LIME_SOFA));
    RegistryObject<Item> GREEN_SOFA = ITEMS.register("green_sofa", () -> new SofaBlockItem(ModBlocks.GREEN_SOFA));
    RegistryObject<Item> CYAN_SOFA = ITEMS.register("cyan_sofa", () -> new SofaBlockItem(ModBlocks.CYAN_SOFA));
    RegistryObject<Item> LIGHT_BLUE_SOFA = ITEMS.register("light_blue_sofa", () -> new SofaBlockItem(ModBlocks.LIGHT_BLUE_SOFA));
    RegistryObject<Item> BLUE_SOFA = ITEMS.register("blue_sofa", () -> new SofaBlockItem(ModBlocks.BLUE_SOFA));
    RegistryObject<Item> PURPLE_SOFA = ITEMS.register("purple_sofa", () -> new SofaBlockItem(ModBlocks.PURPLE_SOFA));
    RegistryObject<Item> MAGENTA_SOFA = ITEMS.register("magenta_sofa", () -> new SofaBlockItem(ModBlocks.MAGENTA_SOFA));
    RegistryObject<Item> PINK_SOFA = ITEMS.register("pink_sofa", () -> new SofaBlockItem(ModBlocks.PINK_SOFA));

    // 高脚凳
    RegistryObject<Item> WHITE_BAR_STOOL = ITEMS.register("white_bar_stool", () -> new BarStoolBlockItem(ModBlocks.WHITE_BAR_STOOL));
    RegistryObject<Item> LIGHT_GRAY_BAR_STOOL = ITEMS.register("light_gray_bar_stool", () -> new BarStoolBlockItem(ModBlocks.LIGHT_GRAY_BAR_STOOL));
    RegistryObject<Item> GRAY_BAR_STOOL = ITEMS.register("gray_bar_stool", () -> new BarStoolBlockItem(ModBlocks.GRAY_BAR_STOOL));
    RegistryObject<Item> BLACK_BAR_STOOL = ITEMS.register("black_bar_stool", () -> new BarStoolBlockItem(ModBlocks.BLACK_BAR_STOOL));
    RegistryObject<Item> BROWN_BAR_STOOL = ITEMS.register("brown_bar_stool", () -> new BarStoolBlockItem(ModBlocks.BROWN_BAR_STOOL));
    RegistryObject<Item> RED_BAR_STOOL = ITEMS.register("red_bar_stool", () -> new BarStoolBlockItem(ModBlocks.RED_BAR_STOOL));
    RegistryObject<Item> ORANGE_BAR_STOOL = ITEMS.register("orange_bar_stool", () -> new BarStoolBlockItem(ModBlocks.ORANGE_BAR_STOOL));
    RegistryObject<Item> YELLOW_BAR_STOOL = ITEMS.register("yellow_bar_stool", () -> new BarStoolBlockItem(ModBlocks.YELLOW_BAR_STOOL));
    RegistryObject<Item> LIME_BAR_STOOL = ITEMS.register("lime_bar_stool", () -> new BarStoolBlockItem(ModBlocks.LIME_BAR_STOOL));
    RegistryObject<Item> GREEN_BAR_STOOL = ITEMS.register("green_bar_stool", () -> new BarStoolBlockItem(ModBlocks.GREEN_BAR_STOOL));
    RegistryObject<Item> CYAN_BAR_STOOL = ITEMS.register("cyan_bar_stool", () -> new BarStoolBlockItem(ModBlocks.CYAN_BAR_STOOL));
    RegistryObject<Item> LIGHT_BLUE_BAR_STOOL = ITEMS.register("light_blue_bar_stool", () -> new BarStoolBlockItem(ModBlocks.LIGHT_BLUE_BAR_STOOL));
    RegistryObject<Item> BLUE_BAR_STOOL = ITEMS.register("blue_bar_stool", () -> new BarStoolBlockItem(ModBlocks.BLUE_BAR_STOOL));
    RegistryObject<Item> PURPLE_BAR_STOOL = ITEMS.register("purple_bar_stool", () -> new BarStoolBlockItem(ModBlocks.PURPLE_BAR_STOOL));
    RegistryObject<Item> MAGENTA_BAR_STOOL = ITEMS.register("magenta_bar_stool", () -> new BarStoolBlockItem(ModBlocks.MAGENTA_BAR_STOOL));
    RegistryObject<Item> PINK_BAR_STOOL = ITEMS.register("pink_bar_stool", () -> new BarStoolBlockItem(ModBlocks.PINK_BAR_STOOL));

    // 黑板
    RegistryObject<Item> CHALKBOARD = ITEMS.register("chalkboard", () -> new BlockItem(ModBlocks.CHALKBOARD.get(), new Item.Properties()));
    RegistryObject<Item> TABLE = ITEMS.register("table", () -> new BlockItem(ModBlocks.TABLE.get(), new Item.Properties()));

    // 展板
    RegistryObject<Item> BASE_SANDWICH_BOARD = ITEMS.register("base_sandwich_board", () -> new SandwichBoardBlockItem(ModBlocks.BASE_SANDWICH_BOARD));
    RegistryObject<Item> GRASS_SANDWICH_BOARD = ITEMS.register("grass_sandwich_board", () -> new SandwichBoardBlockItem(ModBlocks.GRASS_SANDWICH_BOARD));
    RegistryObject<Item> ALLIUM_SANDWICH_BOARD = ITEMS.register("allium_sandwich_board", () -> new SandwichBoardBlockItem(ModBlocks.ALLIUM_SANDWICH_BOARD));
    RegistryObject<Item> AZURE_BLUET_SANDWICH_BOARD = ITEMS.register("azure_bluet_sandwich_board", () -> new SandwichBoardBlockItem(ModBlocks.AZURE_BLUET_SANDWICH_BOARD));
    RegistryObject<Item> CORNFLOWER_SANDWICH_BOARD = ITEMS.register("cornflower_sandwich_board", () -> new SandwichBoardBlockItem(ModBlocks.CORNFLOWER_SANDWICH_BOARD));
    RegistryObject<Item> ORCHID_SANDWICH_BOARD = ITEMS.register("orchid_sandwich_board", () -> new SandwichBoardBlockItem(ModBlocks.ORCHID_SANDWICH_BOARD));
    RegistryObject<Item> PEONY_SANDWICH_BOARD = ITEMS.register("peony_sandwich_board", () -> new SandwichBoardBlockItem(ModBlocks.PEONY_SANDWICH_BOARD));
    RegistryObject<Item> PINK_PETALS_SANDWICH_BOARD = ITEMS.register("pink_petals_sandwich_board", () -> new SandwichBoardBlockItem(ModBlocks.PINK_PETALS_SANDWICH_BOARD));
    RegistryObject<Item> PITCHER_PLANT_SANDWICH_BOARD = ITEMS.register("pitcher_plant_sandwich_board", () -> new SandwichBoardBlockItem(ModBlocks.PITCHER_PLANT_SANDWICH_BOARD));
    RegistryObject<Item> POPPY_SANDWICH_BOARD = ITEMS.register("poppy_sandwich_board", () -> new SandwichBoardBlockItem(ModBlocks.POPPY_SANDWICH_BOARD));
    RegistryObject<Item> SUNFLOWER_SANDWICH_BOARD = ITEMS.register("sunflower_sandwich_board", () -> new SandwichBoardBlockItem(ModBlocks.SUNFLOWER_SANDWICH_BOARD));
    RegistryObject<Item> TORCHFLOWER_SANDWICH_BOARD = ITEMS.register("torchflower_sandwich_board", () -> new SandwichBoardBlockItem(ModBlocks.TORCHFLOWER_SANDWICH_BOARD));
    RegistryObject<Item> TULIP_SANDWICH_BOARD = ITEMS.register("tulip_sandwich_board", () -> new SandwichBoardBlockItem(ModBlocks.TULIP_SANDWICH_BOARD));
    RegistryObject<Item> WITHER_ROSE_SANDWICH_BOARD = ITEMS.register("wither_rose_sandwich_board", () -> new SandwichBoardBlockItem(ModBlocks.WITHER_ROSE_SANDWICH_BOARD));

    // 彩灯
    RegistryObject<Item> STRING_LIGHTS_COLORLESS = ITEMS.register("string_lights_colorless", () -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_COLORLESS));
    RegistryObject<Item> STRING_LIGHTS_WHITE = ITEMS.register("string_lights_white", () -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_WHITE));
    RegistryObject<Item> STRING_LIGHTS_LIGHT_GRAY = ITEMS.register("string_lights_light_gray", () -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_LIGHT_GRAY));
    RegistryObject<Item> STRING_LIGHTS_GRAY = ITEMS.register("string_lights_gray", () -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_GRAY));
    RegistryObject<Item> STRING_LIGHTS_BLACK = ITEMS.register("string_lights_black", () -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_BLACK));
    RegistryObject<Item> STRING_LIGHTS_BROWN = ITEMS.register("string_lights_brown", () -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_BROWN));
    RegistryObject<Item> STRING_LIGHTS_RED = ITEMS.register("string_lights_red", () -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_RED));
    RegistryObject<Item> STRING_LIGHTS_ORANGE = ITEMS.register("string_lights_orange", () -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_ORANGE));
    RegistryObject<Item> STRING_LIGHTS_YELLOW = ITEMS.register("string_lights_yellow", () -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_YELLOW));
    RegistryObject<Item> STRING_LIGHTS_LIME = ITEMS.register("string_lights_lime", () -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_LIME));
    RegistryObject<Item> STRING_LIGHTS_GREEN = ITEMS.register("string_lights_green", () -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_GREEN));
    RegistryObject<Item> STRING_LIGHTS_CYAN = ITEMS.register("string_lights_cyan", () -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_CYAN));
    RegistryObject<Item> STRING_LIGHTS_LIGHT_BLUE = ITEMS.register("string_lights_light_blue", () -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_LIGHT_BLUE));
    RegistryObject<Item> STRING_LIGHTS_BLUE = ITEMS.register("string_lights_blue", () -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_BLUE));
    RegistryObject<Item> STRING_LIGHTS_PURPLE = ITEMS.register("string_lights_purple", () -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_PURPLE));
    RegistryObject<Item> STRING_LIGHTS_MAGENTA = ITEMS.register("string_lights_magenta", () -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_MAGENTA));
    RegistryObject<Item> STRING_LIGHTS_PINK = ITEMS.register("string_lights_pink", () -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_PINK));

    // 挂画
    RegistryObject<Item> YSBB_PAINTING = ITEMS.register("ysbb_painting", () -> new PaintingBlockItem(ModBlocks.YSBB_PAINTING));
    RegistryObject<Item> TARTARIC_ACID_PAINTING = ITEMS.register("tartaric_acid_painting", () -> new PaintingBlockItem(ModBlocks.TARTARIC_ACID_PAINTING));
    RegistryObject<Item> CR019_PAINTING = ITEMS.register("cr019_painting", () -> new PaintingBlockItem(ModBlocks.CR019_PAINTING));
    RegistryObject<Item> UNKNOWN_PAINTING = ITEMS.register("unknown_painting", () -> new PaintingBlockItem(ModBlocks.UNKNOWN_PAINTING));
    RegistryObject<Item> MASTER_MARISA_PAINTING = ITEMS.register("master_marisa_painting", () -> new PaintingBlockItem(ModBlocks.MASTER_MARISA_PAINTING));
    RegistryObject<Item> SON_OF_MAN_PAINTING = ITEMS.register("son_of_man_painting", () -> new PaintingBlockItem(ModBlocks.SON_OF_MAN_PAINTING));
    RegistryObject<Item> DAVID_PAINTING = ITEMS.register("david_painting", () -> new PaintingBlockItem(ModBlocks.DAVID_PAINTING));
    RegistryObject<Item> GIRL_WITH_PEARL_EARRING_PAINTING = ITEMS.register("girl_with_pearl_earring_painting", () -> new PaintingBlockItem(ModBlocks.GIRL_WITH_PEARL_EARRING_PAINTING));
    RegistryObject<Item> STARRY_NIGHT_PAINTING = ITEMS.register("starry_night_painting", () -> new PaintingBlockItem(ModBlocks.STARRY_NIGHT_PAINTING));
    RegistryObject<Item> VAN_GOGH_SELF_PORTRAIT_PAINTING = ITEMS.register("van_gogh_self_portrait_painting", () -> new PaintingBlockItem(ModBlocks.VAN_GOGH_SELF_PORTRAIT_PAINTING));
    RegistryObject<Item> FATHER_PAINTING = ITEMS.register("father_painting", () -> new PaintingBlockItem(ModBlocks.FATHER_PAINTING));
    RegistryObject<Item> GREAT_WAVE_PAINTING = ITEMS.register("great_wave_painting", () -> new PaintingBlockItem(ModBlocks.GREAT_WAVE_PAINTING));
    RegistryObject<Item> MONA_LISA_PAINTING = ITEMS.register("mona_lisa_painting", () -> new PaintingBlockItem(ModBlocks.MONA_LISA_PAINTING));
    RegistryObject<Item> MONDRIAN_PAINTING = ITEMS.register("mondrian_painting", () -> new PaintingBlockItem(ModBlocks.MONDRIAN_PAINTING));

    // 吧台
    RegistryObject<Item> BAR_COUNTER = ITEMS.register("bar_counter", () -> new BlockItem(ModBlocks.BAR_COUNTER.get(), new Item.Properties()));
    // 人字梯
    RegistryObject<Item> STEPLADDER = ITEMS.register("stepladder", () -> new BlockItem(ModBlocks.STEPLADDER.get(), new Item.Properties()));
    // 野生葡萄藤
    RegistryObject<Item> GRAPEVINE = ITEMS.register("grapevine", GrapevineItem::new);
    // 藤架
    RegistryObject<Item> TRELLIS = ITEMS.register("trellis", () -> new BlockItem(ModBlocks.TRELLIS.get(), new Item.Properties()));
    // 葡萄
    RegistryObject<Item> GRAPE = ITEMS.register("grape", () -> new Item(new Item.Properties().food(ModFoods.GRAPE)));
    RegistryObject<Item> ICE_GRAPE = ITEMS.register("ice_grape", () -> new Item(new Item.Properties().food(ModFoods.GRAPE)));
    RegistryObject<Item> GOLD_GRAPE = ITEMS.register("gold_grape", () -> new Item(new Item.Properties().food(ModFoods.GRAPE)));
    RegistryObject<Item> GREEN_GRAPE = ITEMS.register("green_grape", () -> new Item(new Item.Properties().food(ModFoods.GRAPE)));

    // 果盆
    RegistryObject<Item> PRESSING_TUB = ITEMS.register("pressing_tub", () -> new BlockItem(ModBlocks.PRESSING_TUB.get(), new Item.Properties()));
    // 果汁桶
    RegistryObject<Item> GRAPE_BUCKET = ITEMS.register("grape_bucket", () -> new JuiceBucketItem(ModFluids.GRAPE_JUICE));
    RegistryObject<Item> ICE_GRAPE_BUCKET = ITEMS.register("ice_grape_bucket", () -> new JuiceBucketItem(ModFluids.ICE_GRAPE_JUICE));
    RegistryObject<Item> GOLD_GRAPE_BUCKET = ITEMS.register("gold_grape_bucket", () -> new JuiceBucketItem(ModFluids.GOLD_GRAPE_JUICE));
    RegistryObject<Item> GREEN_GRAPE_BUCKET = ITEMS.register("green_grape_bucket", () -> new JuiceBucketItem(ModFluids.GREEN_GRAPE_JUICE));
    RegistryObject<Item> SWEET_BERRIES_BUCKET = ITEMS.register("sweet_berries_bucket", () -> new JuiceBucketItem(ModFluids.SWEET_BERRIES_JUICE));
    RegistryObject<Item> GLOW_BERRIES_BUCKET = ITEMS.register("glow_berries_bucket", () -> new JuiceBucketItem(ModFluids.GLOW_BERRIES_JUICE));
    // 龙头
    RegistryObject<Item> TAP = ITEMS.register("tap", () -> new BlockItem(ModBlocks.TAP.get(), new Item.Properties()));
    // 酒桶
    RegistryObject<Item> BARREL = ITEMS.register("barrel", () -> new BlockItem(ModBlocks.BARREL.get(), new Item.Properties()));
    // 酒柜
    RegistryObject<Item> BAR_CABINET = ITEMS.register("bar_cabinet", () -> new BlockItem(ModBlocks.BAR_CABINET.get(), new Item.Properties()));
    RegistryObject<Item> GLASS_BAR_CABINET = ITEMS.register("glass_bar_cabinet", () -> new BlockItem(ModBlocks.GLASS_BAR_CABINET.get(), new Item.Properties()));
    RegistryObject<Item> CELLAR_CABINET = ITEMS.register("cellar_cabinet", () -> new BlockItem(ModBlocks.CELLAR_CABINET.get(), new Item.Properties()));

    // 酒架
    RegistryObject<Item> TILTED_RACK = ITEMS.register("tilted_rack", () -> new BlockItem(ModBlocks.TILTED_RACK.get(), new Item.Properties()));
    RegistryObject<Item> CIRCULAR_RACK = ITEMS.register("circular_rack", () -> new BlockItem(ModBlocks.CIRCULAR_RACK.get(), new Item.Properties()));
    RegistryObject<Item> HOLDER = ITEMS.register("holder", () -> new BlockItem(ModBlocks.HOLDER.get(), new Item.Properties()));

    // 空瓶
    RegistryObject<Item> EMPTY_BOTTLE = ITEMS.register("empty_bottle", () -> new BottleBlockItem(ModBlocks.EMPTY_BOTTLE.get()));
    RegistryObject<Item> EMPTY_GLASSWARE = ITEMS.register("empty_glassware", () -> new GlasswareBlockItem(ModBlocks.EMPTY_GLASSWARE.get()));

    // 鸡尾酒
    RegistryObject<Item> SIGNATURE_COCKTAIL = ITEMS.register("signature_cocktail", () -> new SignatureCocktailBlockItem(ModBlocks.SIGNATURE_COCKTAIL.get()));
    RegistryObject<Item> MYSTERY_COCKTAIL = ITEMS.register("mystery_cocktail", () -> new CocktailBlockItem(ModBlocks.MYSTERY_COCKTAIL.get()));
    RegistryObject<Item> WHITE_LADY = ITEMS.register("white_lady", () -> new CocktailBlockItem(ModBlocks.WHITE_LADY.get()));
    RegistryObject<Item> EMERALD = ITEMS.register("emerald", () -> new CocktailBlockItem(ModBlocks.EMERALD.get()));
    RegistryObject<Item> BRASS_HEART = ITEMS.register("brass_heart", () -> new CocktailBlockItem(ModBlocks.BRASS_HEART.get()));
    RegistryObject<Item> GODFATHER = ITEMS.register("godfather", () -> new CocktailBlockItem(ModBlocks.GODFATHER.get()));
    RegistryObject<Item> GRASSHOPPER = ITEMS.register("grasshopper", () -> new CocktailBlockItem(ModBlocks.GRASSHOPPER.get()));
    RegistryObject<Item> SCREWDRIVER = ITEMS.register("screwdriver", () -> new CocktailBlockItem(ModBlocks.SCREWDRIVER.get()));
    RegistryObject<Item> MOJITO = ITEMS.register("mojito", () -> new CocktailBlockItem(ModBlocks.MOJITO.get()));
    RegistryObject<Item> ALLIUM_GARDEN = ITEMS.register("allium_garden", () -> new CocktailBlockItem(ModBlocks.ALLIUM_GARDEN.get()));
    RegistryObject<Item> DEPTH_CHARGE = ITEMS.register("depth_charge", () -> new CocktailBlockItem(ModBlocks.DEPTH_CHARGE.get()));
    RegistryObject<Item> NETHER_SPECIAL = ITEMS.register("nether_special", () -> new CocktailBlockItem(ModBlocks.NETHER_SPECIAL.get()));
    RegistryObject<Item> BLOODY_MARY = ITEMS.register("bloody_mary", () -> new CocktailBlockItem(ModBlocks.BLOODY_MARY.get()));
    RegistryObject<Item> SCULK_SPECIAL = ITEMS.register("sculk_special", () -> new CocktailBlockItem(ModBlocks.SCULK_SPECIAL.get()));

    // 雪克杯
    RegistryObject<Item> SHAKER = ITEMS.register("shaker", ShakerItem::new);

    // 燃烧瓶
    RegistryObject<Item> MOLOTOV = ITEMS.register("molotov", () -> new MolotovBlockItem(ModBlocks.MOLOTOV.get()));
    // 酒
    RegistryObject<Item> WINE = ITEMS.register("wine", () -> new DrinkBlockItem(ModBlocks.WINE.get()));
    RegistryObject<Item> CHAMPAGNE = ITEMS.register("champagne", () -> new DrinkBlockItem(ModBlocks.CHAMPAGNE.get()));
    RegistryObject<Item> VODKA = ITEMS.register("vodka", () -> new DrinkBlockItem(ModBlocks.VODKA.get()));
    RegistryObject<Item> BRANDY = ITEMS.register("brandy", () -> new DrinkBlockItem(ModBlocks.BRANDY.get()));
    RegistryObject<Item> CARIGNAN = ITEMS.register("carignan", () -> new DrinkBlockItem(ModBlocks.CARIGNAN.get()));
    RegistryObject<Item> SAKURA_WINE = ITEMS.register("sakura_wine", () -> new DrinkBlockItem(ModBlocks.SAKURA_WINE.get()));
    RegistryObject<Item> PLUM_WINE = ITEMS.register("plum_wine", () -> new DrinkBlockItem(ModBlocks.PLUM_WINE.get()));
    RegistryObject<Item> WHISKEY = ITEMS.register("whiskey", () -> new DrinkBlockItem(ModBlocks.WHISKEY.get()));
    RegistryObject<Item> ICE_WINE = ITEMS.register("ice_wine", () -> new DrinkBlockItem(ModBlocks.ICE_WINE.get()));
    RegistryObject<Item> POLARIS_SWEET_WHITE = ITEMS.register("polaris_sweet_white", () -> new DrinkBlockItem(ModBlocks.POLARIS_SWEET_WHITE.get()));
    RegistryObject<Item> HONEY_WINE = ITEMS.register("honey_wine", () -> new DrinkBlockItem(ModBlocks.HONEY_WINE.get()));
    RegistryObject<Item> RED_QUEEN = ITEMS.register("red_queen", () -> new DrinkBlockItem(ModBlocks.RED_QUEEN.get()));
    RegistryObject<Item> MINERS_STAR = ITEMS.register("miners_star", () -> new DrinkBlockItem(ModBlocks.MINERS_STAR.get()));
    RegistryObject<Item> RUM = ITEMS.register("rum", () -> new DrinkBlockItem(ModBlocks.RUM.get()));
    RegistryObject<Item> RIESLING_DRY_WHITE = ITEMS.register("riesling_dry_white", () -> new DrinkBlockItem(ModBlocks.RIESLING_DRY_WHITE.get()));
    RegistryObject<Item> SUNSET_GLOW = ITEMS.register("sunset_glow", () -> new DrinkBlockItem(ModBlocks.SUNSET_GLOW.get()));
    RegistryObject<Item> MADAME_SHEXIANG = ITEMS.register("madame_shexiang", () -> new DrinkBlockItem(ModBlocks.MADAME_SHEXIANG.get()));
    RegistryObject<Item> SWEET_BERRY_WINE = ITEMS.register("sweet_berry_wine", () -> new DrinkBlockItem(ModBlocks.SWEET_BERRY_WINE.get()));
    RegistryObject<Item> SHERRY = ITEMS.register("sherry", () -> new DrinkBlockItem(ModBlocks.SHERRY.get()));
    RegistryObject<Item> MOTHER_SNOW = ITEMS.register("mother_snow", () -> new DrinkBlockItem(ModBlocks.MOTHER_SNOW.get()));
    RegistryObject<Item> LUMINOUS_BRIDE = ITEMS.register("luminous_bride", () -> new DrinkBlockItem(ModBlocks.LUMINOUS_BRIDE.get()));
    RegistryObject<Item> GLOWFLOWER_BREW = ITEMS.register("glowflower_brew", () -> new DrinkBlockItem(ModBlocks.GLOWFLOWER_BREW.get()));
    RegistryObject<Item> SAUVIGNON_BLANC_DRY_WHITE = ITEMS.register("sauvignon_blanc_dry_white", () -> new DrinkBlockItem(ModBlocks.SAUVIGNON_BLANC_DRY_WHITE.get()));
    RegistryObject<Item> VINEGAR = ITEMS.register("vinegar", () -> new DrinkBlockItem(ModBlocks.VINEGAR.get()));
    RegistryObject<Item> WATERMELON_JUICE = ITEMS.register("watermelon_juice", () -> new DrinkBlockItem(ModBlocks.WATERMELON_JUICE.get()));

    // 香薰
    RegistryObject<Item> SAKURA_INCENSE = ITEMS.register("sakura_incense", () -> new BlockItem(ModBlocks.SAKURA_INCENSE.get(), new Item.Properties()));
    RegistryObject<Item> PINE_INCENSE = ITEMS.register("pine_incense", () -> new BlockItem(ModBlocks.PINE_INCENSE.get(), new Item.Properties()));
    RegistryObject<Item> GINKGO_INCENSE = ITEMS.register("ginkgo_incense", () -> new BlockItem(ModBlocks.GINKGO_INCENSE.get(), new Item.Properties()));
    RegistryObject<Item> SPORE_INCENSE = ITEMS.register("spore_incense", () -> new BlockItem(ModBlocks.SPORE_INCENSE.get(), new Item.Properties()));
    RegistryObject<Item> CATNIP_INCENSE = ITEMS.register("catnip_incense", () -> new BlockItem(ModBlocks.CATNIP_INCENSE.get(), new Item.Properties()));
    RegistryObject<Item> SNOW_INCENSE = ITEMS.register("snow_incense", () -> new BlockItem(ModBlocks.SNOW_INCENSE.get(), new Item.Properties()));
    RegistryObject<Item> BUTTERFLY_INCENSE = ITEMS.register("butterfly_incense", () -> new BlockItem(ModBlocks.BUTTERFLY_INCENSE.get(), new Item.Properties()));
    RegistryObject<Item> FIREFLY_INCENSE = ITEMS.register("firefly_incense", () -> new BlockItem(ModBlocks.FIREFLY_INCENSE.get(), new Item.Properties()));
}
