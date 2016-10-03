package net.epoxide.colorfulmobs.item;

import net.epoxide.colorfulmobs.ColorfulMobs;
import net.epoxide.colorfulmobs.common.ColorProperties;
import net.epoxide.colorfulmobs.common.ColorProperties.IColorHolder;
import net.epoxide.colorfulmobs.lib.ColorObject;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public class ItemColorSetter extends Item {
    
    public ItemColorSetter() {
        
        this.setCreativeTab(ColorfulMobs.tabColors);
    }
    
    @Override
    public boolean itemInteractionForEntity (ItemStack stack, EntityPlayer player, EntityLivingBase target, EnumHand hand) {
        
        if (ColorProperties.hasProperties(target)) {
            
            if (!player.worldObj.isRemote) {
                
                IColorHolder entProps = ColorProperties.getProperties(target);
                ColorObject colorObj = getColorToApply(stack);
                
                if (entProps.isDyed())
                    colorObj = handleColorMerge(entProps.getColor(), colorObj);
                
                entProps.setColor(colorObj);
                entProps.sync();
            }
            
            if (shouldConsumeItem(stack, target))
                stack.stackSize--;
        }
        
        return true;
    }
    
    public ColorObject getColorToApply (ItemStack stack) {
        
        if (stack.hasTagCompound())
            return new ColorObject(stack.getTagCompound());
        
        return new ColorObject(1, 1, 1);
    }
    
    public boolean shouldConsumeItem (ItemStack stack, EntityLivingBase entity) {
        
        return true;
    }
    
    public ColorObject handleColorMerge (ColorObject existingColor, ColorObject newColor) {
        
        return newColor;
    }
}