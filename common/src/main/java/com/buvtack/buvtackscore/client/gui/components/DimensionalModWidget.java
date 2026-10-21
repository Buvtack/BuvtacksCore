package com.buvtack.buvtackscore.client.gui.components;

import com.buvtack.buvtackscore.client.util.ClientModHelper;

public abstract class DimensionalModWidget extends ModWidget {

    public int width;
    public int height;

    public int padding;
    public int margin;

    public DimensionalModWidget(int x, int y, int width, int height) {
        super(x, y);
        this.width = width;
        this.height = height;
        padding = 0;
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
        return x;
    }

    public int getContentY() {
        return y;
    }

    public int getPaddedX() {
        return x - padding;
    }

    public int getPaddedY() {
        return y - padding;
    }

    public int getMarginedX() {
        return x - padding - margin;
    }

    public int getMarginedY() {
        return y - padding - margin;
    }

    public int getContentWidth() {
        return width;
    }

    public int getContentHeight() {
        return height;
    }

    public int getPaddedWidth() {
        return width + padding * 2;
    }

    public int getPaddedHeight() {
        return height + padding * 2;
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
}
