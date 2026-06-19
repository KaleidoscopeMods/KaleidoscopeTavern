package com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco;

import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class HolderBlockEntity extends StorageBlockEntity {
    public HolderBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.HOLDER_BE.get(), pos, state, 1);
    }
}

