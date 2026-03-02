package com.github.ysbbbbbb.kaleidoscopetavern.crafting.recipe;

import com.github.ysbbbbbb.kaleidoscopetavern.crafting.container.BarrelRecipeContainer;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModRecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.common.util.RecipeMatcher;

/**
 * 酒桶的配方类
 *
 * @param ingredients 酒桶输入的原料，没有数量参数，最大只允许 4 种物品输入。可以有 0 种物品输入，此时表示只使用流体酿造
 * @param fluid       酒桶输入的流体，只能有一个流体输入
 * @param carrier     酒桶的容器，必须是 ItemBlock 及其子类，且只能有一个输入
 * @param result      酒桶的输出物品，其必须是 BottleBlockItem 及其子类，只能有一个输出
 * @param unitTime    酿造单位时间，单位为 tick。<br>
 *                    酿造会分多个阶段，每个阶段会持续 n * unitTime tick。<br>
 *                    n 为当前阶段数（从 1 开始）。例如，unitTime 为 100，则第一阶段持续 100 tick，第二阶段持续 200 tick，以此类推
 */
public record BarrelRecipe(
        NonNullList<Ingredient> ingredients,
        Fluid fluid,
        Ingredient carrier,
        ItemStack result,
        int unitTime
) implements Recipe<BarrelRecipeContainer> {
    @Override
    public boolean matches(BarrelRecipeContainer container, Level level) {
        boolean emptyIngredients = this.ingredients.stream().allMatch(Ingredient::isEmpty);
        // 如果全为空
        if (emptyIngredients) {
            return container.getFluid().isSame(this.fluid) && container.itemsIsEmpty();
        }
        // 否则匹配输入的流体和物品
        return container.getFluid().isSame(this.fluid)
               && RecipeMatcher.findMatches(container.getItems(), ingredients) != null;
    }

    @Override
    public ItemStack assemble(BarrelRecipeContainer container, HolderLookup.Provider registries) {
        // 遍历容器，找出数量最少的输入物品数量，作为酿造结果的数量
        // 默认数量为 16，超过这个数量的输入物品不会增加输出物品的数量
        int count = 16;
        for (ItemStack itemStack : container.getItems()) {
            if (!itemStack.isEmpty()) {
                count = Math.min(count, itemStack.getCount());
            }
        }
        return getResultItem(registries).copyWithCount(count);
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.ingredients;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return this.result;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.BARREL_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.BARREL_RECIPE;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return false;
    }
}
