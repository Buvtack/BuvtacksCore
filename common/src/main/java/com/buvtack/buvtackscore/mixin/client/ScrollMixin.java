package com.buvtack.buvtackscore.mixin.client;

import com.buvtack.buvtackscore.client.util.ClientModHelper;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class ScrollMixin {

    @Inject(
            method = "runTick",
            at = @At("TAIL")
    )
    private void updateScroll(boolean advanceGameTime, CallbackInfo ci) {
        //ClientModHelper.setPrevScroll(ClientModHelper.scroll());
    }
}
