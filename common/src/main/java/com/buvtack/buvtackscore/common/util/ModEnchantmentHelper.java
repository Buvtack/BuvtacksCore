package com.buvtack.buvtackscore.common.util;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.item.enchantment.effects.EnchantmentAttributeEffect;

import java.util.List;

public class ModEnchantmentHelper {

    public static int getEnchantmentLevel(ItemStack stack, ResourceKey<Enchantment> enchantment) {
        ItemEnchantments enchants = enchantmentsOf(stack);

        for (Holder<Enchantment> holder : enchants.keySet()) {
            if (holder.is(enchantment))
                return enchants.getLevel(holder);
        }

        return 0;
    }

    public static boolean hasEnchantment(ItemStack stack, ResourceKey<Enchantment> enchantment) {
        return getEnchantmentLevel(stack, enchantment) > 0;
    }

    public static double enchantAttributeOf(ItemStack stack, Holder<Attribute> attribute) {
        double total = 0;
        ItemEnchantments enchantments = enchantmentsOf(stack);

        for (Object2IntMap.Entry<Holder<Enchantment>> entry : enchantments.entrySet()) {
            Holder<Enchantment> enchantment = entry.getKey();
            int level = entry.getIntValue();

            List<EnchantmentAttributeEffect> effects = enchantment.value().effects().get(EnchantmentEffectComponents.ATTRIBUTES);
            if (effects != null) {
                for (EnchantmentAttributeEffect effect : effects) {
                    if (effect.attribute().equals(attribute))
                        total += effect.amount().calculate(level);
                }
            }
        }

        return total;
    }

    public static ItemEnchantments enchantmentsOf(ItemStack stack) {
        if (stack.is(Items.ENCHANTED_BOOK))
            return stack.getOrDefault(
                    DataComponents.STORED_ENCHANTMENTS,
                    ItemEnchantments.EMPTY
            );

        return stack.getOrDefault(
                DataComponents.ENCHANTMENTS,
                ItemEnchantments.EMPTY
        );
    }
}
