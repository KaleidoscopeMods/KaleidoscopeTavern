package com.github.ysbbbbbb.kaleidoscopetavern.client.gui.overlay;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.mixology.ShakerBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopetavern.util.ColorUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Objects;

public class ShakerOverlay implements IGuiOverlay {
    private static final ResourceLocation IMG = new ResourceLocation(KaleidoscopeTavern.MOD_ID, "textures/gui/shaker.png");
    private static final ResourceLocation ICON = new ResourceLocation(KaleidoscopeTavern.MOD_ID, "gui/rhombus");

    @Override
    public void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {
        Minecraft minecraft = gui.getMinecraft();
        if (minecraft.gameMode == null || minecraft.gameMode.getPlayerMode() == GameType.SPECTATOR) {
            return;
        }
        LocalPlayer player = minecraft.player;
        if (player == null) {
            return;
        }
        // 如果指向雪克杯，提示内容
        this.renderShakerBlockTips(guiGraphics, screenWidth, screenHeight, minecraft, player);
        this.renderShakerProgress(guiGraphics, screenWidth, screenHeight, player, partialTick);
    }

    private void renderShakerProgress(GuiGraphics guiGraphics, int screenWidth, int screenHeight, LocalPlayer player, float partialTick) {
        // 如果手持雪克杯
        int remainingTicks = player.getUseItemRemainingTicks();
        if (remainingTicks > 0 && player.getUseItem().is(ModItems.SHAKER.get())) {
            guiGraphics.blit(IMG, screenWidth / 2 - 91, screenHeight / 2 + 32, 0, 0, 181, 17);

            // 图标移动
            int offsetX = (int) Math.round((player.getTicksUsingItem() + partialTick) * 1.5);
            guiGraphics.blit(IMG, screenWidth / 2 - 91 + offsetX, screenHeight / 2 + 26, 181, 0, 11, 13);
        }
    }

    private void renderShakerBlockTips(GuiGraphics guiGraphics, int screenWidth, int screenHeight, Minecraft minecraft, LocalPlayer player) {
        HitResult hitResult = minecraft.hitResult;
        if (!(hitResult instanceof BlockHitResult result)) {
            return;
        }
        Level level = player.level();
        BlockPos blockPos = result.getBlockPos();
        BlockState blockState = player.level().getBlockState(blockPos);
        if (!blockState.is(ModBlocks.SHAKER.get())) {
            return;
        }
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        if (!(blockEntity instanceof ShakerBlockEntity shaker)) {
            return;
        }

        Font font = Minecraft.getInstance().font;
        int x = screenWidth / 2 - 28;
        int y = screenHeight / 2 + 26;

        ItemStackHandler storage = shaker.getStorage();
        for (int i = 0; i < storage.getSlots(); i++) {
            ItemStack stack = storage.getStackInSlot(i);
            if (stack.isEmpty()) {
                continue;
            }
            ChatFormatting chatFormatting = ColorUtils.ITEM_COLOR_CACHE.apply(stack.getItem());
            // 如果没有颜色匹配，渲染物品
            if (chatFormatting == ChatFormatting.RESET) {
                guiGraphics.renderFakeItem(stack, x, y);
                guiGraphics.renderItemDecorations(font, stack, x, y);
            } else {
                int color = Objects.requireNonNull(chatFormatting.getColor()) | 0xFF000000;
                renderIcon(guiGraphics, x, y + 6, color);
            }
            x = x + 20;
        }
    }

    @SuppressWarnings("deprecation")
    private void renderIcon(GuiGraphics pGuiGraphics, int x, int y, int color) {
        float alpha = FastColor.ARGB32.alpha(color) / 255f;
        float red = FastColor.ARGB32.red(color) / 255f;
        float green = FastColor.ARGB32.green(color) / 255f;
        float blue = FastColor.ARGB32.blue(color) / 255f;
        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(ICON);
        pGuiGraphics.blit(x, y, 0, 16, 16, sprite, red, green, blue, alpha);
    }
}
