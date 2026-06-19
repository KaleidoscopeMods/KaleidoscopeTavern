package com.github.ysbbbbbb.kaleidoscopetavern.item;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

public class GlasswareBlockItem extends BlockItem {
    public GlasswareBlockItem(Block block) {
        this(block, new Properties().stacksTo(16));
    }

    public GlasswareBlockItem(Block block, Properties properties) {
        super(block, properties);
    }
}
