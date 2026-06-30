package com.github.ysbbbbbb.kaleidoscopetavern.init;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.effect.*;
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
    RegistryObject<MobEffect> ARDENT_HEAT = EFFECTS.register("ardent_heat", () -> new ArdentHeatEffect(0xFF6B35));
    RegistryObject<MobEffect> LONG_REACH = EFFECTS.register("long_reach", () -> new LongReachEffect(0x8B6914));
    RegistryObject<MobEffect> TOMB_RAIDER = EFFECTS.register("tomb_raider", () -> new BaseEffect(0xDAA520));
    RegistryObject<MobEffect> XP_DRAIN = EFFECTS.register("xp_drain", () -> new XpDrainEffect(0x7CFC00));
    RegistryObject<MobEffect> UPSIDE_DOWN = EFFECTS.register("upside_down", () -> new UpsideDownEffect(0x9B59B6));
    RegistryObject<MobEffect> ZENITH = EFFECTS.register("zenith", () -> new ZenithEffect(0x87CEEB));
    RegistryObject<MobEffect> SHRIEK_ATTACK = EFFECTS.register("shriek_attack", () -> new ShriekAttackEffect(0x0D4C4A));
}
