package com.github.ysbbbbbb.kaleidoscopetavern.block.deco;

import com.github.ysbbbbbb.kaleidoscopetavern.block.properties.PositionType;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco.ChalkboardBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import com.github.ysbbbbbb.kaleidoscopetavern.network.NetworkHandler;
import com.github.ysbbbbbb.kaleidoscopetavern.network.message.ChalkboardOpenS2CMessage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

@SuppressWarnings("deprecation")
public class ChalkboardBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;
    public static final EnumProperty<PositionType> POSITION = EnumProperty.create("position", PositionType.class);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public static final VoxelShape UP_SOUTH_SHAPE = Block.box(0, 0, 0, 16, 14, 1);
    public static final VoxelShape DOWN_SOUTH_SHAPE = Block.box(0, 2, 0, 16, 16, 1);

    private static final VoxelShape UP_NORTH_SHAPE = Block.box(0, 0, 15, 16, 14, 16);
    private static final VoxelShape DOWN_NORTH_SHAPE = Block.box(0, 2, 15, 16, 16, 16);

    private static final VoxelShape UP_EAST_SHAPE = Block.box(0, 0, 0, 1, 14, 16);
    private static final VoxelShape DOWN_EAST_SHAPE = Block.box(0, 2, 0, 1, 16, 16);

    private static final VoxelShape UP_WEST_SHAPE = Block.box(15, 0, 0, 16, 14, 16);
    private static final VoxelShape DOWN_WEST_SHAPE = Block.box(15, 2, 0, 16, 16, 16);

    public ChalkboardBlock() {
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
                .setValue(POSITION, PositionType.SINGLE)
                .setValue(WATERLOGGED, false));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hitResult) {
        Half half = state.getValue(HALF);
        PositionType position = state.getValue(POSITION);
        Direction facing = state.getValue(FACING);
        BlockPos clickedPos;

        // 单个黑板
        if (position == PositionType.SINGLE) {
            clickedPos = half == Half.TOP ? pos.below() : pos;
        }
        // 大黑板
        else if (position == PositionType.MIDDLE) {
            clickedPos = half == Half.TOP ? pos.below() : pos;
        } else if (position == PositionType.LEFT) {
            BlockPos rightPos = pos.relative(facing.getClockWise());
            clickedPos = half == Half.TOP ? rightPos.below() : rightPos;
        } else {
            BlockPos leftPos = pos.relative(facing.getCounterClockWise());
            clickedPos = half == Half.TOP ? leftPos.below() : leftPos;
        }

        if (level.getBlockEntity(clickedPos) instanceof ChalkboardBlockEntity chalkboard) {
            if (chalkboard.isWaxed()) {
                return InteractionResult.FAIL;
            }
            if (chalkboard.playerIsTooFarAwayToEdit(player.getUUID())) {
                return InteractionResult.FAIL;
            }
            if (!level.isClientSide) {
                NetworkHandler.sendToClient(player, new ChalkboardOpenS2CMessage(chalkboard.getBlockPos()));
            }
            return InteractionResult.SUCCESS;
        }

        return super.use(state, level, pos, player, hand, hitResult);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState,
                                  LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        Half half = state.getValue(HALF);
        boolean bottom = half == Half.BOTTOM && direction == Direction.UP;
        boolean top = half == Half.TOP && direction == Direction.DOWN;
        if (direction.getAxis() == Direction.Axis.Y && (bottom || top)) {
            if (neighborState.is(this) && neighborState.getValue(HALF) != half) {
                return state.setValue(FACING, neighborState.getValue(FACING))
                        .setValue(POSITION, neighborState.getValue(POSITION));
            }
            return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos pos = context.getClickedPos();
        Level level = context.getLevel();
        FluidState fluidState = context.getLevel().getFluidState(pos);
        if (pos.getY() < level.getMaxBuildHeight() - 1 && level.getBlockState(pos.above()).canBeReplaced(context)) {
            Direction clickedFace = context.getClickedFace();
            if (clickedFace.getAxis().isVertical()) {
                clickedFace = context.getHorizontalDirection().getOpposite();
            }
            return this.defaultBlockState()
                    .setValue(FACING, clickedFace)
                    .setValue(HALF, Half.BOTTOM)
                    .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
        }
        return null;
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
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack) {
        FluidState fluidState = level.getFluidState(pos);
        BlockState blockState = state.setValue(HALF, Half.TOP)
                .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
        level.setBlockAndUpdate(pos.above(), blockState);
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        Half half = state.getValue(HALF);
        PositionType position = state.getValue(POSITION);
        // 小黑板
        if (half == Half.BOTTOM && position == PositionType.SINGLE) {
            return ChalkboardBlockEntity.small(pos, state);
        }
        // 大黑板
        if (half == Half.BOTTOM && position == PositionType.MIDDLE) {
            return ChalkboardBlockEntity.large(pos, state);
        }
        return null;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, ModBlocks.CHALKBOARD_BE.get(), ChalkboardBlockEntity::tick);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        Half half = state.getValue(HALF);
        PositionType position = state.getValue(POSITION);
        // 小黑板
        if (half == Half.BOTTOM && position == PositionType.SINGLE) {
            return RenderShape.ENTITYBLOCK_ANIMATED;
        }
        // 大黑板
        if (half == Half.BOTTOM && position == PositionType.MIDDLE) {
            return RenderShape.ENTITYBLOCK_ANIMATED;
        }
        return RenderShape.INVISIBLE;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction facing = state.getValue(FACING);
        Half half = state.getValue(HALF);
        if (facing == Direction.NORTH) {
            return half == Half.BOTTOM ? DOWN_NORTH_SHAPE : UP_NORTH_SHAPE;
        } else if (facing == Direction.SOUTH) {
            return half == Half.BOTTOM ? DOWN_SOUTH_SHAPE : UP_SOUTH_SHAPE;
        } else if (facing == Direction.WEST) {
            return half == Half.BOTTOM ? DOWN_WEST_SHAPE : UP_WEST_SHAPE;
        } else {
            return half == Half.BOTTOM ? DOWN_EAST_SHAPE : UP_EAST_SHAPE;
        }
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
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, HALF, POSITION, WATERLOGGED);
    }

    @Override
    public BlockState rotate(BlockState pState, Rotation pRot) {
        return pState.setValue(FACING, pRot.rotate(pState.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }
}
