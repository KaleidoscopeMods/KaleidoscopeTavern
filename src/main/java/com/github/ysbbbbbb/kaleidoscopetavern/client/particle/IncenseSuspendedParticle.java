package com.github.ysbbbbbb.kaleidoscopetavern.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;

/**
 * 香薰大型悬浮粒子：行为类似原版 SporeBlossomAirProvider，但不做代码着色。
 */
public class IncenseSuspendedParticle extends TextureSheetParticle {
    protected IncenseSuspendedParticle(ClientLevel level, SpriteSet sprites,
                                       double x, double y, double z,
                                       double xSpeed, double ySpeed, double zSpeed) {
        super(level, x, y - 0.125, z, xSpeed, ySpeed, zSpeed);
        this.setSize(0.01F, 0.01F);
        this.pickSprite(sprites);
        this.quadSize *= this.random.nextFloat() * 0.6F + 0.6F;
        this.lifetime = Mth.randomBetweenInclusive(level.random, 500, 1000);
        this.hasPhysics = false;
        this.friction = 1.0F;
        this.gravity = 0.01F;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level,
                                       double x, double y, double z,
                                       double xSpeed, double ySpeed, double zSpeed) {
            return new IncenseSuspendedParticle(level, this.sprites, x, y, z, 0, -0.8, 0);
        }
    }
}
