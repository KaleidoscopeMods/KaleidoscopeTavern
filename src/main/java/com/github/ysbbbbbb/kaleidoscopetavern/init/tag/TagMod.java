package com.github.ysbbbbbb.kaleidoscopetavern.init.tag;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public interface TagMod {
    /**
     * 沙发
     */
    TagKey<Block> SOFA = blockTag("sofa");
    /**
     * 高脚凳
     */
    TagKey<Block> BAR_STOOL = blockTag("bar_stool");
    /**
     * 展板
     */
    TagKey<Block> SANDWICH_BOARD = blockTag("sandwich_board");
    /**
     * 彩灯
     */
    TagKey<Block> STRING_LIGHTS = blockTag("string_lights");
    /**
     * 挂画
     */
    TagKey<Block> PAINTING = blockTag("painting");
    /**
     * 可以坐在上面的方块
     */
    TagKey<Block> SITTABLE = blockTag("sittable");
    /**
     * 藤架
     */
    TagKey<Block> GRAPEVINE_TRELLISES = blockTag("grapevine_trellises");
    /**
     * 穿草隐身效果起效的方块
     */
    TagKey<Block> GRASS_STEALTH_PLANTS = blockTag("grass_stealth_plants");
    /**
     * 醇热效果可破坏的石头
     */
    TagKey<Block> ARDENT_HEAT_BREAKABLE = blockTag("ardent_heat_breakable");
    /**
     * 酒柜异形酒瓶名单，这种类型的酒只允许放入一瓶
     */
    TagKey<Item> BAR_CABINET_IRREGULAR = itemTag("bar_cabinet_irregular");
    /**
     * 窖藏酒柜黑名单，即不允许放入窖藏酒柜的酒
     */
    TagKey<Item> CELLAR_CABINET_BLOCKLIST = itemTag("cellar_cabinet_blocklist");
    /**
     * 倾斜酒架黑名单，即不允许放入倾斜酒架的酒
     */
    TagKey<Item> TILTED_RACK_BLOCKLIST = itemTag("tilted_rack_blocklist");
    /**
     * 圆周酒架黑名单，即不允许放入圆周酒架的酒
     */
    TagKey<Item> CIRCULAR_RACK_BLOCKLIST = itemTag("circular_rack_blocklist");
    /**
     * 单体酒架黑名单，即不允许放入单体酒架的酒
     */
    TagKey<Item> HOLDER_BLOCKLIST = itemTag("holder_blocklist");
    /**
     * 鸡尾酒原料，只有这个 tag 的物品才能放入雪克杯，避免玩家误放
     */
    TagKey<Item> COCKTAIL_INGREDIENT = itemTag("cocktail_ingredient");
    /**
     * 鸡尾酒原料颜色分类
     */
    TagKey<Item> COCKTAIL_INGREDIENT_BLACK = itemTag("cocktail_ingredient_black");
    TagKey<Item> COCKTAIL_INGREDIENT_DARK_BLUE = itemTag("cocktail_ingredient_dark_blue");
    TagKey<Item> COCKTAIL_INGREDIENT_DARK_GREEN = itemTag("cocktail_ingredient_dark_green");
    TagKey<Item> COCKTAIL_INGREDIENT_DARK_AQUA = itemTag("cocktail_ingredient_dark_aqua");
    TagKey<Item> COCKTAIL_INGREDIENT_DARK_RED = itemTag("cocktail_ingredient_dark_red");
    TagKey<Item> COCKTAIL_INGREDIENT_DARK_PURPLE = itemTag("cocktail_ingredient_dark_purple");
    TagKey<Item> COCKTAIL_INGREDIENT_GOLD = itemTag("cocktail_ingredient_gold");
    TagKey<Item> COCKTAIL_INGREDIENT_GRAY = itemTag("cocktail_ingredient_gray");
    TagKey<Item> COCKTAIL_INGREDIENT_DARK_GRAY = itemTag("cocktail_ingredient_dark_gray");
    TagKey<Item> COCKTAIL_INGREDIENT_BLUE = itemTag("cocktail_ingredient_blue");
    TagKey<Item> COCKTAIL_INGREDIENT_GREEN = itemTag("cocktail_ingredient_green");
    TagKey<Item> COCKTAIL_INGREDIENT_AQUA = itemTag("cocktail_ingredient_aqua");
    TagKey<Item> COCKTAIL_INGREDIENT_RED = itemTag("cocktail_ingredient_red");
    TagKey<Item> COCKTAIL_INGREDIENT_LIGHT_PURPLE = itemTag("cocktail_ingredient_light_purple");
    TagKey<Item> COCKTAIL_INGREDIENT_YELLOW = itemTag("cocktail_ingredient_yellow");
    TagKey<Item> COCKTAIL_INGREDIENT_WHITE = itemTag("cocktail_ingredient_white");
    /**
     * 摸金校尉可卸装的实体
     */
    TagKey<EntityType<?>> TOMB_RAIDER_DISARMABLE = entityTag("tomb_raider_disarmable");

    static TagKey<Item> itemTag(String name) {
        return TagKey.create(Registries.ITEM, KaleidoscopeTavern.modLoc(name));
    }

    static TagKey<Block> blockTag(String name) {
        return TagKey.create(Registries.BLOCK, KaleidoscopeTavern.modLoc(name));
    }

    static TagKey<EntityType<?>> entityTag(String name) {
        return TagKey.create(Registries.ENTITY_TYPE, KaleidoscopeTavern.modLoc(name));
    }

    static TagKey<DamageType> damageTypeTag(String name) {
        return TagKey.create(Registries.DAMAGE_TYPE, KaleidoscopeTavern.modLoc(name));
    }
}
