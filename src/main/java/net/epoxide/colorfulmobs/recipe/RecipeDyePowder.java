package net.epoxide.colorfulmobs.recipe;

import net.epoxide.colorfulmobs.ColorfulMobs;
import net.epoxide.colorfulmobs.items.ItemColoredPowder;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class RecipeDyePowder implements IRecipe {
    
    @Override
    public boolean matches (InventoryCrafting inventory, World world) {
    
        int itemCount = 0;
        ItemStack mainStack = null;
        
        for (int slot = 0; slot < inventory.getSizeInventory(); slot++) {
            
            ItemStack currentStack = inventory.getStackInSlot(slot);
            
            if (currentStack != null && currentStack.getItem() instanceof ItemColoredPowder) {
                
                ItemColoredPowder powder = (ItemColoredPowder) currentStack.getItem();
                
                if (!powder.getColorToApply(currentStack).isGenericWhite()) {
                    
                    if (mainStack != null)
                        return false;
                    
                    else {
                        
                        mainStack = currentStack;
                    }
                }
                
                if (powder.getColorToApply(currentStack).isGenericWhite())
                    itemCount++;
            }
        }
        
        return mainStack != null && itemCount > 0;
    }
    
    @Override
    public ItemStack getCraftingResult (InventoryCrafting inventory) {
    
        int itemCount = 0;
        ItemStack mainStack = null;
        
        for (int slot = 0; slot < inventory.getSizeInventory(); slot++) {
            
            ItemStack currentStack = inventory.getStackInSlot(slot);
            
            if (currentStack != null && currentStack.getItem() instanceof ItemColoredPowder) {
                
                ItemColoredPowder powder = (ItemColoredPowder) currentStack.getItem();
                
                if (!powder.getColorToApply(currentStack).isGenericWhite()) {
                    
                    if (mainStack != null)
                        return null;
                    
                    else {
                        
                        mainStack = currentStack;
                    }
                }
                
                if (powder.getColorToApply(currentStack).isGenericWhite())
                    itemCount++;
            }
        }
        
        if (mainStack != null && itemCount >= 1) {
            
            ItemStack output = new ItemStack(ColorfulMobs.itemPowder, itemCount + 1);
            output.setTagCompound(mainStack.getTagCompound());
            return output;
        }
        
        return null;
    }
    
    @Override
    public int getRecipeSize () {
    
        return 9;
    }
    
    @Override
    public ItemStack getRecipeOutput () {
    
        return null;
    }
}