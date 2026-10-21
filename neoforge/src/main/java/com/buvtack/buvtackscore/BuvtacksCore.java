package com.buvtack.buvtackscore;

import com.buvtack.buvtackscore.common.util.CommonModHelper;
import com.buvtack.buvtackscore.common.util.ModWorldHelper;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@Mod(Constants.MOD_ID)
public class BuvtacksCore {

    public BuvtacksCore(IEventBus eventBus) {

        // This method is invoked by the NeoForge mod loader when it is ready
        // to load your mod. You can access NeoForge and Common code in this
        // project.

        // Use NeoForge to bootstrap the Common mod.
        Constants.LOG.info("Hello NeoForge world!");
        CommonClass.init();

    }

    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {
        CommonModHelper.SERVER = event.getServer();
    }
}