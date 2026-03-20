package com.github.ysbbbbbb.kaleidoscopetavern.compat.ponder.scene;

import com.github.ysbbbbbb.kaleidoscopetavern.block.plant.GrapeCropBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.block.plant.GrapevineTrellisBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.block.plant.WildGrapevineBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.block.properties.TrellisType;
import com.github.ysbbbbbb.kaleidoscopetavern.compat.ponder.utils.PonderBlockWrapper;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.element.ElementLink;
import net.createmod.ponder.api.element.EntityElement;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.api.scene.Selection;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;


public class GrapevineScenes {
    public static void wildGeneration(SceneBuilder scene, SceneBuildingUtil util) {
        Level level = Minecraft.getInstance().level;
        if (level == null) {
            return;
        }

        scene.title("grapevine_wild_generation", "");
        scene.configureBasePlate(0, 0, 5);
        scene.showBasePlate();

        Selection fullSel = util.select().everywhere().substract(util.select().position(0, 5, 0));
        PonderBlockWrapper vine = new PonderBlockWrapper(1, 2, 0, scene, util);
        PonderBlockWrapper top = new PonderBlockWrapper(0, 5, 0, scene, util);
        ItemStack vineStack = new ItemStack(ModItems.GRAPEVINE.get());
        scene.world().showSection(fullSel, Direction.UP);

        scene.idle(25);

        scene.overlay().showText(140).text("").placeNearTarget();
        scene.idle(20);
        scene.rotateCameraY(120);
        scene.idle(45);
        scene.rotateCameraY(120);
        scene.idle(45);
        scene.rotateCameraY(135);
        scene.idle(45);

        scene.addKeyframe();

        scene.idle(15);
        scene.overlay().showText(40).text("").pointAt(vine.blockSurface(Direction.WEST)).placeNearTarget();
        scene.idle(5);
        scene.overlay().showControls(vine.blockSurface(Direction.NORTH), Pointing.RIGHT, 25).leftClick();
        scene.idle(5);
        scene.world().destroyBlock(vine.pos);
        scene.world().setBlock(vine.pos.above(), ModBlocks.WILD_GRAPEVINE.get().defaultBlockState(), false);
        ElementLink<EntityElement> itemLink = scene.world().createItemEntity(vine.pos.getCenter(), Vec3.ZERO, vineStack.copy());
        scene.idle(50);

        scene.addKeyframe();

        scene.idle(15);
        top.setBlock(Blocks.OAK_PLANKS.defaultBlockState(), false);
        top.showSection(Direction.DOWN);
        scene.idle(15);
        scene.overlay().showText(45).text("").pointAt(top.blockSurface(Direction.WEST).relative(Direction.DOWN, 0.5)).placeNearTarget();
        scene.idle(5);
        scene.overlay().showControls(top.blockSurface(Direction.DOWN).relative(Direction.DOWN, 1), Pointing.UP, 25).rightClick().withItem(vineStack.copy());
        scene.idle(5);
        scene.world().setBlock(top.pos.below(), ModBlocks.WILD_GRAPEVINE.get().defaultBlockState(), false);
        scene.idle(50);

        scene.addKeyframe();

        scene.idle(15);
        scene.overlay().showText(50).text("").placeNearTarget();
        scene.idle(20);
        vine.setBlock(ModBlocks.WILD_GRAPEVINE.get().defaultBlockState(), true);
        scene.world().setBlock(vine.pos.above(), ModBlocks.WILD_GRAPEVINE_PLANT.get().defaultBlockState(), false);
        scene.world().setBlock(top.pos.below(), ModBlocks.WILD_GRAPEVINE_PLANT.get().defaultBlockState(), false);
        scene.world().setBlock(top.pos.below().below(), ModBlocks.WILD_GRAPEVINE.get().defaultBlockState(), true);
        scene.idle(50);

        scene.addKeyframe();

        scene.idle(15);
        scene.overlay().showText(60).text("").placeNearTarget();
        scene.idle(5);
        scene.overlay().showControls(top.blockSurface(Direction.DOWN).relative(Direction.DOWN, 2), Pointing.UP, 25).rightClick().withItem(new ItemStack(Items.SHEARS));
        scene.idle(5);
        scene.world().modifyBlock(top.pos.below().below(), (s) -> s.setValue(WildGrapevineBlock.SHEARED, true), false);
        scene.idle(40);
        scene.overlay().showControls(vine.blockSurface(Direction.DOWN), Pointing.UP, 25).rightClick().withItem(new ItemStack(Items.SHEARS));
        scene.idle(5);
        scene.world().modifyBlock(vine.pos, (s) -> s.setValue(WildGrapevineBlock.SHEARED, true), false);
        scene.idle(50);
    }

    public static void planting(SceneBuilder scene, SceneBuildingUtil util) {
        Level level = Minecraft.getInstance().level;
        if (level == null) {
            return;
        }

        scene.title("grapevine_planting", "");
        scene.configureBasePlate(0, 0, 5);
        scene.showBasePlate();

        PonderBlockWrapper root = new PonderBlockWrapper(0, 1, 1, scene, util);
        Selection grapeSel = util.select().fromTo(0, 3, 1, 4, 3, 4);
        grapeSel = grapeSel.substract(util.select().position(util.grid().at(0, 3, 1)));
        grapeSel = grapeSel.substract(util.select().position(util.grid().at(4, 3, 1)));
        grapeSel = grapeSel.substract(util.select().position(util.grid().at(0, 3, 4)));
        grapeSel = grapeSel.substract(util.select().position(util.grid().at(4, 3, 4)));
        scene.world().showSection(util.select().everywhere(), Direction.UP);

        scene.idle(25);

        scene.overlay().showText(40).text("").pointAt(root.blockSurface(Direction.WEST)).placeNearTarget();
        scene.idle(5);
        scene.overlay().showControls(root.blockSurface(Direction.EAST), Pointing.RIGHT, 25).rightClick().withItem(new ItemStack(ModItems.GRAPEVINE.get()));
        scene.idle(5);
        root.setBlock(ModBlocks.GRAPEVINE_TRELLIS.get().defaultBlockState(), false);
        scene.idle(45);

        scene.addKeyframe();

        scene.idle(15);
        scene.overlay().showText(55).text("").placeNearTarget();
        scene.idle(5);
        growToMaxAge(scene, root.pos, 4);
        scene.world().setBlock(root.pos.above(), ModBlocks.GRAPEVINE_TRELLIS.get().defaultBlockState(), false);
        growToMaxAge(scene, util.grid().at(0, 2, 1), 4);
        scene.world().setBlock(root.pos.above().above(), ModBlocks.GRAPEVINE_TRELLIS.get().defaultBlockState(), false);
        growToMaxAge(scene, util.grid().at(0, 3, 1), 4);
        scene.world().setBlock(util.grid().at(0, 4, 1), ModBlocks.GRAPEVINE_TRELLIS.get().defaultBlockState().setValue(GrapevineTrellisBlock.TYPE, TrellisType.SIX_DIRECTION), false);
        scene.idle(25);

        scene.addKeyframe();

        scene.idle(15);
        scene.overlay().showText(65).text("").placeNearTarget();
        scene.idle(10);
        toCrossVine(scene, util.grid().at(1, 4, 1));
        toCrossVine(scene, util.grid().at(0, 4, 2));
        scene.idle(10);
        toCrossVine(scene, util.grid().at(2, 4, 1));
        toCrossVine(scene, util.grid().at(1, 4, 2));
        toCrossVine(scene, util.grid().at(0, 4, 3));
        scene.idle(10);
        toCrossVine(scene, util.grid().at(3, 4, 1));
        toCrossVine(scene, util.grid().at(2, 4, 2));
        toCrossVine(scene, util.grid().at(1, 4, 3));
        scene.world().setBlock(util.grid().at(0, 4, 4), ModBlocks.GRAPEVINE_TRELLIS.get().defaultBlockState().setValue(GrapevineTrellisBlock.TYPE, TrellisType.SIX_DIRECTION), false);
        scene.idle(10);
        toCrossVine(scene, util.grid().at(3, 4, 2));
        toCrossVine(scene, util.grid().at(2, 4, 3));
        toCrossVine(scene, util.grid().at(1, 4, 4));
        scene.world().setBlock(util.grid().at(4, 4, 1), ModBlocks.GRAPEVINE_TRELLIS.get().defaultBlockState().setValue(GrapevineTrellisBlock.TYPE, TrellisType.SIX_DIRECTION), false);
        scene.idle(10);
        toCrossVine(scene, util.grid().at(4, 4, 2));
        toCrossVine(scene, util.grid().at(3, 4, 3));
        toCrossVine(scene, util.grid().at(2, 4, 4));
        scene.idle(10);
        toCrossVine(scene, util.grid().at(4, 4, 3));
        toCrossVine(scene, util.grid().at(3, 4, 4));
        scene.idle(10);
        scene.world().setBlock(util.grid().at(4, 4, 4), ModBlocks.GRAPEVINE_TRELLIS.get().defaultBlockState().setValue(GrapevineTrellisBlock.TYPE, TrellisType.SIX_DIRECTION), false);
        scene.idle(30);

        scene.addKeyframe();

        scene.idle(15);
        scene.overlay().showText(65).text("").placeNearTarget();
        scene.idle(8);
        scene.world().setBlocks(grapeSel, ModBlocks.GRAPE_CROP.get().defaultBlockState(), false);
        scene.idle(8);
        scene.world().modifyBlocks(grapeSel, (s) -> s.setValue(GrapeCropBlock.AGE, 1), false);
        scene.idle(8);
        scene.world().modifyBlocks(grapeSel, (s) -> s.setValue(GrapeCropBlock.AGE, 2), false);
        scene.idle(8);
        scene.world().modifyBlocks(grapeSel, (s) -> s.setValue(GrapeCropBlock.AGE, 3), false);
        scene.idle(8);
        scene.world().modifyBlocks(grapeSel, (s) -> s.setValue(GrapeCropBlock.AGE, 4), false);
        scene.idle(8);
        scene.world().modifyBlocks(grapeSel, (s) -> s.setValue(GrapeCropBlock.AGE, 5), false);
        scene.idle(25);

        scene.addKeyframe();

        BlockPos grape = util.grid().at(1, 3, 1);
        BlockPos grape1 = util.grid().at(3, 3, 1);
        scene.idle(15);
        scene.overlay().showText(35).text("").pointAt(util.vector().blockSurface(grape, Direction.WEST)).placeNearTarget();
        scene.idle(5);
        scene.overlay().showControls(util.vector().blockSurface(grape, Direction.DOWN), Pointing.UP, 25).leftClick();
        scene.idle(5);
        scene.world().destroyBlock(grape);
        scene.world().createItemEntity(grape.getCenter(), Vec3.ZERO, new ItemStack(ModItems.GRAPE.get()));
        scene.idle(45);
        scene.overlay().showText(35).text("").pointAt(util.vector().blockSurface(grape1, Direction.WEST)).placeNearTarget();
        scene.idle(5);
        scene.overlay().showControls(util.vector().blockSurface(grape1, Direction.DOWN), Pointing.UP, 25).leftClick().withItem(new ItemStack(Items.SHEARS));
        scene.idle(5);
        scene.world().destroyBlock(grape1);
        scene.world().createItemEntity(grape1.getCenter(), Vec3.ZERO, new ItemStack(ModItems.GRAPE.get(), 3));
        scene.idle(45);
    }

    private static void growToMaxAge(SceneBuilder scene, BlockPos pos, int interval) {
        scene.idle(interval);
        for (int i = 1; i < 4; i++) {
            int j = i;
            scene.world().modifyBlock(pos, (s) -> s.setValue(GrapevineTrellisBlock.AGE, j), false);
            scene.idle(interval);
        }
    }

    private static void toCrossVine(SceneBuilder scene, BlockPos pos) {
        scene.world().setBlock(
                pos,
                ModBlocks.GRAPEVINE_TRELLIS.get().defaultBlockState()
                        .setValue(GrapevineTrellisBlock.TYPE, TrellisType.CROSS_UP_DOWN),
                false
        );
    }
}
