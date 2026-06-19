package com.github.ysbbbbbb.kaleidoscopetavern.datamap.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;

/**
 * 饮料效果数据
 *
 * @param item    对应的物品
 * @param effects 包含两层 list
 *                第一层为 brew level -> 效果组的映射
 *                第二层为效果组中的每个都会尝试触发的效果条目
 */
public record DrinkEffectData(Holder<Item> item, List<List<Entry>> effects) {
    public static final Codec<DrinkEffectData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ItemStack.ITEM_NON_AIR_CODEC.fieldOf("item").forGetter(DrinkEffectData::item),
            Codec.list(Codec.list(Entry.ENTRY_CODEC)).fieldOf("effects").forGetter(DrinkEffectData::effects)
    ).apply(instance, DrinkEffectData::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, DrinkEffectData> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public DrinkEffectData decode(RegistryFriendlyByteBuf buf) {
            Holder<Item> item = ByteBufCodecs.holderRegistry(Registries.ITEM).decode(buf);
            List<List<Entry>> effects = Entry.STREAM_CODEC.apply(ByteBufCodecs.list()).apply(ByteBufCodecs.list()).decode(buf);
            return new DrinkEffectData(item, effects);
        }

        @Override
        public void encode(RegistryFriendlyByteBuf buf, DrinkEffectData data) {
            ByteBufCodecs.holderRegistry(Registries.ITEM).encode(buf, data.item());
            Entry.STREAM_CODEC.apply(ByteBufCodecs.list()).apply(ByteBufCodecs.list()).encode(buf, data.effects());
        }
    };

    /**
     * 具体每个效果条目
     *
     * @param effect      药水效果
     * @param duration    持续时间，单位是秒
     * @param amplifier   效果等级，0 表示一级，1 表示二级，以此类推
     * @param probability 效果发生的概率，范围是 0.0 到 1.0，1.0 表示 100% 发生，0.5 表示 50% 发生，以此类推
     */
    public record Entry(Holder<MobEffect> effect, int duration, int amplifier, float probability) {
        public static final Codec<Entry> ENTRY_CODEC = RecordCodecBuilder.create(instance -> instance.group(
                MobEffect.CODEC.fieldOf("effect").forGetter(Entry::effect),
                Codec.INT.fieldOf("duration").forGetter(Entry::duration),
                Codec.INT.fieldOf("amplifier").forGetter(Entry::amplifier),
                Codec.FLOAT.fieldOf("probability").forGetter(Entry::probability)
        ).apply(instance, Entry::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, Entry> STREAM_CODEC = new StreamCodec<>() {
            @Override
            public Entry decode(RegistryFriendlyByteBuf buf) {
                Holder<MobEffect> effect = ByteBufCodecs.holderRegistry(Registries.MOB_EFFECT).decode(buf);
                int duration = ByteBufCodecs.VAR_INT.decode(buf);
                int amplifier = ByteBufCodecs.VAR_INT.decode(buf);
                float probability = ByteBufCodecs.FLOAT.decode(buf);
                return new Entry(effect, duration, amplifier, probability);
            }

            @Override
            public void encode(RegistryFriendlyByteBuf buf, Entry entry) {
                ByteBufCodecs.holderRegistry(Registries.MOB_EFFECT).encode(buf, entry.effect());
                ByteBufCodecs.VAR_INT.encode(buf, entry.duration());
                ByteBufCodecs.VAR_INT.encode(buf, entry.amplifier());
                ByteBufCodecs.FLOAT.encode(buf, entry.probability());
            }
        };
    }
}
