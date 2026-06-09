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
