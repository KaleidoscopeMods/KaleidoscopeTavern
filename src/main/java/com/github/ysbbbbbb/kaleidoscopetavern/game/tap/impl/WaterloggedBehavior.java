package com.github.ysbbbbbb.kaleidoscopetavern.game.tap.impl;

import com.github.ysbbbbbb.kaleidoscopetavern.api.blockentity.ITapBehavior;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.WATERLOGGED;

/**
 * 很特殊的 Behavior，不走默认的注册机制
 */
public class WaterloggedBehavior implements ITapBehavior {
    @Override
    public boolean isMatch(
            Level level, @Nullable Player player,
            BlockPos tapPos, BlockState tapState,
            BlockState sourceState, BlockState destinationState
    ) {
        // 源方必须是含水方块
        if (!sourceState.hasProperty(WATERLOGGED) || !sourceState.getValue(WATERLOGGED)) {
            return false;
        }
        // 下方是空炼药锅、没有满的炼药锅，或者是空瓶
        if (destinationState.is(Blocks.CAULDRON) || destinationState.is(ModBlocks.EMPTY_BOTTLE.get())) {
            return true;
        }
        if (destinationState.is(Blocks.WATER_CAULDRON)) {
            return destinationState.getValue(BlockStateProperties.LEVEL_CAULDRON) < LayeredCauldronBlock.MAX_FILL_LEVEL;
        }
        return false;
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
        // 如果下方是空炼药锅，放水
        if (destinationState.is(Blocks.CAULDRON) || destinationState.is(Blocks.WATER_CAULDRON)) {
            BlockState state = Blocks.WATER_CAULDRON.defaultBlockState();
            state = state.setValue(BlockStateProperties.LEVEL_CAULDRON, LayeredCauldronBlock.MAX_FILL_LEVEL);
            level.setBlockAndUpdate(tapPos.below(), state);
            level.playSound(null, tapPos.below(), SoundEvents.AXOLOTL_SPLASH, SoundSource.BLOCKS, 1.0F, 1.0F);
            ITapBehavior.sendParticles(level, tapPos);
            return;
        }

        // 下方是空瓶
        if (destinationState.is(ModBlocks.EMPTY_BOTTLE.get())) {
            level.setBlockAndUpdate(tapPos.below(), ModBlocks.WATER_BOTTLE.get().defaultBlockState());
            level.playSound(null, tapPos.below(), SoundEvents.BREWING_STAND_BREW, SoundSource.BLOCKS, 1.0F, 1.0F);
            ITapBehavior.sendParticles(level, tapPos);
        }
    }
}
