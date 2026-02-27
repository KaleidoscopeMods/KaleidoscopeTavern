package com.github.ysbbbbbb.kaleidoscopetavern.item;

import com.github.ysbbbbbb.kaleidoscopetavern.api.blockentity.IBarrel;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BottleBlockItem extends BlockItem {
    public static final String BREW_LEVEL_KEY = "BrewLevel";

    public BottleBlockItem(Block block) {
        this(block, new Properties()
                .stacksTo(16));
    }

    public BottleBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    public static void setBrewLevel(ItemStack stack, int brewLevel) {
        stack.getOrCreateTag().putInt(BREW_LEVEL_KEY, brewLevel);
    }

    public static int getBrewLevel(ItemStack stack) {
        if (stack.getTag() != null && stack.getTag().contains(BREW_LEVEL_KEY)) {
            return stack.getTag().getInt(BREW_LEVEL_KEY);
        }
        return 0;
    }

    public ItemStack getFilledStack(int brewLevel) {
        ItemStack stack = new ItemStack(this);
        setBrewLevel(stack, brewLevel);
        return stack;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        int brewLevel = getBrewLevel(stack);
        if (0 < brewLevel) {
            brewLevel = Math.min(brewLevel, IBarrel.BREWING_FINISHED);
            Component brewLevelText = Component.translatable("message.kaleidoscope_tavern.barrel.brew_level.%d".formatted(brewLevel));
            tooltip.add(Component.translatable("tooltip.kaleidoscope_tavern.bottle_block.brew_level", brewLevelText).withStyle(ChatFormatting.GRAY));
        }
    }
}
