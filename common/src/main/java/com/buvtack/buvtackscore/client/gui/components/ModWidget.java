package com.buvtack.buvtackscore.client.gui.components;

import com.buvtack.buvtackscore.client.gui.ModRenderAction;
import com.buvtack.buvtackscore.client.gui.ModTask;
import com.buvtack.buvtackscore.client.gui.ModTaskManager;
import com.buvtack.buvtackscore.client.gui.ModTickAction;
import net.minecraft.client.gui.GuiGraphicsExtractor;

public abstract class ModWidget {

    public ModTaskManager<ModTickAction> tickTasks;
    public ModTaskManager<ModRenderAction> renderTasks;

    public int x;
    public int y;

    protected int mouseX = 0;
    protected int mouseY = 0;
    protected float partialTick = 0;

    public ModWidget(int x, int y) {
        this.x = x;
        this.y = y;

        tickTasks = new ModTaskManager<>();
        renderTasks = new ModTaskManager<>();
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
}
