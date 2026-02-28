package com.github.ysbbbbbb.kaleidoscopetavern.compat.rei.category;


import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.compat.rei.ReiUtil;
import com.github.ysbbbbbb.kaleidoscopetavern.crafting.recipe.BarrelRecipe;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModRecipes;
import com.google.common.collect.Lists;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.plugin.common.displays.crafting.DefaultCustomDisplay;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ReiBarrelRecipeCategory implements DisplayCategory<DefaultCustomDisplay> {
    public static final CategoryIdentifier<DefaultCustomDisplay> ID = CategoryIdentifier.of(KaleidoscopeTavern.MOD_ID, "plugin/barrel");

    private static final MutableComponent TITLE = Component.translatable("block.kaleidoscope_tavern.barrel");
    private static final ResourceLocation BG = KaleidoscopeTavern.modLoc("textures/gui/jei/barrel.png");

    public static final int WIDTH = 180;
    public static final int HEIGHT = 150;

    @Override
    public CategoryIdentifier<? extends DefaultCustomDisplay> getCategoryIdentifier() {
        return ID;
    }

    @Override
    public List<Widget> setupDisplay(DefaultCustomDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ArrayList<>();
        int startX = bounds.x;
        int startY = bounds.y;

        widgets.add(Widgets.createRecipeBase(bounds));
        widgets.add(Widgets.createTexturedWidget(BG, startX, startY, 0, 0, WIDTH, HEIGHT));

        var inputs = display.getInputEntries();

        for (int i = 0; i < inputs.size(); i++) {
            EntryIngredient ingredient = inputs.get(i);
            if (i == 0) {
                widgets.add(Widgets.createSlot(new Point(startX + 10, startY + 9))
                        .entries(ingredient)
                        .markInput());
            } else {
                int offsetX = (i - 1) * 18;
                widgets.add(Widgets.createSlot(new Point(startX + 30 + offsetX, startY + 9))
                        .entries(ingredient)
                        .markInput());
            }
        }

        display.getOptionalRecipe().ifPresent(recipe -> {
            if (recipe instanceof BarrelRecipe barrelRecipe && !barrelRecipe.carrier().isEmpty()) {
                widgets.add(Widgets.createSlot(new Point(startX + 84, startY + 117))
                        .entries(ReiUtil.ofIngredient(barrelRecipe.carrier()))
                        .disableBackground()
                        .markInput());
            }
        });

        widgets.add(Widgets.createSlot(new Point(startX + 152, startY + 86))
                .entries(display.getOutputEntries().get(0))
                .disableBackground()
                .markOutput());

        return widgets;
    }

    @Override
    public int getDisplayWidth(DefaultCustomDisplay display) {
        return WIDTH;
    }

    @Override
    public int getDisplayHeight() {
        return HEIGHT;
    }

    @Override
    public Component getTitle() {
        return TITLE;
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModItems.BARREL.get());
    }

    public static void registerCategories(CategoryRegistry registry) {
        registry.add(new ReiBarrelRecipeCategory());
        registry.addWorkstations(ReiBarrelRecipeCategory.ID, ReiUtil.ofItem(ModItems.BARREL.get()));
    }

    public static void registerDisplays(DisplayRegistry registry) {
        registry.getRecipeManager().getAllRecipesFor(ModRecipes.BARREL_RECIPE).forEach(recipe -> {
            List<EntryIngredient> inputs = Lists.newArrayList();
            inputs.add(0, ReiUtil.ofItemStack(new ItemStack(recipe.fluid().getBucket(), 4)));
            recipe.getIngredients().stream().map(i -> ReiUtil.ofIngredient(i, 16)).forEach(inputs::add);

            List<EntryIngredient> outputs = List.of(ReiUtil.ofItemStack(recipe.result().copyWithCount(16)));

            registry.add(new DefaultCustomDisplay(recipe, inputs, outputs) {
                @Override
                public CategoryIdentifier<?> getCategoryIdentifier() {
                    return ReiBarrelRecipeCategory.ID;
                }
            });
        });
    }
}
