package com.github.ysbbbbbb.kaleidoscopetavern.util;

import com.github.ysbbbbbb.kaleidoscopetavern.init.tag.TagMod;
import com.google.common.collect.Maps;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ColorUtils {
    public static final String[] COLORS = new String[]{
            "white", "light_gray", "gray", "black", "brown", "red", "orange", "yellow",
            "lime", "green", "cyan", "light_blue", "blue", "purple", "magenta", "pink"
    };

    public static final Map<TagKey<Item>, ChatFormatting> COCKTAIL_INGREDIENT_COLORS = Util.make(Maps.newHashMap(), m -> {
        m.put(TagMod.COCKTAIL_INGREDIENT_BLACK, ChatFormatting.BLACK);
        m.put(TagMod.COCKTAIL_INGREDIENT_DARK_BLUE, ChatFormatting.DARK_BLUE);
        m.put(TagMod.COCKTAIL_INGREDIENT_DARK_GREEN, ChatFormatting.DARK_GREEN);
        m.put(TagMod.COCKTAIL_INGREDIENT_DARK_AQUA, ChatFormatting.DARK_AQUA);
        m.put(TagMod.COCKTAIL_INGREDIENT_DARK_RED, ChatFormatting.DARK_RED);
        m.put(TagMod.COCKTAIL_INGREDIENT_DARK_PURPLE, ChatFormatting.DARK_PURPLE);
        m.put(TagMod.COCKTAIL_INGREDIENT_GOLD, ChatFormatting.GOLD);
        m.put(TagMod.COCKTAIL_INGREDIENT_GRAY, ChatFormatting.GRAY);
        m.put(TagMod.COCKTAIL_INGREDIENT_DARK_GRAY, ChatFormatting.DARK_GRAY);
        m.put(TagMod.COCKTAIL_INGREDIENT_BLUE, ChatFormatting.BLUE);
        m.put(TagMod.COCKTAIL_INGREDIENT_GREEN, ChatFormatting.GREEN);
        m.put(TagMod.COCKTAIL_INGREDIENT_AQUA, ChatFormatting.AQUA);
        m.put(TagMod.COCKTAIL_INGREDIENT_RED, ChatFormatting.RED);
        m.put(TagMod.COCKTAIL_INGREDIENT_LIGHT_PURPLE, ChatFormatting.LIGHT_PURPLE);
        m.put(TagMod.COCKTAIL_INGREDIENT_YELLOW, ChatFormatting.YELLOW);
        m.put(TagMod.COCKTAIL_INGREDIENT_WHITE, ChatFormatting.WHITE);
    });


    @SuppressWarnings("deprecation")
    public static final Function<Item, ChatFormatting> ITEM_COLOR_CACHE = Util.memoize(item -> {
        for (TagKey<Item> tagKey : ColorUtils.COCKTAIL_INGREDIENT_COLORS.keySet()) {
            if (item.builtInRegistryHolder().is(tagKey)) {
                return ColorUtils.COCKTAIL_INGREDIENT_COLORS.get(tagKey);
            }
        }
        return ChatFormatting.RESET;
    });

    public static int mixColors(List<ChatFormatting> colors) {
        return mixColors(colors.toArray(new ChatFormatting[0]));
    }

    public static int mixColors(ChatFormatting... colors) {
        if (colors == null || colors.length == 0) {
            // 默认返回白色
            return 0xFFFFFF;
        }

        int totalR = 0, totalG = 0, totalB = 0;
        int count = 0;

        for (ChatFormatting format : colors) {
            // 排除掉 RESET 或者没有颜色属性的格式
            if (format != ChatFormatting.RESET && format.getColor() != null) {
                int colorVal = format.getColor();

                // 位运算：拆分出 R, G, B 通道
                totalR += (colorVal >> 16) & 0xFF;
                totalG += (colorVal >> 8) & 0xFF;
                totalB += colorVal & 0xFF;

                count++;
            }
        }

        // 如果没有有效颜色，返回默认值
        if (count == 0) {
            return 0xFFFFFF;
        }

        // 计算每个通道的平均值
        int avgR = totalR / count;
        int avgG = totalG / count;
        int avgB = totalB / count;

        // 位运算：将 R, G, B 重新拼装回一个 int
        return (avgR << 16) | (avgG << 8) | avgB;
    }
}
