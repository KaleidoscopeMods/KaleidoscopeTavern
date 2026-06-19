package com.github.ysbbbbbb.kaleidoscopetavern.datagen.builder;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.crafting.recipe.ShakerRecipe;
import com.github.ysbbbbbb.kaleidoscopetavern.crafting.serializer.ShakerRecipeSerializer;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
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
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ShakerBuilder implements RecipeBuilder {
    private static final String NAME = "shaker";

    private final List<Ingredient> ingredients = Lists.newArrayList();
    private ItemStack result = ItemStack.EMPTY;

    public static ShakerBuilder builder() {
        return new ShakerBuilder();
    }

    public ShakerBuilder addIngredient(ItemLike itemLike) {
        this.ingredients.add(Ingredient.of(itemLike));
        return this;
    }

    public ShakerBuilder addIngredient(TagKey<Item> tag) {
        this.ingredients.add(Ingredient.of(tag));
        return this;
    }

    public ShakerBuilder addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
        return this;
    }

    public ShakerBuilder setResult(ItemLike itemLike) {
        this.result = new ItemStack(itemLike);
        return this;
    }

    @Override
    public RecipeBuilder unlockedBy(String s, Criterion<?> criterion) {
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
        NonNullList<Ingredient> nullList = NonNullList.withSize(ShakerRecipeSerializer.MAX_INGREDIENTS, Ingredient.EMPTY);
        int size = Math.min(this.ingredients.size(), ShakerRecipeSerializer.MAX_INGREDIENTS);
        for (int i = 0; i < size; i++) {
            nullList.set(i, this.ingredients.get(i));
        }
        ShakerRecipe recipe = new ShakerRecipe(nullList, result, Int2ObjectMaps.emptyMap());
        recipeOutput.accept(id, recipe, null);
    }
}
