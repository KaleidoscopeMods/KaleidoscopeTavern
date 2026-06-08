package com.github.ysbbbbbb.kaleidoscopetavern.block.deco;

import com.github.ysbbbbbb.kaleidoscopetavern.block.brew.BottleBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco.TiltedRackBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.item.BottleBlockItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.server.level.ServerPlayer;
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
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@SuppressWarnings("deprecation")
public class TiltedRackBlock extends HorizontalDirectionalBlock implements EntityBlock {
    private static final int[] LEFT_TO_RIGHT_SLOTS = {0, 1, 2};

    public static final VoxelShape NORTH_SHAPE = Block.box(0, 0, 5, 16, 14, 15);
    public static final VoxelShape SOUTH_SHAPE = Block.box(0, 0, 1, 16, 14, 11);
    public static final VoxelShape EAST_SHAPE = Block.box(1, 0, 0, 11, 14, 16);
    public static final VoxelShape WEST_SHAPE = Block.box(5, 0, 0, 15, 14, 16);

    public TiltedRackBlock() {
        super(Properties.of()
                .mapColor(MapColor.WOOD)
                .strength(2.5F)
                .sound(SoundType.WOOD)
                .noOcclusion()
                .ignitedByLava());
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hitResult) {
        if (level.isClientSide || hand != InteractionHand.MAIN_HAND) {
            return InteractionResult.PASS;
        }

        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (!(blockEntity instanceof TiltedRackBlockEntity tiltedRack)) {
            return InteractionResult.PASS;
        }

        ItemStack handItem = player.getItemInHand(hand);
        ItemStackHandler items = tiltedRack.getItems();
        int preferredSlot = this.getPreferredSlot(state.getValue(FACING), pos, hitResult);
        int[] slotOrder = this.getSlotOrder(preferredSlot);

        if (handItem.isEmpty()) {
            for (int slot : slotOrder) {
                ItemStack stack = items.getStackInSlot(slot);
                if (stack.isEmpty()) {
                    continue;
                }

                player.setItemInHand(hand, stack.copy());
                items.setStackInSlot(slot, ItemStack.EMPTY);
                tiltedRack.refresh();

                level.playSound(null, pos, SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.BLOCKS);
                return InteractionResult.SUCCESS;
            }

            return InteractionResult.PASS;
        }

        BottleBlock bottleBlock = this.getBottleBlock(handItem);
        if (bottleBlock == null) {
            this.sendMessage(player, Component.translatable("message.kaleidoscope_tavern.rack.not_drink"));
            return InteractionResult.PASS;
        }
        if (bottleBlock.irregular()) {
            this.sendMessage(player, Component.translatable("message.kaleidoscope_tavern.rack.irregular"));
            return InteractionResult.PASS;
        }

        for (int slot : slotOrder) {
            if (items.getStackInSlot(slot).isEmpty()) {
                items.setStackInSlot(slot, handItem.split(1));
                tiltedRack.refresh();
                level.playSound(null, pos, SoundEvents.STONE_PLACE, SoundSource.BLOCKS);
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }

    private int getPreferredSlot(Direction direction, BlockPos pos, BlockHitResult hitResult) {
        double relativeX = hitResult.getLocation().x - pos.getX();
        double relativeZ = hitResult.getLocation().z - pos.getZ();
        double localX = switch (direction) {
            case NORTH -> 1.0 - relativeX;
            case SOUTH -> relativeX;
            case EAST -> 1.0 - relativeZ;
            case WEST -> relativeZ;
            default -> 0.5;
        };

        if (localX < 1.0 / 3.0) {
            return 0;
        }
        if (localX < 2.0 / 3.0) {
            return 1;
        }
        return 2;
    }

    private int[] getSlotOrder(int preferredSlot) {
        int[] slotOrder = new int[LEFT_TO_RIGHT_SLOTS.length];
        slotOrder[0] = preferredSlot;

        int index = 1;
        for (int slot : LEFT_TO_RIGHT_SLOTS) {
            if (slot != preferredSlot) {
                slotOrder[index] = slot;
                index++;
            }
        }
        return slotOrder;
    }

    @Nullable
    private BottleBlock getBottleBlock(ItemStack stack) {
        if (stack.getItem() instanceof BottleBlockItem item
            && item.getBlock() instanceof BottleBlock bottleBlock) {
            return bottleBlock;
        }
        return null;
    }

    private void sendMessage(Player player, Component message) {
        if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.connection.send(new ClientboundSetActionBarTextPacket(message));
        }
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
        if (blockEntity instanceof TiltedRackBlockEntity rack) {
            ItemStackHandler items = rack.getItems();
            for (int i = 0; i < items.getSlots(); i++) {
                ItemStack stack = items.getStackInSlot(i);
                if (!stack.isEmpty()) {
                    stacks.add(stack);
                }
            }
        }
        return stacks;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TiltedRackBlockEntity(pos, state);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        switch (direction) {
            case NORTH -> {
                return NORTH_SHAPE;
            }
            case SOUTH -> {
                return SOUTH_SHAPE;
            }
            case WEST -> {
                return WEST_SHAPE;
            }
            case EAST -> {
                return EAST_SHAPE;
            }
        }
        return super.getShape(state, level, pos, context);
    }
}
