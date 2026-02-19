package com.github.ysbbbbbb.kaleidoscopetavern.block.deco;

import com.github.ysbbbbbb.kaleidoscopetavern.block.properties.ConnectionType;
import com.github.ysbbbbbb.kaleidoscopetavern.entity.SitEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@SuppressWarnings("deprecation")
public class SofaBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock, IConnectionBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    // 普通情况
    public static final VoxelShape NORTH_SHAPE = Shapes.or(
            Block.box(0, 0, 0, 16, 8, 16),
            Block.box(0, 8, 11, 16, 18, 16)
    );
    public static final VoxelShape SOUTH_SHAPE = Shapes.or(
            Block.box(0, 0, 0, 16, 8, 16),
            Block.box(0, 8, 0, 16, 18, 5)
    );
    public static final VoxelShape WEST_SHAPE = Shapes.or(
            Block.box(0, 0, 0, 16, 8, 16),
            Block.box(11, 8, 0, 16, 18, 16)
    );
    public static final VoxelShape EAST_SHAPE = Shapes.or(
            Block.box(0, 0, 0, 16, 8, 16),
            Block.box(0, 8, 0, 5, 18, 16)
    );

    // 转角
    public static final VoxelShape NORTH_LEFT_CORNER_SHAPE = Shapes.or(NORTH_SHAPE, Block.box(11, 8, 0, 16, 18, 16));
    public static final VoxelShape NORTH_RIGHT_CORNER_SHAPE = Shapes.or(NORTH_SHAPE, Block.box(0, 8, 0, 5, 18, 16));

    public static final VoxelShape SOUTH_LEFT_CORNER_SHAPE = Shapes.or(SOUTH_SHAPE, Block.box(0, 8, 0, 5, 18, 16));
    public static final VoxelShape SOUTH_RIGHT_CORNER_SHAPE = Shapes.or(SOUTH_SHAPE, Block.box(11, 8, 0, 16, 18, 16));

    public static final VoxelShape WEST_LEFT_CORNER_SHAPE = Shapes.or(WEST_SHAPE, Block.box(0, 8, 0, 16, 18, 5));
    public static final VoxelShape WEST_RIGHT_CORNER_SHAPE = Shapes.or(WEST_SHAPE, Block.box(0, 8, 11, 16, 18, 16));

    public static final VoxelShape EAST_LEFT_CORNER_SHAPE = Shapes.or(EAST_SHAPE, Block.box(0, 8, 11, 16, 18, 16));
    public static final VoxelShape EAST_RIGHT_CORNER_SHAPE = Shapes.or(EAST_SHAPE, Block.box(0, 8, 0, 16, 18, 5));

    public SofaBlock() {
        super(Properties.of()
                .mapColor(MapColor.WOOL)
                .instrument(NoteBlockInstrument.GUITAR)
                .strength(0.8F)
                .sound(SoundType.WOOL)
                .noOcclusion()
                .ignitedByLava());
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(CONNECTION, ConnectionType.SINGLE)
                .setValue(WATERLOGGED, false));
    }

    @Override
    public boolean sameType(BlockState state) {
        return state.getBlock() instanceof SofaBlock;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState,
                                  LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        state = this.updateShape(level, pos, state, direction);
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, CONNECTION, WATERLOGGED);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Direction direction = context.getHorizontalDirection().getOpposite();
        ConnectionType connectionType = this.getConnectionForPlacement(context);

        FluidState fluidState = level.getFluidState(pos);
        return this.defaultBlockState()
                .setValue(FACING, direction)
                .setValue(CONNECTION, connectionType)
                .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        List<SitEntity> entities = level.getEntitiesOfClass(SitEntity.class, new AABB(pos));
        if (entities.isEmpty()) {
            SitEntity entitySit = new SitEntity(level, pos, 0.5125);
            entitySit.setYRot(state.getValue(FACING).toYRot());
            level.addFreshEntity(entitySit);
            player.startRiding(entitySit, true);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public void destroy(LevelAccessor levelAccessor, BlockPos pos, BlockState state) {
        levelAccessor.getEntitiesOfClass(SitEntity.class, new AABB(pos)).forEach(Entity::discard);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        ConnectionType type = pState.getValue(CONNECTION);
        Direction direction = pState.getValue(FACING);

        if (direction == Direction.NORTH) {
            return switch (type) {
                case LEFT_CORNER -> NORTH_LEFT_CORNER_SHAPE;
                case RIGHT_CORNER -> NORTH_RIGHT_CORNER_SHAPE;
                default -> NORTH_SHAPE;
            };
        } else if (direction == Direction.SOUTH) {
            return switch (type) {
                case LEFT_CORNER -> SOUTH_LEFT_CORNER_SHAPE;
                case RIGHT_CORNER -> SOUTH_RIGHT_CORNER_SHAPE;
                default -> SOUTH_SHAPE;
            };
        } else if (direction == Direction.WEST) {
            return switch (type) {
                case LEFT_CORNER -> WEST_LEFT_CORNER_SHAPE;
                case RIGHT_CORNER -> WEST_RIGHT_CORNER_SHAPE;
                default -> WEST_SHAPE;
            };
        } else {
            return switch (type) {
                case LEFT_CORNER -> EAST_LEFT_CORNER_SHAPE;
                case RIGHT_CORNER -> EAST_RIGHT_CORNER_SHAPE;
                default -> EAST_SHAPE;
            };
        }
    }
}
