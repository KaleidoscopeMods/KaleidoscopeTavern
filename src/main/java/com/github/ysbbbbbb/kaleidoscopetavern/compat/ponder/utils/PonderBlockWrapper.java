package com.github.ysbbbbbb.kaleidoscopetavern.compat.ponder.utils;

import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.api.scene.Selection;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public class PonderBlockWrapper {
    protected final SceneBuilder scene;
    protected final SceneBuildingUtil util;
    protected final Selection selection;
    public final BlockPos pos;

    public PonderBlockWrapper(BlockPos pos, SceneBuilder scene, SceneBuildingUtil util) {
        this.scene = scene;
        this.util = util;
        this.pos = pos;
        this.selection = util.select().position(pos);
    }

    public PonderBlockWrapper(int x, int y, int z, SceneBuilder scene, SceneBuildingUtil util) {
        this(util.grid().at(x,y,z), scene, util);
    }

    public Selection selection() {
        return selection;
    }

    public Vec3 blockSurface(Direction face) { return util.vector().blockSurface(pos, face); }

    public void setBlock(BlockState blockState, boolean spawnParticles) {
        scene.world().setBlock(pos, blockState, spawnParticles);
    }

    public void modifyBlock(UnaryOperator<BlockState> stateFunc, boolean spawnParticles) {
        scene.world().modifyBlock(pos, stateFunc, spawnParticles);
    }

    public <T extends BlockEntity> void modifyBlockEntity(Class<T> beType, Consumer<T> consumer) {
        scene.world().modifyBlockEntity(pos, beType, consumer);
    }

    public void showSection(Direction direction) {
        scene.world().showSection(selection(), direction);
    }

    public void hideSection(Direction direction) {
        scene.world().hideSection(selection(), direction);
    }
}
