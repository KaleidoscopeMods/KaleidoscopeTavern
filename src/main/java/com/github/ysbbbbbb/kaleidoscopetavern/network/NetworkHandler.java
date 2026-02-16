package com.github.ysbbbbbb.kaleidoscopetavern.network;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.network.message.ChalkboardOpenS2CMessage;
import com.github.ysbbbbbb.kaleidoscopetavern.network.message.ChalkboardUpdateC2SMessage;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;

public class NetworkHandler {
    private static final String VERSION = "1.0.0";

    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(new ResourceLocation(KaleidoscopeTavern.MOD_ID, "network"),
            () -> VERSION, it -> it.equals(VERSION), it -> it.equals(VERSION));

    public static void init() {
        CHANNEL.registerMessage(0, ChalkboardUpdateC2SMessage.class,
                ChalkboardUpdateC2SMessage::encode, ChalkboardUpdateC2SMessage::decode,
                ChalkboardUpdateC2SMessage::handle, Optional.of(NetworkDirection.PLAY_TO_SERVER));

        CHANNEL.registerMessage(1, ChalkboardOpenS2CMessage.class,
                ChalkboardOpenS2CMessage::encode, ChalkboardOpenS2CMessage::decode,
                ChalkboardOpenS2CMessage::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
    }

    public static void sendToClient(Player player, Object packet) {
        if (player instanceof ServerPlayer serverPlayer) {
            CHANNEL.send(PacketDistributor.PLAYER.with(() -> serverPlayer), packet);
        }
    }

    public static void sendToServer(Object packet) {
        CHANNEL.sendToServer(packet);
    }
}
