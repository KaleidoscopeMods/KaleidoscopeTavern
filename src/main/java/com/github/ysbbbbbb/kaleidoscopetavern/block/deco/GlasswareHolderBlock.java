package com.github.ysbbbbbb.kaleidoscopetavern.block.deco;

import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco.GlasswareHolderBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@SuppressWarnings("deprecation")
public class GlasswareHolderBlock extends HorizontalDirectionalBlock implements EntityBlock {
    public static final VoxelShape NORTH_SOUTH_SHAPE = Block.box(0, 11, 1, 16, 16, 15);
    public static final VoxelShape EAST_WEST_SHAPE = Block.box(1, 11, 0, 15, 16, 16);

    public GlasswareHolderBlock() {
        super(Properties.of()
                .mapColor(MapColor.METAL)
                .strength(0.8F)
                .sound(SoundType.METAL)
                .lightLevel(s -> 8)
                .noOcclusion());
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hitResult) {
        if (hand != InteractionHand.MAIN_HAND) {
            return InteractionResult.PASS;
        }

        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (!(blockEntity instanceof GlasswareHolderBlockEntity be)) {
            return InteractionResult.PASS;
        }

        int slot = this.getSlotFromHit(hitResult, pos);
        ItemStack itemInHand = player.getItemInHand(hand);

        // 手持空酒杯：尝试放入指定槽位
        if (itemInHand.is(ModItems.EMPTY_GLASSWARE.get())) {
            return putOn(level, pos, player, be, slot);
        }

        // 空手：尝试从指定槽位取出
        if (itemInHand.isEmpty()) {
            return takeOut(level, pos, player, be, slot);
        }

        return InteractionResult.PASS;
    }

    private InteractionResult takeOut(Level level, BlockPos pos, Player player, GlasswareHolderBlockEntity be, int slot) {
        ItemStackHandler items = be.getItems();
        if (items.getStackInSlot(slot).isEmpty()) {
            return InteractionResult.PASS;
        }

        if (!level.isClientSide()) {
            ItemStack extracted = items.extractItem(slot, 1, false);
            player.setItemInHand(InteractionHand.MAIN_HAND, extracted);
            be.refresh();
            level.playSound(null, pos, SoundEvents.AMETHYST_BLOCK_PLACE, SoundSource.BLOCKS);
        }

        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    private InteractionResult putOn(Level level, BlockPos pos, Player player, GlasswareHolderBlockEntity be,
                                    int slot
    ) {
        ItemStackHandler items = be.getItems();
        if (!items.getStackInSlot(slot).isEmpty()) {
            return InteractionResult.CONSUME;
        }

        ItemStack itemInHand = player.getMainHandItem();
        if (!level.isClientSide()) {
            items.setStackInSlot(slot, itemInHand.copyWithCount(1));
            if (!player.getAbilities().instabuild) {
                itemInHand.shrink(1);
            }
            be.refresh();
            level.playSound(null, pos, SoundEvents.AMETHYST_BLOCK_PLACE, SoundSource.BLOCKS);
        }

        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    private int getSlotFromHit(BlockHitResult hitResult, BlockPos pos) {
        Vec3 hit = hitResult.getLocation();
        double localX = hit.x - pos.getX();
        double localZ = hit.z - pos.getZ();

        if (localX > 0.5) {
            return localZ > 0.5 ? 3 : 1;
        } else {
            return localZ > 0.5 ? 2 : 0;
        }
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new GlasswareHolderBlockEntity(pos, state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction opposite = context.getHorizontalDirection().getOpposite();
        return this.defaultBlockState().setValue(FACING, opposite);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        List<ItemStack> stacks = super.getDrops(state, builder);
        BlockEntity blockEntity = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (!(blockEntity instanceof GlasswareHolderBlockEntity be)) {
            return stacks;
        }
        ItemStackHandler items = be.getItems();
        for (int i = 0; i < items.getSlots(); i++) {
            ItemStack stack = items.getStackInSlot(i);
            if (!stack.isEmpty()) {
                stacks.add(stack);
            }
        }
        return stacks;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return switch (state.getValue(FACING)) {
            case NORTH, SOUTH -> NORTH_SOUTH_SHAPE;
            default -> EAST_WEST_SHAPE;
        };
    }
}
