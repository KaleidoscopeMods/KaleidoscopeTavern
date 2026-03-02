package com.github.ysbbbbbb.kaleidoscopetavern.init;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.crafting.recipe.BarrelRecipe;
import com.github.ysbbbbbb.kaleidoscopetavern.crafting.recipe.PressingTubRecipe;
import com.github.ysbbbbbb.kaleidoscopetavern.crafting.serializer.BarrelRecipeSerializer;
import com.github.ysbbbbbb.kaleidoscopetavern.crafting.serializer.PressingTubRecipeSerializer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.function.Supplier;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, KaleidoscopeTavern.MOD_ID);

    public static Supplier<RecipeSerializer<?>> PRESSING_TUB_SERIALIZER = RECIPE_SERIALIZERS.register("pressing_tub", PressingTubRecipeSerializer::new);
    public static Supplier<RecipeSerializer<?>> BARREL_SERIALIZER = RECIPE_SERIALIZERS.register("barrel", BarrelRecipeSerializer::new);

    public static RecipeType<PressingTubRecipe> PRESSING_TUB_RECIPE;
    public static RecipeType<BarrelRecipe> BARREL_RECIPE;

    @SubscribeEvent
    public static void register(RegisterEvent evt) {
        if (evt.getRegistryKey().equals(Registries.RECIPE_SERIALIZER)) {
            PRESSING_TUB_RECIPE = RecipeType.simple(KaleidoscopeTavern.modLoc("pressing_tub"));
            BARREL_RECIPE = RecipeType.simple(KaleidoscopeTavern.modLoc("barrel"));
        }
    }
}
