package com.github.ysbbbbbb.kaleidoscopetavern.compat.ponder.scene;

import com.github.ysbbbbbb.kaleidoscopetavern.block.brew.PressingTubBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.brew.PressingTubBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.compat.ponder.utils.PonderBlockWrapper;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.ParticleEmitter;
import net.createmod.ponder.api.element.ElementLink;
import net.createmod.ponder.api.element.EntityElement;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.api.scene.Selection;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fluids.capability.IFluidHandler;

import java.util.concurrent.atomic.AtomicReference;

public class PressingTubScenes {
    public static void introduction(SceneBuilder scene, SceneBuildingUtil util) {
        Level level = Minecraft.getInstance().level;
        if (level == null) {
            return;
        }

        scene.title("pressing_tub_introduction", "");
        scene.configureBasePlate(0, 0, 5);
        scene.showBasePlate();

        Selection decoSel = util.select().fromTo(2, 1, 3, 4, 4, 4);
        PonderBlockWrapper tub = new PonderBlockWrapper(2, 1, 2, scene, util);
        ItemStack grape = new ItemStack(ModItems.GRAPE.get());
        ItemStack diamond = new ItemStack(Items.DIAMOND);

        scene.world().showSection(decoSel, Direction.UP);

        scene.idle(20);

        tub.showSection(Direction.SOUTH);

        scene.idle(25);

        scene.overlay().showText(40).text("").pointAt(tub.blockSurface(Direction.WEST)).placeNearTarget();
        scene.idle(5);
        scene.overlay().showControls(tub.blockSurface(Direction.UP), Pointing.DOWN, 25).rightClick().withItem(grape.copy());
        scene.idle(5);
        tub.modifyBlockEntity(PressingTubBlockEntity.class, (be) -> {
            be.addIngredient(grape.copyWithCount(16));
        });
        scene.idle(50);
        scene.addKeyframe();
        scene.idle(15);

        scene.overlay().showText(60).text("").pointAt(tub.blockSurface(Direction.WEST)).placeNearTarget();
        scene.idle(5);
        scene.overlay().showControls(tub.blockSurface(Direction.UP), Pointing.DOWN, 20).rightClick();
        scene.idle(5);
        tub.modifyBlockEntity(PressingTubBlockEntity.class, (be) -> {
            be.getItems().extractItem(0, 1, false);
            be.refresh();
        });
        scene.idle(25);
        scene.overlay().showControls(tub.blockSurface(Direction.UP), Pointing.DOWN, 20).withItem(grape.copy());
        scene.idle(45);
        scene.addKeyframe();
        scene.idle(15);

        scene.overlay().showText(60).text("").pointAt(tub.blockSurface(Direction.WEST)).placeNearTarget();
        scene.idle(5);
        scene.overlay().showControls(tub.blockSurface(Direction.UP), Pointing.DOWN, 20).rightClick().whileSneaking();
        scene.idle(5);
        tub.modifyBlockEntity(PressingTubBlockEntity.class, (be) -> {
            be.getItems().extractItem(0, 64, false);
            be.refresh();
        });
        scene.idle(25);
        scene.overlay().showControls(tub.blockSurface(Direction.UP), Pointing.DOWN, 20).withItem(grape.copy());
        scene.idle(50);
    }

    public static void pressing(SceneBuilder scene, SceneBuildingUtil util) {
        Level level = Minecraft.getInstance().level;
        if (level == null) {
            return;
        }
        Pig pig = new Pig(EntityType.PIG, level);

        scene.title("pressing_tub_pressing", "");
        scene.configureBasePlate(0, 0, 5);
        scene.showBasePlate();

        Selection decoSel = util.select().fromTo(2, 1, 3, 4, 4, 4);
        PonderBlockWrapper tub = new PonderBlockWrapper(2, 1, 2, scene, util);
        ItemStack grape = new ItemStack(ModItems.GRAPE.get());
        ItemStack diamond = new ItemStack(Items.DIAMOND);

        scene.world().showSection(decoSel, Direction.UP);

        scene.idle(20);

        tub.showSection(Direction.SOUTH);

        scene.idle(30);

        scene.overlay().showText(35).text("").pointAt(tub.blockSurface(Direction.WEST)).placeNearTarget();
        scene.overlay().showControls(tub.blockSurface(Direction.UP), Pointing.DOWN, 25).rightClick().withItem(grape.copy());
        scene.idle(5);
        tub.modifyBlockEntity(PressingTubBlockEntity.class, (be) -> {
            be.addIngredient(grape.copyWithCount(8));
        });
        scene.idle(45);

        scene.addKeyframe();

        scene.idle(15);
        scene.overlay().showText(90).text("").pointAt(tub.blockSurface(Direction.WEST)).placeNearTarget();
        scene.idle(5);
        ElementLink<EntityElement> slime = initSlime(scene, util, 1.5);

        for (int i = 0; i < 8; i++) {
            int j = i;
            scene.world().modifyEntity(slime, (e) -> {
                e.setDeltaMovement(0, fallingSpeed(j, 0.08D, 0.98D), 0);
                e.move(MoverType.SELF, e.getDeltaMovement());
                e.setYBodyRot(180);
                e.setYHeadRot(180);
            });
            scene.idle(1);
        }

        scene.idle(1);
        AtomicReference<Double> speed = new AtomicReference<>();
        speed.set(0D);

        for (int i = 0; i < 6; i++) {
            int j = i;
            scene.idle(1);
            scene.world().modifyEntity(slime, (e) -> {
                speed.set(accelerate(speed.get(), 0.6));
                e.setDeltaMovement(0, jumpSpeed(j + 1) * 0.75, -speed.get() * 0.8);
                e.move(MoverType.SELF, e.getDeltaMovement());
                e.setYBodyRot(180);
                e.setYHeadRot(180);
            });
        }

        for (int i = 0; i < 6; i++) {
            int j = i;
            scene.idle(1);
            scene.world().modifyEntity(slime, (e) -> {
                speed.set(decelerate(speed.get(), 0.6));
                e.setDeltaMovement(0, fallingSpeed(j, 0.08D, 0.98D), -speed.get() * 0.8);
                e.move(MoverType.SELF, e.getDeltaMovement());
                e.setYBodyRot(180);
                e.setYHeadRot(180);
            });
        }

        tub.modifyBlockEntity(PressingTubBlockEntity.class, (be) -> {
            be.press(pig, 2);
        });
        emitParticles(tub.pos.getCenter(), grape.copy(), scene, 10, 0.05, level.getRandom());

        for (int x = 0; x < 7; x++) {
            scene.idle(1);

            for (int i = 0; i < 6; i++) {
                int j = i;
                scene.idle(1);
                scene.world().modifyEntity(slime, (e) -> {
                    e.setDeltaMovement(0, jumpSpeed(j + 1) * 0.75, 0);
                    e.move(MoverType.SELF, e.getDeltaMovement());
                    e.setYBodyRot(180);
                    e.setYHeadRot(180);
                });
            }

            scene.idle(1);

            for (int i = 0; i < 6; i++) {
                int j = i;
                scene.idle(1);
                scene.world().modifyEntity(slime, (e) -> {
                    e.setDeltaMovement(0, fallingSpeed(j, 0.08D, 0.98D), 0);
                    e.move(MoverType.SELF, e.getDeltaMovement());
                    e.setYBodyRot(180);
                    e.setYHeadRot(180);
                });
            }

            tub.modifyBlockEntity(PressingTubBlockEntity.class, (be) -> {
                be.press(pig, 1);
            });
            emitParticles(tub.pos.getCenter(), grape.copy(), scene, 10, 0.05, level.getRandom());
            scene.world().modifyEntity(slime, (e) -> {
                e.setYBodyRot(180);
                e.setYHeadRot(180);
            });
        }

        scene.world().modifyEntity(slime, (e) -> {
            e.lerpTo(e.getX(), e.getY(), e.getZ(), 0, 0, 6, false);
        });
        scene.idle(6);

        speed.set(0D);

        for (int i = 0; i < 6; i++) {
            int j = i;
            scene.idle(1);
            scene.world().modifyEntity(slime, (e) -> {
                speed.set(accelerate(speed.get(), 0.6));
                e.setDeltaMovement(0, jumpSpeed(j + 1) * 0.75, speed.get() * 0.8);
                e.move(MoverType.SELF, e.getDeltaMovement());
                e.setYBodyRot(0);
                e.setYHeadRot(0);
            });
        }

        for (int i = 0; i < 6; i++) {
            int j = i;
            scene.idle(1);
            scene.world().modifyEntity(slime, (e) -> {
                speed.set(decelerate(speed.get(), 0.6));
                e.setDeltaMovement(0, fallingSpeed(j, 0.08D, 0.98D), speed.get() * 0.8);
                e.move(MoverType.SELF, e.getDeltaMovement());
                e.setYBodyRot(0);
                e.setYHeadRot(0);
            });
        }

        scene.world().modifyEntity(slime, (e) -> {
            e.lerpTo(e.getX(), e.getY(), e.getZ(), 180, 0, 6, false);
        });
        scene.idle(6);

        scene.idle(20);

        scene.overlay().showControls(tub.blockSurface(Direction.UP), Pointing.DOWN, 20).rightClick().withItem(new ItemStack(Items.BUCKET));
        scene.idle(5);
        tub.modifyBlockEntity(PressingTubBlockEntity.class, (be) -> {
            be.getFluid().drain(1000, IFluidHandler.FluidAction.EXECUTE);
            be.refresh();
        });
        scene.idle(25);
        scene.overlay().showControls(tub.blockSurface(Direction.UP), Pointing.DOWN, 20).withItem(new ItemStack(ModItems.GRAPE_BUCKET.get()));
        scene.idle(40);

        scene.addKeyframe();

        scene.idle(15);
        scene.overlay().showText(60).text("").pointAt(tub.blockSurface(Direction.WEST)).placeNearTarget();
        scene.idle(5);
        scene.overlay().showControls(tub.blockSurface(Direction.UP), Pointing.DOWN, 25).rightClick().withItem(diamond.copy());
        scene.idle(5);
        tub.modifyBlockEntity(PressingTubBlockEntity.class, (be) -> {
            be.addIngredient(diamond.copyWithCount(12));
        });
        scene.idle(40);

        speed.set(0D);

        for (int i = 0; i < 6; i++) {
            int j = i;
            scene.idle(1);
            scene.world().modifyEntity(slime, (e) -> {
                speed.set(accelerate(speed.get(), 0.6));
                e.setDeltaMovement(0, jumpSpeed(j + 1) * 0.75, -speed.get() * 0.8);
                e.move(MoverType.SELF, e.getDeltaMovement());
                e.setYBodyRot(180);
                e.setYHeadRot(180);
            });
        }

        for (int i = 0; i < 6; i++) {
            int j = i;
            scene.idle(1);
            scene.world().modifyEntity(slime, (e) -> {
                speed.set(decelerate(speed.get(), 0.6));
                e.setDeltaMovement(0, fallingSpeed(j, 0.08D, 0.98D), -speed.get() * 0.8);
                e.move(MoverType.SELF, e.getDeltaMovement());
                e.setYBodyRot(180);
                e.setYHeadRot(180);
            });
        }

        tub.modifyBlockEntity(PressingTubBlockEntity.class, PressingTubBlockEntity::dropContents);

        scene.world().createItemEntity(
                tub.blockSurface(Direction.UP),
                new Vec3(0.1f, 0, 0.14f), diamond.copy());
        scene.world().createItemEntity(
                tub.blockSurface(Direction.UP),
                new Vec3(-0.1f, 0, 0.1f), diamond.copy());
        scene.world().createItemEntity(
                tub.blockSurface(Direction.UP),
                new Vec3(0, 0, -0.14f), diamond.copy());
        scene.world().createItemEntity(
                tub.blockSurface(Direction.UP),
                new Vec3(0.12, 0, 0), diamond.copy());
        scene.world().createItemEntity(
                tub.blockSurface(Direction.UP),
                new Vec3(-0.14, 0, 0.03), diamond.copy());
        scene.world().createItemEntity(
                tub.blockSurface(Direction.UP),
                new Vec3(0.2, 0, -0.06f), diamond.copy());

        scene.idle(10);

        scene.world().modifyEntity(slime, (e) -> {
            e.lerpTo(e.getX(), e.getY(), e.getZ(), 150, 15, 6, false);
        });

        scene.idle(25);
    }

    public static void variant(SceneBuilder scene, SceneBuildingUtil util) {
        Level level = Minecraft.getInstance().level;
        if (level == null) {
            return;
        }

        scene.title("pressing_tub_variant", "");
        scene.configureBasePlate(0, 0, 5);
        scene.showBasePlate();

        Selection decoSel = util.select().fromTo(4, 1, 4, 4, 4, 4);
        Selection decoSel1 = util.select().fromTo(2, 4, 3, 4, 4, 4);
        Selection mainSel = util.select().fromTo(2, 1, 2, 2, 1, 3);
        PonderBlockWrapper tub = new PonderBlockWrapper(2, 1, 2, scene, util);
        ItemStack grape = new ItemStack(ModItems.GRAPE.get());
        ItemStack diamond = new ItemStack(Items.DIAMOND);
        tub.modifyBlock((s) -> s.setValue(PressingTubBlock.TILT, true), false);
        scene.world().setBlock(tub.pos.relative(Direction.SOUTH), Blocks.HAY_BLOCK.defaultBlockState(), false);

        scene.world().showSection(decoSel, Direction.UP);
        scene.world().showSection(decoSel1, Direction.UP);

        scene.idle(20);

        scene.world().showSection(mainSel, Direction.SOUTH);

        scene.idle(30);

        scene.overlay().showText(40).text("").pointAt(tub.blockSurface(Direction.WEST)).placeNearTarget();
        scene.overlay().showControls(tub.blockSurface(Direction.UP), Pointing.DOWN, 25).rightClick().withItem(grape.copy());
        scene.idle(5);
        tub.modifyBlockEntity(PressingTubBlockEntity.class, (be) -> {
            be.addIngredient(grape.copyWithCount(8));
        });
        scene.idle(50);

        scene.addKeyframe();

        scene.idle(15);
        scene.overlay().showText(60).text("").pointAt(tub.blockSurface(Direction.WEST)).placeNearTarget();
        scene.idle(5);
        ElementLink<EntityElement> slime = initSlime(scene, util, 1.5);
        scene.world().modifyEntity(slime, (e) -> {
            e.setPos(e.getX(), e.getY(), e.getZ() - 2);
            e.setYRot(0);
            e.setYBodyRot(0);
            e.setYHeadRot(0);
        });

        for (int i = 0; i < 8; i++) {
            int j = i;
            scene.world().modifyEntity(slime, (e) -> {
                e.setDeltaMovement(0, fallingSpeed(j, 0.08D, 0.98D), 0);
                e.move(MoverType.SELF, e.getDeltaMovement());
                e.setYBodyRot(0);
                e.setYHeadRot(0);
            });
            scene.idle(1);
        }

        scene.idle(1);
        AtomicReference<Double> speed = new AtomicReference<>();
        speed.set(0D);

        for (int i = 0; i < 6; i++) {
            int j = i;
            scene.idle(1);
            scene.world().modifyEntity(slime, (e) -> {
                speed.set(accelerate(speed.get(), 0.6));
                e.setDeltaMovement(0, jumpSpeed(j + 1) * 0.75, speed.get() * 0.7);
                e.move(MoverType.SELF, e.getDeltaMovement());
                e.setYBodyRot(0);
                e.setYHeadRot(0);
            });
        }

        for (int i = 0; i < 6; i++) {
            int j = i;
            scene.idle(1);
            scene.world().modifyEntity(slime, (e) -> {
                speed.set(decelerate(speed.get(), 0.6));
                e.setDeltaMovement(0, fallingSpeed(j, 0.08D, 0.98D), speed.get() * 0.7);
                e.move(MoverType.SELF, e.getDeltaMovement());
                e.setYBodyRot(0);
                e.setYHeadRot(0);
            });
        }

        scene.idle(1);
        scene.world().modifyEntity(slime, (e) -> e.lerpTo(e.getX(), e.getY(), e.getZ(), 180, 0, 6, false));
        scene.idle(6);
        speed.set(0D);

        for (int i = 0; i < 6; i++) {
            int j = i;
            scene.idle(1);
            scene.world().modifyEntity(slime, (e) -> {
                speed.set(accelerate(speed.get(), 0.6));
                e.setDeltaMovement(0, jumpSpeed(j + 1) * 0.75, -speed.get() * 0.7);
                e.move(MoverType.SELF, e.getDeltaMovement());
                e.setYBodyRot(180);
                e.setYHeadRot(180);
            });
        }

        for (int i = 0; i < 9; i++) {
            int j = i;
            scene.idle(1);
            scene.world().modifyEntity(slime, (e) -> {
                speed.set(decelerate(speed.get(), 0.6));
                e.setDeltaMovement(0, fallingSpeed(j, 0.08D, 0.98D), -speed.get() * 0.7);
                e.move(MoverType.SELF, e.getDeltaMovement());
                e.setYBodyRot(180);
                e.setYHeadRot(180);
            });
        }

        scene.idle(1);
        scene.world().modifyEntity(slime, (e) -> e.lerpTo(e.getX(), e.getY(), e.getZ(), 0, 0, 6, false));
        scene.idle(6);
        scene.idle(20);

        scene.addKeyframe();

        scene.idle(15);
        scene.overlay().showText(90).text("").pointAt(tub.blockSurface(Direction.WEST)).placeNearTarget();
        scene.idle(5);
        scene.overlay().showControls(tub.blockSurface(Direction.UP), Pointing.DOWN, 20).rightClick();
        scene.idle(5);
        tub.modifyBlockEntity(PressingTubBlockEntity.class, (be) -> {
            be.getItems().extractItem(0, 64, false);
            be.refresh();
        });
        scene.idle(25);
        scene.overlay().showControls(tub.blockSurface(Direction.UP), Pointing.DOWN, 20).rightClick().withItem(diamond.copy());
        scene.idle(5);
        tub.modifyBlockEntity(PressingTubBlockEntity.class, (be) -> {
            be.addIngredient(diamond.copyWithCount(12));
            be.refresh();
        });
        scene.idle(40);

        speed.set(0D);

        for (int i = 0; i < 6; i++) {
            int j = i;
            scene.idle(1);
            scene.world().modifyEntity(slime, (e) -> {
                speed.set(accelerate(speed.get(), 0.6));
                e.setDeltaMovement(0, jumpSpeed(j + 1) * 0.75, speed.get() * 0.7);
                e.move(MoverType.SELF, e.getDeltaMovement());
                e.setYBodyRot(0);
                e.setYHeadRot(0);
            });
        }

        for (int i = 0; i < 6; i++) {
            int j = i;
            scene.idle(1);
            scene.world().modifyEntity(slime, (e) -> {
                speed.set(decelerate(speed.get(), 0.6));
                e.setDeltaMovement(0, fallingSpeed(j, 0.08D, 0.98D), speed.get() * 0.7);
                e.move(MoverType.SELF, e.getDeltaMovement());
                e.setYBodyRot(0);
                e.setYHeadRot(0);
            });
        }

        scene.idle(1);
        scene.world().modifyEntity(slime, (e) -> e.lerpTo(e.getX(), e.getY(), e.getZ(), 180, 0, 6, false));
        scene.idle(6);
        speed.set(0D);

        for (int i = 0; i < 6; i++) {
            int j = i;
            scene.idle(1);
            scene.world().modifyEntity(slime, (e) -> {
                speed.set(accelerate(speed.get(), 0.6));
                e.setDeltaMovement(0, jumpSpeed(j + 1) * 0.75, -speed.get() * 0.7);
                e.move(MoverType.SELF, e.getDeltaMovement());
                e.setYBodyRot(180);
                e.setYHeadRot(180);
            });
        }

        for (int i = 0; i < 9; i++) {
            int j = i;
            scene.idle(1);
            scene.world().modifyEntity(slime, (e) -> {
                speed.set(decelerate(speed.get(), 0.6));
                e.setDeltaMovement(0, fallingSpeed(j, 0.08D, 0.98D), -speed.get() * 0.7);
                e.move(MoverType.SELF, e.getDeltaMovement());
                e.setYBodyRot(180);
                e.setYHeadRot(180);
            });
        }

        scene.idle(20);
    }

    private static ElementLink<EntityElement> initSlime(SceneBuilder scene, SceneBuildingUtil util, double y) {
        return scene.world().createEntity(
                (l) -> {
                    Slime e = new Slime(EntityType.SLIME, l);
                    e.setYRot(180);
                    e.setYBodyRot(180);
                    e.setYHeadRot(180);
                    Vec3 vec3 = util.grid().at(2, 1, 3).getCenter();
                    e.setPos(vec3.x, vec3.y + y, vec3.z);
                    return e;
                });
    }

    private static double fallingSpeed(int tick, double gravity, double drag) {
        return (Math.pow(drag, tick) - 1) * (gravity / (1 - drag));
    }

    private static double jumpSpeed(int tick) {
        double speed = 0.42 * Math.pow(0.98, tick - 1) + 4 * Math.pow(0.98, tick) - 3.92;
        return speed <= 0.003 ? 0 : speed;
    }

    private static double accelerate(double pre, double drag) {
        double speed = pre * drag * 0.91 + 0.1 * Math.pow(0.6 / drag, 3);
        return speed <= 0.003 ? 0 : speed;
    }

    private static double decelerate(double pre, double drag) {
        double speed = pre * drag * 0.91;
        return speed <= 0.003 ? 0 : speed;
    }

    private static void emitParticles(Vec3 pos, ItemStack itemStack, SceneBuilder scene, int amount, double speed, RandomSource random) {
        double angle = Math.toRadians(random.nextInt(360));
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);
        Vec3 motion = new Vec3(speed * cos, speed, speed * sin);
        ParticleEmitter emitter = scene.effects().simpleParticleEmitter(new ItemParticleOption(ParticleTypes.ITEM, itemStack), motion);
        scene.effects().emitParticles(pos, emitter, amount, 1);
    }
}
