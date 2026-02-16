package com.github.ysbbbbbb.kaleidoscopetavern.client.gui.block;

import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco.ChalkboardBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.network.NetworkHandler;
import com.github.ysbbbbbb.kaleidoscopetavern.network.message.ChalkboardUpdateC2SMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.MultiLineEditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class ChalkboardScreen extends Screen {
    private ChalkboardBlockEntity blockEntity;
    private String text;
    private ChalkboardBlockEntity.TextAlignment textAlignment;
    private MultiLineEditBox customSetting;

    public ChalkboardScreen(ChalkboardBlockEntity blockEntity) {
        super(Component.literal("Chalkboard"));
        this.blockEntity = blockEntity;
        this.text = blockEntity.getText();
        this.textAlignment = blockEntity.getTextAlignment();
    }

    @Override
    protected void init() {
        this.clearWidgets();

        int posX = this.width / 2 - 165;
        int posY = this.height / 2 - 80;
        int boxWidth = 256;

        this.customSetting = this.addRenderableWidget(new MultiLineEditBox(font,
                posX, posY, boxWidth, 120,
                Component.translatable("gui.kaleidoscope_tavern.chalkboard.edit.placeholder"),
                Component.literal("Custom Setting Box")));
        this.customSetting.setValue(text);
        this.customSetting.setCharacterLimit(256);
        this.customSetting.setValueListener(s -> text = s);

        this.addRenderableWidget(
                Button.builder(CommonComponents.GUI_CANCEL, button -> onClose())
                        .bounds(posX, posY + 135, 126, 20)
                        .build()
        );

        this.addRenderableWidget(
                Button.builder(CommonComponents.GUI_DONE, button -> onDone())
                        .bounds(posX + 130, posY + 135, 126, 20)
                        .build()
        );

        // 对齐按钮
        Button left = Button.builder(Component.translatable("gui.kaleidoscope_tavern.chalkboard.edit.text_alignment.left"),
                        button -> {
                            this.textAlignment = ChalkboardBlockEntity.TextAlignment.LEFT;
                            this.init();
                        })
                .bounds(posX + boxWidth + 5, posY + 25, 80, 20)
                .build();

        Button center = Button.builder(Component.translatable("gui.kaleidoscope_tavern.chalkboard.edit.text_alignment.center"),
                        button -> {
                            this.textAlignment = ChalkboardBlockEntity.TextAlignment.CENTER;
                            this.init();
                        })
                .bounds(posX + boxWidth + 5, posY + 50, 80, 20)
                .build();

        Button right = Button.builder(Component.translatable("gui.kaleidoscope_tavern.chalkboard.edit.text_alignment.right"),
                        button -> {
                            this.textAlignment = ChalkboardBlockEntity.TextAlignment.RIGHT;
                            this.init();
                        })
                .bounds(posX + boxWidth + 5, posY + 75, 80, 20)
                .build();

        if (textAlignment == ChalkboardBlockEntity.TextAlignment.LEFT) {
            left.active = false;
        } else if (textAlignment == ChalkboardBlockEntity.TextAlignment.CENTER) {
            center.active = false;
        } else if (textAlignment == ChalkboardBlockEntity.TextAlignment.RIGHT) {
            right.active = false;
        }

        this.addRenderableWidget(left);
        this.addRenderableWidget(center);
        this.addRenderableWidget(right);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    public void tick() {
        this.customSetting.tick();
    }

    @Override
    public void resize(Minecraft mc, int pWidth, int pHeight) {
        String customSettingValue = this.customSetting.getValue();
        super.resize(mc, pWidth, pHeight);
        this.customSetting.setValue(customSettingValue);
    }

    private void onDone() {
        NetworkHandler.sendToServer(new ChalkboardUpdateC2SMessage(blockEntity.getBlockPos(), text, textAlignment));
        this.onClose();
    }

    @Override
    public boolean mouseReleased(double x, double y, int button) {
        return super.mouseReleased(x, y, button) || this.customSetting.mouseReleased(x, y, button);
    }
}
