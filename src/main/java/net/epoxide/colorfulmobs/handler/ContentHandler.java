package net.epoxide.colorfulmobs.handler;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;

import net.minecraftforge.common.ChestGenHooks;

import cpw.mods.fml.common.registry.GameRegistry;

import net.epoxide.colorfulmobs.item.*;

public class ContentHandler {
    
    public static Item itemRGBDust;
    public static Item itemAlphaDust;
    public static Item itemRainbowDust;
    public static Item itemRainbowWand;
    public static Item itemDataChecker;
    
    /**
     * Initializes all items within the mod.
     */
    public static void initItems () {
        
        itemRGBDust = new ItemRGBDust();
        GameRegistry.registerItem(itemRGBDust, "rgb_dust");
        
        itemAlphaDust = new ItemAlphaDust();
        GameRegistry.registerItem(itemAlphaDust, "alpha_dust");
        
        itemRainbowDust = new ItemRainbowDust();
        GameRegistry.registerItem(itemRainbowDust, "rainbow_dust");
        
        itemRainbowWand = new ItemColorWand();
        GameRegistry.registerItem(itemRainbowWand, "rainbow_wand");
        
        itemDataChecker = new ItemDataChecker();
        GameRegistry.registerItem(itemDataChecker, "data_checker");
    }
    
    /**
     * Initializes all the recipes in the mod.
     */
    public static void initRecipes() {
        
    }
    
    /**
     * Initializes dungeon loot.
     */
    public static void initDungeonLoot() {
        
        for (String chestType : ConfigurationHandler.validLootLocations)
            ChestGenHooks.addItem(chestType, new WeightedRandomChestContent(new ItemStack(ContentHandler.itemRainbowDust), 1, 1, ConfigurationHandler.rainbowDustRate));
    }
}
