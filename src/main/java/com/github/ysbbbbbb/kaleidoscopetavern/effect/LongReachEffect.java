package com.github.ysbbbbbb.kaleidoscopetavern.effect;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.common.ForgeMod;

public class LongReachEffect extends BaseEffect {
    public LongReachEffect(int color) {
        super(color);
        this.addAttributeModifier(
                ForgeMod.BLOCK_REACH.get(),
                "7a38abe7-6c6d-4894-8391-84417cb8368a",
                3.0,
                AttributeModifier.Operation.ADDITION
        );
        this.addAttributeModifier(
                ForgeMod.ENTITY_REACH.get(),
                "995f2ddb-6f4c-48c2-b9a0-68fe5b4277d7",
                3.0,
                AttributeModifier.Operation.ADDITION
        );
    }
}
