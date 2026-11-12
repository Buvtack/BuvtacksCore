package com.buvtack.buvtackscore.client.gui.components;

import com.buvtack.buvtackscore.client.graphics.ModColor;
import com.buvtack.buvtackscore.client.gui.Alignment;
import com.buvtack.buvtackscore.client.gui.DefaultTaskNames;
import com.buvtack.buvtackscore.client.gui.DefaultTaskPriorities;
import com.buvtack.buvtackscore.client.gui.ModTask;
import com.buvtack.buvtackscore.client.gui.builders.ModIconBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;

public class ModIcon extends DimensionalModWidget {

    private Identifier iconId;

    public ModIcon() {
        this(0, 0, 0, 0);
    }

    public ModIcon(int x, int y, int width, int height) {
        super(x, y, width, height);
        iconId = null;
        alignment = Alignment.LEFT;

        renderTasks.addTask(DefaultTaskNames.CONTENT, new ModTask<>(DefaultTaskPriorities.CONTENT, this::renderIcon));
    }

    private void renderIcon(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        if (iconId != null)
            graphics.blit(iconId, contentLeft(), contentTop(), contentRight(), contentBottom(), 0, 1, 0, 1);
    }

    public void setIcon(Identifier id) {
        iconId = id;
    }

    public static class Builder {
        private ModIcon icon;

        private Builder() {
            icon = new ModIcon();
        }

        public Builder bounds(int x, int y, int width, int height) {
            icon.x = x;
            icon.y = y;
            icon.width = width;
            icon.height = height;
            return this;
        }

        public Builder padding(int padding) {
            icon.padding = padding;
            return this;
        }

        public Builder margin(int margin) {
            icon.margin = margin;
            return this;
        }

        public Builder icon(Identifier iconId) {
            icon.setIcon(iconId);
            return this;
        }

        public Builder alignment(Alignment alignment) {
            icon.alignment = alignment;
            return this;
        }

        public ModIcon build() {
            return icon;
        }
    }
}
