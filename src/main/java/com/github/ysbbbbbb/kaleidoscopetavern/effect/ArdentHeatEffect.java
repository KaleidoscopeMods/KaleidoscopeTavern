package com.github.ysbbbbbb.kaleidoscopetavern.effect;

import com.github.ysbbbbbb.kaleidoscopetavern.init.tag.TagMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class ArdentHeatEffect extends BaseEffect {
    public ArdentHeatEffect(int color) {
        super(color);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if (!(livingEntity instanceof Player player)) {
            return;
        }
        Level level = player.level();
        if (level.isClientSide()) {
            return;
        }

        // 即将过期时给予30秒饥饿效果
        MobEffectInstance instance = player.getEffect(this);
        if (instance != null && instance.getDuration() <= 1) {
            player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 600, 0));
        }

        if (!player.isSprinting()) {
            return;
        }

        // 撞破面前 3×3 的石头
        Direction facing = player.getDirection();
        boolean brokeBlocks = false;

        for (BlockPos pos : getFrontBlocks(player.blockPosition(), facing)) {
            BlockState state = level.getBlockState(pos);
            if (state.is(TagMod.ARDENT_HEAT_BREAKABLE)) {
                level.destroyBlock(pos, true, player);
                brokeBlocks = true;
            }
        }

        // 破坏方块时加速饥饿消耗（300%增加=4倍总消耗）
        if (brokeBlocks) {
            player.causeFoodExhaustion(0.3F);
        }
    }

    /**
     * 获取玩家面前3×3区域的方块坐标（脚部高度起向上3格，左右各1格）
     */
    private static List<BlockPos> getFrontBlocks(BlockPos playerPos, Direction facing) {
        List<BlockPos> positions = new ArrayList<>();
        BlockPos center = playerPos.relative(facing);
        Direction.Axis facingAxis = facing.getAxis();

        for (int dy = 0; dy <= 2; dy++) {
            for (int d = -1; d <= 1; d++) {
                BlockPos pos;
                if (facingAxis == Direction.Axis.Z) {
                    pos = center.offset(d, dy, 0);
                } else {
                    pos = center.offset(0, dy, d);
                }
                positions.add(pos);
            }
        }
        return positions;
    }
}
