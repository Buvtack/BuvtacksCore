package com.buvtack.buvtackscore.client.gui.builders;

import com.buvtack.buvtackscore.Constants;
import com.buvtack.buvtackscore.client.gui.components.ModWidget;
import net.minecraft.client.gui.components.AbstractWidget;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class Builder {

    protected int x;
    protected int y;

    protected int margin;

    protected List<Supplier<? extends ModWidget>> modWidgets = new ArrayList<>();
    protected List<Supplier<? extends AbstractWidget>> vanillaWidgets = new ArrayList<>();

    public Builder coords(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Builder modWidgets(List<Supplier<? extends ModWidget>> widgets) {
        modWidgets = widgets;
        Constants.LOG.info("Called modWidgets(List<Supplier<? extends ModWidget>>)");
        Constants.LOG.info("widgets parameter: " + widgets);
        Constants.LOG.info("modWidgets: " + modWidgets);
        return this;
    }

    public Builder vanillaWidgets(List<Supplier<? extends AbstractWidget>> widgets) {
        vanillaWidgets = widgets;
        return this;
    }

    public Builder margin(int margin) {
        this.margin = margin;
        return this;
    }

    protected void buildWidget(ModWidget widget) {
        widget.setX(x);
        widget.setY(y);
        widget.setMargin(margin);
        Constants.LOG.info("Building widget, modWidgets: " + modWidgets);
        widget.addModWidgets(modWidgets);
        widget.addVanillaWidgets(vanillaWidgets);
    }
}
