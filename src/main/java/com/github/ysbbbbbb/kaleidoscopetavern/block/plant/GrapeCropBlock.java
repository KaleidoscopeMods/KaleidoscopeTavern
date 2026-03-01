package com.github.ysbbbbbb.kaleidoscopetavern.block.plant;

import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.ToolActions;

import java.util.Collections;
import java.util.List;

@SuppressWarnings("deprecation")
public class GrapeCropBlock extends Block implements BonemealableBlock {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_5;
    public static final int MAX_AGE = BlockStateProperties.MAX_AGE_5;
    public static final VoxelShape SHAPE = Block.box(2, 6, 2, 14, 16, 14);

    private final float growPerTickProbability;

    public GrapeCropBlock() {
        super(Properties.of()
                .mapColor(MapColor.PLANT)
                .noCollission()
                .randomTicks()
                .instabreak()
                .sound(SoundType.CROP)
                .offsetType(BlockBehaviour.OffsetType.XYZ)
                .pushReaction(PushReaction.DESTROY));
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(AGE, 0));
        this.growPerTickProbability = 0.25F;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hitResult) {
        // 只有成熟的葡萄才可以被剪刀收获
        ItemStack heldItem = player.getItemInHand(hand);
        if (heldItem.canPerformAction(ToolActions.SHEARS_HARVEST) && isMaxAge(state)) {
            level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
            // FIXME: 这里应该走 LootTable？
            Block.popResource(level, pos, new ItemStack(ModItems.GRAPE.get(), 3));
            heldItem.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
            player.playSound(SoundEvents.BEEHIVE_SHEAR);
            return InteractionResult.SUCCESS;
        }
        return super.use(state, level, pos, player, hand, hitResult);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return super.isRandomlyTicking(state) && state.getValue(AGE) < MAX_AGE;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (ForgeHooks.onCropsGrowPre(level, pos, state, random.nextDouble() < this.growPerTickProbability)) {
            level.setBlockAndUpdate(pos, state.cycle(AGE));
            ForgeHooks.onCropsGrowPost(level, pos, state);
        }
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState,
                                  LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (state.canSurvive(level, pos)) {
            return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
        }
        return Blocks.AIR.defaultBlockState();
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        // 上方必须是葡萄藤架
        var aboveState = level.getBlockState(pos.above());
        if (aboveState.getBlock() instanceof GrapevineTrellisBlock trellis) {
            return trellis.isMaxAge(aboveState);
        }
        return false;
    }

    public boolean isMaxAge(BlockState state) {
        return state.getValue(AGE) >= MAX_AGE;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient) {
        return !this.isMaxAge(state);
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        int newAge = Math.min(state.getValue(AGE) + random.nextInt(1, 3), MAX_AGE);
        level.setBlockAndUpdate(pos, state.setValue(AGE, newAge));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder lootParamsBuilder) {
        // 只有成熟的葡萄才会掉落物品
        if (isMaxAge(state)) {
            return super.getDrops(state, lootParamsBuilder);
        }
        return Collections.emptyList();
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        return ModItems.GRAPE.get().getDefaultInstance();
    }
}
