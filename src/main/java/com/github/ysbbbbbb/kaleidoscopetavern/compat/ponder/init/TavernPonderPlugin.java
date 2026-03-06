package com.github.ysbbbbbb.kaleidoscopetavern.compat.ponder.init;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.createmod.ponder.foundation.PonderIndex;
import net.minecraft.resources.ResourceLocation;

public class TavernPonderPlugin implements PonderPlugin {
    @Override
    public String getModId() {
        return KaleidoscopeTavern.MOD_ID;
    }

    @Override
    public void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        TavernPonderScreen.register(helper);
    }

    @Override
    public void registerTags(PonderTagRegistrationHelper<ResourceLocation> helper) {
        TavernPonderTags.register(helper);
    }

    public static void init() {
        PonderIndex.addPlugin(new TavernPonderPlugin());
    }
}
