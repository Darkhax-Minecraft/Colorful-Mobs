package net.epoxide.colorfulmobs.item;

import net.epoxide.colorfulmobs.lib.ColorObject;
import net.epoxide.colorfulmobs.lib.Constants;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ItemRainbowDust extends ItemColorSetter {
    
    public ItemRainbowDust() {
        
        this.setUnlocalizedName("colorfulmobs.rainbowdust");
        this.setRegistryName(new ResourceLocation(Constants.MOD_ID, "random_dust"));
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