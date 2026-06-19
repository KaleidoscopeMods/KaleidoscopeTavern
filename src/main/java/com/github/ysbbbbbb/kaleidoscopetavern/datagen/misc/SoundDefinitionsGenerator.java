package com.github.ysbbbbbb.kaleidoscopetavern.datagen.misc;


import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModSounds;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

public class SoundDefinitionsGenerator extends SoundDefinitionsProvider {
    public SoundDefinitionsGenerator(PackOutput output, ExistingFileHelper helper) {
        super(output, KaleidoscopeTavern.MOD_ID, helper);
    }

    @Override
    public void registerSounds() {
        SoundDefinition paddySound = definition().subtitle(subtitle("effect.vision"))
                .with(sound("effect/vision"));
        this.add(ModSounds.EFFECT_VISION, paddySound);

        SoundDefinition holderPopSound = definition().subtitle(subtitle("block.holder.pop"))
                .with(sound("block/holder_pop"));
        this.add(ModSounds.HOLDER_POP.get(), holderPopSound);

        SoundDefinition shakerShakingSound = definition().subtitle(subtitle("item.shaker.shaking"))
                .with(
                        sound("item/shaker/shaking_1"),
                        sound("item/shaker/shaking_2"),
                        sound("item/shaker/shaking_3")
                );
        this.add(ModSounds.SHAKER_SHAKING.get(), shakerShakingSound);

        SoundDefinition shakerEndSound = definition().subtitle(subtitle("item.shaker.end"))
                .with(sound("item/shaker/end"));
        this.add(ModSounds.SHAKER_END.get(), shakerEndSound);
    }

    protected static SoundDefinition.Sound sound(final String name) {
        return sound(ResourceLocation.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, name));
    }

    protected static String subtitle(String subtitle) {
        return "subtitles.%s.%s".formatted(KaleidoscopeTavern.MOD_ID, subtitle);
    }
}
