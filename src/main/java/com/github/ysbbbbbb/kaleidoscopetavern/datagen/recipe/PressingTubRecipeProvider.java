package com.github.ysbbbbbb.kaleidoscopetavern.datagen.recipe;

import com.github.ysbbbbbb.kaleidoscopetavern.datagen.builder.PressingTubBuilder;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModFluids;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;

public class PressingTubRecipeProvider extends ModRecipeProvider {
    public PressingTubRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> consumer) {
        PressingTubBuilder.builder()
                .setIngredient(ModItems.GRAPE.get())
                .setFluid(ModFluids.GRAPE_JUICE.get())
                .save(consumer);

        PressingTubBuilder.builder()
                .setIngredient(Items.SWEET_BERRIES)
                .setFluid(ModFluids.SWEET_BERRIES_JUICE.get())
                .save(consumer);

        PressingTubBuilder.builder()
                .setIngredient(Items.GLOW_BERRIES)
                .setFluid(ModFluids.GLOW_BERRIES_JUICE.get())
                .save(consumer);
    }
}
