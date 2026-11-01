package com.buvtack.buvtackscore.client.gui.components;

import com.buvtack.buvtackscore.client.gui.Alignment;
import com.buvtack.buvtackscore.client.gui.DefaultTaskNames;
import com.buvtack.buvtackscore.client.gui.DefaultTaskPriorities;
import com.buvtack.buvtackscore.client.gui.ModTask;

public class ModEntryList<T extends ModEntry> extends ModPanel {

    private int gap;
    private int innerMargin;

    private ModScrollBar bar;
    private float prevBarProgress;

    private ModEntryList(int x, int y, int width, int height) {
        super(x, y, width, height);

        renderTasks.removeTask(DefaultTaskNames.renderVanillaWidgets);
        renderTasks.addTask(DefaultTaskNames.renderModWidgets, new ModTask<>(DefaultTaskPriorities.CONTENT, (graphics, mouseX, mouseY, partialTick) -> {
            int entryX = this.x + innerMargin;
            int entryY = this.y + innerMargin;
            for (ModWidget widget : modWidgets) {
                ModEntry entry = (ModEntry) widget;
                entry.setX(entryX);
                entry.setY(entryY);

                if (entry.getMarginedY() >= this.y + innerMargin && entry.getMarginedY() <= bottom() - innerMargin)
                    entry.render(graphics, mouseX, mouseY, partialTick);

                entryY += entry.getMarginedHeight() + gap;
            }
        }));

        renderTasks.addTask("renderBar", new ModTask<>(DefaultTaskPriorities.CONTENT, (graphics, mouseX, mouseY, partialTick) -> {
            if (bar != null)
                bar.render(graphics, mouseX, mouseY, partialTick);
        }));

        tickTasks.addTask("tickBar", new ModTask<>(() -> {
            if (bar != null)
                bar.tick();
        }));

        tickTasks.addTask("updateScrollbarPivot", new ModTask<>(() -> {
            float scrollProgress = bar.progress();
            if (scrollProgress != prevBarProgress) {
                prevBarProgress = scrollProgress;

                int listHeight = 0;
                for (ModWidget widget : modWidgets) {
                    ModEntry entry = (ModEntry) widget;
                    listHeight += entry.getMarginedHeight() + gap;
                }
                listHeight += innerMargin * 2;
                bar.setOuterHeight(listHeight);

                float deltaY = bar.progress() * listHeight;
                this.y = (int) -deltaY;
            }
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
            bar.setX(x);
        else if (alignment == Alignment.RIGHT)
            bar.setX(contentRight() - bar.getMarginedWidth());
        else if (alignment == Alignment.CENTER)
            bar.setX(centerX() - bar.getMarginedWidth() / 2);

        bar.setY(getContentY());

        this.bar = bar;
    }

    public void setGap(int gap) {
        this.gap = gap;
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

        public Builder<T> gap(int gap) {
            list.setGap(gap);
            return this;
        }

        public ModEntryList<T> build() {
            return list;
        }
    }
}
