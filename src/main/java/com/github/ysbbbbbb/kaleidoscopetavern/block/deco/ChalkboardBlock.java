package com.github.ysbbbbbb.kaleidoscopetavern.block.deco;

import com.github.ysbbbbbb.kaleidoscopetavern.block.properties.PositionType;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco.ChalkboardBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco.TextBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import com.github.ysbbbbbb.kaleidoscopetavern.network.NetworkHandler;
import com.github.ysbbbbbb.kaleidoscopetavern.network.message.TextOpenS2CMessage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
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
import org.apache.commons.lang3.StringUtils;
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
            BlockPos rightPos = pos.relative(facing.getCounterClockWise());
            clickedPos = half == Half.TOP ? rightPos.below() : rightPos;
        } else {
            BlockPos leftPos = pos.relative(facing.getClockWise());
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
                NetworkHandler.sendToClient(player, new TextOpenS2CMessage(chalkboard.getBlockPos()));
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
        handleRemove(level, pos, state, player);
        super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public void onBlockExploded(BlockState state, Level world, BlockPos pos, Explosion explosion) {
        handleRemove(world, pos, state, null);
        super.onBlockExploded(state, world, pos, explosion);
    }

    private static void handleRemove(Level world, BlockPos pos, BlockState state, @Nullable Player player) {
        if (world.isClientSide) {
            return;
        }

        PositionType position = state.getValue(POSITION);
        Direction facing = state.getValue(FACING);
        Half half = state.getValue(HALF);
        boolean drop = player == null || !player.isCreative();

        // 如果是单个，那么检查上下部分，一起破坏
        if (position == PositionType.SINGLE) {
            BlockPos originPos = half == Half.TOP ? pos.below() : pos;
            world.destroyBlock(originPos, drop);
            world.destroyBlock(originPos.above(), false);
            return;
        }

        // 如果是中间
        BlockPos originPos;
        if (position == PositionType.MIDDLE) {
            originPos = half == Half.TOP ? pos.below() : pos;
        } else if (position == PositionType.LEFT) {
            Direction counterClockWise = facing.getCounterClockWise();
            originPos = half == Half.TOP ? pos.below().relative(counterClockWise) : pos.relative(counterClockWise);
        } else {
            Direction clockWise = facing.getClockWise();
            originPos = half == Half.TOP ? pos.below().relative(clockWise) : pos.relative(clockWise);
        }

        // 破坏 3x2 范围
        if (facing.getAxis() == Direction.Axis.Z) {
            for (int x = -1; x < 2; x++) {
                for (int y = 0; y < 2; y++) {
                    BlockPos targetPos = originPos.offset(x, y, 0);
                    world.destroyBlock(targetPos, y != 1 && drop);
                }
            }
        } else {
            for (int z = -1; z < 2; z++) {
                for (int y = 0; y < 2; y++) {
                    BlockPos targetPos = originPos.offset(0, y, z);
                    world.destroyBlock(targetPos, y != 1 && drop);
                }
            }
        }
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack) {
        Direction direction = state.getValue(FACING);

        // 如果不是玩家放置或者玩家没有按下 Shift 键，那么就尝试和周围的黑板组合成大黑板
        if (entity == null || !entity.isShiftKeyDown()) {
            // 先检查左 1，左 2 是否都是单独的黑板且下方的黑板
            BlockPos left1 = pos.relative(direction.getClockWise());
            BlockPos left2 = left1.relative(direction.getClockWise());
            BlockState left1State = level.getBlockState(left1);
            BlockState left2State = level.getBlockState(left2);

            if (isMatchedChalkboard(level, left1, left1State, direction)
                && isMatchedChalkboard(level, left2, left2State, direction)) {
                setChalkboard(level, pos, state, PositionType.RIGHT);
                setChalkboard(level, left1, left1State, PositionType.MIDDLE);
                setChalkboard(level, left2, left2State, PositionType.LEFT);

                // 移除下侧的 BlockEntity
                level.removeBlockEntity(pos);
                level.removeBlockEntity(left2);

                // 更新中间的
                BlockEntity be = this.newBlockEntity(left1, level.getBlockState(left1));
                if (be != null) {
                    level.setBlockEntity(be);
                }

                return;
            }

            // 再检查左 1， 右 1 是否都是单独的黑板且下方的黑板
            BlockPos right1 = pos.relative(direction.getCounterClockWise());
            BlockState right1State = level.getBlockState(right1);

            if (isMatchedChalkboard(level, left1, left1State, direction)
                && isMatchedChalkboard(level, right1, right1State, direction)) {
                setChalkboard(level, pos, state, PositionType.MIDDLE);
                setChalkboard(level, left1, left1State, PositionType.LEFT);
                setChalkboard(level, right1, right1State, PositionType.RIGHT);

                // 移除下侧的 BlockEntity
                level.removeBlockEntity(left1);
                level.removeBlockEntity(right1);

                // 更新中间的
                BlockEntity be = this.newBlockEntity(pos, level.getBlockState(pos));
                if (be != null) {
                    level.setBlockEntity(be);
                }

                return;
            }

            // 最后检查右 1，右 2 是否都是单独的黑板且下方的黑板
            BlockPos right2 = right1.relative(direction.getCounterClockWise());
            BlockState right2State = level.getBlockState(right2);

            if (isMatchedChalkboard(level, right1, right1State, direction)
                && isMatchedChalkboard(level, right2, right2State, direction)) {
                setChalkboard(level, pos, state, PositionType.LEFT);
                setChalkboard(level, right1, right1State, PositionType.MIDDLE);
                setChalkboard(level, right2, right2State, PositionType.RIGHT);

                // 移除下侧的 BlockEntity
                level.removeBlockEntity(pos);
                level.removeBlockEntity(right2);

                // 更新中间的
                BlockEntity be = this.newBlockEntity(right1, level.getBlockState(right1));
                if (be != null) {
                    level.setBlockEntity(be);
                }

                return;
            }
        }

        // 都不满足，那么单独放置上方就行
        BlockState blockState = state
                .setValue(BlockStateProperties.WATERLOGGED, level.isWaterAt(pos))
                .setValue(HALF, Half.TOP);
        level.setBlockAndUpdate(pos.above(), blockState);
    }

    /**
     * 是否是同向、下部的单独黑板
     */
    private boolean isMatchedChalkboard(Level level, BlockPos pos, BlockState state, Direction direction) {
        boolean stateMatch = state.is(this)
                             && state.getValue(HALF) == Half.BOTTOM
                             && state.getValue(POSITION) == PositionType.SINGLE
                             && state.getValue(FACING) == direction;
        // 如果状态符合，检查是否有字，有字的不合并
        if (stateMatch && level.getBlockEntity(pos) instanceof ChalkboardBlockEntity chalkboard) {
            return StringUtils.isBlank(chalkboard.getText());
        }
        return stateMatch;
    }

    private void setChalkboard(Level level, BlockPos pos, BlockState state, PositionType position) {
        boolean waterAt = level.isWaterAt(pos);
        BlockState blockState = state
                .setValue(BlockStateProperties.WATERLOGGED, waterAt)
                .setValue(POSITION, position);
        level.setBlockAndUpdate(pos, blockState.setValue(HALF, Half.BOTTOM));
        level.setBlockAndUpdate(pos.above(), blockState.setValue(HALF, Half.TOP));
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
        return createTickerHelper(type, ModBlocks.CHALKBOARD_BE.get(), TextBlockEntity::tick);
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
