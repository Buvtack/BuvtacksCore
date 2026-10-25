package com.buvtack.buvtackscore;

import com.buvtack.buvtackscore.common.util.CommonModHelper;
import com.buvtack.buvtackscore.common.util.ModWorldHelper;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;

@EventBusSubscriber
@Mod(Constants.MOD_ID)
public class BuvtacksCore {

    public BuvtacksCore(IEventBus eventBus) {

        // This method is invoked by the NeoForge mod loader when it is ready
        // to load your mod. You can access NeoForge and Common code in this
        // project.

        // Use NeoForge to bootstrap the Common mod.
//        eventBus.addListener(BuvtacksCore::onServerStarting);
//        eventBus.addListener(BuvtacksCore::onServerStopping);
        Constants.LOG.info("Hello NeoForge world!");
        CommonClass.init();

    }

    @SubscribeEvent
    public static void onServerStarting(ServerStartedEvent event) {
        Constants.LOG.info("Server starting on common side!!!");
        CommonModHelper.SERVER = event.getServer();
    }

    @SubscribeEvent
    public static void onServerStopping(ServerStoppedEvent event) {
        Constants.LOG.info("Server stopping on common side!!!");
        CommonModHelper.SERVER = null;
    }
}