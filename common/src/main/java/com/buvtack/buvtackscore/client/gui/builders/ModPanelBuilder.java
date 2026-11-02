package com.buvtack.buvtackscore.client.gui.builders;

import com.buvtack.buvtackscore.client.gui.components.ModPanel;

public class ModPanelBuilder extends DimensionalModWidgetBuilder {

    public ModPanel build() {
        ModPanel panel = new ModPanel(x, y, width, height);
        buildDimensionalWidget(panel);
        return panel;
    }
}
