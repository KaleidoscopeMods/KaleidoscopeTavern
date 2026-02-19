package com.github.ysbbbbbb.kaleidoscopetavern.init;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.item.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, KaleidoscopeTavern.MOD_ID);

    // 沙发
    public static RegistryObject<Item> WHITE_SOFA = ITEMS.register("white_sofa", () -> new SofaBlockItem(ModBlocks.WHITE_SOFA));
    public static RegistryObject<Item> LIGHT_GRAY_SOFA = ITEMS.register("light_gray_sofa", () -> new SofaBlockItem(ModBlocks.LIGHT_GRAY_SOFA));
    public static RegistryObject<Item> GRAY_SOFA = ITEMS.register("gray_sofa", () -> new SofaBlockItem(ModBlocks.GRAY_SOFA));
    public static RegistryObject<Item> BLACK_SOFA = ITEMS.register("black_sofa", () -> new SofaBlockItem(ModBlocks.BLACK_SOFA));
    public static RegistryObject<Item> BROWN_SOFA = ITEMS.register("brown_sofa", () -> new SofaBlockItem(ModBlocks.BROWN_SOFA));
    public static RegistryObject<Item> RED_SOFA = ITEMS.register("red_sofa", () -> new SofaBlockItem(ModBlocks.RED_SOFA));
    public static RegistryObject<Item> ORANGE_SOFA = ITEMS.register("orange_sofa", () -> new SofaBlockItem(ModBlocks.ORANGE_SOFA));
    public static RegistryObject<Item> YELLOW_SOFA = ITEMS.register("yellow_sofa", () -> new SofaBlockItem(ModBlocks.YELLOW_SOFA));
    public static RegistryObject<Item> LIME_SOFA = ITEMS.register("lime_sofa", () -> new SofaBlockItem(ModBlocks.LIME_SOFA));
    public static RegistryObject<Item> GREEN_SOFA = ITEMS.register("green_sofa", () -> new SofaBlockItem(ModBlocks.GREEN_SOFA));
    public static RegistryObject<Item> CYAN_SOFA = ITEMS.register("cyan_sofa", () -> new SofaBlockItem(ModBlocks.CYAN_SOFA));
    public static RegistryObject<Item> LIGHT_BLUE_SOFA = ITEMS.register("light_blue_sofa", () -> new SofaBlockItem(ModBlocks.LIGHT_BLUE_SOFA));
    public static RegistryObject<Item> BLUE_SOFA = ITEMS.register("blue_sofa", () -> new SofaBlockItem(ModBlocks.BLUE_SOFA));
    public static RegistryObject<Item> PURPLE_SOFA = ITEMS.register("purple_sofa", () -> new SofaBlockItem(ModBlocks.PURPLE_SOFA));
    public static RegistryObject<Item> MAGENTA_SOFA = ITEMS.register("magenta_sofa", () -> new SofaBlockItem(ModBlocks.MAGENTA_SOFA));
    public static RegistryObject<Item> PINK_SOFA = ITEMS.register("pink_sofa", () -> new SofaBlockItem(ModBlocks.PINK_SOFA));

    // 高脚凳
    public static RegistryObject<Item> WHITE_BAR_STOOL = ITEMS.register("white_bar_stool", () -> new BarStoolBlockItem(ModBlocks.WHITE_BAR_STOOL));
    public static RegistryObject<Item> LIGHT_GRAY_BAR_STOOL = ITEMS.register("light_gray_bar_stool", () -> new BarStoolBlockItem(ModBlocks.LIGHT_GRAY_BAR_STOOL));
    public static RegistryObject<Item> GRAY_BAR_STOOL = ITEMS.register("gray_bar_stool", () -> new BarStoolBlockItem(ModBlocks.GRAY_BAR_STOOL));
    public static RegistryObject<Item> BLACK_BAR_STOOL = ITEMS.register("black_bar_stool", () -> new BarStoolBlockItem(ModBlocks.BLACK_BAR_STOOL));
    public static RegistryObject<Item> BROWN_BAR_STOOL = ITEMS.register("brown_bar_stool", () -> new BarStoolBlockItem(ModBlocks.BROWN_BAR_STOOL));
    public static RegistryObject<Item> RED_BAR_STOOL = ITEMS.register("red_bar_stool", () -> new BarStoolBlockItem(ModBlocks.RED_BAR_STOOL));
    public static RegistryObject<Item> ORANGE_BAR_STOOL = ITEMS.register("orange_bar_stool", () -> new BarStoolBlockItem(ModBlocks.ORANGE_BAR_STOOL));
    public static RegistryObject<Item> YELLOW_BAR_STOOL = ITEMS.register("yellow_bar_stool", () -> new BarStoolBlockItem(ModBlocks.YELLOW_BAR_STOOL));
    public static RegistryObject<Item> LIME_BAR_STOOL = ITEMS.register("lime_bar_stool", () -> new BarStoolBlockItem(ModBlocks.LIME_BAR_STOOL));
    public static RegistryObject<Item> GREEN_BAR_STOOL = ITEMS.register("green_bar_stool", () -> new BarStoolBlockItem(ModBlocks.GREEN_BAR_STOOL));
    public static RegistryObject<Item> CYAN_BAR_STOOL = ITEMS.register("cyan_bar_stool", () -> new BarStoolBlockItem(ModBlocks.CYAN_BAR_STOOL));
    public static RegistryObject<Item> LIGHT_BLUE_BAR_STOOL = ITEMS.register("light_blue_bar_stool", () -> new BarStoolBlockItem(ModBlocks.LIGHT_BLUE_BAR_STOOL));
    public static RegistryObject<Item> BLUE_BAR_STOOL = ITEMS.register("blue_bar_stool", () -> new BarStoolBlockItem(ModBlocks.BLUE_BAR_STOOL));
    public static RegistryObject<Item> PURPLE_BAR_STOOL = ITEMS.register("purple_bar_stool", () -> new BarStoolBlockItem(ModBlocks.PURPLE_BAR_STOOL));
    public static RegistryObject<Item> MAGENTA_BAR_STOOL = ITEMS.register("magenta_bar_stool", () -> new BarStoolBlockItem(ModBlocks.MAGENTA_BAR_STOOL));
    public static RegistryObject<Item> PINK_BAR_STOOL = ITEMS.register("pink_bar_stool", () -> new BarStoolBlockItem(ModBlocks.PINK_BAR_STOOL));

    // 黑板
    public static RegistryObject<Item> CHALKBOARD = ITEMS.register("chalkboard", () -> new BlockItem(ModBlocks.CHALKBOARD.get(), new Item.Properties()));

    // 展板
    public static RegistryObject<Item> BASE_SANDWICH_BOARD = ITEMS.register("base_sandwich_board", () -> new SandwichBoardBlockItem(ModBlocks.BASE_SANDWICH_BOARD));
    public static RegistryObject<Item> GRASS_SANDWICH_BOARD = ITEMS.register("grass_sandwich_board", () -> new SandwichBoardBlockItem(ModBlocks.GRASS_SANDWICH_BOARD));
    public static RegistryObject<Item> ALLIUM_SANDWICH_BOARD = ITEMS.register("allium_sandwich_board", () -> new SandwichBoardBlockItem(ModBlocks.ALLIUM_SANDWICH_BOARD));
    public static RegistryObject<Item> AZURE_BLUET_SANDWICH_BOARD = ITEMS.register("azure_bluet_sandwich_board", () -> new SandwichBoardBlockItem(ModBlocks.AZURE_BLUET_SANDWICH_BOARD));
    public static RegistryObject<Item> CORNFLOWER_SANDWICH_BOARD = ITEMS.register("cornflower_sandwich_board", () -> new SandwichBoardBlockItem(ModBlocks.CORNFLOWER_SANDWICH_BOARD));
    public static RegistryObject<Item> ORCHID_SANDWICH_BOARD = ITEMS.register("orchid_sandwich_board", () -> new SandwichBoardBlockItem(ModBlocks.ORCHID_SANDWICH_BOARD));
    public static RegistryObject<Item> PEONY_SANDWICH_BOARD = ITEMS.register("peony_sandwich_board", () -> new SandwichBoardBlockItem(ModBlocks.PEONY_SANDWICH_BOARD));
    public static RegistryObject<Item> PINK_PETALS_SANDWICH_BOARD = ITEMS.register("pink_petals_sandwich_board", () -> new SandwichBoardBlockItem(ModBlocks.PINK_PETALS_SANDWICH_BOARD));
    public static RegistryObject<Item> PITCHER_PLANT_SANDWICH_BOARD = ITEMS.register("pitcher_plant_sandwich_board", () -> new SandwichBoardBlockItem(ModBlocks.PITCHER_PLANT_SANDWICH_BOARD));
    public static RegistryObject<Item> POPPY_SANDWICH_BOARD = ITEMS.register("poppy_sandwich_board", () -> new SandwichBoardBlockItem(ModBlocks.POPPY_SANDWICH_BOARD));
    public static RegistryObject<Item> SUNFLOWER_SANDWICH_BOARD = ITEMS.register("sunflower_sandwich_board", () -> new SandwichBoardBlockItem(ModBlocks.SUNFLOWER_SANDWICH_BOARD));
    public static RegistryObject<Item> TORCHFLOWER_SANDWICH_BOARD = ITEMS.register("torchflower_sandwich_board", () -> new SandwichBoardBlockItem(ModBlocks.TORCHFLOWER_SANDWICH_BOARD));
    public static RegistryObject<Item> TULIP_SANDWICH_BOARD = ITEMS.register("tulip_sandwich_board", () -> new SandwichBoardBlockItem(ModBlocks.TULIP_SANDWICH_BOARD));
    public static RegistryObject<Item> WITHER_ROSE_SANDWICH_BOARD = ITEMS.register("wither_rose_sandwich_board", () -> new SandwichBoardBlockItem(ModBlocks.WITHER_ROSE_SANDWICH_BOARD));

    // 彩灯
    public static RegistryObject<Item> STRING_LIGHTS_COLORLESS = ITEMS.register("string_lights_colorless", () -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_COLORLESS));
    public static RegistryObject<Item> STRING_LIGHTS_WHITE = ITEMS.register("string_lights_white", () -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_WHITE));
    public static RegistryObject<Item> STRING_LIGHTS_LIGHT_GRAY = ITEMS.register("string_lights_light_gray", () -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_LIGHT_GRAY));
    public static RegistryObject<Item> STRING_LIGHTS_GRAY = ITEMS.register("string_lights_gray", () -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_GRAY));
    public static RegistryObject<Item> STRING_LIGHTS_BLACK = ITEMS.register("string_lights_black", () -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_BLACK));
    public static RegistryObject<Item> STRING_LIGHTS_BROWN = ITEMS.register("string_lights_brown", () -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_BROWN));
    public static RegistryObject<Item> STRING_LIGHTS_RED = ITEMS.register("string_lights_red", () -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_RED));
    public static RegistryObject<Item> STRING_LIGHTS_ORANGE = ITEMS.register("string_lights_orange", () -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_ORANGE));
    public static RegistryObject<Item> STRING_LIGHTS_YELLOW = ITEMS.register("string_lights_yellow", () -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_YELLOW));
    public static RegistryObject<Item> STRING_LIGHTS_LIME = ITEMS.register("string_lights_lime", () -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_LIME));
    public static RegistryObject<Item> STRING_LIGHTS_GREEN = ITEMS.register("string_lights_green", () -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_GREEN));
    public static RegistryObject<Item> STRING_LIGHTS_CYAN = ITEMS.register("string_lights_cyan", () -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_CYAN));
    public static RegistryObject<Item> STRING_LIGHTS_LIGHT_BLUE = ITEMS.register("string_lights_light_blue", () -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_LIGHT_BLUE));
    public static RegistryObject<Item> STRING_LIGHTS_BLUE = ITEMS.register("string_lights_blue", () -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_BLUE));
    public static RegistryObject<Item> STRING_LIGHTS_PURPLE = ITEMS.register("string_lights_purple", () -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_PURPLE));
    public static RegistryObject<Item> STRING_LIGHTS_MAGENTA = ITEMS.register("string_lights_magenta", () -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_MAGENTA));
    public static RegistryObject<Item> STRING_LIGHTS_PINK = ITEMS.register("string_lights_pink", () -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_PINK));

    // 挂画
    public static RegistryObject<Item> YSBB_PAINTING = ITEMS.register("ysbb_painting", () -> new PaintingBlockItem(ModBlocks.YSBB_PAINTING));
    public static RegistryObject<Item> TARTARIC_ACID_PAINTING = ITEMS.register("tartaric_acid_painting", () -> new PaintingBlockItem(ModBlocks.TARTARIC_ACID_PAINTING));
    public static RegistryObject<Item> CR019_PAINTING = ITEMS.register("cr019_painting", () -> new PaintingBlockItem(ModBlocks.CR019_PAINTING));
    public static RegistryObject<Item> UNKNOWN_PAINTING = ITEMS.register("unknown_painting", () -> new PaintingBlockItem(ModBlocks.UNKNOWN_PAINTING));
    public static RegistryObject<Item> MASTER_MARISA_PAINTING = ITEMS.register("master_marisa_painting", () -> new PaintingBlockItem(ModBlocks.MASTER_MARISA_PAINTING));
    public static RegistryObject<Item> SON_OF_MAN_PAINTING = ITEMS.register("son_of_man_painting", () -> new PaintingBlockItem(ModBlocks.SON_OF_MAN_PAINTING));
    public static RegistryObject<Item> DAVID_PAINTING = ITEMS.register("david_painting", () -> new PaintingBlockItem(ModBlocks.DAVID_PAINTING));
    public static RegistryObject<Item> GIRL_WITH_PEARL_EARRING_PAINTING = ITEMS.register("girl_with_pearl_earring_painting", () -> new PaintingBlockItem(ModBlocks.GIRL_WITH_PEARL_EARRING_PAINTING));
    public static RegistryObject<Item> STARRY_NIGHT_PAINTING = ITEMS.register("starry_night_painting", () -> new PaintingBlockItem(ModBlocks.STARRY_NIGHT_PAINTING));
    public static RegistryObject<Item> VAN_GOGH_SELF_PORTRAIT_PAINTING = ITEMS.register("van_gogh_self_portrait_painting", () -> new PaintingBlockItem(ModBlocks.VAN_GOGH_SELF_PORTRAIT_PAINTING));
    public static RegistryObject<Item> FATHER_PAINTING = ITEMS.register("father_painting", () -> new PaintingBlockItem(ModBlocks.FATHER_PAINTING));
    public static RegistryObject<Item> GREAT_WAVE_PAINTING = ITEMS.register("great_wave_painting", () -> new PaintingBlockItem(ModBlocks.GREAT_WAVE_PAINTING));
    public static RegistryObject<Item> MONA_LISA_PAINTING = ITEMS.register("mona_lisa_painting", () -> new PaintingBlockItem(ModBlocks.MONA_LISA_PAINTING));
    public static RegistryObject<Item> MONDRIAN_PAINTING = ITEMS.register("mondrian_painting", () -> new PaintingBlockItem(ModBlocks.MONDRIAN_PAINTING));

    // 吧台
    public static RegistryObject<Item> BAR_COUNTER = ITEMS.register("bar_counter", () -> new BlockItem(ModBlocks.BAR_COUNTER.get(), new Item.Properties()));
    // 人字梯
    public static RegistryObject<Item> STEPLADDER = ITEMS.register("stepladder", () -> new BlockItem(ModBlocks.STEPLADDER.get(), new Item.Properties()));
}
