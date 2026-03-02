package com.github.ysbbbbbb.kaleidoscopetavern.network.message;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco.TextBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.client.gui.block.TextScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record TextOpenS2CMessage(BlockPos pos) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<TextOpenS2CMessage> TYPE = new CustomPacketPayload.Type<>(KaleidoscopeTavern.modLoc("text_open"));

    public static final StreamCodec<FriendlyByteBuf, TextOpenS2CMessage> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public TextOpenS2CMessage decode(FriendlyByteBuf buf) {
            return new TextOpenS2CMessage(buf.readBlockPos());
        }

        @Override
        public void encode(FriendlyByteBuf buf, TextOpenS2CMessage msg) {
            buf.writeBlockPos(msg.pos());
        }
    };

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(TextOpenS2CMessage message, IPayloadContext context) {
        context.enqueueWork(() -> onHandle(message));
    }

    @OnlyIn(Dist.CLIENT)
    private static void onHandle(TextOpenS2CMessage message) {
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
        if (level.getBlockEntity(pos) instanceof TextBlockEntity textBlock) {
            if (textBlock.isWaxed()) {
                return;
            }
            if (textBlock.playerIsTooFarAwayToEdit(player.getUUID())) {
                return;
            }
            mc.setScreen(new TextScreen(textBlock));
        }
    }
}
