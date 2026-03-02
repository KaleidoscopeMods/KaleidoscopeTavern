package com.github.ysbbbbbb.kaleidoscopetavern.block.brew;

import com.github.ysbbbbbb.kaleidoscopetavern.api.blockentity.IPressingTub;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.brew.PressingTubBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@SuppressWarnings("deprecation")
public class PressingTubBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final MapCodec<PressingTubBlock> CODEC = simpleCodec(p -> new PressingTubBlock());

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty TILT = BooleanProperty.create("tilt");
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public static final VoxelShape SHAPE = Shapes.join(
            Block.box(0, 0, 0, 16, 8, 16),
            Block.box(2, 4, 2, 14, 8, 14),
            BooleanOp.ONLY_FIRST
    );

    public static final VoxelShape TILTED_SHAPE_NORTH = Shapes.or(
            Block.box(0, 0, 0, 16, 8, 8),
            Block.box(0, 4, 4, 16, 12, 12),
            Block.box(0, 8, 8, 16, 16, 16)
    );
    public static final VoxelShape TILTED_SHAPE_SOUTH = Shapes.or(
            Block.box(0, 0, 8, 16, 8, 16),
            Block.box(0, 4, 4, 16, 12, 12),
            Block.box(0, 8, 0, 16, 16, 8)
    );
    public static final VoxelShape TILTED_SHAPE_WEST = Shapes.or(
            Block.box(0, 0, 0, 8, 8, 16),
            Block.box(4, 4, 0, 12, 12, 16),
            Block.box(8, 8, 0, 16, 16, 16)
    );
    public static final VoxelShape TILTED_SHAPE_EAST = Shapes.or(
            Block.box(8, 0, 0, 16, 8, 16),
            Block.box(4, 4, 0, 12, 12, 16),
            Block.box(0, 8, 0, 8, 16, 16)
    );

    public PressingTubBlock() {
        super(Properties.of()
                .mapColor(MapColor.WOOD)
                .instrument(NoteBlockInstrument.GUITAR)
                .strength(0.8F)
                .sound(SoundType.WOOD)
                .ignitedByLava());
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(TILT, false)
                .setValue(WATERLOGGED, false));
    }

    @Override
    public ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
                                           Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!(level.getBlockEntity(pos) instanceof IPressingTub pressingTub)) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        // 如果是空手，尝试取出
        ItemStack itemInHand = player.getItemInHand(hand);
        if (itemInHand.isEmpty()) {
            // 潜行时一次取出一组（64 个），否则一次取出一个
            int removeCount = player.isSecondaryUseActive() ? 64 : 1;
            if (pressingTub.removeIngredient(player, removeCount)) {
                return ItemInteractionResult.SUCCESS;
            }
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        // 然后尝试能否取出
        if (pressingTub.getResult(player, itemInHand)) {
            return ItemInteractionResult.SUCCESS;
        }

        // 最后尝试放入
        if (pressingTub.addIngredient(itemInHand)) {
            return ItemInteractionResult.SUCCESS;
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        // 倾斜的果盘不能踩
        if (state.getValue(TILT)) {
            super.fallOn(level, state, pos, entity, fallDistance);
            return;
        }

        if (entity instanceof LivingEntity livingEntity) {
            if (!(level.getBlockEntity(pos) instanceof IPressingTub pressingTub)) {
                return;
            }
            if (pressingTub.press(livingEntity, fallDistance)) {
                return;
            }
        }

        // 如果压榨不成功，则正常掉落伤害
        super.fallOn(level, state, pos, entity, fallDistance);
    }

    @Override
    public List<ItemStack> getDrops(BlockState pState, LootParams.Builder pParams) {
        List<ItemStack> stacks = super.getDrops(pState, pParams);
        BlockEntity blockEntity = pParams.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (blockEntity instanceof IPressingTub pressingTub) {
            ItemStack drop = pressingTub.getItems().getStackInSlot(0).copy();
            if (!drop.isEmpty()) {
                stacks.add(drop);
            }
        }
        return stacks;
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
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, TILT, WATERLOGGED);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        // 倾斜的朝向，默认为点击的面的朝向
        Direction clickedFace;
        // 依据点击的位置，决定是否倾斜
        boolean isTilting;

        // 如果点击的是上方或下方，那么依据玩家朝向，放置普通的
        if (context.getClickedFace().getAxis().isVertical()) {
            clickedFace = context.getHorizontalDirection().getOpposite();
            isTilting = false;
        } else {
            clickedFace = context.getClickedFace();
            isTilting = true;
        }

        return this.defaultBlockState()
                .setValue(FACING, clickedFace)
                .setValue(TILT, isTilting)
                .setValue(WATERLOGGED, context.getLevel().isWaterAt(context.getClickedPos()));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (state.getValue(TILT)) {
            return switch (state.getValue(FACING)) {
                case NORTH -> TILTED_SHAPE_NORTH;
                case SOUTH -> TILTED_SHAPE_SOUTH;
                case WEST -> TILTED_SHAPE_WEST;
                case EAST -> TILTED_SHAPE_EAST;
                default -> SHAPE;
            };
        }
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new PressingTubBlockEntity(pos, state);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState blockState) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos blockPos) {
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        if (blockEntity instanceof IPressingTub pressingTub) {
            return pressingTub.getFluidAmount();
        }
        return 0;
    }


    @Override
    public ItemStack pickupBlock(@Nullable Player player, LevelAccessor level, BlockPos pos, BlockState state) {
        ItemStack stack = SimpleWaterloggedBlock.super.pickupBlock(player, level, pos, state);
        if (!stack.isEmpty()) {
            return stack;
        }
        if (level.isClientSide()) {
            return stack;
        }

        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (!(blockEntity instanceof PressingTubBlockEntity pressingTub)) {
            return stack;
        }
        if (pressingTub.getFluidAmount() < IPressingTub.MAX_FLUID_AMOUNT) {
            return stack;
        }
        // 判断产物是否是铁桶容器的
        FluidTank fluid = pressingTub.getFluid();
        Item bucket = fluid.getFluid().getFluid().getBucket();
        if (bucket instanceof BucketItem) {
            fluid.drain(IPressingTub.MAX_FLUID_AMOUNT, IFluidHandler.FluidAction.EXECUTE);
            return bucket.getDefaultInstance();
        }
        return stack;
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
