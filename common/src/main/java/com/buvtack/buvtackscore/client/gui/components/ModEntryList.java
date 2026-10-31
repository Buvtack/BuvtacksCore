package com.buvtack.buvtackscore.client.gui.components;

import com.buvtack.buvtackscore.client.gui.Alignment;
import com.buvtack.buvtackscore.client.gui.DefaultTaskNames;
import com.buvtack.buvtackscore.client.gui.DefaultTaskPriorities;
import com.buvtack.buvtackscore.client.gui.ModTask;

public class ModEntryList<T extends ModEntry> extends ModPanel {

    private int gap;
    private int innerMargin;

    private ModScrollBar bar;

    private ModEntryList(int x, int y, int width, int height) {
        super(x, y, width, height);

        renderTasks.removeTask(DefaultTaskNames.renderVanillaWidgets);
        renderTasks.addTask(DefaultTaskNames.renderModWidgets, new ModTask<>(DefaultTaskPriorities.CONTENT, (graphics, mouseX, mouseY, partialTick) -> {
            int entryX = this.x + innerMargin;
            int entryY = this.y + innerMargin;
            for (ModWidget widget : modWidgets) {
                ModEntry entry = (ModEntry) widget;
                entry.x = entryX;
                entry.y = entryY;

                if (entry.getMarginedY() >= this.y + innerMargin && entry.getMarginedY() <= bottom() - innerMargin)
                    entry.render(graphics, mouseX, mouseY, partialTick);

                entryY += entry.getMarginedHeight() + gap;
            }
        }));

        tickTasks.addTask("updateScrollbarPivot", new ModTask<>(() -> {
            int listHeight = 0;
            for (ModWidget widget : modWidgets) {
                ModEntry entry = (ModEntry) widget;
                listHeight += entry.getMarginedHeight();
            }
            listHeight += innerMargin * 2;

            float deltaY = bar.progress() * listHeight;
            this.y -= (int) deltaY;
        }));
    }

    public static <T extends ModEntry> Builder<T> newBuilder(int x, int y, int width, int height) {
        return new Builder<>(x, y, width, height);
    }

    public void addEntries(T... entries) {
        addModWidgets(entries);
    }

    public void setBar(ModScrollBar bar, Alignment alignment) {
        if (alignment == Alignment.LEFT)
            bar.x = x;
        else if (alignment == Alignment.RIGHT)
            bar.x = contentRight() - bar.getMarginedWidth();
        else if (alignment == Alignment.CENTER)
            bar.x = centerX() - bar.getMarginedWidth() / 2;

        bar.y = getContentY();

        this.bar = bar;
    }

    public static class Builder<T extends ModEntry> {

        private ModEntryList<T> list;

        private Builder(int x, int y, int width, int height) {
            list = new ModEntryList(x, y, width, height);
        }

        public Builder<T> entries(T... entries) {
            list.addEntries(entries);
            return this;
        }

        public Builder<T> bar(ModScrollBar bar, Alignment alignment) {
            list.setBar(bar, alignment);
            return this;
        }

        public ModEntryList<T> build() {
            return list;
        }
    }
}
