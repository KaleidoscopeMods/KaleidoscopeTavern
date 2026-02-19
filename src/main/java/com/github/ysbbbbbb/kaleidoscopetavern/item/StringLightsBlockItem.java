package com.github.ysbbbbbb.kaleidoscopetavern.item;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class StringLightsBlockItem extends BlockItem {
    public StringLightsBlockItem(RegistryObject<Block> block) {
        super(block.get(), new Properties());
    }
}

