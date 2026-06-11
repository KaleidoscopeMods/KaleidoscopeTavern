package com.github.ysbbbbbb.kaleidoscopetavern.item;

import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ShakerItem extends BlockItem {
    private static final String STORAGE_TAG = "Storage";

    public ShakerItem() {
        super(ModBlocks.SHAKER.get(), new Properties());
    }

    public static ItemStackHandler getStorage(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        ItemStackHandler handler = new ItemStackHandler();
        handler.deserializeNBT(tag.getCompound(STORAGE_TAG));
        return handler;
    }

    public static void setStorage(ItemStack stack, ItemStackHandler handler) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.put(STORAGE_TAG, handler.serializeNBT());
    }

    public static boolean hasStorage(ItemStack stack) {
        return stack.getOrCreateTag().contains(STORAGE_TAG);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        if (!hasStorage(stack)) {
            return;
        }
        ItemStackHandler storage = getStorage(stack);
        for (int i = 0; i < storage.getSlots(); i++) {
            ItemStack ingredient = storage.getStackInSlot(i);
            if (ingredient.isEmpty()) {
                continue;
            }
            if (ingredient.getHoverName() instanceof MutableComponent component) {
                tooltip.add(component.withStyle(ChatFormatting.GRAY));
            }
        }
    }
}
