package net.epoxide.colorfulmobs.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.epoxide.colorfulmobs.ColorfulMobs;
import net.epoxide.colorfulmobs.common.ColorProperties;

public class ItemRadiantDust extends Item {
    
    public ItemRadiantDust() {
        
        this.setCreativeTab(ColorfulMobs.tabColors);
        this.setTextureName("colorfulmobs:rainbowdust");
        this.setUnlocalizedName("colorfulmobs.radiantdust");
        this.setMaxStackSize(1);
    }
    
    @Override
    public boolean itemInteractionForEntity (ItemStack stack, EntityPlayer player, EntityLivingBase entity) {
        
        if (entity instanceof EntityLivingBase && ColorProperties.hasProperties(entity)) {
            
            ColorProperties props = ColorProperties.getProperties(entity);
            props.setRadiant(true);
            props.sync();
            stack.stackSize--;
        }
        
        return true;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect (ItemStack stack, int pass) {
        
        return true;
    }
}