package net.darkhax.colorfulmobs.common.items;

import net.darkhax.bookshelf.objects.ColorObject;
import net.darkhax.colorfulmobs.ColorfulMobs;
import net.darkhax.colorfulmobs.common.ColorProperties;
import net.darkhax.colorfulmobs.common.PacketColorSync;
import net.darkhax.colorfulmobs.common.handler.GuiHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemColorWand extends Item {

    public ItemColorWand() {

        this.setCreativeTab(ColorfulMobs.tabColor);
        this.setUnlocalizedName("colorfulmobs.colorwand");
        this.setTextureName("colorfulmobs:colorwand");
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity) {

        GuiHandler.setEntity(entity);
        player.openGui(ColorfulMobs.instance, 0, player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ);
        return true;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer player) {
        if (!itemStackIn.hasTagCompound())
            itemStackIn.setTagCompound(new NBTTagCompound());


        return itemStackIn;

    }
}