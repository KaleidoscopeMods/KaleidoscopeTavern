package com.github.ysbbbbbb.kaleidoscopetavern.init;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.item.BarStoolBlockItem;
import com.github.ysbbbbbb.kaleidoscopetavern.item.SofaBlockItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, KaleidoscopeTavern.MOD_ID);

    // 沙发
    public static RegistryObject<Item> WHITE_SOFA = ITEMS.register("white_sofa", () -> new SofaBlockItem(ModBlocks.WHITE_SOFA));
    public static RegistryObject<Item> LIGHT_GRAY_SOFA = ITEMS.register("light_gray_sofa", () -> new SofaBlockItem(ModBlocks.LIGHT_GRAY_SOFA));
    public static RegistryObject<Item> GRAY_SOFA = ITEMS.register("gray_sofa", () -> new SofaBlockItem(ModBlocks.GRAY_SOFA));
    public static RegistryObject<Item> BLACK_SOFA = ITEMS.register("black_sofa", () -> new SofaBlockItem(ModBlocks.BLACK_SOFA));
    public static RegistryObject<Item> BROWN_SOFA = ITEMS.register("brown_sofa", () -> new SofaBlockItem(ModBlocks.BROWN_SOFA));
    public static RegistryObject<Item> RED_SOFA = ITEMS.register("red_sofa", () -> new SofaBlockItem(ModBlocks.RED_SOFA));
    public static RegistryObject<Item> ORANGE_SOFA = ITEMS.register("orange_sofa", () -> new SofaBlockItem(ModBlocks.ORANGE_SOFA));
    public static RegistryObject<Item> YELLOW_SOFA = ITEMS.register("yellow_sofa", () -> new SofaBlockItem(ModBlocks.YELLOW_SOFA));
    public static RegistryObject<Item> LIME_SOFA = ITEMS.register("lime_sofa", () -> new SofaBlockItem(ModBlocks.LIME_SOFA));
    public static RegistryObject<Item> GREEN_SOFA = ITEMS.register("green_sofa", () -> new SofaBlockItem(ModBlocks.GREEN_SOFA));
    public static RegistryObject<Item> CYAN_SOFA = ITEMS.register("cyan_sofa", () -> new SofaBlockItem(ModBlocks.CYAN_SOFA));
    public static RegistryObject<Item> LIGHT_BLUE_SOFA = ITEMS.register("light_blue_sofa", () -> new SofaBlockItem(ModBlocks.LIGHT_BLUE_SOFA));
    public static RegistryObject<Item> BLUE_SOFA = ITEMS.register("blue_sofa", () -> new SofaBlockItem(ModBlocks.BLUE_SOFA));
    public static RegistryObject<Item> PURPLE_SOFA = ITEMS.register("purple_sofa", () -> new SofaBlockItem(ModBlocks.PURPLE_SOFA));
    public static RegistryObject<Item> MAGENTA_SOFA = ITEMS.register("magenta_sofa", () -> new SofaBlockItem(ModBlocks.MAGENTA_SOFA));
    public static RegistryObject<Item> PINK_SOFA = ITEMS.register("pink_sofa", () -> new SofaBlockItem(ModBlocks.PINK_SOFA));

    // 高脚凳
    public static RegistryObject<Item> WHITE_BAR_STOOL = ITEMS.register("white_bar_stool", () -> new BarStoolBlockItem(ModBlocks.WHITE_BAR_STOOL));
    public static RegistryObject<Item> LIGHT_GRAY_BAR_STOOL = ITEMS.register("light_gray_bar_stool", () -> new BarStoolBlockItem(ModBlocks.LIGHT_GRAY_BAR_STOOL));
    public static RegistryObject<Item> GRAY_BAR_STOOL = ITEMS.register("gray_bar_stool", () -> new BarStoolBlockItem(ModBlocks.GRAY_BAR_STOOL));
    public static RegistryObject<Item> BLACK_BAR_STOOL = ITEMS.register("black_bar_stool", () -> new BarStoolBlockItem(ModBlocks.BLACK_BAR_STOOL));
    public static RegistryObject<Item> BROWN_BAR_STOOL = ITEMS.register("brown_bar_stool", () -> new BarStoolBlockItem(ModBlocks.BROWN_BAR_STOOL));
    public static RegistryObject<Item> RED_BAR_STOOL = ITEMS.register("red_bar_stool", () -> new BarStoolBlockItem(ModBlocks.RED_BAR_STOOL));
    public static RegistryObject<Item> ORANGE_BAR_STOOL = ITEMS.register("orange_bar_stool", () -> new BarStoolBlockItem(ModBlocks.ORANGE_BAR_STOOL));
    public static RegistryObject<Item> YELLOW_BAR_STOOL = ITEMS.register("yellow_bar_stool", () -> new BarStoolBlockItem(ModBlocks.YELLOW_BAR_STOOL));
    public static RegistryObject<Item> LIME_BAR_STOOL = ITEMS.register("lime_bar_stool", () -> new BarStoolBlockItem(ModBlocks.LIME_BAR_STOOL));
    public static RegistryObject<Item> GREEN_BAR_STOOL = ITEMS.register("green_bar_stool", () -> new BarStoolBlockItem(ModBlocks.GREEN_BAR_STOOL));
    public static RegistryObject<Item> CYAN_BAR_STOOL = ITEMS.register("cyan_bar_stool", () -> new BarStoolBlockItem(ModBlocks.CYAN_BAR_STOOL));
    public static RegistryObject<Item> LIGHT_BLUE_BAR_STOOL = ITEMS.register("light_blue_bar_stool", () -> new BarStoolBlockItem(ModBlocks.LIGHT_BLUE_BAR_STOOL));
    public static RegistryObject<Item> BLUE_BAR_STOOL = ITEMS.register("blue_bar_stool", () -> new BarStoolBlockItem(ModBlocks.BLUE_BAR_STOOL));
    public static RegistryObject<Item> PURPLE_BAR_STOOL = ITEMS.register("purple_bar_stool", () -> new BarStoolBlockItem(ModBlocks.PURPLE_BAR_STOOL));
    public static RegistryObject<Item> MAGENTA_BAR_STOOL = ITEMS.register("magenta_bar_stool", () -> new BarStoolBlockItem(ModBlocks.MAGENTA_BAR_STOOL));
    public static RegistryObject<Item> PINK_BAR_STOOL = ITEMS.register("pink_bar_stool", () -> new BarStoolBlockItem(ModBlocks.PINK_BAR_STOOL));

    // 黑板
    public static RegistryObject<Item> CHALKBOARD = ITEMS.register("chalkboard", () -> new BlockItem(ModBlocks.CHALKBOARD.get(), new Item.Properties()));
}
