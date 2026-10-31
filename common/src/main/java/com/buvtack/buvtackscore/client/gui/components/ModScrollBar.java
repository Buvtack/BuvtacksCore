package com.buvtack.buvtackscore.client.gui.components;

import com.buvtack.buvtackscore.Constants;
import com.buvtack.buvtackscore.client.graphics.ModColor;
import com.buvtack.buvtackscore.client.gui.DefaultTaskNames;
import com.buvtack.buvtackscore.client.gui.DefaultTaskPriorities;
import com.buvtack.buvtackscore.client.gui.ModTask;
import com.buvtack.buvtackscore.client.util.ClientModHelper;

public class ModScrollBar extends DimensionalModWidget {

    private ModButton pivot;
    private ModColor backgroundColor;

    private int prevClickY = 0;
    private boolean clicked = false;

    private int outerHeight;

    public ModScrollBar(int x, int y, int width, int height) {
        super(x, y, width, height);
        outerHeight = 0;
    }

    public static Builder builder(int x, int y, int width, int height) {
        return new Builder(x, y, width, height);
    }

    public void pivot(ModColor color, ModColor hoverColor, float fade, float intensity, ModColor pressedColor) {
        pivot = ModButton.builder(x, y, width, height)
                .background(color)
                .hover(hoverColor, fade, intensity)
                .pressed(pressedColor)
                .build();

        tickTasks.addTask("movePivot", new ModTask<>(() -> {
            if (pivot.pressed() || clicked) {
                if (!clicked) {
                    prevClickY = (int) ClientModHelper.mouseY();
                    clicked = true;
                }
                if (prevClickY != ClientModHelper.mouseY()) {
                    int gap = (int) (ClientModHelper.mouseY() - prevClickY);
                    pivot.y += gap;
                    pivot.y = Math.min(pivot.y, bottom() - pivot.getMarginedHeight());
                    pivot.y = Math.max(pivot.y, top());
                    prevClickY = (int) ClientModHelper.mouseY();
                }
            }

            if (ClientModHelper.isNotLeftClicking())
                clicked = false;

            if (pressed() && !pivot.pressed())
                pivot.y = (int) Math.min(ClientModHelper.mouseY(), bottom() - pivot.getMarginedHeight());
        }));

        tickTasks.addTask("adjustPivotHeight", new ModTask<>(() -> {
            pivot.height = (int) ((float) height / outerHeight * height);
        }));

        renderTasks.addTask(DefaultTaskNames.CONTENT, new ModTask<>(DefaultTaskPriorities.CONTENT, pivot::render));
        tickTasks.addTask(DefaultTaskNames.CONTENT, new ModTask<>(pivot::tick));
    }

    public void backgroundColor(ModColor color) {
        backgroundColor = color;

        renderTasks.addTask(DefaultTaskNames.BACKGROUND, new ModTask<>(DefaultTaskPriorities.BACKGROUND, (graphics, mouseX, mouseY, partialTick) -> {
            graphics.fill(left(), top(), right(), bottom(), backgroundColor.color);
        }));
    }

    public void setOuterHeight(int outerHeight) {
        this.outerHeight = outerHeight;
    }

    public float progress() {
        float totalProgress = bottom() - pivot.getMarginedHeight() - y;
        return pivot.y / totalProgress;
    }

    public static class Builder {

        private ModScrollBar bar;

        private Builder(int x, int y, int width, int height) {
            bar = new ModScrollBar(x, y, width, height);
        }

        public Builder background(ModColor color) {
            bar.backgroundColor(color);
            return this;
        }

        public Builder pivot(ModColor color, ModColor hoverColor, float fade, float intensity, ModColor pressedColor) {
            bar.pivot(color, hoverColor, fade, intensity, pressedColor);
            return this;
        }

        public Builder outerHeight(int outerHeight) {
            bar.setOuterHeight(outerHeight);
            return this;
        }

        public ModScrollBar build() {
            return bar;
        }
    }
}
