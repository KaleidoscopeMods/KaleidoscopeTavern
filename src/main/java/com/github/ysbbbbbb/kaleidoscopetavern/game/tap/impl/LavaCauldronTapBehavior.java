package com.github.ysbbbbbb.kaleidoscopetavern.game.tap.impl;

import com.github.ysbbbbbb.kaleidoscopetavern.api.blockentity.ITapBehavior;
import com.github.ysbbbbbb.kaleidoscopetavern.config.GeneralConfig;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class LavaCauldronTapBehavior implements ITapBehavior {
    @Override
    public boolean isMatch(
            Level level, @Nullable Player player,
            BlockPos tapPos, BlockState tapState,
            BlockState sourceState, BlockState destinationState
    ) {
        if (GeneralConfig.INFINITE_LAVA_FROM_TAP.get()) {
            // 如果配置允许无限水龙头取熔岩，那么只要下方是炼药锅或者空瓶就行
            return destinationState.is(Blocks.CAULDRON) || destinationState.is(ModBlocks.EMPTY_BOTTLE.get());
        }
        // 否则只能取燃烧瓶
        return destinationState.is(ModBlocks.EMPTY_BOTTLE.get());
    }

    @Override
    @Nullable
    public ParticleOptions onStartExtract(
            Level level, @Nullable Player player,
            BlockPos tapPos, BlockState tapState,
            BlockState sourceState, BlockState destinationState
    ) {
        return ModParticles.LAVA_TAP_DRIP.get();
    }

    @Override
    public void onEndExtract(
            Level level,
            BlockPos tapPos, BlockState tapState,
            BlockState sourceState, BlockState destinationState
    ) {
        // 如果下方是空炼药锅
        if (GeneralConfig.INFINITE_LAVA_FROM_TAP.get() && destinationState.is(Blocks.CAULDRON)) {
            level.setBlockAndUpdate(tapPos.below(), Blocks.LAVA_CAULDRON.defaultBlockState());
            level.playSound(null, tapPos.below(), SoundEvents.LAVA_POP, SoundSource.BLOCKS, 1.0F, 1.0F);
            ITapBehavior.sendParticles(level, tapPos);
            return;
        }

        // 下方是空瓶
        if (destinationState.is(ModBlocks.EMPTY_BOTTLE.get())) {
            level.setBlockAndUpdate(tapPos.below(), ModBlocks.MOLOTOV.get().defaultBlockState());
            level.playSound(null, tapPos.below(), SoundEvents.BREWING_STAND_BREW, SoundSource.BLOCKS, 1.0F, 1.0F);
            ITapBehavior.sendParticles(level, tapPos);
        }
    }
}
