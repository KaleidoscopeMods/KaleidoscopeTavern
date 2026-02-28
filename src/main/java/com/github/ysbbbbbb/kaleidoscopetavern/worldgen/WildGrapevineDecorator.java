package com.github.ysbbbbbb.kaleidoscopetavern.worldgen;

import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModTreeDecoratorTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class WildGrapevineDecorator extends TreeDecorator {
    public static final Codec<WildGrapevineDecorator> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter(d -> d.probability),
                    Codec.intRange(1, 16).fieldOf("max_vines").forGetter(d -> d.maxVines)
            ).apply(instance, WildGrapevineDecorator::new)
    );

    private final float probability;
    private final int maxVines;

    public WildGrapevineDecorator(float probability, int maxVines) {
        this.probability = probability;
        this.maxVines = maxVines;
    }

    @Override
    protected TreeDecoratorType<?> type() {
        return ModTreeDecoratorTypes.WILD_GRAPEVINE.get();
    }

    @Override
    public void place(Context context) {
        RandomSource random = context.random();

        // 尝试生成葡萄藤的数量，至少 1 条，最多 maxVines 条
        int vineCount = random.nextInt(this.maxVines) + 1;
        // 记录实际成功生成的藤链数量，超过 vineCount 就停止
        int[] recordCount = new int[]{0};

        context.leaves().forEach(pos -> {
            if (recordCount[0] >= vineCount) {
                return;
            }
            if (random.nextFloat() < this.probability) {
                // 筛选下方两格都为空气的位置，作为藤链的候选位置
                boolean belowIsAir = context.isAir(pos.below());
                boolean belowTwoIsAir = context.isAir(pos.below(2));
                // 尝试生成长度为 1-3 的葡萄藤
                if (belowIsAir && belowTwoIsAir) {
                    placeVineChain(context, pos.below(), random.nextInt(3) + 1);
                    recordCount[0]++;
                }
            }
        });
    }

    private void placeVineChain(Context context, BlockPos startPos, int chainLength) {
        BlockPos pos = startPos;
        for (int i = 0; i < chainLength; i++) {
            if (!context.isAir(pos)) {
                break;
            }
            if (i == chainLength - 1 || !context.isAir(pos.below())) {
                // 最底部放 head（向下生长端）

                break;
            } else {
                // 上方放 body（植物体）
                context.setBlock(pos, ModBlocks.WILD_GRAPEVINE_PLANT.get().defaultBlockState());
            }
            pos = pos.below();
        }
    }
}
