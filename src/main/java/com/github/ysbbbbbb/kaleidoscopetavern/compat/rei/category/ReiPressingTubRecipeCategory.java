package com.github.ysbbbbbb.kaleidoscopetavern.compat.rei.category;


import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.api.blockentity.IPressingTub;
import com.github.ysbbbbbb.kaleidoscopetavern.compat.rei.ReiUtil;
import com.github.ysbbbbbb.kaleidoscopetavern.crafting.recipe.PressingTubRecipe;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModRecipes;
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
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;

import java.util.ArrayList;
import java.util.List;

public class ReiPressingTubRecipeCategory implements DisplayCategory<DefaultCustomDisplay> {
    public static final CategoryIdentifier<DefaultCustomDisplay> ID = CategoryIdentifier.of(KaleidoscopeTavern.MOD_ID, "plugin/pressing_tub");

    private static final MutableComponent TITLE = Component.translatable("block.kaleidoscope_tavern.pressing_tub");
    private static final ResourceLocation BG = KaleidoscopeTavern.modLoc("textures/gui/jei/pressing_tub.png");

    public static final int WIDTH = 155;
    public static final int HEIGHT = 85;

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

        widgets.add(Widgets.createSlot(new Point(startX + 32, startY + 25))
                .entries(display.getInputEntries().get(0))
                .disableBackground()
                .markInput());

        widgets.add(Widgets.createSlot(new Point(startX + 128, startY + 30))
                .entries(display.getOutputEntries().get(0))
                .disableBackground()
                .markOutput());

        display.getOptionalRecipe().ifPresent(recipe -> {
            if (recipe instanceof PressingTubRecipe pressingTubRecipe) {
                int needPressCount = IPressingTub.MAX_FLUID_AMOUNT / pressingTubRecipe.getFluidAmount();
                if (needPressCount * pressingTubRecipe.getFluidAmount() < IPressingTub.MAX_FLUID_AMOUNT) {
                    needPressCount++;
                }
                Component tip = Component.translatable("jei.kaleidoscope_tavern.pressing_tub.need_press_count", needPressCount);
                widgets.add(Widgets.withTranslate(Widgets.createDrawableWidget((guiGraphics, mouseX, mouseY, v) ->
                        drawCenteredString(guiGraphics, tip)), startX, startY, 0)
                );
            }
        });

        return widgets;
    }

    private void drawCenteredString(GuiGraphics guiGraphics, Component text) {
        Font font = Minecraft.getInstance().font;
        FormattedCharSequence sequence = text.getVisualOrderText();
        guiGraphics.drawString(font, sequence, 77 - font.width(sequence) / 2, 70, 0x555555, false);
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
        return EntryStacks.of(ModItems.PRESSING_TUB.get());
    }

    public static void registerCategories(CategoryRegistry registry) {
        registry.add(new ReiPressingTubRecipeCategory());
        registry.addWorkstations(ReiPressingTubRecipeCategory.ID, ReiUtil.ofItem(ModItems.PRESSING_TUB.get()));
    }

    public static void registerDisplays(DisplayRegistry registry) {
        registry.getRecipeManager().getAllRecipesFor(ModRecipes.PRESSING_TUB_RECIPE).forEach(recipe -> {
            int needPressCount = IPressingTub.MAX_FLUID_AMOUNT / recipe.getFluidAmount();
            if (needPressCount * recipe.getFluidAmount() < IPressingTub.MAX_FLUID_AMOUNT) {
                needPressCount++;
            }

            final int finalNeedPressCount = needPressCount;
            List<EntryIngredient> inputs = recipe.getIngredients().stream()
                    .map(i -> ReiUtil.ofIngredient(i, finalNeedPressCount))
                    .toList();

            List<EntryIngredient> outputs = List.of(ReiUtil.ofItem(recipe.getFluid().getBucket()));

            registry.add(new DefaultCustomDisplay(recipe, inputs, outputs) {
                @Override
                public CategoryIdentifier<?> getCategoryIdentifier() {
                    return ReiPressingTubRecipeCategory.ID;
                }
            });
        });
    }
}
