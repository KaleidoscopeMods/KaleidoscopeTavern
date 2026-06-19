package com.github.ysbbbbbb.kaleidoscopetavern.block.mixology;

import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.mixology.SignatureCocktailBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.datamap.data.DrinkEffectData;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopetavern.item.SignatureCocktailBlockItem;
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

public class SignatureCocktailBlock extends CocktailBlock implements EntityBlock {
    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new SignatureCocktailBlockEntity(pPos, pState);
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        if (SignatureCocktailBlockItem.hasEffects(pStack) && pLevel.getBlockEntity(pPos) instanceof SignatureCocktailBlockEntity ordinary) {
            List<DrinkEffectData.Entry> effects = SignatureCocktailBlockItem.getEffects(pStack);
            int color = SignatureCocktailBlockItem.getColor(pStack);
            ordinary.setEffects(effects);
            ordinary.setColor(color);
            ordinary.refresh();
        }
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        List<ItemStack> stacks = Lists.newArrayList(super.getDrops(state, params));
        BlockEntity blockEntity = params.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (blockEntity instanceof SignatureCocktailBlockEntity be) {
            ItemStack instance = ModItems.SIGNATURE_COCKTAIL.get().getDefaultInstance();
            SignatureCocktailBlockItem.setEffects(instance, be.getEffects());
            SignatureCocktailBlockItem.setColor(instance, be.getColor());
            stacks.add(instance);
        }
        return stacks;
    }
}
