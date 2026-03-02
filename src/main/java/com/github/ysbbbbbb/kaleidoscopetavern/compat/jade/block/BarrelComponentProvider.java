package com.github.ysbbbbbb.kaleidoscopetavern.compat.jade.block;

import com.github.ysbbbbbb.kaleidoscopetavern.block.brew.BarrelBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.brew.BarrelBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.compat.jade.ModPlugin;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringUtil;
import net.minecraft.world.item.ItemStack;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum BarrelComponentProvider implements IBlockComponentProvider {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig pluginConfig) {
        BarrelBlockEntity be = BarrelBlock.getBarrelEntity(accessor.getLevel(), accessor.getPosition(), accessor.getBlockState());
        if (be == null) {
            return;
        }

        int brewLevel = be.getBrewLevel();
        ItemStack result = be.getOutput().getStackInSlot(0);

        Component resultText = Component.translatable("jade.plugin_kaleidoscope_tavern.item_and_count", result.getHoverName(), result.getCount());
        Component levelText = Component.translatable("message.kaleidoscope_tavern.barrel.brew_level.%d".formatted(brewLevel));

        if (!be.isBrewing()) {
            Component message = Component.translatable("message.kaleidoscope_tavern.barrel.not_brewing");
            tooltip.add(message);
            return;
        }

        if (be.isMaxBrewLevel()) {
            tooltip.add(resultText);
            tooltip.add(Component.translatable("jade.plugin_kaleidoscope_tavern.barrel.level", levelText));
            tooltip.add(Component.translatable("jade.plugin_kaleidoscope_tavern.barrel.max_level").withStyle(ChatFormatting.GOLD));
            return;
        }

        Component timeText = Component.literal(StringUtil.formatTickDuration(Math.max(be.getBrewTime(), 0), 20));

        tooltip.add(resultText);
        tooltip.add(Component.translatable("jade.plugin_kaleidoscope_tavern.barrel.level", levelText));
        tooltip.add(Component.translatable("jade.plugin_kaleidoscope_tavern.barrel.next_level", timeText));
    }

    @Override
    public ResourceLocation getUid() {
        return ModPlugin.BARREL;
    }
}
