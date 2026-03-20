package com.github.ysbbbbbb.kaleidoscopetavern.compat.ponder.init;

import net.minecraftforge.fml.ModList;

public class PonderCompat {
    public static final String ID = "ponder";

    public static boolean PONDER_LOADED = false;

    public static void init() {
        if (ModList.get().isLoaded(ID)) {
            PONDER_LOADED = true;
            TavernPonderPlugin.init();
        }
    }
}
