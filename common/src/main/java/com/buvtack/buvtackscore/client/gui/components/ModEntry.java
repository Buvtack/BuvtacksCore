package com.buvtack.buvtackscore.client.gui.components;

import com.buvtack.buvtackscore.client.graphics.ModColor;
import com.buvtack.buvtackscore.client.gui.ModTask;
import com.buvtack.buvtackscore.client.gui.builders.ModEntryBuilder;
import com.buvtack.buvtackscore.client.util.ClientModHelper;
import net.minecraft.client.gui.components.AbstractWidget;

public class ModEntry extends ModPanel {

    private ModEntryList<?> parentList;
    private boolean clickable;

    public ModEntry(int x, int y, int width, int height) {
        super(x, y, width, height);
        tickTasks.addTask("focusEntryInList", new ModTask<>(this::focusEntryInList));
    }

    private void focusEntryInList() {
        if (parentList == null)
            return;

        if (ClientModHelper.isNotLeftClicking())
            clickable = true;

        if (parentList.getFocusedEntry() != this && pressed() && clickable) {
            if (parentList.getFocusedEntry() != null)
                parentList.getFocusedEntry().focused = false;

            focused = true;
            parentList.setFocusedEntry(this);
            clickable = false;
        }
    }

    public void setParentList(ModEntryList<?> list) {
        parentList = list;
    }

    public ModEntryList<?> getParentList() {
        return parentList;
    }

    public static class Builder {

        private ModEntry entry;

        private Builder(int x, int y, int width, int height) {
            entry = new ModEntry(x, y, width, height);
        }

        public Builder backgroundColor(ModColor color) {
            entry.setBackgroundColor(color);
            return this;
        }

//        public Builder modWidgets(ModWidget... widgets) {
//            entry.addModWidgets(widgets);
//            return this;
//        }
//
//        public Builder vanillaWidgets(AbstractWidget... widgets) {
//            entry.addVanillaWidgets(widgets);
//            return this;
//        }

        public Builder hover(ModColor hoverColor, float fadeTicks, float intensity) {
            entry.setFadeTicks(fadeTicks);
            entry.tickFade();
            //entry.addFadingBackground(hoverColor, intensity);
            entry.hoverColor = hoverColor;
            entry.fadeIntensity = intensity;
            return this;
        }

        public ModEntry build() {
            return entry;
        }
    }
}
