package net.epoxide.colorfulmobs.recipe;

import net.epoxide.colorfulmobs.ColorfulMobs;
import net.epoxide.colorfulmobs.handler.ConfigurationHandler;
import net.epoxide.colorfulmobs.lib.ColorObject;
import net.epoxide.colorfulmobs.lib.EnumVanillaColors;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class RecipeManager {

    public RecipeManager() {
        if (ConfigurationHandler.cloneDye)
            GameRegistry.addRecipe(new RecipeDyePowder());

        if (ConfigurationHandler.craftDye)
            createBasicDyeRecipes();

        if (ConfigurationHandler.craftBook)
            GameRegistry.addShapelessRecipe(new ItemStack(ColorfulMobs.itemDataChecker), ColorfulMobs.itemPowder, Items.book);
    }

    public void createBasicDyeRecipes() {

        int counter = 0;
        for (EnumVanillaColors color : EnumVanillaColors.values()) {

            ItemStack powderStack = new ItemStack(ColorfulMobs.itemPowder, 3);
            powderStack.setTagCompound(ColorObject.getTagFromColor(color.colorObj));
            GameRegistry.addRecipe(powderStack, new Object[] { " s ", "pdp", " p ", Character.valueOf('s'), Items.string, Character.valueOf('p'), Items.paper, Character.valueOf('d'), new ItemStack(Items.dye, 1, counter) });
            counter++;
        }
    }
}
