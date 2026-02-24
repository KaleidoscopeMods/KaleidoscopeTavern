package com.github.ysbbbbbb.kaleidoscopetavern.crafting.recipe;

import com.github.ysbbbbbb.kaleidoscopetavern.init.ModRecipes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SingleItemRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import org.apache.commons.lang3.StringUtils;

public class PressingTubRecipe extends SingleItemRecipe {
    /**
     * 输出的流体种类，默认为水
     */
    private final Fluid fluid;
    /**
     * 输出的流体数量，默认 125 mB，相当于一个标准桶的八分之一
     */
    private final int fluidAmount;

    public PressingTubRecipe(ResourceLocation id, Ingredient ingredient,
                             Fluid fluid, int fluidAmount) {
        super(ModRecipes.PRESSING_TUB_RECIPE, ModRecipes.PRESSING_TUB_SERIALIZER.get(),
                id, StringUtils.EMPTY, ingredient, fluid.getBucket().getDefaultInstance());
        this.fluid = fluid;
        this.fluidAmount = fluidAmount;
    }

    @Override
    public boolean matches(Container inv, Level level) {
        return this.ingredient.test(inv.getItem(0));
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public Ingredient getIngredient() {
        return this.ingredient;
    }

    public ItemStack getResult() {
        return this.result;
    }

    public Fluid getFluid() {
        return fluid;
    }

    public int getFluidAmount() {
        return fluidAmount;
    }
}
