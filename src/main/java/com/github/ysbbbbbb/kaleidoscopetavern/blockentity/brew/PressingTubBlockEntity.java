package com.github.ysbbbbbb.kaleidoscopetavern.blockentity.brew;

import com.github.ysbbbbbb.kaleidoscopetavern.api.blockentity.IPressingTub;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.BaseBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.crafting.recipe.PressingTubRecipe;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModRecipes;
import com.github.ysbbbbbb.kaleidoscopetavern.util.FluidUtils;
import com.github.ysbbbbbb.kaleidoscopetavern.util.ItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.fluid.FluidStacksResourceHandler;
import net.neoforged.neoforge.transfer.fluid.FluidUtil;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemUtil;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

import static com.github.ysbbbbbb.kaleidoscopetavern.config.GeneralConfig.PRESSING_TUB_DROP_CONTENTS_ON_NON_JUICEABLE;

@SuppressWarnings("deprecation")
public class PressingTubBlockEntity extends BaseBlockEntity implements IPressingTub {
    private final RecipeManager.CachedCheck<SingleRecipeInput, PressingTubRecipe> quickCheck = RecipeManager.createCheck(ModRecipes.PRESSING_TUB_RECIPE.get());

    /**
     * 压榨桶的物品槽，目前只有一个槽位，用于放置被压榨的物品，最大可放入一组件（64个）物品
     */
    private final ItemStacksResourceHandler items = new ItemStacksResourceHandler(1) {
        @Override
        protected void onContentsChanged(int slot, ItemStack previousContents) {
            // 物品槽内容改变时，需要强制刷新状态，以便客户端同步
            refresh();
        }
    };
    /**
     * 当前压榨桶内的液体量，最大为 1000 mb
     */
    private final FluidStacksResourceHandler fluid = new FluidStacksResourceHandler(1, IPressingTub.MAX_FLUID_AMOUNT) {
        @Override
        protected void onContentsChanged(int slot, FluidStack previousContents) {
            // 液体量改变时，需要强制刷新状态，以便客户端同步
            refresh();
        }
    };

    public PressingTubBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.PRESSING_TUB_BE.get(), pos, state);
    }

    @Override
    public void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        input.readChild("items", this.items);
        input.readChild("fluid", this.fluid);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.putChild("items", this.items);
        output.putChild("fluid", this.fluid);
    }

    @Override
    public boolean addIngredient(ItemStack stack) {
        int count = stack.getCount();
        ItemStack remaining = ItemUtil.insertItemReturnRemaining(this.items, 0, stack.copy(), false, null);
        if (remaining.getCount() >= count) {
            return false;
        }
        stack.shrink(count - remaining.getCount());
        if (this.level instanceof ServerLevel) {
            RandomSource random = this.level.getRandom();
            this.level.playSound(null,
                    worldPosition.getX() + 0.5,
                    worldPosition.getY() + 0.5,
                    worldPosition.getZ() + 0.5,
                    SoundEvents.ITEM_FRAME_ADD_ITEM,
                    SoundSource.BLOCKS,
                    0.5F + random.nextFloat(),
                    random.nextFloat() * 0.7F + 0.6F);
        }
        return true;
    }

    @Override
    public boolean removeIngredient(LivingEntity target, int count) {
        ItemResource resource = this.items.getResource(0);
        if (resource.isEmpty()) {
            return false;
        }
        int available = this.items.getAmountAsInt(0);
        int toExtract = Math.min(count, available);
        ItemStack removed;
        try (Transaction tx = Transaction.openRoot()) {
            toExtract = this.items.extract(0, resource, toExtract, tx);
            removed = resource.toStack(toExtract);
            tx.commit();
        }
        ItemUtils.getItemToLivingEntity(target, removed);
        if (this.level instanceof ServerLevel) {
            RandomSource random = this.level.getRandom();
            this.level.playSound(null,
                    worldPosition.getX() + 0.5,
                    worldPosition.getY() + 0.5,
                    worldPosition.getZ() + 0.5,
                    SoundEvents.ITEM_FRAME_REMOVE_ITEM,
                    SoundSource.BLOCKS,
                    0.5F + random.nextFloat(),
                    random.nextFloat() * 0.7F + 0.6F);
        }
        return true;
    }

    @Override
    public boolean press(LivingEntity target, double fallDistance) {
        if (!(level instanceof ServerLevel serverLevel)) {
            return false;
        }
        if (fallDistance < MIN_FALL_DISTANCE) {
            return false;
        }

        ItemStack stack = ItemUtil.getStack(items, 0);
        if (stack.isEmpty()) {
            // 如果有流体，播放正常压榨效果
            if (this.getFluidAmount() > 0) {
                playSuccessPressEffect(null);
            } else {
                // 没有流体，播放失败压榨效果
                playFailPressEffect(null);
            }
            return false;
        }

        SingleRecipeInput container = new SingleRecipeInput(stack);
        return this.quickCheck.getRecipeFor(container, serverLevel).map(holder -> {
            PressingTubRecipe recipe = holder.value();

            // 准备放入的流体
            FluidStack fluidStack = new FluidStack(recipe.getFluid(), recipe.getFluidAmount());
            FluidStack fluidInTub = FluidUtil.getStack(this.fluid, 0);

            // 如果已经有流体，但是结果不匹配，无法继续压榨
            if (!fluidInTub.isEmpty() && !FluidStack.isSameFluidSameComponents(fluidStack, fluidInTub)) {
                playFailPressEffect(stack);
                // 丢出内容物并刷新状态
                if (PRESSING_TUB_DROP_CONTENTS_ON_NON_JUICEABLE.get() && this.dropContents()) {
                    this.refresh();
                }
                return false;
            }

            // 液体已满，无法继续压榨
            if (this.getFluidAmount() >= IPressingTub.MAX_FLUID_AMOUNT) {
                playFinishedPressEffect();
                return false;
            }
            ItemStack output = recipe.assemble(container);

            // 产物为空，无法继续压榨（一般不太可能发生）
            if (output.isEmpty()) {
                playFailPressEffect(stack);
                return false;
            }

            // 成功压榨，增加液体量，减少物品槽内的物品数量
            try (Transaction tx = Transaction.openRoot()) {
                this.fluid.insert(0, FluidResource.of(fluidStack), fluidStack.getAmount(), tx);
                ItemResource itemResource = this.items.getResource(0);
                this.items.extract(0, itemResource, 1, tx);
                tx.commit();
            }

            playSuccessPressEffect(stack);
            return true;
        }).orElseGet(() -> {
            playFailPressEffect(stack);
            // 没有找到配方，丢出内容物并刷新状态
            if (PRESSING_TUB_DROP_CONTENTS_ON_NON_JUICEABLE.get() && this.dropContents()) {
                this.refresh();
            }
            return false;
        });
    }

    /**
     * 成功压榨时，显示对应物品破碎的粒子，粘液块的音效
     */
    private void playSuccessPressEffect(@Nullable ItemStack stack) {
        if (this.level instanceof ServerLevel serverLevel) {
            RandomSource random = this.level.getRandom();
            this.level.playSound(null,
                    worldPosition.getX() + 0.5,
                    worldPosition.getY() + 0.5,
                    worldPosition.getZ() + 0.5,
                    SoundEvents.SLIME_BLOCK_FALL,
                    SoundSource.BLOCKS,
                    0.5F + random.nextFloat(),
                    random.nextFloat() * 0.3F + 0.7F);

            if (stack == null) {
                serverLevel.sendParticles(ParticleTypes.RAIN,
                        worldPosition.getX() + 0.5,
                        worldPosition.getY() + 0.5,
                        worldPosition.getZ() + 0.5,
                        10, 0.25, 0.2, 0.25, 0.05);
            } else {
                ItemParticleOption option = new ItemParticleOption(ParticleTypes.ITEM, stack.getItem());
                serverLevel.sendParticles(option,
                        worldPosition.getX() + 0.5,
                        worldPosition.getY() + 0.5,
                        worldPosition.getZ() + 0.5,
                        10, 0.25, 0.2, 0.25, 0.05);
            }
        }
    }

    /**
     * 压榨失败时，显示对应物品破碎的粒子，木板的音效
     */
    private void playFailPressEffect(@Nullable ItemStack stack) {
        if (this.level instanceof ServerLevel serverLevel) {
            RandomSource random = this.level.getRandom();
            this.level.playSound(null,
                    worldPosition.getX() + 0.5,
                    worldPosition.getY() + 0.5,
                    worldPosition.getZ() + 0.5,
                    SoundEvents.WOOD_FALL,
                    SoundSource.BLOCKS,
                    0.5F + random.nextFloat(),
                    random.nextFloat() * 0.3F + 0.7F);

            if (stack == null) {
                BlockState state = ModBlocks.PRESSING_TUB.get().defaultBlockState();
                BlockParticleOption option = new BlockParticleOption(ParticleTypes.BLOCK, state);
                serverLevel.sendParticles(option,
                        worldPosition.getX() + 0.5,
                        worldPosition.getY() + 0.5,
                        worldPosition.getZ() + 0.5,
                        10, 0.25, 0.2, 0.25, 0.05);
            } else {
                ItemParticleOption option = new ItemParticleOption(ParticleTypes.ITEM, stack.getItem());
                serverLevel.sendParticles(option,
                        worldPosition.getX() + 0.5,
                        worldPosition.getY() + 0.5,
                        worldPosition.getZ() + 0.5,
                        10, 0.25, 0.2, 0.25, 0.05);
            }
        }
    }

    /**
     * 完成压榨后再踩踏，显示雨水粒子，蜂蜜块的音效
     */
    private void playFinishedPressEffect() {
        if (this.level instanceof ServerLevel serverLevel) {
            RandomSource random = this.level.getRandom();
            this.level.playSound(null,
                    worldPosition.getX() + 0.5,
                    worldPosition.getY() + 0.5,
                    worldPosition.getZ() + 0.5,
                    SoundEvents.HONEY_BLOCK_HIT,
                    SoundSource.BLOCKS,
                    0.5F + random.nextFloat(),
                    random.nextFloat() * 0.3F + 0.7F);

            serverLevel.sendParticles(ParticleTypes.RAIN,
                    worldPosition.getX() + 0.5,
                    worldPosition.getY() + 0.5,
                    worldPosition.getZ() + 0.5,
                    10, 0.25, 0.2, 0.25, 0.05);
        }
    }

    @Override
    public boolean getResult(LivingEntity target, ItemAccess carriedStack) {
        if (level == null) {
            return false;
        }
        // 必须完全满液体才能取出产物
        if (this.fluid.getAmountAsInt(0) < IPressingTub.MAX_FLUID_AMOUNT) {
            return false;
        }
        // 开始把果盆中的流体转移到容器里
        return FluidUtils.fillItem(target, carriedStack, this.fluid, IPressingTub.MAX_FLUID_AMOUNT);
    }

    public boolean dropContents() {
        if (level == null) {
            return false;
        }
        ItemResource resource = items.getResource(0);
        if (resource.isEmpty()) {
            return false;
        }
        int amount = items.getAmountAsInt(0);
        ItemStack stack;
        try (Transaction tx = Transaction.openRoot()) {
            int extracted = items.extract(0, resource, amount, tx);
            stack = resource.toStack(extracted);
            tx.commit();
        }
        if (stack.isEmpty()) {
            return false;
        }

        int totalCount = stack.getCount();
        int directionCount = Math.min(8, totalCount);

        // 8个水平弹射方向 (dx, dz)，从东方向顺时针排列，对角分量已归一化
        double s = 1.0 / Math.sqrt(2.0);
        double[][] dirs = {
                {1.0, 0.0},  // 东
                {s, s},  // 东南
                {0.0, 1.0},  // 南
                {-s, s},  // 西南
                {-1.0, 0.0},  // 西
                {-s, -s},  // 西北
                {0.0, -1.0},  // 北
                {s, -s},  // 东北
        };

        int base = totalCount / directionCount;
        int remainder = totalCount % directionCount;
        RandomSource random = level.getRandom();

        for (int i = 0; i < directionCount; i++) {
            int count = base + (i < remainder ? 1 : 0);
            ItemStack split = stack.copyWithCount(count);

            double dx = dirs[i][0];
            double dz = dirs[i][1];

            // 生成位置：方块中心偏向弹射方向，加少量随机扰动
            double spawnX = worldPosition.getX() + 0.5 + dx * 0.3 + Mth.nextDouble(random, -0.05, 0.05);
            double spawnY = worldPosition.getY() + 0.5 + Mth.nextDouble(random, 0, 0.1);
            double spawnZ = worldPosition.getZ() + 0.5 + dz * 0.3 + Mth.nextDouble(random, -0.05, 0.05);

            // 速度：沿弹射方向加随机扰动，带少量向上分量
            double velX = dx * 0.15 + Mth.nextDouble(random, -0.02, 0.02);
            double velY = 0.1 + Mth.nextDouble(random, -0.02, 0.02);
            double velZ = dz * 0.15 + Mth.nextDouble(random, -0.02, 0.02);

            this.popResource(level, () -> new ItemEntity(level, spawnX, spawnY, spawnZ, split, velX, velY, velZ), split);
        }

        return true;
    }

    private void popResource(Level level, Supplier<ItemEntity> supplier, ItemStack stack) {
        if (level instanceof ServerLevel serverLevel && !stack.isEmpty() && serverLevel.getGameRules().get(GameRules.BLOCK_DROPS) && !level.restoringBlockSnapshots) {
            ItemEntity entity = supplier.get();
            entity.setDefaultPickUpDelay();
            level.addFreshEntity(entity);
        }
    }

    @Override
    public ItemStacksResourceHandler getItems() {
        return items;
    }

    @Override
    public FluidStacksResourceHandler getFluid() {
        return fluid;
    }

    @Override
    public int getFluidAmount() {
        return this.fluid.getAmountAsInt(0);
    }
}
