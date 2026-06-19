package com.github.ysbbbbbb.kaleidoscopetavern.effect;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class HighHeelsEffect extends BaseEffect {
    public HighHeelsEffect(int color) {
        super(color);
        this.addAttributeModifier(
                Attributes.STEP_HEIGHT,
                KaleidoscopeTavern.modLoc("high_heels_step_height"),
                0.5,
                AttributeModifier.Operation.ADD_VALUE
        );
    }
}
