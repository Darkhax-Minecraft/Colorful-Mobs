package net.epoxide.colorfulmobs.item;

import net.epoxide.colorfulmobs.ColorfulMobs;
import net.epoxide.colorfulmobs.common.ColorProperties;
import net.epoxide.colorfulmobs.common.ColorProperties.IColorHolder;
import net.epoxide.colorfulmobs.lib.Constants;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;

public class ItemRadiantDust extends Item {
    
    public ItemRadiantDust() {
        
        this.setCreativeTab(ColorfulMobs.tabColors);
        this.setUnlocalizedName("colorfulmobs.radiantdust");
        this.setRegistryName(new ResourceLocation(Constants.MOD_ID, "radiant_dust"));
        this.setMaxStackSize(1);
    }
    
    @Override
    public boolean itemInteractionForEntity (ItemStack stack, EntityPlayer player, EntityLivingBase target, EnumHand hand) {
        
        if (target instanceof EntityLivingBase && ColorProperties.hasProperties(target)) {
            
            IColorHolder props = ColorProperties.getProperties(target);
            props.setRadiant(true);
            props.sync();
            stack.stackSize--;
        }
        
        return true;
    }
}