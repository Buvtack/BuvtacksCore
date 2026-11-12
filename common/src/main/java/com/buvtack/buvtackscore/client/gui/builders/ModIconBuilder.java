package com.buvtack.buvtackscore.client.gui.builders;

import com.buvtack.buvtackscore.client.gui.components.ModIcon;
import net.minecraft.resources.Identifier;

public class ModIconBuilder extends DimensionalModWidgetBuilder {

    private Identifier iconId;

    public ModIconBuilder icon(Identifier iconId) {
        this.iconId = iconId;
        return this;
    }

    public ModIcon build() {
        ModIcon icon = new ModIcon(x, y, width, height);
        buildDimensionalWidget(icon);
        icon.setIcon(iconId);

        return icon;
    }
}
