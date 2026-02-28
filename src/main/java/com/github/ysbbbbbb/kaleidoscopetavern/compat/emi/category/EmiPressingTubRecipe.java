package com.github.ysbbbbbb.kaleidoscopetavern.compat.emi.category;


import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.api.blockentity.IPressingTub;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModRecipes;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.BasicEmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public class EmiPressingTubRecipe extends BasicEmiRecipe {
    public static final EmiRecipeCategory CATEGORY = new EmiRecipeCategory(
            new ResourceLocation(ModRecipes.PRESSING_TUB_RECIPE.toString()),
            EmiIngredient.of(Ingredient.of(ModItems.PRESSING_TUB.get()))
    );

    private static final ResourceLocation BG = KaleidoscopeTavern.modLoc("textures/gui/jei/pressing_tub.png");

    public static final int WIDTH = 155;
    public static final int HEIGHT = 85;

    public EmiPressingTubRecipe(ResourceLocation id, List<EmiIngredient> inputs, List<EmiStack> outputs) {
        super(CATEGORY, id, WIDTH, HEIGHT);
        this.inputs = inputs;
        this.outputs = outputs;
    }

    public static void register(EmiRegistry registry) {
        registry.addCategory(CATEGORY);
        registry.addWorkstation(CATEGORY, EmiStack.of(ModItems.PRESSING_TUB.get()));

        registry.getRecipeManager().getAllRecipesFor(ModRecipes.PRESSING_TUB_RECIPE).forEach(recipe -> {
            int needPressCount = IPressingTub.MAX_FLUID_AMOUNT / recipe.getFluidAmount();
            if (needPressCount * recipe.getFluidAmount() < IPressingTub.MAX_FLUID_AMOUNT) {
                needPressCount++;
            }

            final int finalNeedPressCount = needPressCount;
            List<EmiIngredient> inputs = recipe.getIngredients().stream()
                    .map(i -> EmiIngredient.of(i, finalNeedPressCount))
                    .toList();

            List<EmiStack> outputs = List.of(EmiStack.of(recipe.getFluid().getBucket()));

            registry.addRecipe(new EmiPressingTubRecipe(recipe.getId(), inputs, outputs));
        });
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(BG, 1, 1, WIDTH, HEIGHT, 0, 0);

        widgets.addSlot(inputs.get(0), 32, 25)
                .drawBack(false);

        widgets.addSlot(outputs.get(0), 124, 26)
                .recipeContext(this)
                .large(true);

        Component tip = Component.translatable("jei.kaleidoscope_tavern.pressing_tub.need_press_count", inputs.get(0).getAmount());
        FormattedCharSequence sequence = tip.getVisualOrderText();
        Font font = Minecraft.getInstance().font;
        widgets.addText(tip, 77 - font.width(sequence) / 2, 74, 0x555555, false);
    }
}
