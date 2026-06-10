package com.github.ysbbbbbb.kaleidoscopetavern.blockentity.mixology;

import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.BaseBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class ShakerBlockEntity extends BaseBlockEntity {
    public ShakerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.SHAKER_BE.get(), pos, state);
    }
}
