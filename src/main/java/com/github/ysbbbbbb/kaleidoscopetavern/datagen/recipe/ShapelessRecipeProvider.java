package com.github.ysbbbbbb.kaleidoscopetavern.datagen.recipe;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.block.deco.SandwichBoardBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopetavern.item.SandwichBoardBlockItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Consumer;

public class ShapelessRecipeProvider extends ModRecipeProvider {
    public ShapelessRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> consumer) {
        // 挂画
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ModItems.YSBB_PAINTING.get())
                .requires(Items.ITEM_FRAME)
                .requires(Tags.Items.DYES_LIME)
                .unlockedBy("has_item_frame", has(Items.ITEM_FRAME))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ModItems.TARTARIC_ACID_PAINTING.get())
                .requires(Items.ITEM_FRAME)
                .requires(Tags.Items.DYES_LIGHT_BLUE)
                .unlockedBy("has_item_frame", has(Items.ITEM_FRAME))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ModItems.CR019_PAINTING.get())
                .requires(Items.ITEM_FRAME)
                .requires(Tags.Items.DYES_RED)
                .unlockedBy("has_item_frame", has(Items.ITEM_FRAME))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ModItems.UNKNOWN_PAINTING.get())
                .requires(Items.ITEM_FRAME)
                .requires(Tags.Items.DYES_YELLOW)
                .unlockedBy("has_item_frame", has(Items.ITEM_FRAME))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ModItems.MASTER_MARISA_PAINTING.get())
                .requires(Items.ITEM_FRAME)
                .requires(Tags.Items.DYES_PURPLE)
                .unlockedBy("has_item_frame", has(Items.ITEM_FRAME))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ModItems.SON_OF_MAN_PAINTING.get())
                .requires(Items.ITEM_FRAME)
                .requires(Items.APPLE)
                .unlockedBy("has_item_frame", has(Items.ITEM_FRAME))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ModItems.DAVID_PAINTING.get())
                .requires(Items.ITEM_FRAME)
                .requires(Tags.Items.DYES_WHITE)
                .unlockedBy("has_item_frame", has(Items.ITEM_FRAME))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ModItems.GIRL_WITH_PEARL_EARRING_PAINTING.get())
                .requires(Items.ITEM_FRAME)
                .requires(Items.ENDER_PEARL)
                .unlockedBy("has_item_frame", has(Items.ITEM_FRAME))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ModItems.STARRY_NIGHT_PAINTING.get())
                .requires(Items.ITEM_FRAME)
                .requires(Items.ECHO_SHARD)
                .unlockedBy("has_item_frame", has(Items.ITEM_FRAME))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ModItems.VAN_GOGH_SELF_PORTRAIT_PAINTING.get())
                .requires(Items.ITEM_FRAME)
                .requires(Items.PAINTING)
                .unlockedBy("has_item_frame", has(Items.ITEM_FRAME))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ModItems.FATHER_PAINTING.get())
                .requires(Items.ITEM_FRAME)
                .requires(Items.IRON_HOE)
                .unlockedBy("has_item_frame", has(Items.ITEM_FRAME))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ModItems.GREAT_WAVE_PAINTING.get())
                .requires(Items.ITEM_FRAME)
                .requires(Items.BAMBOO_RAFT)
                .unlockedBy("has_item_frame", has(Items.ITEM_FRAME))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ModItems.MONA_LISA_PAINTING.get())
                .requires(Items.ITEM_FRAME)
                .requires(Tags.Items.GEMS_DIAMOND)
                .unlockedBy("has_item_frame", has(Items.ITEM_FRAME))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ModItems.PRESSING_TUB.get(), 2)
                .requires(Items.BARREL)
                .unlockedBy("has_barrel", has(Items.BARREL))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ModItems.EMPTY_BOTTLE.get())
                .requires(Items.GLASS_BOTTLE)
                .unlockedBy("has_glass_bottle", has(Items.GLASS_BOTTLE))
                .save(consumer);

        // 展板添加直接合成的变种
        ForgeRegistries.ITEMS.getValues().forEach(item -> {
            // 遍历所有的展板
            if (item instanceof SandwichBoardBlockItem blockItem && blockItem.getBlock() instanceof SandwichBoardBlock result) {
                String resultName = getId(result).getPath();
                result.getTransformItems().forEach(transform -> {
                    String transformName = getId(transform).getPath();
                    ResourceLocation recipeId = KaleidoscopeTavern.modLoc("%s_from_%s".formatted(resultName, transformName));
                    ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, result)
                            .requires(ModItems.BASE_SANDWICH_BOARD.get())
                            .requires(transform)
                            .unlockedBy("has_base_sandwich_board", has(ModItems.BASE_SANDWICH_BOARD.get()))
                            .save(consumer, recipeId);
                });
            }
        });
    }

    @SuppressWarnings("all")
    static ResourceLocation getId(ItemLike itemLike) {
        return BuiltInRegistries.ITEM.getKey(itemLike.asItem());
    }
}