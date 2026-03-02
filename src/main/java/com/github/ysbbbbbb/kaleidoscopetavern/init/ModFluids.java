package com.github.ysbbbbbb.kaleidoscopetavern.init;

import com.github.ysbbbbbb.kaleidoscopetavern.fluid.JuiceFluidType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

import static com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern.MOD_ID;
import static com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern.modLoc;

public class ModFluids {
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, MOD_ID);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(BuiltInRegistries.FLUID, MOD_ID);

    // ID
    public static final ResourceLocation GRAPE_JUICE_ID = modLoc("grape_juice");
    public static final ResourceLocation FLOWING_GRAPE_JUICE_ID = modLoc("flowing_grape_juice");

    public static final ResourceLocation SWEET_BERRIES_JUICE_ID = modLoc("sweet_berries_juice");
    public static final ResourceLocation FLOWING_SWEET_BERRIES_JUICE_ID = modLoc("flowing_sweet_berries_juice");

    public static final ResourceLocation GLOW_BERRIES_JUICE_ID = modLoc("glow_berries_juice");
    public static final ResourceLocation FLOWING_GLOW_BERRIES_JUICE_ID = modLoc("flowing_glow_berries_juice");

    // FluidType
    public static final Supplier<FluidType> GRAPE_JUICE_TYPE = FLUID_TYPES.register("grape_juice", () -> new JuiceFluidType(GRAPE_JUICE_ID, 0));
    public static final Supplier<FluidType> SWEET_BERRIES_JUICE_TYPE = FLUID_TYPES.register("sweet_berries_juice", () -> new JuiceFluidType(SWEET_BERRIES_JUICE_ID, 0));
    public static final Supplier<FluidType> GLOW_BERRIES_JUICE_TYPE = FLUID_TYPES.register("glow_berries_juice", () -> new JuiceFluidType(GLOW_BERRIES_JUICE_ID, 14));

    // Fluid
    public static final Supplier<BaseFlowingFluid.Source> GRAPE_JUICE = FLUIDS.register("grape_juice", () -> new BaseFlowingFluid.Source(ModFluids.grapeJuiceProperties()));
    public static final Supplier<BaseFlowingFluid.Flowing> FLOWING_GRAPE_JUICE = FLUIDS.register("flowing_grape_juice", () -> new BaseFlowingFluid.Flowing(ModFluids.grapeJuiceProperties()));

    public static final Supplier<BaseFlowingFluid.Source> SWEET_BERRIES_JUICE = FLUIDS.register("sweet_berries_juice", () -> new BaseFlowingFluid.Source(ModFluids.sweetBerriesJuiceProperties()));
    public static final Supplier<BaseFlowingFluid.Flowing> FLOWING_SWEET_BERRIES_JUICE = FLUIDS.register("flowing_sweet_berries_juice", () -> new BaseFlowingFluid.Flowing(ModFluids.sweetBerriesJuiceProperties()));

    public static final Supplier<BaseFlowingFluid.Source> GLOW_BERRIES_JUICE = FLUIDS.register("glow_berries_juice", () -> new BaseFlowingFluid.Source(ModFluids.glowBerriesJuiceProperties()));
    public static final Supplier<BaseFlowingFluid.Flowing> FLOWING_GLOW_BERRIES_JUICE = FLUIDS.register("flowing_glow_berries_juice", () -> new BaseFlowingFluid.Flowing(ModFluids.glowBerriesJuiceProperties()));

    private static BaseFlowingFluid.Properties grapeJuiceProperties() {
        return new BaseFlowingFluid.Properties(GRAPE_JUICE_TYPE, GRAPE_JUICE, FLOWING_GRAPE_JUICE).bucket(ModItems.GRAPE_BUCKET);
    }

    private static BaseFlowingFluid.Properties sweetBerriesJuiceProperties() {
        return new BaseFlowingFluid.Properties(SWEET_BERRIES_JUICE_TYPE, SWEET_BERRIES_JUICE, FLOWING_SWEET_BERRIES_JUICE).bucket(ModItems.SWEET_BERRIES_BUCKET);
    }

    private static BaseFlowingFluid.Properties glowBerriesJuiceProperties() {
        return new BaseFlowingFluid.Properties(GLOW_BERRIES_JUICE_TYPE, GLOW_BERRIES_JUICE, FLOWING_GLOW_BERRIES_JUICE).bucket(ModItems.GLOW_BERRIES_BUCKET);
    }
}
