package com.github.ysbbbbbb.kaleidoscopetavern.effect;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.common.ForgeMod;

public class HighHeelsEffect extends BaseEffect {
    public HighHeelsEffect(int color) {
        super(color);
        this.addAttributeModifier(
                ForgeMod.STEP_HEIGHT_ADDITION.get(),
                "8e98b363-4638-49f0-948c-58be80d59c66",
                0.5,
                AttributeModifier.Operation.ADDITION
        );
    }
}
