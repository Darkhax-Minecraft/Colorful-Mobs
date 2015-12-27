package net.epoxide.colorfulmobs.handler;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;

import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

import net.darkhax.bookshelf.lib.ColorObject;
import net.darkhax.bookshelf.lib.util.MathsUtils;

import net.epoxide.colorfulmobs.common.ColorProperties;

public class ForgeEventHandler {
    
    @SubscribeEvent
    public void onEntityConstructed (EntityEvent.EntityConstructing event) {
        
        if (event.entity instanceof EntityLiving && !ColorProperties.hasProperties((EntityLivingBase) event.entity))
            ColorProperties.setProperties((EntityLivingBase) event.entity);
    }
    
    @SubscribeEvent
    public void onEntityJoinWorld (EntityJoinWorldEvent event) {
        
        if (event.entity instanceof EntityLiving && ColorProperties.hasProperties((EntityLivingBase) event.entity)) {
            
            ColorProperties props = ColorProperties.getProperties((EntityLiving) event.entity);
            
            if (!props.isInitialized()) {
                
                if (ConfigurationHandler.spawnRandom && MathsUtils.tryPercentage(ConfigurationHandler.spawnRate) && props.isValidTarget())
                    props.setColorObject(new ColorObject(false));
                    
                props.setInitialized();
            }
        }
    }
}