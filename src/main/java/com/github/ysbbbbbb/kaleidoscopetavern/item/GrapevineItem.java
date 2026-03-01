package com.github.ysbbbbbb.kaleidoscopetavern.item;

import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.Nullable;

public class GrapevineItem extends ItemNameBlockItem {
    public GrapevineItem() {
        super(ModBlocks.WILD_GRAPEVINE.get(), new Item.Properties());
    }

    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        // 野生葡萄藤的燃烧时间为200 ticks（10秒），与木棍接近
        return 200;
    }
}
