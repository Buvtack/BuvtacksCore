package com.buvtack.buvtackscore.mixin.client;

import com.buvtack.buvtackscore.Constants;
import com.buvtack.buvtackscore.client.util.ClientModHelper;
import com.buvtack.buvtackscore.client.util.ModKeyboardHandler;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import org.lwjgl.sdl.*;
import org.lwjgl.system.ffm.mapping.Mapping;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardHandler.class)
public abstract class KeyboardMixin {

    @Shadow
    public abstract void charTyped(long handle, CharacterEvent event);

    @Inject(
            method = "keyPress",
            at = @At("HEAD")
    )
    private void handleKeys(long handle, @KeyEvent.Action int action, KeyEvent event, CallbackInfo ci) {
        Constants.LOG.info("Pressed Key");
        if (ClientModHelper.screen() != null && handle == Minecraft.getInstance().getWindow().handle()) {
            int key = event.keycode();

            if (Math.abs(action) == 1) {
                ModKeyboardHandler.pressedKeys.offer(key);
                ModKeyboardHandler.pressedKey = true;

                if (key == SDLKeycode.SDLK_RCTRL || key == SDLKeycode.SDLK_LCTRL)
                    ModKeyboardHandler.controlPressed = true;
                else if (key == SDLKeycode.SDLK_RSHIFT || key == SDLKeycode.SDLK_LSHIFT)
                    ModKeyboardHandler.shiftPressed = true;
            } else {
                if (key == SDLKeycode.SDLK_RCTRL || key == SDLKeycode.SDLK_LCTRL)
                    ModKeyboardHandler.controlPressed = false;
                else if (key == SDLKeycode.SDLK_RSHIFT || key == SDLKeycode.SDLK_LSHIFT)
                    ModKeyboardHandler.shiftPressed = false;
            }

            if (Math.abs(action) == 1 && Character.isValidCodePoint(key) && !Character.isISOControl(key) && !ModKeyboardHandler.controlPressed && !ModKeyboardHandler.shiftPressed && !buvtackscore$isNonPrintableKey(key))
                charTyped(handle, new CharacterEvent(key));
        }
    }

    @Unique
    private boolean buvtackscore$isNonPrintableKey(int keycode) {
        return keycode >= 0x40000000;
    }

    @Inject(
            method = "charTyped",
            at = @At("HEAD")
    )
    private void handleChars(long handle, CharacterEvent event, CallbackInfo ci) {
        Constants.LOG.info("Typed Char");
        if (ClientModHelper.screen() != null && handle == Minecraft.getInstance().getWindow().handle()) {
            char c = (char) event.codepoint();
            ModKeyboardHandler.enteredChars.offer(c);
            ModKeyboardHandler.enteredChar = true;
        }
    }
}
