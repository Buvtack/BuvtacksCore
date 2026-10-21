package com.buvtack.buvtackscore.client.gui.components;

import com.buvtack.buvtackscore.client.graphics.ModColor;
import com.buvtack.buvtackscore.client.gui.DefaultTaskNames;
import com.buvtack.buvtackscore.client.gui.DefaultTaskPriorities;
import com.buvtack.buvtackscore.client.gui.ModTask;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;

public class ModIcon extends DimensionalModWidget {

    private Identifier iconId;

    private ModIcon() {
        this(0, 0, 0, 0);
    }

    private ModIcon(int x, int y, int width, int height) {
        super(x, y, width, height);
        iconId = null;
    }

    public void addBackground(ModColor color) {
        renderTasks.addTask(DefaultTaskNames.BACKGROUND,
                new ModTask<>(DefaultTaskPriorities.BACKGROUND,
                        (graphics, mouseX, mouseY, partialTick) -> {
                            graphics.fill(left(), top(), right(), bottom(), color.color);
                        }));
    }

    public void addGradientBackground(ModColor first, ModColor second) {
        renderTasks.addTask(DefaultTaskNames.BACKGROUND,
                new ModTask<>(DefaultTaskPriorities.BACKGROUND,
                        (graphics, mouseX, mouseY, partialTick) -> {
                            graphics.fillGradient(left(), top(), right(), bottom(), first.color, second.color);
                        }));
    }

    public void addBorder(ModColor color) {
        renderTasks.addTask(DefaultTaskNames.BORDER,
                new ModTask<>(DefaultTaskPriorities.BORDER,
                        (graphics, mouseX, mouseY, partialTick) -> {
                            graphics.outline(getPaddedX(), getPaddedY(), getPaddedWidth(), getPaddedHeight(), color.color);
                        }));
    }

    public void setIcon(Identifier id) {
        iconId = id;
        renderTasks.addTask(DefaultTaskNames.CONTENT,
                new ModTask<>(DefaultTaskPriorities.CONTENT,
                        (graphics, mouseX, mouseY, partialTick) -> {
                            graphics.blit(id, getContentX(), getContentY(), getContentX() + getContentWidth(), getContentY() + getContentHeight(),
                                    0, 1, 0, 1);
                        }));
    }

    public static Builder builder() {
        return new Builder();
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

        public Builder background(ModColor color) {
            icon.addBackground(color);
            return this;
        }

        public Builder gradientBackground(ModColor first, ModColor second) {
            icon.addGradientBackground(first, second);
            return this;
        }

        public Builder border(ModColor color) {
            icon.addBorder(color);
            return this;
        }

        public Builder icon(Identifier iconId) {
            icon.setIcon(iconId);
            return this;
        }

        public ModIcon build() {
            return icon;
        }
    }
}
