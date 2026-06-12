package com.github.ysbbbbbb.kaleidoscopetavern.effect;

/**
 * 摸金校尉：标记效果，开箱子不会引起猪灵仇恨。
 * 实际逻辑在 {@link com.github.ysbbbbbb.kaleidoscopetavern.event.ChangeTargetEvent} 中处理。
 */
public class TombRaiderEffect extends BaseEffect {
    public TombRaiderEffect(int color) {
        super(color);
    }
}
