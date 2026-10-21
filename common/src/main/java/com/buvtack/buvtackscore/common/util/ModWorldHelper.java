package com.buvtack.buvtackscore.common.util;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class ModWorldHelper {

    public static double blockReachOf(LivingEntity entity) {
        AttributeInstance reach = entity.getAttribute(Attributes.BLOCK_INTERACTION_RANGE);
        if (reach != null)
            return reach.getValue();

        return 4.5D;
    }

    public static double entityReachOf(LivingEntity entity) {
        AttributeInstance reach = entity.getAttribute(Attributes.ENTITY_INTERACTION_RANGE);
        if (reach != null)
            return reach.getValue();

        return 3.0D;
    }

    public static BlockHitResult raycast(LivingEntity entity, double reach) {
        return raycast(entity, reach, false);
    }

    public static BlockHitResult raycast(LivingEntity entity, boolean includeFluid) {
        return raycast(entity, blockReachOf(entity), includeFluid);
    }

    public static BlockHitResult raycast(LivingEntity entity) {
        return raycast(entity, blockReachOf(entity), false);
    }

    public static BlockHitResult raycast(LivingEntity entity, double reach, boolean includeFluid) {
        Vec3 eyePos = entity.getEyePosition(1.0F);
        Vec3 lookPos = entity.getViewVector(1.0F);
        Vec3 endPos = eyePos.add(lookPos.x * reach, lookPos.y * reach, lookPos.z * reach);

        ClipContext context = new ClipContext(
                eyePos,
                endPos,
                ClipContext.Block.OUTLINE,
                includeFluid ? ClipContext.Fluid.SOURCE_ONLY : ClipContext.Fluid.NONE,
                entity
        );

        return entity.level().clip(context);
    }

    public static BlockPos getBlockPosInFrontOf(LivingEntity entity) {
        BlockHitResult hit = raycast(entity);
        if (hit.getType() == HitResult.Type.BLOCK)
            return hit.getBlockPos();

        return null;
    }

    public static BlockState getBlockInFrontOf(LivingEntity entity) {
        BlockPos pos = getBlockPosInFrontOf(entity);
        if (pos != null)
            return entity.level().getBlockState(pos);

        return null;
    }

    public static boolean hasBlockInReach(LivingEntity entity, Identifier blockId) {
        BlockPos pos = getBlockPosInFrontOf(entity);
        if (pos == null)
            return false;

        Block target = CommonModHelper.block(blockId);
        if (target == null)
            return false;

        BlockState state = getBlockInFrontOf(entity);
        if (state == null)
            return false;

        return state.is(target);
    }

    public static ServerLevel level(ResourceKey<Level> levelKey) {
        return CommonModHelper.server().getLevel(levelKey);
    }

    public static ServerLevel overworld() {
        return CommonModHelper.server().overworld();
    }

    public static ServerLevel end() {
        return level(Level.END);
    }

    public static ServerLevel nether() {
        return level(Level.NETHER);
    }

    public static long seed() {
        return overworld().getSeed();
    }
}
