package net.epoxide.colorfulmobs.items;

import net.epoxide.colorfulmobs.ColorfulMobs;
import net.epoxide.colorfulmobs.common.ColorProperties;
import net.epoxide.colorfulmobs.handler.ConfigurationHandler;
import net.epoxide.colorfulmobs.handler.GuiHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemColorWand extends Item {

    public ItemColorWand() {

        this.setCreativeTab(ColorfulMobs.tabColor);
        this.setUnlocalizedName("colorfulmobs.colorwand");
        this.setTextureName("colorfulmobs:colorwand");
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity) {

        if (ConfigurationHandler.limitMobs && !ColorProperties.isValidMob(entity))
            return false;

        GuiHandler.setEntity(entity);
        player.openGui(ColorfulMobs.instance, 0, player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ);
        return true;
    }
}