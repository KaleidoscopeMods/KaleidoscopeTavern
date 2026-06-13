package com.github.ysbbbbbb.kaleidoscopetavern.block.deco;

import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco.IncenseBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public class IncenseBlock extends HorizontalDirectionalBlock implements EntityBlock {
    private static final VoxelShape SHAPE = Block.box(5, 0, 5, 11, 7, 11);
    private static final BooleanProperty POWERED = BooleanProperty.create("powered");

    private final Supplier<? extends ParticleOptions> smallParticle;
    private final Supplier<? extends ParticleOptions> largeParticle;

    public IncenseBlock(Supplier<? extends ParticleOptions> smallParticle, Supplier<? extends ParticleOptions> largeParticle) {
        super(BlockBehaviour.Properties.of()
                .instabreak()
                .noOcclusion()
                .pushReaction(PushReaction.DESTROY)
                .sound(SoundType.DECORATED_POT));
        this.smallParticle = smallParticle;
        this.largeParticle = largeParticle;
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(POWERED, false)
        );
    }

    public IncenseBlock(Supplier<? extends ParticleOptions> particle) {
        this(particle, particle);
    }

    @Nullable
    @SuppressWarnings("unchecked")
    protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(
            BlockEntityType<A> serverType, BlockEntityType<E> clientType, BlockEntityTicker<? super E> ticker
    ) {
        return clientType == serverType ? (BlockEntityTicker<A>) ticker : null;
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (!level.isClientSide && state.getValue(POWERED)) {
            return createTickerHelper(type, ModBlocks.INCENSE_BE.get(), IncenseBlockEntity::serverTick);
        }
        return null;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction opposite = context.getHorizontalDirection().getOpposite();
        return this.defaultBlockState()
                .setValue(FACING, opposite)
                .setValue(POWERED, false);
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        boolean powered = level.hasNeighborSignal(pos);
        boolean wasPowered = state.getValue(POWERED);
        if (powered != wasPowered) {
            level.setBlock(pos, state.setValue(POWERED, powered), Block.UPDATE_CLIENTS);
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        boolean powered = state.getValue(POWERED);

        double x = pos.getX() + 0.5;
        double y = pos.getY() + 0.5;
        double z = pos.getZ() + 0.5;

        // 小型粒子
        if (random.nextInt(3) == 0) {
            double dx = random.nextGaussian() * 0.01;
            double dy = 0.02 + random.nextDouble() * 0.01;
            double dz = random.nextGaussian() * 0.01;

            level.addParticle(smallParticle.get(), x, y, z, dx, dy, dz);
        }

        if (!powered) {
            return;
        }

        // 充能后有大型粒子
        for (int i = 0; i < 5; i++) {
            double ox = x + (random.nextDouble() - 0.5) * 32;
            double oy = y - 2 + random.nextDouble() * 16;
            double oz = z + (random.nextDouble() - 0.5) * 32;

            level.addParticle(largeParticle.get(), ox, oy, oz, 0, 0, 0);
        }
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new IncenseBlockEntity(pPos, pState);
    }
}
