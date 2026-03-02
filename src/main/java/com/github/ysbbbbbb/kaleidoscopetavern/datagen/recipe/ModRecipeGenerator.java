package com.github.ysbbbbbb.kaleidoscopetavern.datagen.recipe;

import com.google.common.collect.Lists;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeGenerator extends ModRecipeProvider {
    private final List<ModRecipeProvider> providers = Lists.newArrayList();

    public ModRecipeGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
        providers.add(new PressingTubRecipeProvider(output, registries));
        providers.add(new BarrelRecipeProvider(output, registries));
        providers.add(new ShapedRecipeProvider(output, registries));
        providers.add(new ShapelessRecipeProvider(output, registries));
    }

    @Override
    public void buildRecipes(RecipeOutput output) {
        for (ModRecipeProvider provider : providers) {
            provider.buildRecipes(output);
        }
    }
}
