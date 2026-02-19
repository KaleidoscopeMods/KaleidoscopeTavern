package com.github.ysbbbbbb.kaleidoscopetavern.block.deco;

import com.google.common.collect.Maps;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

@SuppressWarnings("deprecation")
public class StringLightsBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock {
    public static final Map<Item, StringLightsBlock> TRANSFORM_MAP = Maps.newHashMap();

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public static final VoxelShape SOUTH_SHAPE = Block.box(0, 4, 0, 16, 16, 6);
    public static final VoxelShape NORTH_SHAPE = Block.box(0, 4, 10, 16, 16, 16);
    public static final VoxelShape EAST_SHAPE = Block.box(0, 4, 0, 6, 16, 16);
    public static final VoxelShape WEST_SHAPE = Block.box(10, 4, 0, 16, 16, 16);

    public final @Nullable DyeItem dyeItem;

    public StringLightsBlock(@Nullable Item dyeItem) {
        super(Properties.of()
                .mapColor(dyeItem instanceof DyeItem dye ? dye.getDyeColor() : DyeColor.WHITE)
                .instrument(NoteBlockInstrument.HAT)
                .strength(0.8F)
                .sound(SoundType.CHAIN)
                .noOcclusion()
                .lightLevel(s -> 15));
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, false));
        if (dyeItem instanceof DyeItem dye) {
            this.dyeItem = dye;
            TRANSFORM_MAP.put(dyeItem, this);
        } else {
            this.dyeItem = null;
        }
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
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hitResult) {
        Item item = player.getItemInHand(hand).getItem();
        if (TRANSFORM_MAP.containsKey(item) && item != this.dyeItem) {
            BlockState transform = TRANSFORM_MAP.get(item)
                    .defaultBlockState()
                    .setValue(FACING, state.getValue(FACING))
                    .setValue(WATERLOGGED, state.getValue(WATERLOGGED));
            level.setBlockAndUpdate(pos, transform);
            level.playSound(null, pos, SoundEvents.DYE_USE, SoundSource.BLOCKS);
            level.levelEvent(player, LevelEvent.PARTICLES_PLANT_GROWTH, pos, 0);
            if (!player.isCreative()) {
                player.getItemInHand(hand).shrink(1);
            }
            return InteractionResult.SUCCESS;
        }
        return super.use(state, level, pos, player, hand, hitResult);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction clickedFace = context.getClickedFace();
        if (clickedFace.getAxis().isVertical()) {
            clickedFace = context.getHorizontalDirection().getOpposite();
        }
        boolean waterLogged = context.getLevel().isWaterAt(context.getClickedPos());
        return this.defaultBlockState()
                .setValue(FACING, clickedFace)
                .setValue(WATERLOGGED, waterLogged);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return switch (pState.getValue(FACING)) {
            case SOUTH -> SOUTH_SHAPE;
            case EAST -> EAST_SHAPE;
            case WEST -> WEST_SHAPE;
            default -> NORTH_SHAPE;
        };
    }
}
