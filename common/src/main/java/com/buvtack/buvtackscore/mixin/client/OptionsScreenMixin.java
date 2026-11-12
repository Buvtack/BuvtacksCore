package com.buvtack.buvtackscore.mixin.client;

import com.buvtack.buvtackscore.client.gui.screens.TestLibraryScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OptionsScreen.class)
public class OptionsScreenMixin extends Screen {

    protected OptionsScreenMixin(Component title) {
        super(title);
    }

    @Inject(
            method = "init",
            at = @At("RETURN")
    )
    private void addTestGuiButton(CallbackInfo ci) {
        addRenderableWidget(
                Button.builder(Component.literal("Test UI Library"), button -> {
                    if (minecraft != null)
                        minecraft.setScreenAndShow(new TestLibraryScreen(Component.literal("DAMNNN")));
                })
                        .bounds(10, 10, 100, 20)
                        .build()
        );
    }
}
