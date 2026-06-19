package com.github.ysbbbbbb.kaleidoscopetavern.network.message;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopetavern.item.ShakerItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ClearShakerC2SMessage() implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ClearShakerC2SMessage> TYPE = new CustomPacketPayload.Type<>(KaleidoscopeTavern.modLoc("clear_shaker"));
    public static final StreamCodec<FriendlyByteBuf, ClearShakerC2SMessage> STREAM_CODEC = StreamCodec.unit(new ClearShakerC2SMessage());

    public static void handle(ClearShakerC2SMessage message, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (!(context.player() instanceof ServerPlayer sender)) {
                return;
            }
            ItemStack stack = sender.getMainHandItem();
            if (stack.is(ModItems.SHAKER.get()) && ShakerItem.hasStorage(stack)) {
                ShakerItem.removeAll(stack);
                sender.level().playSound(null, sender.blockPosition(), SoundEvents.BOTTLE_FILL, SoundSource.PLAYERS);
            }
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
