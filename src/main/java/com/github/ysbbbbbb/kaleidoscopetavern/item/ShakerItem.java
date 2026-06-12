package com.github.ysbbbbbb.kaleidoscopetavern.item;

import com.github.ysbbbbbb.kaleidoscopetavern.client.animation.ShakerAnimation;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModSounds;
import com.github.ysbbbbbb.kaleidoscopetavern.util.ColorUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class ShakerItem extends BlockItem {
    private static final String STORAGE_TAG = "Storage";

    public ShakerItem() {
        super(ModBlocks.SHAKER.get(), new Properties());
    }

    public static ItemStackHandler getStorage(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        ItemStackHandler handler = new ItemStackHandler();
        handler.deserializeNBT(tag.getCompound(STORAGE_TAG));
        return handler;
    }

    public static void setStorage(ItemStack stack, ItemStackHandler handler) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.put(STORAGE_TAG, handler.serializeNBT());
    }

    public static boolean hasStorage(ItemStack stack) {
        return stack.getOrCreateTag().contains(STORAGE_TAG);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }

    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int remainingUseDuration) {
        if (remainingUseDuration % 10 == 0) {
            entity.playSound(ModSounds.SHAKER_SHAKING.get(),
                    level.random.nextFloat() * 0.2F + 0.75F,
                    level.random.nextFloat() * 0.2F + 0.8F
            );
        }
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
        int time = this.getUseDuration(stack) - timeLeft;
        if (time < 10) {
            return;
        }

        if (!level.isClientSide) {
            // 调酒
        }
        entity.playSound(ModSounds.SHAKER_END.get());
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 72000;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new ShakerAnimation.ShakerExtensions());
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        if (!hasStorage(stack)) {
            return;
        }
        ItemStackHandler storage = getStorage(stack);
        for (int i = 0; i < storage.getSlots(); i++) {
            ItemStack ingredient = storage.getStackInSlot(i);
            if (ingredient.isEmpty()) {
                continue;
            }
            if (ingredient.getHoverName() instanceof MutableComponent component) {
                ChatFormatting chatFormatting = ColorUtils.ITEM_COLOR_CACHE.apply(ingredient.getItem());
                if (chatFormatting == ChatFormatting.RESET) {
                    chatFormatting = ChatFormatting.GRAY;
                }
                tooltip.add(component.withStyle(chatFormatting));
            }
        }
    }
}
