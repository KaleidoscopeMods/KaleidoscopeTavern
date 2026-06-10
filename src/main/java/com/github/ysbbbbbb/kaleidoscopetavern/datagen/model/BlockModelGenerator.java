package com.github.ysbbbbbb.kaleidoscopetavern.datagen.model;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.block.properties.ConnectionType;
import com.github.ysbbbbbb.kaleidoscopetavern.datagen.builder.ModModelBuilder;
import com.github.ysbbbbbb.kaleidoscopetavern.util.ColorUtils;
import com.mojang.math.Transformation;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

public class BlockModelGenerator extends ModelProvider<ModModelBuilder> {
    public BlockModelGenerator(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, KaleidoscopeTavern.MOD_ID, BLOCK_FOLDER, ModModelBuilder::new, existingFileHelper);
    }

    @NotNull
    @Override
    public String getName() {
        return "Mod Block Models: " + modid;
    }

    @Override
    protected void registerModels() {
        for (String color : ColorUtils.COLORS) {
            sofa(color);
            barStool(color);
        }

        sandwichBoard("grass");
        sandwichBoard("allium");
        sandwichBoard("azure_bluet");
        sandwichBoard("cornflower");
        sandwichBoard("orchid");
        sandwichBoard("peony");
        sandwichBoard("pink_petals");
        sandwichBoard("pitcher_plant");
        sandwichBoard("poppy");
        sandwichBoard("sunflower");
        sandwichBoard("torchflower");
        sandwichBoard("tulip");
        sandwichBoard("wither_rose");

        painting("ysbb");
        painting("tartaric_acid");
        painting("cr019");
        painting("unknown");
        painting("master_marisa");
        painting("son_of_man");
        painting("david");
        painting("girl_with_pearl_earring");
        painting("starry_night");
        painting("van_gogh_self_portrait");
        painting("father");
        painting("great_wave");
        painting("mona_lisa");
        painting("mondrian");

        glassware("empty_glassware");
        glassware("ordinary_cocktail");
        glassware("mystery_cocktail");
        glassware("white_lady");
        glassware("emerald");
        glassware("brass_heart");
        glassware("godfather");
        glassware("grasshopper");
        glassware("screwdriver");
        glassware("mojito");
        glassware("allium_garden");
        glassware("depth_charge");
        glassware("nether_special");
        glassware("bloody_mary");
        glassware("sculk_special");

        cross("block/plant/wild_grapevine", modLoc("block/plant/wild_grapevine")).renderType("cutout");
        cross("block/plant/wild_grapevine_plant", modLoc("block/plant/wild_grapevine_plant")).renderType("cutout");
    }

    private void sofa(String color) {
        ResourceLocation texture = modLoc("block/deco/sofa/%s".formatted(color));
        ResourceLocation particle = mcLoc("block/%s_wool".formatted(color));

        for (ConnectionType type : ConnectionType.values()) {
            String typeName = type.getSerializedName();
            String name = "block/deco/sofa/%s/%s".formatted(color, typeName);
            ResourceLocation parent = modLoc("block/deco/sofa/base/%s".formatted(typeName));
            withExistingParent(name, parent)
                    .texture("texture", texture)
                    .texture("particle", particle);
        }
    }

    private void barStool(String color) {
        ResourceLocation texture = modLoc("block/deco/bar_stool/%s".formatted(color));
        ResourceLocation particle = mcLoc("block/%s_wool".formatted(color));

        String name = "block/deco/bar_stool/%s".formatted(color);
        ResourceLocation parent = modLoc("block/deco/bar_stool/base");
        withExistingParent(name, parent)
                .texture("texture", texture)
                .texture("particle", particle);
    }

    private void sandwichBoard(String type) {
        ResourceLocation texture = modLoc("block/deco/sandwich_board/%s".formatted(type));
        String name = "block/deco/sandwich_board/%s_top".formatted(type);
        ResourceLocation parent = modLoc("block/deco/sandwich_board/deco_top");
        withExistingParent(name, parent)
                .texture("layer1", texture);
    }

    private void painting(String type) {
        ResourceLocation texture = modLoc("block/deco/painting/%s".formatted(type));
        String name = "block/deco/painting/%s".formatted(type);
        ResourceLocation parent = modLoc("block/deco/painting/base");
        withExistingParent(name, parent)
                .texture("texture", texture);
    }

    private void glassware(String name) {
        ResourceLocation parent = modLoc("block/mixology/%s".formatted(name));
        int max = RotationSegment.getMaxSegmentIndex();

        for (int i = 0; i <= max; i++) {
            Matrix4f translate = new Matrix4f()
                    .translate(-0.5f, -0.5f, -0.5f)
                    .rotateY(-i * Mth.DEG_TO_RAD * 22.5f)
                    .translate(0.5f, 0.5f, 0.5f);

            ResourceLocation file = modLoc("block/mixology/%s/rot_%d".formatted(name, i));
            withExistingParent(file.toString(), parent)
                    .rootTransforms()
                    .transform(new Transformation(translate))
                    .end();
        }
    }
}
