package com.buvtack.buvtackscore.client.gui.builders;

import com.buvtack.buvtackscore.client.gui.Alignment;
import com.buvtack.buvtackscore.client.gui.components.ModEntry;
import com.buvtack.buvtackscore.client.gui.components.ModEntryList;
import com.buvtack.buvtackscore.client.gui.components.ModScrollBar;
import com.buvtack.buvtackscore.client.gui.components.ModWidget;

import java.util.List;
import java.util.function.Supplier;

public class ModEntryListBuilder<T extends ModEntry> extends DimensionalModWidgetBuilder {

    private int gap;
    private int innerMargin;

    private ModScrollBar bar;
    private Alignment alignment;

    private List<Supplier<? extends ModWidget>> entries;

    public ModEntryListBuilder<T> gap(int gap) {
        this.gap = gap;
        return this;
    }

    public ModEntryListBuilder<T> innerMargin(int innerMargin) {
        this.innerMargin = innerMargin;
        return this;
    }

    public ModEntryListBuilder<T> bar(ModScrollBar bar, Alignment alignment) {
        this.bar = bar;
        this.alignment = alignment;
        return this;
    }

    public ModEntryList<T> build() {
        ModEntryList<T> list = new ModEntryList<>();
        buildDimensionalWidget(list);
        list.addEntries(entries);
        list.setGap(gap);
        list.setBar(bar, alignment);
        return list;
    }

    public ModEntryListBuilder<T> entries(List<Supplier<? extends ModWidget>> entries) {
        this.entries = entries;
        return this;
    }
}
