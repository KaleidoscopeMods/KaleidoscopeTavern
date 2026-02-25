package com.github.ysbbbbbb.kaleidoscopetavern.crafting.container;

import net.minecraft.core.NonNullList;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;

import java.util.List;

public class BarrelRecipeContainer extends SimpleContainer {
    private final Fluid fluid;

    public BarrelRecipeContainer(List<ItemStack> items, Fluid fluid) {
        super(items.size());
        for (int i = 0; i < items.size(); i++) {
            this.setItem(i, items.get(i));
        }
        this.fluid = fluid;
    }

    public Fluid getFluid() {
        return fluid;
    }

    public NonNullList<ItemStack> getItems() {
        return items;
    }

    public boolean itemsIsEmpty() {
        return items.stream().allMatch(ItemStack::isEmpty);
    }
}
