package com.buvtack.buvtackscore.common.util;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;

import com.google.common.collect.Multimap;

public class ModToolHelper {

    public static boolean isToolOrSword(ItemStack stack) {
        return isSword(stack) || isTool(stack);
    }

    public static boolean isTool(ItemStack stack) {
        if (stack == null || stack.getItem() == null) {
            return false;
        }
        Item item = stack.getItem();
        // In 1.7.10, standard tools inherit from ItemTool (pickaxes, axes, shovels) or ItemHoe
        return item instanceof ItemTool || item instanceof ItemHoe;
    }

    public static boolean isSword(ItemStack stack) {
        if (stack == null || stack.getItem() == null) {
            return false;
        }
        return stack.getItem() instanceof ItemSword;
    }

    public static double damageOf(ItemStack stack) {
        if (stack == null || stack.getItem() == null) {
            return 1.0; // Base bare-handed punch damage
        }

        double baseDamage = 1.0; // Base player punch damage
        Multimap multimap = stack.getAttributeModifiers();

        if (multimap != null) {
            String attackDamageKey = SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName();
            if (multimap.containsKey(attackDamageKey)) {
                for (Object obj : multimap.get(attackDamageKey)) {
                    if (obj instanceof AttributeModifier) {
                        baseDamage += ((AttributeModifier) obj).getAmount();
                    }
                }
            }
        }

        // Add Sharpness enchantment bonus if present
        int sharpnessLevel = ModEnchantmentHelper
            .getEnchantmentLevel(stack, net.minecraft.enchantment.Enchantment.sharpness.effectId);
        if (sharpnessLevel > 0) {
            baseDamage += (1.25F * sharpnessLevel);
        }

        return baseDamage;
    }

    public static int durabilityOf(ItemStack stack) {
        if (stack == null || !stack.isItemStackDamageable()) {
            return 0;
        }
        // In 1.7.10, getMaxDamage() is max durability, getItemDamage() is current damage taken
        return stack.getMaxDamage() - stack.getItemDamage();
    }

    public static int maxDurabilityOf(ItemStack stack) {
        if (stack == null) {
            return 0;
        }
        return stack.getMaxDamage();
    }

    public static int usedDurabilityOf(ItemStack stack) {
        if (stack == null) {
            return 0;
        }
        return stack.getItemDamage();
    }
}
