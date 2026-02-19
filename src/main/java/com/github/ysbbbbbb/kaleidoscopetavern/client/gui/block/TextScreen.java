package com.github.ysbbbbbb.kaleidoscopetavern.client.gui.block;

import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco.TextBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.network.NetworkHandler;
import com.github.ysbbbbbb.kaleidoscopetavern.network.message.TextUpdateC2SMessage;
import com.github.ysbbbbbb.kaleidoscopetavern.util.TextAlignment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.MultiLineEditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class TextScreen extends Screen {
    private final TextBlockEntity blockEntity;

    private String text;
    private TextAlignment textAlignment;
    private MultiLineEditBox customSetting;

    public TextScreen(TextBlockEntity blockEntity) {
        super(Component.literal("Text Edit Screen"));
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
        int maxTextLength = this.blockEntity.getMaxTextLength();

        this.customSetting = this.addRenderableWidget(new MultiLineEditBox(font,
                posX, posY, boxWidth, 120,
                Component.translatable("gui.kaleidoscope_tavern.text.edit.placeholder"),
                Component.literal("Custom Setting Box")));
        this.customSetting.setValue(text);
        this.customSetting.setCharacterLimit(maxTextLength);
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
        Button left = Button.builder(Component.translatable("gui.kaleidoscope_tavern.text.edit.text_alignment.left"),
                        button -> {
                            this.textAlignment = TextAlignment.LEFT;
                            this.init();
                        })
                .bounds(posX + boxWidth + 5, posY + 25, 80, 20)
                .build();

        Button center = Button.builder(Component.translatable("gui.kaleidoscope_tavern.text.edit.text_alignment.center"),
                        button -> {
                            this.textAlignment = TextAlignment.CENTER;
                            this.init();
                        })
                .bounds(posX + boxWidth + 5, posY + 50, 80, 20)
                .build();

        Button right = Button.builder(Component.translatable("gui.kaleidoscope_tavern.text.edit.text_alignment.right"),
                        button -> {
                            this.textAlignment = TextAlignment.RIGHT;
                            this.init();
                        })
                .bounds(posX + boxWidth + 5, posY + 75, 80, 20)
                .build();

        if (textAlignment == TextAlignment.LEFT) {
            left.active = false;
        } else if (textAlignment == TextAlignment.CENTER) {
            center.active = false;
        } else if (textAlignment == TextAlignment.RIGHT) {
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
        NetworkHandler.sendToServer(new TextUpdateC2SMessage(blockEntity.getBlockPos(), text, textAlignment));
        this.onClose();
    }

    @Override
    public boolean mouseReleased(double x, double y, int button) {
        return super.mouseReleased(x, y, button) || this.customSetting.mouseReleased(x, y, button);
    }
}
