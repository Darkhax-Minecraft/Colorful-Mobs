package net.epoxide.colorfulmobs.client;

import org.lwjgl.opengl.GL11;

import net.epoxide.colorfulmobs.common.ColorProperties;
import net.epoxide.colorfulmobs.common.ColorProperties.IColorHolder;
import net.epoxide.colorfulmobs.lib.ColorObject;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RenderingHandler {
    
    @SubscribeEvent
    public void onEntityRenderPre (RenderLivingEvent.Pre event) {
        
        Entity entity = event.getEntity();
        
        if (entity instanceof EntityLivingBase) {
            
            if (ColorProperties.hasProperties((EntityLivingBase) entity)) {
                
                IColorHolder props = ColorProperties.getProperties((EntityLivingBase) entity);
                
                // TOOD add darkhax variant
                if (props.isRadiant()) {
                    
                    // TODO make fleece code
                }
                
                else if (props.isDyed()) {
                    
                    ColorObject obj = props.getColor();
                    
                    GlStateManager.pushMatrix();
                    GlStateManager.enableBlend();
                    GlStateManager.blendFunc(GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA);
                    GlStateManager.color(obj.getRed(), obj.getGreen(), obj.getBlue(), obj.getAlpha());
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onEntityRenderPost (RenderLivingEvent.Post event) {
        
        if (event.getEntity() instanceof EntityLivingBase) {
            
            if (ColorProperties.hasProperties(event.getEntity())) {
                
                IColorHolder props = ColorProperties.getProperties(event.getEntity());
                
                if (props.isDyed()) {
                    
                    GlStateManager.disableBlend();
                    GlStateManager.popMatrix();
                }
            }
        }
    }
}