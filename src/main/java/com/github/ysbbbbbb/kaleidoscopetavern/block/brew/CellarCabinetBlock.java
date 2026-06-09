package com.github.ysbbbbbb.kaleidoscopetavern.block.brew;

import com.github.ysbbbbbb.kaleidoscopetavern.block.AbstractStorageBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.block.properties.PositionType;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.brew.CellarCabinetBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.init.tag.TagMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class CellarCabinetBlock extends AbstractStorageBlock {
    public CellarCabinetBlock() {
        super();
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(POWERED, false)
                .setValue(BarCabinetBlock.POSITION, PositionType.SINGLE));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hitResult) {
        return super.handleUse(state, level, pos, player, hand, hitResult);
    }

    @Override
    protected boolean blockListCheck(ItemStack stack) {
        return stack.is(TagMod.CELLAR_CABINET_BLOCKLIST);
    }

    @Override
    protected int getClickedSlot(Direction direction, BlockPos pos, BlockHitResult hitResult) {
        // 只能点击正面
        if (hitResult.getDirection() != direction) {
            return -1;
        }

        double localX = this.getLocalX(direction, pos, hitResult);
        double relativeY = hitResult.getLocation().y - pos.getY();

        // 九宫格，点那个选择哪个
        int column = (int) (localX * 3) % 3;
        int row = 2 - (int) (relativeY * 3) % 3;

        return column + row * 3;
    }

    @Override
    protected Vec3 getShootPos(Direction direction, BlockPos pos, int slot) {
        Vec3 center = Vec3.atLowerCornerOf(pos).add(0.5, 0.5, 0.5);
        Vec3 scale = Vec3.atLowerCornerOf(direction.getNormal()).scale(0.5);
        return center.add(scale);
    }

    @Override
    protected Vec3 getMovement(Direction direction, BlockPos pos, int slot) {
        double factor = Math.random() * 2 + 0.5;
        Vec3 normal = Vec3.atLowerCornerWithOffset(direction.getNormal(), 0, 0.1, 0);
        return normal.scale(factor);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState,
                                  LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        Direction self = state.getValue(FACING);
        Direction left = self.getClockWise();
        Direction right = self.getCounterClockWise();

        if (direction == left) {
            boolean leftIsCabinet = neighborState.is(this) && neighborState.getValue(FACING) == self;
            PositionType position = state.getValue(BarCabinetBlock.POSITION);
            if (leftIsCabinet) {
                if (position == PositionType.SINGLE) {
                    return state.setValue(BarCabinetBlock.POSITION, PositionType.RIGHT);
                } else if (position == PositionType.LEFT) {
                    return state.setValue(BarCabinetBlock.POSITION, PositionType.MIDDLE);
                }
            } else {
                if (position == PositionType.RIGHT) {
                    return state.setValue(BarCabinetBlock.POSITION, PositionType.SINGLE);
                } else if (position == PositionType.MIDDLE) {
                    return state.setValue(BarCabinetBlock.POSITION, PositionType.LEFT);
                }
            }
        } else if (direction == right) {
            boolean rightIsCabinet = neighborState.is(this) && neighborState.getValue(FACING) == self;
            PositionType position = state.getValue(BarCabinetBlock.POSITION);
            if (rightIsCabinet) {
                if (position == PositionType.SINGLE) {
                    return state.setValue(BarCabinetBlock.POSITION, PositionType.LEFT);
                } else if (position == PositionType.RIGHT) {
                    return state.setValue(BarCabinetBlock.POSITION, PositionType.MIDDLE);
                }
            } else {
                if (position == PositionType.LEFT) {
                    return state.setValue(BarCabinetBlock.POSITION, PositionType.SINGLE);
                } else if (position == PositionType.MIDDLE) {
                    return state.setValue(BarCabinetBlock.POSITION, PositionType.RIGHT);
                }
            }
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Direction opposite = context.getHorizontalDirection().getOpposite();

        BlockState left = level.getBlockState(pos.relative(opposite.getClockWise()));
        BlockState right = level.getBlockState(pos.relative(opposite.getCounterClockWise()));

        PositionType position = PositionType.SINGLE;
        boolean leftIsCabinet = left.is(this) && left.getValue(FACING) == opposite;
        boolean rightIsCabinet = right.is(this) && right.getValue(FACING) == opposite;

        if (leftIsCabinet && rightIsCabinet) {
            position = PositionType.MIDDLE;
        } else if (leftIsCabinet) {
            position = PositionType.RIGHT;
        } else if (rightIsCabinet) {
            position = PositionType.LEFT;
        }

        boolean signal = level.hasNeighborSignal(pos);

        return this.defaultBlockState()
                .setValue(FACING, opposite)
                .setValue(POWERED, signal)
                .setValue(BarCabinetBlock.POSITION, position);
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CellarCabinetBlockEntity(pos, state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED, BarCabinetBlock.POSITION);
    }
}
