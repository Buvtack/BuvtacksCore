package com.buvtack.buvtackscore.client.gui.components;

import com.buvtack.buvtackscore.client.graphics.ModColor;
import com.buvtack.buvtackscore.client.gui.builders.ModPanelBuilder;
import net.minecraft.client.gui.components.AbstractWidget;

public class ModPanel extends DimensionalModWidget {

    public ModPanel(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public static class Builder {

        private ModPanel panel;

        protected Builder(int x, int y, int width, int height) {
            panel = new ModPanel(x, y, width, height);
        }

        public Builder backgroundColor(ModColor color) {
            panel.setBackgroundColor(color);
            return this;
        }

//        public Builder modWidgets(ModWidget... widgets) {
//            panel.addModWidgets(widgets);
//            return this;
//        }
//
//        public Builder vanillaWidgets(AbstractWidget... widgets) {
//            panel.addVanillaWidgets(widgets);
//            return this;
//        }

        public ModPanel build() {
            return panel;
        }
    }
}
