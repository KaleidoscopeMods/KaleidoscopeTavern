package com.github.ysbbbbbb.kaleidoscopetavern.compat.jei.category;


import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.api.blockentity.IPressingTub;
import com.github.ysbbbbbb.kaleidoscopetavern.crafting.recipe.PressingTubRecipe;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModRecipes;
import com.google.common.collect.Lists;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class PressingTubCategory implements IRecipeCategory<PressingTubRecipe> {
    public static final RecipeType<PressingTubRecipe> TYPE = RecipeType.create(KaleidoscopeTavern.MOD_ID, "pressing_tub", PressingTubRecipe.class);

    private static final ResourceLocation BG = KaleidoscopeTavern.modLoc("textures/gui/jei/pressing_tub.png");
    private static final MutableComponent TITLE = Component.translatable("block.kaleidoscope_tavern.pressing_tub");

    public static final int WIDTH = 155;
    public static final int HEIGHT = 85;

    private final IDrawable bgDraw;
    private final IDrawable iconDraw;

    public PressingTubCategory(IGuiHelper guiHelper) {
        this.bgDraw = guiHelper.createDrawable(BG, 0, 0, WIDTH, HEIGHT);
        this.iconDraw = guiHelper.createDrawableItemLike(ModItems.PRESSING_TUB.get());
    }

    public static List<PressingTubRecipe> getRecipes() {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) {
            return List.of();
        }
        List<PressingTubRecipe> recipes = Lists.newArrayList();
        recipes.addAll(level.getRecipeManager().getAllRecipesFor(ModRecipes.PRESSING_TUB_RECIPE));
        return recipes;
    }

    @Override
    public void draw(PressingTubRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        this.bgDraw.draw(guiGraphics);

        int needPressCount = IPressingTub.MAX_FLUID_AMOUNT / recipe.getFluidAmount();
        if (needPressCount * recipe.getFluidAmount() < IPressingTub.MAX_FLUID_AMOUNT) {
            needPressCount++;
        }
        Component tip = Component.translatable("jei.kaleidoscope_tavern.pressing_tub.need_press_count", needPressCount);
        drawCenteredString(guiGraphics, tip);
    }

    private void drawCenteredString(GuiGraphics guiGraphics, Component text) {
        Font font = Minecraft.getInstance().font;
        FormattedCharSequence sequence = text.getVisualOrderText();
        guiGraphics.drawString(font, sequence, 77 - font.width(sequence) / 2, 74, 0x555555, false);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, PressingTubRecipe recipe, IFocusGroup focuses) {
        int needPressCount = IPressingTub.MAX_FLUID_AMOUNT / recipe.getFluidAmount();
        if (needPressCount * recipe.getFluidAmount() < IPressingTub.MAX_FLUID_AMOUNT) {
            needPressCount++;
        }

        for (Ingredient input : recipe.getIngredients()) {
            int finalNeedPressCount = needPressCount;
            List<ItemStack> list = Arrays.stream(input.getItems())
                    .map(s -> s.copyWithCount(finalNeedPressCount))
                    .toList();
            builder.addSlot(RecipeIngredientRole.INPUT, 32, 25)
                    .addIngredients(VanillaTypes.ITEM_STACK, list);
        }

        builder.addSlot(RecipeIngredientRole.OUTPUT, 128, 30)
                .addItemLike(recipe.getFluid().getBucket());
    }

    @Override
    public RecipeType<PressingTubRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return TITLE;
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    @Override
    @Nullable
    public IDrawable getIcon() {
        return iconDraw;
    }
}
