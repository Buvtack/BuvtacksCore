package com.buvtack.buvtackscore;

import com.buvtack.buvtackscore.client.util.ClientModHelper;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;

@EventBusSubscriber
@Mod(value = Constants.MOD_ID, dist = Dist.CLIENT)
public class BuvtacksCoreClient {

    @SubscribeEvent
    public static void onPlayerJoin(ClientPlayerNetworkEvent.LoggingIn event) {
        Constants.LOG.info("Server starting on client side!!!");
        ClientModHelper.SERVER = event.getPlayer().level().getServer();
    }

    @SubscribeEvent
    public static void onPlayerDisconnect(ClientPlayerNetworkEvent.LoggingOut event) {
        Constants.LOG.info("Server stopped on client side!!!");
        ClientModHelper.SERVER = null;
    }
}
