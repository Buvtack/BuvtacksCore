package com.buvtack.buvtackscore.client.gui.components;

import com.buvtack.buvtackscore.client.graphics.ModColor;
import com.buvtack.buvtackscore.client.graphics.ModColors;
import com.buvtack.buvtackscore.client.gui.*;
import com.buvtack.buvtackscore.client.util.ClientModHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;

public class ModLabel extends DimensionalModWidget {

    public Component content;
    private Alignment alignment;

    private ModLabel() {
        super(0, 0, 0, 0);
    }

    private ModLabel(int x, int y) {
        super(x, y, 0, 0);
    }

    @Override
    public int getContentX() {
        return alignment == Alignment.CENTER ? x - width / 2 : (alignment == Alignment.RIGHT ? x - width : x);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private ModLabel label;

        private Builder() {
            label = new ModLabel();
        }

        public Builder coords(int x, int y) {
            label.x = x;
            label.y = y;
            return this;
        }

        public Builder content(Component content) {
            label.renderTasks.addTask(DefaultTaskNames.CONTENT, new ModTask<ModRenderAction>(DefaultTaskPriorities.CONTENT, (graphics, mouseX, mouseY, partialTick) -> {
                graphics.text(ClientModHelper.font(), (FormattedCharSequence) content, label.getContentX(), label.getContentY(), ModColors.WHITE.color);
            }));

            label.width = ClientModHelper.font().width(content.getString());
            label.height = ClientModHelper.font().lineHeight;

            return this;
        }

        public Builder alignment(Alignment alignment) {
            label.alignment = alignment;
            return this;
        }

        public Builder background(ModColor color) {
            label.renderTasks.addTask(
                    DefaultTaskNames.BACKGROUND,
                    new ModTask<>(DefaultTaskPriorities.BACKGROUND,
                            (graphics, mouseX, mouseY, partialTick) -> {
                                graphics.fill(label.getPaddedX(), label.getPaddedY(), label.getPaddedX() + label.getPaddedWidth(), label.getPaddedY() + label.getPaddedHeight(), color.color);
                            }));
            return this;
        }

        public Builder gradientBackground(ModColor first, ModColor second) {
            label.renderTasks.addTask(
                    DefaultTaskNames.BACKGROUND,
                    new ModTask<>(DefaultTaskPriorities.BACKGROUND,
                            (graphics, _,_,_) -> {
                                graphics.fillGradient(label.getPaddedX(), label.getPaddedY(), label.getPaddedX() + label.getPaddedWidth(), label.getPaddedY() + label.getPaddedHeight(), first.color, second.color);
                            }));
            return this;
        }

        public Builder border(ModColor color) {
            label.renderTasks.addTask(DefaultTaskNames.BORDER,
                    new ModTask<>(DefaultTaskPriorities.BORDER,
                            (graphics, mouseX, mouseY, partialTick) -> {
                                graphics.outline(label.getPaddedX(), label.getPaddedY(), label.getPaddedWidth(), label.getPaddedHeight(), color.color);
                            }));
            return this;
        }

        public Builder padding(int padding) {
            label.padding = padding;
            return this;
        }

        public Builder margin(int margin) {
            label.margin = margin;
            return this;
        }

        public ModLabel build() {
            return label;
        }
    }
}
