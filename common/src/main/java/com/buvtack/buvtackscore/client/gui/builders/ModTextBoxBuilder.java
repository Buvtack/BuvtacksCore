package com.buvtack.buvtackscore.client.gui.builders;

import com.buvtack.buvtackscore.client.graphics.ModColor;
import com.buvtack.buvtackscore.client.gui.components.ModTextBox;

public class ModTextBoxBuilder extends DimensionalModWidgetBuilder {

    protected ModColor textColor;

    public ModTextBoxBuilder textColor(ModColor color) {
        textColor = color;
        return this;
    }

    public ModTextBox build() {
        ModTextBox textBox = new ModTextBox(x, y, width, height);
        buildDimensionalWidget(textBox);
        textBox.setTextColor(textColor);
        return textBox;
    }
}
