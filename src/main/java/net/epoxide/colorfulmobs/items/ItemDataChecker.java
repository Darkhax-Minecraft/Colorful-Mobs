package net.epoxide.colorfulmobs.items;

import net.epoxide.colorfulmobs.ColorfulMobs;
import net.epoxide.colorfulmobs.common.ColorProperties;
import net.epoxide.colorfulmobs.handler.ContentHandler;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

public class ItemDataChecker extends Item {

    public ItemDataChecker () {

        this.setCreativeTab(ColorfulMobs.tabColor);
        this.setUnlocalizedName("colorfulmobs.datachecker");
        this.setTextureName("colorfulmobs:infobook");
    }

    @Override
    public boolean itemInteractionForEntity (ItemStack item, EntityPlayer player, EntityLivingBase entity) {

        player.triggerAchievement(ContentHandler.achDataChecker);

        if (player.worldObj.isRemote)
            return false;

        String outputString = StatCollector.translateToLocal("chat.colorfulmobs.name") + ": " + EntityList.getEntityString(entity) + " ";

        if (ColorProperties.hasProperties(entity)) {

            ColorProperties obj = ColorProperties.getProperties(entity);
            outputString = outputString + EnumChatFormatting.RED + StatCollector.translateToLocal("chat.colorfulmobs.red") + ": " + (int) (obj.getColorObj().getRed() * 255) + " " + EnumChatFormatting.GREEN + StatCollector.translateToLocal("chat.colorfulmobs.green") + ": " + (int) (obj.getColorObj().getGreen() * 255) + " " + EnumChatFormatting.BLUE + StatCollector.translateToLocal("chat.colorfulmobs.blue") + ": " + (int) (obj.getColorObj().getBlue() * 255) + " " + EnumChatFormatting.RESET + StatCollector.translateToLocal("chat.colorfulmobs.transparency") + ": " + (100 - (int) (obj.getColorObj().getAlpha() * 100)) + "%";
            obj.sync();
        }

        player.addChatMessage(new ChatComponentText(outputString));
        return true;
    }
}
