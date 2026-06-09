package com.github.ysbbbbbb.kaleidoscopetavern.block;

import com.github.ysbbbbbb.kaleidoscopetavern.block.brew.BottleBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco.StorageBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.entity.ThrownMolotovEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModSounds;
import com.github.ysbbbbbb.kaleidoscopetavern.item.BottleBlockItem;
import com.github.ysbbbbbb.kaleidoscopetavern.item.DrinkBlockItem;
import com.github.ysbbbbbb.kaleidoscopetavern.item.MolotovBlockItem;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
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
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@SuppressWarnings("deprecation")
public abstract class AbstractStorageBlock extends HorizontalDirectionalBlock implements EntityBlock {
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public AbstractStorageBlock(Properties properties) {
        super(properties);
    }

    public AbstractStorageBlock() {
        super(Properties.of()
                .mapColor(MapColor.WOOD)
                .strength(2.5F)
                .sound(SoundType.WOOD)
                .noOcclusion()
                .ignitedByLava());
    }

    /**
     * 通过点击的地方，获取应该取放哪个 index 的物品
     */
    protected abstract int getClickedSlot(Direction direction, BlockPos pos, BlockHitResult hitResult);

    /**
     * 红石激活后，发射酒瓶时，发射点的坐标
     */
    protected abstract Vec3 getShootPos(Direction direction, BlockPos pos, int slot);

    /**
     * 红石激活后，发射酒瓶时，酒瓶的初始运动向量
     */
    protected abstract Vec3 getMovement(Direction direction, BlockPos pos, int slot);

    protected abstract boolean blockListCheck(ItemStack stack);

    /**
     * 获取旋转后的局部X坐标，范围 0-1
     */
    protected double getLocalX(Direction direction, BlockPos pos, BlockHitResult hitResult) {
        double relativeX = hitResult.getLocation().x - pos.getX();
        double relativeZ = hitResult.getLocation().z - pos.getZ();
        return switch (direction) {
            case NORTH -> 1.0 - relativeX;
            case SOUTH -> relativeX;
            case EAST -> 1.0 - relativeZ;
            case WEST -> relativeZ;
            default -> 0.5;
        };
    }

    protected double getLocalZ(Direction direction, BlockPos pos, BlockHitResult hitResult) {
        double relativeX = hitResult.getLocation().x - pos.getX();
        double relativeZ = hitResult.getLocation().z - pos.getZ();
        return switch (direction) {
            case NORTH -> relativeZ;
            case SOUTH -> 1.0 - relativeZ;
            case EAST -> 1.0 - relativeX;
            case WEST -> relativeX;
            default -> 0.5;
        };
    }

    protected InteractionResult handleUse(
            BlockState state, Level level, BlockPos pos, Player player,
            InteractionHand hand, BlockHitResult hitResult
    ) {
        if (hand != InteractionHand.MAIN_HAND) {
            return InteractionResult.PASS;
        }

        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (!(blockEntity instanceof StorageBlockEntity storage)) {
            return InteractionResult.PASS;
        }

        ItemStack handItem = player.getItemInHand(hand);
        int clickedSlot = this.getClickedSlot(state.getValue(FACING), pos, hitResult);

        if (clickedSlot == -1) {
            return InteractionResult.FAIL;
        }
        if (handItem.isEmpty()) {
            return takeOut(level, pos, player, hand, storage, clickedSlot);
        }
        return putOn(level, pos, player, storage, clickedSlot);
    }

    protected InteractionResult putOn(
            Level level, BlockPos pos, Player player,
            StorageBlockEntity storage, int clickedSlot
    ) {
        ItemStackHandler items = storage.getItems();
        ItemStack handItem = player.getMainHandItem();
        BottleBlock bottleBlock = this.getBottleBlock(handItem);

        if (bottleBlock == null) {
            this.sendMessage(player, Component.translatable("message.kaleidoscope_tavern.rack.not_drink"));
            return InteractionResult.FAIL;
        }

        if (this.blockListCheck(handItem)) {
            this.sendMessage(player, Component.translatable("message.kaleidoscope_tavern.rack.irregular"));
            return InteractionResult.FAIL;
        }

        if (items.getStackInSlot(clickedSlot).isEmpty()) {
            items.setStackInSlot(clickedSlot, handItem.split(1));
            storage.refresh();

            level.playSound(null, pos, SoundEvents.STONE_PLACE, SoundSource.BLOCKS);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    protected static InteractionResult takeOut(
            Level level, BlockPos pos, Player player, InteractionHand hand,
            StorageBlockEntity storage, int clickedSlot
    ) {
        ItemStackHandler items = storage.getItems();
        ItemStack stack = items.getStackInSlot(clickedSlot);

        if (!stack.isEmpty()) {
            player.setItemInHand(hand, stack.copy());
            items.setStackInSlot(clickedSlot, ItemStack.EMPTY);
            storage.refresh();

            level.playSound(null, pos, SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.BLOCKS);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Nullable
    protected BottleBlock getBottleBlock(ItemStack stack) {
        if (stack.getItem() instanceof BottleBlockItem item
            && item.getBlock() instanceof BottleBlock bottleBlock) {
            return bottleBlock;
        }
        return null;
    }

    protected void sendMessage(Player player, Component message) {
        if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.connection.send(new ClientboundSetActionBarTextPacket(message));
        }
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        boolean powered = level.hasNeighborSignal(pos);
        boolean wasPowered = state.getValue(POWERED);
        if (powered && !wasPowered) {
            this.popBottle(level, pos, state);
        }
        if (powered != wasPowered) {
            level.setBlock(pos, state.setValue(POWERED, powered), Block.UPDATE_CLIENTS);
        }
    }

    protected void popBottle(Level level, BlockPos pos, BlockState state) {
        if (level.isClientSide || !(level.getBlockEntity(pos) instanceof StorageBlockEntity storage)) {
            return;
        }

        ItemStackHandler items = storage.getItems();
        IntList slots = new IntArrayList();

        // 随机选择一个不为空的
        for (int i = 0; i < items.getSlots(); i++) {
            if (items.getStackInSlot(i).getItem() instanceof BottleBlockItem) {
                slots.add(i);
            }
        }
        if (slots.isEmpty()) {
            return;
        }

        // 随机选择一个
        int randomIndex = level.random.nextInt(slots.size());
        int slot = slots.getInt(randomIndex);
        ItemStack stack = items.getStackInSlot(slot);

        if (stack.getItem() instanceof DrinkBlockItem drink) {
            items.setStackInSlot(slot, ItemStack.EMPTY);
            storage.refresh();

            Direction direction = state.getValue(FACING);
            int brewLevel = BottleBlockItem.getBrewLevel(stack);
            Vec3 shootPos = this.getShootPos(direction, pos, slot);
            Vec3 movement = this.getMovement(direction, pos, slot);

            drink.makeThrownPotion(level, shootPos.x(), shootPos.y(), shootPos.z(), brewLevel, null, movement);
            level.playSound(null, pos, ModSounds.HOLDER_POP.get(), SoundSource.BLOCKS, 0.9F, 1.0F);
            return;
        }

        if (stack.getItem() instanceof MolotovBlockItem) {
            items.setStackInSlot(slot, ItemStack.EMPTY);
            storage.refresh();

            Direction direction = state.getValue(FACING);
            Vec3 shootPos = this.getShootPos(direction, pos, slot);
            Vec3 movement = this.getMovement(direction, pos, slot);

            ThrownMolotovEntity molotov = new ThrownMolotovEntity(level, shootPos.x(), shootPos.y(), shootPos.z());
            molotov.setDeltaMovement(movement);
            level.addFreshEntity(molotov);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction opposite = context.getHorizontalDirection().getOpposite();
        boolean signal = context.getLevel().hasNeighborSignal(context.getClickedPos());
        return this.defaultBlockState()
                .setValue(FACING, opposite)
                .setValue(POWERED, signal);
    }

    @Override
    public float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
        return 1.0F;
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        List<ItemStack> stacks = super.getDrops(state, builder);
        BlockEntity blockEntity = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (blockEntity instanceof StorageBlockEntity storage) {
            ItemStackHandler items = storage.getItems();
            for (int i = 0; i < items.getSlots(); i++) {
                ItemStack stack = items.getStackInSlot(i);
                if (!stack.isEmpty()) {
                    stacks.add(stack);
                }
            }
        }
        return stacks;
    }
}
