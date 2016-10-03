package net.epoxide.colorfulmobs.recipe;

import net.epoxide.colorfulmobs.handler.ContentHandler;
import net.epoxide.colorfulmobs.item.ItemRGBDust;
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
            
            if (currentStack != null && currentStack.getItem() instanceof ItemRGBDust) {
                
                ItemRGBDust powder = (ItemRGBDust) currentStack.getItem();
                
                if (!powder.getColorToApply(currentStack).isWhite()) {
                    
                    if (mainStack != null)
                        return false;
                    
                    else {
                        
                        mainStack = currentStack;
                    }
                }
                
                if (powder.getColorToApply(currentStack).isWhite())
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
            
            if (currentStack != null && currentStack.getItem() instanceof ItemRGBDust) {
                
                ItemRGBDust powder = (ItemRGBDust) currentStack.getItem();
                
                if (!powder.getColorToApply(currentStack).isWhite()) {
                    
                    if (mainStack != null)
                        return null;
                    
                    else {
                        
                        mainStack = currentStack;
                    }
                }
                
                if (powder.getColorToApply(currentStack).isWhite())
                    itemCount++;
            }
        }
        
        if (mainStack != null && itemCount >= 1) {
            
            ItemStack output = new ItemStack(ContentHandler.itemRGBDust, itemCount + 1);
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
    
    @Override
    public ItemStack[] getRemainingItems (InventoryCrafting inv) {
        
        ItemStack[] results = new ItemStack[inv.getSizeInventory()];
        
        for (int slot = 0; slot < results.length; ++slot) {
            
            ItemStack itemstack = inv.getStackInSlot(slot);
            results[slot] = net.minecraftforge.common.ForgeHooks.getContainerItem(itemstack);
        }
        
        return results;
    }
}