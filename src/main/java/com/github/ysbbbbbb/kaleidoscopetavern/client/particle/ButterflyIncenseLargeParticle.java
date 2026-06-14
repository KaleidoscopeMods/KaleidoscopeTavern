package com.github.ysbbbbbb.kaleidoscopetavern.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;

/**
 * 蝴蝶香薰大型粒子：动态帧播放，不做代码着色。
 */
public class ButterflyIncenseLargeParticle extends TextureSheetParticle {
    private final SpriteSet sprites;

    protected ButterflyIncenseLargeParticle(ClientLevel level, SpriteSet sprites,
                                            double x, double y, double z,
                                            double xSpeed, double ySpeed, double zSpeed) {
        super(level, x, y - 0.125, z, xSpeed, ySpeed, zSpeed);
        this.sprites = sprites;
        this.setSize(0.01F, 0.01F);
        this.quadSize *= this.random.nextFloat() * 0.6F + 0.6F;
        this.lifetime = Mth.randomBetweenInclusive(level.random, 500, 1000);
        this.hasPhysics = false;
        this.friction = 1.0F;
        this.gravity = 0.01F;
        this.setSpriteFromAge(sprites);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.removed) {
            // 使其在 0 - 2 之间来回变化
            int age = (this.age / 5) % 3;
            this.setSprite(sprites.get(age, 2));
        }
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
            return new ButterflyIncenseLargeParticle(level, this.sprites, x, y, z, 0, -0.8, 0);
        }
    }
}
