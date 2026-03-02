package com.github.ysbbbbbb.kaleidoscopetavern.init;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.entity.SitEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.entity.ThrownMolotovEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public interface ModEntities {
    DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, KaleidoscopeTavern.MOD_ID);

    Supplier<EntityType<SitEntity>> SIT = ENTITY_TYPES.register("sit", () -> SitEntity.TYPE);
    Supplier<EntityType<ThrownMolotovEntity>> THROWN_MOLOTOV = ENTITY_TYPES.register("thrown_molotov", () -> ThrownMolotovEntity.TYPE);
}
