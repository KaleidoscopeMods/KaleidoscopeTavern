package com.github.ysbbbbbb.kaleidoscopetavern.network.message;

import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco.TextBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.util.TextAlignment;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record TextUpdateC2SMessage(BlockPos pos, String text, TextAlignment textAlignment) {
    public static void encode(TextUpdateC2SMessage message, FriendlyByteBuf buf) {
        buf.writeBlockPos(message.pos);
        buf.writeUtf(message.text, 2048);
        buf.writeEnum(message.textAlignment);
    }

    public static TextUpdateC2SMessage decode(FriendlyByteBuf buf) {
        return new TextUpdateC2SMessage(
                buf.readBlockPos(),
                buf.readUtf(2048),
                buf.readEnum(TextAlignment.class)
        );
    }

    public static void handle(TextUpdateC2SMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        if (context.getDirection().getReceptionSide().isServer()) {
            context.enqueueWork(() -> onHandle(context, message));
        }
        context.setPacketHandled(true);
    }

    private static void onHandle(NetworkEvent.Context context, TextUpdateC2SMessage message) {
        ServerPlayer sender = context.getSender();
        if (sender == null) {
            return;
        }
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
}
