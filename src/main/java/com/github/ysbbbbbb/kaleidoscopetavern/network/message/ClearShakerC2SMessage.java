package com.github.ysbbbbbb.kaleidoscopetavern.network.message;

import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopetavern.item.ShakerItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record ClearShakerC2SMessage() {
    public static void encode(ClearShakerC2SMessage message, FriendlyByteBuf buf) {
    }

    public static ClearShakerC2SMessage decode(FriendlyByteBuf buf) {
        return new ClearShakerC2SMessage();
    }

    public static void handle(ClearShakerC2SMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        if (context.getDirection().getReceptionSide().isServer()) {
            context.enqueueWork(() -> {
                ServerPlayer sender = context.getSender();
                if (sender == null) {
                    return;
                }
                ItemStack stack = sender.getMainHandItem();
                if (stack.is(ModItems.SHAKER.get()) && ShakerItem.hasStorage(stack)) {
                    ShakerItem.removeAll(stack);
                    sender.level().playSound(null, sender.blockPosition(), SoundEvents.BOTTLE_FILL, SoundSource.PLAYERS);
                }
            });
        }
        context.setPacketHandled(true);
    }
}
