package com.buvtack.buvtackscore.client.gui.components;

import com.buvtack.buvtackscore.client.gui.Alignment;
import com.buvtack.buvtackscore.client.util.ClientModHelper;

public abstract class DimensionalModWidget extends ModWidget {

    public int width;
    public int height;

    public int padding;
    public int margin;

    public Alignment alignment;

    public DimensionalModWidget(int x, int y, int width, int height) {
        super(x, y);
        this.width = width;
        this.height = height;
        padding = 0;
        alignment = Alignment.LEFT;
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
}
