package com.github.ysbbbbbb.kaleidoscopetavern.effect;

/**
 * 摸金校尉：标记效果。
 * <ul>
 *     <li>开箱子不会引起猪灵/猪灵蛮兵仇恨（{@link com.github.ysbbbbbb.kaleidoscopetavern.event.ChangeTargetEvent}）</li>
 *     <li>攻击生物时，将目标主手物品耐久降至1后卸下掉落（{@link com.github.ysbbbbbb.kaleidoscopetavern.event.EffectEvent}）</li>
 * </ul>
 */
public class TombRaiderEffect extends BaseEffect {
    public TombRaiderEffect(int color) {
        super(color);
    }
}
