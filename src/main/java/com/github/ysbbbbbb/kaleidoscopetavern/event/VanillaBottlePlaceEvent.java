package com.github.ysbbbbbb.kaleidoscopetavern.event;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.brew.PotionBottleBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import static com.github.ysbbbbbb.kaleidoscopetavern.config.GeneralConfig.*;
import static com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks.*;

@EventBusSubscriber(modid = KaleidoscopeTavern.MOD_ID)
public class VanillaBottlePlaceEvent {
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        if (!player.isShiftKeyDown()) {
            return;
        }

        ItemStack stack = event.getItemStack();
        Placement placement = getPlacement(stack);
        if (placement == null || !placement.enabled()) {
            return;
        }

        BlockPlaceContext context = new BlockPlaceContext(player, event.getHand(), stack, event.getHitVec());
        if (!context.canPlace()) {
            return;
        }

        Level level = event.getLevel();
        BlockState state = placement.block().getStateForPlacement(context);
        if (state == null) {
            return;
        }

        BlockPos placePos = context.getClickedPos();
        if (!state.canSurvive(level, placePos)) {
            return;
        }

        event.setCanceled(true);
        event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide));

        if (level.isClientSide) {
            return;
        }

        if (!level.setBlock(placePos, state, Block.UPDATE_ALL)) {
            return;
        }

        if (placement.storePotionStack() && level.getBlockEntity(placePos) instanceof PotionBottleBlockEntity be) {
            be.setPotionStack(stack);
        }

        level.playSound(null, placePos, SoundEvents.GLASS_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }
    }

    private static Placement getPlacement(ItemStack stack) {
        if (stack.is(Items.POTION)) {
            var potion = stack.get(DataComponents.POTION_CONTENTS);
            if (potion == null || potion.is(Potions.WATER)) {
                return new Placement(WATER_BOTTLE.get(), WATER_BOTTLE_PLACEMENT.get(), false);
            }
            return new Placement(POTION_BOTTLE.get(), POTION_BOTTLE_PLACEMENT.get(), true);
        }

        if (stack.is(Items.HONEY_BOTTLE)) {
            return new Placement(HONEY_BOTTLE.get(), HONEY_BOTTLE_PLACEMENT.get(), false);
        }

        if (stack.is(Items.DRAGON_BREATH)) {
            return new Placement(DRAGON_BREATH_BOTTLE.get(), DRAGON_BREATH_BOTTLE_PLACEMENT.get(), false);
        }

        if (stack.is(Items.EXPERIENCE_BOTTLE)) {
            return new Placement(XP_BOTTLE.get(), EXPERIENCE_BOTTLE_PLACEMENT.get(), false);
        }

        return null;
    }

    private record Placement(Block block, boolean enabled, boolean storePotionStack) {
    }
}
