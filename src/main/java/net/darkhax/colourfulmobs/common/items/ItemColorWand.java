package net.darkhax.colourfulmobs.common.items;

import java.util.Random;

import net.darkhax.colourfulmobs.ColorfulMobs;
import net.darkhax.colourfulmobs.common.ColorObject;
import net.darkhax.colourfulmobs.common.ColorProperties;
import net.darkhax.colourfulmobs.common.PacketColorSync;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemColorWand extends Item {

    public ItemColorWand() {

        this.setCreativeTab(CreativeTabs.tabMisc);
        this.setUnlocalizedName("cm.colorwand");
        this.setTextureName("colorfulmobs:colorwand");
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity) {

        ColorObject colorObj = new ColorObject(false);
        ColorProperties.setEntityColors(colorObj, entity);
        ColorfulMobs.instance.network.sendToAll(new PacketColorSync(colorObj, entity));
        return true;
    }
}