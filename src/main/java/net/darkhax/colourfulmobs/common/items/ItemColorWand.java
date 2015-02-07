package net.darkhax.colourfulmobs.common.items;

import java.util.Random;

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
	public boolean itemInteractionForEntity(ItemStack stack,
			EntityPlayer player, EntityLivingBase entity) {

		NBTTagCompound entityTag = entity.getEntityData();
		NBTTagCompound tag = new NBTTagCompound();
		tag.setFloat("pigmentRed", getRndColor());
		tag.setFloat("pigmentGreen", getRndColor());
		tag.setFloat("pigmentBlue", getRndColor());
		tag.setFloat("pigmentAlpha", 1.0f);
		entityTag.setTag("ColorfulMobs", tag);
		return true;
	}

	public static float getRndColor() {

		Random rnd = new Random();
		return nextIntII(1, 255, rnd) / 255;
	}

	public static float nextIntII(int min, int max, Random rnd) {

		return rnd.nextInt(max - min + 1) + min;
	}
}