package com.github.ysbbbbbb.kaleidoscopetavern.game.grape;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.api.event.PlantGrapeEvent;
import com.github.ysbbbbbb.kaleidoscopetavern.block.plant.TrellisBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = KaleidoscopeTavern.MOD_ID)
public class NormalGrapePlant {
    @SubscribeEvent
    public static void onPlant(PlantGrapeEvent event) {
        Level level = event.level();
        BlockPos pos = event.pos();
        Player player = event.player();
        ItemStack itemInHand = player.getItemInHand(event.hand());
        BlockState state = event.state();

        BlockState belowState = level.getBlockState(pos.below());
        if (belowState.is(BlockTags.DIRT) || belowState.is(BlockTags.GRASS_BLOCKS)) {
            BlockState plantedState = ModBlocks.GRAPEVINE_TRELLIS.get()
                    .defaultBlockState()
                    .setValue(TrellisBlock.WATERLOGGED, state.getValue(TrellisBlock.WATERLOGGED));
            level.setBlockAndUpdate(pos, plantedState);
            level.playSound(null, pos, SoundEvents.CROP_PLANTED, SoundSource.BLOCKS);
            if (!player.isCreative()) {
                itemInHand.shrink(1);
            }
            // 不再触发后续事件
            event.setCanceled(true);
        }
    }
}
