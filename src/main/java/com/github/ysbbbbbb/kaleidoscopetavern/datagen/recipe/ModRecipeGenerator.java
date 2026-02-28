package com.github.ysbbbbbb.kaleidoscopetavern.datagen.recipe;

import com.google.common.collect.Lists;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeGenerator extends ModRecipeProvider {
    private final List<ModRecipeProvider> providers = Lists.newArrayList();

    public ModRecipeGenerator(PackOutput output) {
        super(output);
        providers.add(new PressingTubRecipeProvider(output));
        providers.add(new BarrelRecipeProvider(output));
        providers.add(new ShapedRecipeProvider(output));
        providers.add(new ShapelessRecipeProvider(output));
    }

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> consumer) {
        for (ModRecipeProvider provider : providers) {
            provider.buildRecipes(consumer);
        }
    }
}
