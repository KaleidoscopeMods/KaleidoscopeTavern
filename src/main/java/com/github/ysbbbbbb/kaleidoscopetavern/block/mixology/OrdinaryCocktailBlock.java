package com.github.ysbbbbbb.kaleidoscopetavern.block.mixology;

import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.mixology.OrdinaryCocktailBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.datamap.data.DrinkEffectData;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopetavern.item.OrdinaryCocktailBlockItem;
import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class OrdinaryCocktailBlock extends CocktailBlock implements EntityBlock {
    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new OrdinaryCocktailBlockEntity(pPos, pState);
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        if (OrdinaryCocktailBlockItem.hasEffects(pStack) && pLevel.getBlockEntity(pPos) instanceof OrdinaryCocktailBlockEntity ordinary) {
            List<DrinkEffectData.Entry> effects = OrdinaryCocktailBlockItem.getEffects(pStack);
            int color = OrdinaryCocktailBlockItem.getColor(pStack);
            ordinary.setEffects(effects);
            ordinary.setColor(color);
            ordinary.refresh();
        }
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        List<ItemStack> stacks = Lists.newArrayList(super.getDrops(state, params));
        BlockEntity blockEntity = params.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (blockEntity instanceof OrdinaryCocktailBlockEntity be) {
            ItemStack instance = ModItems.ORDINARY_COCKTAIL.get().getDefaultInstance();
            OrdinaryCocktailBlockItem.setEffects(instance, be.getEffects());
            OrdinaryCocktailBlockItem.setColor(instance, be.getColor());
            stacks.add(instance);
        }
        return stacks;
    }
}
