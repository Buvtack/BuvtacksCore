package com.buvtack.buvtackscore;

import net.minecraftforge.common.MinecraftForge;

import com.buvtack.buvtackscore.client.ClientEvents;

import cpw.mods.fml.common.event.FMLInitializationEvent;

public class ClientProxy extends CommonProxy {

    // Override CommonProxy methods here, if you want a different behaviour on the client (e.g. registering renders).
    // Don't forget to call the super methods as well.

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        MinecraftForge.EVENT_BUS.register(new ClientEvents());
    }
}
