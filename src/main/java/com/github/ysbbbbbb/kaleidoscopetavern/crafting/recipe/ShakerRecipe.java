package com.github.ysbbbbbb.kaleidoscopetavern.crafting.recipe;

import com.github.ysbbbbbb.kaleidoscopetavern.crafting.container.SimpleInput;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModRecipes;
import com.github.ysbbbbbb.kaleidoscopetavern.util.ColorUtils;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.util.RecipeMatcher;
import org.jetbrains.annotations.Nullable;

public final class ShakerRecipe implements Recipe<SimpleInput> {
    private final NonNullList<Ingredient> ingredients;
    private final ItemStack result;

    private Int2ObjectMap<ChatFormatting> ingredientColors;

    public ShakerRecipe(
            NonNullList<Ingredient> ingredients,
            ItemStack result,
            Int2ObjectMap<ChatFormatting> ingredientColors
    ) {
        this.ingredients = ingredients;
        this.result = result;
        this.ingredientColors = ingredientColors;
    }

    @Override
    public boolean matches(SimpleInput input, Level level) {
        return RecipeMatcher.findMatches(input.getInputs(), ingredients) != null;
    }

    @Override
    public ItemStack assemble(SimpleInput input, HolderLookup.Provider registries) {
        return this.result.copy();
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.ingredients;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return this.result;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return false;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.SHAKER_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.SHAKER_RECIPE;
    }

    public NonNullList<Ingredient> ingredients() {
        return ingredients;
    }

    public ItemStack result() {
        return result;
    }

    public Int2ObjectMap<ChatFormatting> ingredientColors() {
        if (this.ingredientColors.equals(Int2ObjectMaps.emptyMap())) {
            this.ingredientColors = getIngredientColors(this.ingredients);
        }
        return ingredientColors;
    }

    private static Int2ObjectMap<ChatFormatting> getIngredientColors(NonNullList<Ingredient> ingredients) {
        Int2ObjectMap<ChatFormatting> ingredientColors = new Int2ObjectOpenHashMap<>();
        for (int i = 0; i < ingredients.size(); i++) {
            ChatFormatting formatting = getColor(ingredients.get(i));
            if (formatting != null) {
                ingredientColors.put(i, formatting);
            }
        }
        return ingredientColors;
    }

    @Nullable
    private static ChatFormatting getColor(Ingredient ingredient) {
        for (ItemStack stack : ingredient.getItems()) {
            for (var entry : ColorUtils.COCKTAIL_INGREDIENT_COLORS.entrySet()) {
                if (stack.is(entry.getKey())) {
                    return entry.getValue();
                }
            }
        }
        return null;
    }
}
