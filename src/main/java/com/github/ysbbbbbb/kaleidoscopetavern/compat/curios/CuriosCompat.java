package com.github.ysbbbbbb.kaleidoscopetavern.compat.curios;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;

import java.util.Optional;

public class CuriosCompat {
    private static final String ID = "curios";

    public static void commonSetup() {
        if (isLoaded()) {
            CuriosCompatInner.registerStringLightsPredicate();
        }
    }

    public static Optional<ItemStack> findStringLights(LivingEntity entity) {
        if (isLoaded()) {
            return CuriosCompatInner.findStringLights(entity);
        }
        return Optional.empty();
    }

    private static boolean isLoaded() {
        return ModList.get().isLoaded(ID);
    }
}
