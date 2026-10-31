package com.buvtack.buvtackscore.client.gui.components;

import com.buvtack.buvtackscore.client.graphics.ModColor;
import net.minecraft.client.gui.components.AbstractWidget;

public class ModEntry extends ModPanel {

    public ModEntry(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public void hoverColor(ModColor color) {
        hoverColor = color;
    }

    public static class Builder {

        private ModEntry entry;

        private Builder(int x, int y, int width, int height) {
            entry = new ModEntry(x, y, width, height);
        }

        public Builder backgroundColor(ModColor color) {
            entry.backgroundColor(color);
            return this;
        }

        public Builder modWidgets(ModWidget... widgets) {
            entry.addModWidgets(widgets);
            return this;
        }

        public Builder vanillaWidgets(AbstractWidget... widgets) {
            entry.addVanillaWidgets(widgets);
            return this;
        }

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
