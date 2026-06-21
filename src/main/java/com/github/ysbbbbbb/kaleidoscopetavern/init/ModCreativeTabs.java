package com.github.ysbbbbbb.kaleidoscopetavern.init;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.item.BottleBlockItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public interface ModCreativeTabs {
    DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, KaleidoscopeTavern.MOD_ID);

    RegistryObject<CreativeModeTab> TAVERN_MAIN_TAB = TABS.register("tavern_main", () -> CreativeModeTab.builder()
            .title(Component.translatable("item_group.kaleidoscope_tavern.tavern_main.name"))
            .icon(ModItems.WINE.get()::getDefaultInstance)
            .displayItems((par, output) -> {
                output.accept(ModItems.GRAPEVINE.get());
                output.accept(ModItems.GRAPE.get());
                output.accept(ModItems.ICE_GRAPE.get());
                output.accept(ModItems.GOLD_GRAPE.get());
                output.accept(ModItems.GREEN_GRAPE.get());
                output.accept(ModItems.TRELLIS.get());

                output.accept(ModItems.PRESSING_TUB.get());
                output.accept(ModItems.BARREL.get());
                output.accept(ModItems.TAP.get());
                output.accept(ModItems.SHAKER.get());

                output.accept(ModItems.GRAPE_BUCKET.get());
                output.accept(ModItems.ICE_GRAPE_BUCKET.get());
                output.accept(ModItems.GOLD_GRAPE_BUCKET.get());
                output.accept(ModItems.GREEN_GRAPE_BUCKET.get());
                output.accept(ModItems.SWEET_BERRIES_BUCKET.get());
                output.accept(ModItems.GLOW_BERRIES_BUCKET.get());

                output.accept(ModItems.EMPTY_BOTTLE.get());
                output.accept(ModItems.EMPTY_GLASSWARE.get());
                output.accept(ModItems.MOLOTOV.get());
                output.accept(ModItems.WATERMELON_JUICE.get());

                output.accept(BottleBlockItem.getMaxLevelDrink(ModItems.VINEGAR));

                // 葡萄桶
                output.accept(BottleBlockItem.getMaxLevelDrink(ModItems.WINE));
                output.accept(BottleBlockItem.getMaxLevelDrink(ModItems.SAKURA_WINE));
                output.accept(BottleBlockItem.getMaxLevelDrink(ModItems.CHAMPAGNE));
                output.accept(BottleBlockItem.getMaxLevelDrink(ModItems.BRANDY));
                output.accept(BottleBlockItem.getMaxLevelDrink(ModItems.CARIGNAN));

                // 冰葡萄桶
                output.accept(BottleBlockItem.getMaxLevelDrink(ModItems.ICE_WINE));
                output.accept(BottleBlockItem.getMaxLevelDrink(ModItems.POLARIS_SWEET_WHITE));
                output.accept(BottleBlockItem.getMaxLevelDrink(ModItems.SHERRY));
                output.accept(BottleBlockItem.getMaxLevelDrink(ModItems.MOTHER_SNOW));

                // 黄金葡萄桶
                output.accept(BottleBlockItem.getMaxLevelDrink(ModItems.MINERS_STAR));
                output.accept(BottleBlockItem.getMaxLevelDrink(ModItems.HONEY_WINE));
                output.accept(BottleBlockItem.getMaxLevelDrink(ModItems.MADAME_SHEXIANG));
                output.accept(BottleBlockItem.getMaxLevelDrink(ModItems.SUNSET_GLOW));

                // 青提桶
                output.accept(BottleBlockItem.getMaxLevelDrink(ModItems.SAUVIGNON_BLANC_DRY_WHITE));
                output.accept(BottleBlockItem.getMaxLevelDrink(ModItems.RIESLING_DRY_WHITE));

                // 发光浆果桶
                output.accept(BottleBlockItem.getMaxLevelDrink(ModItems.LUMINOUS_BRIDE));
                output.accept(BottleBlockItem.getMaxLevelDrink(ModItems.GLOWFLOWER_BREW));

                // 甜浆果桶
                output.accept(BottleBlockItem.getMaxLevelDrink(ModItems.PLUM_WINE));
                output.accept(BottleBlockItem.getMaxLevelDrink(ModItems.SWEET_BERRY_WINE));
                output.accept(BottleBlockItem.getMaxLevelDrink(ModItems.RED_QUEEN));

                // 水桶
                output.accept(BottleBlockItem.getMaxLevelDrink(ModItems.VODKA));
                output.accept(BottleBlockItem.getMaxLevelDrink(ModItems.WHISKEY));
                output.accept(BottleBlockItem.getMaxLevelDrink(ModItems.RUM));

                // 鸡尾酒
                output.accept(ModItems.SIGNATURE_COCKTAIL.get());
                output.accept(ModItems.MYSTERY_COCKTAIL.get());
                output.accept(ModItems.WHITE_LADY.get());
                output.accept(ModItems.EMERALD.get());
                output.accept(ModItems.BRASS_HEART.get());
                output.accept(ModItems.GODFATHER.get());
                output.accept(ModItems.GRASSHOPPER.get());
                output.accept(ModItems.SCREWDRIVER.get());
                output.accept(ModItems.MOJITO.get());
                output.accept(ModItems.ALLIUM_GARDEN.get());
                output.accept(ModItems.DEPTH_CHARGE.get());
                output.accept(ModItems.NETHER_SPECIAL.get());
                output.accept(ModItems.BLOODY_MARY.get());
                output.accept(ModItems.SCULK_SPECIAL.get());
            }).build());

    RegistryObject<CreativeModeTab> TAVERN_DECO_TAB = TABS.register("tavern_deco", () -> CreativeModeTab.builder()
            .title(Component.translatable("item_group.kaleidoscope_tavern.tavern_deco.name"))
            .icon(ModItems.STEPLADDER.get()::getDefaultInstance)
            .withTabsBefore(TAVERN_MAIN_TAB.getId())
            .displayItems((par, output) -> {
                output.accept(ModItems.BAR_CABINET.get());
                output.accept(ModItems.GLASS_BAR_CABINET.get());
                output.accept(ModItems.CELLAR_CABINET.get());
                output.accept(ModItems.BAR_COUNTER.get());
                output.accept(ModItems.TABLE.get());
                output.accept(ModItems.TILTED_RACK.get());
                output.accept(ModItems.CIRCULAR_RACK.get());
                output.accept(ModItems.HOLDER.get());
                output.accept(ModItems.GLASSWARE_HOLDER.get());
                output.accept(ModItems.STEPLADDER.get());

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

                output.accept(ModItems.BELL_PENDANT_LAMP.get());
                output.accept(ModItems.YELLOW_PENDANT_LAMP.get());
                output.accept(ModItems.BLUE_PENDANT_LAMP.get());

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

                output.accept(ModItems.BASE_SANDWICH_BOARD.get());
                output.accept(ModItems.GRASS_SANDWICH_BOARD.get());
                output.accept(ModItems.PINK_PETALS_SANDWICH_BOARD.get());
                output.accept(ModItems.SUNFLOWER_SANDWICH_BOARD.get());
                output.accept(ModItems.POPPY_SANDWICH_BOARD.get());
                output.accept(ModItems.CORNFLOWER_SANDWICH_BOARD.get());
                output.accept(ModItems.ORCHID_SANDWICH_BOARD.get());
                output.accept(ModItems.ALLIUM_SANDWICH_BOARD.get());
                output.accept(ModItems.AZURE_BLUET_SANDWICH_BOARD.get());
                output.accept(ModItems.TULIP_SANDWICH_BOARD.get());
                output.accept(ModItems.TORCHFLOWER_SANDWICH_BOARD.get());
                output.accept(ModItems.WITHER_ROSE_SANDWICH_BOARD.get());
                output.accept(ModItems.PEONY_SANDWICH_BOARD.get());
                output.accept(ModItems.PITCHER_PLANT_SANDWICH_BOARD.get());

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

                output.accept(ModItems.CHALKBOARD.get());

                // 香薰
                output.accept(ModItems.SAKURA_INCENSE.get());
                output.accept(ModItems.PINE_INCENSE.get());
                output.accept(ModItems.GINKGO_INCENSE.get());
                output.accept(ModItems.SPORE_INCENSE.get());
                output.accept(ModItems.CATNIP_INCENSE.get());
                output.accept(ModItems.SNOW_INCENSE.get());
                output.accept(ModItems.BUTTERFLY_INCENSE.get());
                output.accept(ModItems.FIREFLY_INCENSE.get());
            }).build());
}
