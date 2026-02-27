package com.github.ysbbbbbb.kaleidoscopetavern.init;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.entity.SitEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.entity.ThrownMolotovEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public interface ModEntities {
    DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, KaleidoscopeTavern.MOD_ID);

    RegistryObject<EntityType<SitEntity>> SIT = ENTITY_TYPES.register("sit", () -> SitEntity.TYPE);
    RegistryObject<EntityType<ThrownMolotovEntity>> THROWN_MOLOTOV = ENTITY_TYPES.register("thrown_molotov", () -> ThrownMolotovEntity.TYPE);
}
