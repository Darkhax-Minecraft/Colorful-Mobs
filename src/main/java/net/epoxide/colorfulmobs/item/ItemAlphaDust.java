package net.epoxide.colorfulmobs.item;

import java.util.List;

import net.darkhax.bookshelf.lib.ColorObject;

import net.epoxide.colorfulmobs.common.ColorProperties;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemAlphaDust extends ItemColorSetter {
    
    public ItemAlphaDust() {
        
        this.setUnlocalizedName("colorfulmobs.ghostdust");
        this.setTextureName("colorfulmobs:ghost_dust");
    }
    
    @Override
    public boolean hasEffect (ItemStack stack, int pass) {
        
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List tip, boolean isAdvanced) {
        
        tip.add(StatCollector.translateToLocal("tooltip.colorfulmobs.invisibledust"));
    }
    
    @Override
    public ColorObject getColorToApply (ItemStack stack) {
        
        return new ColorObject(1.0f, 1.0f, 1.0f, 0.8f);
    }
    
    @Override
    public ColorObject handleColorMerge (ColorObject existingObj, ColorObject newObj) {
        
        return new ColorObject(existingObj.getRed(), existingObj.getGreen(), existingObj.getBlue(), mergeAlpha(existingObj.getAlpha()));
    }
    
    @Override
    public boolean itemInteractionForEntity (ItemStack stack, EntityPlayer player, EntityLivingBase entity) {
        
        if (!(ColorProperties.getProperties(entity).getColorObj().getAlpha() > 0.0f))
            return false;
            
        super.itemInteractionForEntity(stack, player, entity);
        return true;
    }
    
    /**
     * Makes the alpha value 20% less. Has some checks to prevent the alpha value from going
     * lower then 0f.
     * 
     * @param existing: The existing alpha value.
     * @return float: The new alpha value.
     */
    public float mergeAlpha (float existing) {
        
        float newColor = existing - 0.2f;       
        return (newColor > 1.0f) ? 1.0f : (newColor < 0.0f) ? 0.0f : newColor;
    }
}