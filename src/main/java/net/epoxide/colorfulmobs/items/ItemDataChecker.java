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
import net.minecraft.util.StatCollector;

public class ItemDataChecker extends Item {

    public ItemDataChecker() {
        
        this.setCreativeTab(ColorfulMobs.tabColor);
        this.setUnlocalizedName("dataChecker");
        this.setTextureName("colorfulmobs:data_checker");
    }
    
    public boolean itemInteractionForEntity(ItemStack item, EntityPlayer player, EntityLivingBase entity) {
        
        String outputString = "";
        
        if (ColorProperties.hasColorProperties(entity)) {
        
            ColorObject obj = ColorProperties.getPropsFromEntity(entity).colorObj;
            outputString = outputString + StatCollector.translateToLocal("chat.colorfulmobs.red") + ": " + obj.red + " " + StatCollector.translateToLocal("chat.colorfulmobs.green") + ": " + obj.green + " " + StatCollector.translateToLocal("chat.colorfulmobs.blue") + ": " + obj.blue + " " + StatCollector.translateToLocal("chat.colorfulmobs.alpha") + ": " + obj.alpha + " ";
        }
        
        outputString = outputString + StatCollector.translateToLocal("chat.colorfulmobs.name") + ": " + EntityList.getEntityString(entity);
        player.addChatMessage(new ChatComponentText(outputString));
        return true;
    }
}
