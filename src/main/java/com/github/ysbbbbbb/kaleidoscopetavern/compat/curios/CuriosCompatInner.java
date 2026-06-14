package com.github.ysbbbbbb.kaleidoscopetavern.compat.curios;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.item.StringLightsBlockItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

import java.util.Optional;
import java.util.stream.IntStream;

class CuriosCompatInner {
    static void registerStringLightsPredicate() {
        CuriosApi.registerCurioPredicate(
                KaleidoscopeTavern.modLoc("string_lights"),
                slotResult -> slotResult.stack().getItem() instanceof StringLightsBlockItem
        );
    }

    static Optional<ItemStack> findStringLights(LivingEntity entity) {
        LazyOptional<ICuriosItemHandler> inventory = CuriosApi.getCuriosInventory(entity);
        return inventory.resolve().flatMap(handler -> handler.getCurios().values().stream()
                .flatMap(stacksHandler -> findInStacksHandler(stacksHandler).stream())
                .findFirst()
                .map(ItemStack::copy));
    }

    private static Optional<ItemStack> findInStacksHandler(ICurioStacksHandler stacksHandler) {
        IDynamicStackHandler stacks = stacksHandler.getStacks();
        IDynamicStackHandler cosmeticStacks = stacksHandler.getCosmeticStacks();
        return IntStream.range(0, stacks.getSlots())
                .mapToObj(i -> getRenderableStack(stacksHandler, stacks, cosmeticStacks, i))
                .filter(stack -> stack.getItem() instanceof StringLightsBlockItem)
                .findFirst();
    }

    private static ItemStack getRenderableStack(ICurioStacksHandler stacksHandler, IDynamicStackHandler stacks,
                                                IDynamicStackHandler cosmeticStacks, int index) {
        ItemStack stack = cosmeticStacks.getStackInSlot(index);
        if (stack.isEmpty() && isRenderable(stacksHandler, index)) {
            stack = stacks.getStackInSlot(index);
        }
        return stack;
    }

    private static boolean isRenderable(ICurioStacksHandler stacksHandler, int index) {
        return stacksHandler.getRenders().size() > index && stacksHandler.getRenders().get(index);
    }
}
