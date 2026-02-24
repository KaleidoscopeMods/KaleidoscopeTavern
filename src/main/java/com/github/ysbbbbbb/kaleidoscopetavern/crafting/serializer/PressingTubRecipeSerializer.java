package com.github.ysbbbbbb.kaleidoscopetavern.crafting.serializer;

import com.github.ysbbbbbb.kaleidoscopetavern.api.blockentity.IPressingTub;
import com.github.ysbbbbbb.kaleidoscopetavern.crafting.recipe.PressingTubRecipe;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

import static net.minecraft.util.GsonHelper.getAsJsonArray;
import static net.minecraft.util.GsonHelper.getAsJsonObject;

public class PressingTubRecipeSerializer implements RecipeSerializer<PressingTubRecipe> {
    public static final ResourceLocation DEFAULT_LIQUID_ID = new ResourceLocation("minecraft", "water");
    public static final int DEFAULT_LIQUID_AMOUNT = IPressingTub.MAX_LIQUID_AMOUNT / 8;

    @Override
    public PressingTubRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
        Ingredient ingredient;
        if (GsonHelper.isArrayNode(json, "ingredient")) {
            ingredient = Ingredient.fromJson(getAsJsonArray(json, "ingredient"), false);
        } else {
            ingredient = Ingredient.fromJson(getAsJsonObject(json, "ingredient"), false);
        }

        ResourceLocation fluidId = new ResourceLocation(GsonHelper.getAsString(json, "fluid", DEFAULT_LIQUID_ID.toString()));
        Fluid fluid = ForgeRegistries.FLUIDS.getValue(fluidId);
        if (fluid == null) {
            throw new JsonParseException("Unknown fluid: " + fluidId);
        }

        int fluidAmount = GsonHelper.getAsInt(json, "fluid_amount", DEFAULT_LIQUID_AMOUNT);

        return new PressingTubRecipe(recipeId, ingredient, fluid, fluidAmount);
    }

    @Override
    public PressingTubRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
        Ingredient ingredient = Ingredient.fromNetwork(buffer);
        ResourceLocation fluidId = buffer.readResourceLocation();
        Fluid fluid = Objects.requireNonNull(ForgeRegistries.FLUIDS.getValue(fluidId));
        int fluidAmount = buffer.readInt();
        return new PressingTubRecipe(recipeId, ingredient, fluid, fluidAmount);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, PressingTubRecipe recipe) {
        recipe.getIngredient().toNetwork(buffer);
        ResourceLocation fluidId = Objects.requireNonNull(ForgeRegistries.FLUIDS.getKey(recipe.getFluid()));
        buffer.writeResourceLocation(fluidId);
        buffer.writeInt(recipe.getFluidAmount());
    }
}
