package com.github.ysbbbbbb.kaleidoscopetavern.crafting.serializer;

import com.github.ysbbbbbb.kaleidoscopetavern.crafting.recipe.ShakerRecipe;
import com.github.ysbbbbbb.kaleidoscopetavern.util.ColorUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;
import org.jetbrains.annotations.Nullable;

public class ShakerRecipeSerializer implements RecipeSerializer<ShakerRecipe> {
    public static final int MAX_INGREDIENTS = 3;

    @Override
    public ShakerRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
        NonNullList<Ingredient> ingredients = NonNullList.withSize(MAX_INGREDIENTS, Ingredient.EMPTY);
        Int2ObjectMap<ChatFormatting> ingredientColors = new Int2ObjectOpenHashMap<>();

        JsonArray ingredientsJson = GsonHelper.getAsJsonArray(json, "ingredients", new JsonArray());        
        for (int i = 0; i < ingredientsJson.size(); i++) {
            // 最大支持 3 个原料，超过部分将被忽略
            if (i >= MAX_INGREDIENTS) {
                break;
            }
            JsonElement element = ingredientsJson.get(i);
            ingredients.set(i, Ingredient.fromJson(element));

            ChatFormatting formatting = getColor(element);
            if (formatting != null) {
                ingredientColors.put(i, formatting);
            }
        }

        JsonObject resultJson = GsonHelper.getAsJsonObject(json, "result");
        ItemStack result = CraftingHelper.getItemStack(resultJson, true, true);
        return new ShakerRecipe(recipeId, ingredients, result, ingredientColors);
    }

    @Nullable
    private static ChatFormatting getColor(JsonElement element) {
        // 检查是否是颜色 tag，是的话存入
        if (!element.isJsonObject()) {
            return null;
        }
        JsonObject jsonObject = element.getAsJsonObject();
        String tag = GsonHelper.getAsString(jsonObject, "tag", null);
        if (tag == null) {
            return null;
        }
        ResourceLocation location = new ResourceLocation(tag);
        TagKey<Item> tagKey = TagKey.create(Registries.ITEM, location);
        return ColorUtils.COCKTAIL_INGREDIENT_COLORS.get(tagKey);
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

        Int2ObjectMap<ChatFormatting> ingredientColors = new Int2ObjectOpenHashMap<>();
        int colorSize = buffer.readVarInt();
        for (int i = 0; i < colorSize; i++) {
            int index = buffer.readVarInt();
            int color = buffer.readVarInt();
            ChatFormatting formatting = ChatFormatting.getById(color);
            ingredientColors.put(index, formatting);
        }
        return new ShakerRecipe(recipeId, ingredients, result, ingredientColors);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, ShakerRecipe recipe) {
        buffer.writeVarInt(recipe.ingredients().size());
        for (Ingredient ingredient : recipe.ingredients()) {
            ingredient.toNetwork(buffer);
        }

        buffer.writeItem(recipe.result());

        Int2ObjectMap<ChatFormatting> ingredientColors = recipe.ingredientColors();
        buffer.writeVarInt(ingredientColors.size());
        ingredientColors.forEach((index, formatting) -> {
            buffer.writeVarInt(index);
            buffer.writeVarInt(formatting.getId());
        });
    }
}
