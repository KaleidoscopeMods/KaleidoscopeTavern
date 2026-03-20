package com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco;

import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.BaseBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.entity.SitEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class BarStoolBlockEntity extends BaseBlockEntity {
    /**
     * 颜色，默认为白色，决定客户端渲染的材质
     */
    private final DyeColor color;
    /**
     * 缓存的 sit 实体，避免频繁查找实体导致的性能问题
     */
    private @Nullable SitEntity sitEntity = null;

    public BarStoolBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.BAR_STOOL_BE.get(), pos, state);
        this.color = DyeColor.WHITE;
    }

    public BarStoolBlockEntity(BlockPos pos, BlockState state, DyeColor color) {
        super(ModBlocks.BAR_STOOL_BE.get(), pos, state);
        this.color = color;
    }

    public void tick() {
        if (this.sitEntity == null) {
            return;
        }

        if (this.sitEntity.isRemoved()) {
            this.setSitEntity(null);
            return;
        }

        Entity entity = this.sitEntity.getFirstPassenger();
        if (!(entity instanceof LivingEntity)) {
            this.setSitEntity(null);
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);

        if (this.level != null) {
            int sitId = tag.getInt("SitEntityId");
            if (level.getEntity(sitId) instanceof SitEntity sit
                && sit.blockPosition().equals(this.worldPosition)
            ) {
                this.sitEntity = sit;
            } else {
                this.sitEntity = null;
            }
        } else {
            this.sitEntity = null;
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (this.sitEntity != null && this.sitEntity.isAlive()
            && this.sitEntity.blockPosition().equals(this.worldPosition)
        ) {
            tag.putInt("SitEntityId", this.sitEntity.getId());
        }
    }

    @Nullable
    public SitEntity getSitEntity() {
        return sitEntity;
    }

    public void setSitEntity(@Nullable SitEntity sitEntity) {
        this.sitEntity = sitEntity;
        this.refresh();
    }

    public DyeColor getColor() {
        return color;
    }
}