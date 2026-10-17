package com.github.ysbbbbbb.kaleidoscopetavern.network.message;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.item.ShakerItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ShakerTimeC2SMessage(int id, int time) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ShakerTimeC2SMessage> TYPE = new CustomPacketPayload.Type<>(KaleidoscopeTavern.modLoc("shaker_time_c2s"));

    public static final StreamCodec<FriendlyByteBuf, ShakerTimeC2SMessage> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public ShakerTimeC2SMessage decode(FriendlyByteBuf buf) {
            return new ShakerTimeC2SMessage(buf.readVarInt(), buf.readVarInt());
        }

        @Override
        public void encode(FriendlyByteBuf buf, ShakerTimeC2SMessage msg) {
            buf.writeVarInt(msg.id());
            buf.writeVarInt(msg.time());
        }
    };

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(ShakerTimeC2SMessage message, IPayloadContext context) {
        // 在进入主线程前记录收包时间，避免卡顿时把排队时间算入调酒用时
        long receivedTime = System.nanoTime();
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer sender) {
                ShakerItem.handleTime(sender, message.id(), message.time(), receivedTime);
            }
        });
    }
}
