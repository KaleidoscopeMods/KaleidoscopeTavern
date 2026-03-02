package com.github.ysbbbbbb.kaleidoscopetavern.block.brew;

import com.github.ysbbbbbb.kaleidoscopetavern.block.properties.PositionType;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.brew.BarCabinetBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.item.BottleBlockItem;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@SuppressWarnings("deprecation")
public class BarCabinetBlock extends BaseEntityBlock {
    public static final MapCodec<BarCabinetBlock> CODEC = simpleCodec(p -> new BarCabinetBlock());
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<PositionType> POSITION = EnumProperty.create("position", PositionType.class);

    public BarCabinetBlock() {
        super(Properties.of()
                .mapColor(MapColor.WOOD)
                .strength(2.5F)
                .sound(SoundType.WOOD)
                .noOcclusion()
                .ignitedByLava());
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(POSITION, PositionType.SINGLE));
    }

    @Override
    public ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
                                           Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.isClientSide || hand != InteractionHand.MAIN_HAND) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        Direction direction = state.getValue(FACING);

        // 判断点击的是左侧还是右侧
        boolean isLeftSide = switch (direction) {
            case NORTH -> hitResult.getLocation().x - pos.getX() > 0.5;
            case SOUTH -> hitResult.getLocation().x - pos.getX() < 0.5;
            case EAST -> hitResult.getLocation().z - pos.getZ() < 0.5;
            case WEST -> hitResult.getLocation().z - pos.getZ() > 0.5;
            default -> false;
        };

        if (level.getBlockEntity(pos) instanceof BarCabinetBlockEntity barCabinet
            && this.onClick(barCabinet, player, stack, isLeftSide)) {
            float pitch = stack.isEmpty() ? level.random.nextFloat() * 0.2F + 0.8F : level.random.nextFloat() * 0.2F + 0.2F;
            level.playSound(null, pos, SoundEvents.GLASS_PLACE, SoundSource.BLOCKS,
                    level.random.nextFloat() * 0.2F + 0.8F, pitch);
            return ItemInteractionResult.SUCCESS;
        }

        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    private boolean onClick(BarCabinetBlockEntity barCabinet, Player player, ItemStack stack, boolean isLeftSide) {
        // 如果是异形酒瓶，那么永远都是 isLeftSide = true，放在左边
        boolean irregular = false;
        boolean single = barCabinet.isSingle();

        BottleBlock bottleBlock = this.getBottleBlock(stack);
        ItemStack leftItem = barCabinet.getLeftItem();
        ItemStack rightItem = barCabinet.getRightItem();

        if (bottleBlock != null) {
            // 如果酒柜已经是 single 状态了，此时不允许放入新的酒瓶
            if (single) {
                return false;
            }
            // 异形酒瓶
            if (bottleBlock.irregular()) {
                // 如果已经有酒了，那么不允许放入异形酒瓶
                if (!leftItem.isEmpty() || !rightItem.isEmpty()) {
                    return false;
                }
                // 否则，强制左侧取放
                isLeftSide = true;
                irregular = true;
            } else {
                // 如果仅单个物品，那么强制选择对应侧的
                if (!leftItem.isEmpty() && rightItem.isEmpty() && isLeftSide) {
                    isLeftSide = false;
                } else if (leftItem.isEmpty() && !rightItem.isEmpty() && !isLeftSide) {
                    isLeftSide = true;
                }
            }
        }

        // 如果是空手
        if (stack.isEmpty()) {
            // 异形酒瓶，强制左侧取放
            if (single) {
                isLeftSide = true;
                irregular = true;
            } else {
                // 如果仅单个物品，那么强制选择对应侧的
                if (leftItem.isEmpty() && !rightItem.isEmpty() && isLeftSide) {
                    isLeftSide = false;
                } else if (!leftItem.isEmpty() && rightItem.isEmpty() && !isLeftSide) {
                    isLeftSide = true;
                }
            }
        }

        if (isLeftSide) {
            if (stack.isEmpty()) {
                if (!leftItem.isEmpty()) {
                    player.setItemInHand(InteractionHand.MAIN_HAND, leftItem.copy());
                    barCabinet.setLeftItem(ItemStack.EMPTY);
                    barCabinet.setSingle(false);
                    barCabinet.refresh();
                    return true;
                }
                return false;
            }

            if (bottleBlock != null && leftItem.isEmpty()) {
                ItemStack split = stack.split(1);
                barCabinet.setLeftItem(split);
                barCabinet.setSingle(irregular);
                barCabinet.refresh();
                return true;
            }
        } else {
            if (stack.isEmpty()) {
                if (!rightItem.isEmpty()) {
                    player.setItemInHand(InteractionHand.MAIN_HAND, rightItem.copy());
                    barCabinet.setRightItem(ItemStack.EMPTY);
                    barCabinet.setSingle(false);
                    barCabinet.refresh();
                    return true;
                }
                return false;
            }

            if (bottleBlock != null && rightItem.isEmpty()) {
                ItemStack split = stack.split(1);
                barCabinet.setRightItem(split);
                barCabinet.refresh();
                barCabinet.setSingle(irregular);
                return true;
            }
        }

        return false;
    }

    @Nullable
    private BottleBlock getBottleBlock(ItemStack stack) {
        if (stack.getItem() instanceof BottleBlockItem item
            && item.getBlock() instanceof BottleBlock bottleBlock) {
            return bottleBlock;
        }
        return null;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState,
                                  LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        Direction self = state.getValue(FACING);
        Direction left = self.getClockWise();
        Direction right = self.getCounterClockWise();

        // 如果更新来自左边
        if (direction == left) {
            boolean leftIsCabinet = neighborState.is(this) && neighborState.getValue(FACING) == self;
            PositionType position = state.getValue(POSITION);
            if (leftIsCabinet) {
                if (position == PositionType.SINGLE) {
                    return state.setValue(POSITION, PositionType.RIGHT);
                } else if (position == PositionType.LEFT) {
                    return state.setValue(POSITION, PositionType.MIDDLE);
                }
            } else {
                if (position == PositionType.RIGHT) {
                    return state.setValue(POSITION, PositionType.SINGLE);
                } else if (position == PositionType.MIDDLE) {
                    return state.setValue(POSITION, PositionType.LEFT);
                }
            }
        } else if (direction == right) {
            boolean rightIsCabinet = neighborState.is(this) && neighborState.getValue(FACING) == self;
            PositionType position = state.getValue(POSITION);
            if (rightIsCabinet) {
                if (position == PositionType.SINGLE) {
                    return state.setValue(POSITION, PositionType.LEFT);
                } else if (position == PositionType.RIGHT) {
                    return state.setValue(POSITION, PositionType.MIDDLE);
                }
            } else {
                if (position == PositionType.LEFT) {
                    return state.setValue(POSITION, PositionType.SINGLE);
                } else if (position == PositionType.MIDDLE) {
                    return state.setValue(POSITION, PositionType.RIGHT);
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

        // 如果左右两边都是酒柜，自己是中间
        if (leftIsCabinet && rightIsCabinet) {
            position = PositionType.MIDDLE;
        } else if (leftIsCabinet) {
            position = PositionType.RIGHT;
        } else if (rightIsCabinet) {
            position = PositionType.LEFT;
        }

        return this.defaultBlockState()
                .setValue(FACING, opposite)
                .setValue(POSITION, position);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, POSITION);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        List<ItemStack> stacks = super.getDrops(state, builder);
        BlockEntity blockEntity = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (blockEntity instanceof BarCabinetBlockEntity barCabinet) {
            ItemStack leftItem = barCabinet.getLeftItem();
            if (!leftItem.isEmpty()) {
                stacks.add(leftItem.copy());
            }
            ItemStack rightItem = barCabinet.getRightItem();
            if (!rightItem.isEmpty()) {
                stacks.add(rightItem.copy());
            }
        }
        return stacks;
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BarCabinetBlockEntity(pos, state);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }
}
