package com.github.ysbbbbbb.kaleidoscopetavern.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class GeneralConfig {
    // 不能压汁的物品受到挤压时,是否掉出盆外
    public static ForgeConfigSpec.BooleanValue PRESSING_TUB_DROP_CONTENTS_ON_NON_JUICEABLE;

    // 是否禁用龙头的无限熔岩
    public static ForgeConfigSpec.BooleanValue INFINITE_LAVA_FROM_TAP;

    // 是否允许潜行右键放置各种原版的瓶子
    public static ForgeConfigSpec.BooleanValue WATER_BOTTLE_PLACEMENT;
    public static ForgeConfigSpec.BooleanValue HONEY_BOTTLE_PLACEMENT;
    public static ForgeConfigSpec.BooleanValue POTION_BOTTLE_PLACEMENT;
    public static ForgeConfigSpec.BooleanValue DRAGON_BREATH_BOTTLE_PLACEMENT;
    public static ForgeConfigSpec.BooleanValue EXPERIENCE_BOTTLE_PLACEMENT;

    public static ForgeConfigSpec init() {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        general(builder);
        bottle(builder);
        return builder.build();
    }

    private static void general(ForgeConfigSpec.Builder builder) {
        builder.push("tavern");

        builder.comment("Whether the Pressing Tub drops its contents when a non-juiceable item is pressed.");
        PRESSING_TUB_DROP_CONTENTS_ON_NON_JUICEABLE = builder.define("PressingTubDropContentsOnNonJuiceable", true);

        builder.comment("Whether to enable infinite lava from the tap. If enabled, the tap will provide infinite lava when attached to a lava cauldron.");
        INFINITE_LAVA_FROM_TAP = builder.define("InfiniteLavaFromTap", true);

        builder.pop();
    }

    private static void bottle(ForgeConfigSpec.Builder builder) {
        builder.push("vanilla bottle placement");

        builder.comment("Whether vanilla water bottles can be placed by shift-right-clicking a block.");
        WATER_BOTTLE_PLACEMENT = builder.define("WaterBottle", true);

        builder.comment("Whether vanilla honey bottles can be placed by shift-right-clicking a block.");
        HONEY_BOTTLE_PLACEMENT = builder.define("HoneyBottle", true);

        builder.comment("Whether vanilla potion bottles can be placed by shift-right-clicking a block.");
        POTION_BOTTLE_PLACEMENT = builder.define("PotionBottle", true);

        builder.comment("Whether vanilla dragon's breath bottles can be placed by shift-right-clicking a block.");
        DRAGON_BREATH_BOTTLE_PLACEMENT = builder.define("DragonBreathBottle", true);

        builder.comment("Whether vanilla experience bottles can be placed by shift-right-clicking a block.");
        EXPERIENCE_BOTTLE_PLACEMENT = builder.define("ExperienceBottle", true);

        builder.pop();
    }
}
