package com.github.ysbbbbbb.kaleidoscopetavern.block.deco;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class TableBlock extends Block implements SimpleWaterloggedBlock {
    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;
    public static final IntegerProperty POSITION = IntegerProperty.create("position", 0, 3);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public static final VoxelShape SHAPE = Block.box(0, 13, 0, 16, 16, 16);

    public static final int SINGLE = 0;
    public static final int LEFT = 1;
    public static final int MIDDLE = 2;
    public static final int RIGHT = 3;

    public TableBlock() {
        super(Properties.of()
                .mapColor(MapColor.WOOD)
                .instrument(NoteBlockInstrument.BASS)
                .strength(2.0F, 3.0F)
                .sound(SoundType.WOOD)
                .noOcclusion()
                .ignitedByLava());
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(AXIS, Direction.Axis.Z)
                .setValue(POSITION, SINGLE)
                .setValue(WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AXIS, POSITION, WATERLOGGED);
    }

    private BlockState checkEastWestState(LevelAccessor levelAccessor, BlockPos pos, BlockState baseState) {
        // 如果自己本身已经成为 Z 方向的组合桌，那么不更新
        if (baseState.getValue(AXIS) == Direction.Axis.Z && baseState.getValue(POSITION) != SINGLE) {
            return baseState;
        }
        BlockState westState = levelAccessor.getBlockState(pos.west());
        BlockState eastState = levelAccessor.getBlockState(pos.east());
        if (checkIfShouldLink(eastState, Direction.Axis.Z) && checkIfShouldLink(westState, Direction.Axis.Z)) {
            return baseState.setValue(POSITION, MIDDLE).setValue(AXIS, Direction.Axis.X);
        }
        if (checkIfShouldLink(eastState, Direction.Axis.Z) && !checkIfShouldLink(westState, Direction.Axis.Z)) {
            return baseState.setValue(POSITION, LEFT).setValue(AXIS, Direction.Axis.X);
        }
        if (!checkIfShouldLink(eastState, Direction.Axis.Z) && checkIfShouldLink(westState, Direction.Axis.Z)) {
            return baseState.setValue(POSITION, RIGHT).setValue(AXIS, Direction.Axis.X);
        }
        return baseState.setValue(POSITION, SINGLE);
    }

    private BlockState checkNorthSouthState(LevelAccessor levelAccessor, BlockPos pos, BlockState baseState) {
        // 如果自己本身已经成为 X 方向的组合桌，那么不更新
        if (baseState.getValue(AXIS) == Direction.Axis.X && baseState.getValue(POSITION) != SINGLE) {
            return baseState;
        }
        BlockState northState = levelAccessor.getBlockState(pos.north());
        BlockState southState = levelAccessor.getBlockState(pos.south());
        if (checkIfShouldLink(southState, Direction.Axis.X) && checkIfShouldLink(northState, Direction.Axis.X)) {
            return baseState.setValue(POSITION, MIDDLE).setValue(AXIS, Direction.Axis.Z);
        }
        if (checkIfShouldLink(southState, Direction.Axis.X) && !checkIfShouldLink(northState, Direction.Axis.X)) {
            return baseState.setValue(POSITION, LEFT).setValue(AXIS, Direction.Axis.Z);
        }
        if (!checkIfShouldLink(southState, Direction.Axis.X) && checkIfShouldLink(northState, Direction.Axis.X)) {
            return baseState.setValue(POSITION, RIGHT).setValue(AXIS, Direction.Axis.Z);
        }
        return baseState.setValue(POSITION, SINGLE);
    }

    private boolean checkIfShouldLink(BlockState state, Direction.Axis axis) {
        if (!state.is(this)) {
            return false;
        }
        // 如果对方与修正方向不同，且对方并不是独立状态，则不可以接
        if (state.getValue(AXIS) == axis) {
            return state.getValue(POSITION) == SINGLE;
        }
        // 如果双方方向相同且毗邻，则无论如何都可以接
        return true;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor levelAccessor, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            levelAccessor.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
        }
        if (direction.getAxis() == Direction.Axis.X) {
            return checkEastWestState(levelAccessor, pos, state);
        }
        if (direction.getAxis() == Direction.Axis.Z) {
            return checkNorthSouthState(levelAccessor, pos, state);
        }
        return state;
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos clickedPos = context.getClickedPos();
        Direction direction = context.getHorizontalDirection();
        boolean hasWater = level.getFluidState(clickedPos).getType() == Fluids.WATER;
        BlockState base = this.defaultBlockState().setValue(WATERLOGGED, hasWater);

        if (direction.getAxis() == Direction.Axis.X) {
            return checkNorthSouthState(level, clickedPos, base);
        } else if (direction.getAxis() == Direction.Axis.Z) {
            return checkEastWestState(level, clickedPos, base);
        }

        return base;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type) {
        return false;
    }
}
