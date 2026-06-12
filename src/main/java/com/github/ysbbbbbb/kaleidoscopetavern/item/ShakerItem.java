package com.github.ysbbbbbb.kaleidoscopetavern.item;

import com.github.ysbbbbbb.kaleidoscopetavern.client.animation.ShakerAnimation;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModRecipes;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModSounds;
import com.github.ysbbbbbb.kaleidoscopetavern.util.ColorUtils;
import com.google.common.collect.Lists;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

import static com.github.ysbbbbbb.kaleidoscopetavern.block.mixology.GlasswareBlock.ROTATION;

public class ShakerItem extends BlockItem {
    private static final String STORAGE_TAG = "Storage";
    private static final String RESULT_TAG = "Result";

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

    public static ItemStack getResult(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        if (!tag.contains(RESULT_TAG, Tag.TAG_COMPOUND)) {
            return ItemStack.EMPTY;
        }
        return ItemStack.of(tag.getCompound(RESULT_TAG));
    }

    public static void setResult(ItemStack stack, ItemStack result) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.put(RESULT_TAG, result.serializeNBT());
    }

    public static boolean hasResult(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        return tag.contains(RESULT_TAG, Tag.TAG_COMPOUND);
    }

    public static void removeAll(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.remove(RESULT_TAG);
        tag.remove(STORAGE_TAG);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        ItemStack stack = context.getItemInHand();
        BlockPos pos = context.getClickedPos();

        // 如果有产物，并且点击的是空鸡尾酒杯，尝试倒出
        BlockState rawState = level.getBlockState(pos);
        if (hasResult(stack) && rawState.is(ModBlocks.EMPTY_GLASSWARE.get())) {
            ItemStack result = getResult(stack);
            if (result.getItem() instanceof BlockItem blockItem) {
                BlockState blockState = blockItem.getBlock().defaultBlockState();
                if (blockState.hasProperty(ROTATION)) {
                    blockState = blockState.setValue(ROTATION, rawState.getValue(ROTATION));
                }
                level.setBlockAndUpdate(pos, blockState);
            }
            if (level instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(
                        ParticleTypes.EFFECT,
                        pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                        20, 0.1, 0.1, 0.1, 0.5
                );
            }
            removeAll(stack);
            level.playSound(null, pos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
            return InteractionResult.SUCCESS;
        }

        return super.useOn(context);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemInHand = player.getItemInHand(hand);
        // 如果已经有结果了，不能再摇了
        if (hasResult(itemInHand)) {
            return InteractionResultHolder.pass(itemInHand);
        }
        if (hasStorageItem(itemInHand)) {
            player.startUsingItem(hand);
            return InteractionResultHolder.consume(itemInHand);
        }
        return InteractionResultHolder.pass(itemInHand);
    }

    private boolean hasStorageItem(ItemStack itemInHand) {
        if (!hasStorage(itemInHand)) {
            return false;
        }
        ItemStackHandler storage = getStorage(itemInHand);
        for (int i = 0; i < storage.getSlots(); i++) {
            ItemStack stack = storage.getStackInSlot(i);
            if (!stack.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int remainingUseDuration) {
        if (remainingUseDuration % 10 == 0) {
            entity.playSound(ModSounds.SHAKER_SHAKING.get(),
                    level.random.nextFloat() * 0.2F + 0.75F,
                    level.random.nextFloat() * 0.2F + 0.8F
            );
        }
        // 超出时长，强制中断
        if (entity.getTicksUsingItem() > 110) {
            entity.releaseUsingItem();
        }
    }

    /**
     * < 19 未开始
     * < 69 迷之鸡尾酒
     * < 89 普通鸡尾酒
     * < 99 特调鸡尾酒
     * <= 110 迷之鸡尾酒
     */
    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
        int time = this.getUseDuration(stack) - timeLeft;
        if (time < 19) {
            return;
        }
        // 调酒
        if (!level.isClientSide) {
            if (time < 69) {
                setResult(stack, new ItemStack(ModItems.MYSTERY_COCKTAIL.get()));
            } else if (time < 89) {
                setResult(stack, new ItemStack(ModItems.ORDINARY_COCKTAIL.get()));
            } else if (time < 99) {
                handRecipe(level, stack);
            } else {
                setResult(stack, new ItemStack(ModItems.MYSTERY_COCKTAIL.get()));
            }
        }
        entity.playSound(ModSounds.SHAKER_END.get());
    }

    private void handRecipe(Level level, ItemStack stack) {
        ItemStackHandler handler = getStorage(stack);
        List<ItemStack> stacks = Lists.newArrayList();
        for (int i = 0; i < handler.getSlots(); i++) {
            ItemStack ingredient = handler.getStackInSlot(i);
            if (ingredient.isEmpty()) {
                continue;
            }
            stacks.add(ingredient);
        }
        SimpleContainer container = new SimpleContainer(stacks.toArray(new ItemStack[0]));

        RecipeManager manager = level.getRecipeManager();
        RegistryAccess access = level.registryAccess();
        manager.getRecipeFor(ModRecipes.SHAKER_RECIPE, container, level).ifPresentOrElse(
                recipe -> setResult(stack, recipe.assemble(container, access)),
                () -> setResult(stack, ModItems.MYSTERY_COCKTAIL.get().getDefaultInstance())
        );
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
        if (hasResult(stack)) {
            ItemStack result = getResult(stack);
            if (result.getHoverName() instanceof MutableComponent component) {
                tooltip.add(component.withStyle(ChatFormatting.GRAY));
            }
        } else if (hasStorage(stack)) {
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
}
