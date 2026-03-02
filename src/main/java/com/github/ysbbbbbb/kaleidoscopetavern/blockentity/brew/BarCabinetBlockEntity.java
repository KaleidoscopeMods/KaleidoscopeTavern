package com.github.ysbbbbbb.kaleidoscopetavern.blockentity.brew;

import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.BaseBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class BarCabinetBlockEntity extends BaseBlockEntity {
    /**
     * 酒柜左侧酒瓶。如果仅显示单个酒瓶的酒，则仅渲染左侧酒瓶
     */
    private ItemStack leftItem = ItemStack.EMPTY;
    /**
     * 酒柜右侧酒瓶。如果仅显示单个酒瓶的酒，则不渲染，也不能交互右侧酒瓶
     */
    private ItemStack rightItem = ItemStack.EMPTY;
    /**
     * 是否是异形酒瓶，异形酒瓶只能显示单个酒瓶的酒，并且只能交互左侧酒瓶
     */
    private boolean isSingle = false;

    public BarCabinetBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.BAR_CABINET_BE.get(), pos, state);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);

        if (tag.contains("left_item")) {
            this.leftItem = ItemStack.parseOptional(registries, tag.getCompound("left_item"));
        } else {
            this.leftItem = ItemStack.EMPTY;
        }

        if (tag.contains("right_item")) {
            this.rightItem = ItemStack.parseOptional(registries, tag.getCompound("right_item"));
        } else {
            this.rightItem = ItemStack.EMPTY;
        }

        this.isSingle = tag.getBoolean("is_single");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);

        if (!this.leftItem.isEmpty()) {
            tag.put("left_item", this.leftItem.save(registries, new CompoundTag()));
        }

        if (!this.rightItem.isEmpty()) {
            tag.put("right_item", this.rightItem.save(registries, new CompoundTag()));
        }

        tag.putBoolean("is_single", this.isSingle);
    }

    public ItemStack getLeftItem() {
        return leftItem;
    }

    public void setLeftItem(ItemStack leftItem) {
        this.leftItem = leftItem;
    }

    public ItemStack getRightItem() {
        return rightItem;
    }

    public void setRightItem(ItemStack rightItem) {
        this.rightItem = rightItem;
    }

    public void setSingle(boolean single) {
        isSingle = single;
    }

    public boolean isSingle() {
        return isSingle;
    }
}
