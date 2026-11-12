package com.buvtack.buvtackscore.mixin.client;

import net.minecraft.client.Minecraft;
import org.lwjgl.sdl.SDLKeyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class ClientTickMixin {

    @Unique
    private boolean buvtackscore$startedTextInput = false;

    @Inject(
            method = "tick",
            at = @At("HEAD")
    )
    private void tick(CallbackInfo ci) {
        if (!buvtackscore$startedTextInput) {
            SDLKeyboard.SDL_StartTextInput(Minecraft.getInstance().getWindow().handle());
            buvtackscore$startedTextInput = true;
        }
    }
}
