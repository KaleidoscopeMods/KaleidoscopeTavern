package com.github.ysbbbbbb.kaleidoscopetavern.network.message;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco.TextBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.util.TextAlignment;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record TextUpdateC2SMessage(
        BlockPos pos, String text,
        TextAlignment textAlignment
) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<TextUpdateC2SMessage> TYPE = new CustomPacketPayload.Type<>(KaleidoscopeTavern.modLoc("text_update"));

    public static final StreamCodec<FriendlyByteBuf, TextUpdateC2SMessage> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public TextUpdateC2SMessage decode(FriendlyByteBuf buf) {
            return new TextUpdateC2SMessage(buf.readBlockPos(), buf.readUtf(2048), buf.readEnum(TextAlignment.class));
        }

        @Override
        public void encode(FriendlyByteBuf buf, TextUpdateC2SMessage msg) {
            buf.writeBlockPos(msg.pos());
            buf.writeUtf(msg.text(), 2048);
            buf.writeEnum(msg.textAlignment());
        }
    };

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(TextUpdateC2SMessage message, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer sender) {
                Level level = sender.level();
                BlockPos pos = message.pos();
                if (!level.isLoaded(pos)) {
                    return;
                }
                if (level.getBlockEntity(pos) instanceof TextBlockEntity textBlock) {
                    if (textBlock.isWaxed()) {
                        return;
                    }
                    if (textBlock.playerIsTooFarAwayToEdit(sender.getUUID())) {
                        return;
                    }
                    textBlock.setText(message.text());
                    textBlock.setTextAlignment(message.textAlignment());
                    textBlock.refresh();
                }
            }
        });
    }
}
