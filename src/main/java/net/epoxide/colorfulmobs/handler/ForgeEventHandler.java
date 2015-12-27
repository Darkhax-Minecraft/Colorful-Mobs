package net.epoxide.colorfulmobs.handler;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;

import net.minecraftforge.event.entity.EntityEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

import net.epoxide.colorfulmobs.common.ColorProperties;

public class ForgeEventHandler {

    @SubscribeEvent
    public void onEntityConstructed (EntityEvent.EntityConstructing event) {
    
        if (event.entity instanceof EntityLiving && ColorProperties.hasProperties((EntityLivingBase) event.entity))
            ColorProperties.setProperties((EntityLivingBase) event.entity);
    }
}