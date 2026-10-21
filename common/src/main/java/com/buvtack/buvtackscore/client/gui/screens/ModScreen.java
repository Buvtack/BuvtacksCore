package com.buvtack.buvtackscore.client.gui.screens;

import com.buvtack.buvtackscore.client.gui.ModRenderAction;
import com.buvtack.buvtackscore.client.gui.ModTask;
import com.buvtack.buvtackscore.client.gui.ModTaskManager;
import com.buvtack.buvtackscore.client.gui.ModTickAction;
import com.buvtack.buvtackscore.client.gui.components.ModWidget;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public abstract class ModScreen extends Screen {

    public ModTaskManager<ModTickAction> tickTasks;
    public ModTaskManager<ModRenderAction> renderTasks;

    public List<ModWidget> modWidgets;

    protected ModScreen(Component title) {
        super(title);
        tickTasks = new ModTaskManager<>();
        renderTasks = new ModTaskManager<>();
        modWidgets = new ArrayList<>();
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        super.extractRenderState(graphics, mouseX, mouseY, partialTick);

        for (ModTask<ModRenderAction> task : renderTasks.sortedTasks)
            task.action.render(graphics, mouseX, mouseY, partialTick);

        for (ModWidget widget : modWidgets)
            widget.render(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    public void tick() {
        super.tick();

        for (ModTask<ModTickAction> task : tickTasks.sortedTasks)
            task.action.tick();

        for (ModWidget widget : modWidgets)
            widget.tick();
    }
}
