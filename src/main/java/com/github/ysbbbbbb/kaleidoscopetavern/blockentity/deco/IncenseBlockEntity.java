package com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco;

import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.BaseBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class IncenseBlockEntity extends BaseBlockEntity {
    public IncenseBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.INCENSE_BE.get(), pos, state);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, IncenseBlockEntity incense) {
        if (level.getGameTime() % 60 != 0) {
            return;
        }

        AABB area = new AABB(pos).inflate(32);
        List<LivingEntity> entities = level.getEntitiesOfClass(
                LivingEntity.class, area,
                e -> e.getMobType() == MobType.UNDEAD && e.isAlive()
        );

        for (LivingEntity entity : entities) {
            // 每 3 秒对亡灵生物造成 1 点魔法伤害
            entity.hurt(level.damageSources().magic(), 1.0f);

            // 僵尸村民血量低于5点时转化为普通村民
            if (entity instanceof ZombieVillager zombieVillager && zombieVillager.getHealth() <= 5.0f) {
                zombieVillager.startConverting(null, 60);
            }
        }
    }
}
