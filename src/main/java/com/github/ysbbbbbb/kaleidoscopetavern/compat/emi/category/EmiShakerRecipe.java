package com.github.ysbbbbbb.kaleidoscopetavern.compat.emi.category;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.crafting.recipe.ShakerRecipe;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModRecipes;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.BasicEmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public class EmiShakerRecipe extends BasicEmiRecipe {
    public static final EmiRecipeCategory CATEGORY = new EmiRecipeCategory(
            ResourceLocation.parse(ModRecipes.SHAKER_RECIPE.toString()),
            EmiIngredient.of(Ingredient.of(ModItems.SHAKER.get()))
    );

    private static final ResourceLocation BG = KaleidoscopeTavern.modLoc("textures/gui/jei/shaker.png");

    public static final int WIDTH = 150;
    public static final int HEIGHT = 80;

    private final Int2ObjectMap<ChatFormatting> ingredientColors;

    public EmiShakerRecipe(ResourceLocation id, List<EmiIngredient> inputs, List<EmiStack> outputs,
                           Int2ObjectMap<ChatFormatting> ingredientColors) {
        super(CATEGORY, id, WIDTH, HEIGHT);
        this.inputs = inputs;
        this.outputs = outputs;
        this.ingredientColors = ingredientColors;
    }

    public static void register(EmiRegistry registry) {
        registry.addCategory(CATEGORY);
        registry.addWorkstation(CATEGORY, EmiStack.of(ModItems.SHAKER.get()));

        registry.getRecipeManager().getAllRecipesFor(ModRecipes.SHAKER_RECIPE).forEach(holder -> {
            ShakerRecipe recipe = holder.value();
            List<EmiIngredient> inputs = recipe.getIngredients().stream()
                    .filter(i -> !i.isEmpty())
                    .map(EmiIngredient::of)
                    .toList();

            List<EmiStack> outputs = List.of(EmiStack.of(recipe.result()));

            registry.addRecipe(new EmiShakerRecipe(holder.id(), inputs, outputs, recipe.ingredientColors()));
        });
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(BG, 1, 1, WIDTH, HEIGHT, 0, 0);

        int offsetY = 0;
        for (EmiIngredient input : inputs) {
            widgets.addSlot(input, 52, 14 + offsetY);
            offsetY += 18;
        }

        // 渲染颜色配方的色块
        ingredientColors.forEach((index, color) -> {
            int x = 70;
            int y = 15 + 18 * index;
            int rgba = 0xFF000000 | color.getColor();
            widgets.addDrawable(x, y, 5, 16, (guiGraphics, mouseX, mouseY, delta) ->
                    guiGraphics.fill(0, 0, 5, 16, rgba));
        });

        widgets.addSlot(outputs.getFirst(), 108, 32)
                .recipeContext(this)
                .large(true);
    }
}
