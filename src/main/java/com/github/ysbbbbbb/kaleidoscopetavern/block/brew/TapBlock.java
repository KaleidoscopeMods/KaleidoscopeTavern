package com.github.ysbbbbbb.kaleidoscopetavern.block.brew;

import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.brew.BarrelBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.brew.TapBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopetavern.util.VoxelShapeUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
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
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;

import static com.github.ysbbbbbb.kaleidoscopetavern.blockentity.brew.TapBlockEntity.*;

@SuppressWarnings("deprecation")
public class TapBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;

    public final EnumMap<Direction, VoxelShape> shapes;

    public TapBlock() {
        super(Properties.of()
                .mapColor(MapColor.METAL)
                .strength(0.8F)
                .sound(SoundType.METAL));
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(OPEN, false)
                .setValue(WATERLOGGED, false));
        this.shapes = VoxelShapeUtils.horizontalShapes(
                Block.box(5, 5, 6, 11, 13, 16)
        );
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hitResult) {
        if (hand == InteractionHand.MAIN_HAND) {
            // 如果龙头已经是开启的，直接无视条件关闭
            if (state.getValue(OPEN)) {
                state = state.setValue(OPEN, false);
                level.setBlockAndUpdate(pos, state);

                level.playSound(null, pos, SoundEvents.IRON_TRAPDOOR_CLOSE, SoundSource.BLOCKS, 1.0F, 0.8F);
                return InteractionResult.SUCCESS;
            }

            Direction tapFacing = state.getValue(FACING);

            // 检查龙头是否紧贴着桶
            BlockPos relative = pos.relative(tapFacing.getOpposite());
            BlockState barrelState = level.getBlockState(relative);
            if (!(barrelState.getBlock() instanceof BarrelBlock)) {
                Component message = Component.translatable("message.kaleidoscope_tavern.tap.not_connected");
                if (player instanceof ServerPlayer serverPlayer) {
                    serverPlayer.connection.send(new ClientboundSetActionBarTextPacket(message));
                }
                this.emptyOpen(level, pos, state);
                return InteractionResult.SUCCESS;
            }

            if (!isValidConnection(barrelState, tapFacing)) {
                Component message = Component.translatable("message.kaleidoscope_tavern.tap.connected_is_invalid");
                if (player instanceof ServerPlayer serverPlayer) {
                    serverPlayer.connection.send(new ClientboundSetActionBarTextPacket(message));
                }
                this.emptyOpen(level, pos, state);
                return InteractionResult.SUCCESS;
            }

            BarrelBlockEntity barrelEntity = BarrelBlock.getBarrelEntity(level, relative, barrelState);
            if (barrelEntity == null) {
                this.emptyOpen(level, pos, state);
                return InteractionResult.SUCCESS;
            }

            if (barrelEntity.canTapExtract(level, pos, player)) {
                state = state.setValue(OPEN, true);
                level.setBlockAndUpdate(pos, state);
                level.playSound(null, pos, SoundEvents.IRON_TRAPDOOR_OPEN, SoundSource.BLOCKS, 1.0F, 0.8F);

                // 播放滴水粒子效果
                if (level.getBlockEntity(pos) instanceof TapBlockEntity tapEntity) {
                    ParticleOptions particle = ParticleTypes.DRIPPING_DRIPSTONE_WATER;
                    // 燃烧瓶很特殊
                    // FIXME: 应该用 tag 来决定粒子的效果？
                    if (barrelEntity.getOutput().getStackInSlot(0).is(ModItems.MOLOTOV.get())) {
                        particle = ParticleTypes.DRIPPING_DRIPSTONE_LAVA;
                    }
                    tapEntity.setParticle(particle);
                    tapEntity.setState(TAKE_DRINK_STATE);
                }

                // 一定时间后关闭龙头
                level.scheduleTick(pos, this, TAKE_DRINK_TICKS);
                return InteractionResult.SUCCESS;
            }

            this.emptyOpen(level, pos, state);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    /**
     * 龙头空拧，此时会有一些特殊的粒子效果，并且持续一小段时间，最后自动关闭
     */
    private void emptyOpen(Level level, BlockPos pos, BlockState blockState) {
        // 将龙头设置为关闭状态
        level.setBlockAndUpdate(pos, blockState.setValue(OPEN, true));
        level.playSound(null, pos, SoundEvents.IRON_TRAPDOOR_OPEN, SoundSource.BLOCKS, 1.0F, 0.8F);

        // 将 BE 设置为此状态
        if (level.getBlockEntity(pos) instanceof TapBlockEntity tapEntity) {
            tapEntity.setState(EMPTY_OPEN_STATE);
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        // 如果此时龙头已经被关闭了，那么停止
        if (!state.getValue(OPEN)) {
            return;
        }

        Direction tapFacing = state.getValue(FACING);
        BlockPos relative = pos.relative(tapFacing.getOpposite());
        BlockState barrelState = level.getBlockState(relative);

        BarrelBlockEntity barrelEntity = BarrelBlock.getBarrelEntity(level, relative, barrelState);
        if (barrelEntity == null) {
            return;
        }
        barrelEntity.doTapExtract(level, pos);
        state = state.setValue(OPEN, false);
        level.setBlockAndUpdate(pos, state);

        if (level.getBlockEntity(pos) instanceof TapBlockEntity tapEntity) {
            tapEntity.setParticle(null);
            tapEntity.setState(DEFAULT_STATE);
        }

        // 播放酒瓶转化效果
        level.playSound(null, pos.below(), SoundEvents.BREWING_STAND_BREW, SoundSource.BLOCKS, 1.0F, 1.0F);
        level.sendParticles(
                ParticleTypes.WAX_OFF,
                pos.getX() + 0.5,
                pos.getY() - 0.5,
                pos.getZ() + 0.5,
                10, 0.25, 0.25, 0.25, 0.1
        );

        // 关闭音效
        level.playSound(null, pos, SoundEvents.IRON_TRAPDOOR_CLOSE, SoundSource.BLOCKS, 1.0F, 0.8F);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TapBlockEntity(pos, state);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if (level.isClientSide) {
            return null;
        }
        return createTickerHelper(blockEntityType, ModBlocks.TAP_BE.get(),
                (levelIn, pos, stateIn, tap) -> tap.tick(levelIn));
    }

    private boolean isValidConnection(BlockState barrelState, Direction tapFacing) {
        Direction barrelFacing = barrelState.getValue(BarrelBlock.FACING);
        AttachFace layer = barrelState.getValue(BarrelBlock.LAYER);
        int index = barrelState.getValue(BarrelBlock.INDEX);
        // 必须紧贴桶第二层正面
        if (layer == AttachFace.WALL) {
            if (barrelFacing == Direction.NORTH && index == 1) {
                return tapFacing == Direction.NORTH;
            } else if (barrelFacing == Direction.SOUTH && index == 7) {
                return tapFacing == Direction.SOUTH;
            } else if (barrelFacing == Direction.WEST && index == 3) {
                return tapFacing == Direction.WEST;
            } else if (barrelFacing == Direction.EAST && index == 5) {
                return tapFacing == Direction.EAST;
            }
        }
        return false;
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
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos pos = context.getClickedPos();
        Direction clickedFace = context.getClickedFace();
        if (clickedFace.getAxis().isVertical()) {
            clickedFace = context.getHorizontalDirection().getOpposite();
        }
        return this.defaultBlockState()
                .setValue(FACING, clickedFace)
                .setValue(WATERLOGGED, context.getLevel().isWaterAt(pos));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, OPEN, WATERLOGGED);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        Direction direction = pState.getValue(FACING);
        return this.shapes.getOrDefault(direction, Shapes.block());
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
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
