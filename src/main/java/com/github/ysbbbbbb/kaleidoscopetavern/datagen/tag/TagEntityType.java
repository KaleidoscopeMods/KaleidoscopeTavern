package com.github.ysbbbbbb.kaleidoscopetavern.datagen.tag;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.init.tag.TagMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class TagEntityType extends EntityTypeTagsProvider {
    public TagEntityType(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                         @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, KaleidoscopeTavern.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(TagMod.TOMB_RAIDER_DISARMABLE)
                // 骷髅类（骷髅、流浪者、凋零骷髅）
                .addTag(EntityTypeTags.SKELETONS)
                // 僵尸类
                .addTag(EntityTypeTags.ZOMBIES)
                // 猪灵类
                .add(EntityType.PIGLIN, EntityType.PIGLIN_BRUTE, EntityType.ZOMBIFIED_PIGLIN)
                // 灾厄村民
                .add(EntityType.VINDICATOR, EntityType.PILLAGER)
                // 女巫
                .add(EntityType.WITCH);
    }
}
