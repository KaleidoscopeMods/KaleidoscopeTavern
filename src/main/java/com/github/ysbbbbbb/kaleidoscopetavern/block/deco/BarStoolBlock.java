package com.github.ysbbbbbb.kaleidoscopetavern.block.deco;

import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco.BarStoolBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.entity.SitEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@SuppressWarnings("deprecation")
public class BarStoolBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private final DyeColor color;

    public static final VoxelShape NORTH_SHAPE = Shapes.or(
            Block.box(5, 0, 5, 11, 2, 11),
            Block.box(7, 1, 7, 9, 12, 9),
            Block.box(2, 12, 3, 14, 15, 14),
            Block.box(2, 15, 11, 14, 21, 14)
    );

    public static final VoxelShape SOUTH_SHAPE = Shapes.or(
            Block.box(5, 0, 5, 11, 2, 11),
            Block.box(7, 1, 7, 9, 12, 9),
            Block.box(2, 12, 2, 14, 15, 13),
            Block.box(2, 15, 2, 14, 21, 5)
    );

    public static final VoxelShape EAST_SHAPE = Shapes.or(
            Block.box(5, 0, 5, 11, 2, 11),
            Block.box(7, 1, 7, 9, 12, 9),
            Block.box(2, 12, 2, 13, 15, 14),
            Block.box(2, 15, 2, 5, 21, 14)
    );

    public static final VoxelShape WEST_SHAPE = Shapes.or(
            Block.box(5, 0, 5, 11, 2, 11),
            Block.box(7, 1, 7, 9, 12, 9),
            Block.box(3, 12, 2, 14, 15, 14),
            Block.box(11, 15, 2, 14, 21, 14)
    );

    public BarStoolBlock(DyeColor color) {
        super(Properties.of()
                .mapColor(MapColor.WOOD)
                .instrument(NoteBlockInstrument.GUITAR)
                .strength(0.8F)
                .sound(SoundType.WOOD)
                .noOcclusion()
                .ignitedByLava());
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, false));
        this.color = color;
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
        builder.add(FACING, WATERLOGGED);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!level.isClientSide) {
            List<SitEntity> entities = level.getEntitiesOfClass(SitEntity.class, new AABB(pos));
            if (entities.isEmpty()) {
                SitEntity entitySit = new SitEntity(level, pos, 0.875);
                entitySit.setYRot(state.getValue(FACING).toYRot());
                level.addFreshEntity(entitySit);
                player.startRiding(entitySit, true);
                if (level.getBlockEntity(pos) instanceof BarStoolBlockEntity blockEntity) {
                    blockEntity.setSitEntity(entitySit);
                }
                return InteractionResult.SUCCESS;
            }
        } else {
            // todo-check
            // 不知有无炸弹:(, 但这样能够将抱着的生物（使用 carryOn 模组）放置在方块上，而不是抱着生物坐在凳子上
            return InteractionResult.sidedSuccess(true);
        }
        return InteractionResult.PASS;
    }

    @Override
    public void destroy(LevelAccessor levelAccessor, BlockPos pos, BlockState state) {
        levelAccessor.getEntitiesOfClass(SitEntity.class, new AABB(pos)).forEach(Entity::discard);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public BlockState rotate(BlockState blockState, Rotation rotation) {
        return blockState.setValue(FACING, rotation.rotate(blockState.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState blockState, Mirror mirror) {
        return blockState.rotate(mirror.getRotation(blockState.getValue(FACING)));
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

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new BarStoolBlockEntity(blockPos, blockState, color);
    }

    @Override
    public <T extends BlockEntity> @Nullable BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if (level.isClientSide()) {
            return null;
        }
        return createTickerHelper(blockEntityType, ModBlocks.BAR_STOOL_BE.get(),
                (levelIn, pos, stateIn, tap) -> tap.tick());
    }

    public DyeColor getColor() {
        return color;
    }
}
