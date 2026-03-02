package com.github.ysbbbbbb.kaleidoscopetavern.crafting.serializer;

import com.github.ysbbbbbb.kaleidoscopetavern.api.blockentity.IPressingTub;
import com.github.ysbbbbbb.kaleidoscopetavern.crafting.recipe.PressingTubRecipe;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.material.Fluid;

import java.util.Objects;

public class PressingTubRecipeSerializer implements RecipeSerializer<PressingTubRecipe> {
    public static final int DEFAULT_FLUID_AMOUNT = IPressingTub.MAX_FLUID_AMOUNT / 8;

    private static final MapCodec<PressingTubRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Ingredient.CODEC.fieldOf("ingredient").forGetter(PressingTubRecipe::getIngredient),
            BuiltInRegistries.FLUID.byNameCodec().fieldOf("fluid").forGetter(PressingTubRecipe::getFluid),
            Codec.INT.optionalFieldOf("fluid_amount", DEFAULT_FLUID_AMOUNT).forGetter(PressingTubRecipe::getFluidAmount)
    ).apply(instance, PressingTubRecipe::new));

    private static final StreamCodec<RegistryFriendlyByteBuf, PressingTubRecipe> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public PressingTubRecipe decode(RegistryFriendlyByteBuf buf) {
            Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
            ResourceLocation fluidId = buf.readResourceLocation();
            Fluid fluid = Objects.requireNonNull(BuiltInRegistries.FLUID.get(fluidId));
            int fluidAmount = buf.readInt();
            return new PressingTubRecipe(ingredient, fluid, fluidAmount);
        }

        @Override
        public void encode(RegistryFriendlyByteBuf buf, PressingTubRecipe recipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.getIngredient());
            ResourceLocation fluidId = Objects.requireNonNull(BuiltInRegistries.FLUID.getKey(recipe.getFluid()));
            buf.writeResourceLocation(fluidId);
            buf.writeInt(recipe.getFluidAmount());
        }
    };

    @Override
    public MapCodec<PressingTubRecipe> codec() {
        return CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, PressingTubRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}
