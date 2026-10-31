package com.buvtack.buvtackscore.client.gui.components;

import com.buvtack.buvtackscore.client.graphics.ModColor;
import com.buvtack.buvtackscore.client.gui.DefaultTaskNames;
import com.buvtack.buvtackscore.client.gui.DefaultTaskPriorities;
import com.buvtack.buvtackscore.client.gui.ModTask;
import net.minecraft.client.gui.components.AbstractWidget;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModPanel extends DimensionalModWidget {

    protected List<ModWidget> modWidgets;
    protected List<AbstractWidget> vanillaWidgets;

    public ModPanel(int x, int y, int width, int height) {
        super(x, y, width, height);
        modWidgets = new ArrayList<>();
        vanillaWidgets = new ArrayList<>();

        renderTasks.addTask(DefaultTaskNames.renderModWidgets, new ModTask<>(DefaultTaskPriorities.CONTENT, (graphics, mouseX, mouseY, partialTick) -> {
            for (ModWidget widget : modWidgets)
                widget.render(graphics, mouseX, mouseY, partialTick);
        }));

        tickTasks.addTask(DefaultTaskNames.tickModWidgets, new ModTask<>(DefaultTaskPriorities.BACKGROUND, () -> {
            for (ModWidget widget : modWidgets)
                widget.tick();
        }));

        renderTasks.addTask(DefaultTaskNames.renderVanillaWidgets, new ModTask<>(DefaultTaskPriorities.CONTENT, (graphics, mouseX, mouseY, partialTick) -> {
            for (AbstractWidget widget : vanillaWidgets)
                widget.extractRenderState(graphics, mouseX, mouseY, partialTick);
        }));

        renderTasks.addTask(DefaultTaskNames.BACKGROUND, new ModTask<>(DefaultTaskPriorities.BACKGROUND, (graphics, mouseX, mouseY, partialTick) -> {
            if (backgroundColor != null)
                graphics.fill(left(), top(), right(), bottom(), backgroundColor.color);
        }));
    }

    public void backgroundColor(ModColor color) {
        backgroundColor = color;
    }

    public void addModWidgets(ModWidget... widgets) {
        modWidgets.addAll(Arrays.asList(widgets));
    }

    public void addVanillaWidgets(AbstractWidget... widgets) {
        vanillaWidgets.addAll(Arrays.asList(widgets));
    }

    public static Builder builder(int x, int y, int width, int height) {
        return new Builder(x, y, width, height);
    }

    public static class Builder {

        private ModPanel panel;

        protected Builder(int x, int y, int width, int height) {
            panel = new ModPanel(x, y, width, height);
        }

        public Builder backgroundColor(ModColor color) {
            panel.backgroundColor(color);
            return this;
        }

        public Builder modWidgets(ModWidget... widgets) {
            panel.addModWidgets(widgets);
            return this;
        }

        public Builder vanillaWidgets(AbstractWidget... widgets) {
            panel.addVanillaWidgets(widgets);
            return this;
        }

        public ModPanel build() {
            return panel;
        }
    }
}
