package com.github.ysbbbbbb.kaleidoscopetavern.init;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.effect.BaseEffect;
import com.github.ysbbbbbb.kaleidoscopetavern.effect.GrassStealthEffect;
import com.github.ysbbbbbb.kaleidoscopetavern.effect.HighHeelsEffect;
import com.github.ysbbbbbb.kaleidoscopetavern.effect.VisionEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public interface ModEffects {
    DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, KaleidoscopeTavern.MOD_ID);

    RegistryObject<MobEffect> SLIGHTLY_TIPSY = EFFECTS.register("slightly_tipsy", () -> new BaseEffect(MobEffectCategory.NEUTRAL, 0xFFD94A));
    RegistryObject<MobEffect> HIGH_HEELS = EFFECTS.register("high_heels", () -> new HighHeelsEffect(0xE85BAA));
    RegistryObject<MobEffect> GRASS_STEALTH = EFFECTS.register("grass_stealth", () -> new GrassStealthEffect(0x71BDE7));
    RegistryObject<MobEffect> VISION = EFFECTS.register("vision", () -> new VisionEffect(0x408997));
    RegistryObject<MobEffect> BLOODY_MARY = EFFECTS.register("bloody_mary", () -> new BaseEffect(0xF73A36));
}
