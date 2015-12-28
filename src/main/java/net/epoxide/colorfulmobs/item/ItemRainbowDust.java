package net.epoxide.colorfulmobs.item;

import net.minecraft.item.ItemStack;

import net.darkhax.bookshelf.lib.ColorObject;

public class ItemRainbowDust extends ItemColorSetter {
    
    public ItemRainbowDust() {
        
        this.setTextureName("colorfulmobs:rainbowdust");
        this.setUnlocalizedName("colorfulmobs.rainbowdust");
    }
    
    @Override
    public ColorObject getColorToApply (ItemStack stack) {
        
        return new ColorObject(false);
    }
    
    @Override
    public ColorObject handleColorMerge (ColorObject existingObj, ColorObject newObj) {
        
        return new ColorObject(newObj.getRed(), newObj.getGreen(), newObj.getBlue(), existingObj.getAlpha());
    }
}