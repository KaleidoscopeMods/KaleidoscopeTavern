package com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco;

import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.BaseBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;

public abstract class StorageBlockEntity extends BaseBlockEntity {
    private static final String ITEMS = "items";
    private final ItemStackHandler items;

    protected StorageBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int size) {
        super(type, pos, state);
        this.items = new ItemStackHandler(size) {
            @Override
            public int getSlotLimit(int slot) {
                return 1;
            }
        };
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains(ITEMS)) {
            this.items.deserializeNBT(tag.getCompound(ITEMS));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put(ITEMS, this.items.serializeNBT());
    }

    public ItemStackHandler getItems() {
        return this.items;
    }

    public boolean hasAnyItem() {
        for (int i = 0; i < this.items.getSlots(); i++) {
            if (!this.items.getStackInSlot(i).isEmpty()) {
                return true;
            }
        }
        return false;
    }
}
