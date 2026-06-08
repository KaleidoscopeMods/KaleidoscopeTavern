package com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco;

import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.BaseBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;

public class TiltedRackBlockEntity extends BaseBlockEntity {
    private static final String ITEMS = "items";

    private final ItemStackHandler items = new ItemStackHandler(3) {
        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }
    };

    public TiltedRackBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.TILTED_RACK_BE.get(), pos, state);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains(ITEMS)) {
            items.deserializeNBT(tag.getCompound(ITEMS));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put(ITEMS, items.serializeNBT());
    }

    public ItemStackHandler getItems() {
        return items;
    }
}
