package com.buvtack.buvtackscore.client;

import net.minecraft.client.Minecraft;

import com.buvtack.buvtackscore.client.util.ClientModHelper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent;

public class ClientEvents {

    @SubscribeEvent
    public void onPlayerJoin(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        if (ClientModHelper.isSinglePlayer()) ClientModHelper.SERVER = Minecraft.getMinecraft()
            .getIntegratedServer();
    }

    @SubscribeEvent
    public void onPlayerLeave(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        ClientModHelper.SERVER = null;
    }
}
