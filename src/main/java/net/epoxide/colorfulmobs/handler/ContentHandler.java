package net.epoxide.colorfulmobs.handler;

import net.epoxide.colorfulmobs.dispenser.BehaviorDispenseDye;
import net.epoxide.colorfulmobs.item.ItemAlphaDust;
import net.epoxide.colorfulmobs.item.ItemColorWand;
import net.epoxide.colorfulmobs.item.ItemDataChecker;
import net.epoxide.colorfulmobs.item.ItemRGBDust;
import net.epoxide.colorfulmobs.item.ItemRadiantDust;
import net.epoxide.colorfulmobs.item.ItemRainbowDust;
import net.epoxide.colorfulmobs.lib.ColorObject;
import net.epoxide.colorfulmobs.lib.VanillaColor;
import net.epoxide.colorfulmobs.recipe.RecipeDyePowder;
import net.minecraft.block.BlockDispenser;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ContentHandler {
    
    public static Item itemRGBDust;
    public static Item itemAlphaDust;
    public static Item itemRainbowDust;
    public static Item itemRainbowWand;
    public static Item itemDataChecker;
    public static Item itemRadiantDust;
    
    /**
     * Initializes all items within the mod.
     */
    public static void initItems () {
        
        itemRGBDust = new ItemRGBDust();
        GameRegistry.register(itemRGBDust);
        
        itemAlphaDust = new ItemAlphaDust();
        GameRegistry.register(itemAlphaDust);
        
        itemRainbowDust = new ItemRainbowDust();
        GameRegistry.register(itemRainbowDust);
        
        itemRainbowWand = new ItemColorWand();
        GameRegistry.register(itemRainbowWand);
        
        itemDataChecker = new ItemDataChecker();
        GameRegistry.register(itemDataChecker);
        
        itemRadiantDust = new ItemRadiantDust();
        GameRegistry.register(itemRadiantDust);
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
                new ColorObject(color.getColor()).write(powderStack);
                GameRegistry.addRecipe(new ShapedOreRecipe(powderStack, new Object[] { " s ", "pdp", " p ", Character.valueOf('s'), Items.STRING, Character.valueOf('p'), Items.PAPER, Character.valueOf('d'), color.getDyeName() }));
            }
        
        if (ConfigurationHandler.craftBook)
            GameRegistry.addShapelessRecipe(new ItemStack(ContentHandler.itemDataChecker), ContentHandler.itemRGBDust, Items.BOOK);
        
        if (ConfigurationHandler.craftGhostDust)
            GameRegistry.addShapedRecipe(new ItemStack(ContentHandler.itemAlphaDust, 3), new Object[] { " s ", "pdp", " p ", Character.valueOf('s'), Items.STRING, Character.valueOf('p'), Items.PAPER, Character.valueOf('d'), Items.QUARTZ });
        
        if (ConfigurationHandler.craftRainbowWand)
            GameRegistry.addShapedRecipe(new ItemStack(ContentHandler.itemRainbowWand), new Object[] { "xxx", "xyx", "xxx", Character.valueOf('x'), ContentHandler.itemRainbowDust, Character.valueOf('y'), Items.STICK });
    }
    
    /**
     * Initializes the miscellaneous stuff.
     */
    public static void initMisc () {
        
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(itemRGBDust, new BehaviorDispenseDye());
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(itemAlphaDust, new BehaviorDispenseDye());
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(itemRainbowDust, new BehaviorDispenseDye());
    }
}
