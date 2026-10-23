package com.buvtack.buvtackscore.common.util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import java.util.Collection;

public class ModPotionHelper {

    public static boolean isPotion(ItemStack stack) {
        if (stack == null) {
            return false;
        }
        // In 1.7.10, standard potion items include regular potions and splash potions
        return stack.getItem() == Items.potionitem;
    }

    public static boolean hasEffects(ItemStack stack) {
        if (!isPotion(stack)) {
            return false;
        }
        // In 1.7.10, potion effects on items are stored in NBT or can be retrieved via PotionHelper/Items
        return Items.potionitem.getEffects(stack) != null && !Items.potionitem.getEffects(stack).isEmpty();
    }

    public static boolean isEffectType(ItemStack stack, Potion... potions) {
        if (!isPotion(stack)) {
            return false;
        }

        Collection<PotionEffect> effects = Items.potionitem.getEffects(stack);
        if (effects == null) {
            return false;
        }

        boolean result = false;
        for (Potion targetPotion : potions) {
            for (PotionEffect effect : effects) {
                if (effect.getPotionID() == targetPotion.id) {
                    result = true;
                    break;
                }
            }
            if (result) break;
        }

        return result;
    }

    public static int getEffectLevel(ItemStack stack, Potion effect) {
        if (!isPotion(stack) || effect == null) {
            return 0;
        }

        Collection<PotionEffect> effects = Items.potionitem.getEffects(stack);
        if (effects == null) {
            return 0;
        }

        for (PotionEffect instance : effects) {
            if (instance.getPotionID() == effect.id) {
                // Amplifier 0 is level 1, so add 1 to match modern level convention
                return instance.getAmplifier() + 1;
            }
        }

        return 0;
    }

    public static int getEffectLevel(EntityLivingBase entity, Potion effect) {
        if (entity == null || effect == null) {
            return 0;
        }

        if (entity.isPotionActive(effect)) {
            PotionEffect instance = entity.getActivePotionEffect(effect);
            if (instance != null) {
                return instance.getAmplifier() + 1;
            }
        }

        return 0;
    }

    public static boolean hasEffect(EntityLivingBase entity, Potion effect) {
        return getEffectLevel(entity, effect) > 0;
    }
}
