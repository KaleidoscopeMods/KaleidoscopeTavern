package com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco;

import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class ChalkboardBlockEntity extends TextBlockEntity {
    /**
     * 这个黑板是否是大型的（3x2），否则就是普通的（1x2）
     */
    private boolean isLarge = false;

    public ChalkboardBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlocks.CHALKBOARD_BE.get(), pos, blockState);
    }

    public static ChalkboardBlockEntity small(BlockPos pos, BlockState blockState) {
        ChalkboardBlockEntity be = new ChalkboardBlockEntity(pos, blockState);
        be.setLarge(false);
        return be;
    }

    public static ChalkboardBlockEntity large(BlockPos pos, BlockState blockState) {
        ChalkboardBlockEntity be = new ChalkboardBlockEntity(pos, blockState);
        be.setLarge(true);
        return be;
    }

    @Override
    public int getMaxTextLength() {
        return this.isLarge() ? 1500 : 350;
    }


    public boolean isLarge() {
        return isLarge;
    }

    public void setLarge(boolean large) {
        isLarge = large;
    }
}
