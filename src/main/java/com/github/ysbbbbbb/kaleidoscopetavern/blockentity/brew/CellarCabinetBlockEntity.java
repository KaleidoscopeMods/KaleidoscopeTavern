package com.github.ysbbbbbb.kaleidoscopetavern.blockentity.brew;

import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco.StorageBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class CellarCabinetBlockEntity extends StorageBlockEntity {
    public CellarCabinetBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.CELLAR_CABINET_BE.get(), pos, state, 9);
    }
}

