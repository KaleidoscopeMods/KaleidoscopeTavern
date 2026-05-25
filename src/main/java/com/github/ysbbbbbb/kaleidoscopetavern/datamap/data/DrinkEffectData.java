package com.github.ysbbbbbb.kaleidoscopetavern.datamap.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.ItemStackTemplate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 饮料效果数据
 *
 * @param item    对应的物品
 * @param effects brew level -> 效果组的映射
 */
public record DrinkEffectData(ItemStackTemplate item, Map<Integer, List<Entry>> effects) {
    private static final Codec<Integer> LEVEL_CODEC = Codec.STRING.comapFlatMap(DrinkEffectData::parseLevel, String::valueOf);

    private static final Codec<Map<Integer, List<Entry>>> EFFECTS_CODEC = Codec
            .unboundedMap(LEVEL_CODEC, Codec.list(Entry.CODEC))
            .xmap(LinkedHashMap::new, LinkedHashMap::new);

    public static final Codec<DrinkEffectData> DIRECT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ItemStackTemplate.CODEC.fieldOf("item").forGetter(DrinkEffectData::item),
            EFFECTS_CODEC.fieldOf("effects").forGetter(DrinkEffectData::effects)
    ).apply(instance, DrinkEffectData::new));

    private static DataResult<Integer> parseLevel(String value) {
        try {
            return DataResult.success(Integer.parseInt(value));
        } catch (NumberFormatException exception) {
            return DataResult.error(() -> "Invalid drink effect brew level: " + value);
        }
    }

    public List<Entry> entriesForLevel(int brewLevel) {
        return this.effects.getOrDefault(brewLevel, List.of());
    }

    /**
     * 具体每个效果条目
     *
     * @param effect      药水效果
     * @param duration    持续时间，单位是秒
     * @param amplifier   效果等级，0 表示一级，1 表示二级，以此类推
     * @param probability 效果发生的概率，范围是 0.0 到 1.0，1.0 表示 100% 发生，0.5 表示 50% 发生，以此类推
     */
    public record Entry(Holder<MobEffect> effect, int duration, int amplifier, float probability) {
        public static final Codec<Entry> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                MobEffect.CODEC.fieldOf("effect").forGetter(Entry::effect),
                Codec.INT.fieldOf("duration").forGetter(Entry::duration),
                Codec.INT.fieldOf("amplifier").forGetter(Entry::amplifier),
                Codec.FLOAT.fieldOf("probability").forGetter(Entry::probability)
        ).apply(instance, Entry::new));

        public int durationTicks() {
            return this.duration * 20;
        }
    }
}
