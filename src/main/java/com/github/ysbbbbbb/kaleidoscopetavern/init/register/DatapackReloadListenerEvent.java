package com.github.ysbbbbbb.kaleidoscopetavern.init.register;


import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.datamap.resources.DrinkEffectDataReloadListener;
import com.github.ysbbbbbb.kaleidoscopetavern.network.NetworkHandler;
import com.github.ysbbbbbb.kaleidoscopetavern.network.message.DrinkEffectSyncS2CMessage;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = KaleidoscopeTavern.MOD_ID)
public class DatapackReloadListenerEvent {
    @SubscribeEvent
    public static void onAddReloadListenerEvent(AddReloadListenerEvent event) {
        event.addListener(new DrinkEffectDataReloadListener());
    }

    @SubscribeEvent
    public static void onDatapackSyncEvent(OnDatapackSyncEvent event) {
        DrinkEffectSyncS2CMessage message = DrinkEffectSyncS2CMessage.fromServer();
        event.getPlayers().forEach(player -> NetworkHandler.sendToClient(player, message));
    }
}
