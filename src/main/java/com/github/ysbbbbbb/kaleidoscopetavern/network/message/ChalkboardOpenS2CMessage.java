package com.github.ysbbbbbb.kaleidoscopetavern.network.message;

import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco.ChalkboardBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.client.gui.block.ChalkboardScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record ChalkboardOpenS2CMessage(BlockPos pos) {
    public static void encode(ChalkboardOpenS2CMessage message, FriendlyByteBuf buf) {
        buf.writeBlockPos(message.pos);
    }

    public static ChalkboardOpenS2CMessage decode(FriendlyByteBuf buf) {
        return new ChalkboardOpenS2CMessage(buf.readBlockPos());
    }

    public static void handle(ChalkboardOpenS2CMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        if (context.getDirection().getReceptionSide().isClient()) {
            context.enqueueWork(() -> onHandle(message));
        }
        context.setPacketHandled(true);
    }

    @OnlyIn(Dist.CLIENT)
    private static void onHandle(ChalkboardOpenS2CMessage message) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) {
            return;
        }
        Player player = mc.player;
        if (player == null) {
            return;
        }
        if (!mc.level.isLoaded(message.pos)) {
            return;
        }
        Level level = mc.level;
        BlockPos pos = message.pos();
        if (level.getBlockEntity(pos) instanceof ChalkboardBlockEntity chalkboard) {
            if (chalkboard.isWaxed()) {
                return;
            }
            if (chalkboard.playerIsTooFarAwayToEdit(player.getUUID())) {
                return;
            }
            mc.setScreen(new ChalkboardScreen(chalkboard));
        }
    }
}
