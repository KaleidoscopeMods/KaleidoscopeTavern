package com.github.ysbbbbbb.kaleidoscopetavern.block.properties;

import net.minecraft.util.StringRepresentable;

import java.util.Locale;

public enum PositionType implements StringRepresentable {
    /**
     * 黑板的连接位置
     */
    SINGLE,
    LEFT,
    MIDDLE,
    RIGHT;

    @Override
    public String getSerializedName() {
        return this.name().toLowerCase(Locale.ENGLISH);
    }

    @Override
    public String toString() {
        return this.getSerializedName();
    }
}
