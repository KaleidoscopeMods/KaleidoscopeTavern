package com.github.ysbbbbbb.kaleidoscopetavern.block.plant;

import com.github.ysbbbbbb.kaleidoscopetavern.block.properties.TrellisType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * 藤架类方块工具接口
 */
public interface ITrellis {
    EnumProperty<TrellisType> TYPE = EnumProperty.create("type", TrellisType.class);

    // 碰撞箱，为 4x4x16 柱形状
    VoxelShape SINGLE_SHAPE = Block.box(6, 0, 6, 10, 16, 10);
    VoxelShape NORTH_SOUTH_SHAPE = Block.box(6, 6, 0, 10, 10, 16);
    VoxelShape EAST_WEST_SHAPE = Block.box(0, 6, 6, 16, 10, 10);
    VoxelShape CROSS_NORTH_SOUTH_SHAPE = Shapes.or(SINGLE_SHAPE, NORTH_SOUTH_SHAPE);
    VoxelShape CROSS_EAST_WEST_SHAPE = Shapes.or(SINGLE_SHAPE, EAST_WEST_SHAPE);
    VoxelShape CROSS_UP_DOWN_SHAPE = Shapes.or(NORTH_SOUTH_SHAPE, EAST_WEST_SHAPE);
    VoxelShape SIX_DIRECTION_SHAPE = Shapes.or(SINGLE_SHAPE, NORTH_SOUTH_SHAPE, EAST_WEST_SHAPE);

    // 选择框，比碰撞箱略大，方便交互
    VoxelShape SINGLE_OUTLINE = Block.box(4, 0, 4, 12, 16, 12);
    VoxelShape NORTH_SOUTH_OUTLINE = Block.box(4, 4, 0, 12, 12, 16);
    VoxelShape EAST_WEST_OUTLINE = Block.box(0, 4, 4, 16, 12, 12);
    VoxelShape CROSS_NORTH_SOUTH_OUTLINE = Shapes.or(SINGLE_OUTLINE, NORTH_SOUTH_OUTLINE);
    VoxelShape CROSS_EAST_WEST_OUTLINE = Shapes.or(SINGLE_OUTLINE, EAST_WEST_OUTLINE);
    VoxelShape CROSS_UP_DOWN_OUTLINE = Shapes.or(NORTH_SOUTH_OUTLINE, EAST_WEST_OUTLINE);
    VoxelShape SIX_DIRECTION_OUTLINE = Shapes.or(SINGLE_OUTLINE, NORTH_SOUTH_OUTLINE, EAST_WEST_OUTLINE);

    /**
     * 指定轴向是否有藤架（仅满足一个即可）
     */
    static boolean axisHasTrellis(LevelAccessor level, BlockPos pos, Direction.Axis axis) {
        Direction positive = Direction.fromAxisAndDirection(axis, Direction.AxisDirection.POSITIVE);
        BlockState positiveState = level.getBlockState(pos.relative(positive));
        if (positiveState.getBlock() instanceof ITrellis trellis) {
            return trellis.sameType(positiveState);
        }

        Direction negative = Direction.fromAxisAndDirection(axis, Direction.AxisDirection.NEGATIVE);
        BlockState negativeState = level.getBlockState(pos.relative(negative));
        if (negativeState.getBlock() instanceof ITrellis trellis) {
            return trellis.sameType(negativeState);
        }
        return false;
    }

    /**
     * 根据相邻藤架连接情况和放置面轴向确定藤架形态（用于放置时）
     *
     * <p>两轴邻居时直接返回对应十字形态，不存在"补全为六向"的情况。
     * 单轴邻居时，邻居轴与放置面轴共同决定十字平面。
     */
    static TrellisType getTypeForPlacement(boolean xHas, boolean yHas, boolean zHas, Direction.Axis clickAxis) {
        // 三轴均有邻居 → 六向
        if (xHas && yHas && zHas) {
            return TrellisType.SIX_DIRECTION;
        }

        // 两轴有邻居 → 对应十字形态（与放置面无关）
        if (xHas && yHas) {
            return TrellisType.CROSS_EAST_WEST;
        }
        if (yHas && zHas) {
            return TrellisType.CROSS_NORTH_SOUTH;
        }
        if (xHas && zHas) {
            return TrellisType.CROSS_UP_DOWN;
        }

        // 单轴有邻居：邻居轴 × 放置面轴 决定十字平面
        if (xHas) {
            return switch (clickAxis) {
                case X -> TrellisType.EAST_WEST;
                case Y -> TrellisType.CROSS_EAST_WEST;
                case Z -> TrellisType.CROSS_UP_DOWN;
            };
        }
        if (yHas) {
            return switch (clickAxis) {
                case X -> TrellisType.CROSS_EAST_WEST;
                case Y -> TrellisType.SINGLE;
                case Z -> TrellisType.CROSS_NORTH_SOUTH;
            };
        }
        if (zHas) {
            return switch (clickAxis) {
                case X -> TrellisType.CROSS_UP_DOWN;
                case Y -> TrellisType.CROSS_NORTH_SOUTH;
                case Z -> TrellisType.NORTH_SOUTH;
            };
        }

        // 无相邻藤架 → 放置面决定基础形态
        return switch (clickAxis) {
            case X -> TrellisType.EAST_WEST;
            case Y -> TrellisType.SINGLE;
            case Z -> TrellisType.NORTH_SOUTH;
        };
    }

    /**
     * 根据当前形态和实时邻居连接情况推算新形态（用于邻居更新时）
     *
     * <p>基础形态（SINGLE/NORTH_SOUTH/EAST_WEST）携带自身轴信息，
     * 邻居消失时不改变自身轴，仅移除额外连接；十字形态按剩余连接降级。
     * <ul>
     *   <li>SINGLE：自身轴 Y，响应 X/Z 邻居</li>
     *   <li>NORTH_SOUTH：自身轴 Z，响应 X/Y 邻居</li>
     *   <li>EAST_WEST：自身轴 X，响应 Y/Z 邻居</li>
     *   <li>CROSS_NORTH_SOUTH：Z+Y 连接，加 X 升级为六向</li>
     *   <li>CROSS_EAST_WEST：X+Y 连接，加 Z 升级为六向</li>
     *   <li>CROSS_UP_DOWN：X+Z 连接，加 Y 升级为六向</li>
     * </ul>
     */
    static TrellisType updateType(TrellisType current, boolean xHas, boolean yHas, boolean zHas) {
        return switch (current) {
            case SINGLE -> {
                // 自身轴 Y：X+Z 同时存在则三轴齐全 → 六向
                if (xHas && zHas) {
                    yield TrellisType.SIX_DIRECTION;
                }
                if (xHas) {
                    yield TrellisType.CROSS_EAST_WEST;
                }
                if (zHas) {
                    yield TrellisType.CROSS_NORTH_SOUTH;
                }
                yield TrellisType.SINGLE;
            }
            case NORTH_SOUTH -> {
                // 自身轴 Z：X+Y 同时存在则三轴齐全 → 六向
                if (xHas && yHas) {
                    yield TrellisType.SIX_DIRECTION;
                }
                if (xHas) {
                    yield TrellisType.CROSS_UP_DOWN;
                }
                if (yHas) {
                    yield TrellisType.CROSS_NORTH_SOUTH;
                }
                yield TrellisType.NORTH_SOUTH;
            }
            case EAST_WEST -> {
                // 自身轴 X：Y+Z 同时存在则三轴齐全 → 六向
                if (yHas && zHas) {
                    yield TrellisType.SIX_DIRECTION;
                }
                if (yHas) {
                    yield TrellisType.CROSS_EAST_WEST;
                }
                if (zHas) {
                    yield TrellisType.CROSS_UP_DOWN;
                }
                yield TrellisType.EAST_WEST;
            }
            case CROSS_NORTH_SOUTH -> {
                // Z+Y 连接；加 X → 六向；失去其中一轴则降为对应基础形态
                if (xHas) {
                    yield TrellisType.SIX_DIRECTION;
                }
                if (zHas && yHas) {
                    yield TrellisType.CROSS_NORTH_SOUTH;
                }
                if (zHas) {
                    yield TrellisType.NORTH_SOUTH;
                }
                yield TrellisType.SINGLE;
            }
            case CROSS_EAST_WEST -> {
                // X+Y 连接；加 Z → 六向；失去其中一轴则降为对应基础形态
                if (zHas) {
                    yield TrellisType.SIX_DIRECTION;
                }
                if (xHas && yHas) {
                    yield TrellisType.CROSS_EAST_WEST;
                }
                if (xHas) {
                    yield TrellisType.EAST_WEST;
                }
                yield TrellisType.SINGLE;
            }
            case CROSS_UP_DOWN -> {
                // X+Z 连接；加 Y → 六向；失去其中一轴则降为对应基础形态
                if (yHas) {
                    yield TrellisType.SIX_DIRECTION;
                }
                if (xHas && zHas) {
                    yield TrellisType.CROSS_UP_DOWN;
                }
                if (xHas) {
                    yield TrellisType.EAST_WEST;
                }
                yield TrellisType.NORTH_SOUTH;
            }
            case SIX_DIRECTION -> {
                // 失去某轴连接时降为对应十字形态
                if (xHas && yHas && zHas) {
                    yield TrellisType.SIX_DIRECTION;
                }
                if (xHas && yHas) {
                    yield TrellisType.CROSS_EAST_WEST;
                }
                if (yHas && zHas) {
                    yield TrellisType.CROSS_NORTH_SOUTH;
                }
                if (xHas && zHas) {
                    yield TrellisType.CROSS_UP_DOWN;
                }
                if (xHas) {
                    yield TrellisType.EAST_WEST;
                }
                if (zHas) {
                    yield TrellisType.NORTH_SOUTH;
                }
                yield TrellisType.SINGLE;
            }
        };
    }

    /**
     * 判断方块状态是否属于同一类型的可连接方块
     */
    boolean sameType(BlockState state);

    /**
     * 根据形态返回碰撞箱
     */
    default VoxelShape collisionShape(TrellisType type) {
        return switch (type) {
            case SINGLE -> SINGLE_SHAPE;
            case NORTH_SOUTH -> NORTH_SOUTH_SHAPE;
            case EAST_WEST -> EAST_WEST_SHAPE;
            case CROSS_NORTH_SOUTH -> CROSS_NORTH_SOUTH_SHAPE;
            case CROSS_EAST_WEST -> CROSS_EAST_WEST_SHAPE;
            case CROSS_UP_DOWN -> CROSS_UP_DOWN_SHAPE;
            case SIX_DIRECTION -> SIX_DIRECTION_SHAPE;
        };
    }

    default VoxelShape selectShape(TrellisType type) {
        return switch (type) {
            case SINGLE -> SINGLE_OUTLINE;
            case NORTH_SOUTH -> NORTH_SOUTH_OUTLINE;
            case EAST_WEST -> EAST_WEST_OUTLINE;
            case CROSS_NORTH_SOUTH -> CROSS_NORTH_SOUTH_OUTLINE;
            case CROSS_EAST_WEST -> CROSS_EAST_WEST_OUTLINE;
            case CROSS_UP_DOWN -> CROSS_UP_DOWN_OUTLINE;
            case SIX_DIRECTION -> SIX_DIRECTION_OUTLINE;
        };
    }
}