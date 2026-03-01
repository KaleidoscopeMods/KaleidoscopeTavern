package com.github.ysbbbbbb.kaleidoscopetavern.block.plant;

import com.github.ysbbbbbb.kaleidoscopetavern.block.properties.TrellisType;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.ToolActions;

import static com.github.ysbbbbbb.kaleidoscopetavern.block.plant.ITrellis.axisHasTrellis;
import static com.github.ysbbbbbb.kaleidoscopetavern.block.plant.ITrellis.updateType;

@SuppressWarnings("deprecation")
public class GrapevineTrellisBlock extends Block implements SimpleWaterloggedBlock, ITrellis, BonemealableBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
    public static final int MAX_AGE = BlockStateProperties.MAX_AGE_3;
    /**
     * 生长时优先检查的方向，先向上生长，如果向上生长失败，再依次检查东、西、南、北四个方向
     */
    public static final Direction[] CHECK_DIRECTION = new Direction[]{Direction.UP, Direction.EAST, Direction.WEST, Direction.SOUTH, Direction.NORTH};

    private final float growPerTickProbability;

    public GrapevineTrellisBlock() {
        super(Properties.of()
                .mapColor(MapColor.WOOD)
                .instrument(NoteBlockInstrument.GUITAR)
                .strength(0.8F)
                .sound(SoundType.WOOD)
                .randomTicks()
                .noOcclusion()
                .pushReaction(PushReaction.DESTROY)
                .ignitedByLava());
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(TYPE, TrellisType.SINGLE)
                .setValue(AGE, 0)
                .setValue(WATERLOGGED, false));
        this.growPerTickProbability = 0.25F;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hitResult) {
        // 如果玩家拿的是剪刀，可以剪下葡萄藤
        ItemStack itemInHand = player.getItemInHand(hand);
        if (!itemInHand.canPerformAction(ToolActions.SHEARS_HARVEST)) {
            return super.use(state, level, pos, player, hand, hitResult);
        }
        BlockState newState = ModBlocks.TRELLIS.get()
                .defaultBlockState()
                .setValue(TYPE, state.getValue(TYPE))
                .setValue(WATERLOGGED, state.getValue(WATERLOGGED));
        level.setBlockAndUpdate(pos, newState);
        // 掉落一个葡萄藤物品
        Block.popResource(level, pos, ModItems.GRAPEVINE.get().getDefaultInstance());
        itemInHand.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
        player.playSound(SoundEvents.BEEHIVE_SHEAR);
        return InteractionResult.SUCCESS;
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
        var trellisType = updateType(state.getValue(TYPE), xHas, yHas, zHas);

        state = state.setValue(TYPE, trellisType);
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    public boolean sameType(BlockState state) {
        return state.is(ModBlocks.TRELLIS.get()) || state.is(ModBlocks.GRAPEVINE_TRELLIS.get());
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (ForgeHooks.onCropsGrowPre(level, pos, state, random.nextDouble() < this.growPerTickProbability)) {
            this.doGrow(level, pos, state);
        }
    }

    /**
     * 判断是否达到最大生长阶段
     */
    public boolean isMaxAge(BlockState state) {
        return state.getValue(AGE) >= MAX_AGE;
    }

    /**
     * 葡萄藤下方是否符合生长条件
     * <p>
     * 下方要么为满 age 的葡萄藤，要么为泥土类方块
     */
    public boolean belowSupportGrow(BlockState belowState) {
        if (belowState.is(this)) {
            return isMaxAge(belowState);
        } else {
            return belowState.is(BlockTags.DIRT);
        }
    }

    /**
     * 指定位置是否能够生长葡萄藤
     */
    public boolean canGrowInto(BlockState checkState) {
        return checkState.is(ModBlocks.TRELLIS.get());
    }

    /**
     * 向周围生长时，葡萄藤的状态应该是什么样子的
     */
    public BlockState getGrowIntoState(Direction direction, BlockState checkState) {
        var type = checkState.getOptionalValue(TYPE).orElse(TrellisType.SINGLE);
        boolean waterlogged = checkState.getOptionalValue(WATERLOGGED).orElse(false);
        // 向上生长时，年龄为 0；向其他方向生长时，年龄为 MAX_AGE
        int age = direction == Direction.UP ? 0 : MAX_AGE;
        return this.defaultBlockState()
                .setValue(TYPE, type)
                .setValue(AGE, age)
                .setValue(WATERLOGGED, waterlogged);
    }

    /**
     * 下方能否生长葡萄方块
     * <p>
     * 必须要求下方有一格的空气方块
     */
    public boolean canGrowGrape(LevelReader level, BlockPos pos) {
        if (pos.getY() < level.getMinBuildHeight() + 1) {
            return false;
        }
        return level.getBlockState(pos.below()).isAir();
    }

    public boolean canGrow(LevelReader level, BlockPos pos, BlockState state) {
        // 如果是 single 状态
        if (state.getValue(TYPE) == TrellisType.SINGLE) {
            // 先检查下方是否满足生长条件
            BlockState belowState = level.getBlockState(pos.below());
            if (!belowSupportGrow(belowState)) {
                return false;
            }
            // 如果没有达到最大年龄，直接增加年龄
            if (!isMaxAge(state)) {
                return true;
            }
        }

        // 如果已经达到最大年龄，此时尝试往各个方向检查
        if (isMaxAge(state)) {
            for (Direction direction : CHECK_DIRECTION) {
                BlockPos checkPos = pos.relative(direction);
                BlockState checkState = level.getBlockState(checkPos);
                if (this.canGrowInto(checkState)) {
                    return true;
                }
            }

            // 如果所有方向都检查完了都不能生长，那么检查下方是否有两格空位，生长葡萄
            return canGrowGrape(level, pos);
        } else {
            // 其他朝向的，只要没有达到最大年龄，就可以继续生长
            return true;
        }
    }

    public void doGrow(Level level, BlockPos pos, BlockState state) {
        // 如果是 single 状态
        if (state.getValue(TYPE) == TrellisType.SINGLE) {
            // 先检查下方是否满足生长条件
            BlockState belowState = level.getBlockState(pos.below());
            if (!belowSupportGrow(belowState)) {
                return;
            }
            // 如果没有达到最大年龄，直接增加年龄
            if (!isMaxAge(state)) {
                level.setBlockAndUpdate(pos, state.cycle(AGE));
                ForgeHooks.onCropsGrowPost(level, pos, state);
                return;
            }
        }

        // 如果已经达到最大年龄，此时尝试往各个方向检查
        if (isMaxAge(state)) {
            for (Direction direction : CHECK_DIRECTION) {
                BlockPos checkPos = pos.relative(direction);
                BlockState checkState = level.getBlockState(checkPos);
                if (this.canGrowInto(checkState)) {
                    BlockState growIntoState = this.getGrowIntoState(direction, checkState);
                    level.setBlockAndUpdate(checkPos, growIntoState);
                    ForgeHooks.onCropsGrowPost(level, checkPos, checkState);
                    return;
                }
            }

            // 如果所有方向都检查完了都不能生长，那么检查下方是否有两格空位，生长葡萄
            if (canGrowGrape(level, pos)) {
                level.setBlockAndUpdate(pos.below(), ModBlocks.GRAPE_CROP.get().defaultBlockState());
                ForgeHooks.onCropsGrowPost(level, pos.below(), state);
            }
        } else {
            // 其他朝向的，直接加满
            level.setBlockAndUpdate(pos, state.setValue(AGE, MAX_AGE));
            ForgeHooks.onCropsGrowPost(level, pos, state);
        }
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient) {
        return this.canGrow(level, pos, state);
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        this.doGrow(level, pos, state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TYPE, AGE, WATERLOGGED);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return collisionShape(state.getValue(TYPE));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return selectShape(state.getValue(TYPE));
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        return ModItems.GRAPEVINE.get().getDefaultInstance();
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }
}
