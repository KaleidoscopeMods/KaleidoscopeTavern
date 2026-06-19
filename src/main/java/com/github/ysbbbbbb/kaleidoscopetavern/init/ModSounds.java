package com.github.ysbbbbbb.kaleidoscopetavern.init;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, KaleidoscopeTavern.MOD_ID);

    public static final Supplier<SoundEvent> EFFECT_VISION = registerSound("effect.vision");
    public static final Supplier<SoundEvent> HOLDER_POP = registerSound("block.holder.pop");
    public static final Supplier<SoundEvent> SHAKER_SHAKING = registerSound("item.shaker.shaking");
    public static final Supplier<SoundEvent> SHAKER_END = registerSound("item.shaker.end");

    private static Supplier<SoundEvent> registerSound(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createFixedRangeEvent(ResourceLocation.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, name), 16.0F));
    }
}