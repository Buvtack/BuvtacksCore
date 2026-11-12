package com.buvtack.buvtackscore.client.gui.components;

import com.buvtack.buvtackscore.Constants;
import com.buvtack.buvtackscore.client.graphics.ModColor;
import com.buvtack.buvtackscore.client.graphics.ModColors;
import com.buvtack.buvtackscore.client.gui.Alignment;
import com.buvtack.buvtackscore.client.gui.DefaultTaskNames;
import com.buvtack.buvtackscore.client.gui.DefaultTaskPriorities;
import com.buvtack.buvtackscore.client.gui.ModTask;
import com.buvtack.buvtackscore.client.util.ClientModHelper;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;

import java.util.ArrayList;
import java.util.List;

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

    protected ModColor borderColor;
    protected ModColor hoverBorderColor;
    protected ModColor pressedBorderColor;
    protected ModColor focusedBorderColor;

    protected boolean focused;

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
        renderTasks.addTask(DefaultTaskNames.BORDER, new ModTask<>(DefaultTaskPriorities.BORDER, this::renderBorder));
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

    private void renderBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        if (focusedColor != null && focused)
            graphics.fill(left(), top(), right(), bottom(), focusedColor.color);
        else if (pressedColor != null && pressed(mouseX, mouseY))
            graphics.fill(left(), top(), right(), bottom(), pressedColor.color);
        else if (hoverColor != null && hovered(mouseX, mouseY)) {
            float ratio = Math.min(fade, fadeTicks) / fadeTicks * fadeIntensity;
            ModColor baseColor = backgroundColor != null ? backgroundColor : ModColors.TRANSPARENT;
            ModColor color = baseColor.lerp(hoverColor, ratio);
            graphics.fill(left(), top(), right(), bottom(), color.color);
        }
        else if (backgroundColor != null)
            graphics.fill(left(), top(), right(), bottom(), backgroundColor.color);
    }

    private void renderBorder(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        if (focusedBorderColor != null && focused)
            graphics.outline(left(), top(), getPaddedWidth(), getPaddedHeight(), focusedBorderColor.color);
        else if (pressedBorderColor != null && pressed(mouseX, mouseY))
            graphics.outline(left(), top(), getPaddedWidth(), getPaddedHeight(), pressedBorderColor.color);
        else if (hoverBorderColor != null && hovered(mouseX, mouseY))
            graphics.outline(left(), top(), getPaddedWidth(), getPaddedHeight(), hoverBorderColor.color);
        else if (borderColor != null)
            graphics.outline(left(), top(), getPaddedWidth(), getPaddedHeight(), borderColor.color);
    }

    public boolean hovered() {
        return ClientModHelper.mouseX() >= getPaddedX() && ClientModHelper.mouseX() <= getPaddedX() + getPaddedWidth() &&
                ClientModHelper.mouseY() >= getPaddedY() && ClientModHelper.mouseY() <= getPaddedY() + getPaddedHeight();
    }

    public boolean hovered(int mouseX, int mouseY) {
        return mouseX >= getPaddedX() && mouseX <= getPaddedX() + getPaddedWidth() &&
                mouseY >= getPaddedY() && mouseY <= getPaddedY() + getPaddedHeight();
    }

    public void setBackgroundColor(ModColor color) {
        this.backgroundColor = color;
    }

    public void setHoverColor(ModColor color, float fade, float intensity) {
        hoverColor = color;
        fadeTicks = fade;
        fadeIntensity = intensity;
    }

    public void setPressedColor(ModColor color) {
        pressedColor = color;
    }

    public void setFocusedColor(ModColor color) {
        focusedColor = color;
    }

    public void setBorderColor(ModColor color) {
        borderColor = color;
    }

    public void setHoverBorderColor(ModColor color) {
        hoverBorderColor = color;
    }

    public void setPressedBorderColor(ModColor color) {
        pressedBorderColor = color;
    }

    public void setFocusedBorderColor(ModColor color) {
        focusedBorderColor = color;
    }

    public void setPadding(int padding) {
        this.padding = padding;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
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
