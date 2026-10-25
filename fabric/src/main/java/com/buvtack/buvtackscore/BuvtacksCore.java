package com.buvtack.buvtackscore;

import com.buvtack.buvtackscore.common.util.CommonModHelper;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class BuvtacksCore implements ModInitializer {

    @Override
    public void onInitialize() {

        // This method is invoked by the Fabric mod loader when it is ready
        // to load your mod. You can access Fabric and Common code in this
        // project.

        // Use Fabric to bootstrap the Common mod.
        CommonClass.init();

        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            Constants.LOG.info("Server starting on common side!!!");
            CommonModHelper.SERVER = server;
        });
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> {
            Constants.LOG.info("Server stopped on common side!!!");
            CommonModHelper.SERVER = null;
        });
    }
}
