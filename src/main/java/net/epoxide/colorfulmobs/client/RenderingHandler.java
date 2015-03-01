package net.epoxide.colorfulmobs.client;

import net.darkhax.bookshelf.objects.ColorObject;
import net.epoxide.colorfulmobs.common.ColorProperties;
import net.epoxide.colorfulmobs.handler.ConfigurationHandler;
import net.minecraft.entity.EntityList;
import net.minecraftforge.client.event.RenderLivingEvent;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class RenderingHandler {

    @SubscribeEvent
    public void onEntityRenderPre(RenderLivingEvent.Pre event) {

        if (event.entity != null) {
            
            if (event.entity == null || ConfigurationHandler.limitMobs && !ConfigurationHandler.validMobs.contains(EntityList.getEntityString(event.entity)))
                return;
            
            
            if (ColorProperties.hasColorProperties(event.entity)) {

                ColorObject obj = ColorProperties.getPropsFromEntity(event.entity).colorObj;

                GL11.glPushMatrix();
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glColor4f(obj.red, obj.green, obj.blue, obj.alpha);
            }
        }
    }

    @SubscribeEvent
    public void onEntityRenderPost(RenderLivingEvent.Post event) {

        if (event.entity != null) {
            
            if (event.entity == null || ConfigurationHandler.limitMobs && !ConfigurationHandler.validMobs.contains(EntityList.getEntityString(event.entity)))
                return;
        }
        
        if (ColorProperties.hasColorProperties(event.entity)) {

            GL11.glDisable(GL11.GL_BLEND);
            GL11.glPopMatrix();
        }
    }
}