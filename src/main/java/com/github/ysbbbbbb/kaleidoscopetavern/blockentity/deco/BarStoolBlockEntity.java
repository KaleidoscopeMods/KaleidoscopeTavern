package com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco;

import com.github.ysbbbbbb.kaleidoscopetavern.block.deco.BarStoolBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.BaseBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.entity.SitEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

public class BarStoolBlockEntity extends BaseBlockEntity {

    private static final String CACHE_ROT_KEY = "CacheRot";
    private static final float ROTATE_SYNC_THRESHOLD = 0.35F;
    // 颜色,默认为白色
    private final DyeColor color;
    private float cachedRot;

    public BarStoolBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.BAR_STOOL_BE.get(), pos, state);
        this.color = DyeColor.WHITE;
        this.cachedRot = getInitialRot(state);
    }

    public BarStoolBlockEntity(BlockPos pos, BlockState state, DyeColor color) {
        super(ModBlocks.BAR_STOOL_BE.get(), pos, state);
        this.color = color;
        this.cachedRot = getInitialRot(state);
    }

    public float getCachedRot() {
        return cachedRot;
    }

    public DyeColor getColor() {
        return color;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, BarStoolBlockEntity blockEntity) {
        blockEntity.serverTick(level, pos);
    }

    private void serverTick(Level level, BlockPos pos) {
        if (level.isClientSide()) {
            return;
        }
        Entity passenger = findPassenger(level, pos);
        if (passenger == null) {
            return;
        }
        float targetRot = Mth.wrapDegrees(getBodyRot(passenger));
        float diff = Mth.degreesDifferenceAbs(this.cachedRot, targetRot);
        if (diff < ROTATE_SYNC_THRESHOLD) {
            return;
        }
        this.cachedRot = targetRot;
        this.refresh();
    }

    @Override
    public void load(CompoundTag compoundTag) {
        super.load(compoundTag);
        if (compoundTag.contains(CACHE_ROT_KEY, CompoundTag.TAG_FLOAT)) {
            this.cachedRot = Mth.wrapDegrees(compoundTag.getFloat(CACHE_ROT_KEY));
            return;
        }
        if (compoundTag.contains(CACHE_ROT_KEY, CompoundTag.TAG_INT)) {
            this.cachedRot = Mth.wrapDegrees(compoundTag.getInt(CACHE_ROT_KEY));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);
        compoundTag.putFloat(CACHE_ROT_KEY, Mth.wrapDegrees(this.cachedRot));
    }


    private static @Nullable Entity findPassenger(Level level, BlockPos pos) {
        for (SitEntity sitEntity : level.getEntitiesOfClass(SitEntity.class, new AABB(pos))) {
            if (!sitEntity.isAlive() || sitEntity.getPassengers().isEmpty()) {
                continue;
            }
            return sitEntity.getFirstPassenger();
        }
        return null;
    }

    private static float getInitialRot(BlockState state) {
        if (!state.hasProperty(BarStoolBlock.FACING)) {
            return 0.0F;
        }
        return state.getValue(BarStoolBlock.FACING).toYRot();
    }

    private static float getBodyRot(Entity passenger) {
        if (passenger instanceof LivingEntity livingEntity) {
            return livingEntity.yBodyRot;
        }
        return passenger.getYRot();
    }
}
