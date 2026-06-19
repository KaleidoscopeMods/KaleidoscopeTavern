package com.github.ysbbbbbb.kaleidoscopetavern.block.mixology;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class MysteryCocktailBlock extends CocktailBlock {
    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        double x = pos.getX() + 0.5;
        double y = pos.getY() + 0.5;
        double z = pos.getZ() + 0.5;

        double xSpeed = random.nextDouble();
        double ySpeed = random.nextDouble();
        double zSpeed = random.nextDouble();

        level.addParticle(ParticleTypes.EFFECT,
                x + random.nextDouble() / 5 * (random.nextBoolean() ? 1 : -1),
                y + random.nextDouble() / 5,
                z + random.nextDouble() / 5 * (random.nextBoolean() ? 1 : -1),
                xSpeed, ySpeed, zSpeed
        );
    }
}
