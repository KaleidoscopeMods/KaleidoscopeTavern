package com.github.ysbbbbbb.kaleidoscopetavern.client.init;


import com.github.ysbbbbbb.kaleidoscopetavern.client.model.brew.BarrelModel;
import com.github.ysbbbbbb.kaleidoscopetavern.client.model.deco.LargeChalkboardModel;
import com.github.ysbbbbbb.kaleidoscopetavern.client.model.deco.SmallChalkboardModel;
import com.github.ysbbbbbb.kaleidoscopetavern.client.render.entity.SitRenderer;
import com.github.ysbbbbbb.kaleidoscopetavern.client.render.entity.StringLightsLayer;
import com.github.ysbbbbbb.kaleidoscopetavern.client.render.entity.ThrownMolotovRenderer;
import com.github.ysbbbbbb.kaleidoscopetavern.entity.SitEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.entity.ThrownMolotovEntity;
import net.minecraft.client.renderer.entity.ArmorStandRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.world.entity.EntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ModEntitiesRender {
    @SubscribeEvent
    public static void onEntityRenderers(EntityRenderersEvent.RegisterRenderers evt) {
        EntityRenderers.register(SitEntity.TYPE, SitRenderer::new);
        EntityRenderers.register(ThrownMolotovEntity.TYPE, ThrownMolotovRenderer::new);
    }

    @SubscribeEvent
    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(SmallChalkboardModel.LAYER_LOCATION, SmallChalkboardModel::createBodyLayer);
        event.registerLayerDefinition(LargeChalkboardModel.LAYER_LOCATION, LargeChalkboardModel::createBodyLayer);
        event.registerLayerDefinition(BarrelModel.LAYER_LOCATION, BarrelModel::createBodyLayer);
    }

    @SubscribeEvent
    @SuppressWarnings("all")
    public static void onAddLayers(EntityRenderersEvent.AddLayers event) {
        // 给玩家添加彩灯挂件渲染层
        for (PlayerSkin.Model skin : event.getSkins()) {
            LivingEntityRenderer renderer = event.getSkin(skin);
            if (renderer != null) {
                renderer.addLayer(new StringLightsLayer<>(renderer));
            }
        }
        // 盔甲架
        ArmorStandRenderer renderer = event.getRenderer(EntityType.ARMOR_STAND);
        if (renderer != null) {
            renderer.addLayer(new StringLightsLayer<>(renderer));
        }
    }
}
