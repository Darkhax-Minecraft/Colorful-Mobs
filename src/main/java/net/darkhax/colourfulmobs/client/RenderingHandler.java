package net.darkhax.colourfulmobs.client;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.event.RenderLivingEvent;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class RenderingHandler {

	@SubscribeEvent
	public void onEntityRender(RenderLivingEvent.Pre event) {

		if (event.entity.getEntityData() != null
				&& event.entity.getEntityData().hasKey("ColorfulMobs")) {

			NBTTagCompound colorTag = event.entity.getEntityData()
					.getCompoundTag("ColorfulMobs");
			GL11.glPushMatrix();
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glColor4f(colorTag.getFloat("pigmentRed"),
					colorTag.getFloat("pigmentGreen"),
					colorTag.getFloat("pigmentBlue"), 1.0f);
			GL11.glPopMatrix();
		}
	}
}