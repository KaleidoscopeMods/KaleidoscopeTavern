package com.github.ysbbbbbb.kaleidoscopetavern.compat.rei;

import com.github.ysbbbbbb.kaleidoscopetavern.compat.rei.category.ReiBarrelRecipeCategory;
import com.github.ysbbbbbb.kaleidoscopetavern.compat.rei.category.ReiPressingTubRecipeCategory;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.forge.REIPluginClient;

@REIPluginClient
public class ModREIClientPlugin implements REIClientPlugin {
    @Override
    public void registerCategories(CategoryRegistry registry) {
        ReiBarrelRecipeCategory.registerCategories(registry);
        ReiPressingTubRecipeCategory.registerCategories(registry);
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        ReiBarrelRecipeCategory.registerDisplays(registry);
        ReiPressingTubRecipeCategory.registerDisplays(registry);
    }
}