package com.buvtack.buvtackscore.client.gui.components;

import com.buvtack.buvtackscore.Constants;
import com.buvtack.buvtackscore.client.gui.Alignment;
import com.buvtack.buvtackscore.client.gui.DefaultTaskNames;
import com.buvtack.buvtackscore.client.gui.DefaultTaskPriorities;
import com.buvtack.buvtackscore.client.gui.ModTask;
import com.buvtack.buvtackscore.client.gui.builders.ModEntryListBuilder;
import com.buvtack.buvtackscore.client.util.ClientModHelper;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class ModEntryList<T extends ModEntry> extends ModPanel {

    private int gap;
    private int innerMargin;
    private boolean focusEntryOnClick;
    private ModEntry focusedEntry;

    private int offsetY = 0;

    private ModScrollBar bar;
    private float prevBarProgress;

    public ModEntryList() {
        this(0, 0, 0, 0);
    }

    public ModEntryList(int x, int y, int width, int height) {
        super(x, y, width, height);

        renderTasks.removeTask(DefaultTaskNames.renderVanillaWidgets);
        renderTasks.removeTask(DefaultTaskNames.renderModWidgets);
        renderTasks.addTask(DefaultTaskNames.renderModWidgets, new ModTask<>(DefaultTaskPriorities.CONTENT, (graphics, mouseX, mouseY, partialTick) -> {
            int entryX = this.x + innerMargin;
            int entryY = this.y + innerMargin - offsetY;
            //Constants.LOG.info("entryY:" + entryY);
            graphics.enableScissor(contentLeft(), contentTop(), contentRight(), contentBottom());
            for (ModWidget widget : modWidgets) {
                ModEntry entry = (ModEntry) widget;
                entry.setX(entryX);
                entry.setY(entryY);
                entry.focused = entry == focusedEntry;

                entry.render(graphics, mouseX, mouseY, partialTick);

                entryY += entry.getMarginedHeight() + gap;
            }
            graphics.disableScissor();
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

                int listHeight = totalListHeight();
                bar.setOuterHeight(listHeight);

                float deltaY = bar.progress() * listHeight;
                offsetY = (int) deltaY;
            }
        }));

        tickTasks.addTask("scroll", new ModTask<>(this::scroll));
        tickTasks.addTask("focusEntryOnClick", new ModTask<>(this::unfocusEntry));
    }

    private void scroll() {
        if (hovered()) {
            double scroll = ClientModHelper.scroll();
            if (scroll > 0)
                bar.movePivot(Math.max(bar.progress() - 20.0 / totalListHeight(), 0));
            else if (scroll < 0)
                bar.movePivot(Math.min(bar.progress() + 20.0 / totalListHeight(), 1));

            ClientModHelper.resetScroll();
        }
    }

    private void unfocusEntry() {
        if (!focusEntryOnClick)
            return;

        if (ClientModHelper.isLeftClicking() && !hovered())
            focusedEntry = null;
    }

    public void setFocusedEntry(ModEntry entry) {
        this.focusedEntry = entry;
    }

    private int totalListHeight() {
        int height = 0;
        for (ModWidget widget : modWidgets) {
            ModEntry entry = (ModEntry) widget;
            height += entry.getMarginedHeight() + gap;
        }
        height += innerMargin * 2;
        return height;
    }

    public boolean isEntryHovered(ModEntry entry, int mouseX, int mouseY) {
        return hovered(mouseX, mouseY) && entry.hovered(mouseX, mouseY);
    }

    public boolean isEntryHovered(ModEntry entry) {
        return hovered() && entry.hovered();
    }

    public boolean isEntryPressed(ModEntry entry, int mouseX, int mouseY) {
        return pressed(mouseX, mouseY) && entry.pressed(mouseX, mouseY);
    }

    public boolean isEntryPressed(ModEntry entry) {
        return pressed() && entry.pressed();
    }

    public void addEntries(List<Supplier<? extends ModWidget>> entries) {
        addModWidgets(entries);
    }

    @Override
    public void addModWidgets(List<Supplier<? extends ModWidget>> suppliers) {
        super.addModWidgets(suppliers);

        for (ModWidget widget : modWidgets) {
            if (widget instanceof ModEntry entry)
                entry.setParentList(this);
        }
    }

    public ModEntry getFocusedEntry() {
        if (!focusEntryOnClick)
            return null;

        return focusedEntry;
    }

    public void setBar(ModScrollBar bar, Alignment alignment) {
        if (alignment == Alignment.LEFT)
            bar.setX(x);
        else if (alignment == Alignment.RIGHT)
            bar.setX(contentRight() - bar.getMarginedWidth());
        else if (alignment == Alignment.CENTER)
            bar.setX(centerX() - bar.getMarginedWidth() / 2);

        bar.setY(getContentY());
        bar.setHeight(getContentHeight());

        this.bar = bar;
    }

    public void setGap(int gap) {
        this.gap = gap;
    }

    public void setFocusEntryOnClick(boolean focus) {
        if (!focus)
            focusedEntry = null;

        focusEntryOnClick = focus;
    }

    public static class Builder<T extends ModEntry> {

        private ModEntryList<T> list;

        private Builder(int x, int y, int width, int height) {
            list = new ModEntryList(x, y, width, height);
        }

        public Builder<T> entries(List<Supplier<? extends ModWidget>> entries) {
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
