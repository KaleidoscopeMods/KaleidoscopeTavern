package com.github.ysbbbbbb.kaleidoscopetavern.effect;

import com.github.ysbbbbbb.kaleidoscopetavern.init.tag.TagMod;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class GrassStealthEffect extends BaseEffect {
    public GrassStealthEffect(int color) {
        super(color);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return duration % 10 == 0;
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if (!livingEntity.isShiftKeyDown()) {
            return true;
        }
        Level level = livingEntity.level();
        BlockPos pos = livingEntity.blockPosition();
        BlockPos abovePos = pos.above();
        if (notInGrassStealthPlant(level, pos) && notInGrassStealthPlant(level, abovePos)) {
            return true;
        }
        if (livingEntity instanceof Player player) {
            player.causeFoodExhaustion(0.1F);
        }
        // 清除周围生物对玩家的敌意
        level.getEntitiesOfClass(Mob.class, new AABB(pos).inflate(32)).forEach(e -> {
            if (e.getTarget() == livingEntity) {
                e.setTarget(null);
            }
        });
        return true;
    }

    public static boolean notInGrassStealthPlant(Level level, BlockPos pos) {
        BlockState blockState = level.getBlockState(pos);
        if (blockState.getBlock() instanceof CropBlock cropBlock) {
            return !cropBlock.isMaxAge(blockState);
        }
        return !blockState.is(TagMod.GRASS_STEALTH_PLANTS);
    }
}
