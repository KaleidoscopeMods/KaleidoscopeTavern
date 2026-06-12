package com.github.ysbbbbbb.kaleidoscopetavern.blockentity.mixology;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.BaseBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.datamap.data.DrinkEffectData;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class OrdinaryCocktailBlockEntity extends BaseBlockEntity {
    private static final Codec<List<DrinkEffectData.Entry>> EFFECTS_CODEC = Codec.list(DrinkEffectData.Entry.ENTRY_CODEC);

    private List<DrinkEffectData.Entry> effects = Lists.newArrayList();
    private int color = 0xFFFFFF;

    public OrdinaryCocktailBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.ORDINARY_COCKTAIL_BE.get(), pos, state);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("effects")) {
            ListTag tagList = tag.getList("effects", Tag.TAG_COMPOUND);
            EFFECTS_CODEC.decode(NbtOps.INSTANCE, tagList)
                    .resultOrPartial(KaleidoscopeTavern.LOGGER::error)
                    .ifPresent(r -> this.effects = r.getFirst());
        }
        if (tag.contains("color")) {
            this.color = tag.getInt("color");
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        EFFECTS_CODEC.encodeStart(NbtOps.INSTANCE, this.effects)
                .result()
                .ifPresent(r -> tag.put("effects", r));
        tag.putInt("color", this.color);
    }

    public List<DrinkEffectData.Entry> getEffects() {
        return effects;
    }

    public void setEffects(List<DrinkEffectData.Entry> effects) {
        this.effects = effects;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
