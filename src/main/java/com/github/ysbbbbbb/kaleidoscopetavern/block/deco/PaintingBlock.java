package com.github.ysbbbbbb.kaleidoscopetavern.block.deco;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@SuppressWarnings("deprecation")
public class PaintingBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock {
    public static final EnumProperty<AttachFace> ATTACH_FACE = BlockStateProperties.ATTACH_FACE;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public static final VoxelShape NORTH_SHAPE = Block.box(1, 1, 15, 15, 15, 16);
    public static final VoxelShape SOUTH_SHAPE = Block.box(1, 1, 0, 15, 15, 1);
    public static final VoxelShape WEST_SHAPE = Block.box(15, 1, 1, 16, 15, 15);
    public static final VoxelShape EAST_SHAPE = Block.box(0, 1, 1, 1, 15, 15);
    public static final VoxelShape FLOOR_SHAPE = Block.box(1, 0, 1, 15, 1, 15);
    public static final VoxelShape CEILING_SHAPE = Block.box(1, 15, 1, 15, 16, 15);

    private @Nullable String tooltipKey;

    public PaintingBlock() {
        super(Properties.of()
                .mapColor(MapColor.WOOD)
                .instrument(NoteBlockInstrument.HAT)
                .strength(0.8F)
                .sound(SoundType.WOOD)
                .noOcclusion());
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(ATTACH_FACE, AttachFace.WALL)
                .setValue(WATERLOGGED, false));
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
        builder.add(FACING, ATTACH_FACE, WATERLOGGED);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction clickedFace = context.getClickedFace();
        AttachFace attachFace = AttachFace.WALL;
        if (clickedFace.getAxis().isVertical()) {
            attachFace = clickedFace == Direction.UP ? AttachFace.FLOOR : AttachFace.CEILING;
            Direction horizontalDirection = context.getHorizontalDirection();
            clickedFace = clickedFace == Direction.UP ? horizontalDirection.getOpposite() : horizontalDirection;
        }
        boolean waterLogged = context.getLevel().isWaterAt(context.getClickedPos());
        return this.defaultBlockState()
                .setValue(FACING, clickedFace)
                .setValue(ATTACH_FACE, attachFace)
                .setValue(WATERLOGGED, waterLogged);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        AttachFace attachFace = pState.getValue(ATTACH_FACE);
        if (attachFace == AttachFace.FLOOR) {
            return FLOOR_SHAPE;
        } else if (attachFace == AttachFace.CEILING) {
            return CEILING_SHAPE;
        }
        return switch (pState.getValue(FACING)) {
            case SOUTH -> SOUTH_SHAPE;
            case EAST -> EAST_SHAPE;
            case WEST -> WEST_SHAPE;
            default -> NORTH_SHAPE;
        };
    }

    @Override
    public String getDescriptionId() {
        return "block.kaleidoscope_tavern.painting";
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter level, List<Component> tooltip, TooltipFlag flag) {
        if (this.tooltipKey == null) {
            this.tooltipKey = Util.makeDescriptionId("tooltip", BuiltInRegistries.BLOCK.getKey(this));
        }
        tooltip.add(Component.translatable(this.tooltipKey).withStyle(ChatFormatting.GRAY));
    }
}