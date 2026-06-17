package com.github.ysbbbbbb.kaleidoscopetavern.game.tap;

import com.github.ysbbbbbb.kaleidoscopetavern.api.blockentity.ITapBehavior;
import com.github.ysbbbbbb.kaleidoscopetavern.game.tap.impl.WaterloggedBehavior;
import com.google.common.collect.Maps;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class TapBehaviorManager {
    private static final Map<Block, ITapBehavior> BEHAVIOR_MAP = Maps.newHashMap();
    /**
     * 很特殊的 Behavior，不走默认的注册机制
     */
    private static final WaterloggedBehavior WATERLOGGED_BEHAVIOR = new WaterloggedBehavior();

    public static void register(Block block, ITapBehavior behavior) {
        BEHAVIOR_MAP.put(block, behavior);
    }

    public static boolean contains(BlockState sourceState) {
        boolean contains = BEHAVIOR_MAP.containsKey(sourceState.getBlock());
        if (!contains && hasWaterlogged(sourceState)) {
            return true;
        }
        return contains;
    }

    @Nullable
    public static ITapBehavior get(BlockState sourceState) {
        ITapBehavior behavior = BEHAVIOR_MAP.get(sourceState.getBlock());
        if (behavior == null && hasWaterlogged(sourceState)) {
            return WATERLOGGED_BEHAVIOR;
        }
        return behavior;
    }

    private static boolean hasWaterlogged(BlockState sourceState) {
        return sourceState.hasProperty(BlockStateProperties.WATERLOGGED)
               && sourceState.getValue(BlockStateProperties.WATERLOGGED);
    }
}
