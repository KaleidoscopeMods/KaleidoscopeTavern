package com.github.ysbbbbbb.kaleidoscopetavern.block.plant;

import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ToolActions;

@SuppressWarnings("deprecation")
public class WildGrapevineBlock extends GrowingPlantHeadBlock implements BonemealableBlock {
    /**
     * 被剪刀修剪过后，无法再随机生长了，直到被重新种植
     */
    public static BooleanProperty SHEARED = BooleanProperty.create("sheared");

    private static final VoxelShape SHAPE = Block.box(1, 0, 1, 15, 16, 15);
    private static final BlockBehaviour.Properties PROPERTIES = Properties.of()
            .mapColor(MapColor.PLANT)
            .randomTicks()
            .noCollission()
            .instabreak()
            .sound(SoundType.CAVE_VINES)
            .pushReaction(PushReaction.DESTROY);

    public WildGrapevineBlock() {
        super(PROPERTIES, Direction.DOWN, SHAPE, false, 0.1);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(AGE, 0)
                .setValue(SHEARED, false));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hitResult) {
        ItemStack item = player.getItemInHand(hand);
        if (item.canPerformAction(ToolActions.SHEARS_CARVE)) {
            if (state.getValue(SHEARED)) {
                return InteractionResult.CONSUME;
            } else {
                level.setBlockAndUpdate(pos, state.setValue(SHEARED, true));
                item.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
                player.playSound(SoundEvents.SHEEP_SHEAR);
                return InteractionResult.SUCCESS;
            }
        }
        return super.use(state, level, pos, player, hand, hitResult);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(SHEARED);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos relative = pos.relative(this.growthDirection.getOpposite());
        BlockState relativeState = level.getBlockState(relative);
        return relativeState.is(this.getHeadBlock())
               || relativeState.is(this.getBodyBlock())
               || this.canAttachTo(relativeState)
               || relativeState.isFaceSturdy(level, relative, this.growthDirection);
    }

    @Override
    protected boolean canAttachTo(BlockState state) {
        // 树叶不属于 SupportType.FULL，故需要特殊判断一下
        return state.is(BlockTags.LEAVES);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        // 如果被剪刀修剪过了，就不再随机生长了，直到被重新种植
        if (state.getValue(SHEARED)) {
            return;
        }
        super.randomTick(state, level, pos, random);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient) {
        // 只有当没有被剪刀修剪过，才可以使用骨粉生长
        return !state.getValue(SHEARED) && super.isValidBonemealTarget(level, pos, state, isClient);
    }

    @Override
    protected int getBlocksToGrowWhenBonemealed(RandomSource randomSource) {
        return 1;
    }

    @Override
    protected boolean canGrowInto(BlockState state) {
        return state.isAir();
    }

    @Override
    protected Block getBodyBlock() {
        return ModBlocks.WILD_GRAPEVINE_PLANT.get();
    }
}
