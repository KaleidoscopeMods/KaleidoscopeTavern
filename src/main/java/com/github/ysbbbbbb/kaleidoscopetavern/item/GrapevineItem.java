package com.github.ysbbbbbb.kaleidoscopetavern.item;

import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;

public class GrapevineItem extends ItemNameBlockItem {
    public GrapevineItem() {
        super(ModBlocks.WILD_GRAPEVINE.get(), new Item.Properties());
    }
}
