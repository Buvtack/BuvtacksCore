package com.buvtack.buvtackscore.client.gui.builders;

import com.buvtack.buvtackscore.client.graphics.ModColor;
import com.buvtack.buvtackscore.client.gui.components.ModButton;
import com.buvtack.buvtackscore.client.gui.components.ModScrollBar;

public class ModScrollBarBuilder extends DimensionalModWidgetBuilder {

    private ModButton pivot;
    private int outerHeight;

    public ModScrollBarBuilder pivot(ModButton button) {
        pivot = button;
        return this;
    }

    public ModScrollBarBuilder pivot(ModColor backgroundColor, ModColor hoverColor, float fade, float intensity, ModColor pressedColor) {
        ModButtonBuilder builder = new ModButtonBuilder();
        builder.bounds(x, y, width, height);
        builder.background(backgroundColor);
        builder.hover(hoverColor, fade, intensity);
        builder.pressed(pressedColor);
        pivot = builder.build();
        return this;
    }

    public ModScrollBarBuilder outerHeight(int outerHeight) {
        this.outerHeight = outerHeight;
        return this;
    }

    public ModScrollBar build() {
        ModScrollBar bar = new ModScrollBar();
        buildDimensionalWidget(bar);
        bar.setOuterHeight(outerHeight);
        bar.pivot(pivot);
        return bar;
    }
}
