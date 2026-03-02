package com.github.ysbbbbbb.kaleidoscopetavern.item;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class PaintingBlockItem extends BlockItem {
    public PaintingBlockItem(Supplier<? extends Block> block) {
        super(block.get(), new Properties());
    }
}
