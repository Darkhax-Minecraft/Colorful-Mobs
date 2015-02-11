package net.darkhax.colourfulmobs.common;

import net.darkhax.colourfulmobs.ColorfulMobs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class MobDataHandler {

	@SubscribeEvent
	public void onStartTrackingMob(EntityEvent.EntityConstructing event) {

		if (event.entity instanceof EntityLiving
				&& !(event.entity instanceof EntityPlayer))
			ColorProperties.setPropsToEntity((EntityLivingBase) event.entity);
	}

	@SubscribeEvent
	public void onWorldJoin(PlayerEvent.StartTracking event) {

		if (event.target instanceof EntityLiving
				&& !(event.target instanceof EntityPlayer)
				&& !event.target.worldObj.isRemote) {
			ColorfulMobs.instance.network
					.sendToAll(new PacketColorSync(
							ColorProperties
									.getPropsFromEntity((EntityLivingBase) event.target).colorObj,
							(EntityLivingBase) event.target));
		}
	}
}