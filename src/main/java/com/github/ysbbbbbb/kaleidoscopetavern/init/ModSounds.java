package com.github.ysbbbbbb.kaleidoscopetavern.init;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, KaleidoscopeTavern.MOD_ID);

    public static final RegistryObject<SoundEvent> EFFECT_VISION = registerSound("effect.vision");
    public static final RegistryObject<SoundEvent> HOLDER_POP = registerSound("block.holder.pop");
    public static final RegistryObject<SoundEvent> SHAKER_SHAKING = registerSound("item.shaker.shaking");
    public static final RegistryObject<SoundEvent> SHAKER_END = registerSound("item.shaker.end");

    private static RegistryObject<SoundEvent> registerSound(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createFixedRangeEvent(new ResourceLocation(KaleidoscopeTavern.MOD_ID, name), 16.0F));
    }
}
