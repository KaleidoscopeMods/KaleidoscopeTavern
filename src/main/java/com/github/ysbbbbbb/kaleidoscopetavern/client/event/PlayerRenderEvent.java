package com.github.ysbbbbbb.kaleidoscopetavern.client.event;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderPlayerEvent;

import static com.github.ysbbbbbb.kaleidoscopetavern.effect.GrassStealthEffect.notInGrassStealthPlant;

@EventBusSubscriber(modid = KaleidoscopeTavern.MOD_ID, value = Dist.CLIENT)
public class PlayerRenderEvent {
    @SubscribeEvent
    public static void onPlayerRender(RenderPlayerEvent.Pre event) {
        Player player = event.getEntity();
        if (!player.isAlive()) {
            return;
        }
        if (player.hasEffect(ModEffects.GRASS_STEALTH) && player.isShiftKeyDown()) {
            Level level = player.level();
            BlockPos pos = player.blockPosition();
            BlockPos abovePos = pos.above();
            if (notInGrassStealthPlant(level, pos) && notInGrassStealthPlant(level, abovePos)) {
                return;
            }
            event.setCanceled(true);
        }
    }
}