package com.github.ysbbbbbb.kaleidoscopetavern.network.message;

import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco.ChalkboardBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record ChalkboardUpdateC2SMessage(BlockPos pos, String text, ChalkboardBlockEntity.TextAlignment textAlignment) {
    public static void encode(ChalkboardUpdateC2SMessage message, FriendlyByteBuf buf) {
        buf.writeBlockPos(message.pos);
        buf.writeUtf(message.text);
        buf.writeEnum(message.textAlignment);
    }

    public static ChalkboardUpdateC2SMessage decode(FriendlyByteBuf buf) {
        return new ChalkboardUpdateC2SMessage(buf.readBlockPos(), buf.readUtf(), buf.readEnum(ChalkboardBlockEntity.TextAlignment.class));
    }

    public static void handle(ChalkboardUpdateC2SMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        if (context.getDirection().getReceptionSide().isServer()) {
            context.enqueueWork(() -> onHandle(context, message));
        }
        context.setPacketHandled(true);
    }

    private static void onHandle(NetworkEvent.Context context, ChalkboardUpdateC2SMessage message) {
        ServerPlayer sender = context.getSender();
        if (sender == null) {
            return;
        }
        Level level = sender.level();
        BlockPos pos = message.pos();
        if (!level.isLoaded(pos)) {
            return;
        }
        if (level.getBlockEntity(pos) instanceof ChalkboardBlockEntity chalkboard) {
            if (chalkboard.isWaxed()) {
                return;
            }
            if (chalkboard.playerIsTooFarAwayToEdit(sender.getUUID())) {
                return;
            }
            chalkboard.setText(message.text());
            chalkboard.setTextAlignment(message.textAlignment());
            chalkboard.refresh();
        }
    }
}
