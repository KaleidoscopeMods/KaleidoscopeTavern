package com.github.ysbbbbbb.kaleidoscopetavern.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;

public class FireflyIncenseLargeParticle extends TextureSheetParticle {
    private final float baseAlpha;
    private final float flickerSpeed;

    protected FireflyIncenseLargeParticle(ClientLevel level, double x, double y, double z,
                                          double xSpeed, double ySpeed, double zSpeed) {
        super(level, x, y, z);
        this.xd = xSpeed;
        this.yd = ySpeed;
        this.zd = zSpeed;
        this.lifetime = 60 + level.random.nextInt(40);
        this.gravity = -0.001F;
        this.quadSize = 0.08F + level.random.nextFloat() * 0.04F;
        this.baseAlpha = 0.9F;
        this.alpha = this.baseAlpha;
        this.flickerSpeed = 0.3F + level.random.nextFloat() * 0.4F;
        this.hasPhysics = false;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;

        if (this.age++ >= this.lifetime) {
            this.remove();
            return;
        }

        this.xd += (this.random.nextDouble() - 0.5) * 0.002;
        this.zd += (this.random.nextDouble() - 0.5) * 0.002;
        this.yd += (this.random.nextDouble() - 0.5) * 0.0005;

        float flicker = (float) (Math.sin(this.age * this.flickerSpeed) * 0.4 + 0.6);
        float progress = (float) this.age / this.lifetime;
        float fadeAlpha = progress > 0.8F ? this.baseAlpha * (1.0F - (progress - 0.8F) * 5.0F) : this.baseAlpha;
        this.alpha = fadeAlpha * flicker;

        this.move(this.xd, this.yd, this.zd);
        this.xd *= 0.96;
        this.zd *= 0.96;
    }

    @Override
    public int getLightColor(float partialTick) {
        return 0xF000F0;
    }

    public static TextureSheetParticle create(SimpleParticleType type, ClientLevel level,
                                              double x, double y, double z,
                                              double xSpeed, double ySpeed, double zSpeed) {
        return new FireflyIncenseLargeParticle(level, x, y, z, xSpeed, ySpeed, zSpeed);
    }
}
