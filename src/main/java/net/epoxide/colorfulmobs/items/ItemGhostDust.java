package net.epoxide.colorfulmobs.items;

import java.util.List;

import net.epoxide.colorfulmobs.common.ColorProperties;
import net.epoxide.colorfulmobs.lib.ColorObject;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemGhostDust extends ItemColorSetter {
    
    public ItemGhostDust() {
    
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
    public ColorObject applyMerger (ColorObject existingObj, ColorObject newObj) {
    
        return new ColorObject(existingObj.getRed(), existingObj.getGreen(), existingObj.getBlue(), mergeAlpha(existingObj.getAlpha()));
    }
    
    @Override
    public boolean itemInteractionForEntity (ItemStack stack, EntityPlayer player, EntityLivingBase entity) {
    
        if (!(ColorProperties.getPropsFromEntity(entity).getColorObj().getAlpha() > 0.0f))
            return false;
        
        super.itemInteractionForEntity(stack, player, entity);
        return true;
    }
    
    /**
     * 
     * @param existing
     * @return
     */
    public float mergeAlpha (float existing) {
    
        float newColor = existing - 0.2f;
        
        return (newColor > 1.0f) ? 1.0f : (newColor < 0.0f) ? 0.0f : newColor;
    }
}