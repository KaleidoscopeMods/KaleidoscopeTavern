package com.github.ysbbbbbb.kaleidoscopetavern.block.deco;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

@SuppressWarnings("deprecation")
public class StepladderBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock {
    public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public static final VoxelShape DOWN_SOUTH = Shapes.or(
            Block.box(0, 0, 0, 16, 16, 12),
            Block.box(0, 0, 12, 16, 8, 16)
    );

    public static final VoxelShape DOWN_NORTH = Shapes.or(
            Block.box(0, 0, 4, 16, 16, 16),
            Block.box(0, 0, 0, 16, 8, 4)
    );

    public static final VoxelShape DOWN_EAST = Shapes.or(
            Block.box(0, 0, 0, 12, 16, 16),
            Block.box(12, 0, 0, 16, 8, 16)
    );

    public static final VoxelShape DOWN_WEST = Shapes.or(
            Block.box(4, 0, 0, 16, 16, 16),
            Block.box(0, 0, 0, 4, 8, 16)
    );

    public static final VoxelShape UP_SOUTH = Shapes.or(
            Block.box(0, 0, 0, 16, 16, 4),
            Block.box(0, 0, 4, 16, 8, 8)
    );

    public static final VoxelShape UP_NORTH = Shapes.or(
            Block.box(0, 0, 12, 16, 16, 16),
            Block.box(0, 0, 8, 16, 8, 12)
    );

    public static final VoxelShape UP_EAST = Shapes.or(
            Block.box(0, 0, 0, 4, 16, 16),
            Block.box(4, 0, 0, 8, 8, 16)
    );

    public static final VoxelShape UP_WEST = Shapes.or(
            Block.box(12, 0, 0, 16, 16, 16),
            Block.box(8, 0, 0, 12, 8, 16)
    );

    public StepladderBlock() {
        super(Properties.of()
                .mapColor(MapColor.WOOD)
                .instrument(NoteBlockInstrument.GUITAR)
                .strength(0.8F)
                .sound(SoundType.WOOD)
                .noOcclusion()
                .ignitedByLava());
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(HALF, Half.BOTTOM)
                .setValue(WATERLOGGED, false));
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState,
                                  LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        Half half = state.getValue(HALF);
        boolean isBottom = half == Half.BOTTOM && direction == Direction.UP;
        boolean isTop = half == Half.TOP && direction == Direction.DOWN;
        if (direction.getAxis() == Direction.Axis.Y && (isBottom || isTop)) {
            if (neighborState.is(this) && neighborState.getValue(HALF) != half) {
                return state.setValue(FACING, neighborState.getValue(FACING));
            }
            return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide && player.isCreative() && state.getValue(HALF) == Half.TOP) {
            BlockPos below = pos.below();
            BlockState belowState = level.getBlockState(below);
            if (belowState.is(state.getBlock()) && belowState.getValue(HALF) == Half.BOTTOM) {
                BlockState airBlockState = belowState.getFluidState().is(Fluids.WATER) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
                level.setBlock(below, airBlockState, Block.UPDATE_SUPPRESS_DROPS | Block.UPDATE_ALL);
                level.levelEvent(player, LevelEvent.PARTICLES_DESTROY_BLOCK, below, Block.getId(belowState));
            }
        }
        super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, HALF, WATERLOGGED);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos pos = context.getClickedPos();
        Level level = context.getLevel();
        if (pos.getY() < level.getMaxBuildHeight() - 1 && level.getBlockState(pos.above()).canBeReplaced(context)) {
            return this.defaultBlockState()
                    .setValue(FACING, context.getHorizontalDirection().getOpposite())
                    .setValue(HALF, Half.BOTTOM)
                    .setValue(WATERLOGGED, level.isWaterAt(pos));
        }
        return null;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        BlockPos above = pos.above();
        BlockState blockState = state.setValue(HALF, Half.TOP)
                .setValue(WATERLOGGED, level.isWaterAt(above));
        level.setBlockAndUpdate(above, blockState);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder lootParamsBuilder) {
        if (state.getValue(HALF) == Half.BOTTOM) {
            return super.getDrops(state, lootParamsBuilder);
        }
        return Collections.emptyList();
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction facing = state.getValue(FACING);
        Half half = state.getValue(HALF);
        return switch (half) {
            case TOP -> switch (facing) {
                case SOUTH -> UP_SOUTH;
                case WEST -> UP_WEST;
                case EAST -> UP_EAST;
                default -> UP_NORTH;
            };
            case BOTTOM -> switch (facing) {
                case SOUTH -> DOWN_SOUTH;
                case WEST -> DOWN_WEST;
                case EAST -> DOWN_EAST;
                default -> DOWN_NORTH;
            };
        };
    }
}
