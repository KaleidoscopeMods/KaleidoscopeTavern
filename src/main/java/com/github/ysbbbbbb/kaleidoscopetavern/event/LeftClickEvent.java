package com.github.ysbbbbbb.kaleidoscopetavern.event;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopetavern.item.ShakerItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = KaleidoscopeTavern.MOD_ID)
public class LeftClickEvent {
    @SubscribeEvent
    public static void onLeftClickItem(PlayerInteractEvent.LeftClickEmpty event) {
        Player player = event.getEntity();
        if (player.isSecondaryUseActive() && event.getHand() == InteractionHand.MAIN_HAND) {
            ItemStack stack = player.getMainHandItem();
            if (stack.is(ModItems.SHAKER.get()) && ShakerItem.hasStorage(stack)) {
                ShakerItem.removeAll(stack);
                player.playSound(SoundEvents.BOTTLE_FILL);
            }
        }
    }
}
