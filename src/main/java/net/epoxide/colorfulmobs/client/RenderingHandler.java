package net.epoxide.colorfulmobs.client;

import org.lwjgl.opengl.GL11;

import net.minecraft.entity.EntityLivingBase;

import net.minecraftforge.client.event.RenderLivingEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

import net.darkhax.bookshelf.lib.ColorObject;

import net.epoxide.colorfulmobs.common.ColorProperties;

public class RenderingHandler {
    
    @SubscribeEvent
    public void onEntityRenderPre (RenderLivingEvent.Pre event) {
        
        if (event.entity instanceof EntityLivingBase) {
            
            if (ColorProperties.hasProperties(event.entity)) {
                
                ColorProperties props = ColorProperties.getProperties(event.entity);
                
                if (props.isDyed()) {
                    
                    ColorObject obj = props.getColorObj();
                    
                    GL11.glPushMatrix();
                    GL11.glEnable(GL11.GL_BLEND);
                    GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA);
                    GL11.glColor4f(obj.getRed(), obj.getGreen(), obj.getBlue(), obj.getAlpha());
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onEntityRenderPost (RenderLivingEvent.Post event) {
        
        if (event.entity instanceof EntityLivingBase) {
            
            if (ColorProperties.hasProperties(event.entity)) {
                
                ColorProperties props = ColorProperties.getProperties(event.entity);
                
                if (props.isDyed()) {
                    
                    GL11.glDisable(GL11.GL_BLEND);
                    GL11.glPopMatrix();
                }
            }
        }
    }
}