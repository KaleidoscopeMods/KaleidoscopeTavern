package com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco;

import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.BaseBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.util.UUID;

public class ChalkboardBlockEntity extends BaseBlockEntity {
    /**
     * 正在编辑这个黑板的玩家的 UUID
     */
    private @Nullable UUID playerWhoMayEdit = null;
    /**
     * 这个黑板是否是大型的（3x2），否则就是普通的（1x2）
     */
    private boolean isLarge = false;
    /**
     * 这个黑板上的文本内容，默认为空字符串
     */
    private String text = StringUtils.EMPTY;
    /**
     * 这个黑板上的文本颜色，默认为白色
     */
    private DyeColor color = DyeColor.WHITE;
    /**
     * 文本对齐方式，默认为居中
     */
    private TextAlignment textAlignment = TextAlignment.CENTER;
    /**
     * 这个黑板是否被打蜡了（无法被再次修改），默认为 false
     */
    private boolean isWaxed = false;
    /**
     * 这个黑板是否在发光（文本会发光），默认为 false
     */
    private boolean isGlowing = false;

    public ChalkboardBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlocks.CHALKBOARD_BE.get(), pos, blockState);
    }

    public static ChalkboardBlockEntity small(BlockPos pos, BlockState blockState) {
        ChalkboardBlockEntity be = new ChalkboardBlockEntity(pos, blockState);
        be.setLarge(false);
        return be;
    }

    public static ChalkboardBlockEntity large(BlockPos pos, BlockState blockState) {
        ChalkboardBlockEntity be = new ChalkboardBlockEntity(pos, blockState);
        be.setLarge(true);
        return be;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, ChalkboardBlockEntity be) {
        UUID uuid = be.getPlayerWhoMayEdit();
        if (uuid != null && be.playerIsTooFarAwayToEdit(uuid)) {
            be.setPlayerWhoMayEdit(null);
        }
    }

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

    @Override
    public AABB getRenderBoundingBox() {
        BlockPos pos = this.getBlockPos();
        if (this.isLarge) {
            return new AABB(pos.offset(-1, 0, -1), pos.offset(2, 2, 2));
        }
        return new AABB(pos, pos.offset(1, 2, 1));
    }

    @Nullable
    public UUID getPlayerWhoMayEdit() {
        return playerWhoMayEdit;
    }

    public void setPlayerWhoMayEdit(@Nullable UUID playerWhoMayEdit) {
        this.playerWhoMayEdit = playerWhoMayEdit;
    }

    public boolean isLarge() {
        return isLarge;
    }

    public void setLarge(boolean large) {
        isLarge = large;
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

    public enum TextAlignment {
        LEFT,
        CENTER,
        RIGHT;

        public static TextAlignment byId(int id) {
            return switch (id) {
                case 0 -> LEFT;
                case 2 -> RIGHT;
                default -> CENTER;
            };
        }

        public int getId() {
            return switch (this) {
                case LEFT -> 0;
                case RIGHT -> 2;
                default -> 1;
            };
        }
    }
}
