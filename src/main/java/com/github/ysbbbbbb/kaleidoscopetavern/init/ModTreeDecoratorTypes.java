package com.github.ysbbbbbb.kaleidoscopetavern.init;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.worldgen.WildGrapevineDecorator;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public interface ModTreeDecoratorTypes {
    DeferredRegister<TreeDecoratorType<?>> TREE_DECORATOR_TYPES = DeferredRegister.create(Registries.TREE_DECORATOR_TYPE, KaleidoscopeTavern.MOD_ID);

    Supplier<TreeDecoratorType<WildGrapevineDecorator>> WILD_GRAPEVINE = TREE_DECORATOR_TYPES.register("wild_grapevine",
            () -> new TreeDecoratorType<>(WildGrapevineDecorator.CODEC));
}
