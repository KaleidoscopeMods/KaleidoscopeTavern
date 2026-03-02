package com.github.ysbbbbbb.kaleidoscopetavern.item;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class BarStoolBlockItem extends BlockItem {
    public BarStoolBlockItem(Supplier<? extends Block> block) {
        super(block.get(), new Item.Properties());
    }
}
