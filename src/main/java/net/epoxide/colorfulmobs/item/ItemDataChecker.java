package net.epoxide.colorfulmobs.item;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;

import net.darkhax.bookshelf.lib.ColorObject;

import net.epoxide.colorfulmobs.ColorfulMobs;
import net.epoxide.colorfulmobs.common.ColorProperties;

public class ItemDataChecker extends Item {
    
    public ItemDataChecker() {
        
        this.setCreativeTab(ColorfulMobs.tabColors);
        this.setUnlocalizedName("colorfulmobs.datachecker");
        this.setTextureName("colorfulmobs:infobook");
    }
    
    @Override
    public boolean itemInteractionForEntity (ItemStack item, EntityPlayer player, EntityLivingBase entity) {
        
        if (player.worldObj.isRemote)
            return false;
            
        String outputString = StatCollector.translateToLocal("chat.colorfulmobs.name") + ": " + EntityList.getEntityString(entity) + " ";
        
        if (ColorProperties.hasProperties(entity)) {
            
            ColorObject obj = ColorProperties.getProperties(entity).getColorObj();
            outputString = outputString + EnumChatFormatting.RED + StatCollector.translateToLocal("chat.colorfulmobs.red") + ": " + (int) (obj.getRed() * 255) + " " + EnumChatFormatting.GREEN + StatCollector.translateToLocal("chat.colorfulmobs.green") + ": " + (int) (obj.getGreen() * 255) + " " + EnumChatFormatting.BLUE + StatCollector.translateToLocal("chat.colorfulmobs.blue") + ": " + (int) (obj.getBlue() * 255) + " " + EnumChatFormatting.RESET + StatCollector.translateToLocal("chat.colorfulmobs.transparency") + ": " + (100 - (int) (obj.getAlpha() * 100)) + "%";
        }
        
        player.addChatMessage(new ChatComponentText(outputString));
        return true;
    }
}