package com.github.ysbbbbbb.kaleidoscopetavern.item;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class SandwichBoardBlockItem extends BlockItem {
    public SandwichBoardBlockItem(Supplier<? extends Block> block) {
        super(block.get(), new Properties());
    }
}
