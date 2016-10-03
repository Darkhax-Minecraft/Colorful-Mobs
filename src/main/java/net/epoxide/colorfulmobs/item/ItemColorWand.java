package net.epoxide.colorfulmobs.item;

import net.epoxide.colorfulmobs.ColorfulMobs;
import net.epoxide.colorfulmobs.common.network.GuiHandler;
import net.epoxide.colorfulmobs.lib.Constants;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;

public class ItemColorWand extends Item {
    
    public ItemColorWand() {
        
        this.setCreativeTab(ColorfulMobs.tabColors);
        this.setUnlocalizedName("colorfulmobs.colorwand");
        this.setRegistryName(new ResourceLocation(Constants.MOD_ID, "wand_color"));
        this.setMaxDamage(16);
    }
    
    @Override
    public boolean itemInteractionForEntity (ItemStack stack, EntityPlayer player, EntityLivingBase target, EnumHand hand) {
        
        GuiHandler.setEntity(target);
        player.openGui(ColorfulMobs.instance, 0, player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ);
        stack.damageItem(1, player);
        return true;
    }
    
    @Override
    public boolean getIsRepairable (ItemStack input, ItemStack repairItem) {
        
        return (repairItem.getItem() instanceof ItemRainbowDust);
    }
}