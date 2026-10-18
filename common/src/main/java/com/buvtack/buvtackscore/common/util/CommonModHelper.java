package com.buvtack.buvtackscore.common.util;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class CommonModHelper {

    public static Item item(Identifier id) {
        return BuiltInRegistries.ITEM.getValue(id);
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

    public static int getAvailableInventorySpace(ServerPlayer player, Item item) {
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

    public static void addItemToPlayer(ServerPlayer player, Item item, int amount) {
        player.getInventory().add(new ItemStack(item, amount));
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
        return list.toArray(String[]::new);
    }

    public static boolean isMelee(DamageSource source) {
        return source.is(DamageTypes.MOB_ATTACK) || source.is(DamageTypes.PLAYER_ATTACK);
    }
}
