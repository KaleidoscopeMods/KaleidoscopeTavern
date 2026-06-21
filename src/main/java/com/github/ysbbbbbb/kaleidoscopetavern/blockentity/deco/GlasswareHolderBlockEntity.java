package com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco;

import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.BaseBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.items.ItemStackHandler;

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

    @Override
    public AABB getRenderBoundingBox() {
        BlockPos pos = this.getBlockPos();
        return new AABB(pos, pos.offset(1, 1, 1));
    }
}
