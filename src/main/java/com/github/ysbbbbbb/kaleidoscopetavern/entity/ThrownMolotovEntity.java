package com.github.ysbbbbbb.kaleidoscopetavern.entity;

import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.phys.HitResult;

@SuppressWarnings("all")
public class ThrownMolotovEntity extends ThrowableProjectile {
    public static final EntityType<ThrownMolotovEntity> TYPE = EntityType.Builder.<ThrownMolotovEntity>of(ThrownMolotovEntity::new, MobCategory.MISC)
            .sized(0.25F, 0.25F)
            .clientTrackingRange(4)
            .updateInterval(10)
            .build("thrown_molotov");

    public ThrownMolotovEntity(EntityType<? extends ThrowableProjectile> type, Level level) {
        super(type, level);
    }

    public ThrownMolotovEntity(Level level, LivingEntity shooter) {
        super(TYPE, shooter, level);
    }

    public ThrownMolotovEntity(Level level, double pX, double pY, double pZ) {
        super(TYPE, pX, pY, pZ, level);
    }

    @Override
    protected void defineSynchedData() {
    }

    public ItemStack getItem() {
        return new ItemStack(ModItems.MOLOTOV.get());
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);

        // 默认半径 3，范围内必定点燃；边缘外延 2 格范围内随机点燃，距离越远概率越低
        int radius = 3;

        if (!this.level().isClientSide) {
            // 在周围放火，圆形范围 + 边缘随机
            BlockPos center = this.blockPosition();
            var random = this.level().getRandom();

            for (int dx = -radius; dx <= radius; dx++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    double distSq = dx * dx + dz * dz;
                    double radiusSq = radius * radius;

                    // 半径内必定点燃；边缘外延 2 格范围内随机点燃，距离越远概率越低
                    if (distSq > radiusSq) {
                        double overshoot = Math.sqrt(distSq) - radius;
                        if (overshoot > 2 || random.nextFloat() >= (1.0F - (float) overshoot / 2.0F) * 0.6F) {
                            continue;
                        }
                    }

                    for (int dy = -1; dy <= 1; dy++) {
                        BlockPos pos = center.offset(dx, dy, dz);
                        if (this.level().isEmptyBlock(pos) && BaseFireBlock.canBePlacedAt(this.level(), pos, this.getDirection())) {
                            this.level().setBlockAndUpdate(pos, BaseFireBlock.getState(this.level(), pos));
                            break;
                        }
                    }
                }
            }

            // 播放音效和粒子
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                    SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 2.0F, 1.0F);
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                    SoundEvents.GLASS_BREAK, SoundSource.BLOCKS, 2.0F, 1.0F);

            if (this.level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.FLAME,
                        this.getX(), this.getY() + 0.5, this.getZ(),
                        30, radius, 1, radius, 0.1);
                serverLevel.sendParticles(ParticleTypes.SMOKE,
                        this.getX(), this.getY() + 0.5, this.getZ(),
                        20, radius, 1, radius, 0.1);
            }

            this.discard();
        }
    }
}
