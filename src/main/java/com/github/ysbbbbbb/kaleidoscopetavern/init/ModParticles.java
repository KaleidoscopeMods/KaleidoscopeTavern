package com.github.ysbbbbbb.kaleidoscopetavern.init;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public interface ModParticles {
    DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, KaleidoscopeTavern.MOD_ID);

    RegistryObject<SimpleParticleType> WATER_TAP_DRIP = PARTICLES.register("water_tap_drip", () -> new SimpleParticleType(true));
    RegistryObject<SimpleParticleType> LAVA_TAP_DRIP = PARTICLES.register("lava_tap_drip", () -> new SimpleParticleType(true));
}
