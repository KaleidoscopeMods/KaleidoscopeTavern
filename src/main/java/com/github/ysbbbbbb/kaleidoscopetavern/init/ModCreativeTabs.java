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
            }).build());
}
