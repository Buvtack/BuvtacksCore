package com.buvtack.buvtackscore.mixin.client;

import com.buvtack.buvtackscore.client.util.ClientModHelper;
import com.buvtack.buvtackscore.client.util.ModMouseHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public class MouseMixin {

    @Inject(
            method = "onScroll",
            at = @At("HEAD")
    )
    private void handleScroll(long handle, double xOffset, double yOffset, CallbackInfo ci) {
        if (ClientModHelper.screen() != null && Minecraft.getInstance().getWindow().handle() == handle)
            ModMouseHandler.scroll = yOffset;
    }
}
