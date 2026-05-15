package com.github.ysbbbbbb.kaleidoscopetavern.game.tap.impl;

import com.github.ysbbbbbb.kaleidoscopetavern.api.blockentity.ITapBehavior;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.brew.DrinkBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class WatermelonTapBehavior implements ITapBehavior {
    @Override
    public boolean isMatch(
            Level level, @Nullable Player player,
            BlockPos tapPos, BlockState tapState,
            BlockState sourceState, BlockState destinationState
    ) {
        return destinationState.is(ModBlocks.EMPTY_BOTTLE.get());
    }

    @Override
    @Nullable
    public ParticleOptions onStartExtract(
            Level level, @Nullable Player player,
            BlockPos tapPos, BlockState tapState,
            BlockState sourceState, BlockState destinationState
    ) {
        return ModParticles.WATER_TAP_DRIP.get();
    }

    @Override
    public void onEndExtract(
            Level level,
            BlockPos tapPos, BlockState tapState,
            BlockState sourceState, BlockState destinationState
    ) {
        // 下方是空瓶
        if (destinationState.is(ModBlocks.EMPTY_BOTTLE.get())) {
            level.setBlockAndUpdate(tapPos.below(), ModBlocks.WATERMELON_JUICE.get().defaultBlockState());
            // 需要额外设置数量
            if (level.getBlockEntity(tapPos.below()) instanceof DrinkBlockEntity drink) {
                drink.addItem(ModItems.WATERMELON_JUICE.get().getDefaultInstance());
                drink.refresh();
            }
            level.playSound(null, tapPos.below(), SoundEvents.BREWING_STAND_BREW, SoundSource.BLOCKS, 1.0F, 1.0F);
            ITapBehavior.sendParticles(level, tapPos);
        }
    }
}
