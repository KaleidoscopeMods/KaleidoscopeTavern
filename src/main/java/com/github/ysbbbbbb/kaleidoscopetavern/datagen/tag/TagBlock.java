package com.github.ysbbbbbb.kaleidoscopetavern.datagen.tag;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import com.github.ysbbbbbb.kaleidoscopetavern.init.tag.TagMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class TagBlock extends BlockTagsProvider {
    public TagBlock(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, KaleidoscopeTavern.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(TagMod.SOFA).add(
                ModBlocks.WHITE_SOFA.get(),
                ModBlocks.LIGHT_GRAY_SOFA.get(),
                ModBlocks.GRAY_SOFA.get(),
                ModBlocks.BLACK_SOFA.get(),
                ModBlocks.BROWN_SOFA.get(),
                ModBlocks.RED_SOFA.get(),
                ModBlocks.ORANGE_SOFA.get(),
                ModBlocks.YELLOW_SOFA.get(),
                ModBlocks.LIME_SOFA.get(),
                ModBlocks.GREEN_SOFA.get(),
                ModBlocks.CYAN_SOFA.get(),
                ModBlocks.LIGHT_BLUE_SOFA.get(),
                ModBlocks.BLUE_SOFA.get(),
                ModBlocks.PURPLE_SOFA.get(),
                ModBlocks.MAGENTA_SOFA.get(),
                ModBlocks.PINK_SOFA.get()
        );

        this.tag(TagMod.BAR_STOOL).add(
                ModBlocks.WHITE_BAR_STOOL.get(),
                ModBlocks.LIGHT_GRAY_BAR_STOOL.get(),
                ModBlocks.GRAY_BAR_STOOL.get(),
                ModBlocks.BLACK_BAR_STOOL.get(),
                ModBlocks.BROWN_BAR_STOOL.get(),
                ModBlocks.RED_BAR_STOOL.get(),
                ModBlocks.ORANGE_BAR_STOOL.get(),
                ModBlocks.YELLOW_BAR_STOOL.get(),
                ModBlocks.LIME_BAR_STOOL.get(),
                ModBlocks.GREEN_BAR_STOOL.get(),
                ModBlocks.CYAN_BAR_STOOL.get(),
                ModBlocks.LIGHT_BLUE_BAR_STOOL.get(),
                ModBlocks.BLUE_BAR_STOOL.get(),
                ModBlocks.PURPLE_BAR_STOOL.get(),
                ModBlocks.MAGENTA_BAR_STOOL.get(),
                ModBlocks.PINK_BAR_STOOL.get()
        );

        this.tag(TagMod.SANDWICH_BOARD).add(
                ModBlocks.BASE_SANDWICH_BOARD.get(),
                ModBlocks.GRASS_SANDWICH_BOARD.get(),
                ModBlocks.ALLIUM_SANDWICH_BOARD.get(),
                ModBlocks.AZURE_BLUET_SANDWICH_BOARD.get(),
                ModBlocks.CORNFLOWER_SANDWICH_BOARD.get(),
                ModBlocks.ORCHID_SANDWICH_BOARD.get(),
                ModBlocks.PEONY_SANDWICH_BOARD.get(),
                ModBlocks.PINK_PETALS_SANDWICH_BOARD.get(),
                ModBlocks.PITCHER_PLANT_SANDWICH_BOARD.get(),
                ModBlocks.POPPY_SANDWICH_BOARD.get(),
                ModBlocks.SUNFLOWER_SANDWICH_BOARD.get(),
                ModBlocks.TORCHFLOWER_SANDWICH_BOARD.get(),
                ModBlocks.TULIP_SANDWICH_BOARD.get(),
                ModBlocks.WITHER_ROSE_SANDWICH_BOARD.get()
        );

        this.tag(TagMod.STRING_LIGHTS).add(
                ModBlocks.STRING_LIGHTS_COLORLESS.get(),
                ModBlocks.STRING_LIGHTS_WHITE.get(),
                ModBlocks.STRING_LIGHTS_LIGHT_GRAY.get(),
                ModBlocks.STRING_LIGHTS_GRAY.get(),
                ModBlocks.STRING_LIGHTS_BLACK.get(),
                ModBlocks.STRING_LIGHTS_BROWN.get(),
                ModBlocks.STRING_LIGHTS_RED.get(),
                ModBlocks.STRING_LIGHTS_ORANGE.get(),
                ModBlocks.STRING_LIGHTS_YELLOW.get(),
                ModBlocks.STRING_LIGHTS_LIME.get(),
                ModBlocks.STRING_LIGHTS_GREEN.get(),
                ModBlocks.STRING_LIGHTS_CYAN.get(),
                ModBlocks.STRING_LIGHTS_LIGHT_BLUE.get(),
                ModBlocks.STRING_LIGHTS_BLUE.get(),
                ModBlocks.STRING_LIGHTS_PURPLE.get(),
                ModBlocks.STRING_LIGHTS_MAGENTA.get(),
                ModBlocks.STRING_LIGHTS_PINK.get()
        );

        this.tag(TagMod.PAINTING).add(
                ModBlocks.YSBB_PAINTING.get(),
                ModBlocks.TARTARIC_ACID_PAINTING.get(),
                ModBlocks.CR019_PAINTING.get(),
                ModBlocks.UNKNOWN_PAINTING.get(),
                ModBlocks.MASTER_MARISA_PAINTING.get(),
                ModBlocks.SON_OF_MAN_PAINTING.get(),
                ModBlocks.DAVID_PAINTING.get(),
                ModBlocks.GIRL_WITH_PEARL_EARRING_PAINTING.get(),
                ModBlocks.STARRY_NIGHT_PAINTING.get(),
                ModBlocks.VAN_GOGH_SELF_PORTRAIT_PAINTING.get(),
                ModBlocks.FATHER_PAINTING.get(),
                ModBlocks.GREAT_WAVE_PAINTING.get(),
                ModBlocks.MONA_LISA_PAINTING.get(),
                ModBlocks.MONDRIAN_PAINTING.get()
        );

        this.tag(TagMod.SITTABLE)
                .addTag(TagMod.SOFA)
                .addTag(TagMod.BAR_STOOL);

        this.tag(BlockTags.MINEABLE_WITH_AXE)
                .addTag(TagMod.SOFA)
                .addTag(TagMod.BAR_STOOL)
                .addTag(TagMod.SANDWICH_BOARD)
                .addTag(TagMod.PAINTING)
                .add(ModBlocks.BAR_COUNTER.get());

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .addTag(TagMod.STRING_LIGHTS);
    }
}
