package net.epoxide.colorfulmobs.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.darkhax.bookshelf.lib.ColorObject;

import net.epoxide.colorfulmobs.ColorfulMobs;
import net.epoxide.colorfulmobs.common.ColorProperties;

public class ItemColorSetter extends Item {
    
    public ItemColorSetter() {
        
        this.setCreativeTab(ColorfulMobs.tabColors);
    }
    
    @Override
    public boolean itemInteractionForEntity (ItemStack stack, EntityPlayer player, EntityLivingBase entity) {
        
        if (!player.worldObj.isRemote) {
            
            ColorProperties entProps = ColorProperties.getProperties(entity);
            ColorObject colorObj = getColorToApply(stack);
            
            if (entProps.isDyed())
                colorObj = handleColorMerge(entProps.getColorObj(), colorObj);
                
            entProps.setColorObject(colorObj).sync();
        }
        
        if (shouldConsumeItem(stack, entity))
            stack.stackSize--;
            
        return true;
    }
    
    /**
     * Creates the ColorObject that should be applied to the entity. By default, the NBT of the
     * ItemStack will try to be used, and if the NBT is not valid, the color white will be
     * used.
     * 
     * @param stack: The ItemStack being used on an entity.
     * @return ColorObject: The ColorObject to apply to the entity.
     */
    public ColorObject getColorToApply (ItemStack stack) {
        
        if (stack.hasTagCompound())
            return new ColorObject(stack.getTagCompound());
            
        return new ColorObject(1, 1, 1);
    }
    
    /**
     * Checks to see whether or not the dye item should be consumed. By default, this is true.
     * 
     * @param stack: The ItemStack being used.
     * @param entity: The EntityLivingBase that the item is being used on.
     * @return boolean: Whether or not the ItemStack should be consumed.
     */
    public boolean shouldConsumeItem (ItemStack stack, EntityLivingBase entity) {
        
        return true;
    }
    
    /**
     * Handles how colors should be merged, when a color has already been set to a mob.
     * 
     * @param existingColor: The color that already exists on the target entity.
     * @param newColor: The new color being applied to the target entity.
     * @return ColorObject: The color that should be set as the color for that entity.
     */
    public ColorObject handleColorMerge (ColorObject existingColor, ColorObject newColor) {
        
        return newColor;
    }
}