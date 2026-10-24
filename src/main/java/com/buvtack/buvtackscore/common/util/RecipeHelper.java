package com.buvtack.buvtackscore.common.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.registry.GameRegistry;

public class RecipeHelper {

    public static void addPickaxeRecipe(Item pickaxe, Item material, Item stick) {
        GameRegistry.addRecipe(new ItemStack(pickaxe), "MMM", " B ", " B ", 'M', material, 'B', stick);
    }

    public static void addSwordRecipe(Item sword, Item material, Item stick) {
        GameRegistry.addRecipe(new ItemStack(sword),
            "M  ",
            "M  ",
            "B  ",
            'M', material, 'B', stick);

        GameRegistry.addRecipe(new ItemStack(sword),
            " M ",
            " M ",
            " B ",
            'M', material, 'B', stick);

        GameRegistry.addRecipe(new ItemStack(sword),
            "  M",
            "  M",
            "  B",
            'M', material, 'B', stick);
    }

    public static void addAxeRecipe(Item axe, Item material, Item stick) {
        GameRegistry.addRecipe(new ItemStack(axe),
            "MM ",
            "MB ",
            " B ",
            'M', material, 'B', stick);

        GameRegistry.addRecipe(new ItemStack(axe),
            " MM",
            " MB",
            "  B",
            'M', material, 'B', stick);

        GameRegistry.addRecipe(new ItemStack(axe),
            "MM ",
            "BM ",
            "B  ",
            'M', material, 'B', stick);

        GameRegistry.addRecipe(new ItemStack(axe),
            " MM",
            " BM",
            " B ",
            'M', material, 'B', stick);
    }

    public static void addShovelRecipe(Item shovel, Item material, Item stick) {
        GameRegistry.addRecipe(new ItemStack(shovel),
            "M  ",
            "B  ",
            "B  ",
            'M', material, 'B', stick);

        GameRegistry.addRecipe(new ItemStack(shovel),
            " M ",
            " B ",
            " B ",
            'M', material, 'B', stick);

        GameRegistry.addRecipe(new ItemStack(shovel),
            "  M",
            "  B",
            "  B",
            'M', material, 'B', stick);
    }

    public static void addHoeRecipe(Item hoe, Item material, Item stick) {
        GameRegistry.addRecipe(new ItemStack(hoe),
            "MM ",
            " B ",
            " B ",
            'M', material, 'B', stick);

        GameRegistry.addRecipe(new ItemStack(hoe),
            " MM",
            "  B",
            "  B",
            'M', material, 'B', stick);

        GameRegistry.addRecipe(new ItemStack(hoe),
            "MM ",
            "B  ",
            "B  ",
            'M', material, 'B', stick);

        GameRegistry.addRecipe(new ItemStack(hoe),
            " MM",
            " B ",
            " B ",
            'M', material, 'B', stick);
    }

    public static void addToolsetRecipe(Item pickaxe, Item sword, Item axe, Item shovel, Item hoe, Item material,
        Item stick) {
        addPickaxeRecipe(pickaxe, material, stick);
        addSwordRecipe(sword, material, stick);
        addAxeRecipe(axe, material, stick);
        addShovelRecipe(shovel, material, stick);
        addHoeRecipe(hoe, material, stick);
    }

    public static void addHelmetRecipe(Item helmet, Item material) {
        GameRegistry.addRecipe(new ItemStack(helmet),
            "MMM",
            "M M",
            "   ",
            'M', material);

        GameRegistry.addRecipe(new ItemStack(helmet),
            "   ",
            "MMM",
            "M M",
            'M', material);
    }

    public static void addChestplateRecipe(Item chestplate, Item material) {
        GameRegistry.addRecipe(new ItemStack(chestplate), "M M", "MMM", "MMM", 'M', material);
    }

    public static void addLeggingsRecipe(Item leggings, Item material) {
        GameRegistry.addRecipe(new ItemStack(leggings), "MMM", "M M", "M M", 'M', material);
    }

    public static void addBootsRecipe(Item boots, Item material) {
        GameRegistry.addRecipe(new ItemStack(boots),
            "M M",
            "M M",
            "   ",
            'M', material);

        GameRegistry.addRecipe(new ItemStack(boots),
            "   ",
            "M M",
            "M M",
            'M', material);
    }

    public static void addArmorRecipe(Item helmet, Item chestplate, Item leggings, Item boots, Item material) {
        addHelmetRecipe(helmet, material);
        addChestplateRecipe(chestplate, material);
        addLeggingsRecipe(leggings, material);
        addBootsRecipe(boots, material);
    }

    public static void addToolsAndArmorRecipe(Item helmet, Item chestplate, Item leggings, Item boots, Item pickaxe,
        Item sword, Item axe, Item shovel, Item hoe, Item material, Item stick) {
        addArmorRecipe(helmet, chestplate, leggings, boots, material);
        addToolsetRecipe(pickaxe, sword, axe, shovel, hoe, material, stick);
    }

    public static void addIngotBlockRecipe(Block block, Item ingot) {
        GameRegistry.addRecipe(new ItemStack(block), "###", "###", "###", '#', ingot);

        GameRegistry.addShapelessRecipe(new ItemStack(ingot, 9), block);
    }

    public static void addSmallBlockRecipe(Block block, Item ingot) {
        GameRegistry.addRecipe(new ItemStack(block),
            "II ",
            "II ",
            "   ",
            'I', ingot);

        GameRegistry.addRecipe(new ItemStack(block),
            " II",
            " II",
            "   ",
            'I', ingot);

        GameRegistry.addRecipe(new ItemStack(block),
            "   ",
            "II ",
            "II ",
            'I', ingot);

        GameRegistry.addRecipe(new ItemStack(block),
            "   ",
            " II",
            " II",
            'I', ingot);

        GameRegistry.addShapelessRecipe(new ItemStack(ingot, 4), block);
    }

    public static void addSlabRecipe(Block slab, Block material, boolean addInverse) {
        GameRegistry.addRecipe(new ItemStack(slab, 6),
            "MMM",
            "   ",
            "   ",
            'M', material);

        GameRegistry.addRecipe(new ItemStack(slab, 6),
            "   ",
            "MMM",
            "   ",
            'M', material);

        GameRegistry.addRecipe(new ItemStack(slab, 6),
            "   ",
            "   ",
            "MMM",
            'M', material);

        if (addInverse) {
            GameRegistry.addRecipe(new ItemStack(material),
                "   ",
                "S  ",
                "S  ",
                'S', slab);


            GameRegistry.addRecipe(new ItemStack(material),
                "   ",
                " S ",
                " S ",
                'S', slab);

            GameRegistry.addRecipe(new ItemStack(material),
                "   ",
                "  S",
                "  S",
                'S', slab);

            GameRegistry.addRecipe(new ItemStack(material),
                "S  ",
                "S  ",
                "   ",
                'S', slab);

            GameRegistry.addRecipe(new ItemStack(material),
                " S ",
                " S ",
                "   ",
                'S', slab);

            GameRegistry.addRecipe(new ItemStack(material),
                "  S",
                "  S",
                "   ",
                'S', slab);
        }
    }

    public static void addStairsRecipe(Block stairs, Block material, int amount) {
        GameRegistry.addRecipe(new ItemStack(stairs, amount), "M  ", "MM ", "MMM", 'M', material);

        GameRegistry.addRecipe(new ItemStack(stairs, amount), "  M", " MM", "MMM", 'M', material);
    }

    public static void addWallRecipe(Block wall, Block material, int amount) {
        GameRegistry.addRecipe(new ItemStack(wall, amount),
            "MMM",
            "MMM",
            "   ",
            'M', material);

        GameRegistry.addRecipe(new ItemStack(wall, amount),
            "   ",
            "MMM",
            "MMM",
            'M', material);
    }

    public static void addSurroundRecipe(Item outline, Item core, Item result, int amount) {
        GameRegistry.addRecipe(new ItemStack(result, amount), "OOO", "OCO", "OOO", 'O', outline, 'C', core);
    }

    public static void addSemiSurroundedRecipe(Item outline, Item core, Item result, int amount) {
        GameRegistry.addRecipe(new ItemStack(result, amount), " O ", "OCO", " O ", 'O', outline, 'C', core);
    }

    public static void addNuggetIngotRecipe(Item ingot, Item nugget) {
        // 9 nuggets -> 1 ingot
        GameRegistry.addRecipe(new ItemStack(ingot), "NNN", "NNN", "NNN", 'N', nugget);

        // 1 ingot -> 9 nuggets (shapeless)
        GameRegistry.addShapelessRecipe(new ItemStack(nugget, 9), ingot);
    }

    public static void addRodRecipe(Item rod, int amount, Item material) {
        GameRegistry.addRecipe(new ItemStack(rod, amount),
            "   ",
            "S  ",
            "S  ",
            'S', material);


        GameRegistry.addRecipe(new ItemStack(rod, amount),
            "   ",
            " S ",
            " S ",
            'S', material);

        GameRegistry.addRecipe(new ItemStack(rod, amount),
            "   ",
            "  S",
            "  S",
            'S', material);

        GameRegistry.addRecipe(new ItemStack(rod, amount),
            "S  ",
            "S  ",
            "   ",
            'S', material);

        GameRegistry.addRecipe(new ItemStack(rod, amount),
            " S ",
            " S ",
            "   ",
            'S', material);

        GameRegistry.addRecipe(new ItemStack(rod, amount),
            "  S",
            "  S",
            "   ",
            'S', material);
    }

    public static void addPlateRecipe(Item plate, Item material) {
        GameRegistry.addRecipe(new ItemStack(plate),
            "II ",
            "II ",
            "   ",
            'I', material);

        GameRegistry.addRecipe(new ItemStack(plate),
            " II",
            " II",
            "   ",
            'I', material);

        GameRegistry.addRecipe(new ItemStack(plate),
            "   ",
            "II ",
            "II ",
            'I', material);

        GameRegistry.addRecipe(new ItemStack(plate),
            "   ",
            " II",
            " II",
            'I', material);
    }
}
