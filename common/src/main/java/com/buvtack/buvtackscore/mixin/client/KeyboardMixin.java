package com.buvtack.buvtackscore.mixin.client;

import com.buvtack.buvtackscore.client.util.ClientModHelper;
import com.buvtack.buvtackscore.client.util.ModKeyboardHandler;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardHandler.class)
public class KeyboardMixin {

    @Inject(
            method = "keyPress",
            at = @At("HEAD")
    )
    private void handleKeys(long handle, @KeyEvent.Action int action, KeyEvent event, CallbackInfo ci) {
        if (ClientModHelper.screen() != null && handle == Minecraft.getInstance().getWindow().handle()) {
            if (action == GLFW.GLFW_PRESS || action == GLFW.GLFW_REPEAT) {
                ModKeyboardHandler.pressedKeys.offer(event.key());
                ModKeyboardHandler.pressedKey = true;

                if (event.key() == GLFW.GLFW_KEY_RIGHT_CONTROL || event.key() == GLFW.GLFW_KEY_LEFT_CONTROL)
                    ModKeyboardHandler.controlPressed = true;
                else if (event.key() == GLFW.GLFW_KEY_RIGHT_SHIFT || event.key() == GLFW.GLFW_KEY_LEFT_SHIFT)
                    ModKeyboardHandler.shiftPressed = true;
            } else if (action == GLFW.GLFW_RELEASE) {
                if (event.key() == GLFW.GLFW_KEY_RIGHT_CONTROL || event.key() == GLFW.GLFW_KEY_LEFT_CONTROL)
                    ModKeyboardHandler.controlPressed = false;
                else if (event.key() == GLFW.GLFW_KEY_RIGHT_SHIFT || event.key() == GLFW.GLFW_KEY_LEFT_SHIFT)
                    ModKeyboardHandler.shiftPressed = false;
            }
        }
    }

    @Inject(
            method = "charTyped",
            at = @At("HEAD")
    )
    private void handleChars(long handle, CharacterEvent event, CallbackInfo ci) {
        if (ClientModHelper.screen() != null && handle == Minecraft.getInstance().getWindow().handle()) {
            char c = (char) event.codepoint();
            ModKeyboardHandler.enteredChars.offer(c);
            ModKeyboardHandler.enteredChar = true;
        }
    }
}
