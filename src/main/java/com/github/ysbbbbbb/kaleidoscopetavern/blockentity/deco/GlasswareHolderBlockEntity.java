package com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco;

import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.BaseBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;

public class GlasswareHolderBlockEntity extends BaseBlockEntity {
    private static final String ITEMS = "items";

    private final ItemStackHandler items = new ItemStackHandler(4) {
        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }
    };

    public GlasswareHolderBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.GLASSWARE_HOLDER_BE.get(), pos, state);
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains(ITEMS)) {
            this.items.deserializeNBT(registries, tag.getCompound(ITEMS));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put(ITEMS, this.items.serializeNBT(registries));
    }

    public ItemStackHandler getItems() {
        return this.items;
    }
}
