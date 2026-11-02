package com.buvtack.buvtackscore.client.gui.components;

import com.buvtack.buvtackscore.Constants;
import com.buvtack.buvtackscore.client.gui.*;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public abstract class ModWidget {

    public ModTaskManager<ModTickAction> tickTasks;
    public ModTaskManager<ModRenderAction> renderTasks;

    public int x;
    public int y;

    public int margin;

    protected int mouseX = 0;
    protected int mouseY = 0;
    protected float partialTick = 0;

    protected List<ModWidget> modWidgets;
    protected List<AbstractWidget> vanillaWidgets;

    public ModWidget(int x, int y) {
        this.x = x;
        this.y = y;

        margin = 0;

        tickTasks = new ModTaskManager<>();
        renderTasks = new ModTaskManager<>();

        modWidgets = new ArrayList<>();
        vanillaWidgets = new ArrayList<>();

        renderTasks.addTask(DefaultTaskNames.renderModWidgets, new ModTask<>(DefaultTaskPriorities.CONTENT, (graphics, mouseX, mouseY, partialTick) -> {
            for (ModWidget widget : modWidgets)
                widget.render(graphics, mouseX, mouseY, partialTick);
        }));

        renderTasks.addTask(DefaultTaskNames.renderVanillaWidgets, new ModTask<>(DefaultTaskPriorities.CONTENT, (graphics, mouseX, mouseY, partialTick) -> {
            for (AbstractWidget widget : vanillaWidgets)
                widget.extractRenderState(graphics, mouseX, mouseY, partialTick);
        }));

        tickTasks.addTask(DefaultTaskNames.tickModWidgets, new ModTask<>(() -> {
            for (ModWidget widget : modWidgets)
                widget.tick();
        }));
    }

    public void render(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.partialTick = partialTick;

        for (ModTask<ModRenderAction> task : renderTasks.sortedTasks)
            task.action.render(graphics, mouseX, mouseY, partialTick);
    }

    public void tick() {
        for (ModTask<ModTickAction> task : tickTasks.sortedTasks)
            task.action.tick();
    }

    public void addModWidgets(List<Supplier<? extends ModWidget>> widgetsSuppliers) {
        if (widgetsSuppliers == null) {
            Constants.LOG.warn("widgetsSuppliers is null!");
            return;
        }

        List<ModWidget> widgets = new ArrayList<>();
        widgetsSuppliers.forEach(supplier -> {
            ModWidget widget = supplier.get();
            widgets.add(widget);
        });

        for (ModWidget widget : widgets) {
            widget.setX(widget.x + x);
            widget.setY(widget.y + y);
        }

        modWidgets.addAll(widgets);
    }

    public void addVanillaWidgets(List<Supplier<? extends AbstractWidget>> widgetsSuppliers) {
        if (widgetsSuppliers == null) {
            Constants.LOG.warn("widgetsSuppliers is null!");
            return;
        }

        List<AbstractWidget> widgets = new ArrayList<>();
        widgetsSuppliers.forEach(supplier -> {
            AbstractWidget widget = supplier.get();
            widgets.add(widget);
        });

        for (AbstractWidget widget : widgets) {
            widget.setY(widget.getY() + y);
            widget.setX(widget.getX() + x);
        }

        vanillaWidgets.addAll(widgets);
    }

    public void setX(int x) {
        int prevX = this.x;
        int gap = x - prevX;
        for (ModWidget widget : modWidgets)
            widget.setX(widget.x + gap);

        for (AbstractWidget widget : vanillaWidgets)
            widget.setX(widget.getX() + gap);

        this.x = x;
    }

    public void setY(int y) {
        int prevY = this.y;
        int gap = y - prevY;
        for (ModWidget widget : modWidgets)
            widget.setY(widget.y + gap);

        for (AbstractWidget widget : vanillaWidgets)
            widget.setY(widget.getY() + gap);

        this.y = y;
    }

    public void setMargin(int margin) {
        this.margin = margin;
    }
}
