package com.buvtack.buvtackscore.client.gui.components;

import com.buvtack.buvtackscore.client.graphics.ModColor;
import com.buvtack.buvtackscore.client.graphics.ModColors;
import com.buvtack.buvtackscore.client.gui.Alignment;
import com.buvtack.buvtackscore.client.gui.DefaultTaskNames;
import com.buvtack.buvtackscore.client.gui.DefaultTaskPriorities;
import com.buvtack.buvtackscore.client.gui.ModTask;
import com.buvtack.buvtackscore.client.util.ClientModHelper;
import net.minecraft.client.gui.GuiGraphicsExtractor;

public abstract class DimensionalModWidget extends ModWidget {

    public int width;
    public int height;

    public int padding;
    public int margin;

    protected float fadeTicks;
    protected float fade;
    protected float fadeIntensity;

    public Alignment alignment;

    protected ModColor hoverColor;
    protected ModColor backgroundColor;
    protected ModColor pressedColor;
    protected ModColor focusedColor;

    private boolean focused;

    public DimensionalModWidget(int x, int y, int width, int height) {
        super(x, y);
        this.width = width;
        this.height = height;
        padding = 0;
        alignment = Alignment.LEFT;
        pressedColor = null;
        hoverColor = null;
        backgroundColor = null;
        focusedColor = null;
        fade = 0;
        fadeTicks = 0;
        focused = false;

        renderTasks.addTask(DefaultTaskNames.BACKGROUND, new ModTask<>(DefaultTaskPriorities.BACKGROUND, this::renderBackground));
    }

    public void setFadeTicks(float fade) {
        fadeTicks = fade;
    }

    public void tickFade() {
        renderTasks.addTask(DefaultTaskNames.FADE,
                new ModTask<>(DefaultTaskPriorities.MEANINGLESS,
                        (graphics, mouseX, mouseY, partialTick) -> {
                            if (hovered(mouseX, mouseY) && fade < fadeTicks)
                                fade += partialTick;
                            else if (!hovered(mouseX, mouseY))
                                fade = 0;
                        }));
    }

    public void renderBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        if (backgroundColor != null)
            graphics.fill(left(), top(), right(), bottom(), backgroundColor.color);

        if (hoverColor != null && hovered(mouseX, mouseY)) {
            float ratio = Math.min(fade, fadeTicks) / fadeTicks * fadeIntensity;
            ModColor color = backgroundColor.lerp(hoverColor, ratio);
            graphics.fill(left(), top(), right(), bottom(), color.color);
        }

        if (pressedColor != null && pressed(mouseX, mouseY))
            graphics.fill(left(), top(), right(), bottom(), pressedColor.color);

        if (focusedColor != null && focused)
            graphics.fill(left(), top(), right(), bottom(), focusedColor.color);
    }

    public boolean hovered() {
        return ClientModHelper.mouseX() >= getPaddedX() && ClientModHelper.mouseX() <= getPaddedX() + getPaddedWidth() &&
                ClientModHelper.mouseY() >= getPaddedY() && ClientModHelper.mouseY() <= getPaddedY() + getPaddedHeight();
    }

    public boolean hovered(int mouseX, int mouseY) {
        return mouseX >= getPaddedX() && mouseX <= getPaddedX() + getPaddedWidth() &&
                mouseY >= getPaddedY() && mouseY <= getPaddedY() + getPaddedHeight();
    }

    public int getContentX() {
        return alignment == Alignment.CENTER ? x - width / 2 : (alignment == Alignment.RIGHT ? x - width : x);
    }

    public int getContentY() {
        return y;
    }

    public int getPaddedX() {
        return getContentX() - padding;
    }

    public int getPaddedY() {
        return getContentY() - padding;
    }

    public int getMarginedX() {
        return getPaddedX() - margin;
    }

    public int getMarginedY() {
        return getPaddedY() - margin;
    }

    public int getContentWidth() {
        return width;
    }

    public int getContentHeight() {
        return height;
    }

    public int getPaddedWidth() {
        return getContentWidth() + padding * 2;
    }

    public int getPaddedHeight() {
        return getContentHeight() + padding * 2;
    }

    public int getMarginedWidth() {
        return getPaddedWidth() + margin * 2;
    }

    public int getMarginedHeight() {
        return getPaddedHeight() + margin * 2;
    }

    public int centerX() {
        return x + width / 2;
    }

    public int centerY() {
        return y + height / 2;
    }

    public int left() {
        return getPaddedX();
    }

    public int right() {
        return getPaddedX() + getPaddedWidth();
    }

    public int top() {
        return getPaddedY();
    }

    public int bottom() {
        return getPaddedY() + getPaddedHeight();
    }

    public int contentLeft() {
        return getContentX();
    }

    public int contentRight() {
        return contentLeft() + getContentWidth();
    }

    public int contentTop() {
        return getContentY();
    }

    public int contentBottom() {
        return contentTop() + getContentHeight();
    }

    protected int getXFromAlignment(Alignment alignment) {
        return alignment == Alignment.CENTER ? centerX() : (alignment == Alignment.RIGHT ? getContentX() - getContentWidth() : getContentX());
    }

    protected int getXFromAlignmentOf(int width, Alignment alignment) {
        return alignment == Alignment.CENTER ? centerX() : (alignment == Alignment.RIGHT ? contentRight() - width : contentLeft());
    }

    public boolean pressed(int mouseX, int mouseY) {
        return hovered(mouseX, mouseY) && ClientModHelper.isLeftClicking();
    }

    public boolean pressed() {
        return hovered() && ClientModHelper.isLeftClicking();
    }
}
