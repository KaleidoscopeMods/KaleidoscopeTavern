package com.github.ysbbbbbb.kaleidoscopetavern.api.blockentity;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.ItemStackHandler;

/**
 * 果盆方块实体的接口，定义果盆的核心交互逻辑。
 * <p>
 * 果盆允许玩家放入食材并通过跳踩进行压榨，液体量达到 {@value #MAX_LIQUID_AMOUNT} 后可用容器取出产物。
 */
public interface IPressingTub {
    /**
     * 果盆内液体的最大容量，单位为毫桶（mB），默认为 1000 mB，相当于一个标准桶的容量。
     */
    int MAX_LIQUID_AMOUNT = FluidType.BUCKET_VOLUME;
    float MIN_FALL_DISTANCE = 0.5F;

    /**
     * 获取果盆的物品栏。
     * <p>
     * 目前只有一个槽位，用于放置待压榨的食材，最多可容纳一组（64 个）相同物品。
     */
    ItemStackHandler getItems();

    /**
     * 获取果盆的液体槽。
     * <p>
     * 果盆内只能容纳一种液体，最大容量为 {@value #MAX_LIQUID_AMOUNT} 毫桶。
     */
    FluidTank getFluid();

    /**
     * 获取果盆当前积累的液体量。
     * <p>
     * 液体量达到 {@value #MAX_LIQUID_AMOUNT} 时，方可取出压榨产物。
     */
    int getLiquidAmount();

    /**
     * 向果盆内添加待压榨的食材，成功返回 {@code true}，失败返回 {@code false}。
     * <p>
     * 以下情况会导致添加失败：
     * <ul>
     *   <li>物品槽内已有物品，且与新添加的物品种类不同</li>
     *   <li>物品槽内已有物品，且数量已达上限（64 个）</li>
     * </ul>
     */
    boolean addIngredient(ItemStack stack);

    /**
     * 从果盆内取回未压榨完毕的食材，成功返回 {@code true}，失败返回 {@code false}。
     * <p>
     * 以下情况会导致取回失败：
     * <ul>
     *   <li>物品槽内没有物品</li>
     * </ul>
     *
     * @param target 执行取回操作的实体，通常为玩家，用于确定物品的归还目标
     * @param count  要取回的物品数量，若超过当前物品槽内的数量，则取回全部剩余物品
     */
    boolean removeIngredient(LivingEntity target, int count);

    /**
     * 当实体在果盆上落地或跳踩时触发压榨操作，成功返回 {@code true}，失败返回 {@code false}。
     * <p>
     * 以下情况会导致压榨失败：
     * <ul>
     *   <li>下落高度不足以触发压榨（小于 {@value #MIN_FALL_DISTANCE}）</li>
     *   <li>物品槽内没有食材</li>
     *   <li>液体量已满（大于等于 {@value #MAX_LIQUID_AMOUNT}）</li>
     *   <li>物品槽内的食材不匹配任何压榨配方</li>
     *   <li>本次压榨产物与已有产物不同</li>
     * </ul>
     *
     * @param target       执行压榨的实体，通常为玩家
     * @param fallDistance 实体的下落高度，暂无实际用途，预留供后续扩展
     * @return 是否成功完成本次压榨操作
     */
    boolean press(LivingEntity target, float fallDistance);

    /**
     * 当液体量积满时，使用容器取出压榨产物，成功返回 {@code true}，失败返回 {@code false}。
     * <p>
     * 以下情况会导致取出失败：
     * <ul>
     *   <li>当前液体量不足（小于 {@value #MAX_LIQUID_AMOUNT}）</li>
     *   <li>手持容器不符合配方要求</li>
     * </ul>
     *
     * @param target       执行取出操作的实体，通常为玩家
     * @param carriedStack 实体当前手持的物品，用于校验是否为合法的取出容器
     */
    boolean getResult(LivingEntity target, ItemStack carriedStack);
}