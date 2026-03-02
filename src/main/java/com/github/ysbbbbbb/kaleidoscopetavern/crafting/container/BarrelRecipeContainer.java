package com.github.ysbbbbbb.kaleidoscopetavern.crafting.container;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.IItemHandler;

public class BarrelRecipeContainer implements RecipeInput {
    private final NonNullList<ItemStack> items;
    private final Fluid fluid;

    public BarrelRecipeContainer(IItemHandler itemHandler, FluidTank fluid) {
        this.items = NonNullList.withSize(itemHandler.getSlots(), ItemStack.EMPTY);
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            this.items.set(i, itemHandler.getStackInSlot(i));
        }
        this.fluid = fluid.getFluid().getFluid();
    }

    @Override
    public ItemStack getItem(int index) {
        return items.get(index);
    }

    @Override
    public int size() {
        return items.size();
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
