package com.github.ysbbbbbb.kaleidoscopetavern.crafting.serializer;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.crafting.recipe.BarrelRecipe;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.material.Fluid;

import java.util.Objects;

public class BarrelRecipeSerializer implements RecipeSerializer<BarrelRecipe> {
    public static final ResourceLocation EMPTY_RECIPE_ID = KaleidoscopeTavern.modLoc("empty");
    public static final int DEFAULT_UNIT_TIME = 2400;
    public static final int MAX_INGREDIENTS = 4;

    private static final MapCodec<BarrelRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Ingredient.CODEC.listOf().xmap(
                    list -> {
                        NonNullList<Ingredient> nonnull = NonNullList.withSize(MAX_INGREDIENTS, Ingredient.EMPTY);
                        for (int i = 0; i < Math.min(list.size(), MAX_INGREDIENTS); i++) {
                            nonnull.set(i, list.get(i));
                        }
                        return nonnull;
                    },
                    nonnull -> nonnull.stream().filter(i -> !i.isEmpty()).toList()
            ).optionalFieldOf("ingredients", NonNullList.withSize(MAX_INGREDIENTS, Ingredient.EMPTY)).forGetter(BarrelRecipe::ingredients),
            BuiltInRegistries.FLUID.byNameCodec().fieldOf("fluid").forGetter(BarrelRecipe::fluid),
            Ingredient.CODEC.fieldOf("carrier").forGetter(BarrelRecipe::carrier),
            ItemStack.CODEC.fieldOf("result").forGetter(BarrelRecipe::result),
            Codec.INT.optionalFieldOf("unit_time", DEFAULT_UNIT_TIME).forGetter(BarrelRecipe::unitTime)
    ).apply(instance, BarrelRecipe::new));

    private static final StreamCodec<RegistryFriendlyByteBuf, BarrelRecipe> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public BarrelRecipe decode(RegistryFriendlyByteBuf buf) {
            int size = Math.min(MAX_INGREDIENTS, buf.readVarInt());
            NonNullList<Ingredient> ingredients = NonNullList.withSize(MAX_INGREDIENTS, Ingredient.EMPTY);
            for (int i = 0; i < size; i++) {
                ingredients.set(i, Ingredient.CONTENTS_STREAM_CODEC.decode(buf));
            }
            ResourceLocation fluidId = buf.readResourceLocation();
            Fluid fluid = Objects.requireNonNull(BuiltInRegistries.FLUID.get(fluidId));
            Ingredient carrier = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
            ItemStack result = ItemStack.STREAM_CODEC.decode(buf);
            int unitTime = buf.readVarInt();
            return new BarrelRecipe(ingredients, fluid, carrier, result, unitTime);
        }

        @Override
        public void encode(RegistryFriendlyByteBuf buf, BarrelRecipe recipe) {
            var nonEmpty = recipe.ingredients().stream().filter(i -> !i.isEmpty()).toList();
            buf.writeVarInt(nonEmpty.size());
            for (Ingredient ingredient : nonEmpty) {
                Ingredient.CONTENTS_STREAM_CODEC.encode(buf, ingredient);
            }
            ResourceLocation fluidId = BuiltInRegistries.FLUID.getKey(recipe.fluid());
            buf.writeResourceLocation(Objects.requireNonNull(fluidId));
            Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.carrier());
            ItemStack.STREAM_CODEC.encode(buf, recipe.result());
            buf.writeVarInt(recipe.unitTime());
        }
    };

    @Override
    public MapCodec<BarrelRecipe> codec() {
        return CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, BarrelRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}
