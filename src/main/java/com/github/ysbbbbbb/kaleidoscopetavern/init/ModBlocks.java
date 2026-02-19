package com.github.ysbbbbbb.kaleidoscopetavern.init;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.block.deco.*;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco.ChalkboardBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco.SandwichBlockEntity;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("DataFlowIssue")
public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, KaleidoscopeTavern.MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, KaleidoscopeTavern.MOD_ID);

    // 装饰部分
    // 沙发
    public static RegistryObject<Block> WHITE_SOFA = BLOCKS.register("white_sofa", SofaBlock::new);
    public static RegistryObject<Block> LIGHT_GRAY_SOFA = BLOCKS.register("light_gray_sofa", SofaBlock::new);
    public static RegistryObject<Block> GRAY_SOFA = BLOCKS.register("gray_sofa", SofaBlock::new);
    public static RegistryObject<Block> BLACK_SOFA = BLOCKS.register("black_sofa", SofaBlock::new);
    public static RegistryObject<Block> BROWN_SOFA = BLOCKS.register("brown_sofa", SofaBlock::new);
    public static RegistryObject<Block> RED_SOFA = BLOCKS.register("red_sofa", SofaBlock::new);
    public static RegistryObject<Block> ORANGE_SOFA = BLOCKS.register("orange_sofa", SofaBlock::new);
    public static RegistryObject<Block> YELLOW_SOFA = BLOCKS.register("yellow_sofa", SofaBlock::new);
    public static RegistryObject<Block> LIME_SOFA = BLOCKS.register("lime_sofa", SofaBlock::new);
    public static RegistryObject<Block> GREEN_SOFA = BLOCKS.register("green_sofa", SofaBlock::new);
    public static RegistryObject<Block> CYAN_SOFA = BLOCKS.register("cyan_sofa", SofaBlock::new);
    public static RegistryObject<Block> LIGHT_BLUE_SOFA = BLOCKS.register("light_blue_sofa", SofaBlock::new);
    public static RegistryObject<Block> BLUE_SOFA = BLOCKS.register("blue_sofa", SofaBlock::new);
    public static RegistryObject<Block> PURPLE_SOFA = BLOCKS.register("purple_sofa", SofaBlock::new);
    public static RegistryObject<Block> MAGENTA_SOFA = BLOCKS.register("magenta_sofa", SofaBlock::new);
    public static RegistryObject<Block> PINK_SOFA = BLOCKS.register("pink_sofa", SofaBlock::new);

    // 高脚凳
    public static RegistryObject<Block> WHITE_BAR_STOOL = BLOCKS.register("white_bar_stool", BarStoolBlock::new);
    public static RegistryObject<Block> LIGHT_GRAY_BAR_STOOL = BLOCKS.register("light_gray_bar_stool", BarStoolBlock::new);
    public static RegistryObject<Block> GRAY_BAR_STOOL = BLOCKS.register("gray_bar_stool", BarStoolBlock::new);
    public static RegistryObject<Block> BLACK_BAR_STOOL = BLOCKS.register("black_bar_stool", BarStoolBlock::new);
    public static RegistryObject<Block> BROWN_BAR_STOOL = BLOCKS.register("brown_bar_stool", BarStoolBlock::new);
    public static RegistryObject<Block> RED_BAR_STOOL = BLOCKS.register("red_bar_stool", BarStoolBlock::new);
    public static RegistryObject<Block> ORANGE_BAR_STOOL = BLOCKS.register("orange_bar_stool", BarStoolBlock::new);
    public static RegistryObject<Block> YELLOW_BAR_STOOL = BLOCKS.register("yellow_bar_stool", BarStoolBlock::new);
    public static RegistryObject<Block> LIME_BAR_STOOL = BLOCKS.register("lime_bar_stool", BarStoolBlock::new);
    public static RegistryObject<Block> GREEN_BAR_STOOL = BLOCKS.register("green_bar_stool", BarStoolBlock::new);
    public static RegistryObject<Block> CYAN_BAR_STOOL = BLOCKS.register("cyan_bar_stool", BarStoolBlock::new);
    public static RegistryObject<Block> LIGHT_BLUE_BAR_STOOL = BLOCKS.register("light_blue_bar_stool", BarStoolBlock::new);
    public static RegistryObject<Block> BLUE_BAR_STOOL = BLOCKS.register("blue_bar_stool", BarStoolBlock::new);
    public static RegistryObject<Block> PURPLE_BAR_STOOL = BLOCKS.register("purple_bar_stool", BarStoolBlock::new);
    public static RegistryObject<Block> MAGENTA_BAR_STOOL = BLOCKS.register("magenta_bar_stool", BarStoolBlock::new);
    public static RegistryObject<Block> PINK_BAR_STOOL = BLOCKS.register("pink_bar_stool", BarStoolBlock::new);

    // 黑板
    public static RegistryObject<Block> CHALKBOARD = BLOCKS.register("chalkboard", ChalkboardBlock::new);

    // 展板
    public static final RegistryObject<Block> BASE_SANDWICH_BOARD = BLOCKS.register("base_sandwich_board", SandwichBoardBlock::new);
    public static final RegistryObject<Block> GRASS_SANDWICH_BOARD = BLOCKS.register("grass_sandwich_board", () -> new SandwichBoardBlock(Items.GRASS));
    public static final RegistryObject<Block> ALLIUM_SANDWICH_BOARD = BLOCKS.register("allium_sandwich_board", () -> new SandwichBoardBlock(Items.ALLIUM));
    public static final RegistryObject<Block> AZURE_BLUET_SANDWICH_BOARD = BLOCKS.register("azure_bluet_sandwich_board", () -> new SandwichBoardBlock(Items.AZURE_BLUET, Items.OXEYE_DAISY, Items.LILY_OF_THE_VALLEY));
    public static final RegistryObject<Block> CORNFLOWER_SANDWICH_BOARD = BLOCKS.register("cornflower_sandwich_board", () -> new SandwichBoardBlock(Items.CORNFLOWER));
    public static final RegistryObject<Block> ORCHID_SANDWICH_BOARD = BLOCKS.register("orchid_sandwich_board", () -> new SandwichBoardBlock(Items.BLUE_ORCHID));
    public static final RegistryObject<Block> PEONY_SANDWICH_BOARD = BLOCKS.register("peony_sandwich_board", () -> new SandwichBoardBlock(Items.PEONY, Items.LILAC));
    public static final RegistryObject<Block> PINK_PETALS_SANDWICH_BOARD = BLOCKS.register("pink_petals_sandwich_board", () -> new SandwichBoardBlock(Items.PINK_PETALS));
    public static final RegistryObject<Block> PITCHER_PLANT_SANDWICH_BOARD = BLOCKS.register("pitcher_plant_sandwich_board", () -> new SandwichBoardBlock(Items.PITCHER_PLANT));
    public static final RegistryObject<Block> POPPY_SANDWICH_BOARD = BLOCKS.register("poppy_sandwich_board", () -> new SandwichBoardBlock(Items.POPPY, Items.ROSE_BUSH));
    public static final RegistryObject<Block> SUNFLOWER_SANDWICH_BOARD = BLOCKS.register("sunflower_sandwich_board", () -> new SandwichBoardBlock(Items.SUNFLOWER, Items.DANDELION));
    public static final RegistryObject<Block> TORCHFLOWER_SANDWICH_BOARD = BLOCKS.register("torchflower_sandwich_board", () -> new SandwichBoardBlock(Items.TORCHFLOWER));
    public static final RegistryObject<Block> TULIP_SANDWICH_BOARD = BLOCKS.register("tulip_sandwich_board", () -> new SandwichBoardBlock(Items.RED_TULIP, Items.ORANGE_TULIP, Items.WHITE_TULIP, Items.PINK_TULIP));
    public static final RegistryObject<Block> WITHER_ROSE_SANDWICH_BOARD = BLOCKS.register("wither_rose_sandwich_board", () -> new SandwichBoardBlock(Items.WITHER_ROSE));

    // 彩灯
    public static final RegistryObject<Block> STRING_LIGHTS_COLORLESS = BLOCKS.register("string_lights_colorless", () -> new StringLightsBlock(null));
    public static final RegistryObject<Block> STRING_LIGHTS_WHITE = BLOCKS.register("string_lights_white", () -> new StringLightsBlock(Items.WHITE_DYE));
    public static final RegistryObject<Block> STRING_LIGHTS_LIGHT_GRAY = BLOCKS.register("string_lights_light_gray", () -> new StringLightsBlock(Items.LIGHT_GRAY_DYE));
    public static final RegistryObject<Block> STRING_LIGHTS_GRAY = BLOCKS.register("string_lights_gray", () -> new StringLightsBlock(Items.GRAY_DYE));
    public static final RegistryObject<Block> STRING_LIGHTS_BLACK = BLOCKS.register("string_lights_black", () -> new StringLightsBlock(Items.BLACK_DYE));
    public static final RegistryObject<Block> STRING_LIGHTS_BROWN = BLOCKS.register("string_lights_brown", () -> new StringLightsBlock(Items.BROWN_DYE));
    public static final RegistryObject<Block> STRING_LIGHTS_RED = BLOCKS.register("string_lights_red", () -> new StringLightsBlock(Items.RED_DYE));
    public static final RegistryObject<Block> STRING_LIGHTS_ORANGE = BLOCKS.register("string_lights_orange", () -> new StringLightsBlock(Items.ORANGE_DYE));
    public static final RegistryObject<Block> STRING_LIGHTS_YELLOW = BLOCKS.register("string_lights_yellow", () -> new StringLightsBlock(Items.YELLOW_DYE));
    public static final RegistryObject<Block> STRING_LIGHTS_LIME = BLOCKS.register("string_lights_lime", () -> new StringLightsBlock(Items.LIME_DYE));
    public static final RegistryObject<Block> STRING_LIGHTS_GREEN = BLOCKS.register("string_lights_green", () -> new StringLightsBlock(Items.GREEN_DYE));
    public static final RegistryObject<Block> STRING_LIGHTS_CYAN = BLOCKS.register("string_lights_cyan", () -> new StringLightsBlock(Items.CYAN_DYE));
    public static final RegistryObject<Block> STRING_LIGHTS_LIGHT_BLUE = BLOCKS.register("string_lights_light_blue", () -> new StringLightsBlock(Items.LIGHT_BLUE_DYE));
    public static final RegistryObject<Block> STRING_LIGHTS_BLUE = BLOCKS.register("string_lights_blue", () -> new StringLightsBlock(Items.BLUE_DYE));
    public static final RegistryObject<Block> STRING_LIGHTS_PURPLE = BLOCKS.register("string_lights_purple", () -> new StringLightsBlock(Items.PURPLE_DYE));
    public static final RegistryObject<Block> STRING_LIGHTS_MAGENTA = BLOCKS.register("string_lights_magenta", () -> new StringLightsBlock(Items.MAGENTA_DYE));
    public static final RegistryObject<Block> STRING_LIGHTS_PINK = BLOCKS.register("string_lights_pink", () -> new StringLightsBlock(Items.PINK_DYE));

    // 挂画
    public static RegistryObject<Block> YSBB_PAINTING = BLOCKS.register("ysbb_painting", PaintingBlock::new);
    public static RegistryObject<Block> TARTARIC_ACID_PAINTING = BLOCKS.register("tartaric_acid_painting", PaintingBlock::new);
    public static RegistryObject<Block> CR019_PAINTING = BLOCKS.register("cr019_painting", PaintingBlock::new);
    public static RegistryObject<Block> UNKNOWN_PAINTING = BLOCKS.register("unknown_painting", PaintingBlock::new);
    public static RegistryObject<Block> MASTER_MARISA_PAINTING = BLOCKS.register("master_marisa_painting", PaintingBlock::new);
    public static RegistryObject<Block> SON_OF_MAN_PAINTING = BLOCKS.register("son_of_man_painting", PaintingBlock::new);
    public static RegistryObject<Block> DAVID_PAINTING = BLOCKS.register("david_painting", PaintingBlock::new);
    public static RegistryObject<Block> GIRL_WITH_PEARL_EARRING_PAINTING = BLOCKS.register("girl_with_pearl_earring_painting", PaintingBlock::new);
    public static RegistryObject<Block> STARRY_NIGHT_PAINTING = BLOCKS.register("starry_night_painting", PaintingBlock::new);
    public static RegistryObject<Block> VAN_GOGH_SELF_PORTRAIT_PAINTING = BLOCKS.register("van_gogh_self_portrait_painting", PaintingBlock::new);
    public static RegistryObject<Block> FATHER_PAINTING = BLOCKS.register("father_painting", PaintingBlock::new);
    public static RegistryObject<Block> GREAT_WAVE_PAINTING = BLOCKS.register("great_wave_painting", PaintingBlock::new);
    public static RegistryObject<Block> MONA_LISA_PAINTING = BLOCKS.register("mona_lisa_painting", PaintingBlock::new);
    public static RegistryObject<Block> MONDRIAN_PAINTING = BLOCKS.register("mondrian_painting", PaintingBlock::new);

    // 吧台
    public static RegistryObject<Block> BAR_COUNTER = BLOCKS.register("bar_counter", BarCounterBlock::new);


    // BlockEntity
    public static RegistryObject<BlockEntityType<ChalkboardBlockEntity>> CHALKBOARD_BE = BLOCK_ENTITIES.register(
            "chalkboard", () -> BlockEntityType.Builder
                    .of(ChalkboardBlockEntity::new, CHALKBOARD.get())
                    .build(null)
    );

    public static RegistryObject<BlockEntityType<SandwichBlockEntity>> SANDWICH_BOARD_BE = BLOCK_ENTITIES.register(
            "sandwich_board", () -> BlockEntityType.Builder.of(SandwichBlockEntity::new,
                    BASE_SANDWICH_BOARD.get(),
                    GRASS_SANDWICH_BOARD.get(),
                    ALLIUM_SANDWICH_BOARD.get(),
                    AZURE_BLUET_SANDWICH_BOARD.get(),
                    CORNFLOWER_SANDWICH_BOARD.get(),
                    ORCHID_SANDWICH_BOARD.get(),
                    PEONY_SANDWICH_BOARD.get(),
                    PINK_PETALS_SANDWICH_BOARD.get(),
                    PITCHER_PLANT_SANDWICH_BOARD.get(),
                    POPPY_SANDWICH_BOARD.get(),
                    SUNFLOWER_SANDWICH_BOARD.get(),
                    TORCHFLOWER_SANDWICH_BOARD.get(),
                    TULIP_SANDWICH_BOARD.get(),
                    WITHER_ROSE_SANDWICH_BOARD.get()
            ).build(null)
    );
}