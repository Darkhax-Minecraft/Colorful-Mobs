package net.epoxide.colorfulmobs.recipe;

import net.epoxide.colorfulmobs.ColorfulMobs;
import net.epoxide.colorfulmobs.handler.ConfigurationHandler;
import net.epoxide.colorfulmobs.lib.EnumVanillaColors;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class RecipeManager {
    
    public RecipeManager() {
    
        if (ConfigurationHandler.cloneDye)
            GameRegistry.addRecipe(new RecipeDyePowder());
        
        if (ConfigurationHandler.craftDye)
            createBasicDyeRecipes();
        
        if (ConfigurationHandler.craftBook)
            GameRegistry.addShapelessRecipe(new ItemStack(ColorfulMobs.itemDataChecker), ColorfulMobs.itemPowder, Items.book);
        
        if (ConfigurationHandler.craftGhostDust)
            GameRegistry.addShapedRecipe(new ItemStack(ColorfulMobs.itemGhostDust, 3), new Object[] { " s ", "pdp", " p ", Character.valueOf('s'), Items.string, Character.valueOf('p'), Items.paper, Character.valueOf('d'), Items.quartz });
        
        if (ConfigurationHandler.craftRainbowWand)
            GameRegistry.addShapedRecipe(new ItemStack(ColorfulMobs.itemColorWand), new Object[] { "xxx", "xyx", "xxx", Character.valueOf('x'), ColorfulMobs.itemRainbowDust, Character.valueOf('y'), Items.stick });
    }
    
    public void createBasicDyeRecipes () {
    
        for (EnumVanillaColors color : EnumVanillaColors.values()) {
            
            ItemStack powderStack = new ItemStack(ColorfulMobs.itemPowder, 3);
            color.colorObj.writeToItemStack(powderStack);
            GameRegistry.addRecipe(new ShapedOreRecipe(powderStack, new Object[] { " s ", "pdp", " p ", Character.valueOf('s'), Items.string, Character.valueOf('p'), Items.paper, Character.valueOf('d'), color.colorName }));
        }
    }
}
