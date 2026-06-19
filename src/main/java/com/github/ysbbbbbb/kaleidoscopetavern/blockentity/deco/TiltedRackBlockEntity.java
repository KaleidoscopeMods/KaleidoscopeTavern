package com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco;

import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class TiltedRackBlockEntity extends StorageBlockEntity {
    public TiltedRackBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.TILTED_RACK_BE.get(), pos, state, 3);
    }
}
