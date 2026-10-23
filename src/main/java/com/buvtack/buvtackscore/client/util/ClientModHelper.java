package com.buvtack.buvtackscore.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;

public class ClientModHelper {

    public static MinecraftServer SERVER = null;

    public static boolean isLeftClicking() {
        return Mouse.isButtonDown(0);
    }

    public static boolean isNotLeftClicking() {
        return !Mouse.isButtonDown(0);
    }

    public static EntityPlayer player() {
        return Minecraft.getMinecraft().thePlayer;
    }

    public static GuiScreen screen() {
        return Minecraft.getMinecraft().currentScreen;
    }

    public static boolean isValidRegistryName(String name) {
        return name.indexOf(':') > 0 && name.indexOf(':') < name.length() - 1;
    }

    public static boolean isSinglePlayer() {
        return Minecraft.getMinecraft().isSingleplayer();
    }

    public static boolean connected() {
        return server() != null || isSinglePlayer();
    }

    public static boolean isAdmin() {
        EntityPlayer p = player();
        if (p == null) return false;
        if (isSinglePlayer()) {
            return true; // Singleplayer owner has full permissions
        }
        MinecraftServer srv = server();
        if (srv != null) {
            return srv.getConfigurationManager().func_152596_g(p.getGameProfile());
        }

        return false;
    }

    public static boolean isDrawingBow() {
        EntityPlayer p = player();
        return p != null && p.isUsingItem() && p.getHeldItem() != null && p.getHeldItem().getItem() instanceof ItemBow;
    }

    public static boolean isEating() {
        EntityPlayer p = player();
        if (p == null || !p.isUsingItem()) {
            return false;
        }
        ItemStack stack = p.getItemInUse();
        if (stack == null) {
            return false;
        }
        EnumAction action = stack.getItemUseAction();
        return action == EnumAction.eat || action == EnumAction.drink;
    }

    public static boolean isHolding(Item item) {
        EntityPlayer p = player();
        if (p == null || p.getHeldItem() == null) {
            return false;
        }
        return p.getHeldItem().getItem() == item;
    }

    public static boolean isHolding(ItemStack stack) {
        if (stack == null) return false;
        return isHolding(stack.getItem());
    }

    public static boolean isBlock(ItemStack stack) {
        if (stack == null || !(stack.getItem() instanceof ItemBlock)) {
            return false;
        }
        return true;
    }

    public static EntityPlayer localPlayer() {
        return player();
    }

    public static net.minecraft.client.multiplayer.WorldClient level() {
        return Minecraft.getMinecraft().theWorld;
    }

    public static long seed() {
        net.minecraft.client.multiplayer.WorldClient w = level();
        return w != null ? w.getSeed() : 0L;
    }

    public static MinecraftServer server() {
        return SERVER;
    }

    public static FontRenderer font() {
        return Minecraft.getMinecraft().fontRenderer;
    }

    public static double mouseX() {
        return Mouse.getX() * scaledWidth() / Minecraft.getMinecraft().displayWidth;
    }

    public static double mouseY() {
        Minecraft mc = Minecraft.getMinecraft();
        return mc.displayHeight - Mouse.getY() * scaledHeight() / mc.displayHeight - 1;
    }

    private static int scaledWidth() {
        Minecraft mc = Minecraft.getMinecraft();
        net.minecraft.client.gui.ScaledResolution sr = new net.minecraft.client.gui.ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
        return sr.getScaledWidth();
    }

    private static int scaledHeight() {
        Minecraft mc = Minecraft.getMinecraft();
        net.minecraft.client.gui.ScaledResolution sr = new net.minecraft.client.gui.ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
        return sr.getScaledHeight();
    }

    public static void playSound(ResourceLocation sound, float pitch) {
        Minecraft.getMinecraft().getSoundHandler().playSound(
            PositionedSoundRecord.func_147674_a(sound, pitch)
        );
    }
}
