package com.github.ysbbbbbb.kaleidoscopetavern.block.deco;

import com.github.ysbbbbbb.kaleidoscopetavern.block.AbstractStorageBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco.TiltedRackBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.init.tag.TagMod;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class TiltedRackBlock extends AbstractStorageBlock {
    public static final VoxelShape NORTH_SHAPE = Block.box(0, 0, 5, 16, 14, 15);
    public static final VoxelShape SOUTH_SHAPE = Block.box(0, 0, 1, 16, 14, 11);
    public static final VoxelShape EAST_SHAPE = Block.box(1, 0, 0, 11, 14, 16);
    public static final VoxelShape WEST_SHAPE = Block.box(5, 0, 0, 15, 14, 16);
    private static final MapCodec<TiltedRackBlock> CODEC = simpleCodec(p -> new TiltedRackBlock());

    public TiltedRackBlock() {
        super();
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(POWERED, false));
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
                                              Player player, InteractionHand hand, BlockHitResult hitResult) {
        return super.handleUse(state, level, pos, player, hand, hitResult);
    }

    @Override
    protected boolean blockListCheck(ItemStack stack) {
        return stack.is(TagMod.TILTED_RACK_BLOCKLIST);
    }

    @Override
    protected int getClickedSlot(Direction direction, BlockPos pos, BlockHitResult hitResult) {
        double localX = this.getLocalX(direction, pos, hitResult);
        if (localX < 1.0 / 3.0) {
            return 0;
        }
        if (localX < 2.0 / 3.0) {
            return 1;
        }
        return 2;
    }

    @Override
    protected Vec3 getShootPos(Direction direction, BlockPos pos, int slot) {
        direction = direction.getOpposite();
        Vec3 center = Vec3.atLowerCornerOf(pos).add(0.5, 0.875, 0.5);
        Vec3 scale = Vec3.atLowerCornerOf(direction.getNormal()).scale(0.5);
        return center.add(scale);
    }

    @Override
    protected Vec3 getMovement(Direction direction, BlockPos pos, int slot) {
        direction = direction.getOpposite();
        Vec3i normal = direction.getNormal();
        double factor = Math.random() + 0.5;
        return Vec3.atLowerCornerWithOffset(normal, 0, 0.75, 0)
                .scale(factor);
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TiltedRackBlockEntity(pos, state);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case SOUTH -> SOUTH_SHAPE;
            case EAST -> EAST_SHAPE;
            case WEST -> WEST_SHAPE;
            default -> NORTH_SHAPE;
        };
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }
}
