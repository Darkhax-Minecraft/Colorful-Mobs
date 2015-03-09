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
            outputString = outputString + EnumChatFormatting.RED + StatCollector.translateToLocal("chat.colorfulmobs.red") + ": " + (int) (obj.red * 255) + " " + EnumChatFormatting.GREEN + StatCollector.translateToLocal("chat.colorfulmobs.green") + ": " + (int) (obj.green * 255) + " " + EnumChatFormatting.BLUE + StatCollector.translateToLocal("chat.colorfulmobs.blue") + ": " + (int) (obj.blue * 255) + " " + EnumChatFormatting.RESET + StatCollector.translateToLocal("chat.colorfulmobs.transparency") + ": " + (int) (obj.alpha * 100) + "% ";
        }

        outputString = outputString + StatCollector.translateToLocal("chat.colorfulmobs.name") + ": " + EntityList.getEntityString(entity);
        player.addChatMessage(new ChatComponentText(outputString));
        return true;
    }
}
