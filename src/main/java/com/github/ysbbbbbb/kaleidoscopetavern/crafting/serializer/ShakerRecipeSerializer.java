package com.github.ysbbbbbb.kaleidoscopetavern.crafting.serializer;

import com.github.ysbbbbbb.kaleidoscopetavern.crafting.recipe.ShakerRecipe;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class ShakerRecipeSerializer implements RecipeSerializer<ShakerRecipe> {
    public static final int MAX_INGREDIENTS = 3;

    private static final MapCodec<ShakerRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Ingredient.CODEC.listOf().xmap(list -> {
                        NonNullList<Ingredient> nonnull = NonNullList.withSize(MAX_INGREDIENTS, Ingredient.EMPTY);
                        for (int i = 0; i < Math.min(list.size(), MAX_INGREDIENTS); i++) {
                            nonnull.set(i, list.get(i));
                        }
                        return nonnull;
                    }, nonnull -> nonnull.stream().filter(i -> !i.isEmpty()).toList()
            ).optionalFieldOf("ingredients", NonNullList.withSize(MAX_INGREDIENTS, Ingredient.EMPTY)).forGetter(ShakerRecipe::ingredients),
            ItemStack.CODEC.fieldOf("result").forGetter(ShakerRecipe::result)
    ).apply(instance, (ingredients, result) -> new ShakerRecipe(ingredients, result, Int2ObjectMaps.emptyMap())));

    private static final StreamCodec<RegistryFriendlyByteBuf, ShakerRecipe> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public ShakerRecipe decode(RegistryFriendlyByteBuf buf) {
            int size = Math.min(MAX_INGREDIENTS, buf.readVarInt());
            NonNullList<Ingredient> ingredients = NonNullList.withSize(MAX_INGREDIENTS, Ingredient.EMPTY);
            for (int i = 0; i < size; i++) {
                ingredients.set(i, Ingredient.CONTENTS_STREAM_CODEC.decode(buf));
            }

            ItemStack result = ItemStack.STREAM_CODEC.decode(buf);

            Int2ObjectMap<ChatFormatting> ingredientColors = new Int2ObjectOpenHashMap<>();
            int colorSize = buf.readVarInt();
            for (int i = 0; i < colorSize; i++) {
                int index = buf.readVarInt();
                ChatFormatting formatting = ChatFormatting.getById(buf.readVarInt());
                if (formatting != null) {
                    ingredientColors.put(index, formatting);
                }
            }
            return new ShakerRecipe(ingredients, result, ingredientColors);
        }

        @Override
        public void encode(RegistryFriendlyByteBuf buf, ShakerRecipe recipe) {
            var nonEmpty = recipe.ingredients().stream().filter(i -> !i.isEmpty()).toList();
            buf.writeVarInt(nonEmpty.size());
            for (Ingredient ingredient : nonEmpty) {
                Ingredient.CONTENTS_STREAM_CODEC.encode(buf, ingredient);
            }

            ItemStack.STREAM_CODEC.encode(buf, recipe.result());

            Int2ObjectMap<ChatFormatting> ingredientColors = recipe.ingredientColors();
            buf.writeVarInt(ingredientColors.size());
            ingredientColors.forEach((index, formatting) -> {
                buf.writeVarInt(index);
                buf.writeVarInt(formatting.getId());
            });
        }
    };

    @Override
    public MapCodec<ShakerRecipe> codec() {
        return CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, ShakerRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}
