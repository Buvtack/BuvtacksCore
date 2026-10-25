package com.buvtack.buvtackscore;

import com.buvtack.buvtackscore.client.util.ClientModHelper;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;

public class BuvtacksCoreClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            Constants.LOG.info("Server starting on client side!!!");
            ClientModHelper.SERVER = client.player.level().getServer();
        });

        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            Constants.LOG.info("Server stopped on client side!!!");
            ClientModHelper.SERVER = null;
        });
    }
}
