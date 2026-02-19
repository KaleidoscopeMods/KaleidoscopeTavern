package com.github.ysbbbbbb.kaleidoscopetavern.block.deco;

import com.github.ysbbbbbb.kaleidoscopetavern.block.properties.ConnectionType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;

import java.util.function.Function;

import static net.minecraft.world.level.block.HorizontalDirectionalBlock.FACING;

/**
 * 沙发，吧台这种复杂链接状态的方块的工具接口
 */
public interface IConnectionBlock {
    EnumProperty<ConnectionType> CONNECTION = EnumProperty.create("connection", ConnectionType.class);

    /**
     * 判断方块状态是否属于同一类型的可连接方块
     */
    boolean sameType(BlockState state);

    /**
     * 方块变更时，更新连接状态
     */
    default BlockState updateShape(LevelAccessor level, BlockPos pos, BlockState state, Direction update) {
        Direction facing = state.getValue(FACING);
        boolean horizontalChange = update.getAxis() == facing.getClockWise().getAxis();
        boolean frontChange = update == facing;

        if (horizontalChange || frontChange) {
            Direction left = facing.getClockWise();
            Direction right = facing.getCounterClockWise();
            ConnectionType type = state.getValue(CONNECTION);

            // 如果自己是左拐角，更新来自左侧，不需要刷新状态
            if (type == ConnectionType.LEFT_CORNER && update == left) {
                return state;
            }

            // 如果自己是右拐角，更新来自右侧，不需要刷新状态
            if (type == ConnectionType.RIGHT_CORNER && update == right) {
                return state;
            }

            BlockState leftState = level.getBlockState(pos.relative(left));
            BlockState rightState = level.getBlockState(pos.relative(right));
            BlockState frontState = level.getBlockState(pos.relative(facing));

            boolean leftConnected = leftConnected(leftState, facing, this::sameType);
            boolean rightConnected = rightConnected(rightState, facing, this::sameType);
            boolean frontLeftConnected = frontLeftConnected(frontState, facing, this::sameType);
            boolean frontRightConnected = frontRightConnected(frontState, facing, this::sameType);

            ConnectionType changedType = getConnectionType(leftConnected, rightConnected, frontLeftConnected, frontRightConnected);
            return state.setValue(CONNECTION, changedType);
        }

        return state;
    }

    /**
     * 获取放置时的连接状态
     */
    default ConnectionType getConnectionForPlacement(BlockPlaceContext context) {
        // 先检查左右两侧是否有沙发
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Direction direction = context.getHorizontalDirection().getOpposite();

        Direction left = direction.getClockWise();
        Direction right = direction.getCounterClockWise();

        BlockState leftState = level.getBlockState(pos.relative(left));
        BlockState rightState = level.getBlockState(pos.relative(right));
        BlockState frontState = level.getBlockState(pos.relative(direction));

        boolean leftConnected = leftConnected(leftState, direction, this::sameType);
        boolean rightConnected = rightConnected(rightState, direction, this::sameType);
        boolean frontLeftConnected = frontLeftConnected(frontState, direction, this::sameType);
        boolean frontRightConnected = frontRightConnected(frontState, direction, this::sameType);

        return getConnectionType(leftConnected, rightConnected, frontLeftConnected, frontRightConnected);
    }

    /**
     * 判断左侧是否连接
     * <p>
     * 连接条件：
     * 1. 同朝向
     * 2. 朝右，但是 type 是单个、右侧、右拐角
     */
    static boolean leftConnected(BlockState leftState, Direction self, Function<BlockState, Boolean> typeCheck) {
        if (typeCheck.apply(leftState)) {
            Direction check = leftState.getValue(FACING);
            Direction right = self.getCounterClockWise();
            if (check == right) {
                ConnectionType connection = leftState.getValue(CONNECTION);
                return connection == ConnectionType.SINGLE || connection == ConnectionType.RIGHT || connection == ConnectionType.RIGHT_CORNER;
            }
            return check == self;
        }
        return false;
    }

    /**
     * 判断右侧是否连接
     * <p>
     * 连接条件：
     * 1. 同朝向
     * 2. 朝左，但是 type 是单个、左侧、左拐角
     */
    static boolean rightConnected(BlockState rightState, Direction self, Function<BlockState, Boolean> typeCheck) {
        if (typeCheck.apply(rightState)) {
            Direction check = rightState.getValue(FACING);
            Direction left = self.getClockWise();
            if (check == left) {
                ConnectionType connection = rightState.getValue(CONNECTION);
                return connection == ConnectionType.SINGLE || connection == ConnectionType.LEFT || connection == ConnectionType.LEFT_CORNER;
            }
            return check == self;
        }
        return false;
    }

    /**
     * 判断前侧是否左拐角连接
     * <p>
     * 连接条件：
     * 朝左，type 为单个、右拐角、左侧
     */
    static boolean frontLeftConnected(BlockState frontState, Direction direction, Function<BlockState, Boolean> typeCheck) {
        if (typeCheck.apply(frontState)) {
            Direction check = frontState.getValue(FACING);
            Direction left = direction.getClockWise();
            ConnectionType connection = frontState.getValue(CONNECTION);
            if (check == left) {
                return connection != ConnectionType.LEFT_CORNER;
            }
        }
        return false;
    }

    /**
     * 判断前侧是否右拐角连接
     * <p>
     * 连接条件：
     * 朝右，type 为单个、右拐角、左侧
     */
    static boolean frontRightConnected(BlockState frontState, Direction direction, Function<BlockState, Boolean> typeCheck) {
        if (typeCheck.apply(frontState)) {
            Direction check = frontState.getValue(FACING);
            Direction right = direction.getCounterClockWise();
            ConnectionType connection = frontState.getValue(CONNECTION);
            if (check == right) {
                return connection != ConnectionType.RIGHT_CORNER;
            }
        }
        return false;
    }

    /**
     * 根据连接状态获取连接类型
     */
    static ConnectionType getConnectionType(boolean leftConnected, boolean rightConnected,
                                            boolean frontLeftConnected, boolean frontRightConnected) {
        if (leftConnected && rightConnected) {
            // 优先横向连接
            return ConnectionType.MIDDLE;
        } else if (frontLeftConnected) {
            // 其次是左拐角连接
            if (rightConnected) {
                return ConnectionType.LEFT;
            }
            return ConnectionType.RIGHT_CORNER;
        } else if (frontRightConnected) {
            // 其次是右拐角连接
            if (leftConnected) {
                return ConnectionType.RIGHT;
            }
            return ConnectionType.LEFT_CORNER;
        } else if (leftConnected) {
            return ConnectionType.RIGHT;
        } else if (rightConnected) {
            return ConnectionType.LEFT;
        } else {
            return ConnectionType.SINGLE;
        }
    }
}
