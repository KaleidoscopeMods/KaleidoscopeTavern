package com.github.ysbbbbbb.kaleidoscopetavern.init;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, KaleidoscopeTavern.MOD_ID);

    public static RegistryObject<CreativeModeTab> TAVERN_MAIN_TAB = TABS.register("tavern_main", () -> CreativeModeTab.builder()
            .title(Component.translatable("item_group.kaleidoscope_tavern.tavern_main.name"))
            .icon(Items.APPLE::getDefaultInstance)
            .displayItems((par, output) -> {
                output.accept(ModItems.WHITE_SOFA.get());
                output.accept(ModItems.LIGHT_GRAY_SOFA.get());
                output.accept(ModItems.GRAY_SOFA.get());
                output.accept(ModItems.BLACK_SOFA.get());
                output.accept(ModItems.BROWN_SOFA.get());
                output.accept(ModItems.RED_SOFA.get());
                output.accept(ModItems.ORANGE_SOFA.get());
                output.accept(ModItems.YELLOW_SOFA.get());
                output.accept(ModItems.LIME_SOFA.get());
                output.accept(ModItems.GREEN_SOFA.get());
                output.accept(ModItems.CYAN_SOFA.get());
                output.accept(ModItems.LIGHT_BLUE_SOFA.get());
                output.accept(ModItems.BLUE_SOFA.get());
                output.accept(ModItems.PURPLE_SOFA.get());
                output.accept(ModItems.MAGENTA_SOFA.get());
                output.accept(ModItems.PINK_SOFA.get());

                output.accept(ModItems.WHITE_BAR_STOOL.get());
                output.accept(ModItems.LIGHT_GRAY_BAR_STOOL.get());
                output.accept(ModItems.GRAY_BAR_STOOL.get());
                output.accept(ModItems.BLACK_BAR_STOOL.get());
                output.accept(ModItems.BROWN_BAR_STOOL.get());
                output.accept(ModItems.RED_BAR_STOOL.get());
                output.accept(ModItems.ORANGE_BAR_STOOL.get());
                output.accept(ModItems.YELLOW_BAR_STOOL.get());
                output.accept(ModItems.LIME_BAR_STOOL.get());
                output.accept(ModItems.GREEN_BAR_STOOL.get());
                output.accept(ModItems.CYAN_BAR_STOOL.get());
                output.accept(ModItems.LIGHT_BLUE_BAR_STOOL.get());
                output.accept(ModItems.BLUE_BAR_STOOL.get());
                output.accept(ModItems.PURPLE_BAR_STOOL.get());
                output.accept(ModItems.MAGENTA_BAR_STOOL.get());
                output.accept(ModItems.PINK_BAR_STOOL.get());

                output.accept(ModItems.CHALKBOARD.get());

                output.accept(ModItems.BASE_SANDWICH_BOARD.get());
                output.accept(ModItems.GRASS_SANDWICH_BOARD.get());
                output.accept(ModItems.ALLIUM_SANDWICH_BOARD.get());
                output.accept(ModItems.AZURE_BLUET_SANDWICH_BOARD.get());
                output.accept(ModItems.CORNFLOWER_SANDWICH_BOARD.get());
                output.accept(ModItems.ORCHID_SANDWICH_BOARD.get());
                output.accept(ModItems.PEONY_SANDWICH_BOARD.get());
                output.accept(ModItems.PINK_PETALS_SANDWICH_BOARD.get());
                output.accept(ModItems.PITCHER_PLANT_SANDWICH_BOARD.get());
                output.accept(ModItems.POPPY_SANDWICH_BOARD.get());
                output.accept(ModItems.SUNFLOWER_SANDWICH_BOARD.get());
                output.accept(ModItems.TORCHFLOWER_SANDWICH_BOARD.get());
                output.accept(ModItems.TULIP_SANDWICH_BOARD.get());
                output.accept(ModItems.WITHER_ROSE_SANDWICH_BOARD.get());

                output.accept(ModItems.STRING_LIGHTS_COLORLESS.get());
                output.accept(ModItems.STRING_LIGHTS_WHITE.get());
                output.accept(ModItems.STRING_LIGHTS_LIGHT_GRAY.get());
                output.accept(ModItems.STRING_LIGHTS_GRAY.get());
                output.accept(ModItems.STRING_LIGHTS_BLACK.get());
                output.accept(ModItems.STRING_LIGHTS_BROWN.get());
                output.accept(ModItems.STRING_LIGHTS_RED.get());
                output.accept(ModItems.STRING_LIGHTS_ORANGE.get());
                output.accept(ModItems.STRING_LIGHTS_YELLOW.get());
                output.accept(ModItems.STRING_LIGHTS_LIME.get());
                output.accept(ModItems.STRING_LIGHTS_GREEN.get());
                output.accept(ModItems.STRING_LIGHTS_CYAN.get());
                output.accept(ModItems.STRING_LIGHTS_LIGHT_BLUE.get());
                output.accept(ModItems.STRING_LIGHTS_BLUE.get());
                output.accept(ModItems.STRING_LIGHTS_PURPLE.get());
                output.accept(ModItems.STRING_LIGHTS_MAGENTA.get());
                output.accept(ModItems.STRING_LIGHTS_PINK.get());

                output.accept(ModItems.YSBB_PAINTING.get());
                output.accept(ModItems.TARTARIC_ACID_PAINTING.get());
                output.accept(ModItems.CR019_PAINTING.get());
                output.accept(ModItems.UNKNOWN_PAINTING.get());
                output.accept(ModItems.MASTER_MARISA_PAINTING.get());
                output.accept(ModItems.SON_OF_MAN_PAINTING.get());
                output.accept(ModItems.DAVID_PAINTING.get());
                output.accept(ModItems.GIRL_WITH_PEARL_EARRING_PAINTING.get());
                output.accept(ModItems.STARRY_NIGHT_PAINTING.get());
                output.accept(ModItems.VAN_GOGH_SELF_PORTRAIT_PAINTING.get());
                output.accept(ModItems.FATHER_PAINTING.get());
                output.accept(ModItems.GREAT_WAVE_PAINTING.get());
                output.accept(ModItems.MONA_LISA_PAINTING.get());
                output.accept(ModItems.MONDRIAN_PAINTING.get());

                output.accept(ModItems.BAR_COUNTER.get());
                output.accept(ModItems.STEPLADDER.get());
            }).build());
}
