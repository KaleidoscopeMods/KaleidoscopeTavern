package com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco;

import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.BaseBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.util.TextAlignment;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.util.UUID;

public abstract class TextBlockEntity extends BaseBlockEntity {
    /**
     * 正在编辑这个写字板的玩家的 UUID
     */
    protected @Nullable UUID playerWhoMayEdit = null;
    /**
     * 这个写字板上的文本内容，默认为空字符串
     */
    protected String text = StringUtils.EMPTY;
    /**
     * 这个写字板上的文本颜色，默认为白色
     */
    protected DyeColor color = DyeColor.WHITE;
    /**
     * 文本对齐方式，默认为居中
     */
    protected TextAlignment textAlignment = TextAlignment.CENTER;
    /**
     * 这个写字板是否被打蜡了（无法被再次修改），默认为 false
     */
    protected boolean isWaxed = false;
    /**
     * 这个写字板是否在发光（文本会发光），默认为 false
     */
    protected boolean isGlowing = false;

    public TextBlockEntity(BlockEntityType<?> entityType, BlockPos pos, BlockState state) {
        super(entityType, pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, TextBlockEntity be) {
        UUID uuid = be.getPlayerWhoMayEdit();
        if (uuid != null && be.playerIsTooFarAwayToEdit(uuid)) {
            be.setPlayerWhoMayEdit(null);
        }
    }

    /**
     * 当前这个写字板允许的最大文本长度（字符数）
     *
     * @return 最大文本长度
     */
    public abstract int getMaxTextLength();

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.text = tag.getString("text");
        this.color = DyeColor.byId(tag.getInt("color"));
        this.textAlignment = TextAlignment.byId(tag.getInt("text_alignment"));
        this.isWaxed = tag.getBoolean("is_waxed");
        this.isGlowing = tag.getBoolean("is_glowing");
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putString("text", this.text);
        tag.putInt("color", this.color.getId());
        tag.putInt("text_alignment", this.textAlignment.getId());
        tag.putBoolean("is_waxed", this.isWaxed);
        tag.putBoolean("is_glowing", this.isGlowing);
    }

    public boolean playerIsTooFarAwayToEdit(UUID id) {
        if (this.level == null) {
            return true;
        }
        Player player = this.level.getPlayerByUUID(id);
        if (player == null) {
            return true;
        }
        BlockPos pos = this.getBlockPos();
        return player.distanceToSqr(pos.getX(), pos.getY(), pos.getZ()) > 8 * 8;
    }

    @Nullable
    public UUID getPlayerWhoMayEdit() {
        return playerWhoMayEdit;
    }

    public void setPlayerWhoMayEdit(@Nullable UUID playerWhoMayEdit) {
        this.playerWhoMayEdit = playerWhoMayEdit;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public DyeColor getColor() {
        return color;
    }

    public void setColor(DyeColor color) {
        this.color = color;
    }

    public TextAlignment getTextAlignment() {
        return textAlignment;
    }

    public void setTextAlignment(TextAlignment textAlignment) {
        this.textAlignment = textAlignment;
    }

    public boolean isWaxed() {
        return isWaxed;
    }

    public void setWaxed(boolean waxed) {
        isWaxed = waxed;
    }

    public boolean isGlowing() {
        return isGlowing;
    }

    public void setGlowing(boolean glowing) {
        isGlowing = glowing;
    }
}
