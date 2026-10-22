package com.buvtack.buvtackscore.common.util;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;

public class ModPotionHelper {

    public static boolean isPotion(ItemStack stack) {
        return stack.is(Items.POTION) || stack.is(Items.LINGERING_POTION) || stack.is(Items.SPLASH_POTION);
    }

    public static boolean hasEffects(ItemStack stack) {
        return stack.has(DataComponents.POTION_CONTENTS);
    }

    public static boolean isEffectType(ItemStack stack, Holder<Potion>... potions) {
        if (!isPotion(stack))
            return false;

        PotionContents contents = stack.get(DataComponents.POTION_CONTENTS);
        if (contents == null)
            return false;

        boolean result = false;
        for (Holder<Potion> potion : potions)
            result |= contents.is(potion);

        return result;
    }

    public static int getEffectLevel(ItemStack stack, Holder<MobEffect> effect) {
        PotionContents contents = stack.get(DataComponents.POTION_CONTENTS);
        return getEffectLevel(contents, effect);
    }

    private static int getEffectLevel(PotionContents contents, Holder<MobEffect> effect) {
        if (contents == null)
            return 0;

        for (MobEffectInstance instance : contents.getAllEffects())
            if (instance.getEffect().is(effect))
                return instance.getAmplifier() + 1;

        return 0;
    }

    public static int getEffectLevel(LivingEntity entity, Holder<MobEffect> effect) {
        MobEffectInstance instance = entity.getEffect(effect);
        if (instance != null) {
            return instance.getAmplifier() + 1;
        }

        return 0;
    }

    public static boolean hasEffect(LivingEntity entity, Holder<MobEffect> effect) {
        return getEffectLevel(entity, effect) > 0;
    }
}
