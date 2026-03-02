package com.github.ysbbbbbb.kaleidoscopetavern.compat.emi;

import com.github.ysbbbbbb.kaleidoscopetavern.compat.emi.category.EmiBarrelRecipe;
import com.github.ysbbbbbb.kaleidoscopetavern.compat.emi.category.EmiPressingTubRecipe;
import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;

@EmiEntrypoint
public class ModEmiPlugin implements EmiPlugin {
    @Override
    public void register(EmiRegistry registry) {
        EmiBarrelRecipe.register(registry);
        EmiPressingTubRecipe.register(registry);
    }
}