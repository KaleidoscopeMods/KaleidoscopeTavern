package com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco;

import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class CircularRackBlockEntity extends StorageBlockEntity {
    public CircularRackBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.CIRCULAR_RACK_BE.get(), pos, state, 6);
    }
}

