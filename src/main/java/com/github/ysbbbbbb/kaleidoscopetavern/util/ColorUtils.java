package com.github.ysbbbbbb.kaleidoscopetavern.util;

import com.github.ysbbbbbb.kaleidoscopetavern.init.tag.TagMod;
import com.google.common.collect.Maps;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

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
}
