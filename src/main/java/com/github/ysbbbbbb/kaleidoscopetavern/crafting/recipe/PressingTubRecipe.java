package com.github.ysbbbbbb.kaleidoscopetavern.crafting.recipe;

import com.github.ysbbbbbb.kaleidoscopetavern.init.ModRecipes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SingleItemRecipe;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import org.apache.commons.lang3.StringUtils;

public class PressingTubRecipe extends SingleItemRecipe {
    private final Fluid fluid;
    private final int fluidAmount;

    public PressingTubRecipe(Ingredient ingredient, Fluid fluid, int fluidAmount) {
        super(ModRecipes.PRESSING_TUB_RECIPE, ModRecipes.PRESSING_TUB_SERIALIZER.get(),
                StringUtils.EMPTY, ingredient, fluid.getBucket().getDefaultInstance());
        this.fluid = fluid;
        this.fluidAmount = fluidAmount;
    }

    @Override
    public boolean matches(SingleRecipeInput input, Level level) {
        return this.ingredient.test(input.getItem(0));
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
