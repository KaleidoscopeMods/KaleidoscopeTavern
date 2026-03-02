package com.github.ysbbbbbb.kaleidoscopetavern.datagen.builder;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.crafting.recipe.PressingTubRecipe;
import com.github.ysbbbbbb.kaleidoscopetavern.crafting.serializer.PressingTubRecipeSerializer;
import net.minecraft.advancements.Criterion;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

public class PressingTubBuilder implements RecipeBuilder {
    private static final String NAME = "pressing_tub";

    private Ingredient ingredient = Ingredient.EMPTY;
    private Fluid fluid = Fluids.WATER;
    private int fluidAmount = PressingTubRecipeSerializer.DEFAULT_FLUID_AMOUNT;

    public static PressingTubBuilder builder() {
        return new PressingTubBuilder();
    }

    public PressingTubBuilder setIngredient(ItemLike itemLike) {
        this.ingredient = Ingredient.of(itemLike);
        return this;
    }

    public PressingTubBuilder setIngredient(TagKey<Item> itemLike) {
        this.ingredient = Ingredient.of(itemLike);
        return this;
    }

    public PressingTubBuilder setFluid(Fluid fluid) {
        this.fluid = fluid;
        return this;
    }

    public PressingTubBuilder setFluidAmount(int amount) {
        this.fluidAmount = amount;
        return this;
    }

    @Override
    public RecipeBuilder unlockedBy(String name, Criterion<?> trigger) {
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String groupName) {
        return this;
    }

    @Override
    public Item getResult() {
        return this.fluid.getBucket();
    }

    @Override
    public void save(RecipeOutput output) {
        String path = RecipeBuilder.getDefaultRecipeId(this.getResult()).getPath();
        ResourceLocation filePath = KaleidoscopeTavern.modLoc(NAME + "/" + path);
        this.save(output, filePath);
    }

    @Override
    public void save(RecipeOutput output, String recipeId) {
        ResourceLocation filePath = KaleidoscopeTavern.modLoc(NAME + "/" + recipeId);
        this.save(output, filePath);
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation id) {
        PressingTubRecipe recipe = new PressingTubRecipe(this.ingredient, this.fluid, this.fluidAmount);
        recipeOutput.accept(id, recipe, null);
    }
}
