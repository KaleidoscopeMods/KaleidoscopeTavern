package com.github.ysbbbbbb.kaleidoscopetavern.datagen.loottable;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.HashSet;
import java.util.Set;

public class BlockLootTables extends BlockLootSubProvider {
    public final Set<Block> knownBlocks = new HashSet<>();

    public BlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    public void generate() {
        // 沙发
        dropSelf(ModBlocks.WHITE_SOFA.get());
        dropSelf(ModBlocks.LIGHT_GRAY_SOFA.get());
        dropSelf(ModBlocks.GRAY_SOFA.get());
        dropSelf(ModBlocks.BLACK_SOFA.get());
        dropSelf(ModBlocks.BROWN_SOFA.get());
        dropSelf(ModBlocks.RED_SOFA.get());
        dropSelf(ModBlocks.ORANGE_SOFA.get());
        dropSelf(ModBlocks.YELLOW_SOFA.get());
        dropSelf(ModBlocks.LIME_SOFA.get());
        dropSelf(ModBlocks.GREEN_SOFA.get());
        dropSelf(ModBlocks.CYAN_SOFA.get());
        dropSelf(ModBlocks.LIGHT_BLUE_SOFA.get());
        dropSelf(ModBlocks.BLUE_SOFA.get());
        dropSelf(ModBlocks.PURPLE_SOFA.get());
        dropSelf(ModBlocks.MAGENTA_SOFA.get());
        dropSelf(ModBlocks.PINK_SOFA.get());

        // 高脚凳
        dropSelf(ModBlocks.WHITE_BAR_STOOL.get());
        dropSelf(ModBlocks.LIGHT_GRAY_BAR_STOOL.get());
        dropSelf(ModBlocks.GRAY_BAR_STOOL.get());
        dropSelf(ModBlocks.BLACK_BAR_STOOL.get());
        dropSelf(ModBlocks.BROWN_BAR_STOOL.get());
        dropSelf(ModBlocks.RED_BAR_STOOL.get());
        dropSelf(ModBlocks.ORANGE_BAR_STOOL.get());
        dropSelf(ModBlocks.YELLOW_BAR_STOOL.get());
        dropSelf(ModBlocks.LIME_BAR_STOOL.get());
        dropSelf(ModBlocks.GREEN_BAR_STOOL.get());
        dropSelf(ModBlocks.CYAN_BAR_STOOL.get());
        dropSelf(ModBlocks.LIGHT_BLUE_BAR_STOOL.get());
        dropSelf(ModBlocks.BLUE_BAR_STOOL.get());
        dropSelf(ModBlocks.PURPLE_BAR_STOOL.get());
        dropSelf(ModBlocks.MAGENTA_BAR_STOOL.get());
        dropSelf(ModBlocks.PINK_BAR_STOOL.get());

        // 黑板
        dropSelf(ModBlocks.CHALKBOARD.get());

        // 展板
        dropSelf(ModBlocks.BASE_SANDWICH_BOARD.get());
        dropSelf(ModBlocks.ALLIUM_SANDWICH_BOARD.get());
        dropSelf(ModBlocks.AZURE_BLUET_SANDWICH_BOARD.get());
        dropSelf(ModBlocks.CORNFLOWER_SANDWICH_BOARD.get());
        dropSelf(ModBlocks.ORCHID_SANDWICH_BOARD.get());
        dropSelf(ModBlocks.PEONY_SANDWICH_BOARD.get());
        dropSelf(ModBlocks.PINK_PETALS_SANDWICH_BOARD.get());
        dropSelf(ModBlocks.PITCHER_PLANT_SANDWICH_BOARD.get());
        dropSelf(ModBlocks.POPPY_SANDWICH_BOARD.get());
        dropSelf(ModBlocks.SUNFLOWER_SANDWICH_BOARD.get());
        dropSelf(ModBlocks.TORCHFLOWER_SANDWICH_BOARD.get());
        dropSelf(ModBlocks.TULIP_SANDWICH_BOARD.get());
        dropSelf(ModBlocks.WITHER_ROSE_SANDWICH_BOARD.get());

        // 彩灯
        dropSelf(ModBlocks.STRING_LIGHTS_COLORLESS.get());
        dropSelf(ModBlocks.STRING_LIGHTS_WHITE.get());
        dropSelf(ModBlocks.STRING_LIGHTS_LIGHT_GRAY.get());
        dropSelf(ModBlocks.STRING_LIGHTS_GRAY.get());
        dropSelf(ModBlocks.STRING_LIGHTS_BLACK.get());
        dropSelf(ModBlocks.STRING_LIGHTS_BROWN.get());
        dropSelf(ModBlocks.STRING_LIGHTS_RED.get());
        dropSelf(ModBlocks.STRING_LIGHTS_ORANGE.get());
        dropSelf(ModBlocks.STRING_LIGHTS_YELLOW.get());
        dropSelf(ModBlocks.STRING_LIGHTS_LIME.get());
        dropSelf(ModBlocks.STRING_LIGHTS_GREEN.get());
        dropSelf(ModBlocks.STRING_LIGHTS_CYAN.get());
        dropSelf(ModBlocks.STRING_LIGHTS_LIGHT_BLUE.get());
        dropSelf(ModBlocks.STRING_LIGHTS_BLUE.get());
        dropSelf(ModBlocks.STRING_LIGHTS_PURPLE.get());
        dropSelf(ModBlocks.STRING_LIGHTS_MAGENTA.get());
        dropSelf(ModBlocks.STRING_LIGHTS_PINK.get());

        // 挂画
        dropSelf(ModBlocks.YSBB_PAINTING.get());
        dropSelf(ModBlocks.TARTARIC_ACID_PAINTING.get());
        dropSelf(ModBlocks.CR019_PAINTING.get());
        dropSelf(ModBlocks.UNKNOWN_PAINTING.get());
        dropSelf(ModBlocks.MASTER_MARISA_PAINTING.get());
        dropSelf(ModBlocks.SON_OF_MAN_PAINTING.get());
        dropSelf(ModBlocks.DAVID_PAINTING.get());
        dropSelf(ModBlocks.GIRL_WITH_PEARL_EARRING_PAINTING.get());
        dropSelf(ModBlocks.STARRY_NIGHT_PAINTING.get());
        dropSelf(ModBlocks.VAN_GOGH_SELF_PORTRAIT_PAINTING.get());
        dropSelf(ModBlocks.FATHER_PAINTING.get());
        dropSelf(ModBlocks.GREAT_WAVE_PAINTING.get());
        dropSelf(ModBlocks.MONA_LISA_PAINTING.get());
        dropSelf(ModBlocks.MONDRIAN_PAINTING.get());

        // 吧台
        dropSelf(ModBlocks.BAR_COUNTER.get());
        // 人字梯
        dropSelf(ModBlocks.STEPLADDER.get());
    }

    @Override
    public void add(Block block, LootTable.Builder builder) {
        this.knownBlocks.add(block);
        super.add(block, builder);
    }

    @Override
    public Iterable<Block> getKnownBlocks() {
        return this.knownBlocks;
    }

    public ResourceLocation modLoc(String name) {
        return KaleidoscopeTavern.modLoc(name);
    }
}
