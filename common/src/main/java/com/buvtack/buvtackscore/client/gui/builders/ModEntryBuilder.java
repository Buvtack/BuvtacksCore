package com.buvtack.buvtackscore.client.gui.builders;

import com.buvtack.buvtackscore.client.gui.components.ModEntry;

public class ModEntryBuilder extends DimensionalModWidgetBuilder {

    public ModEntry build() {
        ModEntry entry = new ModEntry(x, y, width, height);
        buildDimensionalWidget(entry);
        return entry;
    }
}
