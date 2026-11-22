package com.github.ysbbbbbb.kaleidoscopetavern.api.blockentity;

import net.minecraft.world.item.ItemStack;

/**
 * 饮品存储接口 — 能够存放饮品的方块实体实现此接口，
 * 以便酒桶龙头提取、饮品物品摆放等流程可以写入第三方饮品方块实体。
 */
public interface IDrinkStorage {
    /**
     * 向存储中添加一瓶饮品，添加成功后由实现类自行负责持久化标记与同步刷新。
     *
     * @param drink 要添加的饮品，添加成功后存储中只保留 1 个。
     * @return 添加失败情况：
     * - 存储已满
     */
    boolean addDrink(ItemStack drink);
}
