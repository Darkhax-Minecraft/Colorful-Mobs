package net.darkhax.colourfulmobs.common.items;

import net.darkhax.colourfulmobs.ColorfulMobs;
import net.darkhax.colourfulmobs.common.ColorObject;
import net.darkhax.colourfulmobs.common.ColorProperties;
import net.darkhax.colourfulmobs.common.PacketColorSync;
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
	public boolean itemInteractionForEntity(ItemStack stack,
			EntityPlayer player, EntityLivingBase entity) {

		if (!player.worldObj.isRemote) {

			ColorObject colorObj = new ColorObject(false);
			ColorProperties.setEntityColors(colorObj, entity);
			ColorfulMobs.instance.network.sendToAll(new PacketColorSync(
					colorObj, entity));
		}

		return true;
	}
}