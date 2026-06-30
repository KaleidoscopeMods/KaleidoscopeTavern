package com.github.ysbbbbbb.kaleidoscopetavern.effect;

import com.github.ysbbbbbb.kaleidoscopetavern.init.ModEffects;
import com.github.ysbbbbbb.kaleidoscopetavern.init.tag.TagMod;
import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class ArdentHeatEffect extends BaseEffect {
    /**
     * 临时变量，用来暂存玩家撞击次数
     */
    private static final String COLLISION_COUNT_TAG = "kaleidoscope_tavern:ardent_heat_collision_count";
    /**
     * 玩家护甲列表
     */
    private static final EquipmentSlot[] ARMOR_SLOTS = {
            EquipmentSlot.HEAD, EquipmentSlot.CHEST,
            EquipmentSlot.LEGS, EquipmentSlot.FEET
    };

    public ArdentHeatEffect(int color) {
        super(color);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if (!(livingEntity instanceof Player player)) {
            return true;
        }
        Level level = player.level();
        if (level.isClientSide()) {
            return true;
        }

        // 即将过期时给予30秒饥饿效果
        MobEffectInstance instance = player.getEffect(ModEffects.ARDENT_HEAT);
        if (instance != null && instance.getDuration() <= 1) {
            player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 600, 0));
        }

        if (!player.isSprinting()) {
            return true;
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

        // 破坏方块时加速饥饿消耗（1.5 倍消耗速度）
        if (brokeBlocks) {
            player.causeFoodExhaustion(0.45F);

            List<EquipmentSlot> slots = Lists.newArrayList();
            for (EquipmentSlot slot : ARMOR_SLOTS) {
                ItemStack armor = player.getItemBySlot(slot);
                if (!armor.isEmpty()) {
                    slots.add(slot);
                }
            }

            if (!slots.isEmpty()) {
                // 撞墙时随机选择一个扣除耐久
                int index = level.random.nextInt(slots.size());
                EquipmentSlot selected = slots.get(index);
                ItemStack armor = player.getItemBySlot(selected);
                if (armor.isDamageableItem()) {
                    armor.hurtAndBreak(1, player, selected);
                }
            } else {
                // 不穿盔甲时，累计撞击 16 次扣 1 点伤害
                CompoundTag data = player.getPersistentData();
                int count = data.getInt(COLLISION_COUNT_TAG) + 1;
                if (count >= 16) {
                    player.hurt(player.damageSources().generic(), 1.0F);
                    count -= 16;
                }
                data.putInt(COLLISION_COUNT_TAG, count);
            }
        }
        return true;
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
