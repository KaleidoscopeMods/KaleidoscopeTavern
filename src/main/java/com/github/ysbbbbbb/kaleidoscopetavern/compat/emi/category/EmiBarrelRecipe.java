package com.github.ysbbbbbb.kaleidoscopetavern.compat.emi.category;


import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModRecipes;
import com.google.common.collect.Lists;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.BasicEmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public class EmiBarrelRecipe extends BasicEmiRecipe {
    public static final EmiRecipeCategory CATEGORY = new EmiRecipeCategory(
            new ResourceLocation(ModRecipes.BARREL_RECIPE.toString()),
            EmiIngredient.of(Ingredient.of(ModItems.BARREL.get()))
    );

    private static final ResourceLocation BG = KaleidoscopeTavern.modLoc("textures/gui/jei/barrel.png");

    public static final int WIDTH = 180;
    public static final int HEIGHT = 150;

    public EmiBarrelRecipe(ResourceLocation id, List<EmiIngredient> inputs, List<EmiStack> outputs, List<EmiIngredient> catalysts) {
        super(CATEGORY, id, WIDTH, HEIGHT);
        this.inputs = inputs;
        this.outputs = outputs;
        this.catalysts = catalysts;
    }

    public static void register(EmiRegistry registry) {
        registry.addCategory(CATEGORY);
        registry.addWorkstation(CATEGORY, EmiStack.of(ModItems.BARREL.get()));

        registry.getRecipeManager().getAllRecipesFor(ModRecipes.BARREL_RECIPE).forEach(recipe -> {
            List<EmiIngredient> inputs = Lists.newArrayList();
            inputs.add(0, EmiStack.of(recipe.fluid().getBucket(), 4));
            recipe.getIngredients().stream().map(i -> EmiIngredient.of(i, 16)).forEach(inputs::add);

            List<EmiStack> outputs = List.of(EmiStack.of(recipe.result(), 16));
            List<EmiIngredient> catalysts = List.of(EmiIngredient.of(recipe.carrier()));

            registry.addRecipe(new EmiBarrelRecipe(recipe.getId(), inputs, outputs, catalysts));
        });
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(BG, 1, 1, WIDTH, HEIGHT, 0, 0);

        for (int i = 0; i < inputs.size(); i++) {
            EmiIngredient ingredient = inputs.get(i);
            if (i == 0) {
                widgets.addSlot(ingredient, 10, 9);
            } else {
                int offsetX = (i - 1) * 18;
                widgets.addSlot(ingredient, 30 + offsetX, 9);
            }
        }

        if (!catalysts.isEmpty()) {
            widgets.addSlot(catalysts.get(0), 84, 117)
                    .drawBack(false);
        }

        widgets.addSlot(outputs.get(0), 148, 82)
                .recipeContext(this)
                .large(true);
    }
}
