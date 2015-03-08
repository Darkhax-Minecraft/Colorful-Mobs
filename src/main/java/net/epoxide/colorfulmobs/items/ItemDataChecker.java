package net.epoxide.colorfulmobs.items;

import net.darkhax.bookshelf.objects.ColorObject;
import net.epoxide.colorfulmobs.ColorfulMobs;
import net.epoxide.colorfulmobs.common.ColorProperties;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

public class ItemDataChecker extends Item {

    public ItemDataChecker() {

        this.setCreativeTab(ColorfulMobs.tabColor);
        this.setUnlocalizedName("colorfulmobs.datachecker");
        this.setTextureName("colorfulmobs:infobook");
    }

    public boolean itemInteractionForEntity(ItemStack item, EntityPlayer player, EntityLivingBase entity) {

        if (player.worldObj.isRemote)
            return false;

        String outputString = "";

        if (ColorProperties.hasColorProperties(entity)) {

            ColorObject obj = ColorProperties.getPropsFromEntity(entity).colorObj;
            outputString = outputString + EnumChatFormatting.RED + StatCollector.translateToLocal("chat.colorfulmobs.red") + ": " + obj.red + " " + EnumChatFormatting.GREEN + StatCollector.translateToLocal("chat.colorfulmobs.green") + ": " + obj.green + " " + EnumChatFormatting.BLUE + StatCollector.translateToLocal("chat.colorfulmobs.blue") + ": " + obj.blue + " " + EnumChatFormatting.RESET + StatCollector.translateToLocal("chat.colorfulmobs.alpha") + ": " + obj.alpha + " ";
        }

        outputString = outputString + StatCollector.translateToLocal("chat.colorfulmobs.name") + ": " + EntityList.getEntityString(entity);
        player.addChatMessage(new ChatComponentText(outputString));
        return true;
    }
}
