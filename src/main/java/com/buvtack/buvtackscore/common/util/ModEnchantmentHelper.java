package com.buvtack.buvtackscore.common.util;

import java.util.Map;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;

public class ModEnchantmentHelper {

    public static int getEnchantmentLevel(ItemStack stack, int enchantmentId) {
        if (stack == null) {
            return 0;
        }
        return EnchantmentHelper.getEnchantmentLevel(enchantmentId, stack);
    }

    public static int getEnchantmentLevel(ItemStack stack, Enchantment enchantment) {
        if (enchantment == null) {
            return 0;
        }
        return getEnchantmentLevel(stack, enchantment.effectId);
    }

    public static boolean hasEnchantment(ItemStack stack, int enchantmentId) {
        return getEnchantmentLevel(stack, enchantmentId) > 0;
    }

    public static boolean hasEnchantment(ItemStack stack, Enchantment enchantment) {
        return getEnchantmentLevel(stack, enchantment) > 0;
    }

    @SuppressWarnings("unchecked")
    public static Map<Integer, Integer> enchantmentsOf(ItemStack stack) {
        if (stack == null) {
            return null;
        }

        // Handles both regular enchanted items and enchanted books in 1.7.10
        return EnchantmentHelper.getEnchantments(stack);
    }
}
