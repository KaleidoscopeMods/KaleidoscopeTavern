package com.github.ysbbbbbb.kaleidoscopetavern.init;

import net.minecraft.world.food.FoodProperties;

public interface ModFoods {
    FoodProperties GRAPE = (new FoodProperties.Builder())
            .nutrition(2).saturationModifier(0.5F)
            .alwaysEdible().build();
}
