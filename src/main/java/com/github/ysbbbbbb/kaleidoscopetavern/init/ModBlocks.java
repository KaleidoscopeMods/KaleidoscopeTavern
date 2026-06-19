package com.github.ysbbbbbb.kaleidoscopetavern.init;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.block.brew.*;
import com.github.ysbbbbbb.kaleidoscopetavern.block.deco.*;
import com.github.ysbbbbbb.kaleidoscopetavern.block.mixology.*;
import com.github.ysbbbbbb.kaleidoscopetavern.block.plant.*;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.brew.*;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco.*;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.mixology.ShakerBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.mixology.SignatureCocktailBlockEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.phys.shapes.Shapes;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

@SuppressWarnings("DataFlowIssue")
public interface ModBlocks {
    DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(KaleidoscopeTavern.MOD_ID);
    DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, KaleidoscopeTavern.MOD_ID);

    // 装饰部分
    // 沙发
    DeferredBlock<Block> WHITE_SOFA = BLOCKS.register("white_sofa", SofaBlock::new);
    DeferredBlock<Block> LIGHT_GRAY_SOFA = BLOCKS.register("light_gray_sofa", SofaBlock::new);
    DeferredBlock<Block> GRAY_SOFA = BLOCKS.register("gray_sofa", SofaBlock::new);
    DeferredBlock<Block> BLACK_SOFA = BLOCKS.register("black_sofa", SofaBlock::new);
    DeferredBlock<Block> BROWN_SOFA = BLOCKS.register("brown_sofa", SofaBlock::new);
    DeferredBlock<Block> RED_SOFA = BLOCKS.register("red_sofa", SofaBlock::new);
    DeferredBlock<Block> ORANGE_SOFA = BLOCKS.register("orange_sofa", SofaBlock::new);
    DeferredBlock<Block> YELLOW_SOFA = BLOCKS.register("yellow_sofa", SofaBlock::new);
    DeferredBlock<Block> LIME_SOFA = BLOCKS.register("lime_sofa", SofaBlock::new);
    DeferredBlock<Block> GREEN_SOFA = BLOCKS.register("green_sofa", SofaBlock::new);
    DeferredBlock<Block> CYAN_SOFA = BLOCKS.register("cyan_sofa", SofaBlock::new);
    DeferredBlock<Block> LIGHT_BLUE_SOFA = BLOCKS.register("light_blue_sofa", SofaBlock::new);
    DeferredBlock<Block> BLUE_SOFA = BLOCKS.register("blue_sofa", SofaBlock::new);
    DeferredBlock<Block> PURPLE_SOFA = BLOCKS.register("purple_sofa", SofaBlock::new);
    DeferredBlock<Block> MAGENTA_SOFA = BLOCKS.register("magenta_sofa", SofaBlock::new);
    DeferredBlock<Block> PINK_SOFA = BLOCKS.register("pink_sofa", SofaBlock::new);

    // 高脚凳
    DeferredBlock<Block> WHITE_BAR_STOOL = BLOCKS.register("white_bar_stool", () -> new BarStoolBlock(DyeColor.WHITE));
    DeferredBlock<Block> LIGHT_GRAY_BAR_STOOL = BLOCKS.register("light_gray_bar_stool", () -> new BarStoolBlock(DyeColor.LIGHT_GRAY));
    DeferredBlock<Block> GRAY_BAR_STOOL = BLOCKS.register("gray_bar_stool", () -> new BarStoolBlock(DyeColor.GRAY));
    DeferredBlock<Block> BLACK_BAR_STOOL = BLOCKS.register("black_bar_stool", () -> new BarStoolBlock(DyeColor.BLACK));
    DeferredBlock<Block> BROWN_BAR_STOOL = BLOCKS.register("brown_bar_stool", () -> new BarStoolBlock(DyeColor.BROWN));
    DeferredBlock<Block> RED_BAR_STOOL = BLOCKS.register("red_bar_stool", () -> new BarStoolBlock(DyeColor.RED));
    DeferredBlock<Block> ORANGE_BAR_STOOL = BLOCKS.register("orange_bar_stool", () -> new BarStoolBlock(DyeColor.ORANGE));
    DeferredBlock<Block> YELLOW_BAR_STOOL = BLOCKS.register("yellow_bar_stool", () -> new BarStoolBlock(DyeColor.YELLOW));
    DeferredBlock<Block> LIME_BAR_STOOL = BLOCKS.register("lime_bar_stool", () -> new BarStoolBlock(DyeColor.LIME));
    DeferredBlock<Block> GREEN_BAR_STOOL = BLOCKS.register("green_bar_stool", () -> new BarStoolBlock(DyeColor.GREEN));
    DeferredBlock<Block> CYAN_BAR_STOOL = BLOCKS.register("cyan_bar_stool", () -> new BarStoolBlock(DyeColor.CYAN));
    DeferredBlock<Block> LIGHT_BLUE_BAR_STOOL = BLOCKS.register("light_blue_bar_stool", () -> new BarStoolBlock(DyeColor.LIGHT_BLUE));
    DeferredBlock<Block> BLUE_BAR_STOOL = BLOCKS.register("blue_bar_stool", () -> new BarStoolBlock(DyeColor.BLUE));
    DeferredBlock<Block> PURPLE_BAR_STOOL = BLOCKS.register("purple_bar_stool", () -> new BarStoolBlock(DyeColor.PURPLE));
    DeferredBlock<Block> MAGENTA_BAR_STOOL = BLOCKS.register("magenta_bar_stool", () -> new BarStoolBlock(DyeColor.MAGENTA));
    DeferredBlock<Block> PINK_BAR_STOOL = BLOCKS.register("pink_bar_stool", () -> new BarStoolBlock(DyeColor.PINK));

    // 黑板
    DeferredBlock<Block> CHALKBOARD = BLOCKS.register("chalkboard", ChalkboardBlock::new);
    DeferredBlock<Block> TABLE = BLOCKS.register("table", TableBlock::new);

    // 展板
    DeferredBlock<Block> BASE_SANDWICH_BOARD = BLOCKS.register("base_sandwich_board", () -> new SandwichBoardBlock());
    DeferredBlock<Block> GRASS_SANDWICH_BOARD = BLOCKS.register("grass_sandwich_board", () -> new SandwichBoardBlock(Items.SHORT_GRASS));
    DeferredBlock<Block> ALLIUM_SANDWICH_BOARD = BLOCKS.register("allium_sandwich_board", () -> new SandwichBoardBlock(Items.ALLIUM));
    DeferredBlock<Block> AZURE_BLUET_SANDWICH_BOARD = BLOCKS.register("azure_bluet_sandwich_board", () -> new SandwichBoardBlock(Items.AZURE_BLUET, Items.OXEYE_DAISY, Items.LILY_OF_THE_VALLEY));
    DeferredBlock<Block> CORNFLOWER_SANDWICH_BOARD = BLOCKS.register("cornflower_sandwich_board", () -> new SandwichBoardBlock(Items.CORNFLOWER));
    DeferredBlock<Block> ORCHID_SANDWICH_BOARD = BLOCKS.register("orchid_sandwich_board", () -> new SandwichBoardBlock(Items.BLUE_ORCHID));
    DeferredBlock<Block> PEONY_SANDWICH_BOARD = BLOCKS.register("peony_sandwich_board", () -> new SandwichBoardBlock(Items.PEONY, Items.LILAC));
    DeferredBlock<Block> PINK_PETALS_SANDWICH_BOARD = BLOCKS.register("pink_petals_sandwich_board", () -> new SandwichBoardBlock(Items.PINK_PETALS));
    DeferredBlock<Block> PITCHER_PLANT_SANDWICH_BOARD = BLOCKS.register("pitcher_plant_sandwich_board", () -> new SandwichBoardBlock(Items.PITCHER_PLANT));
    DeferredBlock<Block> POPPY_SANDWICH_BOARD = BLOCKS.register("poppy_sandwich_board", () -> new SandwichBoardBlock(Items.POPPY, Items.ROSE_BUSH));
    DeferredBlock<Block> SUNFLOWER_SANDWICH_BOARD = BLOCKS.register("sunflower_sandwich_board", () -> new SandwichBoardBlock(Items.SUNFLOWER, Items.DANDELION));
    DeferredBlock<Block> TORCHFLOWER_SANDWICH_BOARD = BLOCKS.register("torchflower_sandwich_board", () -> new SandwichBoardBlock(Items.TORCHFLOWER));
    DeferredBlock<Block> TULIP_SANDWICH_BOARD = BLOCKS.register("tulip_sandwich_board", () -> new SandwichBoardBlock(Items.RED_TULIP, Items.ORANGE_TULIP, Items.WHITE_TULIP, Items.PINK_TULIP));
    DeferredBlock<Block> WITHER_ROSE_SANDWICH_BOARD = BLOCKS.register("wither_rose_sandwich_board", () -> new SandwichBoardBlock(Items.WITHER_ROSE));

    // 彩灯
    DeferredBlock<Block> STRING_LIGHTS_COLORLESS = BLOCKS.register("string_lights_colorless", () -> new StringLightsBlock(null));
    DeferredBlock<Block> STRING_LIGHTS_WHITE = BLOCKS.register("string_lights_white", () -> new StringLightsBlock(Items.WHITE_DYE));
    DeferredBlock<Block> STRING_LIGHTS_LIGHT_GRAY = BLOCKS.register("string_lights_light_gray", () -> new StringLightsBlock(Items.LIGHT_GRAY_DYE));
    DeferredBlock<Block> STRING_LIGHTS_GRAY = BLOCKS.register("string_lights_gray", () -> new StringLightsBlock(Items.GRAY_DYE));
    DeferredBlock<Block> STRING_LIGHTS_BLACK = BLOCKS.register("string_lights_black", () -> new StringLightsBlock(Items.BLACK_DYE));
    DeferredBlock<Block> STRING_LIGHTS_BROWN = BLOCKS.register("string_lights_brown", () -> new StringLightsBlock(Items.BROWN_DYE));
    DeferredBlock<Block> STRING_LIGHTS_RED = BLOCKS.register("string_lights_red", () -> new StringLightsBlock(Items.RED_DYE));
    DeferredBlock<Block> STRING_LIGHTS_ORANGE = BLOCKS.register("string_lights_orange", () -> new StringLightsBlock(Items.ORANGE_DYE));
    DeferredBlock<Block> STRING_LIGHTS_YELLOW = BLOCKS.register("string_lights_yellow", () -> new StringLightsBlock(Items.YELLOW_DYE));
    DeferredBlock<Block> STRING_LIGHTS_LIME = BLOCKS.register("string_lights_lime", () -> new StringLightsBlock(Items.LIME_DYE));
    DeferredBlock<Block> STRING_LIGHTS_GREEN = BLOCKS.register("string_lights_green", () -> new StringLightsBlock(Items.GREEN_DYE));
    DeferredBlock<Block> STRING_LIGHTS_CYAN = BLOCKS.register("string_lights_cyan", () -> new StringLightsBlock(Items.CYAN_DYE));
    DeferredBlock<Block> STRING_LIGHTS_LIGHT_BLUE = BLOCKS.register("string_lights_light_blue", () -> new StringLightsBlock(Items.LIGHT_BLUE_DYE));
    DeferredBlock<Block> STRING_LIGHTS_BLUE = BLOCKS.register("string_lights_blue", () -> new StringLightsBlock(Items.BLUE_DYE));
    DeferredBlock<Block> STRING_LIGHTS_PURPLE = BLOCKS.register("string_lights_purple", () -> new StringLightsBlock(Items.PURPLE_DYE));
    DeferredBlock<Block> STRING_LIGHTS_MAGENTA = BLOCKS.register("string_lights_magenta", () -> new StringLightsBlock(Items.MAGENTA_DYE));
    DeferredBlock<Block> STRING_LIGHTS_PINK = BLOCKS.register("string_lights_pink", () -> new StringLightsBlock(Items.PINK_DYE));

    // 挂画
    DeferredBlock<Block> YSBB_PAINTING = BLOCKS.register("ysbb_painting", PaintingBlock::new);
    DeferredBlock<Block> TARTARIC_ACID_PAINTING = BLOCKS.register("tartaric_acid_painting", PaintingBlock::new);
    DeferredBlock<Block> CR019_PAINTING = BLOCKS.register("cr019_painting", PaintingBlock::new);
    DeferredBlock<Block> UNKNOWN_PAINTING = BLOCKS.register("unknown_painting", PaintingBlock::new);
    DeferredBlock<Block> MASTER_MARISA_PAINTING = BLOCKS.register("master_marisa_painting", PaintingBlock::new);
    DeferredBlock<Block> SON_OF_MAN_PAINTING = BLOCKS.register("son_of_man_painting", PaintingBlock::new);
    DeferredBlock<Block> DAVID_PAINTING = BLOCKS.register("david_painting", PaintingBlock::new);
    DeferredBlock<Block> GIRL_WITH_PEARL_EARRING_PAINTING = BLOCKS.register("girl_with_pearl_earring_painting", PaintingBlock::new);
    DeferredBlock<Block> STARRY_NIGHT_PAINTING = BLOCKS.register("starry_night_painting", PaintingBlock::new);
    DeferredBlock<Block> VAN_GOGH_SELF_PORTRAIT_PAINTING = BLOCKS.register("van_gogh_self_portrait_painting", PaintingBlock::new);
    DeferredBlock<Block> FATHER_PAINTING = BLOCKS.register("father_painting", PaintingBlock::new);
    DeferredBlock<Block> GREAT_WAVE_PAINTING = BLOCKS.register("great_wave_painting", PaintingBlock::new);
    DeferredBlock<Block> MONA_LISA_PAINTING = BLOCKS.register("mona_lisa_painting", PaintingBlock::new);
    DeferredBlock<Block> MONDRIAN_PAINTING = BLOCKS.register("mondrian_painting", PaintingBlock::new);

    // 垂灯
    DeferredBlock<Block> BELL_PENDANT_LAMP = BLOCKS.register("bell_pendant_lamp", PendantLampBlock::new);
    DeferredBlock<Block> YELLOW_PENDANT_LAMP = BLOCKS.register("yellow_pendant_lamp", PendantLampBlock::new);
    DeferredBlock<Block> BLUE_PENDANT_LAMP = BLOCKS.register("blue_pendant_lamp", PendantLampBlock::new);

    // 香薰
    DeferredBlock<Block> SAKURA_INCENSE = BLOCKS.register("sakura_incense", () -> new IncenseBlock(
            ModParticles.SAKURA_INCENSE_PARTICLE, () -> ParticleTypes.CHERRY_LEAVES
    ));

    DeferredBlock<Block> PINE_INCENSE = BLOCKS.register("pine_incense", () -> new IncenseBlock(
            ModParticles.PINE_INCENSE_PARTICLE, ModParticles.PINE_INCENSE_LARGE_PARTICLE
    ));

    DeferredBlock<Block> GINKGO_INCENSE = BLOCKS.register("ginkgo_incense", () -> new IncenseBlock(
            ModParticles.GINKGO_INCENSE_PARTICLE, ModParticles.GINKGO_INCENSE_LARGE_PARTICLE
    ));

    DeferredBlock<Block> SPORE_INCENSE = BLOCKS.register("spore_incense", () -> new IncenseBlock(
            ModParticles.SPORE_INCENSE_PARTICLE, () -> ParticleTypes.SPORE_BLOSSOM_AIR
    ));

    DeferredBlock<Block> CATNIP_INCENSE = BLOCKS.register("catnip_incense", () -> new IncenseBlock(
            ModParticles.CATNIP_INCENSE_PARTICLE, ModParticles.CATNIP_INCENSE_LARGE_PARTICLE
    ));

    DeferredBlock<Block> SNOW_INCENSE = BLOCKS.register("snow_incense", () -> new IncenseBlock(
            ModParticles.SNOW_INCENSE_PARTICLE, ModParticles.SNOW_INCENSE_LARGE_PARTICLE
    ));

    DeferredBlock<Block> BUTTERFLY_INCENSE = BLOCKS.register("butterfly_incense", () -> new IncenseBlock(
            ModParticles.BUTTERFLY_INCENSE_PARTICLE, ModParticles.BUTTERFLY_INCENSE_LARGE_PARTICLE
    ));

    DeferredBlock<Block> FIREFLY_INCENSE = BLOCKS.register("firefly_incense", () -> new IncenseBlock(
            ModParticles.FIREFLY_INCENSE_PARTICLE, ModParticles.FIREFLY_INCENSE_LARGE_PARTICLE,
            -0.67, 5.33
    ));

    // 吧台
    DeferredBlock<Block> BAR_COUNTER = BLOCKS.register("bar_counter", BarCounterBlock::new);
    // 人字梯
    DeferredBlock<Block> STEPLADDER = BLOCKS.register("stepladder", StepladderBlock::new);
    // 野生葡萄藤
    DeferredBlock<Block> WILD_GRAPEVINE = BLOCKS.register("wild_grapevine", WildGrapevineBlock::new);
    DeferredBlock<Block> WILD_GRAPEVINE_PLANT = BLOCKS.register("wild_grapevine_plant", WildGrapevinePlantBlock::new);
    // 藤架
    DeferredBlock<Block> TRELLIS = BLOCKS.register("trellis", TrellisBlock::new);
    // 葡萄藤
    DeferredBlock<Block> GRAPEVINE_TRELLIS = BLOCKS.register("grapevine_trellis", () -> new GrapevineTrellisBlock(
            (state, level, pos, random) -> 0.25F,
            () -> ModBlocks.GRAPE_CROP.get().defaultBlockState()
    ));
    DeferredBlock<Block> ICE_GRAPEVINE_TRELLIS = BLOCKS.register("ice_grapevine_trellis", () -> new GrapevineTrellisBlock(
            (state, level, pos, random) ->
                    level.getBiome(pos).value().getBaseTemperature() < 0.15F ? 0.8F : 0.25F,
            () -> ModBlocks.ICE_GRAPE_CROP.get().defaultBlockState()
    ));
    DeferredBlock<Block> GOLD_GRAPEVINE_TRELLIS = BLOCKS.register("gold_grapevine_trellis", () -> new GrapevineTrellisBlock(
            (state, level, pos, random) ->
                    level.getBiome(pos).value().getBaseTemperature() > 1.0F ? 0.8F : 0.25F,
            () -> ModBlocks.GOLD_GRAPE_CROP.get().defaultBlockState()
    ));
    // 葡萄
    DeferredBlock<Block> GRAPE_CROP = BLOCKS.register("grape_crop", () -> new GrapeCropBlock(
            (state, level, pos, random) -> 0.25F,
            () -> new ItemStack(ModItems.GRAPE.get(), 3)
    ));
    DeferredBlock<Block> ICE_GRAPE_CROP = BLOCKS.register("ice_grape_crop", () -> new GrapeCropBlock(
            (state, level, pos, random) ->
                    level.getBiome(pos).value().getBaseTemperature() < 0.15F ? 0.8F : 0.25F,
            () -> new ItemStack(ModItems.ICE_GRAPE.get(), 3)
    ));
    DeferredBlock<Block> GOLD_GRAPE_CROP = BLOCKS.register("gold_grape_crop", () -> new GrapeCropBlock(
            (state, level, pos, random) ->
                    level.getBiome(pos).value().getBaseTemperature() > 1.0F ? 0.8F : 0.25F,
            () -> new ItemStack(ModItems.GOLD_GRAPE.get(), 3)
    ));

    // 果盆
    DeferredBlock<Block> PRESSING_TUB = BLOCKS.register("pressing_tub", PressingTubBlock::new);
    // 龙头
    DeferredBlock<Block> TAP = BLOCKS.register("tap", TapBlock::new);

    // 酒杯架
    DeferredBlock<Block> GLASSWARE_HOLDER = BLOCKS.register("glassware_holder", GlasswareHolderBlock::new);
    // 空瓶
    DeferredBlock<Block> EMPTY_BOTTLE = BLOCKS.register("empty_bottle", () -> new BottleBlock());
    DeferredBlock<Block> EMPTY_GLASSWARE = BLOCKS.register("empty_glassware", () -> new GlasswareBlock());

    // 鸡尾酒
    DeferredBlock<Block> SIGNATURE_COCKTAIL = BLOCKS.register("signature_cocktail", SignatureCocktailBlock::new);
    DeferredBlock<Block> MYSTERY_COCKTAIL = BLOCKS.register("mystery_cocktail", MysteryCocktailBlock::new);
    DeferredBlock<Block> WHITE_LADY = BLOCKS.register("white_lady", () -> new CocktailBlock());
    DeferredBlock<Block> EMERALD = BLOCKS.register("emerald", () -> new CocktailBlock());
    DeferredBlock<Block> BRASS_HEART = BLOCKS.register("brass_heart", () -> new CocktailBlock());
    DeferredBlock<Block> GODFATHER = BLOCKS.register("godfather", () -> new CocktailBlock());
    DeferredBlock<Block> GRASSHOPPER = BLOCKS.register("grasshopper", () -> new CocktailBlock());
    DeferredBlock<Block> SCREWDRIVER = BLOCKS.register("screwdriver", () -> new CocktailBlock());
    DeferredBlock<Block> MOJITO = BLOCKS.register("mojito", () -> new CocktailBlock());
    DeferredBlock<Block> ALLIUM_GARDEN = BLOCKS.register("allium_garden", () -> new CocktailBlock());
    DeferredBlock<Block> DEPTH_CHARGE = BLOCKS.register("depth_charge", () -> new CocktailBlock());
    DeferredBlock<Block> NETHER_SPECIAL = BLOCKS.register("nether_special", () -> new CocktailBlock());
    DeferredBlock<Block> BLOODY_MARY = BLOCKS.register("bloody_mary", () -> new CocktailBlock());
    DeferredBlock<Block> SCULK_SPECIAL = BLOCKS.register("sculk_special", () -> new CocktailBlock());

    // 雪克杯
    DeferredBlock<Block> SHAKER = BLOCKS.register("shaker", ShakerBlock::new);
    // 燃烧瓶
    DeferredBlock<Block> MOLOTOV = BLOCKS.register("molotov", MolotovBlock::new);
    // 酒桶
    DeferredBlock<Block> BARREL = BLOCKS.register("barrel", BarrelBlock::new);
    // 酒柜
    DeferredBlock<Block> BAR_CABINET = BLOCKS.register("bar_cabinet", BarCabinetBlock::new);
    DeferredBlock<Block> GLASS_BAR_CABINET = BLOCKS.register("glass_bar_cabinet", BarCabinetBlock::new);
    DeferredBlock<Block> CELLAR_CABINET = BLOCKS.register("cellar_cabinet", CellarCabinetBlock::new);

    // 酒架
    DeferredBlock<Block> TILTED_RACK = BLOCKS.register("tilted_rack", TiltedRackBlock::new);
    DeferredBlock<Block> CIRCULAR_RACK = BLOCKS.register("circular_rack", CircularRackBlock::new);
    DeferredBlock<Block> HOLDER = BLOCKS.register("holder", HolderBlock::new);

    // 杂项的瓶子
    DeferredBlock<Block> WATER_BOTTLE = BLOCKS.register("water_bottle", BottleBlock::simpleBottle);
    DeferredBlock<Block> HONEY_BOTTLE = BLOCKS.register("honey_bottle", BottleBlock::simpleBottle);
    DeferredBlock<Block> DRAGON_BREATH_BOTTLE = BLOCKS.register("dragon_breath_bottle", BottleBlock::simpleBottle);
    DeferredBlock<Block> POTION_BOTTLE = BLOCKS.register("potion_bottle", PotionBottleBlock::new);
    DeferredBlock<Block> XP_BOTTLE = BLOCKS.register("xp_bottle", BottleBlock::simpleBottle);

    // 酒
    DeferredBlock<Block> WINE = BLOCKS.register("wine", DrinkBlock.create().maxCount(4).shapes(
            Block.box(6, 0, 6, 10, 16, 10),
            Block.box(2, 0, 6, 14, 16, 10),
            Shapes.or(
                    Block.box(2, 0, 10, 14, 16, 14),
                    Block.box(6, 0, 2, 10, 16, 14)
            ),
            Block.box(2, 0, 2, 14, 16, 14)
    ).build());

    DeferredBlock<Block> CHAMPAGNE = BLOCKS.register("champagne", DrinkBlock.create().maxCount(4).shapes(
            Block.box(6, 0, 6, 10, 16, 10),
            Block.box(2, 0, 6, 14, 16, 10),
            Shapes.or(
                    Block.box(2, 0, 10, 14, 16, 14),
                    Block.box(6, 0, 2, 10, 16, 14)
            ),
            Block.box(2, 0, 2, 14, 16, 14)
    ).build());

    DeferredBlock<Block> VODKA = BLOCKS.register("vodka", DrinkBlock.create().maxCount(4).shapes(
            Block.box(4, 0, 4, 12, 15, 12),
            Block.box(0, 0, 4, 16, 15, 12),
            Shapes.or(
                    Block.box(0, 0, 8, 16, 15, 16),
                    Block.box(4, 0, 0, 12, 15, 16)
            ),
            Block.box(0, 0, 0, 16, 16, 16)
    ).build());

    DeferredBlock<Block> BRANDY = BLOCKS.register("brandy", DrinkBlock.create().maxCount(3).shapes(
            Block.box(3, 0, 6, 13, 13, 10),
            Block.box(1, 0, 3, 15, 12, 12),
            Block.box(1, 0, 1, 16, 12, 13)
    ).build());

    DeferredBlock<Block> CARIGNAN = BLOCKS.register("carignan", DrinkBlock.create().maxCount(3).shapes(
            Block.box(3, 0, 6, 13, 12, 10),
            Block.box(1, 0, 3, 15, 12, 12),
            Block.box(0, 0, 1, 16, 12, 13)
    ).build());

    DeferredBlock<Block> SAKURA_WINE = BLOCKS.register("sakura_wine", DrinkBlock.create().maxCount(4).shapes(
            Block.box(6, 0, 6, 10, 16, 10),
            Block.box(2, 0, 6, 14, 16, 10),
            Shapes.or(
                    Block.box(2, 0, 10, 14, 16, 14),
                    Block.box(6, 0, 2, 10, 16, 14)
            ),
            Block.box(2, 0, 2, 14, 16, 14)
    ).build());

    DeferredBlock<Block> PLUM_WINE = BLOCKS.register("plum_wine", DrinkBlock.create().maxCount(4).shapes(
            Block.box(6, 0, 6, 10, 12, 10),
            Block.box(3, 0, 6, 13, 12, 10),
            Shapes.or(
                    Block.box(3, 0, 9, 13, 12, 13),
                    Block.box(6, 0, 3, 10, 12, 13)
            ),
            Block.box(3, 0, 3, 13, 12, 13)
    ).build());

    DeferredBlock<Block> WHISKEY = BLOCKS.register("whiskey", DrinkBlock.create().maxCount(4).shapes(
            Block.box(6, 0, 6, 10, 16, 10),
            Block.box(2, 0, 6, 14, 16, 10),
            Shapes.or(
                    Block.box(2, 0, 10, 14, 16, 14),
                    Block.box(6, 0, 2, 10, 16, 14)
            ),
            Block.box(2, 0, 2, 14, 16, 14)
    ).build());

    DeferredBlock<Block> ICE_WINE = BLOCKS.register("ice_wine", DrinkBlock.create().maxCount(4).shapes(
            Block.box(6, 0, 6, 10, 16, 10),
            Block.box(2, 0, 6, 14, 16, 10),
            Shapes.or(
                    Block.box(2, 0, 10, 14, 16, 14),
                    Block.box(6, 0, 2, 10, 16, 14)
            ),
            Block.box(2, 0, 2, 14, 16, 14)
    ).build());

    DeferredBlock<Block> POLARIS_SWEET_WHITE = BLOCKS.register("polaris_sweet_white", DrinkBlock.create().maxCount(4).shapes(
            Block.box(6, 0, 6, 10, 16, 10),
            Block.box(2, 0, 6, 14, 16, 10),
            Shapes.or(
                    Block.box(2, 0, 10, 14, 16, 14),
                    Block.box(6, 0, 2, 10, 16, 14)
            ),
            Block.box(2, 0, 2, 14, 16, 14)
    ).build());

    DeferredBlock<Block> HONEY_WINE = BLOCKS.register("honey_wine", DrinkBlock.create().maxCount(4).shapes(
            Block.box(6, 0, 6, 10, 16, 10),
            Block.box(2, 0, 6, 14, 16, 10),
            Shapes.or(
                    Block.box(2, 0, 10, 14, 16, 14),
                    Block.box(6, 0, 2, 10, 16, 14)
            ),
            Block.box(2, 0, 2, 14, 16, 14)
    ).build());

    DeferredBlock<Block> RED_QUEEN = BLOCKS.register("red_queen", DrinkBlock.create().maxCount(4).shapes(
            Block.box(6, 0, 6, 10, 16, 10),
            Block.box(2, 0, 6, 14, 16, 10),
            Shapes.or(
                    Block.box(2, 0, 10, 14, 16, 14),
                    Block.box(6, 0, 2, 10, 16, 14)
            ),
            Block.box(2, 0, 2, 14, 16, 14)
    ).build());

    DeferredBlock<Block> MINERS_STAR = BLOCKS.register("miners_star", DrinkBlock.create().maxCount(4).shapes(
            Block.box(6, 0, 6, 10, 16, 10),
            Block.box(2, 0, 6, 14, 16, 10),
            Shapes.or(
                    Block.box(2, 0, 10, 14, 16, 14),
                    Block.box(6, 0, 2, 10, 16, 14)
            ),
            Block.box(2, 0, 2, 14, 16, 14)
    ).build());

    DeferredBlock<Block> RUM = BLOCKS.register("rum", DrinkBlock.create().maxCount(4).shapes(
            Block.box(6, 0, 6, 10, 16, 10),
            Block.box(2, 0, 6, 14, 16, 10),
            Shapes.or(
                    Block.box(2, 0, 10, 14, 16, 14),
                    Block.box(6, 0, 2, 10, 16, 14)
            ),
            Block.box(2, 0, 2, 14, 16, 14)
    ).build());

    DeferredBlock<Block> RIESLING_DRY_WHITE = BLOCKS.register("riesling_dry_white", DrinkBlock.create().maxCount(4).shapes(
            Block.box(4, 0, 4, 12, 15, 12),
            Block.box(0, 0, 4, 16, 15, 12),
            Shapes.or(
                    Block.box(0, 0, 8, 16, 15, 16),
                    Block.box(4, 0, 0, 12, 15, 16)
            ),
            Block.box(0, 0, 0, 16, 16, 16)
    ).build());

    DeferredBlock<Block> SUNSET_GLOW = BLOCKS.register("sunset_glow", DrinkBlock.create().maxCount(3).shapes(
            Block.box(3, 0, 6, 13, 13, 10),
            Block.box(1, 0, 3, 15, 12, 12),
            Block.box(1, 0, 1, 16, 12, 13)
    ).build());

    DeferredBlock<Block> MADAME_SHEXIANG = BLOCKS.register("madame_shexiang", DrinkBlock.create().maxCount(4).shapes(
            Block.box(4, 0, 4, 12, 15, 12),
            Block.box(0, 0, 4, 16, 15, 12),
            Shapes.or(
                    Block.box(0, 0, 8, 16, 15, 16),
                    Block.box(4, 0, 0, 12, 15, 16)
            ),
            Block.box(0, 0, 0, 16, 16, 16)
    ).build());

    DeferredBlock<Block> SWEET_BERRY_WINE = BLOCKS.register("sweet_berry_wine", DrinkBlock.create().maxCount(4).shapes(
            Block.box(4, 0, 4, 12, 15, 12),
            Block.box(0, 0, 4, 16, 15, 12),
            Shapes.or(
                    Block.box(0, 0, 8, 16, 15, 16),
                    Block.box(4, 0, 0, 12, 15, 16)
            ),
            Block.box(0, 0, 0, 16, 16, 16)
    ).build());

    DeferredBlock<Block> SHERRY = BLOCKS.register("sherry", DrinkBlock.create().maxCount(4).shapes(
            Block.box(6, 0, 6, 10, 16, 10),
            Block.box(2, 0, 6, 14, 16, 10),
            Shapes.or(
                    Block.box(2, 0, 10, 14, 16, 14),
                    Block.box(6, 0, 2, 10, 16, 14)
            ),
            Block.box(2, 0, 2, 14, 16, 14)
    ).build());

    DeferredBlock<Block> MOTHER_SNOW = BLOCKS.register("mother_snow", DrinkBlock.create().maxCount(4).shapes(
            Block.box(4, 0, 4, 12, 15, 12),
            Block.box(0, 0, 4, 16, 15, 12),
            Shapes.or(
                    Block.box(0, 0, 8, 16, 15, 16),
                    Block.box(4, 0, 0, 12, 15, 16)
            ),
            Block.box(0, 0, 0, 16, 16, 16)
    ).build());

    DeferredBlock<Block> LUMINOUS_BRIDE = BLOCKS.register("luminous_bride", DrinkBlock.create().maxCount(4).shapes(
            Block.box(6, 0, 6, 10, 16, 10),
            Block.box(2, 0, 6, 14, 16, 10),
            Shapes.or(
                    Block.box(2, 0, 10, 14, 16, 14),
                    Block.box(6, 0, 2, 10, 16, 14)
            ),
            Block.box(2, 0, 2, 14, 16, 14)
    ).build());

    DeferredBlock<Block> GLOWFLOWER_BREW = BLOCKS.register("glowflower_brew", DrinkBlock.create().maxCount(4).shapes(
            Block.box(6, 0, 6, 10, 16, 10),
            Block.box(2, 0, 6, 14, 16, 10),
            Shapes.or(
                    Block.box(2, 0, 10, 14, 16, 14),
                    Block.box(6, 0, 2, 10, 16, 14)
            ),
            Block.box(2, 0, 2, 14, 16, 14)
    ).build());

    DeferredBlock<Block> SAUVIGNON_BLANC_DRY_WHITE = BLOCKS.register("sauvignon_blanc_dry_white", DrinkBlock.create().maxCount(4).shapes(
            Block.box(6, 0, 6, 10, 16, 10),
            Block.box(2, 0, 6, 14, 16, 10),
            Shapes.or(
                    Block.box(2, 0, 10, 14, 16, 14),
                    Block.box(6, 0, 2, 10, 16, 14)
            ),
            Block.box(2, 0, 2, 14, 16, 14)
    ).build());
    DeferredBlock<Block> VINEGAR = BLOCKS.register("vinegar", DrinkBlock.create().maxCount(4).shapes(
            Block.box(6, 0, 6, 10, 16, 10),
            Block.box(2, 0, 6, 14, 16, 10),
            Shapes.or(
                    Block.box(2, 0, 10, 14, 16, 14),
                    Block.box(6, 0, 2, 10, 16, 14)
            ),
            Block.box(2, 0, 2, 14, 16, 14)
    ).build());

    DeferredBlock<Block> WATERMELON_JUICE = BLOCKS.register("watermelon_juice", DrinkBlock.create().maxCount(4).shapes(
            Block.box(6, 0, 6, 10, 16, 10),
            Block.box(2, 0, 6, 14, 16, 10),
            Shapes.or(
                    Block.box(2, 0, 10, 14, 16, 14),
                    Block.box(6, 0, 2, 10, 16, 14)
            ),
            Block.box(2, 0, 2, 14, 16, 14)
    ).build());

    // BlockEntity
    Supplier<BlockEntityType<ChalkboardBlockEntity>> CHALKBOARD_BE = BLOCK_ENTITIES.register(
            "chalkboard", () -> BlockEntityType.Builder
                    .of(ChalkboardBlockEntity::new, CHALKBOARD.get())
                    .build(null)
    );


    Supplier<BlockEntityType<BarStoolBlockEntity>> BAR_STOOL_BE = BLOCK_ENTITIES.register(
            "bar_stool", () -> BlockEntityType.Builder.of(BarStoolBlockEntity::new,
                    BLUE_BAR_STOOL.get(),
                    GREEN_BAR_STOOL.get(),
                    ORANGE_BAR_STOOL.get(),
                    PURPLE_BAR_STOOL.get(),
                    YELLOW_BAR_STOOL.get(),
                    BLACK_BAR_STOOL.get(),
                    WHITE_BAR_STOOL.get(),
                    GRAY_BAR_STOOL.get(),
                    BROWN_BAR_STOOL.get(),
                    LIME_BAR_STOOL.get(),
                    MAGENTA_BAR_STOOL.get(),
                    CYAN_BAR_STOOL.get(),
                    LIGHT_BLUE_BAR_STOOL.get(),
                    PINK_BAR_STOOL.get(),
                    LIGHT_GRAY_BAR_STOOL.get(),
                    RED_BAR_STOOL.get()).build(null)
    );

    Supplier<BlockEntityType<SandwichBlockEntity>> SANDWICH_BOARD_BE = BLOCK_ENTITIES.register(
            "sandwich_board", () -> BlockEntityType.Builder.of(SandwichBlockEntity::new,
                    BASE_SANDWICH_BOARD.get(),
                    GRASS_SANDWICH_BOARD.get(),
                    ALLIUM_SANDWICH_BOARD.get(),
                    AZURE_BLUET_SANDWICH_BOARD.get(),
                    CORNFLOWER_SANDWICH_BOARD.get(),
                    ORCHID_SANDWICH_BOARD.get(),
                    PEONY_SANDWICH_BOARD.get(),
                    PINK_PETALS_SANDWICH_BOARD.get(),
                    PITCHER_PLANT_SANDWICH_BOARD.get(),
                    POPPY_SANDWICH_BOARD.get(),
                    SUNFLOWER_SANDWICH_BOARD.get(),
                    TORCHFLOWER_SANDWICH_BOARD.get(),
                    TULIP_SANDWICH_BOARD.get(),
                    WITHER_ROSE_SANDWICH_BOARD.get()
            ).build(null)
    );

    Supplier<BlockEntityType<PressingTubBlockEntity>> PRESSING_TUB_BE = BLOCK_ENTITIES.register(
            "pressing_tub", () -> BlockEntityType.Builder
                    .of(PressingTubBlockEntity::new, PRESSING_TUB.get())
                    .build(null)
    );

    Supplier<BlockEntityType<DrinkBlockEntity>> DRINK_BE = BLOCK_ENTITIES.register(
            "drink", () -> BlockEntityType.Builder
                    .of(DrinkBlockEntity::new,
                            WINE.get(), CHAMPAGNE.get(), VODKA.get(), BRANDY.get(), CARIGNAN.get(),
                            SAKURA_WINE.get(), PLUM_WINE.get(), WHISKEY.get(), ICE_WINE.get(),
                            POLARIS_SWEET_WHITE.get(), HONEY_WINE.get(), RED_QUEEN.get(), MINERS_STAR.get(),
                            RUM.get(), RIESLING_DRY_WHITE.get(), SUNSET_GLOW.get(), MADAME_SHEXIANG.get(),
                            SWEET_BERRY_WINE.get(), SHERRY.get(), MOTHER_SNOW.get(), LUMINOUS_BRIDE.get(),
                            GLOWFLOWER_BREW.get(), SAUVIGNON_BLANC_DRY_WHITE.get(), VINEGAR.get(),
                            WATERMELON_JUICE.get()
                    ).build(null)
    );

    Supplier<BlockEntityType<BarrelBlockEntity>> BARREL_BE = BLOCK_ENTITIES.register(
            "barrel", () -> BlockEntityType.Builder
                    .of(BarrelBlockEntity::new, BARREL.get())
                    .build(null)
    );

    Supplier<BlockEntityType<TapBlockEntity>> TAP_BE = BLOCK_ENTITIES.register(
            "tap", () -> BlockEntityType.Builder
                    .of(TapBlockEntity::new, TAP.get())
                    .build(null)
    );

    Supplier<BlockEntityType<BarCabinetBlockEntity>> BAR_CABINET_BE = BLOCK_ENTITIES.register(
            "bar_cabinet", () -> BlockEntityType.Builder
                    .of(BarCabinetBlockEntity::new, BAR_CABINET.get(), GLASS_BAR_CABINET.get())
                    .build(null)
    );

    Supplier<BlockEntityType<CellarCabinetBlockEntity>> CELLAR_CABINET_BE = BLOCK_ENTITIES.register(
            "cellar_cabinet", () -> BlockEntityType.Builder
                    .of(CellarCabinetBlockEntity::new, CELLAR_CABINET.get())
                    .build(null)
    );

    Supplier<BlockEntityType<TiltedRackBlockEntity>> TILTED_RACK_BE = BLOCK_ENTITIES.register(
            "tilted_rack", () -> BlockEntityType.Builder
                    .of(TiltedRackBlockEntity::new, TILTED_RACK.get())
                    .build(null)
    );

    Supplier<BlockEntityType<CircularRackBlockEntity>> CIRCULAR_RACK_BE = BLOCK_ENTITIES.register(
            "circular_rack", () -> BlockEntityType.Builder
                    .of(CircularRackBlockEntity::new, CIRCULAR_RACK.get())
                    .build(null)
    );

    Supplier<BlockEntityType<HolderBlockEntity>> HOLDER_BE = BLOCK_ENTITIES.register(
            "holder", () -> BlockEntityType.Builder
                    .of(HolderBlockEntity::new, HOLDER.get())
                    .build(null)
    );

    Supplier<BlockEntityType<ShakerBlockEntity>> SHAKER_BE = BLOCK_ENTITIES.register(
            "shaker", () -> BlockEntityType.Builder
                    .of(ShakerBlockEntity::new, SHAKER.get())
                    .build(null)
    );

    Supplier<BlockEntityType<SignatureCocktailBlockEntity>> SIGNATURE_COCKTAIL_BE = BLOCK_ENTITIES.register(
            "signature_cocktail", () -> BlockEntityType.Builder
                    .of(SignatureCocktailBlockEntity::new, SIGNATURE_COCKTAIL.get())
                    .build(null)
    );

    Supplier<BlockEntityType<IncenseBlockEntity>> INCENSE_BE = BLOCK_ENTITIES.register(
            "incense", () -> BlockEntityType.Builder
                    .of(IncenseBlockEntity::new,
                            SAKURA_INCENSE.get(), PINE_INCENSE.get(), GINKGO_INCENSE.get(), SPORE_INCENSE.get(),
                            CATNIP_INCENSE.get(), SNOW_INCENSE.get(), BUTTERFLY_INCENSE.get(), FIREFLY_INCENSE.get()
                    ).build(null)
    );

    Supplier<BlockEntityType<GlasswareHolderBlockEntity>> GLASSWARE_HOLDER_BE = BLOCK_ENTITIES.register(
            "glassware_holder", () -> BlockEntityType.Builder
                    .of(GlasswareHolderBlockEntity::new, GLASSWARE_HOLDER.get())
                    .build(null)
    );

    Supplier<BlockEntityType<PotionBottleBlockEntity>> POTION_BOTTLE_BE = BLOCK_ENTITIES.register(
            "potion_bottle", () -> BlockEntityType.Builder
                    .of(PotionBottleBlockEntity::new, POTION_BOTTLE.get())
                    .build(null)
    );
}