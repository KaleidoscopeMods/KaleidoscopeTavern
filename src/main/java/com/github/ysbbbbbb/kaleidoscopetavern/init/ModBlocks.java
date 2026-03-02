package com.github.ysbbbbbb.kaleidoscopetavern.init;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.block.brew.*;
import com.github.ysbbbbbb.kaleidoscopetavern.block.deco.*;
import com.github.ysbbbbbb.kaleidoscopetavern.block.plant.*;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.brew.*;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco.ChalkboardBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco.SandwichBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.phys.shapes.Shapes;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

@SuppressWarnings("DataFlowIssue")
public interface ModBlocks {
    DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(KaleidoscopeTavern.MOD_ID);
    DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, KaleidoscopeTavern.MOD_ID);

    // 装饰部分
    // 沙发
    DeferredBlock<Block> WHITE_SOFA = BLOCKS.register("white_sofa", SofaBlock::new);
    DeferredBlock<Block> LIGHT_GRAY_SOFA = BLOCKS.register("light_gray_sofa", SofaBlock::new);
    DeferredBlock<Block> GRAY_SOFA = BLOCKS.register("gray_sofa", SofaBlock::new);
    DeferredBlock<Block> BLACK_SOFA = BLOCKS.register("black_sofa", SofaBlock::new);
    DeferredBlock<Block> BROWN_SOFA = BLOCKS.register("brown_sofa", SofaBlock::new);
    DeferredBlock<Block> RED_SOFA = BLOCKS.register("red_sofa", SofaBlock::new);
    DeferredBlock<Block> ORANGE_SOFA = BLOCKS.register("orange_sofa", SofaBlock::new);
    DeferredBlock<Block> YELLOW_SOFA = BLOCKS.register("yellow_sofa", SofaBlock::new);
    DeferredBlock<Block> LIME_SOFA = BLOCKS.register("lime_sofa", SofaBlock::new);
    DeferredBlock<Block> GREEN_SOFA = BLOCKS.register("green_sofa", SofaBlock::new);
    DeferredBlock<Block> CYAN_SOFA = BLOCKS.register("cyan_sofa", SofaBlock::new);
    DeferredBlock<Block> LIGHT_BLUE_SOFA = BLOCKS.register("light_blue_sofa", SofaBlock::new);
    DeferredBlock<Block> BLUE_SOFA = BLOCKS.register("blue_sofa", SofaBlock::new);
    DeferredBlock<Block> PURPLE_SOFA = BLOCKS.register("purple_sofa", SofaBlock::new);
    DeferredBlock<Block> MAGENTA_SOFA = BLOCKS.register("magenta_sofa", SofaBlock::new);
    DeferredBlock<Block> PINK_SOFA = BLOCKS.register("pink_sofa", SofaBlock::new);

    // 高脚凳
    DeferredBlock<Block> WHITE_BAR_STOOL = BLOCKS.register("white_bar_stool", BarStoolBlock::new);
    DeferredBlock<Block> LIGHT_GRAY_BAR_STOOL = BLOCKS.register("light_gray_bar_stool", BarStoolBlock::new);
    DeferredBlock<Block> GRAY_BAR_STOOL = BLOCKS.register("gray_bar_stool", BarStoolBlock::new);
    DeferredBlock<Block> BLACK_BAR_STOOL = BLOCKS.register("black_bar_stool", BarStoolBlock::new);
    DeferredBlock<Block> BROWN_BAR_STOOL = BLOCKS.register("brown_bar_stool", BarStoolBlock::new);
    DeferredBlock<Block> RED_BAR_STOOL = BLOCKS.register("red_bar_stool", BarStoolBlock::new);
    DeferredBlock<Block> ORANGE_BAR_STOOL = BLOCKS.register("orange_bar_stool", BarStoolBlock::new);
    DeferredBlock<Block> YELLOW_BAR_STOOL = BLOCKS.register("yellow_bar_stool", BarStoolBlock::new);
    DeferredBlock<Block> LIME_BAR_STOOL = BLOCKS.register("lime_bar_stool", BarStoolBlock::new);
    DeferredBlock<Block> GREEN_BAR_STOOL = BLOCKS.register("green_bar_stool", BarStoolBlock::new);
    DeferredBlock<Block> CYAN_BAR_STOOL = BLOCKS.register("cyan_bar_stool", BarStoolBlock::new);
    DeferredBlock<Block> LIGHT_BLUE_BAR_STOOL = BLOCKS.register("light_blue_bar_stool", BarStoolBlock::new);
    DeferredBlock<Block> BLUE_BAR_STOOL = BLOCKS.register("blue_bar_stool", BarStoolBlock::new);
    DeferredBlock<Block> PURPLE_BAR_STOOL = BLOCKS.register("purple_bar_stool", BarStoolBlock::new);
    DeferredBlock<Block> MAGENTA_BAR_STOOL = BLOCKS.register("magenta_bar_stool", BarStoolBlock::new);
    DeferredBlock<Block> PINK_BAR_STOOL = BLOCKS.register("pink_bar_stool", BarStoolBlock::new);

    // 黑板
    DeferredBlock<Block> CHALKBOARD = BLOCKS.register("chalkboard", ChalkboardBlock::new);

    // 展板
    DeferredBlock<Block> BASE_SANDWICH_BOARD = BLOCKS.register("base_sandwich_board", () -> new SandwichBoardBlock());
    DeferredBlock<Block> GRASS_SANDWICH_BOARD = BLOCKS.register("grass_sandwich_board", () -> new SandwichBoardBlock(Items.SHORT_GRASS));
    DeferredBlock<Block> ALLIUM_SANDWICH_BOARD = BLOCKS.register("allium_sandwich_board", () -> new SandwichBoardBlock(Items.ALLIUM));
    DeferredBlock<Block> AZURE_BLUET_SANDWICH_BOARD = BLOCKS.register("azure_bluet_sandwich_board", () -> new SandwichBoardBlock(Items.AZURE_BLUET, Items.OXEYE_DAISY, Items.LILY_OF_THE_VALLEY));
    DeferredBlock<Block> CORNFLOWER_SANDWICH_BOARD = BLOCKS.register("cornflower_sandwich_board", () -> new SandwichBoardBlock(Items.CORNFLOWER));
    DeferredBlock<Block> ORCHID_SANDWICH_BOARD = BLOCKS.register("orchid_sandwich_board", () -> new SandwichBoardBlock(Items.BLUE_ORCHID));
    DeferredBlock<Block> PEONY_SANDWICH_BOARD = BLOCKS.register("peony_sandwich_board", () -> new SandwichBoardBlock(Items.PEONY, Items.LILAC));
    DeferredBlock<Block> PINK_PETALS_SANDWICH_BOARD = BLOCKS.register("pink_petals_sandwich_board", () -> new SandwichBoardBlock(Items.PINK_PETALS));
    DeferredBlock<Block> PITCHER_PLANT_SANDWICH_BOARD = BLOCKS.register("pitcher_plant_sandwich_board", () -> new SandwichBoardBlock(Items.PITCHER_PLANT));
    DeferredBlock<Block> POPPY_SANDWICH_BOARD = BLOCKS.register("poppy_sandwich_board", () -> new SandwichBoardBlock(Items.POPPY, Items.ROSE_BUSH));
    DeferredBlock<Block> SUNFLOWER_SANDWICH_BOARD = BLOCKS.register("sunflower_sandwich_board", () -> new SandwichBoardBlock(Items.SUNFLOWER, Items.DANDELION));
    DeferredBlock<Block> TORCHFLOWER_SANDWICH_BOARD = BLOCKS.register("torchflower_sandwich_board", () -> new SandwichBoardBlock(Items.TORCHFLOWER));
    DeferredBlock<Block> TULIP_SANDWICH_BOARD = BLOCKS.register("tulip_sandwich_board", () -> new SandwichBoardBlock(Items.RED_TULIP, Items.ORANGE_TULIP, Items.WHITE_TULIP, Items.PINK_TULIP));
    DeferredBlock<Block> WITHER_ROSE_SANDWICH_BOARD = BLOCKS.register("wither_rose_sandwich_board", () -> new SandwichBoardBlock(Items.WITHER_ROSE));

    // 彩灯
    DeferredBlock<Block> STRING_LIGHTS_COLORLESS = BLOCKS.register("string_lights_colorless", () -> new StringLightsBlock(null));
    DeferredBlock<Block> STRING_LIGHTS_WHITE = BLOCKS.register("string_lights_white", () -> new StringLightsBlock(Items.WHITE_DYE));
    DeferredBlock<Block> STRING_LIGHTS_LIGHT_GRAY = BLOCKS.register("string_lights_light_gray", () -> new StringLightsBlock(Items.LIGHT_GRAY_DYE));
    DeferredBlock<Block> STRING_LIGHTS_GRAY = BLOCKS.register("string_lights_gray", () -> new StringLightsBlock(Items.GRAY_DYE));
    DeferredBlock<Block> STRING_LIGHTS_BLACK = BLOCKS.register("string_lights_black", () -> new StringLightsBlock(Items.BLACK_DYE));
    DeferredBlock<Block> STRING_LIGHTS_BROWN = BLOCKS.register("string_lights_brown", () -> new StringLightsBlock(Items.BROWN_DYE));
    DeferredBlock<Block> STRING_LIGHTS_RED = BLOCKS.register("string_lights_red", () -> new StringLightsBlock(Items.RED_DYE));
    DeferredBlock<Block> STRING_LIGHTS_ORANGE = BLOCKS.register("string_lights_orange", () -> new StringLightsBlock(Items.ORANGE_DYE));
    DeferredBlock<Block> STRING_LIGHTS_YELLOW = BLOCKS.register("string_lights_yellow", () -> new StringLightsBlock(Items.YELLOW_DYE));
    DeferredBlock<Block> STRING_LIGHTS_LIME = BLOCKS.register("string_lights_lime", () -> new StringLightsBlock(Items.LIME_DYE));
    DeferredBlock<Block> STRING_LIGHTS_GREEN = BLOCKS.register("string_lights_green", () -> new StringLightsBlock(Items.GREEN_DYE));
    DeferredBlock<Block> STRING_LIGHTS_CYAN = BLOCKS.register("string_lights_cyan", () -> new StringLightsBlock(Items.CYAN_DYE));
    DeferredBlock<Block> STRING_LIGHTS_LIGHT_BLUE = BLOCKS.register("string_lights_light_blue", () -> new StringLightsBlock(Items.LIGHT_BLUE_DYE));
    DeferredBlock<Block> STRING_LIGHTS_BLUE = BLOCKS.register("string_lights_blue", () -> new StringLightsBlock(Items.BLUE_DYE));
    DeferredBlock<Block> STRING_LIGHTS_PURPLE = BLOCKS.register("string_lights_purple", () -> new StringLightsBlock(Items.PURPLE_DYE));
    DeferredBlock<Block> STRING_LIGHTS_MAGENTA = BLOCKS.register("string_lights_magenta", () -> new StringLightsBlock(Items.MAGENTA_DYE));
    DeferredBlock<Block> STRING_LIGHTS_PINK = BLOCKS.register("string_lights_pink", () -> new StringLightsBlock(Items.PINK_DYE));

    // 挂画
    DeferredBlock<Block> YSBB_PAINTING = BLOCKS.register("ysbb_painting", PaintingBlock::new);
    DeferredBlock<Block> TARTARIC_ACID_PAINTING = BLOCKS.register("tartaric_acid_painting", PaintingBlock::new);
    DeferredBlock<Block> CR019_PAINTING = BLOCKS.register("cr019_painting", PaintingBlock::new);
    DeferredBlock<Block> UNKNOWN_PAINTING = BLOCKS.register("unknown_painting", PaintingBlock::new);
    DeferredBlock<Block> MASTER_MARISA_PAINTING = BLOCKS.register("master_marisa_painting", PaintingBlock::new);
    DeferredBlock<Block> SON_OF_MAN_PAINTING = BLOCKS.register("son_of_man_painting", PaintingBlock::new);
    DeferredBlock<Block> DAVID_PAINTING = BLOCKS.register("david_painting", PaintingBlock::new);
    DeferredBlock<Block> GIRL_WITH_PEARL_EARRING_PAINTING = BLOCKS.register("girl_with_pearl_earring_painting", PaintingBlock::new);
    DeferredBlock<Block> STARRY_NIGHT_PAINTING = BLOCKS.register("starry_night_painting", PaintingBlock::new);
    DeferredBlock<Block> VAN_GOGH_SELF_PORTRAIT_PAINTING = BLOCKS.register("van_gogh_self_portrait_painting", PaintingBlock::new);
    DeferredBlock<Block> FATHER_PAINTING = BLOCKS.register("father_painting", PaintingBlock::new);
    DeferredBlock<Block> GREAT_WAVE_PAINTING = BLOCKS.register("great_wave_painting", PaintingBlock::new);
    DeferredBlock<Block> MONA_LISA_PAINTING = BLOCKS.register("mona_lisa_painting", PaintingBlock::new);
    DeferredBlock<Block> MONDRIAN_PAINTING = BLOCKS.register("mondrian_painting", PaintingBlock::new);

    // 吧台
    DeferredBlock<Block> BAR_COUNTER = BLOCKS.register("bar_counter", BarCounterBlock::new);
    // 人字梯
    DeferredBlock<Block> STEPLADDER = BLOCKS.register("stepladder", StepladderBlock::new);
    // 野生葡萄藤
    DeferredBlock<Block> WILD_GRAPEVINE = BLOCKS.register("wild_grapevine", WildGrapevineBlock::new);
    DeferredBlock<Block> WILD_GRAPEVINE_PLANT = BLOCKS.register("wild_grapevine_plant", WildGrapevinePlantBlock::new);
    // 藤架
    DeferredBlock<Block> TRELLIS = BLOCKS.register("trellis", TrellisBlock::new);
    // 葡萄藤
    DeferredBlock<Block> GRAPEVINE_TRELLIS = BLOCKS.register("grapevine_trellis", GrapevineTrellisBlock::new);
    // 葡萄
    DeferredBlock<Block> GRAPE_CROP = BLOCKS.register("grape_crop", GrapeCropBlock::new);

    // 果盆
    DeferredBlock<Block> PRESSING_TUB = BLOCKS.register("pressing_tub", PressingTubBlock::new);
    // 龙头
    DeferredBlock<Block> TAP = BLOCKS.register("tap", TapBlock::new);
    // 空瓶
    DeferredBlock<Block> EMPTY_BOTTLE = BLOCKS.register("empty_bottle", () -> new BottleBlock());
    // 燃烧瓶
    DeferredBlock<Block> MOLOTOV = BLOCKS.register("molotov", MolotovBlock::new);
    // 酒桶
    DeferredBlock<Block> BARREL = BLOCKS.register("barrel", BarrelBlock::new);
    // 酒柜
    DeferredBlock<Block> BAR_CABINET = BLOCKS.register("bar_cabinet", BarCabinetBlock::new);
    DeferredBlock<Block> GLASS_BAR_CABINET = BLOCKS.register("glass_bar_cabinet", BarCabinetBlock::new);

    // 酒
    DeferredBlock<Block> WINE = BLOCKS.register("wine", DrinkBlock.create().maxCount(4).shapes(
            Block.box(6, 0, 6, 10, 16, 10),
            Block.box(2, 0, 6, 14, 16, 10),
            Shapes.or(
                    Block.box(2, 0, 10, 14, 16, 14),
                    Block.box(6, 0, 2, 10, 16, 14)
            ),
            Block.box(2, 0, 2, 14, 16, 14)
    ).build());

    DeferredBlock<Block> CHAMPAGNE = BLOCKS.register("champagne", DrinkBlock.create().maxCount(4).shapes(
            Block.box(6, 0, 6, 10, 16, 10),
            Block.box(2, 0, 6, 14, 16, 10),
            Shapes.or(
                    Block.box(2, 0, 10, 14, 16, 14),
                    Block.box(6, 0, 2, 10, 16, 14)
            ),
            Block.box(2, 0, 2, 14, 16, 14)
    ).build());

    DeferredBlock<Block> VODKA = BLOCKS.register("vodka", DrinkBlock.create().maxCount(4).shapes(
            Block.box(4, 0, 4, 12, 15, 12),
            Block.box(0, 0, 4, 16, 15, 12),
            Shapes.or(
                    Block.box(0, 0, 8, 16, 15, 16),
                    Block.box(4, 0, 0, 12, 15, 16)
            ),
            Block.box(0, 0, 0, 16, 16, 16)
    ).build());

    DeferredBlock<Block> BRANDY = BLOCKS.register("brandy", DrinkBlock.create().irregular().maxCount(3).shapes(
            Block.box(3, 0, 6, 13, 13, 10),
            Block.box(1, 0, 3, 15, 12, 12),
            Block.box(1, 0, 1, 16, 12, 13)
    ).build());

    DeferredBlock<Block> CARIGNAN = BLOCKS.register("carignan", DrinkBlock.create().irregular().maxCount(3).shapes(
            Block.box(3, 0, 6, 13, 12, 10),
            Block.box(1, 0, 3, 15, 12, 12),
            Block.box(0, 0, 1, 16, 12, 13)
    ).build());

    DeferredBlock<Block> SAKURA_WINE = BLOCKS.register("sakura_wine", DrinkBlock.create().maxCount(4).shapes(
            Block.box(6, 0, 6, 10, 16, 10),
            Block.box(2, 0, 6, 14, 16, 10),
            Shapes.or(
                    Block.box(2, 0, 10, 14, 16, 14),
                    Block.box(6, 0, 2, 10, 16, 14)
            ),
            Block.box(2, 0, 2, 14, 16, 14)
    ).build());

    DeferredBlock<Block> PLUM_WINE = BLOCKS.register("plum_wine", DrinkBlock.create().maxCount(4).shapes(
            Block.box(6, 0, 6, 10, 12, 10),
            Block.box(3, 0, 6, 13, 12, 10),
            Shapes.or(
                    Block.box(3, 0, 9, 13, 12, 13),
                    Block.box(6, 0, 3, 10, 12, 13)
            ),
            Block.box(3, 0, 3, 13, 12, 13)
    ).build());

    DeferredBlock<Block> WHISKEY = BLOCKS.register("whiskey", DrinkBlock.create().maxCount(4).shapes(
            Block.box(6, 0, 6, 10, 16, 10),
            Block.box(2, 0, 6, 14, 16, 10),
            Shapes.or(
                    Block.box(2, 0, 10, 14, 16, 14),
                    Block.box(6, 0, 2, 10, 16, 14)
            ),
            Block.box(2, 0, 2, 14, 16, 14)
    ).build());

    DeferredBlock<Block> ICE_WINE = BLOCKS.register("ice_wine", DrinkBlock.create().maxCount(4).shapes(
            Block.box(6, 0, 6, 10, 16, 10),
            Block.box(2, 0, 6, 14, 16, 10),
            Shapes.or(
                    Block.box(2, 0, 10, 14, 16, 14),
                    Block.box(6, 0, 2, 10, 16, 14)
            ),
            Block.box(2, 0, 2, 14, 16, 14)
    ).build());

    DeferredBlock<Block> VINEGAR = BLOCKS.register("vinegar", DrinkBlock.create().maxCount(4).shapes(
            Block.box(6, 0, 6, 10, 16, 10),
            Block.box(2, 0, 6, 14, 16, 10),
            Shapes.or(
                    Block.box(2, 0, 10, 14, 16, 14),
                    Block.box(6, 0, 2, 10, 16, 14)
            ),
            Block.box(2, 0, 2, 14, 16, 14)
    ).build());

    // BlockEntity
    Supplier<BlockEntityType<ChalkboardBlockEntity>> CHALKBOARD_BE = BLOCK_ENTITIES.register(
            "chalkboard", () -> BlockEntityType.Builder
                    .of(ChalkboardBlockEntity::new, CHALKBOARD.get())
                    .build(null)
    );

    Supplier<BlockEntityType<SandwichBlockEntity>> SANDWICH_BOARD_BE = BLOCK_ENTITIES.register(
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

    Supplier<BlockEntityType<PressingTubBlockEntity>> PRESSING_TUB_BE = BLOCK_ENTITIES.register(
            "pressing_tub", () -> BlockEntityType.Builder
                    .of(PressingTubBlockEntity::new, PRESSING_TUB.get())
                    .build(null)
    );

    Supplier<BlockEntityType<DrinkBlockEntity>> DRINK_BE = BLOCK_ENTITIES.register(
            "drink", () -> BlockEntityType.Builder
                    .of(DrinkBlockEntity::new,
                            WINE.get(), CHAMPAGNE.get(), VODKA.get(), BRANDY.get(), CARIGNAN.get(),
                            SAKURA_WINE.get(), PLUM_WINE.get(), WHISKEY.get(), ICE_WINE.get(), VINEGAR.get()
                    ).build(null)
    );

    Supplier<BlockEntityType<BarrelBlockEntity>> BARREL_BE = BLOCK_ENTITIES.register(
            "barrel", () -> BlockEntityType.Builder
                    .of(BarrelBlockEntity::new, BARREL.get())
                    .build(null)
    );

    Supplier<BlockEntityType<TapBlockEntity>> TAP_BE = BLOCK_ENTITIES.register(
            "tap", () -> BlockEntityType.Builder
                    .of(TapBlockEntity::new, TAP.get())
                    .build(null)
    );

    Supplier<BlockEntityType<BarCabinetBlockEntity>> BAR_CABINET_BE = BLOCK_ENTITIES.register(
            "bar_cabinet", () -> BlockEntityType.Builder
                    .of(BarCabinetBlockEntity::new, BAR_CABINET.get(), GLASS_BAR_CABINET.get())
                    .build(null)
    );
}