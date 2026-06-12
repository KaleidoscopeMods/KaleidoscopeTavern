package com.github.ysbbbbbb.kaleidoscopetavern.client.render.misc;

import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.mixology.OrdinaryCocktailBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.item.OrdinaryCocktailBlockItem;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class OrdinaryCocktailColor {
    public static class Block implements BlockColor {
        @Override
        public int getColor(BlockState state, @Nullable BlockAndTintGetter level, @Nullable BlockPos pos, int tintIndex) {
            if (tintIndex != 0 || level == null || pos == null) {
                return 0xFFFFFF;
            }
            if (level.getBlockEntity(pos) instanceof OrdinaryCocktailBlockEntity be) {
                return be.getColor();
            }
            return 0xFFFFFF;
        }
    }

    public static class Item implements ItemColor {
        @Override
        public int getColor(ItemStack stack, int tintIndex) {
            if (tintIndex != 1) {
                return 0xFFFFFF;
            }
            return OrdinaryCocktailBlockItem.getColor(stack);
        }
    }
}
