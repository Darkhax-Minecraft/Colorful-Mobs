package net.epoxide.colorfulmobs.client;

import net.epoxide.colorfulmobs.common.ColorProperties;
import net.epoxide.colorfulmobs.lib.ColorObject;
import net.minecraftforge.client.event.RenderLivingEvent;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class RenderingHandler {
    
    @SubscribeEvent
    public void onEntityRenderPre (RenderLivingEvent.Pre event) {
    
        if (event.entity != null) {
            
            if (ColorProperties.hasColorProperties(event.entity)) {
                
                ColorObject obj = ColorProperties.getPropsFromEntity(event.entity).colorObj;
                
                GL11.glPushMatrix();
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GL11.glColor4f(obj.getRed(), obj.getGreen(), obj.getBlue(), obj.getAlpha());
            }
        }
    }
    
    @SubscribeEvent
    public void onEntityRenderPost (RenderLivingEvent.Post event) {
    
        if (ColorProperties.hasColorProperties(event.entity)) {
            
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glPopMatrix();
        }
    }
}