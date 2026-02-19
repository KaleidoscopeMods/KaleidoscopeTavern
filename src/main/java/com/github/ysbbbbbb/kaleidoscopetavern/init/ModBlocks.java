package com.github.ysbbbbbb.kaleidoscopetavern.init;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.block.deco.BarStoolBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.block.deco.ChalkboardBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.block.deco.SandwichBoardBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.block.deco.SofaBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco.ChalkboardBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco.SandwichBlockEntity;
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
    public static RegistryObject<Block> BASE_SANDWICH_BOARD = BLOCKS.register("base_sandwich_board", SandwichBoardBlock::new);
    public static RegistryObject<Block> ALLIUM_SANDWICH_BOARD = BLOCKS.register("allium_sandwich_board", SandwichBoardBlock::new);
    public static RegistryObject<Block> AZURE_BLUET_SANDWICH_BOARD = BLOCKS.register("azure_bluet_sandwich_board", SandwichBoardBlock::new);
    public static RegistryObject<Block> CORNFLOWER_SANDWICH_BOARD = BLOCKS.register("cornflower_sandwich_board", SandwichBoardBlock::new);
    public static RegistryObject<Block> ORCHID_SANDWICH_BOARD = BLOCKS.register("orchid_sandwich_board", SandwichBoardBlock::new);
    public static RegistryObject<Block> PEONY_SANDWICH_BOARD = BLOCKS.register("peony_sandwich_board", SandwichBoardBlock::new);
    public static RegistryObject<Block> PINK_PETALS_SANDWICH_BOARD = BLOCKS.register("pink_petals_sandwich_board", SandwichBoardBlock::new);
    public static RegistryObject<Block> PITCHER_PLANT_SANDWICH_BOARD = BLOCKS.register("pitcher_plant_sandwich_board", SandwichBoardBlock::new);
    public static RegistryObject<Block> POPPY_SANDWICH_BOARD = BLOCKS.register("poppy_sandwich_board", SandwichBoardBlock::new);
    public static RegistryObject<Block> SUNFLOWER_SANDWICH_BOARD = BLOCKS.register("sunflower_sandwich_board", SandwichBoardBlock::new);
    public static RegistryObject<Block> TORCHFLOWER_SANDWICH_BOARD = BLOCKS.register("torchflower_sandwich_board", SandwichBoardBlock::new);
    public static RegistryObject<Block> TULIP_SANDWICH_BOARD = BLOCKS.register("tulip_sandwich_board", SandwichBoardBlock::new);
    public static RegistryObject<Block> WITHER_ROSE_SANDWICH_BOARD = BLOCKS.register("wither_rose_sandwich_board", SandwichBoardBlock::new);


    // BlockEntity
    public static RegistryObject<BlockEntityType<ChalkboardBlockEntity>> CHALKBOARD_BE = BLOCK_ENTITIES.register(
            "chalkboard", () -> BlockEntityType.Builder
                    .of(ChalkboardBlockEntity::new, CHALKBOARD.get())
                    .build(null)
    );

    public static RegistryObject<BlockEntityType<SandwichBlockEntity>> SANDWICH_BOARD_BE = BLOCK_ENTITIES.register(
            "sandwich_board", () -> BlockEntityType.Builder.of(SandwichBlockEntity::new,
                    BASE_SANDWICH_BOARD.get(),
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