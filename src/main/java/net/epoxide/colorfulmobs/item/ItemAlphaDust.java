package net.epoxide.colorfulmobs.item;

import java.util.List;

import net.epoxide.colorfulmobs.common.ColorProperties;
import net.epoxide.colorfulmobs.lib.ColorObject;
import net.epoxide.colorfulmobs.lib.Constants;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemAlphaDust extends ItemColorSetter {
    
    public ItemAlphaDust() {
        
        this.setUnlocalizedName("colorfulmobs.ghostdust");
        this.setRegistryName(new ResourceLocation(Constants.MOD_ID, "alpha_dust"));
    }
    
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List<String> tip, boolean isAdvanced) {
        
        tip.add(I18n.format("tooltip.colorfulmobs.invisibledust"));
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
    public boolean itemInteractionForEntity (ItemStack stack, EntityPlayer player, EntityLivingBase target, EnumHand hand) {
        
        if (!(ColorProperties.getProperties(target).getColor().getAlpha() > 0.0f))
            return false;
        
        return super.itemInteractionForEntity(stack, player, target, hand);
    }
    
    public float mergeAlpha (float existing) {
        
        float newColor = existing - 0.2f;
        return (newColor > 1.0f) ? 1.0f : (newColor < 0.0f) ? 0.0f : newColor;
    }
}