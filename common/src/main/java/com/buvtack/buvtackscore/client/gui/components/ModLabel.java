package com.buvtack.buvtackscore.client.gui.components;

import com.buvtack.buvtackscore.client.graphics.ModColor;
import com.buvtack.buvtackscore.client.graphics.ModColors;
import com.buvtack.buvtackscore.client.gui.*;
import com.buvtack.buvtackscore.client.util.ClientModHelper;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;

public class ModLabel extends DimensionalModWidget {

    public Component content;
    private ModColor textColor;
    private ModColor hoverTextColor;
    private ModColor pressedTextColor;
    private ModColor focusedTextColor;

    public ModLabel() {
        this(0, 0);
    }

    public ModLabel(int x, int y) {
        super(x, y, 0, 0);

        textColor = ModColors.WHITE;
        renderTasks.addTask(DefaultTaskNames.CONTENT, new ModTask<>(DefaultTaskPriorities.CONTENT, this::renderContent));
    }

    private void renderContent(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        if (content != null) {
            ModColor color = null;
            if (focused && focusedTextColor != null)
                color = focusedTextColor;
            else if (pressed(mouseX, mouseY) && pressedTextColor != null)
                color = pressedTextColor;
            else if (hovered(mouseX, mouseY) && hoverTextColor != null)
                color = hoverTextColor;
            else if (textColor != null)
                color = textColor;

            if (color != null)
                graphics.text(ClientModHelper.font(), content.getString(), getContentX(), getContentY(), color.color);
        }
    }

    public void alignment(Alignment alignment) {
        this.alignment = alignment;
    }

    public void setTextColor(ModColor color) {
        textColor = color;
    }

    public void setHoverTextColor(ModColor color) {
        hoverTextColor = color;
    }

    public void setPressedTextColor(ModColor color) {
        pressedTextColor = color;
    }

    public void setFocusedTextColor(ModColor color) {
        focusedTextColor = color;
    }

    public void setContent(Component content) {
        this.content = content;
        width = ClientModHelper.font().width(content.getString());
        height = ClientModHelper.font().lineHeight;
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
                graphics.text(ClientModHelper.font(), content, label.getContentX(), label.getContentY(), ModColors.WHITE.color);
                label.width = ClientModHelper.font().width(content.getString());
                label.height = ClientModHelper.font().lineHeight;
            }));

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
