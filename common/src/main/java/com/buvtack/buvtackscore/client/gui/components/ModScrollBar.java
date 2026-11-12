package com.buvtack.buvtackscore.client.gui.components;

import com.buvtack.buvtackscore.Constants;
import com.buvtack.buvtackscore.client.graphics.ModColor;
import com.buvtack.buvtackscore.client.gui.DefaultTaskNames;
import com.buvtack.buvtackscore.client.gui.DefaultTaskPriorities;
import com.buvtack.buvtackscore.client.gui.ModTask;
import com.buvtack.buvtackscore.client.gui.builders.ModButtonBuilder;
import com.buvtack.buvtackscore.client.util.ClientModHelper;
import net.minecraft.client.gui.GuiGraphicsExtractor;

public class ModScrollBar extends DimensionalModWidget {

    private ModButton pivot;

    private int prevClickY = 0;
    private boolean clicked = false;

    private int outerHeight;

    public ModScrollBar() {
        this(0, 0, 0, 0);
    }

    public ModScrollBar(int x, int y, int width, int height) {
        super(x, y, width, height);
        outerHeight = 0;
    }

    @Override
    public void render(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        if (pivot != null && pivot.getMarginedHeight() >= outerHeight)
            return;

        super.render(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    public void tick() {
        if (pivot != null && pivot.getMarginedHeight() >= outerHeight)
            return;

        super.tick();
    }

    public void movePivot(double progress) {
        int freeToMoveHeight = bottom() - pivot.getMarginedHeight() - y;
        int moveTo = (int) (freeToMoveHeight * progress);
        pivot.setY(moveTo + y);
    }

    public void pivot(ModColor color, ModColor hoverColor, float fade, float intensity, ModColor pressedcolor) {
        ModButtonBuilder builder = new ModButtonBuilder();
        builder.bounds(x, y, width, height);
        builder.background(color);
        builder.hover(hoverColor, fade, intensity);
        builder.pressed(pressedColor);
        pivot = builder.build();

        pivot(pivot);
    }

    public void pivot(ModButton button) {
        this.pivot = button;

        tickTasks.addTask("movePivot", new ModTask<>(() -> {
            if (pivot.getMarginedHeight() > outerHeight)
                return;

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
            //Constants.LOG.info("Pivot Height: " + pivot.height);
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
        if (pivot == null)
            return 0;

        float totalProgress = bottom() - pivot.getMarginedHeight() - y;
        return (pivot.y - y) / totalProgress;
    }

    public void setX(int x) {
        this.x = x;
        if (pivot != null)
            pivot.x = x;
    }

    public void setY(int y) {
        int prevY = this.y;
        this.y = y;
        if (pivot != null) {
            int gap = y - prevY;
            pivot.y += gap;
        }
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
