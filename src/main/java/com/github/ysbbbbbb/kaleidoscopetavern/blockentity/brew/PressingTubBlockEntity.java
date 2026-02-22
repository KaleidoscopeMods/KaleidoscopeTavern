package com.github.ysbbbbbb.kaleidoscopetavern.blockentity.brew;

import com.github.ysbbbbbb.kaleidoscopetavern.api.blockentity.IPressingTub;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.BaseBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.crafting.recipe.PressingTubRecipe;
import com.github.ysbbbbbb.kaleidoscopetavern.crafting.recipe.PressingTubRecipe.PressingTubRecipeCache;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModRecipes;
import com.github.ysbbbbbb.kaleidoscopetavern.util.ItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class PressingTubBlockEntity extends BaseBlockEntity implements IPressingTub {
    private final RecipeManager.CachedCheck<Container, PressingTubRecipe> quickCheck = RecipeManager.createCheck(ModRecipes.PRESSING_TUB_RECIPE);

    /**
     * 压榨桶的物品槽，目前只有一个槽位，用于放置被压榨的物品，最大可放入一组件（64个）物品
     */
    private final ItemStackHandler items = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            // 物品槽内容改变时，需要强制刷新状态，以便客户端同步
            refresh();
        }
    };
    /**
     * 当前压榨桶内的液体量，强制认定 8 数量才能够完成压榨并取出
     */
    private int liquidAmount = 0;
    /**
     * 当前压榨桶缓存的配方，用于持续压榨的判断，客户端渲染和者交互提示等用途，默认值为 null 代表没有缓存的配方
     */
    private @Nullable PressingTubRecipeCache cachedRecipe = null;
    /**
     * 物品栏的 Capability，用于漏斗自动化
     */
    private @Nullable LazyOptional<IItemHandler> itemCapability = null;

    public PressingTubBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.PRESSING_TUB_BE.get(), pos, state);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.items.deserializeNBT(tag.getCompound("items"));
        this.liquidAmount = tag.getInt("liquid_amount");
        if (tag.contains("cached_recipe")) {
            this.cachedRecipe = PressingTubRecipeCache.fromTag(tag.getCompound("cached_recipe"));
        } else {
            this.cachedRecipe = null;
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("items", this.items.serializeNBT());
        tag.putInt("liquid_amount", this.liquidAmount);
        if (this.cachedRecipe != null) {
            tag.put("cached_recipe", this.cachedRecipe.toTag());
        }
    }

    @Override
    public boolean addIngredient(ItemStack stack) {
        int count = stack.getCount();
        ItemStack remaining = this.items.insertItem(0, stack.copy(), false);
        if (remaining.getCount() >= count) {
            return false;
        }
        stack.shrink(count - remaining.getCount());
        if (this.level instanceof ServerLevel) {
            this.level.playSound(null,
                    worldPosition.getX() + 0.5,
                    worldPosition.getY() + 0.5,
                    worldPosition.getZ() + 0.5,
                    SoundEvents.ITEM_FRAME_ADD_ITEM,
                    SoundSource.BLOCKS,
                    0.5F + this.level.random.nextFloat(),
                    this.level.random.nextFloat() * 0.7F + 0.6F);
        }
        return true;
    }

    @Override
    public boolean removeIngredient(LivingEntity target) {
        ItemStack removed = this.items.extractItem(0, 64, false);
        if (removed.isEmpty()) {
            return false;
        }
        ItemUtils.getItemToLivingEntity(target, removed);
        if (this.level instanceof ServerLevel) {
            this.level.playSound(null,
                    worldPosition.getX() + 0.5,
                    worldPosition.getY() + 0.5,
                    worldPosition.getZ() + 0.5,
                    SoundEvents.ITEM_FRAME_REMOVE_ITEM,
                    SoundSource.BLOCKS,
                    0.5F + this.level.random.nextFloat(),
                    this.level.random.nextFloat() * 0.7F + 0.6F);
        }
        return true;
    }

    @Override
    public boolean press(LivingEntity target, float fallDistance) {
        if (level == null) {
            return false;
        }
        if (fallDistance < MIN_FALL_DISTANCE) {
            return false;
        }

        if (liquidAmount > 0) {
            // 只要有流体，就播放音效和粒子
            playFallOnEffect();
        }

        ItemStack stack = items.getStackInSlot(0);
        if (stack.isEmpty()) {
            return false;
        }

        SimpleContainer container = new SimpleContainer(stack);
        return this.quickCheck.getRecipeFor(container, level).map(recipe -> {
            // 没有缓存配方则设置缓存配方
            if (this.cachedRecipe == null) {
                this.cachedRecipe = PressingTubRecipeCache.fromRecipe(recipe);
                // 第一下的压榨音效
                playFallOnEffect();
            }

            // 配方不同，无法继续压榨
            if (!this.cachedRecipe.id().equals(recipe.getId())) {
                // 丢出内容物并刷新状态
                if (this.dropContents()) {
                    this.refresh();
                }
                return false;
            }

            // 液体已满，无法继续压榨
            if (this.liquidAmount >= IPressingTub.MAX_LIQUID_AMOUNT) {
                return false;
            }
            ItemStack output = recipe.assemble(container, level.registryAccess());

            // 产物为空，无法继续压榨（一般不太可能发生）
            if (output.isEmpty()) {
                return false;
            }
            this.liquidAmount++;
            this.items.extractItem(0, 1, false);
            return true;
        }).orElseGet(() -> {
            // 没有找到配方，丢出内容物并刷新状态
            if (this.dropContents()) {
                this.refresh();
            }
            return false;
        });
    }

    private void playFallOnEffect() {
        if (this.level instanceof ServerLevel serverLevel) {
            boolean isFull = this.liquidAmount >= IPressingTub.MAX_LIQUID_AMOUNT;
            SoundEvent soundEvent = isFull ? SoundEvents.HONEY_BLOCK_FALL : SoundEvents.SLIME_BLOCK_FALL;

            this.level.playSound(null,
                    worldPosition.getX() + 0.5,
                    worldPosition.getY() + 0.5,
                    worldPosition.getZ() + 0.5,
                    soundEvent,
                    SoundSource.BLOCKS,
                    0.5F + this.level.random.nextFloat(),
                    this.level.random.nextFloat() * 0.7F + 0.6F);

            serverLevel.sendParticles(ParticleTypes.FALLING_WATER,
                    worldPosition.getX() + 0.5,
                    worldPosition.getY() + 0.5,
                    worldPosition.getZ() + 0.5,
                    10, 0.25, 0.2, 0.25, 0.05);
        }
    }

    @Override
    public boolean getResult(LivingEntity target, ItemStack carriedStack) {
        if (this.cachedRecipe == null || level == null) {
            this.clearData();
            this.refresh();
            return false;
        }
        if (this.liquidAmount < IPressingTub.MAX_LIQUID_AMOUNT) {
            return false;
        }
        ResourceLocation id = this.cachedRecipe.id();
        return level.getRecipeManager().byKey(id).map(recipe -> {
            if (recipe instanceof PressingTubRecipe pressingTubRecipe) {
                if (pressingTubRecipe.getCarrier().test(carriedStack)) {
                    ItemStack output = this.cachedRecipe.result().copy();
                    ItemUtils.getItemToLivingEntity(target, output);
                    carriedStack.shrink(1);
                    this.clearData();
                    this.refresh();

                    if (level instanceof ServerLevel) {
                        level.playSound(null,
                                worldPosition.getX() + 0.5,
                                worldPosition.getY() + 0.5,
                                worldPosition.getZ() + 0.5,
                                SoundEvents.BUCKET_FILL,
                                SoundSource.BLOCKS,
                                0.5F + this.level.random.nextFloat(),
                                this.level.random.nextFloat() * 0.7F + 0.6F);
                    }

                    return true;
                }
                // 容器不匹配，什么也不做
            } else {
                // 配方类型不匹配，清除数据并刷新状态
                this.clearData();
                this.refresh();
            }
            return false;
        }).orElseGet(() -> {
            // 没有找到配方，清除数据并刷新状态
            this.clearData();
            this.refresh();
            return false;
        });
    }

    public boolean dropContents() {
        if (level == null) {
            return false;
        }
        ItemStack stack = items.extractItem(0, 64, false);
        if (!stack.isEmpty()) {
            Block.popResource(level, worldPosition, stack);
            return true;
        }
        return false;
    }

    public void clearData() {
        // 物品栏不清除，其他都清除
        this.liquidAmount = 0;
        this.cachedRecipe = null;
    }

    @Override
    public ItemStackHandler getItems() {
        return items;
    }

    @Override
    public int getLiquidAmount() {
        return liquidAmount;
    }

    @Override
    public void setLiquidAmount(int liquidAmount) {
        this.liquidAmount = Math.min(liquidAmount, IPressingTub.MAX_LIQUID_AMOUNT);
    }

    @Override
    public @Nullable PressingTubRecipeCache getCachedRecipe() {
        return this.cachedRecipe;
    }

    @Override
    public void setCachedRecipe(@Nullable PressingTubRecipeCache cachedRecipe) {
        this.cachedRecipe = cachedRecipe;
    }

    @Override
    @SuppressWarnings("all")
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (!this.remove && cap == ForgeCapabilities.ITEM_HANDLER) {
            if (this.itemCapability == null) {
                this.itemCapability = LazyOptional.of(() -> this.items);
            }
            return this.itemCapability.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void setBlockState(BlockState blockState) {
        super.setBlockState(blockState);
        if (this.itemCapability != null) {
            LazyOptional<?> oldHandler = this.itemCapability;
            this.itemCapability = null;
            oldHandler.invalidate();
        }
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        if (itemCapability != null) {
            itemCapability.invalidate();
            itemCapability = null;
        }
    }
}
