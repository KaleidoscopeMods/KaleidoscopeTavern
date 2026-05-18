package com.github.ysbbbbbb.kaleidoscopetavern.util;

import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.transfer.ResourceHandlerUtil;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.fluid.FluidStacksResourceHandler;
import net.neoforged.neoforge.transfer.fluid.FluidUtil;

public class FluidUtils {
    /**
     * 类似于 forge 提供的 FluidUtil 里的方法，但不限于玩家实体
     * <p>
     * 该方法尝试将流体从物品容器转移到流体容器里，成功返回 true，失败返回 false
     *
     * @param user    任意实体，通常是玩家，但也可以是其他生物
     * @param bucket  物品形态的容器，一般是桶，但也可以是其他模组的容器
     * @param handler 流体容器的能力接口，使用新的 ResourceHandler API
     * @param amount  要转移的流体量，单位为毫桶（mB）
     * @return 如果成功转移了流体，返回 true；如果没有转移任何流体，返回 false
     */
    public static boolean emptyItem(LivingEntity user, ItemAccess bucket, FluidStacksResourceHandler handler, int amount) {
        // 开始把桶中的流体转移到流体容器中
        var stackFluid = bucket.getCapability(Capabilities.Fluid.ITEM);
        if (stackFluid == null || stackFluid.getResource(0).isEmpty()) {
            return false;
        }
        var moved = ResourceHandlerUtil.moveFirst(stackFluid, handler, _ -> true, amount, null);
        if (moved != null) {
            FluidUtil.triggerSoundAndGameEvent(moved.resource(), user.level(), user.position(), null, false);
            return true;
        }
        return false;
    }

    /**
     * 类似于 forge 提供的 FluidUtil 里的方法，但不限于玩家实体
     * <p>
     * 该方法尝试将流体从流体容器转移到物品容器里，成功返回 true，失败返回 false
     *
     * @param user    任意实体，通常是玩家，但也可以是其他生物
     * @param bucket  物品形态的容器，一般是桶，但也可以是其他模组的容器
     * @param handler 流体容器的能力接口，使用新的 ResourceHandler API
     * @param amount  要转移的流体量，单位为毫桶（mB）
     * @return 如果成功转移了流体，返回 true；如果没有转移任何流体，返回 false
     */
    public static boolean fillItem(LivingEntity user, ItemAccess bucket, FluidStacksResourceHandler handler, int amount) {
        // 开始把流体容器中的流体转移到桶里
        var stackFluid = bucket.getCapability(Capabilities.Fluid.ITEM);
        if (stackFluid == null) {
            return false;
        }
        var moved = ResourceHandlerUtil.moveFirst(handler, stackFluid, _ -> true, amount, null);
        if (moved != null) {
            FluidUtil.triggerSoundAndGameEvent(moved.resource(), user.level(), user.position(), null, true);
            return true;
        }
        return false;
    }

    public static boolean isFluidContainer(ItemAccess bucket) {
        if (bucket.getAmount() <= 0) {
            return false;
        }
        var stackFluid = bucket.getCapability(Capabilities.Fluid.ITEM);
        return stackFluid != null;
    }
}
