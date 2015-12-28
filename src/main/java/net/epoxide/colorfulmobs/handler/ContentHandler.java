package net.epoxide.colorfulmobs.handler;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;

import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.oredict.ShapedOreRecipe;

import cpw.mods.fml.common.registry.GameRegistry;

import net.darkhax.bookshelf.lib.ColorObject;
import net.darkhax.bookshelf.lib.VanillaColor;

import net.epoxide.colorfulmobs.item.*;
import net.epoxide.colorfulmobs.recipe.RecipeDyePowder;

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
    public static void initRecipes () {
        
        if (ConfigurationHandler.cloneDye)
            GameRegistry.addRecipe(new RecipeDyePowder());
            
        if (ConfigurationHandler.craftDye)
            for (VanillaColor color : VanillaColor.values()) {
                
                ItemStack powderStack = new ItemStack(ContentHandler.itemRGBDust, 3);
                new ColorObject(color.color).writeToItemStack(powderStack);
                GameRegistry.addRecipe(new ShapedOreRecipe(powderStack, new Object[] { " s ", "pdp", " p ", Character.valueOf('s'), Items.string, Character.valueOf('p'), Items.paper, Character.valueOf('d'), color.getDyeName() }));
            }
            
        if (ConfigurationHandler.craftBook)
            GameRegistry.addShapelessRecipe(new ItemStack(ContentHandler.itemDataChecker), ContentHandler.itemRGBDust, Items.book);
            
        if (ConfigurationHandler.craftGhostDust)
            GameRegistry.addShapedRecipe(new ItemStack(ContentHandler.itemAlphaDust, 3), new Object[] { " s ", "pdp", " p ", Character.valueOf('s'), Items.string, Character.valueOf('p'), Items.paper, Character.valueOf('d'), Items.quartz });
            
        if (ConfigurationHandler.craftRainbowWand)
            GameRegistry.addShapedRecipe(new ItemStack(ContentHandler.itemRainbowWand), new Object[] { "xxx", "xyx", "xxx", Character.valueOf('x'), ContentHandler.itemRainbowDust, Character.valueOf('y'), Items.stick });
    }
    
    /**
     * Initializes dungeon loot.
     */
    public static void initDungeonLoot () {
        
        for (String chestType : ConfigurationHandler.validLootLocations)
            ChestGenHooks.addItem(chestType, new WeightedRandomChestContent(new ItemStack(ContentHandler.itemRainbowDust), 1, 1, ConfigurationHandler.rainbowDustRate));
    }
}
