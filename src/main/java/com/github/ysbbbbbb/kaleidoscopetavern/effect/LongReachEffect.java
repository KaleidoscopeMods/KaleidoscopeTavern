package com.github.ysbbbbbb.kaleidoscopetavern.effect;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class LongReachEffect extends BaseEffect {
    public LongReachEffect(int color) {
        super(color);
        this.addAttributeModifier(
                Attributes.BLOCK_INTERACTION_RANGE,
                KaleidoscopeTavern.modLoc("long_reach_block"),
                3.0,
                AttributeModifier.Operation.ADD_VALUE
        );
        this.addAttributeModifier(
                Attributes.ENTITY_INTERACTION_RANGE,
                KaleidoscopeTavern.modLoc("long_reach_entity"),
                3.0,
                AttributeModifier.Operation.ADD_VALUE
        );
    }
}
