package com.github.ysbbbbbb.kaleidoscopetavern.network.message;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.item.ShakerItem;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ShakerTimeS2CMessage(int id, int time, int delay) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ShakerTimeS2CMessage> TYPE = new CustomPacketPayload.Type<>(KaleidoscopeTavern.modLoc("shaker_time_s2c"));

    public static final StreamCodec<FriendlyByteBuf, ShakerTimeS2CMessage> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public ShakerTimeS2CMessage decode(FriendlyByteBuf buf) {
            return new ShakerTimeS2CMessage(buf.readVarInt(), buf.readVarInt(), buf.readVarInt());
        }

        @Override
        public void encode(FriendlyByteBuf buf, ShakerTimeS2CMessage msg) {
            buf.writeVarInt(msg.id());
            buf.writeVarInt(msg.time());
            buf.writeVarInt(msg.delay());
        }
    };

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(ShakerTimeS2CMessage message, IPayloadContext context) {
        long receivedTime = System.nanoTime();
        context.enqueueWork(() -> onHandle(message, receivedTime));
    }

    @OnlyIn(Dist.CLIENT)
    private static void onHandle(ShakerTimeS2CMessage message, long receivedTime) {
        if (Minecraft.getInstance().player != null) {
            ShakerItem.syncTime(Minecraft.getInstance().player, message.id(), message.time(), message.delay(), receivedTime);
        }
    }
}
