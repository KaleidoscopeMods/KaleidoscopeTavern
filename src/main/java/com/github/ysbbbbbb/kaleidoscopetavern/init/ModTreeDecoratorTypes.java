package com.github.ysbbbbbb.kaleidoscopetavern.init;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.worldgen.WildGrapevineDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public interface ModTreeDecoratorTypes {
    DeferredRegister<TreeDecoratorType<?>> TREE_DECORATOR_TYPES = DeferredRegister.create(ForgeRegistries.TREE_DECORATOR_TYPES, KaleidoscopeTavern.MOD_ID);

    RegistryObject<TreeDecoratorType<WildGrapevineDecorator>> WILD_GRAPEVINE = TREE_DECORATOR_TYPES.register("wild_grapevine",
            () -> new TreeDecoratorType<>(WildGrapevineDecorator.CODEC));
}
