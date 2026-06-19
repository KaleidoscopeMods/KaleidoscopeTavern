package com.github.ysbbbbbb.kaleidoscopetavern.client.init;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.client.animation.ShakerAnimation;
import com.github.ysbbbbbb.kaleidoscopetavern.client.gui.overlay.ShakerOverlay;
import com.github.ysbbbbbb.kaleidoscopetavern.client.render.block.*;
import com.github.ysbbbbbb.kaleidoscopetavern.client.render.misc.PotionBottleColor;
import com.github.ysbbbbbb.kaleidoscopetavern.client.render.misc.SignatureCocktailColor;
import com.github.ysbbbbbb.kaleidoscopetavern.compat.ponder.init.PonderCompat;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

import static net.neoforged.neoforge.client.gui.VanillaGuiLayers.CROSSHAIR;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT, modid = KaleidoscopeTavern.MOD_ID)
public class ClientSetupEvent {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        // 其他模组兼容
        PonderCompat.init();

        // 触发类加载
        ShakerAnimation.trigger();
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
        BlockEntityRenderers.register(ModBlocks.GLASSWARE_HOLDER_BE.get(), GlasswareHolderBlockEntityRender::new);
    }

    @SubscribeEvent
    public static void onRegisterClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerItem(new ShakerAnimation.ShakerExtensions(), ModItems.SHAKER);
    }

    @SubscribeEvent
    public static void onRegisterGuiOverlays(RegisterGuiLayersEvent event) {
        event.registerAbove(CROSSHAIR, KaleidoscopeTavern.modLoc("shaker_overlay"), new ShakerOverlay());
    }

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        event.register(new SignatureCocktailColor.Block(), ModBlocks.SIGNATURE_COCKTAIL.get());
        event.register(new PotionBottleColor(), ModBlocks.POTION_BOTTLE.get());
    }

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.register(new SignatureCocktailColor.Item(), ModItems.SIGNATURE_COCKTAIL.get());
    }
}
