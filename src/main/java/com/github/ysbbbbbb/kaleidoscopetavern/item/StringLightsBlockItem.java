package com.github.ysbbbbbb.kaleidoscopetavern.item;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class StringLightsBlockItem extends BlockItem {
    public StringLightsBlockItem(Supplier<? extends Block> block) {
        super(block.get(), new Properties());
    }

    @Override
    public @Nullable EquipmentSlot getEquipmentSlot(ItemStack stack) {
        return EquipmentSlot.CHEST;
    }
}
