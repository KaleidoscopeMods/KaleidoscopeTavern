package com.github.ysbbbbbb.kaleidoscopetavern.event;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.worldgen.WildGrapevineDecorator;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = KaleidoscopeTavern.MOD_ID)
public class AddFeaturesEvent {
    @SubscribeEvent
    public static void addFeatures(ServerAboutToStartEvent event) {
        var access = event.getServer().registryAccess();

        access.registry(Registries.CONFIGURED_FEATURE).ifPresent(registry -> {
            addWildGrapevineTreeDeco(registry, TreeFeatures.SUPER_BIRCH_BEES_0002, 0.002f, 3);
            addWildGrapevineTreeDeco(registry, TreeFeatures.SUPER_BIRCH_BEES, 1f, 1);

            addWildGrapevineTreeDeco(registry, TreeFeatures.OAK_BEES_0002, 0.002f, 3);
            addWildGrapevineTreeDeco(registry, TreeFeatures.OAK_BEES_002, 0.02f, 2);
            addWildGrapevineTreeDeco(registry, TreeFeatures.OAK_BEES_005, 0.05f, 1);

            addWildGrapevineTreeDeco(registry, TreeFeatures.BIRCH_BEES_0002, 0.002f, 3);
            addWildGrapevineTreeDeco(registry, TreeFeatures.BIRCH_BEES_002, 0.02f, 2);
            addWildGrapevineTreeDeco(registry, TreeFeatures.BIRCH_BEES_005, 0.05f, 1);

            addWildGrapevineTreeDeco(registry, TreeFeatures.FANCY_OAK_BEES_0002, 0.002f, 3);
            addWildGrapevineTreeDeco(registry, TreeFeatures.FANCY_OAK_BEES_002, 0.02f, 2);
            addWildGrapevineTreeDeco(registry, TreeFeatures.FANCY_OAK_BEES_005, 0.05f, 1);
            addWildGrapevineTreeDeco(registry, TreeFeatures.FANCY_OAK_BEES, 1f, 1);

            // 樱花树下不生成葡萄藤了，毕竟它们的树叶颜色不太搭
        });
    }

    private static void addWildGrapevineTreeDeco(
            Registry<ConfiguredFeature<?, ?>> registry,
            ResourceKey<ConfiguredFeature<?, ?>> id,
            float probability,
            int maxVines
    ) {
        ConfiguredFeature<?, ?> configuredFeature = registry.get(id);
        if (configuredFeature == null) {
            return;
        }
        FeatureConfiguration config = configuredFeature.config();
        if (config instanceof TreeConfiguration treeConfiguration) {
            // 因为原 list 是 ImmutableList，所以只能复制一份新的 list 出来添加装饰器
            treeConfiguration.decorators = ImmutableList.<TreeDecorator>builder()
                    .addAll(treeConfiguration.decorators)
                    .add(new WildGrapevineDecorator(probability, maxVines))
                    .build();
        }
    }
}
