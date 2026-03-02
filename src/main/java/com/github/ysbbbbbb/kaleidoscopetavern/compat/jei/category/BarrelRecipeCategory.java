package com.github.ysbbbbbb.kaleidoscopetavern.compat.jei.category;


import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.crafting.recipe.BarrelRecipe;
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
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class BarrelRecipeCategory implements IRecipeCategory<RecipeHolder<BarrelRecipe>> {
    public static final RecipeType<RecipeHolder<BarrelRecipe>> TYPE = RecipeType.createRecipeHolderType(KaleidoscopeTavern.modLoc("barrel"));

    private static final ResourceLocation BG = KaleidoscopeTavern.modLoc("textures/gui/jei/barrel.png");
    private static final MutableComponent TITLE = Component.translatable("block.kaleidoscope_tavern.barrel");

    public static final int WIDTH = 180;
    public static final int HEIGHT = 150;

    private final IDrawable bgDraw;
    private final IDrawable iconDraw;

    public BarrelRecipeCategory(IGuiHelper guiHelper) {
        this.bgDraw = guiHelper.createDrawable(BG, 0, 0, WIDTH, HEIGHT);
        this.iconDraw = guiHelper.createDrawableItemLike(ModItems.BARREL.get());
    }

    public static List<RecipeHolder<BarrelRecipe>> getRecipes() {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) {
            return List.of();
        }
        List<RecipeHolder<BarrelRecipe>> recipes = Lists.newArrayList();
        recipes.addAll(level.getRecipeManager().getAllRecipesFor(ModRecipes.BARREL_RECIPE));
        return recipes;
    }

    @Override
    public void draw(RecipeHolder<BarrelRecipe> holder, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        this.bgDraw.draw(guiGraphics);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<BarrelRecipe> holder, IFocusGroup focuses) {
        BarrelRecipe recipe = holder.value();

        int offsetX = 0;
        for (Ingredient input : recipe.getIngredients()) {
            List<ItemStack> list = Arrays.stream(input.getItems())
                    .map(s -> s.copyWithCount(16))
                    .toList();
            builder.addSlot(RecipeIngredientRole.INPUT, 30 + offsetX, 9)
                    .addIngredients(VanillaTypes.ITEM_STACK, list);
            offsetX += 18;
        }

        ItemStack fluidStack = new ItemStack(recipe.fluid().getBucket(), 4);
        builder.addSlot(RecipeIngredientRole.INPUT, 10, 9)
                .addItemStack(fluidStack);

        builder.addSlot(RecipeIngredientRole.CATALYST, 84, 117)
                .addIngredients(recipe.carrier());

        ItemStack outputStack = recipe.result().copyWithCount(16);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 152, 86)
                .addItemStack(outputStack);
    }

    @Override
    public RecipeType<RecipeHolder<BarrelRecipe>> getRecipeType() {
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
