package com.github.ysbbbbbb.kaleidoscopetavern.block.brew;

import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.brew.BarrelBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import com.github.ysbbbbbb.kaleidoscopetavern.util.FluidUtils;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("deprecation")
public class BarrelBlock extends BaseEntityBlock {
    public static final MapCodec<BarrelBlock> CODEC = simpleCodec(p -> new BarrelBlock());
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    /**
     * 对应酒桶的上中下三层
     */
    public static final EnumProperty<AttachFace> LAYER = EnumProperty.create("layer", AttachFace.class);
    /**
     * 对应每层的序号，也就是从左往右，从上往下，0 - 8
     */
    public static final IntegerProperty INDEX = IntegerProperty.create("index", 0, 8);

    // 中间位置
    private static final VoxelShape SHAPE_FULL = Block.box(0, 0, 0, 16, 16, 16);
    // 南北朝向桶身左列
    private static final VoxelShape SHAPE_CUT_LEFT = Block.box(4, 0, 0, 16, 16, 16);
    // 南北朝向桶身右列
    private static final VoxelShape SHAPE_CUT_RIGHT = Block.box(0, 0, 0, 12, 16, 16);
    // 东西朝向桶身近行
    private static final VoxelShape SHAPE_CUT_NEAR = Block.box(0, 0, 4, 16, 16, 16);
    // 东西朝向桶身远行
    private static final VoxelShape SHAPE_CUT_FAR = Block.box(0, 0, 0, 16, 16, 12);

    private static final AttachFace[] LAYERS = {AttachFace.FLOOR, AttachFace.WALL, AttachFace.CEILING};

    public BarrelBlock() {
        super(Properties.of()
                .mapColor(MapColor.WOOD)
                .strength(2.5F)
                .sound(SoundType.WOOD)
                .noOcclusion()
                .pushReaction(PushReaction.BLOCK)
                .ignitedByLava());
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(LAYER, AttachFace.FLOOR)
                .setValue(INDEX, 4));
    }

    @Override
    public ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (hand != InteractionHand.MAIN_HAND) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
        BarrelBlockEntity barrelEntity = getBarrelEntity(level, pos, state);
        if (barrelEntity == null) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
        // 只有顶层可以交互
        if (isCeiling(state)) {
            if (!barrelEntity.isOpen()) {
                // 如果是关着的，此时点击顶层会尝试开盖
                return barrelEntity.openLid(player) ? ItemInteractionResult.SUCCESS : ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
            }

            boolean clickedLid = isLid(state);
            ItemStack itemInHand = player.getItemInHand(hand);

            // 如果的空手，且没有点击中心，则尝试关盖
            if (itemInHand.isEmpty() && !clickedLid) {
                return barrelEntity.closeLid(player) ? ItemInteractionResult.SUCCESS : ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
            }

            // 只有点击中心，才能进行添加物品或流体的交互
            if (clickedLid) {
                // 如果拿的是流体容器
                if (FluidUtils.isFluidContainer(itemInHand)) {
                    Optional<FluidStack> optional = FluidUtil.getFluidContained(itemInHand);
                    // 尝试先从容器中倒出流体到酒桶里，如果成功了就结束了
                    if (optional.isPresent() && barrelEntity.addFluid(player, itemInHand)) {
                        return ItemInteractionResult.SUCCESS;
                    }
                    // 如果倒出失败了，再尝试从酒桶里装流体到容器里
                    if (barrelEntity.removeFluid(player, itemInHand)) {
                        return ItemInteractionResult.SUCCESS;
                    }
                    // 流体容器不可作为原料输入
                    return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
                }

                // 其他情况，有物品，则尝试添加
                if (!itemInHand.isEmpty() && barrelEntity.addIngredient(player, itemInHand)) {
                    return ItemInteractionResult.SUCCESS;
                }

                // 没有物品，则尝试取出
                if (itemInHand.isEmpty() && barrelEntity.removeIngredient(player)) {
                    return ItemInteractionResult.SUCCESS;
                }
            }
        } else {
            barrelEntity.tipBrewInfo(player);
            return ItemInteractionResult.SUCCESS;
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    /**
     * 判断是否为顶盖，只有顶盖可以添加物品和流体交互
     */
    private boolean isLid(BlockState state) {
        return state.getValue(LAYER) == AttachFace.CEILING && state.getValue(INDEX) == 4;
    }

    /**
     * 判断是否为顶部，用于开盖和关盖判断
     */
    private boolean isCeiling(BlockState state) {
        return state.getValue(LAYER) == AttachFace.CEILING;
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if (level.isClientSide) {
            return null;
        }
        return createTickerHelper(blockEntityType, ModBlocks.BARREL_BE.get(),
                (levelIn, pos, stateIn, barrel) -> barrel.tick(levelIn));
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos pos = context.getClickedPos();
        Level level = context.getLevel();

        // 需要 3 格高度
        if (pos.getY() > level.getMaxBuildHeight() - 3) {
            return null;
        }

        // 检查 3x3x3 范围内是否有足够空间
        for (int y = 0; y < 3; y++) {
            for (int z = -1; z <= 1; z++) {
                for (int x = -1; x <= 1; x++) {
                    if (x == 0 && y == 0 && z == 0) {
                        continue;
                    }
                    BlockPos checkPos = pos.offset(x, y, z);
                    if (!level.getBlockState(checkPos).canBeReplaced(context)) {
                        return null;
                    }
                }
            }
        }

        Direction facing = context.getHorizontalDirection().getOpposite();
        return this.defaultBlockState()
                .setValue(FACING, facing)
                .setValue(LAYER, AttachFace.FLOOR)
                .setValue(INDEX, 4);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack) {
        if (level.isClientSide) {
            return;
        }

        Direction facing = state.getValue(FACING);

        for (int y = 0; y < 3; y++) {
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    // 跳过原点（已由放置逻辑处理）
                    if (y == 0 && row == 1 && col == 1) {
                        continue;
                    }
                    int index = row * 3 + col;
                    BlockPos targetPos = pos.offset(col - 1, y, row - 1);
                    level.setBlockAndUpdate(targetPos, this.defaultBlockState()
                            .setValue(FACING, facing)
                            .setValue(LAYER, LAYERS[y])
                            .setValue(INDEX, index));
                }
            }
        }
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        handleRemove(level, pos, state, player);
        return super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public void onBlockExploded(BlockState state, Level level, BlockPos pos, Explosion explosion) {
        handleRemove(level, pos, state, null);
        super.onBlockExploded(state, level, pos, explosion);
    }

    public static BlockPos getOriginPos(BlockPos pos, BlockState state) {
        AttachFace layer = state.getValue(LAYER);
        int index = state.getValue(INDEX);

        // 根据当前方块的 LAYER 和 INDEX 反推原点位置（FLOOR 层中心, INDEX 4）
        int yOffset = layer == AttachFace.FLOOR ? 0 : layer == AttachFace.WALL ? 1 : 2;
        int col = index % 3;
        int row = index / 3;

        // 返回原点位置
        return pos.offset(-(col - 1), -yOffset, -(row - 1));
    }

    @Nullable
    public static BarrelBlockEntity getBarrelEntity(Level level, BlockPos clickPos, BlockState clickState) {
        if (clickState.getBlock() instanceof BarrelBlock) {
            BlockPos origin = getOriginPos(clickPos, clickState);
            // 获取原点位置的 BlockEntity，并检查是否为 BarrelBlockEntity
            if (level.getBlockEntity(origin) instanceof BarrelBlockEntity barrelEntity) {
                return barrelEntity;
            }
        }
        return null;
    }

    private static void handleRemove(Level level, BlockPos pos, BlockState state, @Nullable Player player) {
        if (level.isClientSide) {
            return;
        }

        boolean drop = player == null || !player.isCreative();
        BlockPos origin = getOriginPos(pos, state);

        // 破坏 3x3x3 范围
        for (int y = 0; y < 3; y++) {
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    BlockPos targetPos = origin.offset(c - 1, y, r - 1);
                    level.destroyBlock(targetPos, drop);
                }
            }
        }
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        // 仅原点方块（FLOOR 层中心）掉落物品，避免重复掉落
        if (state.getValue(LAYER) == AttachFace.FLOOR && state.getValue(INDEX) == 4) {
            return super.getDrops(state, builder);
        }
        return Collections.emptyList();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, LAYER, INDEX);
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        // 仅底层中间方块附加 BlockEntity
        AttachFace layer = state.getValue(LAYER);
        int index = state.getValue(INDEX);
        if (layer == AttachFace.FLOOR && index == 4) {
            return new BarrelBlockEntity(pos, state);
        }
        return null;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction facing = state.getValue(FACING);
        int index = state.getValue(INDEX);
        int col = index % 3;
        int row = index / 3;
        return switch (facing) {
            case NORTH, SOUTH -> col == 0 ? SHAPE_CUT_LEFT : col == 2 ? SHAPE_CUT_RIGHT : SHAPE_FULL;
            case WEST, EAST -> row == 0 ? SHAPE_CUT_NEAR : row == 2 ? SHAPE_CUT_FAR : SHAPE_FULL;
            default -> SHAPE_FULL;
        };
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        // 仅底层中间方块能显示
        AttachFace layer = state.getValue(LAYER);
        int index = state.getValue(INDEX);
        if (layer == AttachFace.FLOOR && index == 4) {
            return RenderShape.ENTITYBLOCK_ANIMATED;
        }
        return RenderShape.INVISIBLE;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }
}
