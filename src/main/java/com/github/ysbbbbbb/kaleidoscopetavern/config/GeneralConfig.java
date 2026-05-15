package com.github.ysbbbbbb.kaleidoscopetavern.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class GeneralConfig {
    public static ForgeConfigSpec init() {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        general(builder);
        return builder.build();
    }

    // 不能压汁的物品受到挤压时,是否掉出盆外
    public static ForgeConfigSpec.BooleanValue PRESSING_TUB_DROP_CONTENTS_ON_NON_JUICEABLE;

    private static void general(ForgeConfigSpec.Builder builder) {
        builder.push("tavern");

        builder.comment("Whether the Pressing Tub drops its contents when a non-juiceable item is pressed.");
        PRESSING_TUB_DROP_CONTENTS_ON_NON_JUICEABLE = builder.define("PressingTubDropContentsOnNonJuiceable", true);

        builder.pop();
    }
}
