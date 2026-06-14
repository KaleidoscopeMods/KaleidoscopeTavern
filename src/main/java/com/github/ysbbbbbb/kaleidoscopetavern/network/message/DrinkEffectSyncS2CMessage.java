package com.github.ysbbbbbb.kaleidoscopetavern.network.message;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.datamap.data.DrinkEffectData;
import com.github.ysbbbbbb.kaleidoscopetavern.datamap.resources.DrinkEffectDataReloadListener;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * 服务端 -> 客户端同步所有 DrinkEffectData 数据
 */
public class DrinkEffectSyncS2CMessage {
    private static final String ENTRIES = "entries";

    private final CompoundTag data;

    public DrinkEffectSyncS2CMessage(CompoundTag data) {
        this.data = data;
    }

    /**
     * 从当前服务端 INSTANCE 构建消息
     */
    public static DrinkEffectSyncS2CMessage fromServer() {
        CompoundTag syncData = new CompoundTag();
        ListTag entries = new ListTag();
        for (DrinkEffectData data : DrinkEffectDataReloadListener.INSTANCE.values()) {
            var result = DrinkEffectData.CODEC.encodeStart(NbtOps.INSTANCE, data);
            result.result().ifPresentOrElse(tag -> {
                if (tag instanceof CompoundTag compoundTag) {
                    entries.add(compoundTag);
                }
            }, () -> result.error().ifPresent(error -> KaleidoscopeTavern.LOGGER.error(
                    "Failed to encode drink effect data for sync: {}", error.message())));
        }
        syncData.put(ENTRIES, entries);
        return new DrinkEffectSyncS2CMessage(syncData);
    }

    public static void encode(DrinkEffectSyncS2CMessage message, FriendlyByteBuf buf) {
        buf.writeNbt(message.data);
    }

    public static DrinkEffectSyncS2CMessage decode(FriendlyByteBuf buf) {
        CompoundTag data = buf.readNbt();
        return new DrinkEffectSyncS2CMessage(data == null ? new CompoundTag() : data);
    }

    public static void handle(DrinkEffectSyncS2CMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        if (context.getDirection().getReceptionSide().isClient()) {
            context.enqueueWork(() -> onHandle(message));
        }
        context.setPacketHandled(true);
    }

    @OnlyIn(Dist.CLIENT)
    private static void onHandle(DrinkEffectSyncS2CMessage message) {
        DrinkEffectDataReloadListener.INSTANCE.clear();
        ListTag entries = message.data.getList(ENTRIES, Tag.TAG_COMPOUND);
        for (int i = 0; i < entries.size(); i++) {
            CompoundTag entry = entries.getCompound(i);
            var result = DrinkEffectData.CODEC.parse(NbtOps.INSTANCE, entry);
            result.result().ifPresentOrElse(data -> DrinkEffectDataReloadListener.INSTANCE.put(data.item(), data),
                    () -> result.error().ifPresent(error -> KaleidoscopeTavern.LOGGER.error(
                            "Failed to decode synced drink effect data: {}", error.message())));
        }
    }
}
