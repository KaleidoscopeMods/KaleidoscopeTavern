package com.github.ysbbbbbb.kaleidoscopetavern.compat.jei.category;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.crafting.recipe.ShakerRecipe;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModRecipes;
import com.google.common.collect.Lists;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ShakerRecipeCategory implements IRecipeCategory<RecipeHolder<ShakerRecipe>> {
    public static final RecipeType<RecipeHolder<ShakerRecipe>> TYPE = RecipeType.createRecipeHolderType(KaleidoscopeTavern.modLoc("shaker"));

    private static final ResourceLocation BG = KaleidoscopeTavern.modLoc("textures/gui/jei/shaker.png");
    private static final MutableComponent TITLE = Component.translatable("block.kaleidoscope_tavern.shaker");

    public static final int WIDTH = 150;
    public static final int HEIGHT = 80;

    private final IDrawable bgDraw;
    private final IDrawable iconDraw;

    public ShakerRecipeCategory(IGuiHelper guiHelper) {
        this.bgDraw = guiHelper.createDrawable(BG, 0, 0, WIDTH, HEIGHT);
        this.iconDraw = guiHelper.createDrawableItemLike(ModItems.SHAKER.get());
    }

    public static List<RecipeHolder<ShakerRecipe>> getRecipes() {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) {
            return List.of();
        }
        List<RecipeHolder<ShakerRecipe>> recipes = Lists.newArrayList();
        recipes.addAll(level.getRecipeManager().getAllRecipesFor(ModRecipes.SHAKER_RECIPE));
        return recipes;
    }

    @SuppressWarnings("all")
    @Override
    public void draw(RecipeHolder<ShakerRecipe> recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        this.bgDraw.draw(guiGraphics);

        // 如果对应的配方是颜色配方，那么渲染一个色块
        recipe.value().ingredientColors().forEach((index, color) -> {
            int x = 66;
            int y = 14 + 18 * index;
            int rgba = 0xFF000000 | color.getColor();
            guiGraphics.fill(x, y, x + 8, y + 16, rgba);
        });
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<ShakerRecipe> recipe, IFocusGroup focuses) {
        int offsetY = 0;
        for (Ingredient input : recipe.value().getIngredients()) {
            if (input.isEmpty()) {
                continue;
            }
            builder.addSlot(RecipeIngredientRole.INPUT, 52, 14 + offsetY)
                    .setStandardSlotBackground()
                    .addIngredients(input);
            offsetY += 18;
        }

        ItemStack outputStack = recipe.value().result();
        builder.addSlot(RecipeIngredientRole.OUTPUT, 112, 36)
                .setOutputSlotBackground()
                .addItemStack(outputStack);
    }

    @Override
    public RecipeType<RecipeHolder<ShakerRecipe>> getRecipeType() {
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
