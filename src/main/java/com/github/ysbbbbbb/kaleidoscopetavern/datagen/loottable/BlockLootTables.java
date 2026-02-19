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
