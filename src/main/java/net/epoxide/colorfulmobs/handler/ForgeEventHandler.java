package net.epoxide.colorfulmobs.handler;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;

import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

import net.darkhax.bookshelf.lib.ColorObject;
import net.darkhax.bookshelf.lib.util.MathsUtils;

import net.epoxide.colorfulmobs.ColorfulMobs;
import net.epoxide.colorfulmobs.common.ColorProperties;
import net.epoxide.colorfulmobs.common.network.PacketSyncColor;
import net.epoxide.colorfulmobs.lib.Utilities;

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
    
    @SubscribeEvent
    public void onEntityTracked (PlayerEvent.StartTracking event) {
        
        if (event.target instanceof EntityLiving && ColorProperties.hasProperties((EntityLivingBase) event.target) && !event.target.worldObj.isRemote && event.entityPlayer instanceof EntityPlayerMP)
            ColorfulMobs.network.sendTo(new PacketSyncColor(ColorProperties.getProperties((EntityLivingBase) event.target).getColorObj(), (EntityLivingBase) event.target), (EntityPlayerMP) event.entityPlayer);
    }
    
    @SubscribeEvent
    public void onMobDeath (LivingDropsEvent event) {
        
        if (ConfigurationHandler.dropPowder && ColorProperties.hasProperties(event.entityLiving)) {
            
            ColorProperties props = ColorProperties.getProperties(event.entityLiving);
            
            if (props.isDyed() && !props.getColorObj().isGenericWhite()) {
                
                ItemStack stack = new ItemStack(ContentHandler.itemRGBDust);
                props.getColorObj().writeToItemStack(stack);
                Utilities.dropStackInWorld(event.entityLiving.worldObj, event.entityLiving.posX, event.entityLiving.posY, event.entityLiving.posZ, stack, false);
            }
        }
    }
}