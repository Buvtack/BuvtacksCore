package com.buvtack.buvtackscore.common.util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.WorldServer;

public class ModWorldHelper {

    public static MovingObjectPosition raycast(EntityLivingBase entity, double reach) {
        if (entity == null) {
            return null;
        }
        // Entity built-in rayTrace handles eye position, view vector, and reach distance in 1.7.10
        return entity.rayTrace(reach, 1.0F);
    }

    public static WorldServer level(int dimensionId) {
        MinecraftServer server = CommonModHelper.server();
        if (server != null) {
            return server.worldServerForDimension(dimensionId);
        }
        return null;
    }

    public static WorldServer overworld() {
        return level(0);
    }

    public static WorldServer nether() {
        return level(-1);
    }

    public static WorldServer end() {
        return level(1);
    }

    public static long seed() {
        WorldServer ow = overworld();
        return ow != null ? ow.getSeed() : 0L;
    }
}
