package com.github.ysbbbbbb.kaleidoscopetavern.api.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface IBarrel {
    /**
     * 酒桶的物品槽数量，默认为 4 组。
     */
    int MAX_ITEM_SLOTS = 4;

    /**
     * 酒桶的液体槽容量，单位为毫桶（mB），默认为 4000 mB，相当于 4 个标准桶的容量。
     */
    int MAX_FLUID_AMOUNT = 4 * 1000;

    /**
     * 发酵等级，没有开始酿造时为 0
     */
    int BREWING_NOT_STARTED = 0;

    /**
     * 发酵等级，每过单位时间增加 1。大于等于 1 时表示正在酿造中，此时无法打开盖子
     */
    int BREWING_STARTED = 1;

    /**
     * 发酵等级，达到 7 时为最高品质
     */
    int BREWING_FINISHED = 7;

    /**
     * 打开酒桶盖子
     *
     * @param user 打开盖子的实体，通常是玩家，但也可能是其他生物。
     *             主要用于发送提示信息，可以为 null。
     * @return 打开失败情况：
     * - 酒桶正在酿造中（发酵等级大于等于 1）
     */
    boolean openLid(@Nullable LivingEntity user);

    /**
     * 关闭酒桶盖子
     *
     * @param user 关闭盖子的实体，通常是玩家，但也可能是其他生物。
     *             主要用于发送提示信息，可以为 null。
     * @return 永远返回 true，因为关闭盖子没有任何限制条件
     */
    boolean closeLid(@Nullable LivingEntity user);

    /**
     * 添加酿造原料
     *
     * @param user  进行添加操作的实体，通常是玩家，但也可能是其他生物。
     *              主要用于发送提示信息，可以为 null。
     * @param stack 用于添加的物品堆，必须满足酿造配方的要求。
     * @return 添加失败情况：
     * - 酒桶盖子已关闭
     * - 酒桶正在酿造中（发酵等级大于等于 1）
     * - 流体没有装满
     * - 物品槽已满
     */
    boolean addIngredient(@Nullable LivingEntity user, ItemStack stack);

    /**
     * 移除酿造原料
     *
     * @param user 进行移除操作的实体，通常是玩家，但也可能是其他生物。
     *             因为涉及物品返还，所以必须有实体进行交互，不能为 null。
     * @return 移除失败情况：
     * - 酒桶盖子已关闭
     * - 酒桶正在酿造中（发酵等级大于等于 1）
     * - 物品槽已空
     */
    boolean removeIngredient(@NotNull LivingEntity user);

    /**
     * 添加流体
     *
     * @param user  进行添加操作的实体，通常是玩家，但也可能是其他生物。
     *              因为涉及物品返还，所以必须有实体进行交互，不能为 null。
     * @param stack 添加流体的物品
     * @return 添加失败情况：
     * - 酒桶盖子已关闭
     * - 酒桶正在酿造中（发酵等级大于等于 1）
     * - 物品槽不为空
     * - 流体槽已满
     */
    boolean addFluid(@NotNull LivingEntity user, ItemStack stack);

    /**
     * 移除流体
     *
     * @param user  进行移除操作的实体，通常是玩家，但也可能是其他生物。
     *              因为涉及物品返还，所以必须有实体进行交互，不能为 null。
     * @param stack 移除流体的物品
     * @return 移除失败情况：
     * - 酒桶盖子已关闭
     * - 酒桶正在酿造中（发酵等级大于等于 1）
     * - 物品槽不为空
     * - 流体槽已空
     */
    boolean removeFluid(@NotNull LivingEntity user, ItemStack stack);

    /**
     * 检查酒桶是否正在酿造中
     *
     * @return 当前发酵等级大于等于 1 时返回 true，表示正在酿造中；否则返回 false。
     */
    boolean isBrewing();

    /**
     * 检查酒桶是否达到最高酿造品质
     *
     * @return 当前发酵等级大于等于 {@value BREWING_FINISHED} 时返回 true，表示达到最高品质；否则返回 false。
     */
    boolean isMaxBrewLevel();

    /**
     * 检查是否可以从酒桶的水龙头提取成品
     *
     * @param tapPos 龙头的位置。
     * @param user   提取成品的实体，通常是玩家，但也可能是其他生物。
     *               主要用于发送提示信息，可以为 null。
     * @return 失败情况：
     * - 酒桶没有处于酿造状态（发酵等级小于 {@value BREWING_STARTED}）
     * - 酒桶已经空了
     * - 龙头下方容器不符合
     */
    boolean canTapExtract(Level level, BlockPos tapPos, LivingEntity user);

    /**
     * 实际执行从酒桶的水龙头提取成品的操作
     */
    void doTapExtract(Level level, BlockPos tapPos);

    /**
     * 获取酒桶的物品栏
     *
     * @return 酒桶的物品栏，包含 {@value MAX_ITEM_SLOTS} 个槽位。
     */
    ItemStackHandler getIngredient();

    /**
     * 获取酒桶的液体槽
     *
     * @return 酒桶的液体槽，容量为 {@value MAX_FLUID_AMOUNT} 毫桶。
     */
    FluidTank getFluid();

    /**
     * 设置酒桶的开盖状态
     *
     * @param open true 表示打开盖子，false 表示关闭盖子
     */
    void setOpen(boolean open);

    /**
     * 获取酒桶的开盖状态
     *
     * @return true 表示盖子打开，false 表示盖子关闭
     */
    boolean isOpen();

    /**
     * 获取酒桶当前的发酵等级
     *
     * @return 发酵等级：<br>
     * {@value BREWING_NOT_STARTED} 表示未开始酿造 <br>
     * {@value BREWING_STARTED} 表示正在酿造中<br>
     * {@value BREWING_FINISHED} 表示酿造完成达到最高品质，其他值表示不同的发酵等级
     */
    int getBrewLevel();

    /**
     * 获取当前阶段的剩余时间，单位为 tick，每过一个 tick 减少 1，当达到 0 时进入下一个阶段。
     *
     * @return 当前阶段的剩余时间，单位为 tick
     */
    int getBrewTime();

    /**
     * 获取酒桶的输出槽
     *
     * @return 酒桶的输出槽，包含 1 个槽位，用于存放酿造完成后的成品。
     */
    ItemStackHandler getOutput();

    /**
     * 获取当前酒桶缓存的配方 ID，主要用于获取酿造时间和容器
     *
     * @return 当没有任何配方时，返回 null
     */
    @Nullable ResourceLocation getRecipeId();
}
