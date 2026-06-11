package com.github.ysbbbbbb.kaleidoscopetavern.datagen.tag;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopetavern.init.tag.TagCommon;
import com.github.ysbbbbbb.kaleidoscopetavern.init.tag.TagMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class TagItem extends ItemTagsProvider {
    public TagItem(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider,
                   CompletableFuture<TagLookup<Block>> pBlockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pBlockTags, KaleidoscopeTavern.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(TagMod.BAR_CABINET_IRREGULAR).add(
                ModItems.BRANDY.get(),
                ModItems.CARIGNAN.get()
        );

        tag(TagMod.CELLAR_CABINET_BLOCKLIST).add(
                ModItems.BRANDY.get(),
                ModItems.CARIGNAN.get(),
                ModItems.MOTHER_SNOW.get(),
                ModItems.MINERS_STAR.get(),
                ModItems.MADAME_SHEXIANG.get(),
                ModItems.SUNSET_GLOW.get(),
                ModItems.RIESLING_DRY_WHITE.get(),
                ModItems.SWEET_BERRY_WINE.get(),
                ModItems.VODKA.get(),
                ModItems.RUM.get()
        );

        tag(TagMod.TILTED_RACK_BLOCKLIST).add(
                ModItems.BRANDY.get(),
                ModItems.CARIGNAN.get()
        );

        tag(TagMod.HOLDER_BLOCKLIST).add(
                ModItems.BRANDY.get(),
                ModItems.CARIGNAN.get(),
                ModItems.MOTHER_SNOW.get(),
                ModItems.MINERS_STAR.get(),
                ModItems.MADAME_SHEXIANG.get(),
                ModItems.SUNSET_GLOW.get(),
                ModItems.RIESLING_DRY_WHITE.get(),
                ModItems.SWEET_BERRY_WINE.get(),
                ModItems.VODKA.get(),
                ModItems.RUM.get()
        );

        tag(TagMod.CIRCULAR_RACK_BLOCKLIST);

        tag(TagMod.COCKTAIL_INGREDIENT_GREEN).add(
                ModItems.SAUVIGNON_BLANC_DRY_WHITE.get(),
                ModItems.RIESLING_DRY_WHITE.get()
        );

        tag(TagMod.COCKTAIL_INGREDIENT_YELLOW).add(
                ModItems.LUMINOUS_BRIDE.get(),
                ModItems.GLOWFLOWER_BREW.get()
        );

        tag(TagMod.COCKTAIL_INGREDIENT_GOLD).add(
                ModItems.MINERS_STAR.get(),
                ModItems.HONEY_WINE.get(),
                ModItems.MADAME_SHEXIANG.get(),
                ModItems.SUNSET_GLOW.get()
        );

        tag(TagMod.COCKTAIL_INGREDIENT_RED).add(
                ModItems.PLUM_WINE.get(),
                ModItems.SWEET_BERRY_WINE.get(),
                ModItems.RED_QUEEN.get()
        );

        tag(TagMod.COCKTAIL_INGREDIENT_LIGHT_PURPLE).add(
                ModItems.WINE.get(),
                ModItems.CHAMPAGNE.get(),
                ModItems.SAKURA_WINE.get(),
                ModItems.BRANDY.get(),
                ModItems.CARIGNAN.get()
        );

        tag(TagMod.COCKTAIL_INGREDIENT_BLUE).add(
                ModItems.ICE_WINE.get(),
                ModItems.POLARIS_SWEET_WHITE.get(),
                ModItems.MOTHER_SNOW.get(),
                ModItems.SHERRY.get()
        );

        tag(TagMod.COCKTAIL_INGREDIENT_WHITE).add(
                ModItems.VODKA.get(),
                ModItems.WHISKEY.get(),
                ModItems.RUM.get()
        );

        tag(TagMod.COCKTAIL_INGREDIENT_BLACK);
        tag(TagMod.COCKTAIL_INGREDIENT_DARK_BLUE);
        tag(TagMod.COCKTAIL_INGREDIENT_DARK_GREEN);
        tag(TagMod.COCKTAIL_INGREDIENT_DARK_AQUA);
        tag(TagMod.COCKTAIL_INGREDIENT_DARK_RED);
        tag(TagMod.COCKTAIL_INGREDIENT_DARK_PURPLE);
        tag(TagMod.COCKTAIL_INGREDIENT_GRAY);
        tag(TagMod.COCKTAIL_INGREDIENT_DARK_GRAY);
        tag(TagMod.COCKTAIL_INGREDIENT_AQUA);

        tag(TagMod.COCKTAIL_INGREDIENT)
                .addTag(TagMod.COCKTAIL_INGREDIENT_BLACK)
                .addTag(TagMod.COCKTAIL_INGREDIENT_DARK_BLUE)
                .addTag(TagMod.COCKTAIL_INGREDIENT_DARK_GREEN)
                .addTag(TagMod.COCKTAIL_INGREDIENT_DARK_AQUA)
                .addTag(TagMod.COCKTAIL_INGREDIENT_DARK_RED)
                .addTag(TagMod.COCKTAIL_INGREDIENT_DARK_PURPLE)
                .addTag(TagMod.COCKTAIL_INGREDIENT_GOLD)
                .addTag(TagMod.COCKTAIL_INGREDIENT_GRAY)
                .addTag(TagMod.COCKTAIL_INGREDIENT_DARK_GRAY)
                .addTag(TagMod.COCKTAIL_INGREDIENT_BLUE)
                .addTag(TagMod.COCKTAIL_INGREDIENT_GREEN)
                .addTag(TagMod.COCKTAIL_INGREDIENT_AQUA)
                .addTag(TagMod.COCKTAIL_INGREDIENT_RED)
                .addTag(TagMod.COCKTAIL_INGREDIENT_LIGHT_PURPLE)
                .addTag(TagMod.COCKTAIL_INGREDIENT_YELLOW)
                .addTag(TagMod.COCKTAIL_INGREDIENT_WHITE);

        tag(TagCommon.FRUITS_GRAPES).add(
                ModItems.GRAPE.get()
        );

        tag(TagCommon.FRUITS).add(
                ModItems.GRAPE.get(),
                ModItems.ICE_GRAPE.get(),
                ModItems.GOLD_GRAPE.get(),
                ModItems.GREEN_GRAPE.get()
        );
    }
}
