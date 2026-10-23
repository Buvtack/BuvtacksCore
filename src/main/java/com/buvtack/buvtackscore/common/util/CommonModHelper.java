package com.buvtack.buvtackscore.common.util;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;

import java.util.LinkedList;
import java.util.List;

public class CommonModHelper {

    public static MinecraftServer server;

    public static Item item(String name) {
        return (Item) Item.itemRegistry.getObject(name);
    }

    public static Block block(String name) {
        return (Block) Block.blockRegistry.getObject(name);
    }

    public static Item findItem(String modId, String name) {
        return GameRegistry.findItem(modId, name);
    }

    public static Block findBlock(String modId, String name) {
        return GameRegistry.findBlock(modId, name);
    }

    public static int getItemCount(EntityPlayer player, Item item) {
        int total = 0;

        for (ItemStack stack : player.inventory.mainInventory) {
            if (stack != null && stack.getItem() == item) {
                total += stack.stackSize;
            }
        }

        return total;
    }

    public static int getItemCount(EntityPlayer player, String modId, String name) {
        Item item = GameRegistry.findItem(modId, name);
        return item != null ? getItemCount(player, item) : 0;
    }

    public static int getAvailableInventorySpace(EntityPlayer player, Item item) {
        InventoryPlayer inventory = player.inventory;
        int maxStackSize = item.getItemStackLimit();
        int result = 0;

        for (int i = 0; i < 36; i++) {
            ItemStack stack = inventory.mainInventory[i];
            if (stack == null) {
                result += maxStackSize;
            } else if (stack.getItem() == item) {
                // Optional: if your items use metadata/subtypes, you might want to
                // also check `&& stack.getItemDamage() == 0` here.
                result += (maxStackSize - stack.stackSize);
            }
        }

        return result;
    }

    public static void addItemToPlayer(EntityPlayer player, Item item, int amount) {
        ItemStack stack = new ItemStack(item, amount);
        boolean added = player.inventory.addItemStackToInventory(stack);

        // If the inventory was full and it couldn't fit everything, drop the rest on the ground
        if (!added && !player.worldObj.isRemote) {
            player.dropPlayerItemWithRandomChoice(stack, false);
        }
    }

    public static String[] splitString(String string, char delimiter) {
        List<String> list = new LinkedList<String>();
        String word = "";
        for (char ch : string.toCharArray()) {
            if (ch != delimiter) {
                word += ch;
            } else {
                list.add(word);
                word = "";
            }
        }
        list.add(word);
        return list.toArray(new String[0]);
    }

    public static boolean isMelee(DamageSource source) {
        // Check if the attacker is an entity and the damage isn't flagged as projectile/indirect
        if (source.getEntity() instanceof EntityLivingBase) {
            // Exclude projectiles like arrows, magic, etc.
            return !source.isProjectile() && source.getSourceOfDamage() == source.getEntity();
        }
        return false;
    }

    public static MinecraftServer server() {
        return server;
    }
}
