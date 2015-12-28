package net.epoxide.colorfulmobs.client;

import org.lwjgl.opengl.GL11;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;

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
                
                if (props.isRadiant() || !(event.entity instanceof EntityPlayer) && event.entity.getCommandSenderName().equalsIgnoreCase("darkhax")) {
                    
                    int tickOffset = event.entity.ticksExisted / 25 + event.entity.getEntityId();
                    int colorIndex = tickOffset % EntitySheep.fleeceColorTable.length;
                    int offsetIndex = (tickOffset + 1) % EntitySheep.fleeceColorTable.length;
                    float colorOffset = ((float) (event.entity.ticksExisted % 25) + event.entity.swingProgress) / 25.0F;
                    GL11.glColor3f(EntitySheep.fleeceColorTable[colorIndex][0] * (1.0F - colorOffset) + EntitySheep.fleeceColorTable[offsetIndex][0] * colorOffset, EntitySheep.fleeceColorTable[colorIndex][1] * (1.0F - colorOffset) + EntitySheep.fleeceColorTable[offsetIndex][1] * colorOffset, EntitySheep.fleeceColorTable[colorIndex][2] * (1.0F - colorOffset) + EntitySheep.fleeceColorTable[offsetIndex][2] * colorOffset);
                }
                
                else if (props.isDyed()) {
                    
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