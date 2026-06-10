package com.github.ysbbbbbb.kaleidoscopetavern.crafting.recipe;

import com.github.ysbbbbbb.kaleidoscopetavern.init.ModRecipes;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.RecipeMatcher;

public record ShakerRecipe(
        ResourceLocation id,
        NonNullList<Ingredient> ingredients,
        ItemStack result,
        Int2ObjectMap<ChatFormatting> ingredientColors
) implements Recipe<SimpleContainer> {
    @Override
    public boolean matches(SimpleContainer container, Level level) {
        return RecipeMatcher.findMatches(container.items, ingredients) != null;
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer, RegistryAccess pRegistryAccess) {
        return this.result.copy();
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.ingredients;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return this.result;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return false;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.SHAKER_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.SHAKER_RECIPE;
    }
}
