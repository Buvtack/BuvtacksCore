package com.buvtack.buvtackscore.mixin.client;

import com.buvtack.buvtackscore.client.util.ClientModHelper;
import org.lwjgl.sdl.SDLEvents;
import org.lwjgl.sdl.SDL_Event;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SDLEvents.class)
public class SDLMixin {

    @Inject(method = "SDL_PollEvent", at = @At("RETURN"))
    private static void onPollEvent(SDL_Event event, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue() && event != null) {
            ClientModHelper.handleSDLEvent(event);
        }
    }
}
