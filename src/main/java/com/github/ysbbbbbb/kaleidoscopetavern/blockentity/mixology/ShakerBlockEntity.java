package com.github.ysbbbbbb.kaleidoscopetavern.blockentity.mixology;

import com.github.ysbbbbbb.kaleidoscopetavern.api.blockentity.IShaker;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.BaseBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import com.github.ysbbbbbb.kaleidoscopetavern.item.BottleBlockItem;
import com.github.ysbbbbbb.kaleidoscopetavern.item.IHasContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class ShakerBlockEntity extends BaseBlockEntity implements IShaker {
    private final ItemStackHandler storage = new ItemStackHandler(3) {
        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }
    };

    private ItemStack result = ItemStack.EMPTY;
    public AnimationState putState = new AnimationState();

    public ShakerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.SHAKER_BE.get(), pos, state);
    }

    @Override
    public boolean addIngredient(ItemStack stack, @Nullable LivingEntity user) {
        // 先看看满了么
        if (!hasEmptySlots()) {
            return false;
        }

        // 如果是酒，需要首先检查品质，必须是优质以上
        if (stack.getItem() instanceof BottleBlockItem) {
            int brewLevel = BottleBlockItem.getBrewLevel(stack);
            if (brewLevel < 4) {
                if (user instanceof Player player && !player.level().isClientSide) {
                    player.sendSystemMessage(Component.translatable("message.kaleidoscope_tavern.shaker.brew_level_too_low"));
                }
                return false;
            }
        }

        ItemStack copy = stack.copyWithCount(1);
        ItemHandlerHelper.insertItemStacked(storage, copy, false);
        this.refresh();

        // 如果是容器类物品
        if (stack.getItem() instanceof IHasContainer hasContainer && user != null && level != null) {
            // 返还容器
            ItemStack carried = hasContainer.getContainerItem().getDefaultInstance();
            if (user instanceof Player player) {
                ItemHandlerHelper.giveItemToPlayer(player, carried);
            } else {
                ItemEntity itemEntity = new ItemEntity(level, user.getX(), user.getY(), user.getZ(), carried);
                level.addFreshEntity(itemEntity);
            }
            level.playSound(null, worldPosition, SoundEvents.BOTTLE_EMPTY,
                    SoundSource.BLOCKS, 0.75F, 1.0F
            );
        } else if (level != null) {
            level.playSound(null, worldPosition, SoundEvents.ITEM_FRAME_ADD_ITEM,
                    SoundSource.BLOCKS, 0.75F, 1.0F
            );
        }

        stack.shrink(1);

        // 动画
        if (level != null) {
            this.putState.start((int) level.getGameTime());
        }
        // 粒子
        if (this.level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
                    net.minecraft.core.particles.ParticleTypes.BUBBLE_POP,
                    worldPosition.getX() + 0.5, worldPosition.getY() + 0.75, worldPosition.getZ() + 0.5,
                    8, 0.2, 0.3, 0.2, 0
            );
        }

        return true;
    }

    private boolean hasEmptySlots() {
        for (int i = 0; i < storage.getSlots(); i++) {
            ItemStack slot = storage.getStackInSlot(i);
            if (slot.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("Storage", storage.serializeNBT());
        tag.put("result", result.serializeNBT());
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.storage.deserializeNBT(tag.getCompound("Storage"));
        this.result.deserializeNBT(tag.getCompound("result"));
    }

    public ItemStackHandler getStorage() {
        return storage;
    }

    public void setStorage(ItemStackHandler storage) {
        for (int i = 0; i < storage.getSlots(); i++) {
            ItemStack slot = storage.getStackInSlot(i);
            this.storage.setStackInSlot(i, slot);
        }
    }

    public ItemStack getResult() {
        return result;
    }

    public void setResult(ItemStack result) {
        this.result = result;
    }
}
