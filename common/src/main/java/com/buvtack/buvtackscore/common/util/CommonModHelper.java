package com.buvtack.buvtackscore.common.util;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.LinkedList;
import java.util.List;

public class CommonModHelper {

    public static MinecraftServer SERVER = null;

    public static Item item(Identifier id) {
        return BuiltInRegistries.ITEM.getValue(id);
    }

    public static Block block(Identifier id) {
        return BuiltInRegistries.BLOCK.getValue(id);
    }

    public static int getItemCount(Player player, Item item) {
        int total = 0;

        for (ItemStack stack : player.getInventory())
            if (!stack.isEmpty() && stack.is(item))
                total += stack.getCount();

        ItemStack offhand = player.getOffhandItem();
        if (!offhand.isEmpty() && offhand.is(item))
            total += offhand.getCount();

        return total;
    }

    public static int getItemCount(Player player, Identifier itemId) {
        Item item = item(itemId);
        return getItemCount(player, item);
    }

    public static int getAvailableInventorySpace(Player player, Item item) {
        Inventory inventory = player.getInventory();
        int maxStackSize = item.getDefaultMaxStackSize();
        int result = 0;

        for (int i = 0; i < 36; i++) {
            ItemStack stack = inventory.getItem(i);
            if (stack.isEmpty())
                result += maxStackSize;
            else if (stack.is(item))
                result += (maxStackSize - stack.getCount());
        }

        return result;
    }

    public static void addItemToPlayer(Player player, Item item, int amount) {
        player.addItem(new ItemStack(item, amount));
        player.containerMenu.broadcastChanges();
        player.inventoryMenu.broadcastChanges();
    }

    public static String[] splitString(String string, char delimiter) {
        List<String> list = new LinkedList<>();
        String word = "";
        for (char ch : string.toCharArray()) {
            if (ch != delimiter)
                word += ch;
            else {
                list.add(word);
                word = "";
            }
        }
        list.add(word);
        return list.toArray(String[]::new);
    }

    public static boolean isMelee(DamageSource source) {
        return source.is(DamageTypes.MOB_ATTACK) || source.is(DamageTypes.PLAYER_ATTACK);
    }

    public static MinecraftServer server() {
        return SERVER;
    }
}
