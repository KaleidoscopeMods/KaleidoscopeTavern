package com.github.ysbbbbbb.kaleidoscopetavern.block.properties;

import net.minecraft.util.StringRepresentable;

import java.util.Locale;

public enum TrellisType implements StringRepresentable {
    /**
     * 藤架的链接状态，也用于葡萄藤架
     */
    SINGLE,
    NORTH_SOUTH,
    EAST_WEST,
    SIX_DIRECTION,
    CROSS_NORTH_SOUTH,
    CROSS_EAST_WEST,
    CROSS_UP_DOWN;

    @Override
    public String getSerializedName() {
        return this.name().toLowerCase(Locale.ENGLISH);
    }

    @Override
    public String toString() {
        return this.getSerializedName();
    }
}
