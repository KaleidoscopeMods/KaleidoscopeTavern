package com.github.ysbbbbbb.kaleidoscopetavern.entity;


import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.init.tag.TagMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;

@SuppressWarnings("all")
public class SitEntity extends Entity {
    public static final EntityType<SitEntity> TYPE = EntityType.Builder.<SitEntity>of(SitEntity::new, MobCategory.MISC)
            .sized(0.5f, 0.1f)
            .clientTrackingRange(10)
            .noSummon()
            .build(ResourceKey.create(Registries.ENTITY_TYPE,
                    Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "sit")));
    private int passengerTick = 0;

    public SitEntity(EntityType<?> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn);
    }

    public SitEntity(Level worldIn, BlockPos pos) {
        this(TYPE, worldIn);
        this.setPos(pos.getX() + 0.5, pos.getY() + 0.4375, pos.getZ() + 0.5);
    }

    public SitEntity(Level worldIn, BlockPos pos, double y) {
        this(TYPE, worldIn);
        this.setPos(pos.getX() + 0.5, pos.getY() + y, pos.getZ() + 0.5);
    }

    @Override
    public Vec3 getPassengerRidingPosition(Entity entity) {
        return super.getPassengerRidingPosition(entity).add(0, -0.0625, 0);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
    }

    @Override
    public void tick() {
        if (!this.level().isClientSide()) {
            this.checkBelowWorld();
            this.checkPassengers();

            // 每秒检查一次所处位置是否有方块，没有就删除实体
            if (this.tickCount % 20 == 0) {
                BlockState blockState = this.level().getBlockState(this.blockPosition());
                if (!blockState.is(TagMod.SITTABLE)) {
                    this.discard();
                }
            }
        }
    }

    private void checkPassengers() {
        if (this.getPassengers().isEmpty()) {
            passengerTick++;
        } else {
            passengerTick = 0;
        }
        if (passengerTick > 10) {
            this.discard();
        }
    }

    @Override
    public boolean skipAttackInteraction(Entity targetEntity) {
        return true;
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float damage) {
        return false;
    }

    @Override
    public void move(MoverType moverType, Vec3 movement) {
    }

    @Override
    public void push(Entity pushedEntity) {
    }

    @Override
    public void push(double x, double y, double z) {
    }

    @Override
    protected boolean repositionEntityAfterLoad() {
        return false;
    }

    @Override
    public void thunderHit(ServerLevel serverLevel, LightningBolt lightningBolt) {
    }

    @Override
    public void refreshDimensions() {
    }

    @Override
    public boolean canCollideWith(Entity entity) {
        return false;
    }
}
