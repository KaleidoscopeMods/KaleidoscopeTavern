package com.github.ysbbbbbb.kaleidoscopetavern.datagen.misc;


import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModSounds;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinition;
import net.minecraftforge.common.data.SoundDefinitionsProvider;

public class SoundDefinitionsGenerator extends SoundDefinitionsProvider {
    public SoundDefinitionsGenerator(PackOutput output, ExistingFileHelper helper) {
        super(output, KaleidoscopeTavern.MOD_ID, helper);
    }

    @Override
    public void registerSounds() {
        SoundDefinition paddySound = definition().subtitle(subtitle("effect.vision"))
                .with(sound("effect/vision"));
        this.add(ModSounds.EFFECT_VISION.get(), paddySound);
    }

    protected static SoundDefinition.Sound sound(final String name) {
        return sound(new ResourceLocation(KaleidoscopeTavern.MOD_ID, name));
    }

    protected static String subtitle(String subtitle) {
        return "subtitles.%s.%s".formatted(KaleidoscopeTavern.MOD_ID, subtitle);
    }
}
