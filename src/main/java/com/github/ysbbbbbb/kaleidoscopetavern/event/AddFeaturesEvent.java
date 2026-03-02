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
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;

@EventBusSubscriber(modid = KaleidoscopeTavern.MOD_ID)
public class AddFeaturesEvent {
    @SubscribeEvent
    public static void addFeatures(ServerAboutToStartEvent event) {
        var access = event.getServer().registryAccess();

        access.registry(Registries.CONFIGURED_FEATURE).ifPresent(registry -> {
            addWildGrapevineTreeDeco(registry, TreeFeatures.SUPER_BIRCH_BEES_0002, 0.002f, 3, 3);
            addWildGrapevineTreeDeco(registry, TreeFeatures.SUPER_BIRCH_BEES, 1f, 1, 3);

            addWildGrapevineTreeDeco(registry, TreeFeatures.OAK_BEES_0002, 0.002f, 3, 3);
            addWildGrapevineTreeDeco(registry, TreeFeatures.OAK_BEES_002, 0.02f, 2, 3);
            addWildGrapevineTreeDeco(registry, TreeFeatures.OAK_BEES_005, 0.05f, 1, 3);

            addWildGrapevineTreeDeco(registry, TreeFeatures.BIRCH_BEES_0002, 0.002f, 3, 3);
            addWildGrapevineTreeDeco(registry, TreeFeatures.BIRCH_BEES_002, 0.02f, 2, 3);
            addWildGrapevineTreeDeco(registry, TreeFeatures.BIRCH_BEES_005, 0.05f, 1, 3);

            // 高大橡树会生成较多的葡萄藤
            addWildGrapevineTreeDeco(registry, TreeFeatures.FANCY_OAK_BEES_0002, 0.02f, 5, 7);
            addWildGrapevineTreeDeco(registry, TreeFeatures.FANCY_OAK_BEES_002, 0.2f, 5, 7);
            addWildGrapevineTreeDeco(registry, TreeFeatures.FANCY_OAK_BEES_005, 0.5f, 5, 7);
            addWildGrapevineTreeDeco(registry, TreeFeatures.FANCY_OAK_BEES, 1f, 5, 7);

            // 樱花树下不生成葡萄藤了，毕竟它们的树叶颜色不太搭
        });
    }

    private static void addWildGrapevineTreeDeco(
            Registry<ConfiguredFeature<?, ?>> registry,
            ResourceKey<ConfiguredFeature<?, ?>> id,
            float probability,
            int maxVineCount,
            int vineChainLength
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
                    .add(new WildGrapevineDecorator(probability, maxVineCount, vineChainLength))
                    .build();
        }
    }
}
