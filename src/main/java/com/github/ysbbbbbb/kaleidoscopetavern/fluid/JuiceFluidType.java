package com.github.ysbbbbbb.kaleidoscopetavern.fluid;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.PathType;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.FluidType;
import org.jetbrains.annotations.Nullable;


public class JuiceFluidType extends FluidType {
    private final Identifier id;

    public JuiceFluidType(Identifier id, Properties properties) {
        super(properties);
        this.id = id;
    }

    public JuiceFluidType(Identifier id, int lightLevel) {
        this(id, FluidType.Properties.create()
                .descriptionId(Util.makeDescriptionId("block", id))
                .fallDistanceModifier(0)
                .canExtinguish(true)
                // 不能无限
                .canConvertToSource(false)
                .supportsBoating(true)
                .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
                .canHydrate(true)
                .lightLevel(lightLevel)
        );
    }

    public Identifier getId() {
        return id;
    }

    @Override
    @Nullable
    public PathType getBlockPathType(FluidState state, BlockGetter level, BlockPos pos,
                                     @Nullable Mob mob, boolean canFluidLog) {
        return canFluidLog ? super.getBlockPathType(state, level, pos, mob, true) : null;
    }
}
