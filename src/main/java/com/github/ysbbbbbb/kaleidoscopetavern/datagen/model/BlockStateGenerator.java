package com.github.ysbbbbbb.kaleidoscopetavern.datagen.model;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.block.deco.SandwichBoardBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.block.deco.SofaBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.block.deco.StepladderBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.block.properties.ConnectionType;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class BlockStateGenerator extends BlockStateProvider {
    public BlockStateGenerator(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, KaleidoscopeTavern.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        // 沙发
        sofa(ModBlocks.WHITE_SOFA, "white");
        sofa(ModBlocks.LIGHT_GRAY_SOFA, "light_gray");
        sofa(ModBlocks.GRAY_SOFA, "gray");
        sofa(ModBlocks.BLACK_SOFA, "black");
        sofa(ModBlocks.BROWN_SOFA, "brown");
        sofa(ModBlocks.RED_SOFA, "red");
        sofa(ModBlocks.ORANGE_SOFA, "orange");
        sofa(ModBlocks.YELLOW_SOFA, "yellow");
        sofa(ModBlocks.LIME_SOFA, "lime");
        sofa(ModBlocks.GREEN_SOFA, "green");
        sofa(ModBlocks.CYAN_SOFA, "cyan");
        sofa(ModBlocks.LIGHT_BLUE_SOFA, "light_blue");
        sofa(ModBlocks.BLUE_SOFA, "blue");
        sofa(ModBlocks.PURPLE_SOFA, "purple");
        sofa(ModBlocks.MAGENTA_SOFA, "magenta");
        sofa(ModBlocks.PINK_SOFA, "pink");

        // 高脚凳
        barStool(ModBlocks.WHITE_BAR_STOOL, "white");
        barStool(ModBlocks.LIGHT_GRAY_BAR_STOOL, "light_gray");
        barStool(ModBlocks.GRAY_BAR_STOOL, "gray");
        barStool(ModBlocks.BLACK_BAR_STOOL, "black");
        barStool(ModBlocks.BROWN_BAR_STOOL, "brown");
        barStool(ModBlocks.RED_BAR_STOOL, "red");
        barStool(ModBlocks.ORANGE_BAR_STOOL, "orange");
        barStool(ModBlocks.YELLOW_BAR_STOOL, "yellow");
        barStool(ModBlocks.LIME_BAR_STOOL, "lime");
        barStool(ModBlocks.GREEN_BAR_STOOL, "green");
        barStool(ModBlocks.CYAN_BAR_STOOL, "cyan");
        barStool(ModBlocks.LIGHT_BLUE_BAR_STOOL, "light_blue");
        barStool(ModBlocks.BLUE_BAR_STOOL, "blue");
        barStool(ModBlocks.PURPLE_BAR_STOOL, "purple");
        barStool(ModBlocks.MAGENTA_BAR_STOOL, "magenta");
        barStool(ModBlocks.PINK_BAR_STOOL, "pink");

        // 展板
        sandwichBoard(ModBlocks.BASE_SANDWICH_BOARD, "base");
        sandwichBoard(ModBlocks.GRASS_SANDWICH_BOARD, "grass");
        sandwichBoard(ModBlocks.ALLIUM_SANDWICH_BOARD, "allium");
        sandwichBoard(ModBlocks.AZURE_BLUET_SANDWICH_BOARD, "azure_bluet");
        sandwichBoard(ModBlocks.CORNFLOWER_SANDWICH_BOARD, "cornflower");
        sandwichBoard(ModBlocks.ORCHID_SANDWICH_BOARD, "orchid");
        sandwichBoard(ModBlocks.PEONY_SANDWICH_BOARD, "peony");
        sandwichBoard(ModBlocks.PINK_PETALS_SANDWICH_BOARD, "pink_petals");
        sandwichBoard(ModBlocks.PITCHER_PLANT_SANDWICH_BOARD, "pitcher_plant");
        sandwichBoard(ModBlocks.POPPY_SANDWICH_BOARD, "poppy");
        sandwichBoard(ModBlocks.SUNFLOWER_SANDWICH_BOARD, "sunflower");
        sandwichBoard(ModBlocks.TORCHFLOWER_SANDWICH_BOARD, "torchflower");
        sandwichBoard(ModBlocks.TULIP_SANDWICH_BOARD, "tulip");
        sandwichBoard(ModBlocks.WITHER_ROSE_SANDWICH_BOARD, "wither_rose");

        // 彩灯
        stringLights(ModBlocks.STRING_LIGHTS_COLORLESS, "colorless");
        stringLights(ModBlocks.STRING_LIGHTS_WHITE, "white");
        stringLights(ModBlocks.STRING_LIGHTS_LIGHT_GRAY, "light_gray");
        stringLights(ModBlocks.STRING_LIGHTS_GRAY, "gray");
        stringLights(ModBlocks.STRING_LIGHTS_BLACK, "black");
        stringLights(ModBlocks.STRING_LIGHTS_BROWN, "brown");
        stringLights(ModBlocks.STRING_LIGHTS_RED, "red");
        stringLights(ModBlocks.STRING_LIGHTS_ORANGE, "orange");
        stringLights(ModBlocks.STRING_LIGHTS_YELLOW, "yellow");
        stringLights(ModBlocks.STRING_LIGHTS_LIME, "lime");
        stringLights(ModBlocks.STRING_LIGHTS_GREEN, "green");
        stringLights(ModBlocks.STRING_LIGHTS_CYAN, "cyan");
        stringLights(ModBlocks.STRING_LIGHTS_LIGHT_BLUE, "light_blue");
        stringLights(ModBlocks.STRING_LIGHTS_BLUE, "blue");
        stringLights(ModBlocks.STRING_LIGHTS_PURPLE, "purple");
        stringLights(ModBlocks.STRING_LIGHTS_MAGENTA, "magenta");
        stringLights(ModBlocks.STRING_LIGHTS_PINK, "pink");

        // 挂画
        painting(ModBlocks.YSBB_PAINTING, "ysbb");
        painting(ModBlocks.TARTARIC_ACID_PAINTING, "tartaric_acid");
        painting(ModBlocks.CR019_PAINTING, "cr019");
        painting(ModBlocks.UNKNOWN_PAINTING, "unknown");
        painting(ModBlocks.MASTER_MARISA_PAINTING, "master_marisa");
        painting(ModBlocks.SON_OF_MAN_PAINTING, "son_of_man");
        painting(ModBlocks.DAVID_PAINTING, "david");
        painting(ModBlocks.GIRL_WITH_PEARL_EARRING_PAINTING, "girl_with_pearl_earring");
        painting(ModBlocks.STARRY_NIGHT_PAINTING, "starry_night");
        painting(ModBlocks.VAN_GOGH_SELF_PORTRAIT_PAINTING, "van_gogh_self_portrait");
        painting(ModBlocks.FATHER_PAINTING, "father");
        painting(ModBlocks.GREAT_WAVE_PAINTING, "great_wave");
        painting(ModBlocks.MONA_LISA_PAINTING, "mona_lisa");
        painting(ModBlocks.MONDRIAN_PAINTING, "mondrian");

        // 吧台
        barCounter(ModBlocks.BAR_COUNTER);
        // 人字梯
        stepladder(ModBlocks.STEPLADDER);
    }

    private void sofa(RegistryObject<Block> block, String color) {
        horizontalBlock(block.get(), blockState -> {
            ConnectionType connection = blockState.getValue(SofaBlock.CONNECTION);
            String type = connection.getSerializedName();
            ResourceLocation file = modLoc("block/deco/sofa/%s/%s".formatted(color, type));
            return new ModelFile.UncheckedModelFile(file);
        });
    }

    private void barStool(RegistryObject<Block> block, String color) {
        horizontalBlock(block.get(), blockState -> {
            ResourceLocation file = modLoc("block/deco/bar_stool/%s".formatted(color));
            return new ModelFile.UncheckedModelFile(file);
        });
    }

    private void sandwichBoard(RegistryObject<Block> block, String type) {
        horizontalBlock(block.get(), blockState -> {
            Half half = blockState.getValue(SandwichBoardBlock.HALF);
            if (half == Half.BOTTOM) {
                return new ModelFile.UncheckedModelFile(modLoc("block/deco/sandwich_board/base"));
            } else {
                return new ModelFile.UncheckedModelFile(modLoc("block/deco/sandwich_board/%s_top".formatted(type)));
            }
        });
    }

    private void stringLights(RegistryObject<Block> block, String color) {
        horizontalBlock(block.get(), blockState -> {
            ResourceLocation file = modLoc("block/deco/string_lights/%s".formatted(color));
            return new ModelFile.UncheckedModelFile(file);
        });
    }

    private void painting(RegistryObject<Block> block, String type) {
        horizontalFaceBlock(block.get(), blockState -> {
            ResourceLocation file = modLoc("block/deco/painting/%s".formatted(type));
            return new ModelFile.UncheckedModelFile(file);
        });
    }

    private void barCounter(RegistryObject<Block> block) {
        horizontalBlock(block.get(), blockState -> {
            ConnectionType connection = blockState.getValue(SofaBlock.CONNECTION);
            String type = connection.getSerializedName();
            ResourceLocation file = modLoc("block/deco/bar_counter/%s".formatted(type));
            return new ModelFile.UncheckedModelFile(file);
        });
    }

    private void stepladder(RegistryObject<Block> block) {
        horizontalBlock(block.get(), blockState -> {
            Half half = blockState.getValue(StepladderBlock.HALF);
            if (half == Half.BOTTOM) {
                return new ModelFile.UncheckedModelFile(modLoc("block/deco/stepladder/bottom"));
            } else {
                return new ModelFile.UncheckedModelFile(modLoc("block/deco/stepladder/top"));
            }
        });
    }
}
