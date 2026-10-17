package com.github.ysbbbbbb.kaleidoscopetavern.item;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.mixology.SignatureCocktailBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.crafting.container.SimpleInput;
import com.github.ysbbbbbb.kaleidoscopetavern.datamap.data.DrinkEffectData;
import com.github.ysbbbbbb.kaleidoscopetavern.init.*;
import com.github.ysbbbbbb.kaleidoscopetavern.network.NetworkHandler;
import com.github.ysbbbbbb.kaleidoscopetavern.network.message.ShakerTimeC2SMessage;
import com.github.ysbbbbbb.kaleidoscopetavern.network.message.ShakerTimeS2CMessage;
import com.github.ysbbbbbb.kaleidoscopetavern.util.CocktailEffectHelper;
import com.github.ysbbbbbb.kaleidoscopetavern.util.ColorUtils;
import com.github.ysbbbbbb.kaleidoscopetavern.util.ShakerTime;
import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.items.ItemStackHandler;

import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import static com.github.ysbbbbbb.kaleidoscopetavern.block.mixology.GlasswareBlock.FACING;

@EventBusSubscriber(modid = KaleidoscopeTavern.MOD_ID)
public class ShakerItem extends BlockItem {
    private static final int STORAGE_SIZE = 3;

    // 单人游戏两端的实体编号可能相同，分别保存计时状态
    private static final Map<LivingEntity, ShakingData> CLIENT_SHAKING_DATA = new WeakHashMap<>();
    private static final Map<LivingEntity, ShakingData> SERVER_SHAKING_DATA = new WeakHashMap<>();

    private static class ShakingData {
        private final ItemStack stack;
        private final InteractionHand hand;
        private int id;
        private boolean startSent;
        private long startTime = System.nanoTime();
        private long lastSyncTime = startTime;
        private long stopTime;
        private int latency;
        private int jitter;
        private int syncedTime = -1;
        private long receivedTime;
        private double previousProgress;

        private ShakingData(ItemStack stack, InteractionHand hand, int id) {
            this.stack = stack;
            this.hand = hand;
            this.id = id;
        }
    }

    public ShakerItem() {
        super(ModBlocks.SHAKER.get(), new Properties().stacksTo(1));
    }

    private static Map<LivingEntity, ShakingData> getShakingData(LivingEntity entity) {
        return entity.level().isClientSide ? CLIENT_SHAKING_DATA : SERVER_SHAKING_DATA;
    }

    public static ItemStackHandler getStorage(ItemStack stack) {
        ItemStackHandler handler = new ItemStackHandler(STORAGE_SIZE);
        ItemContainerContents contents = stack.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY);
        for (int i = 0; i < Math.min(handler.getSlots(), contents.getSlots()); i++) {
            handler.setStackInSlot(i, contents.getStackInSlot(i));
        }
        return handler;
    }

    public static void setStorage(ItemStack stack, ItemStackHandler handler) {
        List<ItemStack> items = Lists.newArrayList();
        for (int i = 0; i < handler.getSlots(); i++) {
            items.add(handler.getStackInSlot(i));
        }
        stack.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(items));
    }

    public static boolean hasStorage(ItemStack stack) {
        return stack.has(DataComponents.CONTAINER);
    }

    public static ItemStack getResult(ItemStack stack) {
        return stack.getOrDefault(ModDataComponents.SHAKER_RESULT, Result.EMPTY).stack();
    }

    public static void setResult(ItemStack stack, ItemStack result) {
        stack.set(ModDataComponents.SHAKER_RESULT, new Result(result.copy()));
    }

    public static boolean hasResult(ItemStack stack) {
        return stack.has(ModDataComponents.SHAKER_RESULT) && !getResult(stack).isEmpty();
    }

    public static void removeAll(ItemStack stack) {
        stack.remove(ModDataComponents.SHAKER_RESULT);
        stack.remove(DataComponents.CONTAINER);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        ItemStack stack = context.getItemInHand();
        BlockPos pos = context.getClickedPos();

        // 如果有产物，并且点击的是空鸡尾酒杯，尝试倒出
        BlockState rawState = level.getBlockState(pos);
        if (hasResult(stack) && rawState.is(ModBlocks.EMPTY_GLASSWARE.get())) {
            pourResult(level, stack, pos, rawState);
            return InteractionResult.SUCCESS;
        }

        return super.useOn(context);
    }

    /**
     * 将雪克杯中的调酒产物倒入空酒杯方块。
     * 放置对应鸡尾酒方块，若为特调鸡尾酒则计算合成效果和混合颜色。
     */
    private void pourResult(Level level, ItemStack stack, BlockPos pos, BlockState rawState) {
        ItemStack result = getResult(stack);

        // 替换成鸡尾酒方块
        if (result.getItem() instanceof BlockItem blockItem) {
            BlockState blockState = blockItem.getBlock().defaultBlockState();
            if (blockState.hasProperty(FACING)) {
                blockState = blockState.setValue(FACING, rawState.getValue(FACING));
            }
            level.setBlockAndUpdate(pos, blockState);
        }

        // 如果是特调鸡尾酒，需要计算效果和颜色
        if (level.getBlockEntity(pos) instanceof SignatureCocktailBlockEntity ordinary) {
            ItemStackHandler storage = getStorage(stack);

            // 数据计算
            CocktailEffectHelper.CollectedData data = CocktailEffectHelper.collectFromStorage(storage);
            List<DrinkEffectData.Entry> mergedEffects = CocktailEffectHelper.mergeEffects(data.effects());
            int finalColor = ColorUtils.mixColors(data.colors());

            ordinary.setColor(finalColor);
            ordinary.setEffects(mergedEffects);
            ordinary.refresh();
        }

        // 清除雪克杯数据
        removeAll(stack);

        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
                    ParticleTypes.EFFECT,
                    pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                    20, 0.1, 0.1, 0.1, 0.5
            );
            serverLevel.playSound(null, pos, SoundEvents.BOTTLE_FILL,
                    SoundSource.BLOCKS, 1.0F, 1.0F);
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemInHand = player.getItemInHand(hand);
        ShakingData previous = getShakingData(player).get(player);
        // 松手后先等待本次用时报告，不能在结算前重新开始
        if (previous != null && previous.stopTime != 0) {
            return InteractionResultHolder.pass(itemInHand);
        }
        // 如果已经有结果了，不能再摇了
        if (hasResult(itemInHand)) {
            return InteractionResultHolder.pass(itemInHand);
        }

        // 物品数量检查
        if (!hasStorageItem(itemInHand)) {
            return InteractionResultHolder.pass(itemInHand);
        }

        // 检查是否三瓶全满
        ItemStackHandler storage = getStorage(itemInHand);
        for (int i = 0; i < storage.getSlots(); i++) {
            ItemStack stack = storage.getStackInSlot(i);
            if (!stack.isEmpty()) {
                continue;
            }
            if (player instanceof ServerPlayer serverPlayer) {
                Component message = Component.translatable("message.kaleidoscope_tavern.shaker.amount_too_low");
                serverPlayer.connection.send(new ClientboundSetActionBarTextPacket(message));
            }
            return InteractionResultHolder.pass(itemInHand);
        }

        // 然后才能摇
        player.startUsingItem(hand);
        if (player.getUseItem() != itemInHand) {
            return InteractionResultHolder.pass(itemInHand);
        }
        int id = level.isClientSide ? level.random.nextInt(Integer.MAX_VALUE - 1) + 1 : 0;
        getShakingData(player).put(player, new ShakingData(itemInHand, hand, id));
        return InteractionResultHolder.consume(itemInHand);
    }

    private boolean hasStorageItem(ItemStack itemInHand) {
        if (!hasStorage(itemInHand)) {
            return false;
        }
        ItemStackHandler storage = getStorage(itemInHand);
        for (int i = 0; i < storage.getSlots(); i++) {
            ItemStack stack = storage.getStackInSlot(i);
            if (!stack.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int remainingUseDuration) {
        if (remainingUseDuration % 10 == 0) {
            entity.playSound(ModSounds.SHAKER_SHAKING.get(),
                    level.random.nextFloat() * 0.2F + 0.75F,
                    level.random.nextFloat() * 0.2F + 0.8F
            );
        }
        ShakingData data = getShakingData(entity).get(entity);
        if (data == null) {
            return;
        }
        if (level.isClientSide) {
            // use 内的原版使用包尚未发出，等首次使用 tick 再报告开始
            if (!data.startSent) {
                data.startSent = true;
                NetworkHandler.sendToServer(new ShakerTimeC2SMessage(data.id, -1));
            }
            return;
        }
        long now = System.nanoTime();
        // 超出时长，强制中断，给在途的松手消息留出有限容错
        if (ShakerTime.elapsedMillis(data.startTime, now) > ShakerTime.MAX_TIME + ShakerTime.MAX_TOLERANCE) {
            finishShaking(entity, data, ShakerTime.elapsedMillis(data.startTime, now));
            entity.stopUsingItem();
        } else if (data.id != 0 && ShakerTime.elapsedMillis(data.lastSyncTime, now) >= ShakerTime.SYNC_INTERVAL) {
            syncTime(entity, data, now);
        }
    }

    /**
     * < 950 毫秒未开始
     * < 3450 毫秒迷之鸡尾酒
     * < 4450 毫秒普通鸡尾酒
     * < 4950 毫秒特调鸡尾酒
     * >= 4950 毫秒迷之鸡尾酒
     */
    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
        ShakingData data = getShakingData(entity).get(entity);
        if (data == null) {
            return;
        }
        if (level.isClientSide) {
            // 报告指针对应的用时，使有限校正后的显示与松手判定一致
            int time = (int) (data.syncedTime < 0
                    ? Math.min(ShakerTime.MAX_TIME, ShakerTime.elapsedMillis(data.startTime, System.nanoTime()))
                    : getProgress(entity));
            getShakingData(entity).remove(entity);
            NetworkHandler.sendToServer(new ShakerTimeC2SMessage(data.id, time));
            if (time >= 950) {
                entity.playSound(ModSounds.SHAKER_END.get());
            }
        } else {
            // 原版松手包先到，用时报告到达后再结算
            data.stopTime = System.nanoTime();
        }
    }

    public static void handleTime(ServerPlayer player, int id, int time, long receivedTime) {
        ShakingData data = getShakingData(player).get(player);
        if (data == null || !isValid(player, data) || id <= 0
                || (data.stopTime == 0 && player.getUseItem() != data.stack)) {
            return;
        }
        if (time == -1) {
            if (data.id == 0 && data.stopTime == 0) {
                data.id = id;
                data.startTime = receivedTime;
                data.latency = Math.max(0, player.connection.latency());
                syncTime(player, data, System.nanoTime());
            }
            return;
        }
        if (data.id != id || time < 0 || time > ShakerTime.MAX_TIME) {
            return;
        }
        long serverTime = ShakerTime.elapsedMillis(data.startTime, receivedTime);
        int tolerance = ShakerTime.getTolerance(player.connection.latency(), data.jitter);
        finishShaking(player, data, ShakerTime.selectTime(serverTime, time, tolerance));
        player.stopUsingItem();
    }

    private static boolean isValid(LivingEntity entity, ShakingData data) {
        return entity.isAlive() && entity.getItemInHand(data.hand) == data.stack
                && data.stack.is(ModItems.SHAKER.get()) && hasStorage(data.stack) && !hasResult(data.stack);
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        ShakingData data = getShakingData(player).get(player);
        if (data == null) {
            return;
        }
        if (!isValid(player, data) || (data.stopTime == 0 && player.getUseItem() != data.stack)) {
            getShakingData(player).remove(player);
        } else if (!player.level().isClientSide && data.stopTime != 0
                && ShakerTime.elapsedMillis(data.stopTime, System.nanoTime()) >= ShakerTime.MAX_TOLERANCE) {
            // 未收到用时报告时，使用松手时记录的服务端用时
            finishShaking(player, data, ShakerTime.elapsedMillis(data.startTime, data.stopTime));
        }
    }

    private static void syncTime(LivingEntity entity, ShakingData data, long now) {
        if (entity instanceof ServerPlayer player) {
            int latency = Math.max(0, player.connection.latency());
            data.jitter = Math.max(Math.abs(latency - data.latency), data.jitter * 9 / 10);
            data.latency = latency;
            data.lastSyncTime = now;
            int time = (int) Math.min(ShakerTime.MAX_TIME, ShakerTime.elapsedMillis(data.startTime, now));
            NetworkHandler.sendToClient(player, new ShakerTimeS2CMessage(data.id, time,
                    Math.min(ShakerTime.MAX_TOLERANCE, latency / 2)));
        }
    }

    public static void syncTime(Player player, int id, int time, int delay, long receivedTime) {
        ShakingData data = getShakingData(player).get(player);
        if (data == null || data.id != id || player.getUseItem() != data.stack) {
            return;
        }
        data.previousProgress = getProgress(player);
        data.syncedTime = Math.min(ShakerTime.MAX_TIME, time + delay);
        data.receivedTime = receivedTime;
    }

    public static double getProgress(LivingEntity entity) {
        ShakingData data = getShakingData(entity).get(entity);
        if (data == null || data.syncedTime < 0) {
            return 0;
        }
        return ShakerTime.getProgress(data.previousProgress, data.syncedTime,
                ShakerTime.elapsedMillis(data.receivedTime, System.nanoTime()));
    }

    private static void finishShaking(LivingEntity entity, ShakingData data, long time) {
        getShakingData(entity).remove(entity);
        if (!isValid(entity, data) || time < 950) {
            return;
        }
        ItemStack stack = data.stack;
        Level level = entity.level();
        ItemStackHandler storage = getStorage(stack);
        for (int i = 0; i < storage.getSlots(); i++) {
            if (storage.getStackInSlot(i).isEmpty()) {
                return;
            }
        }
        // 调酒
        if (!level.isClientSide) {
            if (time < 3450) {
                setResult(stack, new ItemStack(ModItems.MYSTERY_COCKTAIL.get()));
            } else if (time < 4450) {
                setResult(stack, new ItemStack(ModItems.SIGNATURE_COCKTAIL.get()));
            } else if (time < 4950) {
                handRecipe(level, stack);
            } else {
                setResult(stack, new ItemStack(ModItems.MYSTERY_COCKTAIL.get()));
            }
        }
        entity.playSound(ModSounds.SHAKER_END.get());
    }

    private static void handRecipe(Level level, ItemStack stack) {
        ItemStackHandler handler = getStorage(stack);
        List<ItemStack> stacks = Lists.newArrayList();
        for (int i = 0; i < handler.getSlots(); i++) {
            ItemStack ingredient = handler.getStackInSlot(i);
            if (ingredient.isEmpty()) {
                continue;
            }
            stacks.add(ingredient);
        }

        SimpleInput container = new SimpleInput(stacks);
        RecipeManager manager = level.getRecipeManager();
        RegistryAccess access = level.registryAccess();

        manager.getRecipeFor(ModRecipes.SHAKER_RECIPE, container, level).ifPresentOrElse(
                recipe -> setResult(stack, recipe.value().assemble(container, access)),
                () -> setResult(stack, new ItemStack(ModItems.SIGNATURE_COCKTAIL.get()))
        );
    }

    @Override
    public int getUseDuration(ItemStack pStack, LivingEntity entity) {
        return 72000;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        if (hasResult(stack)) {
            ItemStack result = getResult(stack);
            if (result.getHoverName() instanceof MutableComponent component) {
                MutableComponent text = component.withStyle(ChatFormatting.GRAY);
                tooltip.add(Component.literal("▶ ")
                        .withStyle(ChatFormatting.GRAY)
                        .append(text));
            }
        } else if (hasStorage(stack)) {
            ItemStackHandler storage = getStorage(stack);
            for (int i = 0; i < storage.getSlots(); i++) {
                ItemStack ingredient = storage.getStackInSlot(i);
                if (ingredient.isEmpty()) {
                    continue;
                }
                if (ingredient.getHoverName() instanceof MutableComponent component) {
                    ChatFormatting chatFormatting = ColorUtils.ITEM_COLOR_CACHE.apply(ingredient.getItem());
                    if (chatFormatting == ChatFormatting.RESET) {
                        chatFormatting = ChatFormatting.GRAY;
                    }
                    MutableComponent text = component.withStyle(chatFormatting);
                    tooltip.add(Component.literal("▶ ")
                            .withStyle(ChatFormatting.GRAY)
                            .append(text));
                }
            }
        }
    }

    public record Result(ItemStack stack) {
        public static final Codec<Result> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                ItemStack.OPTIONAL_CODEC.fieldOf("stack").forGetter(Result::stack)
        ).apply(instance, Result::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, Result> STREAM_CODEC = StreamCodec.composite(
                ItemStack.OPTIONAL_STREAM_CODEC, Result::stack, Result::new
        );

        public static final Result EMPTY = new Result(ItemStack.EMPTY);
    }
}
