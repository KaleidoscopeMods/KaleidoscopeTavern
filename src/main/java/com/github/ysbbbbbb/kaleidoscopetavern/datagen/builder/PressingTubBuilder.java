package com.github.ysbbbbbb.kaleidoscopetavern.datagen.builder;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.crafting.serializer.PressingTubRecipeSerializer;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModRecipes;
import com.google.gson.JsonObject;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Consumer;

public class PressingTubBuilder implements RecipeBuilder {
    private static final String NAME = "pressing_tub";

    private Ingredient ingredient = Ingredient.EMPTY;
    private Fluid fluid = Fluids.WATER;
    private int fluidAmount = PressingTubRecipeSerializer.DEFAULT_LIQUID_AMOUNT;

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
    public RecipeBuilder unlockedBy(String name, CriterionTriggerInstance trigger) {
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
    public void save(Consumer<FinishedRecipe> output) {
        String path = RecipeBuilder.getDefaultRecipeId(this.getResult()).getPath();
        ResourceLocation filePath = KaleidoscopeTavern.modLoc(NAME + "/" + path);
        this.save(output, filePath);
    }

    @Override
    public void save(Consumer<FinishedRecipe> output, String recipeId) {
        ResourceLocation filePath = KaleidoscopeTavern.modLoc(NAME + "/" + recipeId);
        this.save(output, filePath);
    }

    @Override
    public void save(Consumer<FinishedRecipe> recipeOutput, ResourceLocation id) {
        recipeOutput.accept(new PressingTubFinishedRecipe(id, this.ingredient, this.fluid, this.fluidAmount));
    }

    public static class PressingTubFinishedRecipe implements FinishedRecipe {
        private final ResourceLocation id;
        private final Ingredient ingredient;
        private final Fluid fluid;
        private final int fluidAmount;

        public PressingTubFinishedRecipe(ResourceLocation id, Ingredient ingredient, Fluid fluid, int fluidAmount) {
            this.id = id;
            this.ingredient = ingredient;
            this.fluid = fluid;
            this.fluidAmount = fluidAmount;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            json.add("ingredient", this.ingredient.toJson());
            ResourceLocation fluidId = Objects.requireNonNull(ForgeRegistries.FLUIDS.getKey(this.fluid));
            json.addProperty("fluid", fluidId.toString());
            json.addProperty("fluid_amount", this.fluidAmount);
        }

        @Override
        public ResourceLocation getId() {
            return this.id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return ModRecipes.PRESSING_TUB_SERIALIZER.get();
        }

        @Override
        @Nullable
        public JsonObject serializeAdvancement() {
            return null;
        }

        @Override
        @Nullable
        public ResourceLocation getAdvancementId() {
            return null;
        }
    }
}
