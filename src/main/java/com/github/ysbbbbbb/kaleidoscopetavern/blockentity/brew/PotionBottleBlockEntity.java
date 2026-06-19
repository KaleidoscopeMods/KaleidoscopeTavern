package com.github.ysbbbbbb.kaleidoscopetavern.blockentity.brew;

import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.BaseBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class PotionBottleBlockEntity extends BaseBlockEntity {
    private static final String ITEM_KEY = "Item";
    private ItemStack potionStack = ItemStack.EMPTY;

    public PotionBottleBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.POTION_BOTTLE_BE.get(), pos, state);
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.potionStack = ItemStack.parseOptional(registries, tag.getCompound(ITEM_KEY));
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put(ITEM_KEY, this.potionStack.saveOptional(registries));
    }

    public ItemStack getPotionStack() {
        return this.potionStack;
    }

    public void setPotionStack(ItemStack stack) {
        this.potionStack = stack.copyWithCount(1);
        this.refresh();
    }
}
