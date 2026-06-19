package com.github.ysbbbbbb.kaleidoscopetavern.api.blockentity;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public interface IShaker {
    /**
     * 向雪克杯里添加原料
     *
     * @param stack 准备加入的原料（不可取回）
     * @param user  添加原料的实体（用来返还物品，可以不存在）
     * @return 如果已经满了，加入失败
     */
    boolean addIngredient(ItemStack stack, @Nullable LivingEntity user);
}
