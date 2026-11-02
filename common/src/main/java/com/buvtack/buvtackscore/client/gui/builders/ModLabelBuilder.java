package com.buvtack.buvtackscore.client.gui.builders;

import com.buvtack.buvtackscore.client.graphics.ModColor;
import com.buvtack.buvtackscore.client.gui.Alignment;
import com.buvtack.buvtackscore.client.gui.components.ModLabel;
import net.minecraft.network.chat.Component;

public class ModLabelBuilder extends DimensionalModWidgetBuilder {

    protected Component content;
    protected Alignment alignment;
    protected ModColor textColor;
    protected ModColor hoverTextColor;
    protected ModColor pressedTextColor;
    protected ModColor focusedTextColor;

    public ModLabelBuilder content(Component content) {
        this.content = content;
        return this;
    }

    public ModLabelBuilder alignment(Alignment alignment) {
        this.alignment = alignment;
        return this;
    }

    public ModLabelBuilder textColor(ModColor color) {
        textColor = color;
        return this;
    }

    public ModLabelBuilder hoverTextColor(ModColor color) {
        hoverTextColor = color;
        return this;
    }

    public ModLabelBuilder pressedTextColor(ModColor color) {
        pressedTextColor = color;
        return this;
    }

    public ModLabelBuilder focusedTextColor(ModColor color) {
        focusedTextColor = color;
        return this;
    }

    public ModLabel build() {
        ModLabel label = new ModLabel(x, y);
        buildDimensionalWidget(label);
        label.setContent(content);
        if (textColor != null)
            label.setTextColor(textColor);
        if (hoverTextColor != null)
            label.setHoverTextColor(hoverTextColor);
        if (pressedTextColor != null)
            label.setPressedTextColor(pressedTextColor);
        if (focusedTextColor != null)
            label.setFocusedTextColor(focusedTextColor);

        label.alignment(alignment);
        return label;
    }
}
