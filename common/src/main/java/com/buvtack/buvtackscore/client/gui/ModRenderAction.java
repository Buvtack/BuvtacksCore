package com.buvtack.buvtackscore.client.gui;

import net.minecraft.client.gui.GuiGraphicsExtractor;

@FunctionalInterface
public interface ModRenderAction extends ModAction {

    void render(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick);
}
