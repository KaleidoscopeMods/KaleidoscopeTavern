package com.github.ysbbbbbb.kaleidoscopetavern.block.brew;

import com.github.ysbbbbbb.kaleidoscopetavern.entity.ThrownMolotovEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class MolotovBlock extends BottleBlock {
    @Override
    public void onProjectileHit(Level level, BlockState state, BlockHitResult hit, Projectile projectile) {
        if (!level.isClientSide) {
            // 原地生成一个燃烧瓶实体
            BlockPos pos = hit.getBlockPos();
            ThrownMolotovEntity molotov = new ThrownMolotovEntity(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
            level.addFreshEntity(molotov);
        }
        super.onProjectileHit(level, state, hit, projectile);
    }
}
