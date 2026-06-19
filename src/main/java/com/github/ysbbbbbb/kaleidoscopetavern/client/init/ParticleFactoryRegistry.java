package com.github.ysbbbbbb.kaleidoscopetavern.client.init;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.client.particle.ButterflyIncenseLargeParticle;
import com.github.ysbbbbbb.kaleidoscopetavern.client.particle.FireflyIncenseLargeParticle;
import com.github.ysbbbbbb.kaleidoscopetavern.client.particle.IncenseParticle;
import com.github.ysbbbbbb.kaleidoscopetavern.client.particle.IncenseSuspendedParticle;
import com.github.ysbbbbbb.kaleidoscopetavern.client.particle.TapDripParticle;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModParticles;
import net.minecraft.client.particle.CherryParticle;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

@EventBusSubscriber(modid = KaleidoscopeTavern.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ParticleFactoryRegistry {
    @SubscribeEvent
    public static void onRegisterParticleFactory(RegisterParticleProvidersEvent event) {
        // 龙头粒子
        event.registerSprite(ModParticles.WATER_TAP_DRIP.get(), TapDripParticle::createWaterTapDripParticle);
        event.registerSprite(ModParticles.LAVA_TAP_DRIP.get(), TapDripParticle::createLavaTapDripParticle);

        // 小型香薰粒子
        event.registerSprite(ModParticles.SAKURA_INCENSE_PARTICLE.get(), IncenseParticle::create);
        event.registerSprite(ModParticles.PINE_INCENSE_PARTICLE.get(), IncenseParticle::create);
        event.registerSprite(ModParticles.GINKGO_INCENSE_PARTICLE.get(), IncenseParticle::create);
        event.registerSprite(ModParticles.SPORE_INCENSE_PARTICLE.get(), IncenseParticle::create);
        event.registerSprite(ModParticles.CATNIP_INCENSE_PARTICLE.get(), IncenseParticle::create);
        event.registerSprite(ModParticles.SNOW_INCENSE_PARTICLE.get(), IncenseParticle::create);
        event.registerSprite(ModParticles.BUTTERFLY_INCENSE_PARTICLE.get(), IncenseParticle::create);
        event.registerSprite(ModParticles.FIREFLY_INCENSE_PARTICLE.get(), IncenseParticle::create);

        // 大型香薰粒子
        event.registerSpriteSet(ModParticles.PINE_INCENSE_LARGE_PARTICLE.get(), spriteSet ->
                (type, level, x, y, z, xSpeed, ySpeed, zSpeed)
                        -> new CherryParticle(level, x, y, z, spriteSet)
        );
        event.registerSpriteSet(ModParticles.GINKGO_INCENSE_LARGE_PARTICLE.get(), spriteSet ->
                (type, level, x, y, z, xSpeed, ySpeed, zSpeed) -> {
                    CherryParticle particle = new CherryParticle(level, x, y, z, spriteSet);
                    particle.scale(1.5f);
                    return particle;
                }
        );
        event.registerSpriteSet(ModParticles.CATNIP_INCENSE_LARGE_PARTICLE.get(), IncenseSuspendedParticle.Provider::new);
        event.registerSpriteSet(ModParticles.SNOW_INCENSE_LARGE_PARTICLE.get(), spriteSet ->
                (type, level, x, y, z, xSpeed, ySpeed, zSpeed)
                        -> new CherryParticle(level, x, y, z, spriteSet)
        );
        event.registerSpriteSet(ModParticles.BUTTERFLY_INCENSE_LARGE_PARTICLE.get(), ButterflyIncenseLargeParticle.Provider::new);
        event.registerSprite(ModParticles.FIREFLY_INCENSE_LARGE_PARTICLE.get(), FireflyIncenseLargeParticle::create);
    }
}
