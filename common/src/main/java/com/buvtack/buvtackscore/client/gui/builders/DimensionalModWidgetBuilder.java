package com.buvtack.buvtackscore.client.gui.builders;

import com.buvtack.buvtackscore.client.graphics.ModColor;
import com.buvtack.buvtackscore.client.gui.components.DimensionalModWidget;

public class DimensionalModWidgetBuilder extends Builder {

    protected int width;
    protected int height;

    protected int padding;

    protected ModColor backgroundColor;
    protected ModColor hoverColor;
    protected ModColor pressedColor;
    protected ModColor focusedColor;

    protected ModColor borderColor;

    protected float fadeTicks;
    protected float fadeIntensity;

    public DimensionalModWidgetBuilder bounds(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        return this;
    }

    public DimensionalModWidgetBuilder background(ModColor color) {
        backgroundColor = color;
        return this;
    }

    public DimensionalModWidgetBuilder hover(ModColor color, float fade, float intensity) {
        fadeTicks = fade;
        hoverColor = color;
        fadeIntensity = intensity;
        return this;
    }

    public DimensionalModWidgetBuilder pressed(ModColor color) {
        pressedColor = color;
        return this;
    }

    public DimensionalModWidgetBuilder focused(ModColor color) {
        focusedColor = color;
        return this;
    }

    public DimensionalModWidgetBuilder border(ModColor color) {
        borderColor = color;
        return this;
    }

    public DimensionalModWidgetBuilder padding(int padding) {
        this.padding = padding;
        return this;
    }

    protected void buildDimensionalWidget(DimensionalModWidget widget) {
        buildWidget(widget);
        widget.setPadding(padding);
        widget.setWidth(width);
        widget.setHeight(height);
        widget.setBackgroundColor(backgroundColor);
        widget.setHoverColor(hoverColor, fadeTicks, fadeIntensity);
        widget.setPressedColor(pressedColor);
        widget.setFocusedColor(focusedColor);
        widget.setBorderColor(borderColor);
        widget.tickFade();
    }
}
