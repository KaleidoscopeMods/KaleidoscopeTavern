package com.github.ysbbbbbb.kaleidoscopetavern.datagen.misc;


import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModParticles;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ParticleDescriptionProvider;

public class ParticleDescriptionGenerator extends ParticleDescriptionProvider {
    public ParticleDescriptionGenerator(PackOutput output, ExistingFileHelper fileHelper) {
        super(output, fileHelper);
    }

    @Override
    protected void addDescriptions() {
        this.spriteSet(ModParticles.WATER_TAP_DRIP.get(), new ResourceLocation("drip_hang"));
        this.spriteSet(ModParticles.LAVA_TAP_DRIP.get(), new ResourceLocation("drip_hang"));

        // 小型香薰粒子
        this.spriteSet(ModParticles.SAKURA_INCENSE_PARTICLE.get(), KaleidoscopeTavern.modLoc("sakura"));
        this.spriteSet(ModParticles.PINE_INCENSE_PARTICLE.get(), KaleidoscopeTavern.modLoc("pine"));
        this.spriteSet(ModParticles.GINKGO_INCENSE_PARTICLE.get(), KaleidoscopeTavern.modLoc("ginkgo"));
        this.spriteSet(ModParticles.SPORE_INCENSE_PARTICLE.get(), KaleidoscopeTavern.modLoc("spore"));
        this.spriteSet(ModParticles.CATNIP_INCENSE_PARTICLE.get(), KaleidoscopeTavern.modLoc("catnip"));
        this.spriteSet(ModParticles.SNOW_INCENSE_PARTICLE.get(), KaleidoscopeTavern.modLoc("snow_incense"));
        this.spriteSet(ModParticles.BUTTERFLY_INCENSE_PARTICLE.get(), KaleidoscopeTavern.modLoc("butterfly"));
        this.spriteSet(ModParticles.FIREFLY_INCENSE_PARTICLE.get(), KaleidoscopeTavern.modLoc("firefly"));

        this.spriteSet(ModParticles.PINE_INCENSE_LARGE_PARTICLE.get(),
                KaleidoscopeTavern.modLoc("pine_large/1"),
                KaleidoscopeTavern.modLoc("pine_large/2"),
                KaleidoscopeTavern.modLoc("pine_large/3")
        );

        this.spriteSet(ModParticles.GINKGO_INCENSE_LARGE_PARTICLE.get(), KaleidoscopeTavern.modLoc("ginkgo_large"));
        this.spriteSet(ModParticles.CATNIP_INCENSE_LARGE_PARTICLE.get(), KaleidoscopeTavern.modLoc("catnip_large"));

        this.spriteSet(ModParticles.SNOW_INCENSE_LARGE_PARTICLE.get(),
                KaleidoscopeTavern.modLoc("snow_incense_large/1"),
                KaleidoscopeTavern.modLoc("snow_incense_large/2")
        );

        this.spriteSet(ModParticles.BUTTERFLY_INCENSE_LARGE_PARTICLE.get(),
                KaleidoscopeTavern.modLoc("butterfly_large/1"),
                KaleidoscopeTavern.modLoc("butterfly_large/2"),
                KaleidoscopeTavern.modLoc("butterfly_large/3")
        );

        this.spriteSet(ModParticles.FIREFLY_INCENSE_LARGE_PARTICLE.get(), KaleidoscopeTavern.modLoc("firefly_large"));
    }
}
