package com.buvtack.buvtackscore.common.util;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;

public class ModToolHelper {

    public static boolean isToolOrSword(ItemStack stack) {
        return isSword(stack) || isTool(stack);
    }

    public static boolean isTool(ItemStack stack) {
        String registryName = BuiltInRegistries.ITEM.getKey(stack.getItem()).toString();
        return stack.is(ItemTags.AXES) ||
                stack.is(ItemTags.HOES) ||
                stack.is(ItemTags.PICKAXES) ||
                stack.is(ItemTags.SHOVELS) ||
                stack.is(ItemTags.SPEARS) ||
                stack.is(Items.MACE);
    }

    public static boolean isSword(ItemStack stack) {
        return stack.is(ItemTags.SWORDS);
    }

    public static float attackSpeedOf(ItemStack stack) {
        ItemAttributeModifiers modifiers = stack.getOrDefault(
                DataComponents.ATTRIBUTE_MODIFIERS,
                ItemAttributeModifiers.EMPTY
        );

        for (ItemAttributeModifiers.Entry entry : modifiers.modifiers()) {
            if (entry.attribute().is(Attributes.ATTACK_SPEED) && entry.slot().test(EquipmentSlot.MAINHAND)) {
                AttributeModifier modifier = entry.modifier();

                if (modifier.operation() == AttributeModifier.Operation.ADD_VALUE)
                    return (float) (4 + modifier.amount());
            }
        }

        return 4F;
    }

    public static double attributeOf(ItemStack stack, Holder<Attribute> attribute) {
        ItemAttributeModifiers modifiers = stack.getOrDefault(
                DataComponents.ATTRIBUTE_MODIFIERS,
                ItemAttributeModifiers.EMPTY
        );

        double result = 0;
        for (ItemAttributeModifiers.Entry entry : modifiers.modifiers()) {
            if (entry.attribute().is(attribute) && entry.slot().test(EquipmentSlot.MAINHAND)) {
                AttributeModifier modifier = entry.modifier();
                result += modifier.amount();
            }
        }

        return result;
    }

    public static int durabilityOf(ItemStack stack) {
        return stack.getMaxDamage() - stack.getDamageValue();
    }
}
