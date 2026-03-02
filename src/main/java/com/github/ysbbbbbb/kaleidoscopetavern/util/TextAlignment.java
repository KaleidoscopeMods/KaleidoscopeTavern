package com.github.ysbbbbbb.kaleidoscopetavern.util;

public enum TextAlignment {
    LEFT,
    CENTER,
    RIGHT;

    public static TextAlignment byId(int id) {
        return switch (id) {
            case 0 -> LEFT;
            case 2 -> RIGHT;
            default -> CENTER;
        };
    }

    public int getId() {
        return switch (this) {
            case LEFT -> 0;
            case RIGHT -> 2;
            default -> 1;
        };
    }
}
