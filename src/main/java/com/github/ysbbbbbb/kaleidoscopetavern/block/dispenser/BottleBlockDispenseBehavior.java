package com.github.ysbbbbbb.kaleidoscopetavern.block.dispenser;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.item.BottleBlockItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.DirectionalPlaceContext;
import net.minecraft.world.level.block.DispenserBlock;
import org.jetbrains.annotations.NotNull;

public class BottleBlockDispenseBehavior extends OptionalDispenseItemBehavior {
    @Override
    protected @NotNull ItemStack execute(BlockSource source, ItemStack stack) {
        this.setSuccess(false);
        ServerLevel level = source.level();
        if (stack.getItem() instanceof BottleBlockItem bottleBlockItem) {
            Direction facing = source.state().getValue(DispenserBlock.FACING);
            BlockPos placePos = source.pos().relative(facing);
            Direction placeDirection = facing.getAxis() == Direction.Axis.Y ? Direction.NORTH : facing;
            try {
                DirectionalPlaceContext context = new DirectionalPlaceContext(level, placePos, facing, stack, placeDirection);
                this.setSuccess(bottleBlockItem.place(context).consumesAction());
            } catch (Exception exception) {
                KaleidoscopeTavern.LOGGER.error("Error trying to place bottle block item from dispenser", exception);
            }
        }
        return stack;
    }
}
