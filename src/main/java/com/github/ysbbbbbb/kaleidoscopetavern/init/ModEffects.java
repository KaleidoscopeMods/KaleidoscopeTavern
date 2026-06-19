package com.github.ysbbbbbb.kaleidoscopetavern.init;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.effect.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public interface ModEffects {
    DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, KaleidoscopeTavern.MOD_ID);

    DeferredHolder<MobEffect, MobEffect> SLIGHTLY_TIPSY = EFFECTS.register("slightly_tipsy", () -> new BaseEffect(MobEffectCategory.NEUTRAL, 0xFFD94A));
    DeferredHolder<MobEffect, MobEffect> HIGH_HEELS = EFFECTS.register("high_heels", () -> new HighHeelsEffect(0xE85BAA));
    DeferredHolder<MobEffect, MobEffect> GRASS_STEALTH = EFFECTS.register("grass_stealth", () -> new GrassStealthEffect(0x71BDE7));
    DeferredHolder<MobEffect, MobEffect> VISION = EFFECTS.register("vision", () -> new VisionEffect(0x408997));
    DeferredHolder<MobEffect, MobEffect> BLOODY_MARY = EFFECTS.register("bloody_mary", () -> new BaseEffect(0xF73A36));
    DeferredHolder<MobEffect, MobEffect> ARDENT_HEAT = EFFECTS.register("ardent_heat", () -> new ArdentHeatEffect(0xFF6B35));
    DeferredHolder<MobEffect, MobEffect> LONG_REACH = EFFECTS.register("long_reach", () -> new LongReachEffect(0x8B6914));
    DeferredHolder<MobEffect, MobEffect> TOMB_RAIDER = EFFECTS.register("tomb_raider", () -> new TombRaiderEffect(0xDAA520));
    DeferredHolder<MobEffect, MobEffect> XP_DRAIN = EFFECTS.register("xp_drain", () -> new XpDrainEffect(0x7CFC00));
    DeferredHolder<MobEffect, MobEffect> UPSIDE_DOWN = EFFECTS.register("upside_down", () -> new UpsideDownEffect(0x9B59B6));
    DeferredHolder<MobEffect, MobEffect> ZENITH = EFFECTS.register("zenith", () -> new ZenithEffect(0x87CEEB));
    DeferredHolder<MobEffect, MobEffect> SHRIEK_ATTACK = EFFECTS.register("shriek_attack", () -> new ShriekAttackEffect(0x0D4C4A));
}