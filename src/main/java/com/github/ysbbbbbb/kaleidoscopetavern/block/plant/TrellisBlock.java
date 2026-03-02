package com.github.ysbbbbbb.kaleidoscopetavern.block.plant;

import com.github.ysbbbbbb.kaleidoscopetavern.block.properties.TrellisType;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import static com.github.ysbbbbbb.kaleidoscopetavern.block.plant.ITrellis.*;

@SuppressWarnings("deprecation")
public class TrellisBlock extends Block implements SimpleWaterloggedBlock, ITrellis {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

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

    @Override
    public ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
                                           Player player, InteractionHand hand, BlockHitResult hitResult) {
        // 玩家手持的是葡萄藤
        ItemStack itemInHand = player.getItemInHand(hand);
        if (!itemInHand.is(ModItems.GRAPEVINE.get())) {
            return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
        }
        // 如果藤架是单个的
        TrellisType type = state.getValue(TYPE);
        if (type != TrellisType.SINGLE) {
            return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
        }
        // 并且下方是泥土，那么可以种植
        BlockState belowState = level.getBlockState(pos.below());
        if (belowState.is(BlockTags.DIRT)) {
            BlockState plantedState = ModBlocks.GRAPEVINE_TRELLIS.get()
                    .defaultBlockState()
                    .setValue(WATERLOGGED, state.getValue(WATERLOGGED));
            level.setBlockAndUpdate(pos, plantedState);
            level.playSound(null, pos, SoundEvents.CROP_PLANTED, SoundSource.BLOCKS);
            if (!player.isCreative()) {
                itemInHand.shrink(1);
            }
            return ItemInteractionResult.SUCCESS;
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
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
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Direction.Axis clickAxis = context.getClickedFace().getAxis();

        boolean xHas = axisHasTrellis(level, pos, Direction.Axis.X);
        boolean yHas = axisHasTrellis(level, pos, Direction.Axis.Y);
        boolean zHas = axisHasTrellis(level, pos, Direction.Axis.Z);
        var type = getTypeForPlacement(xHas, yHas, zHas, clickAxis);

        return this.defaultBlockState()
                .setValue(TYPE, type)
                .setValue(WATERLOGGED, level.isWaterAt(pos));
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
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TYPE, WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }
}
