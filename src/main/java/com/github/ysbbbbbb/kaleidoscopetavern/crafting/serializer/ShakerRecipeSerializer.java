package com.github.ysbbbbbb.kaleidoscopetavern.crafting.serializer;

import com.github.ysbbbbbb.kaleidoscopetavern.crafting.recipe.ShakerRecipe;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;
import org.jetbrains.annotations.Nullable;

public class ShakerRecipeSerializer implements RecipeSerializer<ShakerRecipe> {
    public static final int MAX_INGREDIENTS = 3;

    @Override
    public ShakerRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
        JsonArray ingredientsJson = GsonHelper.getAsJsonArray(json, "ingredients", new JsonArray());
        NonNullList<Ingredient> ingredients = NonNullList.withSize(MAX_INGREDIENTS, Ingredient.EMPTY);
        for (int i = 0; i < ingredientsJson.size(); i++) {
            // 最大支持 3 个原料，超过部分将被忽略
            if (i >= MAX_INGREDIENTS) {
                break;
            }
            ingredients.set(i, Ingredient.fromJson(ingredientsJson.get(i)));
        }

        JsonObject resultJson = GsonHelper.getAsJsonObject(json, "result");
        ItemStack result = CraftingHelper.getItemStack(resultJson, true, true);
        return new ShakerRecipe(recipeId, ingredients, result);
    }

    @Override
    @Nullable
    public ShakerRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
        int size = Math.min(MAX_INGREDIENTS, buffer.readVarInt());
        NonNullList<Ingredient> ingredients = NonNullList.withSize(size, Ingredient.EMPTY);
        for (int i = 0; i < size; i++) {
            ingredients.set(i, Ingredient.fromNetwork(buffer));
        }
        ItemStack result = buffer.readItem();
        return new ShakerRecipe(recipeId, ingredients, result);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, ShakerRecipe recipe) {
        buffer.writeVarInt(recipe.ingredients().size());
        for (Ingredient ingredient : recipe.ingredients()) {
            ingredient.toNetwork(buffer);
        }
        buffer.writeItem(recipe.result());
    }
}
