package com.github.ysbbbbbb.kaleidoscopetavern.compat.ponder.scene;

import com.github.ysbbbbbb.kaleidoscopetavern.block.brew.TapBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.brew.BarrelBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.brew.TapBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.compat.ponder.utils.PonderBlockWrapper;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModFluids;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.ParticleEmitter;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.api.scene.Selection;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

public class BarrelScenes {
    public static void introduction(SceneBuilder scene, SceneBuildingUtil util) {
        Level level = Minecraft.getInstance().level;
        if (level == null) {
            return;
        }

        scene.title("barrel_introduction", "");
        scene.configureBasePlate(0, 0, 5);
        scene.showBasePlate();

        Selection barrelSel = util.select().fromTo(1, 1, 1, 3, 3, 3);
        Selection ladderSel = util.select().fromTo(1, 1, 2, 1, 2, 2);
        PonderBlockWrapper barrel = new PonderBlockWrapper(2, 1, 2, scene, util);
        PonderBlockWrapper barrelTop = new PonderBlockWrapper(2, 3, 2, scene, util);
        PonderBlockWrapper tap = new PonderBlockWrapper(2, 2, 0, scene, util);
        PonderBlockWrapper bottle = new PonderBlockWrapper(2, 1, 0, scene, util);
        ItemStack grapeBucket = new ItemStack(ModItems.GRAPE_BUCKET.get());
        bottle.setBlock(ModBlocks.EMPTY_BOTTLE.get().defaultBlockState(), false);
        barrel.modifyBlockEntity(BarrelBlockEntity.class, (be) -> {
            be.setOpen(false);
            be.refresh();
        });

        scene.idle(20);

        scene.world().showSection(barrelSel, Direction.DOWN);
        tap.showSection(Direction.DOWN);
        scene.idle(4);
        scene.world().showSection(ladderSel, Direction.EAST);

        scene.idle(25);

        scene.overlay().showText(45).text("").pointAt(barrelTop.blockSurface(Direction.WEST)).placeNearTarget();
        scene.idle(5);
        scene.overlay().showControls(barrelTop.blockSurface(Direction.UP), Pointing.DOWN, 35).rightClick();
        scene.idle(5);
        barrel.modifyBlockEntity(BarrelBlockEntity.class, (be) -> {
            be.setOpen(true);
            be.refresh();
        });
        scene.idle(50);
        scene.addKeyframe();
        scene.idle(15);

        scene.overlay().showText(45).text("").pointAt(barrelTop.blockSurface(Direction.WEST)).placeNearTarget();
        scene.idle(5);
        scene.overlay().showControls(barrelTop.blockSurface(Direction.UP), Pointing.DOWN, 35).rightClick().withItem(grapeBucket.copy());
        scene.idle(5);
        barrel.modifyBlockEntity(BarrelBlockEntity.class, (be) -> {
            be.getFluid().fill(new FluidStack(ModFluids.GRAPE_JUICE.get(), 4000), IFluidHandler.FluidAction.EXECUTE);
            be.refresh();
        });
        scene.idle(50);
        scene.addKeyframe();
        scene.idle(15);

        scene.overlay().showText(45).text("").pointAt(barrelTop.blockSurface(Direction.WEST)).placeNearTarget();
        scene.idle(5);
        scene.overlay().showControls(barrelTop.blockSurface(Direction.UP), Pointing.DOWN, 25).rightClick();
        scene.idle(5);
        barrel.modifyBlockEntity(BarrelBlockEntity.class, (be) -> {
            be.setOpen(false);
            be.refresh();
        });
        scene.idle(45);
        scene.addKeyframe();
        scene.idle(15);

        scene.overlay().showText(60).text("").pointAt(barrelTop.blockSurface(Direction.WEST)).placeNearTarget();
        scene.idle(5);
        scene.overlay().showText(50).text("").placeNearTarget();
        scene.overlay().showControls(barrelTop.blockSurface(Direction.UP), Pointing.DOWN, 40).withItem(new ItemStack(Items.CLOCK));
        scene.idle(65);
        scene.addKeyframe();
        scene.idle(15);

        bottle.showSection(Direction.SOUTH);
        scene.idle(15);

        scene.overlay().showText(85).text("").pointAt(barrelTop.blockSurface(Direction.WEST)).placeNearTarget();
        scene.idle(5);
        scene.overlay().showControls(tap.blockSurface(Direction.UP), Pointing.DOWN, 25).rightClick();
        scene.idle(5);
        tap.modifyBlock((s) -> s.setValue(TapBlock.OPEN, true), false);
        tap.modifyBlockEntity(TapBlockEntity.class, (be) -> {
            be.setState(TapBlockEntity.TAKE_DRINK_STATE);
        });
        barrel.modifyBlockEntity(BarrelBlockEntity.class, (be) -> {
            be.doTapExtract(level, tap.pos);
        });
        ParticleEmitter emitter = scene.effects().simpleParticleEmitter(ParticleTypes.DRIPPING_DRIPSTONE_WATER, new Vec3(0, -0.01, 0));
        for (int i = 0; i < 42; i++) {
            scene.effects().emitParticles(tap.blockSurface(Direction.DOWN).relative(Direction.UP, 0.25), emitter, 1, 1);
            scene.idle(1);
        }
        scene.idle(38);
        bottle.setBlock(ModBlocks.WINE.get().defaultBlockState(), false);
        scene.idle(25);
    }
}
