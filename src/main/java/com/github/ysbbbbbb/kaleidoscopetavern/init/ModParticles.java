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

    // 小型香薰粒子
    RegistryObject<SimpleParticleType> SAKURA_INCENSE_PARTICLE = PARTICLES.register("sakura_incense", () -> new SimpleParticleType(false));
    RegistryObject<SimpleParticleType> PINE_INCENSE_PARTICLE = PARTICLES.register("pine_incense", () -> new SimpleParticleType(false));
    RegistryObject<SimpleParticleType> GINKGO_INCENSE_PARTICLE = PARTICLES.register("ginkgo_incense", () -> new SimpleParticleType(false));
    RegistryObject<SimpleParticleType> SPORE_INCENSE_PARTICLE = PARTICLES.register("spore_incense", () -> new SimpleParticleType(false));
    RegistryObject<SimpleParticleType> CATNIP_INCENSE_PARTICLE = PARTICLES.register("catnip_incense", () -> new SimpleParticleType(false));
    RegistryObject<SimpleParticleType> SNOW_INCENSE_PARTICLE = PARTICLES.register("snow_incense", () -> new SimpleParticleType(false));
    RegistryObject<SimpleParticleType> BUTTERFLY_INCENSE_PARTICLE = PARTICLES.register("butterfly_incense", () -> new SimpleParticleType(false));
    RegistryObject<SimpleParticleType> FIREFLY_INCENSE_PARTICLE = PARTICLES.register("firefly_incense", () -> new SimpleParticleType(false));

    // 大型香薰粒子
    RegistryObject<SimpleParticleType> PINE_INCENSE_LARGE_PARTICLE = PARTICLES.register("pine_incense_large", () -> new SimpleParticleType(false));
    RegistryObject<SimpleParticleType> GINKGO_INCENSE_LARGE_PARTICLE = PARTICLES.register("ginkgo_incense_large", () -> new SimpleParticleType(false));
    RegistryObject<SimpleParticleType> CATNIP_INCENSE_LARGE_PARTICLE = PARTICLES.register("catnip_incense_large", () -> new SimpleParticleType(false));
    RegistryObject<SimpleParticleType> SNOW_INCENSE_LARGE_PARTICLE = PARTICLES.register("snow_incense_large", () -> new SimpleParticleType(false));
    RegistryObject<SimpleParticleType> BUTTERFLY_INCENSE_LARGE_PARTICLE = PARTICLES.register("butterfly_incense_large", () -> new SimpleParticleType(false));
    RegistryObject<SimpleParticleType> FIREFLY_INCENSE_LARGE_PARTICLE = PARTICLES.register("firefly_incense_large", () -> new SimpleParticleType(false));
}
