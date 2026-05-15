package com.github.ysbbbbbb.kaleidoscopetavern.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class GeneralConfig {
    public static ModConfigSpec init() {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        general(builder);
        return builder.build();
    }

    // 不能压汁的物品受到挤压时,是否掉出盆外
    public static ModConfigSpec.BooleanValue PRESSING_TUB_DROP_CONTENTS_ON_NON_JUICEABLE;

    // 是否禁用龙头的无限熔岩
    public static ModConfigSpec.BooleanValue INFINITE_LAVA_FROM_TAP;

    private static void general(ModConfigSpec.Builder builder) {
        builder.push("tavern");

        builder.comment("Whether the Pressing Tub drops its contents when a non-juiceable item is pressed.");
        PRESSING_TUB_DROP_CONTENTS_ON_NON_JUICEABLE = builder.define("PressingTubDropContentsOnNonJuiceable", true);

        builder.comment("Whether to enable infinite lava from the tap. If enabled, the tap will provide infinite lava when attached to a lava cauldron.");
        INFINITE_LAVA_FROM_TAP = builder.define("InfiniteLavaFromTap", true);

        builder.pop();
    }
}
