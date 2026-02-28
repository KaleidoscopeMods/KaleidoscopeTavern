package com.github.ysbbbbbb.kaleidoscopetavern.blockentity.brew;

import com.github.ysbbbbbb.kaleidoscopetavern.block.brew.TapBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.BaseBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class TapBlockEntity extends BaseBlockEntity {
    /**
     * 龙头取酒耗时 80 tick，前 38 tick 会有粒子效果，后 42 tick 没有粒子效果
     */
    public static final int TAKE_DRINK_TICKS = 80;
    public static final int TAKE_DRINK_PARTICLE_TICKS = 38;
    /**
     * 空拧状态，此时只会持续一小段时间
     */
    public static final int EMPTY_OPEN_TICKS = 5;

    /**
     * 龙头有不同的状态
     * 0：默认状态，什么也不做
     * 1：取酒状态，此时会正常触发滴水粒子效果
     * 2：空拧状态，此时会产生几个 cloud 粒子效果
     */
    public static final int DEFAULT_STATE = 0;
    public static final int TAKE_DRINK_STATE = 1;
    public static final int EMPTY_OPEN_STATE = 2;

    private int state = 0;
    private @Nullable ParticleOptions particle = null;
    private int tickCounter = 0;

    public TapBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.TAP_BE.get(), pos, state);
    }

    public void tick(Level level) {
        // 如果自己的状态是关闭状态，不 tick
        BlockState blockState = this.getBlockState();
        if (!blockState.getValue(TapBlock.OPEN)) {
            return;
        }

        this.tickCounter++;

        if (this.state == TAKE_DRINK_STATE) {
            this.onTakeDrink(level);
        } else if (state == EMPTY_OPEN_STATE) {
            this.onEmptyOpen(level);
        }
    }

    private void onTakeDrink(Level level) {
        if (this.particle == null) {
            return;
        }
        // 以防万一，如果超过了取酒的总耗时，强制把状态切换会默认
        if (this.tickCounter > TAKE_DRINK_TICKS) {
            this.state = DEFAULT_STATE;
            this.particle = null;
            return;
        }
        // 只有前 TAKE_DRINK_PARTICLE_TICKS tick 会生成粒子
        if (this.tickCounter > TAKE_DRINK_PARTICLE_TICKS) {
            return;
        }
        if (level instanceof ServerLevel serverLevel) {
            BlockPos pos = this.getBlockPos();
            serverLevel.sendParticles(particle,
                    pos.getX() + 0.5, pos.getY() + 0.25, pos.getZ() + 0.5,
                    1, 0, 0, 0, 0);
        }
    }

    private void onEmptyOpen(Level level) {
        BlockPos pos = this.getBlockPos();
        if (level instanceof ServerLevel serverLevel && this.tickCounter % 2 == 0) {
            serverLevel.sendParticles(ParticleTypes.CLOUD,
                    pos.getX() + 0.5, pos.getY() + 0.25, pos.getZ() + 0.5,
                    1, 0.1, 0.1, 0.1, 0.01);
        }
        // 空拧状态持续 EMPTY_OPEN_TICKS tick 就切回默认状态
        if (this.tickCounter > EMPTY_OPEN_TICKS) {
            this.state = DEFAULT_STATE;
            this.particle = null;

            // 将龙头设置为关闭状态
            BlockState blockState = this.getBlockState();
            blockState = blockState.setValue(TapBlock.OPEN, false);
            level.setBlockAndUpdate(pos, blockState);
            level.playSound(null, pos, SoundEvents.IRON_TRAPDOOR_CLOSE, SoundSource.BLOCKS, 1.0F, 0.8F);
        }
    }

    public void setParticle(@Nullable ParticleOptions particle) {
        this.particle = particle;
        this.tickCounter = 0;
    }

    public void setState(int state) {
        this.state = state;
        this.tickCounter = 0;
    }
}
