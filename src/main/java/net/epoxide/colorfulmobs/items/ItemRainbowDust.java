package net.epoxide.colorfulmobs.items;

import java.util.List;

import net.epoxide.colorfulmobs.handler.AchievementHandler;
import net.epoxide.colorfulmobs.lib.ColorObject;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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
    public ColorObject applyMerger (ColorObject existingObj, ColorObject newObj) {
    
        return new ColorObject(newObj.getRed(), newObj.getGreen(), newObj.getBlue(), existingObj.getAlpha());
    }
    
    @Override
    public boolean itemInteractionForEntity (ItemStack stack, EntityPlayer player, EntityLivingBase entity) {
    
        super.itemInteractionForEntity(stack, player, entity);
        player.triggerAchievement(AchievementHandler.achRainbow);
        return true;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean advanced) {
    
        list.add(StatCollector.translateToLocal("tooltip.colorfulmobs.rainbow"));
    }
}