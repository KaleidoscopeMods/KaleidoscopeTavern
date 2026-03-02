package com.github.ysbbbbbb.kaleidoscopetavern.fluid;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.PathType;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.FluidType;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class JuiceFluidType extends FluidType {
    private final ResourceLocation id;
    private final ResourceLocation stillTexture;
    private final ResourceLocation flowingTexture;

    public JuiceFluidType(ResourceLocation id, Properties properties) {
        super(properties);
        this.id = id;
        this.stillTexture = ResourceLocation.fromNamespaceAndPath(this.id.getNamespace(), "block/%s_still".formatted(id.getPath()));
        this.flowingTexture = ResourceLocation.fromNamespaceAndPath(this.id.getNamespace(), "block/%s_flow".formatted(id.getPath()));
    }

    public JuiceFluidType(ResourceLocation id, int lightLevel) {
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

    public ResourceLocation getId() {
        return id;
    }

    @Override
    @Nullable
    public PathType getBlockPathType(FluidState state, BlockGetter level, BlockPos pos,
                                     @Nullable Mob mob, boolean canFluidLog) {
        return canFluidLog ? super.getBlockPathType(state, level, pos, mob, true) : null;
    }

    @Override
    @SuppressWarnings("removal")
    public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
        consumer.accept(new IClientFluidTypeExtensions() {
            @Override
            public ResourceLocation getStillTexture() {
                return stillTexture;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return flowingTexture;
            }
        });
    }
}
