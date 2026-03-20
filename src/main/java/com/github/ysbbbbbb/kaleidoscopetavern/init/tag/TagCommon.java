package com.github.ysbbbbbb.kaleidoscopetavern.init.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public interface TagCommon {
    TagKey<Item> FRUITS_GRAPES = itemTag("fruits/grapes");

    // 均衡饮食模组
    TagKey<Item> FRUITS = dietTag("fruits");
    TagKey<Item> GRAINS = dietTag("grains");
    TagKey<Item> PROTEINS = dietTag("proteins");
    TagKey<Item> SUGARS = dietTag("sugars");
    TagKey<Item> DIET_VEGETABLES = dietTag("vegetables");

    // 静谧四季模组
    TagKey<Item> SPRING_CROPS = seasonsItemTag("spring_crops");
    TagKey<Item> SUMMER_CROPS = seasonsItemTag("summer_crops");
    TagKey<Item> AUTUMN_CROPS = seasonsItemTag("autumn_crops");
    TagKey<Item> WINTER_CROPS = seasonsItemTag("winter_crops");

    TagKey<Block> SPRING_CROPS_BLOCK = seasonsBlockTag("spring_crops");
    TagKey<Block> SUMMER_CROPS_BLOCK = seasonsBlockTag("summer_crops");
    TagKey<Block> AUTUMN_CROPS_BLOCK = seasonsBlockTag("autumn_crops");
    TagKey<Block> WINTER_CROPS_BLOCK = seasonsBlockTag("winter_crops");

    // 节气模组
    TagKey<Block> DRY_AVERAGE = eclipticSeasonsTag("crops/dry_average");
    TagKey<Block> AVERAGE_MOIST = eclipticSeasonsTag("crops/average_moist");
    TagKey<Block> MOIST_HUMID = eclipticSeasonsTag("crops/moist_humid");
    TagKey<Block> HUMID_HUMID = eclipticSeasonsTag("crops/humid_humid");

    static TagKey<Item> itemTag(String name) {
        return TagKey.create(Registries.ITEM, new ResourceLocation("forge", name));
    }

    /**
     * 兼容均衡饮食模组
     */
    static TagKey<Item> dietTag(String name) {
        return TagKey.create(Registries.ITEM, new ResourceLocation("diet", name));
    }

    /**
     * 静谧四季模组兼容
     */
    static TagKey<Item> seasonsItemTag(String name) {
        return TagKey.create(Registries.ITEM, new ResourceLocation("sereneseasons", name));
    }

    static TagKey<Block> seasonsBlockTag(String name) {
        return TagKey.create(Registries.BLOCK, new ResourceLocation("sereneseasons", name));
    }

    static TagKey<Block> blockTag(String name) {
        return TagKey.create(Registries.BLOCK, new ResourceLocation("forge", name));
    }

    static TagKey<Block> eclipticSeasonsTag(String name) {
        return TagKey.create(Registries.BLOCK, new ResourceLocation("eclipticseasons", name));
    }
}
