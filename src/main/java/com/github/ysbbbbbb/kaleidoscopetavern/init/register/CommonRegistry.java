package com.github.ysbbbbbb.kaleidoscopetavern.init.register;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.block.brew.BottleBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.block.dispenser.BottleBlockDispenseBehavior;
import com.github.ysbbbbbb.kaleidoscopetavern.game.tap.TapBehaviorManager;
import com.github.ysbbbbbb.kaleidoscopetavern.game.tap.impl.*;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopetavern.item.BottleBlockItem;
import com.github.ysbbbbbb.kaleidoscopetavern.network.NetworkHandler;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = KaleidoscopeTavern.MOD_ID)
public class CommonRegistry {
    @SubscribeEvent
    public static void onSetupEvent(FMLCommonSetupEvent event) {
        event.enqueueWork(NetworkHandler::init);
        event.enqueueWork(CommonRegistry::addComposter);
        event.enqueueWork(CommonRegistry::addTapBehavior);
        event.enqueueWork(CommonRegistry::addDispenserBehavior);
    }

    private static void addTapBehavior() {
        TapBehaviorManager.register(ModBlocks.BARREL.get(), new BarrelTapBehavior());
        TapBehaviorManager.register(Blocks.WATER_CAULDRON, new WaterCauldronTapBehavior());
        TapBehaviorManager.register(Blocks.LAVA_CAULDRON, new LavaCauldronTapBehavior());
        TapBehaviorManager.register(Blocks.BEE_NEST, new BeehiveTapBehavior());
        TapBehaviorManager.register(Blocks.BEEHIVE, new BeehiveTapBehavior());
        TapBehaviorManager.register(Blocks.DRAGON_HEAD, new DragonHeadTapBehavior());
        TapBehaviorManager.register(Blocks.DRAGON_WALL_HEAD, new DragonHeadTapBehavior());
        TapBehaviorManager.register(Blocks.MELON, new WatermelonTapBehavior());
    }

    private static void addComposter() {
        ComposterBlock.COMPOSTABLES.put(ModItems.GRAPEVINE.get(), 0.25F);
        ComposterBlock.COMPOSTABLES.put(ModItems.GRAPE.get(), 0.5F);
        ComposterBlock.COMPOSTABLES.put(ModItems.ICE_GRAPE.get(), 0.5F);
        ComposterBlock.COMPOSTABLES.put(ModItems.GOLD_GRAPE.get(), 0.5F);
        ComposterBlock.COMPOSTABLES.put(ModItems.GREEN_GRAPE.get(), 0.5F);
    }

    private static void addDispenserBehavior() {
        // 遍历物品列表，注册所有的 Bottle Block 子类方块
        ForgeRegistries.BLOCKS.getValues().forEach(block -> {
            if (block instanceof BottleBlock) {
                if (block.asItem() instanceof BottleBlockItem blockItem) {
                    DispenserBlock.registerBehavior(blockItem, new BottleBlockDispenseBehavior());
                }
            }
        });
    }
}
