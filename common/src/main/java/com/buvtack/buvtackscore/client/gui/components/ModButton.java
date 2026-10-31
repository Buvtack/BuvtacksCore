package com.buvtack.buvtackscore.client.gui.components;

import com.buvtack.buvtackscore.client.graphics.ModColor;
import com.buvtack.buvtackscore.client.graphics.ModColors;
import com.buvtack.buvtackscore.client.gui.Alignment;
import com.buvtack.buvtackscore.client.gui.DefaultTaskNames;
import com.buvtack.buvtackscore.client.gui.DefaultTaskPriorities;
import com.buvtack.buvtackscore.client.gui.ModTask;
import com.buvtack.buvtackscore.client.util.ClientModHelper;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Display;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ModButton extends DimensionalModWidget {

    private Runnable onClick;
    private SoundEvent clickSound;

    private List<DimensionalModWidget> widgets;

    private boolean clickable = false;

    private ModButton() {
        this(0, 0, 0, 0);
    }

    private ModButton(int x, int y, int width, int height) {
        super(x, y, width, height);
        backgroundColor = ModColors.TRANSPARENT;
        hoverColor = ModColors.TRANSPARENT;
        fadeTicks = 0;
        fade = 0;
        onClick = null;
        clickSound = SoundEvents.UI_BUTTON_CLICK.value();
        widgets = new ArrayList<>();
    }

    public void bounds(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void onClick(Runnable onClick) {
        this.onClick = onClick;
        tickTasks.addTask(DefaultTaskNames.ONCLICK, new ModTask<>(() -> {
            if (ClientModHelper.isNotLeftClicking())
                clickable = true;

            if (ClientModHelper.isLeftClicking()) {
                if (hovered(mouseX, mouseY) && clickable) {
                    if (clickSound != null)
                        ClientModHelper.playSound(clickSound, 1.0F);

                    if (this.onClick != null)
                        this.onClick.run();
                }

                clickable = false;
            }
        }));
    }

    public void clickSound(SoundEvent sound) {
        this.clickSound = sound;
    }

    public void addVanillaBackground() {
        renderTasks.addTask(DefaultTaskNames.VANILLA_BACKGROUND,
                new ModTask<>(DefaultTaskPriorities.VANILLA_BACKGROUND,
                        (graphics, mouseX, mouseY, partialTick) -> {
                            height = 20;
                            Identifier buttonTexture = Identifier.fromNamespaceAndPath("minecraft", "textures/gui/sprites/widget/button.png");
                            renderButtonTexture(graphics, buttonTexture);
                        }));
    }

    public void addVanillaHoverBackground() {
        renderTasks.addTask(DefaultTaskNames.VANILLA_BACKGROUND,
                new ModTask<>(DefaultTaskPriorities.VANILLA_BACKGROUND,
                        (graphics, mouseX, mouseY, partialTick) -> {
                            String button = hovered(mouseX, mouseY) ? "button_highlighted.png" : "button.png";
                            Identifier texture = Identifier.fromNamespaceAndPath("minecraft", "textures/gui/sprites/widget/" + button);
                            renderButtonTexture(graphics, texture);
                        }));
    }

    private void renderButtonTexture(GuiGraphicsExtractor graphics, Identifier id) {
        int halfWidth = width / 2;
        int remainder = width % 2;
        graphics.blit(id, x, y, x + halfWidth + remainder, y + height, 0.0f, (halfWidth + remainder) / 200F, 0F, 1F);
        graphics.blit(id, x + halfWidth + remainder, y, x + width, y + height, 1F - halfWidth / 200F, 1F, 0F, 1F);
    }

    public void label(ModLabel label) {
        label(label, 0, 0);
    }

    public void label(ModLabel label, int offsetX, int offsetY) {
        widgets.add(label);

        renderTasks.addTask("adjustLabelCoords", new ModTask<>(DefaultTaskPriorities.MEANINGLESS,
                (graphics, mouseX, mouseY, partialTick) -> {
                    label.x = getXFromAlignmentOf(label.getMarginedWidth(), label.alignment) + offsetX;
                    label.y = centerY() - ClientModHelper.font().lineHeight / 2 + offsetY;
                }));

        renderTasks.addTask("renderLabel", new ModTask<>(DefaultTaskPriorities.CONTENT, label::render));
    }

    public void icon(ModIcon icon) {
        icon(icon, 0, 0);
    }

    public void icon(ModIcon icon, int offsetX, int offsetY) {
        widgets.add(icon);

        renderTasks.addTask("adjustIconCoords", new ModTask<>(DefaultTaskPriorities.MEANINGLESS,
                (graphics, mouseX, mouseY, partialTick) -> {
                    icon.x = getXFromAlignmentOf(icon.getMarginedWidth(), icon.alignment) + offsetX;
                    icon.y = centerY() - icon.getMarginedHeight() / 2 + offsetY;
                }));

        renderTasks.addTask("renderIcon", new ModTask<>(DefaultTaskPriorities.CONTENT, icon::render));
    }

    public static Builder builder(int x, int y, int width, int height) {
        return new Builder(x, y, width, height);
    }

    public static class Builder {
        private ModButton button;

        private Builder(int x, int y, int width, int height) {
            button = new ModButton(x, y, width, height);
        }

        public Builder bounds(int x, int y, int width, int height) {
            button.bounds(x, y, width, height);
            return this;
        }

        public Builder onClick(Runnable action) {
            button.onClick(action);
            return this;
        }

        public Builder clickSound(SoundEvent sound) {
            button.clickSound(sound);
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

        public Builder background(ModColor color) {
            button.backgroundColor = color;
            return this;
        }

        public Builder hover(ModColor color, float fade, float intensity) {
            button.setFadeTicks(fade);
            button.tickFade();
            button.hoverColor = color;
            button.fadeIntensity = intensity;
            return this;
        }

        public Builder pressed(ModColor color) {
            button.pressedColor = color;
            return this;
        }

        public Builder focused(ModColor color) {
            button.focusedColor = color;
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
//            button.label.x = button.x + button.label.x;
//            button.label.y = button.y + button.label.y;
//
//            button.icon.x = button.x + button.icon.x;
//            button.icon



            return button;
        }
    }
}
