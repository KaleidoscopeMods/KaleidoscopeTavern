package com.github.ysbbbbbb.kaleidoscopetavern.datagen.builder;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.crafting.recipe.BarrelRecipe;
import com.github.ysbbbbbb.kaleidoscopetavern.crafting.serializer.BarrelRecipeSerializer;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import com.google.common.collect.Lists;
import net.minecraft.advancements.Criterion;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BarrelBuilder implements RecipeBuilder {
    private static final String NAME = "barrel";

    private final List<Ingredient> ingredients = Lists.newArrayList();

    private Fluid fluid = Fluids.WATER;
    private Ingredient carrier = Ingredient.of(ModItems.EMPTY_BOTTLE.get());
    private ItemStack result = ItemStack.EMPTY;
    private int unitTime = BarrelRecipeSerializer.DEFAULT_UNIT_TIME;

    public static BarrelBuilder builder() {
        return new BarrelBuilder();
    }

    public BarrelBuilder addIngredient(ItemLike itemLike) {
        this.ingredients.add(Ingredient.of(itemLike));
        return this;
    }

    public BarrelBuilder addIngredient(TagKey<Item> tag) {
        this.ingredients.add(Ingredient.of(tag));
        return this;
    }

    public BarrelBuilder addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
        return this;
    }

    public BarrelBuilder setFluid(Fluid fluid) {
        this.fluid = fluid;
        return this;
    }

    public BarrelBuilder setCarrier(ItemLike itemLike) {
        this.carrier = Ingredient.of(itemLike);
        return this;
    }

    public BarrelBuilder setCarrier(TagKey<Item> tag) {
        this.carrier = Ingredient.of(tag);
        return this;
    }

    public BarrelBuilder setResult(ItemLike itemLike) {
        this.result = new ItemStack(itemLike);
        return this;
    }

    public BarrelBuilder setUnitTime(int unitTime) {
        this.unitTime = unitTime;
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
        return this.result.getItem();
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
        NonNullList<Ingredient> nullList = NonNullList.withSize(BarrelRecipeSerializer.MAX_INGREDIENTS, Ingredient.EMPTY);
        int size = Math.min(this.ingredients.size(), BarrelRecipeSerializer.MAX_INGREDIENTS);
        for (int i = 0; i < size; i++) {
            nullList.set(i, this.ingredients.get(i));
        }
        BarrelRecipe recipe = new BarrelRecipe(nullList, fluid, carrier, result, unitTime);
        recipeOutput.accept(id, recipe, null);
    }
}
