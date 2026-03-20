package com.github.ysbbbbbb.kaleidoscopetavern.compat.ponder.init;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.compat.ponder.scene.BarrelScenes;
import com.github.ysbbbbbb.kaleidoscopetavern.compat.ponder.scene.GrapevineScenes;
import com.github.ysbbbbbb.kaleidoscopetavern.compat.ponder.scene.PressingTubScenes;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.minecraft.resources.ResourceLocation;

public class TavernPonderScreen {
    public static void register(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        helper.forComponents(KaleidoscopeTavern.modLoc("pressing_tub"))
                .addStoryBoard("pressing_tub/introduction", PressingTubScenes::introduction)
                .addStoryBoard("pressing_tub/introduction", PressingTubScenes::pressing)
                .addStoryBoard("pressing_tub/introduction", PressingTubScenes::variant);

        helper.forComponents(KaleidoscopeTavern.modLoc("barrel"))
                .addStoryBoard("barrel/introduction", BarrelScenes::introduction);

        helper.forComponents(KaleidoscopeTavern.modLoc("grapevine"))
                .addStoryBoard("grapevine/wild_generation", GrapevineScenes::wildGeneration)
                .addStoryBoard("grapevine/planting", GrapevineScenes::planting);
    }
}
