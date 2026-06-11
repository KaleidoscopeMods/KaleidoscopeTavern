package com.github.ysbbbbbb.kaleidoscopetavern.client.init;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.client.gui.overlay.ShakerOverlay;
import com.github.ysbbbbbb.kaleidoscopetavern.client.render.block.*;
import com.github.ysbbbbbb.kaleidoscopetavern.compat.ponder.init.PonderCompat;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static net.minecraftforge.client.gui.overlay.VanillaGuiOverlay.CROSSHAIR;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT, modid = KaleidoscopeTavern.MOD_ID)
public class ClientSetupEvent {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        // 其他模组兼容
        PonderCompat.init();
    }

    @SubscribeEvent
    public static void onEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        BlockEntityRenderers.register(ModBlocks.CHALKBOARD_BE.get(), ChalkboardBlockEntityRender::new);
        BlockEntityRenderers.register(ModBlocks.SANDWICH_BOARD_BE.get(), SandwichBlockEntityRender::new);
        BlockEntityRenderers.register(ModBlocks.PRESSING_TUB_BE.get(), PressingTubBlockEntityRender::new);
        BlockEntityRenderers.register(ModBlocks.BARREL_BE.get(), BarrelBlockEntityRender::new);
        BlockEntityRenderers.register(ModBlocks.BAR_CABINET_BE.get(), BarCabinetBlockEntityRender::new);
        BlockEntityRenderers.register(ModBlocks.BAR_STOOL_BE.get(), BarStoolBlockEntityRender::new);
        BlockEntityRenderers.register(ModBlocks.CELLAR_CABINET_BE.get(), CellarCabinetBlockEntityRender::new);
        BlockEntityRenderers.register(ModBlocks.TILTED_RACK_BE.get(), TiltedRackBlockEntityRender::new);
        BlockEntityRenderers.register(ModBlocks.CIRCULAR_RACK_BE.get(), CircularRackBlockEntityRender::new);
        BlockEntityRenderers.register(ModBlocks.HOLDER_BE.get(), HolderBlockEntityRender::new);
        BlockEntityRenderers.register(ModBlocks.SHAKER_BE.get(), ShakerBlockEntityRender::new);
    }

    @SubscribeEvent
    public static void onRegisterGuiOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAbove(CROSSHAIR.id(), "shaker_overlay", new ShakerOverlay());
    }
}
