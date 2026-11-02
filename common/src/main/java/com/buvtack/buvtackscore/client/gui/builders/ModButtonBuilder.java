package com.buvtack.buvtackscore.client.gui.builders;

import com.buvtack.buvtackscore.client.gui.components.ModButton;
import net.minecraft.sounds.SoundEvent;

public class ModButtonBuilder extends DimensionalModWidgetBuilder {

    private ModButton button;

    protected Runnable onClick;
    protected SoundEvent clickSound;

    protected boolean vanillaBackground = false;
    protected boolean vanillaHoverBackground = false;

    public ModButtonBuilder onClick(Runnable onClick) {
        this.onClick = onClick;
        return this;
    }

    public ModButtonBuilder clickSound(SoundEvent sound) {
        clickSound = sound;
        return this;
    }

    public ModButtonBuilder withVanillaBackground() {
        vanillaBackground = true;
        return this;
    }

    public ModButtonBuilder withVanillaHover() {
        vanillaHoverBackground = true;
        return this;
    }

    public ModButton build() {
        button = new ModButton();
        buildDimensionalWidget(button);
        button.onClick(onClick);
        button.clickSound(clickSound);

        if (vanillaBackground)
            button.addVanillaBackground();
        if (vanillaHoverBackground)
            button.addVanillaHoverBackground();

        return button;
    }
}
