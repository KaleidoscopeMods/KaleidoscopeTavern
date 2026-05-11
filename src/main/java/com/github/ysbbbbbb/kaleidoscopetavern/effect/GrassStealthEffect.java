package com.github.ysbbbbbb.kaleidoscopetavern.effect;

import com.github.ysbbbbbb.kaleidoscopetavern.init.tag.TagMod;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;

public class GrassStealthEffect extends BaseEffect {
    public GrassStealthEffect(int color) {
        super(color);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration % 10 == 0;
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if (!livingEntity.isShiftKeyDown() || !isInGrassStealthPlant(livingEntity)) {
            return;
        }
        livingEntity.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 20));
        if (livingEntity instanceof Player player) {
            player.causeFoodExhaustion(0.02F);
        }
    }

    private static boolean isInGrassStealthPlant(LivingEntity entity) {
        BlockPos pos = entity.blockPosition();
        Level level = entity.level();
        BlockState blockState = level.getBlockState(pos);
        if (blockState.getBlock() instanceof CropBlock cropBlock) {
            return cropBlock.isMaxAge(blockState);
        }
        return blockState.is(TagMod.GRASS_STEALTH_PLANTS);
    }
}
