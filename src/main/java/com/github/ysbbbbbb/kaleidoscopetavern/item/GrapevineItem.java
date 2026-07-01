package com.github.ysbbbbbb.kaleidoscopetavern.item;

import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class GrapevineItem extends ItemNameBlockItem {
    public GrapevineItem() {
        super(ModBlocks.WILD_GRAPEVINE.get(), new Item.Properties());
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.kaleidoscope_tavern.grapevine.1").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.kaleidoscope_tavern.grapevine.2").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.kaleidoscope_tavern.grapevine.3").withStyle(ChatFormatting.GRAY));
    }
}
