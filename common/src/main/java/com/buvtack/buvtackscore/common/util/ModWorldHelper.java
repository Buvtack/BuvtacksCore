package com.buvtack.buvtackscore.common.util;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.core.jmx.Server;

import java.lang.reflect.Method;
import java.util.Arrays;

public class ModWorldHelper {

    private static final String USE_WITHOUT_ITEM = resolveName();

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
        return raycast(entity, reach, ClipContext.Fluid.NONE);
    }

    public static BlockHitResult raycast(LivingEntity entity) {
        return raycast(entity, blockReachOf(entity), ClipContext.Fluid.NONE);
    }

    public static BlockHitResult raycast(LivingEntity entity, ClipContext.Fluid fluid) {
        return raycast(entity, blockReachOf(entity), fluid);
    }

    public static BlockHitResult raycast(LivingEntity entity, double reach, ClipContext.Fluid fluid) {
        Vec3 eyePos = entity.getEyePosition(1.0F);
        Vec3 lookPos = entity.getViewVector(1.0F);
        Vec3 endPos = eyePos.add(lookPos.scale(reach));

        ClipContext context = new ClipContext(
                eyePos,
                endPos,
                ClipContext.Block.OUTLINE,
                fluid,
                entity
        );

        return entity.level().clip(context);
    }

    public static BlockPos getBlockPosInFrontOf(LivingEntity entity, ClipContext.Fluid fluid) {
        BlockHitResult hit = raycast(entity, fluid);
        if (hit.getType() == HitResult.Type.BLOCK)
            return hit.getBlockPos();

        return null;
    }

    public static BlockState getBlockInFrontOf(LivingEntity entity, ClipContext.Fluid fluid) {
        BlockPos pos = getBlockPosInFrontOf(entity, fluid);
        if (pos != null)
            return entity.level().getBlockState(pos);

        return null;
    }

    public static boolean hasBlockInReach(LivingEntity entity, Identifier blockId, ClipContext.Fluid fluid) {
        BlockPos pos = getBlockPosInFrontOf(entity, fluid);
        if (pos == null)
            return false;

        Block target = CommonModHelper.block(blockId);
        if (target == null)
            return false;

        BlockState state = getBlockInFrontOf(entity, fluid);
        if (state == null)
            return false;

        return state.is(target);
    }

    public static ServerLevel playerLevel(ServerPlayer player) {
        return player.level();
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

    public static boolean isInteractableBlock(Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        boolean isBlockEntity = level.getBlockEntity(pos) != null;
        boolean hasMenu = state.getMenuProvider(level, pos) != null;
        boolean interactable = overridesUseWithoutItem(state);
        return isBlockEntity || hasMenu || interactable;
    }

    private static String resolveName() {
        for (Method m : BlockBehaviour.class.getDeclaredMethods()) {
            if (m.getReturnType() == InteractionResult.class
                    && Arrays.equals(m.getParameterTypes(),
                    new Class<?>[]{BlockState.class, Level.class, BlockPos.class, Player.class, BlockHitResult.class})) {
                return m.getName();
            }
        }
        throw new IllegalStateException("useWithoutItem not found");
    }

    private static boolean overridesUseWithoutItem(BlockState state) {
        for (Class<?> c = state.getBlock().getClass(); c != null && c != BlockBehaviour.class; c = c.getSuperclass()) {
            try {
                c.getDeclaredMethod(USE_WITHOUT_ITEM,
                        BlockState.class, Level.class, BlockPos.class,
                        Player.class, BlockHitResult.class);
                return true;
            } catch (NoSuchMethodException ignored) {
            }
        }
        return false;
    }
}
