package com.buvtack.buvtackscore.client.gui.components;

import com.buvtack.buvtackscore.client.graphics.ModColor;
import com.buvtack.buvtackscore.client.graphics.ModColors;
import com.buvtack.buvtackscore.client.gui.Alignment;
import com.buvtack.buvtackscore.client.gui.DefaultTaskNames;
import com.buvtack.buvtackscore.client.gui.DefaultTaskPriorities;
import com.buvtack.buvtackscore.client.gui.ModTask;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public class ModButton extends DimensionalModWidget {

    private ModIcon icon;
    private ModLabel label;
    private ModColor backgroundColor;
    private ModColor hoverColor;

    private float fadeTicks;
    private float fade;

    private ModButton() {
        this(0, 0, 0, 0);
    }

    private ModButton(int x, int y, int width, int height) {
        super(x, y, width, height);
        icon = ModIcon.builder().build();
        label = ModLabel.builder().build();
        backgroundColor = ModColors.TRANSPARENT;
        hoverColor = ModColors.TRANSPARENT;
        fadeTicks = 0;
        fade = 0;
    }

    public void setFadeTicks(float fade) {
        fadeTicks = fade;
    }

    public void tickFade() {
        renderTasks.addTask(DefaultTaskNames.FADE,
                new ModTask<>(DefaultTaskPriorities.MEANINGLESS,
                        (graphics, mouseX, mouseY, partialTick) -> {
                            if (hovered(mouseX, mouseY))
                                fade += partialTick;
                            else
                                fade = 0;
                        }));
    }

    public void addFadingBackground(ModColor color, float intensity) {
        hoverColor = color;
        renderTasks.addTask(DefaultTaskNames.BACKGROUND,
                new ModTask<>(DefaultTaskPriorities.BACKGROUND,
                        (graphics, mouseX, mouseY, partialTick) -> {
                            if (hovered(mouseX, mouseY)) {
                                ModColor fadedColor;
                                if (fadeTicks == 0)
                                    fadedColor = backgroundColor;
                                else {
                                    float ratio = fade / fadeTicks;
                                    fadedColor = backgroundColor.lerp(hoverColor, ratio * intensity);
                                }
                                graphics.fill(left(), top(), right(), bottom(), fadedColor.color);
                            }
                        }));
    }

    public void addBackground(ModColor color) {
        renderTasks.addTask(DefaultTaskNames.BACKGROUND,
                new ModTask<>(DefaultTaskPriorities.BACKGROUND,
                        (graphics, mouseX, mouseY, partialTick) -> {
                            graphics.fill(getPaddedX(), getPaddedHeight(), getPaddedX() + getPaddedWidth(), getPaddedY() + getPaddedHeight(), color.color);
                        }));
        backgroundColor = color;
    }

    public void addVanillaBackground() {
        height = 20;
        renderTasks.addTask(DefaultTaskNames.VANILLA_BACKGROUND,
                new ModTask<>(DefaultTaskPriorities.VANILLA_BACKGROUND,
                        (graphics, mouseX, mouseY, partialTick) -> {
                            Identifier buttonTexture = Identifier.fromNamespaceAndPath("minecraft", "textures/gui/sprites/widget/button.png");
                            renderButtonTexture(graphics, buttonTexture);
                        }));
    }

    public void addVanillaHoverBackground() {
        renderTasks.addTask(DefaultTaskNames.VANILLA_BACKGROUND,
                new ModTask<>(DefaultTaskPriorities.VANILLA_BACKGROUND,
                        (graphics, mouseX, mouseY, partialTick) -> {
                            String button = hovered(mouseX, mouseY) ? "button_highlighted.png" : "button.png";
                            Identifier texture = Identifier.fromNamespaceAndPath("minecraft", "textures/gui/sprites/widgets/" + button);
                            renderButtonTexture(graphics, texture);
                        }));
    }

    private void renderButtonTexture(GuiGraphicsExtractor graphics, Identifier id) {
        int halfWidth = width / 2;
        int remainder = width % 2;
        graphics.blit(id, x, y, x + halfWidth + remainder, y + height, 0.0f, (halfWidth + remainder) / 200F, 0F, 1F);
        graphics.blit(id, x + halfWidth + remainder, y, x + width, y + height, 1F - halfWidth / 200F, 1F, 0F, 1F);
    }

    public void label(Component content, Alignment alignment) {
        int labelX = alignment == Alignment.CENTER ? centerX() : (alignment == Alignment.RIGHT ? getContentX() - getContentWidth() : getContentX());
        int labelY = centerY();

        label = ModLabel.builder()
                .coords(labelX, labelY)
                .content(content)
                .alignment(alignment)
                .build();
    }

    public void icon() {

    }

    public static class Builder {
        private ModButton button;

        private Builder() {
            button = new ModButton();
        }

        public Builder bounds(int x, int y, int width, int height) {
            button.x = x;
            button.y = y;
            button.width = width;
            button.height = height;
            return this;
        }

        public Builder padding(int padding) {
            button.padding = padding;
            return this;
        }

        public Builder margin(int margin) {
            button.margin = margin;
            return this;
        }

        public Builder label(Component content, Alignment alignment) {
            button.label(content, alignment);
            return this;
        }

        public Builder label(ModLabel label) {
            button.label = label;
            return this;
        }

        public Builder background(ModColor color) {
            button.addBackground(color);
            return this;
        }

        public Builder hover(ModColor color, float fade, float intensity) {
            button.setFadeTicks(fade);
            button.tickFade();
            button.addFadingBackground(color, intensity);
            return this;
        }

        public Builder withVanillaButtonTexture() {
            button.addVanillaBackground();
            return this;
        }

        public Builder withVanillaButtonHighlight() {
            button.addVanillaHoverBackground();
            return this;
        }

        public ModButton build() {
            return button;
        }
    }
}
