package net.epoxide.colorfulmobs.item;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.epoxide.colorfulmobs.ColorfulMobs;
import net.epoxide.colorfulmobs.common.ColorProperties;
import net.epoxide.colorfulmobs.lib.ColorObject;
import net.epoxide.colorfulmobs.lib.Constants;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;

public class ItemDataChecker extends Item {
    
    public ItemDataChecker() {
        
        this.setCreativeTab(ColorfulMobs.tabColors);
        this.setUnlocalizedName("colorfulmobs.datachecker");
        this.setRegistryName(new ResourceLocation(Constants.MOD_ID, "data_checker"));
    }
    
    @Override
    public boolean itemInteractionForEntity (ItemStack stack, EntityPlayer player, EntityLivingBase target, EnumHand hand) {
        
        if (player.worldObj.isRemote)
            return false;
        
        String outputString = I18n.format("chat.colorfulmobs.name") + ": " + EntityList.getEntityString(target) + " ";
        
        if (ColorProperties.hasProperties(target)) {
            
            ColorObject obj = ColorProperties.getProperties(target).getColor();
            outputString = outputString + ChatFormatting.RED + I18n.format("chat.colorfulmobs.red") + ": " + (int) (obj.getRed() * 255) + " " + ChatFormatting.GREEN + I18n.format("chat.colorfulmobs.green") + ": " + (int) (obj.getGreen() * 255) + " " + ChatFormatting.BLUE + I18n.format("chat.colorfulmobs.blue") + ": " + (int) (obj.getBlue() * 255) + " " + ChatFormatting.RESET + I18n.format("chat.colorfulmobs.transparency") + ": " + (100 - (int) (obj.getAlpha() * 100)) + "%";
        }
        
        player.addChatMessage(new TextComponentString(outputString));
        return true;
    }
}