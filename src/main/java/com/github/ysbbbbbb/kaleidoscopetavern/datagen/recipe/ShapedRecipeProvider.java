package com.github.ysbbbbbb.kaleidoscopetavern.datagen.recipe;

import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class ShapedRecipeProvider extends ModRecipeProvider {
    public ShapedRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    public void buildRecipes(RecipeOutput consumer) {
        // 沙发
        sofa(consumer, ModItems.WHITE_SOFA, Items.WHITE_WOOL);
        sofa(consumer, ModItems.ORANGE_SOFA, Items.ORANGE_WOOL);
        sofa(consumer, ModItems.MAGENTA_SOFA, Items.MAGENTA_WOOL);
        sofa(consumer, ModItems.LIGHT_BLUE_SOFA, Items.LIGHT_BLUE_WOOL);
        sofa(consumer, ModItems.YELLOW_SOFA, Items.YELLOW_WOOL);
        sofa(consumer, ModItems.LIME_SOFA, Items.LIME_WOOL);
        sofa(consumer, ModItems.PINK_SOFA, Items.PINK_WOOL);
        sofa(consumer, ModItems.GRAY_SOFA, Items.GRAY_WOOL);
        sofa(consumer, ModItems.LIGHT_GRAY_SOFA, Items.LIGHT_GRAY_WOOL);
        sofa(consumer, ModItems.CYAN_SOFA, Items.CYAN_WOOL);
        sofa(consumer, ModItems.PURPLE_SOFA, Items.PURPLE_WOOL);
        sofa(consumer, ModItems.BLUE_SOFA, Items.BLUE_WOOL);
        sofa(consumer, ModItems.BROWN_SOFA, Items.BROWN_WOOL);
        sofa(consumer, ModItems.GREEN_SOFA, Items.GREEN_WOOL);
        sofa(consumer, ModItems.BLACK_SOFA, Items.BLACK_WOOL);
        sofa(consumer, ModItems.RED_SOFA, Items.RED_WOOL);

        // 高脚凳
        barStool(consumer, ModItems.WHITE_BAR_STOOL, Items.WHITE_WOOL);
        barStool(consumer, ModItems.ORANGE_BAR_STOOL, Items.ORANGE_WOOL);
        barStool(consumer, ModItems.MAGENTA_BAR_STOOL, Items.MAGENTA_WOOL);
        barStool(consumer, ModItems.LIGHT_BLUE_BAR_STOOL, Items.LIGHT_BLUE_WOOL);
        barStool(consumer, ModItems.YELLOW_BAR_STOOL, Items.YELLOW_WOOL);
        barStool(consumer, ModItems.LIME_BAR_STOOL, Items.LIME_WOOL);
        barStool(consumer, ModItems.PINK_BAR_STOOL, Items.PINK_WOOL);
        barStool(consumer, ModItems.GRAY_BAR_STOOL, Items.GRAY_WOOL);
        barStool(consumer, ModItems.LIGHT_GRAY_BAR_STOOL, Items.LIGHT_GRAY_WOOL);
        barStool(consumer, ModItems.CYAN_BAR_STOOL, Items.CYAN_WOOL);
        barStool(consumer, ModItems.PURPLE_BAR_STOOL, Items.PURPLE_WOOL);
        barStool(consumer, ModItems.BLUE_BAR_STOOL, Items.BLUE_WOOL);
        barStool(consumer, ModItems.BROWN_BAR_STOOL, Items.BROWN_WOOL);
        barStool(consumer, ModItems.GREEN_BAR_STOOL, Items.GREEN_WOOL);
        barStool(consumer, ModItems.BLACK_BAR_STOOL, Items.BLACK_WOOL);
        barStool(consumer, ModItems.RED_BAR_STOOL, Items.RED_WOOL);

        // 香薰
        incense(consumer, ModItems.SAKURA_INCENSE, Items.CHERRY_SAPLING);
        incense(consumer, ModItems.PINE_INCENSE, Items.SPRUCE_SAPLING);
        incense(consumer, ModItems.GINKGO_INCENSE, Items.YELLOW_DYE);
        incense(consumer, ModItems.SPORE_INCENSE, Items.SPORE_BLOSSOM);
        incense(consumer, ModItems.CATNIP_INCENSE, Items.ALLIUM);
        incense(consumer, ModItems.SNOW_INCENSE, Items.SNOWBALL);
        incense(consumer, ModItems.BUTTERFLY_INCENSE, Items.PITCHER_PLANT);
        incense(consumer, ModItems.FIREFLY_INCENSE, Items.GLOWSTONE_DUST);

        // 黑板
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModItems.CHALKBOARD.get())
                .pattern("III")
                .pattern("ISI")
                .pattern("III")
                .define('I', Items.INK_SAC)
                .define('S', ItemTags.SIGNS)
                .unlockedBy("has_ink_sac", has(Items.INK_SAC))
                .save(consumer);

        // 展板
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModItems.BASE_SANDWICH_BOARD.get())
                .pattern("I")
                .pattern("S")
                .define('I', Items.INK_SAC)
                .define('S', ItemTags.WOODEN_SLABS)
                .unlockedBy("has_ink_sac", has(Items.INK_SAC))
                .save(consumer);

        // 灯串
        // 无色的
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModItems.STRING_LIGHTS_COLORLESS.get(), 8)
                .pattern("CCC")
                .pattern("LLL")
                .define('C', Items.CHAIN)
                .define('L', Items.LANTERN)
                .unlockedBy("has_chain", has(Items.CHAIN))
                .save(consumer);

        // 有色灯串
        stringLights(consumer, ModItems.STRING_LIGHTS_WHITE, Items.WHITE_DYE);
        stringLights(consumer, ModItems.STRING_LIGHTS_ORANGE, Items.ORANGE_DYE);
        stringLights(consumer, ModItems.STRING_LIGHTS_MAGENTA, Items.MAGENTA_DYE);
        stringLights(consumer, ModItems.STRING_LIGHTS_LIGHT_BLUE, Items.LIGHT_BLUE_DYE);
        stringLights(consumer, ModItems.STRING_LIGHTS_YELLOW, Items.YELLOW_DYE);
        stringLights(consumer, ModItems.STRING_LIGHTS_LIME, Items.LIME_DYE);
        stringLights(consumer, ModItems.STRING_LIGHTS_PINK, Items.PINK_DYE);
        stringLights(consumer, ModItems.STRING_LIGHTS_GRAY, Items.GRAY_DYE);
        stringLights(consumer, ModItems.STRING_LIGHTS_LIGHT_GRAY, Items.LIGHT_GRAY_DYE);
        stringLights(consumer, ModItems.STRING_LIGHTS_CYAN, Items.CYAN_DYE);
        stringLights(consumer, ModItems.STRING_LIGHTS_PURPLE, Items.PURPLE_DYE);
        stringLights(consumer, ModItems.STRING_LIGHTS_BLUE, Items.BLUE_DYE);
        stringLights(consumer, ModItems.STRING_LIGHTS_BROWN, Items.BROWN_DYE);
        stringLights(consumer, ModItems.STRING_LIGHTS_GREEN, Items.GREEN_DYE);
        stringLights(consumer, ModItems.STRING_LIGHTS_BLACK, Items.BLACK_DYE);
        stringLights(consumer, ModItems.STRING_LIGHTS_RED, Items.RED_DYE);

        // 蒙德里安挂画是有序合成
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModItems.MONDRIAN_PAINTING.get())
                .pattern(" B ")
                .pattern("WFY")
                .pattern(" R ")
                .define('F', Items.ITEM_FRAME)
                .define('B', Tags.Items.DYES_BLUE)
                .define('W', Tags.Items.DYES_WHITE)
                .define('Y', Tags.Items.DYES_YELLOW)
                .define('R', Tags.Items.DYES_RED)
                .unlockedBy("has_item_frame", has(Items.ITEM_FRAME))
                .save(consumer);

        // 吧台
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModItems.BAR_COUNTER.get())
                .pattern("NNN")
                .pattern("WWW")
                .pattern("WWW")
                .define('N', Tags.Items.NUGGETS_GOLD)
                .define('W', ItemTags.PLANKS)
                .unlockedBy("has_nugget", has(Tags.Items.NUGGETS_GOLD))
                .save(consumer);

        // 人字梯
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModItems.STEPLADDER.get())
                .pattern("L  ")
                .pattern("LL ")
                .pattern("LLL")
                .define('L', Items.LADDER)
                .unlockedBy("has_ladder", has(Items.LADDER))
                .save(consumer);

        // 藤架
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModItems.TRELLIS.get(), 8)
                .pattern("G")
                .pattern("G")
                .pattern("G")
                .define('G', ModItems.GRAPEVINE.get())
                .unlockedBy("has_grapevine", has(ModItems.GRAPEVINE.get()))
                .save(consumer);

        // 龙头
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModItems.TAP.get())
                .pattern("L")
                .pattern("H")
                .define('L', Items.LEVER)
                .define('H', Items.HOPPER)
                .unlockedBy("has_lever", has(Items.LEVER))
                .save(consumer);

        // 酒桶
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModItems.BARREL.get())
                .pattern("BBB")
                .pattern("BBB")
                .pattern("BBB")
                .define('B', Items.BARREL)
                .unlockedBy("has_barrel", has(Items.BARREL))
                .save(consumer);

        // 酒柜
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModItems.BAR_CABINET.get())
                .pattern("GGG")
                .pattern("G G")
                .pattern("GGG")
                .define('G', ModItems.GRAPEVINE.get())
                .unlockedBy("has_grapevine", has(ModItems.GRAPEVINE.get()))
                .save(consumer);

        // 玻璃酒柜
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModItems.GLASS_BAR_CABINET.get())
                .pattern("GGG")
                .pattern("GPG")
                .pattern("GGG")
                .define('G', ModItems.GRAPEVINE.get())
                .define('P', Tags.Items.GLASS_PANES)
                .unlockedBy("has_grapevine", has(ModItems.GRAPEVINE.get()))
                .save(consumer);

        // 窖藏酒柜
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModItems.CELLAR_CABINET.get())
                .pattern("GGG")
                .pattern("GTG")
                .pattern("GGG")
                .define('G', ModItems.GRAPEVINE.get())
                .define('T', ItemTags.TRAPDOORS)
                .unlockedBy("has_grapevine", has(ModItems.GRAPEVINE.get()))
                .save(consumer);

        // 倾斜酒架
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModItems.TILTED_RACK.get(), 3)
                .pattern("I  ")
                .pattern("CI ")
                .pattern("C I")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('C', Items.CHAIN)
                .unlockedBy("has_chain", has(Items.CHAIN))
                .save(consumer);

        // 圆周酒架
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModItems.CIRCULAR_RACK.get(), 2)
                .pattern("IRI")
                .pattern("IRI")
                .pattern("IRI")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('R', Items.END_ROD)
                .unlockedBy("has_chain", has(Items.END_ROD))
                .save(consumer);

        // 单体酒架
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModItems.HOLDER.get())
                .pattern(" C ")
                .pattern(" C ")
                .pattern("I I")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('C', Items.CHAIN)
                .unlockedBy("has_chain", has(Items.CHAIN))
                .save(consumer);

        // 桌子
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModItems.TABLE.get())
                .pattern("WWW")
                .pattern(" F ")
                .pattern(" I ")
                .define('W', ItemTags.PLANKS)
                .define('F', ItemTags.FENCES)
                .define('I', Tags.Items.INGOTS_IRON)
                .unlockedBy("has_fence", has(ItemTags.FENCES))
                .save(consumer);

        // 酒杯
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModItems.EMPTY_GLASSWARE.get())
                .pattern("G G")
                .pattern(" G ")
                .define('G', Tags.Items.GLASS_PANES)
                .unlockedBy("has_glass_pane", has(Tags.Items.GLASS_PANES))
                .save(consumer);

        // 酒杯架
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModItems.GLASSWARE_HOLDER.get())
                .pattern("NNN")
                .pattern("CCC")
                .pattern("NNN")
                .define('N', Tags.Items.NUGGETS_IRON)
                .define('C', Items.CHAIN)
                .unlockedBy("has_chain", has(Items.CHAIN))
                .save(consumer);

        // 垂灯
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModItems.BELL_PENDANT_LAMP.get(), 8)
                .pattern("C")
                .pattern("C")
                .pattern("B")
                .define('C', Items.CHAIN)
                .define('B', Items.BELL)
                .unlockedBy("has_chain", has(Items.CHAIN))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModItems.YELLOW_PENDANT_LAMP.get(), 4)
                .pattern("C")
                .pattern("C")
                .pattern("B")
                .define('C', Items.CHAIN)
                .define('B', Items.LANTERN)
                .unlockedBy("has_chain", has(Items.CHAIN))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModItems.BLUE_PENDANT_LAMP.get(), 4)
                .pattern("C")
                .pattern("C")
                .pattern("B")
                .define('C', Items.CHAIN)
                .define('B', Items.SOUL_LANTERN)
                .unlockedBy("has_chain", has(Items.CHAIN))
                .save(consumer);
    }

    private void sofa(RecipeOutput consumer, Supplier<? extends Item> item, Item wool) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, item.get())
                .pattern("W W")
                .pattern("WWW")
                .pattern("L L")
                .define('W', wool)
                .define('L', Tags.Items.INGOTS_IRON)
                .unlockedBy("has_wool", has(wool))
                .save(consumer);
    }

    private void barStool(RecipeOutput consumer, Supplier<? extends Item> item, Item wool) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, item.get())
                .pattern("W")
                .pattern("C")
                .pattern("L")
                .define('W', wool)
                .define('C', Items.CHAIN)
                .define('L', Tags.Items.INGOTS_IRON)
                .unlockedBy("has_wool", has(wool))
                .save(consumer);
    }

    private void stringLights(RecipeOutput consumer, Supplier<? extends Item> item, Item dye) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, item.get(), 8)
                .pattern("CCC")
                .pattern("LLL")
                .pattern("DDD")
                .define('C', Items.CHAIN)
                .define('L', Items.LANTERN)
                .define('D', dye)
                .unlockedBy("has_dye", has(dye))
                .save(consumer);
    }

    private void incense(RecipeOutput consumer, Supplier<? extends Item> item, Item ingredient) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, item.get())
                .pattern("F")
                .pattern("C")
                .pattern("B")
                .define('F', Items.FEATHER)
                .define('C', ingredient)
                .define('B', Items.GLASS_BOTTLE)
                .unlockedBy("has_glass_bottle", has(Items.GLASS_BOTTLE))
                .save(consumer);
    }
}
