package com.github.ysbbbbbb.kaleidoscopetavern.datagen.recipe;

import com.github.ysbbbbbb.kaleidoscopetavern.datagen.builder.ShakerBuilder;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopetavern.init.tag.TagMod;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

public class ShakerRecipeProvider extends ModRecipeProvider {
    public ShakerRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> consumer) {
        // 血腥玛丽
        ShakerBuilder.builder()
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_RED)
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_RED)
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_RED)
                .setResult(ModItems.BLOODY_MARY.get())
                .save(consumer);

        // 翡翠
        ShakerBuilder.builder()
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_GREEN)
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_GREEN)
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_GREEN)
                .setResult(ModItems.EMERALD.get())
                .save(consumer);

        // 绿色蚱蜢
        ShakerBuilder.builder()
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_GREEN)
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_GREEN)
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_WHITE)
                .setResult(ModItems.GRASSHOPPER.get())
                .save(consumer);

        // 绒球葱花园
        ShakerBuilder.builder()
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_LIGHT_PURPLE)
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_LIGHT_PURPLE)
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_LIGHT_PURPLE)
                .setResult(ModItems.ALLIUM_GARDEN.get())
                .save(consumer);

        // 深水炸弹
        ShakerBuilder.builder()
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_BLUE)
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_LIGHT_PURPLE)
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_WHITE)
                .setResult(ModItems.DEPTH_CHARGE.get())
                .save(consumer);

        // 螺丝起子
        ShakerBuilder.builder()
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_YELLOW)
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_YELLOW)
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_GOLD)
                .setResult(ModItems.SCREWDRIVER.get())
                .save(consumer);

        // 教父
        ShakerBuilder.builder()
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_RED)
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_RED)
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_GOLD)
                .setResult(ModItems.GODFATHER.get())
                .save(consumer);

        // 白色佳人
        ShakerBuilder.builder()
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_WHITE)
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_GREEN)
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_YELLOW)
                .setResult(ModItems.WHITE_LADY.get())
                .save(consumer);

        // 莫吉托
        ShakerBuilder.builder()
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_WHITE)
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_WHITE)
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_GREEN)
                .setResult(ModItems.MOJITO.get())
                .save(consumer);

        // 黄铜心脏
        ShakerBuilder.builder()
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_GOLD)
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_GOLD)
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_GOLD)
                .setResult(ModItems.BRASS_HEART.get())
                .save(consumer);

        // 下界特调
        ShakerBuilder.builder()
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_RED)
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_GREEN)
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_BLUE)
                .setResult(ModItems.NETHER_SPECIAL.get())
                .save(consumer);

        // 幽匿特调
        ShakerBuilder.builder()
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_BLUE)
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_BLUE)
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_LIGHT_PURPLE)
                .setResult(ModItems.SCULK_SPECIAL.get())
                .save(consumer);
    }
}
