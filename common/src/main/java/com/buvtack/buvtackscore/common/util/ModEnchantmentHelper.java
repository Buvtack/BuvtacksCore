package com.buvtack.buvtackscore.common.util;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.item.enchantment.effects.EnchantmentAttributeEffect;

import java.util.List;

public class ModEnchantmentHelper {

    public static int getEnchantmentLevel(ItemStack stack, ResourceKey<Enchantment> enchantment) {
        ItemEnchantments enchants = stack.getOrDefault(
                DataComponents.ENCHANTMENTS,
                ItemEnchantments.EMPTY
        );

        for (Holder<Enchantment> holder : enchants.keySet()) {
            if (holder.is(enchantment))
                return enchants.getLevel(holder);
        }

        return 0;
    }

    public static boolean hasEnchantment(ItemStack stack, ResourceKey<Enchantment> enchantment) {
        return getEnchantmentLevel(stack, enchantment) > 0;
    }

    public static double enchantAttributeOf(ItemStack stack, ResourceKey<Enchantment> enchantment) {
        if (!hasEnchantment(stack, enchantment))
            return 0F;

        ItemEnchantments enchants = EnchantmentHelper.getEnchantmentsForCrafting(stack);
        for (Holder<Enchantment> holder : enchants.keySet()) {
            int level = enchants.getLevel(holder);
            List<EnchantmentAttributeEffect> effects = holder.value().effects().get(EnchantmentEffectComponents.ATTRIBUTES);

        }
        return 0;
    }
}
