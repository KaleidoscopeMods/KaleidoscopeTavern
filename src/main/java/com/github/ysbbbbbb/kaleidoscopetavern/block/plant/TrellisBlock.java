package com.github.ysbbbbbb.kaleidoscopetavern.block.plant;

import com.github.ysbbbbbb.kaleidoscopetavern.block.properties.TrellisType;
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
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class TrellisBlock extends Block implements SimpleWaterloggedBlock {
    public static final EnumProperty<TrellisType> TYPE = EnumProperty.create("type", TrellisType.class);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    // 碰撞箱
    private static final VoxelShape SINGLE_SHAPE = Block.box(6, 0, 6, 10, 16, 10);
    private static final VoxelShape NORTH_SOUTH_SHAPE = Block.box(6, 6, 0, 10, 10, 16);
    private static final VoxelShape EAST_WEST_SHAPE = Block.box(0, 6, 6, 16, 10, 10);
    private static final VoxelShape CROSS_NORTH_SOUTH_SHAPE = Shapes.or(SINGLE_SHAPE, NORTH_SOUTH_SHAPE);
    private static final VoxelShape CROSS_EAST_WEST_SHAPE = Shapes.or(SINGLE_SHAPE, EAST_WEST_SHAPE);
    private static final VoxelShape CROSS_UP_DOWN_SHAPE = Shapes.or(NORTH_SOUTH_SHAPE, EAST_WEST_SHAPE);
    private static final VoxelShape SIX_DIRECTION_SHAPE = Shapes.or(SINGLE_SHAPE, NORTH_SOUTH_SHAPE, EAST_WEST_SHAPE);

    // 选择框，比碰撞箱略大，方便交互
    public static final VoxelShape SINGLE_OUTLINE = Block.box(4, 0, 4, 12, 16, 12);
    public static final VoxelShape NORTH_SOUTH_OUTLINE = Block.box(4, 4, 0, 12, 12, 16);
    public static final VoxelShape EAST_WEST_OUTLINE = Block.box(0, 4, 4, 16, 12, 12);
    public static final VoxelShape CROSS_NORTH_SOUTH_OUTLINE = Shapes.or(SINGLE_OUTLINE, NORTH_SOUTH_OUTLINE);
    public static final VoxelShape CROSS_EAST_WEST_OUTLINE = Shapes.or(SINGLE_OUTLINE, EAST_WEST_OUTLINE);
    public static final VoxelShape CROSS_UP_DOWN_OUTLINE = Shapes.or(NORTH_SOUTH_OUTLINE, EAST_WEST_OUTLINE);
    public static final VoxelShape SIX_DIRECTION_OUTLINE = Shapes.or(SINGLE_OUTLINE, NORTH_SOUTH_OUTLINE, EAST_WEST_OUTLINE);

    public TrellisBlock() {
        super(Properties.of()
                .mapColor(MapColor.WOOD)
                .instrument(NoteBlockInstrument.GUITAR)
                .strength(0.8F)
                .sound(SoundType.WOOD)
                .noOcclusion()
                .ignitedByLava());
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(TYPE, TrellisType.SINGLE)
                .setValue(WATERLOGGED, false));
    }

    /**
     * 指定轴向是否有藤架（仅满足一个即可）
     */
    public static boolean axisHasTrellis(LevelAccessor level, BlockPos pos, Direction.Axis axis) {
        Direction positive = Direction.fromAxisAndDirection(axis, Direction.AxisDirection.POSITIVE);
        BlockState positiveState = level.getBlockState(pos.relative(positive));
        if (positiveState.getBlock() instanceof TrellisBlock) {
            return true;
        }

        Direction negative = Direction.fromAxisAndDirection(axis, Direction.AxisDirection.NEGATIVE);
        BlockState negativeState = level.getBlockState(pos.relative(negative));
        return negativeState.getBlock() instanceof TrellisBlock;
    }

    /**
     * 根据相邻藤架连接情况和放置面轴向确定藤架形态（用于放置时）
     *
     * <p>两轴邻居时直接返回对应十字形态，不存在"补全为六向"的情况。
     * 单轴邻居时，邻居轴与放置面轴共同决定十字平面。
     */
    public static TrellisType getTrellisType(boolean xHas, boolean yHas, boolean zHas, Direction.Axis clickAxis) {
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
    public static TrellisType updateType(TrellisType current, boolean xHas, boolean yHas, boolean zHas) {
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

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState,
                                  LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        boolean xHas = axisHasTrellis(level, pos, Direction.Axis.X);
        boolean yHas = axisHasTrellis(level, pos, Direction.Axis.Y);
        boolean zHas = axisHasTrellis(level, pos, Direction.Axis.Z);

        return state.setValue(TYPE, updateType(state.getValue(TYPE), xHas, yHas, zHas));
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Direction.Axis axis = context.getClickedFace().getAxis();

        boolean xAxisHasTrellis = axisHasTrellis(level, pos, Direction.Axis.X);
        boolean yAxisHasTrellis = axisHasTrellis(level, pos, Direction.Axis.Y);
        boolean zAxisHasTrellis = axisHasTrellis(level, pos, Direction.Axis.Z);

        TrellisType type = getTrellisType(xAxisHasTrellis, yAxisHasTrellis, zAxisHasTrellis, axis);
        return this.defaultBlockState()
                .setValue(TYPE, type)
                .setValue(WATERLOGGED, level.isWaterAt(pos));
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(TYPE)) {
            case SINGLE -> SINGLE_SHAPE;
            case NORTH_SOUTH -> NORTH_SOUTH_SHAPE;
            case EAST_WEST -> EAST_WEST_SHAPE;
            case CROSS_NORTH_SOUTH -> CROSS_NORTH_SOUTH_SHAPE;
            case CROSS_EAST_WEST -> CROSS_EAST_WEST_SHAPE;
            case CROSS_UP_DOWN -> CROSS_UP_DOWN_SHAPE;
            case SIX_DIRECTION -> SIX_DIRECTION_SHAPE;
        };
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(TYPE)) {
            case SINGLE -> SINGLE_OUTLINE;
            case NORTH_SOUTH -> NORTH_SOUTH_OUTLINE;
            case EAST_WEST -> EAST_WEST_OUTLINE;
            case CROSS_NORTH_SOUTH -> CROSS_NORTH_SOUTH_OUTLINE;
            case CROSS_EAST_WEST -> CROSS_EAST_WEST_OUTLINE;
            case CROSS_UP_DOWN -> CROSS_UP_DOWN_OUTLINE;
            case SIX_DIRECTION -> SIX_DIRECTION_OUTLINE;
        };
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TYPE, WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }
}
