package com.github.ysbbbbbb.kaleidoscopetavern.init.register;


import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.datamap.resources.DrinkEffectDataReloadListener;
import com.github.ysbbbbbb.kaleidoscopetavern.network.NetworkHandler;
import com.github.ysbbbbbb.kaleidoscopetavern.network.message.DrinkEffectSyncS2CMessage;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;

@EventBusSubscriber(modid = KaleidoscopeTavern.MOD_ID)
public class DatapackReloadListenerEvent {
    @SubscribeEvent
    public static void onAddReloadListenerEvent(AddReloadListenerEvent event) {
        event.addListener(new DrinkEffectDataReloadListener());
    }

    @SubscribeEvent
    public static void onDatapackSyncEvent(OnDatapackSyncEvent event) {
        DrinkEffectSyncS2CMessage message = DrinkEffectSyncS2CMessage.fromServer();
        event.getRelevantPlayers().forEach(player -> NetworkHandler.sendToClient(player, message));
    }
}
