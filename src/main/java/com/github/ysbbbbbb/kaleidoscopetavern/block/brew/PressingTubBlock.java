package com.github.ysbbbbbb.kaleidoscopetavern.block.brew;

import com.github.ysbbbbbb.kaleidoscopetavern.api.blockentity.IPressingTub;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.brew.PressingTubBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.crafting.recipe.PressingTubRecipe;
import com.github.ysbbbbbb.kaleidoscopetavern.crafting.recipe.PressingTubRecipe.PressingTubRecipeCache;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@SuppressWarnings("deprecation")
public class PressingTubBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final VoxelShape SHAPE = Shapes.join(
            Block.box(0, 0, 0, 16, 8, 16),
            Block.box(2, 2, 2, 14, 8, 14),
            BooleanOp.ONLY_FIRST
    );

    public PressingTubBlock() {
        super(Properties.of()
                .mapColor(MapColor.WOOD)
                .instrument(NoteBlockInstrument.GUITAR)
                .strength(0.8F)
                .sound(SoundType.WOOD)
                .ignitedByLava());
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(WATERLOGGED, false));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hitResult) {
        if (!(level.getBlockEntity(pos) instanceof IPressingTub pressingTub)) {
            return InteractionResult.PASS;
        }

        // 如果是空手，尝试取出
        ItemStack itemInHand = player.getItemInHand(hand);
        if (itemInHand.isEmpty()) {
            if (pressingTub.removeIngredient(player)) {
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        }

        // 然后尝试能否取出
        if (pressingTub.getResult(player, itemInHand)) {
            return InteractionResult.SUCCESS;
        }

        // 最后尝试放入
        if (pressingTub.addIngredient(itemInHand)) {
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (entity instanceof LivingEntity livingEntity) {
            if (!(level.getBlockEntity(pos) instanceof IPressingTub pressingTub)) {
                return;
            }
            if (pressingTub.press(livingEntity, fallDistance)) {
                return;
            }
        }
        // 如果压榨不成功，则正常掉落伤害
        super.fallOn(level, state, pos, entity, fallDistance);
    }

    @Override
    public List<ItemStack> getDrops(BlockState pState, LootParams.Builder pParams) {
        List<ItemStack> stacks = super.getDrops(pState, pParams);
        BlockEntity blockEntity = pParams.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (blockEntity instanceof IPressingTub pressingTub) {
            ItemStack drop = pressingTub.getItems().getStackInSlot(0).copy();
            if (!drop.isEmpty()) {
                stacks.add(drop);
            }
        }
        return stacks;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState,
                                  LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new PressingTubBlockEntity(pos, state);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState blockState) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos blockPos) {
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        if (blockEntity instanceof IPressingTub pressingTub) {
            return pressingTub.getLiquidAmount();
        }
        return 0;
    }

    @Override
    public ItemStack pickupBlock(LevelAccessor level, BlockPos pos, BlockState state) {
        ItemStack stack = SimpleWaterloggedBlock.super.pickupBlock(level, pos, state);
        if (!stack.isEmpty()) {
            return stack;
        }
        if (!(level instanceof ServerLevel serverLevel)) {
            return stack;
        }

        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (!(blockEntity instanceof PressingTubBlockEntity pressingTub)) {
            return stack;
        }
        if (pressingTub.getLiquidAmount() < IPressingTub.MAX_LIQUID_AMOUNT) {
            return stack;
        }

        // 检查是否是铁桶容器
        PressingTubRecipeCache cachedRecipe = pressingTub.getCachedRecipe();
        if (cachedRecipe == null) {
            return stack;
        }
        var recipeOpt = serverLevel.getRecipeManager().byKey(cachedRecipe.id());
        if (recipeOpt.isEmpty()) {
            return stack;
        }
        if (!(recipeOpt.get() instanceof PressingTubRecipe pressingTubRecipe)) {
            return stack;
        }
        Ingredient carrier = pressingTubRecipe.getCarrier();
        if (!carrier.test(Items.BUCKET.getDefaultInstance())) {
            return stack;
        }
        pressingTub.clearData();
        pressingTub.refresh();
        return pressingTubRecipe.getResult().copy();
    }
}
