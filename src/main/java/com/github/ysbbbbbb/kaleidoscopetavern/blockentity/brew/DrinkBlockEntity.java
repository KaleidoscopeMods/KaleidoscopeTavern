package com.github.ysbbbbbb.kaleidoscopetavern.blockentity.brew;

import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.BaseBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

@SuppressWarnings("deprecation")
public class DrinkBlockEntity extends BaseBlockEntity {
    // 实际上这个物品列表的大小应该是可变的，但为了简化实现，我们暂时将其固定为4个。
    private final NonNullList<ItemStack> items = NonNullList.withSize(4, ItemStack.EMPTY);

    public DrinkBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.DRINK_BE.get(), pos, state);
    }

    public boolean addItem(ItemStack stack) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).isEmpty()) {
                items.set(i, stack.copyWithCount(1));
                return true;
            }
        }
        // 没有空位了，一般来说应该在外面调用这个方法之前就检查过了
        // 但为了安全起见，我们返回 false
        return false;
    }

    public ItemStack removeItem() {
        // 倒序取出最后一个物品
        for (int i = items.size() - 1; i >= 0; i--) {
            if (!items.get(i).isEmpty()) {
                ItemStack stack = items.get(i);
                items.set(i, ItemStack.EMPTY);
                return stack.copyWithCount(1);
            }
        }
        // 没有物品了，返回空的 ItemStack
        return ItemStack.EMPTY;
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        ContainerHelper.loadAllItems(tag, this.items);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        ContainerHelper.saveAllItems(tag, this.items);
    }

    public NonNullList<ItemStack> getItems() {
        return items;
    }
}
