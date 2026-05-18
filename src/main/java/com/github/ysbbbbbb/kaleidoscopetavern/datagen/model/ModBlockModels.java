package com.github.ysbbbbbb.kaleidoscopetavern.datagen.model;

import com.github.ysbbbbbb.kaleidoscopetavern.block.brew.BarCabinetBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.block.brew.DrinkBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.block.brew.PressingTubBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.block.brew.TapBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.block.deco.*;
import com.github.ysbbbbbb.kaleidoscopetavern.block.plant.GrapeCropBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.block.plant.GrapevineTrellisBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.block.plant.TrellisBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.block.properties.ConnectionType;
import com.github.ysbbbbbb.kaleidoscopetavern.block.properties.TrellisType;
import com.github.ysbbbbbb.kaleidoscopetavern.datagen.model.catalog.*;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.renderer.block.dispatch.VariantMutator;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.Half;

import java.util.Optional;

import static net.minecraft.client.data.models.BlockModelGenerators.*;

public final class ModBlockModels {
    private final String modId;

    ModBlockModels(String modId) {
        this.modId = modId;
    }

    void generateAll(BlockModelGenerators blockModels) {
        generateSofaModels(blockModels);
        generateBarStoolModels(blockModels);
        generateSandwichBoardModels(blockModels);
        generatePaintingModels(blockModels);
        generateCrossModels(blockModels);


        generateSofaBlockStates(blockModels);
        generateBarStoolBlockStates(blockModels);
        generateSandwichBoardBlockStates(blockModels);
        generateStringLightsBlockStates(blockModels);
        generatePaintingBlockStates(blockModels);
        generateBarCounterBlockState(blockModels);
        generateStepladderBlockState(blockModels);
        generateTableBlockState(blockModels);
        generateWildGrapevineBlockStates(blockModels);
        generateTrellisBlockState(blockModels);
        generateGrapevineTrellisBlockStates(blockModels);
        generateGrapeCropBlockStates(blockModels);
        generatePressingTubBlockState(blockModels);
        generateTapBlockState(blockModels);
        generateBarrelBlockState(blockModels);
        generateBottleBlockStates(blockModels);
        generateBarCabinetBlockStates(blockModels);
        generateDrinkBlockStates(blockModels);
        generateChalkboardBlockState(blockModels);
        generateBaseSandwichBoardBlockState(blockModels);
    }

    /**
     * 生成沙发块模型 JSON（每个颜色+连接类型）
     */
    private void generateSofaModels(BlockModelGenerators blockModels) {
        for (BlockItemCatalogEntry sofa : SofaModelCatalog.entries()) {
            Material texture = blockTexture("block/deco/sofa/" + sofa.path());
            Material particle = vanillaTexture("block/" + sofa.path() + "_wool");
            for (ConnectionType type : ConnectionType.values()) {
                String typeName = type.getSerializedName();
                Identifier modelId = modLoc("block/deco/sofa/" + sofa.path() + "/" + typeName);
                Identifier parent = modLoc("block/deco/sofa/base/" + typeName);
                ModelTemplate template = new ModelTemplate(Optional.of(parent), Optional.empty(),
                        TextureSlot.TEXTURE, TextureSlot.PARTICLE);
                TextureMapping mapping = new TextureMapping()
                        .put(TextureSlot.TEXTURE, texture)
                        .put(TextureSlot.PARTICLE, particle);
                template.create(modelId, mapping, blockModels.modelOutput);
            }
        }
    }

    /**
     * 生成高脚凳块模型 JSON
     */
    private void generateBarStoolModels(BlockModelGenerators blockModels) {
        for (BlockItemCatalogEntry barStool : BarStoolModelCatalog.entries()) {
            Material texture = blockTexture("block/deco/bar_stool/" + barStool.path());
            Material particle = vanillaTexture("block/" + barStool.path() + "_wool");
            Identifier modelId = modLoc("block/deco/bar_stool/" + barStool.path());
            Identifier parent = modLoc("block/deco/bar_stool/base");
            ModelTemplate template = new ModelTemplate(Optional.of(parent), Optional.empty(),
                    TextureSlot.TEXTURE, TextureSlot.PARTICLE);
            TextureMapping mapping = new TextureMapping()
                    .put(TextureSlot.TEXTURE, texture)
                    .put(TextureSlot.PARTICLE, particle);
            template.create(modelId, mapping, blockModels.modelOutput);
        }
    }

    /**
     * 生成展板块模型 JSON
     */
    private void generateSandwichBoardModels(BlockModelGenerators blockModels) {
        for (BlockItemCatalogEntry sandwichBoard : SandwichBoardModelCatalog.entries()) {
            Material layer1 = blockTexture("block/deco/sandwich_board/" + sandwichBoard.path());
            Identifier modelId = modLoc("block/deco/sandwich_board/" + sandwichBoard.path() + "_top");
            Identifier parent = modLoc("block/deco/sandwich_board/deco_top");
            ModelTemplate template = new ModelTemplate(Optional.of(parent), Optional.empty(), TextureSlot.LAYER1);
            TextureMapping mapping = new TextureMapping().put(TextureSlot.LAYER1, layer1);
            template.create(modelId, mapping, blockModels.modelOutput);
        }
    }

    /**
     * 生成挂画块模型 JSON
     */
    private void generatePaintingModels(BlockModelGenerators blockModels) {
        for (BlockItemCatalogEntry painting : PaintingModelCatalog.entries()) {
            Material texture = blockTexture("block/deco/painting/" + painting.path());
            Identifier modelId = modLoc("block/deco/painting/" + painting.path());
            Identifier parent = modLoc("block/deco/painting/base");
            ModelTemplate template = new ModelTemplate(Optional.of(parent), Optional.empty(), TextureSlot.TEXTURE);
            TextureMapping mapping = new TextureMapping().put(TextureSlot.TEXTURE, texture);
            template.create(modelId, mapping, blockModels.modelOutput);
        }
    }

    /**
     * 生成十字/植物模型 JSON（野生葡萄藤等）
     */
    private void generateCrossModels(BlockModelGenerators blockModels) {
        Identifier wildGrapevineId = modLoc("block/plant/wild_grapevine");
        ModelTemplates.CROSS.create(wildGrapevineId,
                TextureMapping.cross(blockTexture("block/plant/wild_grapevine")),
                blockModels.modelOutput);

        Identifier wildGrapevinePlantId = modLoc("block/plant/wild_grapevine_plant");
        ModelTemplates.CROSS.create(wildGrapevinePlantId,
                TextureMapping.cross(blockTexture("block/plant/wild_grapevine_plant")),
                blockModels.modelOutput);
    }

    /**
     * 沙发（水平朝向 + 连接类型）
     */
    private void generateSofaBlockStates(BlockModelGenerators bm) {
        for (BlockItemCatalogEntry sofa : SofaModelCatalog.entries()) {
            Block block = sofa.block().get();
            bm.blockStateOutput.accept(MultiVariantGenerator.dispatch(block)
                    .with(PropertyDispatch.initial(SofaBlock.CONNECTION)
                            .generate(connection -> {
                                Identifier modelId = modLoc("block/deco/sofa/" + sofa.path() + "/" + connection.getSerializedName());
                                return BlockModelGenerators.plainVariant(modelId);
                            }))
                    .with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING));
        }
    }

    /**
     * 高脚凳（水平朝向）
     */
    private void generateBarStoolBlockStates(BlockModelGenerators bm) {
        for (BlockItemCatalogEntry barStool : BarStoolModelCatalog.entries()) {
            Block block = barStool.block().get();
            Identifier modelId = modLoc("block/deco/bar_stool/" + barStool.path());
            bm.blockStateOutput.accept(
                    MultiVariantGenerator.dispatch(block, BlockModelGenerators.plainVariant(modelId))
                            .with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING));
        }
    }

    /**
     * 展板（水平朝向 + 上半/下半）
     */
    private void generateSandwichBoardBlockStates(BlockModelGenerators bm) {
        for (BlockItemCatalogEntry sandwichBoard : SandwichBoardModelCatalog.entries()) {
            Block block = sandwichBoard.block().get();
            bm.blockStateOutput.accept(
                    MultiVariantGenerator.dispatch(block)
                            .with(PropertyDispatch.initial(SandwichBoardBlock.HALF)
                                    .select(Half.BOTTOM, BlockModelGenerators.plainVariant(modLoc("block/deco/sandwich_board/base")))
                                    .select(Half.TOP, BlockModelGenerators.plainVariant(modLoc("block/deco/sandwich_board/" + sandwichBoard.path() + "_top"))))
                            .with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING));
        }
    }

    /**
     * 彩灯（水平朝向）
     */
    private void generateStringLightsBlockStates(BlockModelGenerators bm) {
        for (BlockItemCatalogEntry stringLights : StringLightsModelCatalog.entries()) {
            Block block = stringLights.block().get();
            Identifier modelId = modLoc("block/deco/string_lights/" + stringLights.path());
            bm.blockStateOutput.accept(
                    MultiVariantGenerator.dispatch(block, BlockModelGenerators.plainVariant(modelId))
                            .with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING));
        }
    }

    /**
     * 挂画（水平朝向 + 附着面）
     */
    private void generatePaintingBlockStates(BlockModelGenerators bm) {
        for (BlockItemCatalogEntry painting : PaintingModelCatalog.entries()) {
            Block block = painting.block().get();
            Identifier modelId = modLoc("block/deco/painting/" + painting.path());
            bm.blockStateOutput.accept(
                    MultiVariantGenerator.dispatch(block, BlockModelGenerators.plainVariant(modelId))
                            .with(paintingRotation()));
        }
    }

    /**
     * 吧台（水平朝向 + 连接类型）
     */
    private void generateBarCounterBlockState(BlockModelGenerators bm) {
        Block block = ModBlocks.BAR_COUNTER.get();
        bm.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(block)
                        .with(PropertyDispatch.initial(SofaBlock.CONNECTION)
                                .generate(connection -> {
                                    Identifier modelId = modLoc("block/deco/bar_counter/"
                                                                + connection.getSerializedName());
                                    return BlockModelGenerators.plainVariant(modelId);
                                }))
                        .with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING));
    }

    /**
     * 人字梯（水平朝向 + 上半/下半）
     */
    private void generateStepladderBlockState(BlockModelGenerators bm) {
        Block block = ModBlocks.STEPLADDER.get();
        bm.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(block)
                        .with(PropertyDispatch.initial(StepladderBlock.HALF)
                                .select(Half.BOTTOM, BlockModelGenerators.plainVariant(
                                        modLoc("block/deco/stepladder/bottom")))
                                .select(Half.TOP, BlockModelGenerators.plainVariant(
                                        modLoc("block/deco/stepladder/top"))))
                        .with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING));
    }

    /**
     * 桌子（位置 + 轴）
     */
    private void generateTableBlockState(BlockModelGenerators bm) {
        Block block = ModBlocks.TABLE.get();
        bm.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(block)
                        .with(PropertyDispatch.initial(TableBlock.POSITION, TableBlock.AXIS)
                                .select(TableBlock.SINGLE, Direction.Axis.X,
                                        BlockModelGenerators.plainVariant(modLoc("block/deco/table/single")))
                                .select(TableBlock.SINGLE, Direction.Axis.Z,
                                        BlockModelGenerators.plainVariant(modLoc("block/deco/table/single")))
                                .select(TableBlock.LEFT, Direction.Axis.X,
                                        BlockModelGenerators.plainVariant(modLoc("block/deco/table/left")))
                                .select(TableBlock.LEFT, Direction.Axis.Z,
                                        BlockModelGenerators.plainVariant(modLoc("block/deco/table/left_rot")))
                                .select(TableBlock.RIGHT, Direction.Axis.X,
                                        BlockModelGenerators.plainVariant(modLoc("block/deco/table/right")))
                                .select(TableBlock.RIGHT, Direction.Axis.Z,
                                        BlockModelGenerators.plainVariant(modLoc("block/deco/table/right_rot")))
                                .select(TableBlock.MIDDLE, Direction.Axis.X,
                                        BlockModelGenerators.plainVariant(modLoc("block/deco/table/middle")))
                                .select(TableBlock.MIDDLE, Direction.Axis.Z,
                                        BlockModelGenerators.plainVariant(modLoc("block/deco/table/middle_rot")))));
    }

    /**
     * 野生葡萄藤（简单方块）
     */
    private void generateWildGrapevineBlockStates(BlockModelGenerators bm) {
        bm.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(
                ModBlocks.WILD_GRAPEVINE.get(),
                BlockModelGenerators.plainVariant(modLoc("block/plant/wild_grapevine"))));
        bm.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(
                ModBlocks.WILD_GRAPEVINE_PLANT.get(),
                BlockModelGenerators.plainVariant(modLoc("block/plant/wild_grapevine_plant"))));
    }

    /**
     * 藤架（藤架类型）
     */
    private void generateTrellisBlockState(BlockModelGenerators bm) {
        Block block = ModBlocks.TRELLIS.get();
        bm.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(block)
                        .with(PropertyDispatch.initial(TrellisBlock.TYPE)
                                .generate(type -> BlockModelGenerators.plainVariant(
                                        modLoc("block/plant/trellis/" + type.getSerializedName())))));
    }

    /**
     * 葡萄藤架（藤架类型 + 生长阶段）
     */
    private void generateGrapevineTrellisBlockStates(BlockModelGenerators bm) {
        grapevineTrellisBlockState(bm, ModBlocks.GRAPEVINE_TRELLIS.get(), "grapevine_trellis");
        grapevineTrellisBlockState(bm, ModBlocks.ICE_GRAPEVINE_TRELLIS.get(), "ice_grapevine_trellis");
        grapevineTrellisBlockState(bm, ModBlocks.GOLD_GRAPEVINE_TRELLIS.get(), "gold_grapevine_trellis");
    }

    private void grapevineTrellisBlockState(BlockModelGenerators bm, Block block, String grapeType) {
        bm.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(block)
                        .with(PropertyDispatch.initial(GrapevineTrellisBlock.AGE, TrellisBlock.TYPE)
                                .generate((age, type) -> {
                                    Identifier modelId;
                                    if (type == TrellisType.SINGLE) {
                                        modelId = modLoc("block/plant/" + grapeType + "/"
                                                         + type.getSerializedName() + "_stage" + age);
                                    } else {
                                        modelId = modLoc("block/plant/" + grapeType + "/"
                                                         + type.getSerializedName());
                                    }
                                    return BlockModelGenerators.plainVariant(modelId);
                                })));
    }

    /**
     * 葡萄作物（生长阶段）
     */
    private void generateGrapeCropBlockStates(BlockModelGenerators bm) {
        grapeCropBlockState(bm, ModBlocks.GRAPE_CROP.get(), "grape_crop");
        grapeCropBlockState(bm, ModBlocks.ICE_GRAPE_CROP.get(), "ice_grape_crop");
        grapeCropBlockState(bm, ModBlocks.GOLD_GRAPE_CROP.get(), "gold_grape_crop");
    }

    private void grapeCropBlockState(BlockModelGenerators bm, Block block, String grapeType) {
        bm.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(block)
                        .with(PropertyDispatch.initial(GrapeCropBlock.AGE)
                                .generate(age -> BlockModelGenerators.plainVariant(
                                        modLoc("block/plant/" + grapeType + "/stage" + age)))));
    }

    /**
     * 果盆（水平朝向 + 倾斜）
     */
    private void generatePressingTubBlockState(BlockModelGenerators bm) {
        Block block = ModBlocks.PRESSING_TUB.get();
        bm.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(block)
                        .with(PropertyDispatch.initial(PressingTubBlock.TILT)
                                .select(false, BlockModelGenerators.plainVariant(
                                        modLoc("block/brew/pressing_tub")))
                                .select(true, BlockModelGenerators.plainVariant(
                                        modLoc("block/brew/tilt_pressing_tub"))))
                        .with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING));
    }

    /**
     * 龙头（水平朝向 + 开/关）
     */
    private void generateTapBlockState(BlockModelGenerators bm) {
        Block block = ModBlocks.TAP.get();
        bm.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(block)
                        .with(PropertyDispatch.initial(TapBlock.OPEN)
                                .select(false, BlockModelGenerators.plainVariant(
                                        modLoc("block/brew/tap/close")))
                                .select(true, BlockModelGenerators.plainVariant(
                                        modLoc("block/brew/tap/open"))))
                        .with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING));
    }

    /**
     * 酒桶（简单方块）
     */
    private void generateBarrelBlockState(BlockModelGenerators bm) {
        bm.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(
                ModBlocks.BARREL.get(),
                BlockModelGenerators.plainVariant(modLoc("block/brew/barrel"))));
    }

    /**
     * 瓶子方块（水平朝向）—— 空瓶、燃烧瓶、水瓶、蜂蜜瓶、龙息瓶
     */
    private void generateBottleBlockStates(BlockModelGenerators bm) {
        bottleHorizontalBlockState(bm, ModBlocks.EMPTY_BOTTLE.get(), "block/brew/empty_bottle");
        bottleHorizontalBlockState(bm, ModBlocks.MOLOTOV.get(), "block/brew/molotov");
        bottleHorizontalBlockState(bm, ModBlocks.WATER_BOTTLE.get(), "block/brew/water_bottle");
        bottleHorizontalBlockState(bm, ModBlocks.HONEY_BOTTLE.get(), "block/brew/honey_bottle");
        bottleHorizontalBlockState(bm, ModBlocks.DRAGON_BREATH_BOTTLE.get(), "block/brew/dragon_breath_bottle");
    }

    private void bottleHorizontalBlockState(BlockModelGenerators bm, Block block, String modelPath) {
        bm.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(block, BlockModelGenerators.plainVariant(modLoc(modelPath)))
                        .with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING));
    }

    /**
     * 酒柜（水平朝向 + 位置）
     */
    private void generateBarCabinetBlockStates(BlockModelGenerators bm) {
        barCabinetBlockState(bm, ModBlocks.BAR_CABINET.get(), "bar_cabinet");
        barCabinetBlockState(bm, ModBlocks.GLASS_BAR_CABINET.get(), "glass_bar_cabinet");
    }

    private void barCabinetBlockState(BlockModelGenerators bm, Block block, String name) {
        bm.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(block)
                        .with(PropertyDispatch.initial(BarCabinetBlock.POSITION)
                                .generate(position -> BlockModelGenerators.plainVariant(
                                        modLoc("block/brew/" + name + "/"
                                               + position.getSerializedName()))))
                        .with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING));
    }

    /**
     * 酒/饮料（水平朝向 + 数量）
     */
    private void generateDrinkBlockStates(BlockModelGenerators bm) {
        for (BlockItemCatalogEntry drink : DrinkModelCatalog.entries()) {
            drinkBlockState(bm, drink.block().get(), drink.path());
        }
    }

    private void drinkBlockState(BlockModelGenerators bm, Block block, String name) {
        if (block instanceof DrinkBlock drinkBlock) {
            bm.blockStateOutput.accept(
                    MultiVariantGenerator.dispatch(block)
                            .with(PropertyDispatch.initial(drinkBlock.getCountProperty())
                                    .generate(count -> BlockModelGenerators.plainVariant(
                                            modLoc("block/brew/drink/" + name + "/count" + count))))
                            .with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING));
        }
    }

    /**
     * 黑板（水平朝向）
     */
    private void generateChalkboardBlockState(BlockModelGenerators bm) {
        Block block = ModBlocks.CHALKBOARD.get();
        Identifier modelId = modLoc("block/deco/chalkboard");
        bm.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(block, BlockModelGenerators.plainVariant(modelId))
                        .with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING));
    }

    /**
     * 基础展板（水平朝向 + 上半/下半）
     */
    private void generateBaseSandwichBoardBlockState(BlockModelGenerators bm) {
        Block block = ModBlocks.BASE_SANDWICH_BOARD.get();
        bm.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(block)
                        .with(PropertyDispatch.initial(SandwichBoardBlock.HALF)
                                .select(Half.BOTTOM, BlockModelGenerators.plainVariant(
                                        modLoc("block/deco/sandwich_board/base")))
                                .select(Half.TOP, BlockModelGenerators.plainVariant(
                                        modLoc("block/deco/sandwich_board/base_top"))))
                        .with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING));
    }

    /**
     * 挂画的旋转分发（FACING + ATTACH_FACE）
     */
    private static PropertyDispatch<VariantMutator> paintingRotation() {
        return PropertyDispatch.modify(PaintingBlock.ATTACH_FACE, PaintingBlock.FACING)
                .select(AttachFace.CEILING, Direction.NORTH, X_ROT_180.then(Y_ROT_180))
                .select(AttachFace.CEILING, Direction.EAST, X_ROT_180.then(Y_ROT_270))
                .select(AttachFace.CEILING, Direction.SOUTH, X_ROT_180)
                .select(AttachFace.CEILING, Direction.WEST, X_ROT_180.then(Y_ROT_90))
                .select(AttachFace.FLOOR, Direction.NORTH, NOP)
                .select(AttachFace.FLOOR, Direction.EAST, Y_ROT_90)
                .select(AttachFace.FLOOR, Direction.SOUTH, Y_ROT_180)
                .select(AttachFace.FLOOR, Direction.WEST, Y_ROT_270)
                .select(AttachFace.WALL, Direction.NORTH, X_ROT_90)
                .select(AttachFace.WALL, Direction.EAST, X_ROT_90.then(Y_ROT_90))
                .select(AttachFace.WALL, Direction.SOUTH, X_ROT_90.then(Y_ROT_180))
                .select(AttachFace.WALL, Direction.WEST, X_ROT_90.then(Y_ROT_270));
    }

    private Identifier modLoc(String path) {
        return Identifier.fromNamespaceAndPath(modId, path);
    }

    private Material blockTexture(String path) {
        return new Material(Identifier.fromNamespaceAndPath(modId, path));
    }

    private Material vanillaTexture(String path) {
        return new Material(Identifier.withDefaultNamespace(path));
    }
}
