package com.github.ysbbbbbb.kaleidoscopetavern.block.deco;

import com.github.ysbbbbbb.kaleidoscopetavern.block.AbstractStorageBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco.CircularRackBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class CircularRackBlock extends AbstractStorageBlock {
    public static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 2, 16);

    public CircularRackBlock() {
        super();
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(POWERED, false));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hitResult) {
        return super.handleUse(state, level, pos, player, hand, hitResult, false);
    }

    @Override
    protected int getClickedSlot(Direction direction, BlockPos pos, BlockHitResult hitResult) {
        double localX = getLocalX(direction, pos, hitResult);
        double localZ = getLocalZ(direction, pos, hitResult);

        // 依据 x z 计算角度，然后决定位置
        double angle = Math.atan2(localZ - 0.5, localX - 0.5) * Mth.RAD_TO_DEG;
        angle = (angle + 360) % 360;

        if (angle > 300) {
            return 5;
        } else if (angle > 240) {
            return 0;
        } else if (angle > 180) {
            return 1;
        } else if (angle > 120) {
            return 2;
        } else if (angle > 60) {
            return 3;
        } else {
            return 4;
        }
    }

    @Override
    protected Vec3 getShootPos(Direction direction, BlockPos pos, int slot) {
        return Vec3.atCenterOf(pos);
    }

    @Override
    protected Vec3 getMovement(Direction direction, BlockPos pos, int slot) {
        double factor = Math.random() * 2 + 0.5;
        return new Vec3(0, factor, 0);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (random.nextInt(8) != 0) {
            return;
        }
        if (level.getBlockEntity(pos) instanceof CircularRackBlockEntity rack && rack.hasAnyItem()) {
            double x = pos.getX() + 0.5 + (random.nextDouble() - 0.5);
            double y = pos.getY() + 0.5;
            double z = pos.getZ() + 0.5 + (random.nextDouble() - 0.5);
            level.addParticle(ParticleTypes.END_ROD, x, y, z, 0.01, 0.01, 0.01);
        }
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CircularRackBlockEntity(pos, state);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}

