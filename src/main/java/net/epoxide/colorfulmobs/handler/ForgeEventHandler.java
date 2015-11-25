package net.epoxide.colorfulmobs.handler;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;

import net.epoxide.colorfulmobs.common.ColorProperties;
import net.epoxide.colorfulmobs.items.ItemColoredPowder;
import net.epoxide.colorfulmobs.lib.ColorObject;
import net.epoxide.colorfulmobs.lib.Utilities;

public class ForgeEventHandler {
    
    @SubscribeEvent
    public void onEntityConstructed (EntityEvent.EntityConstructing event) {
        
        if (event.entity instanceof EntityLivingBase && !ColorProperties.hasProperties((EntityLivingBase) event.entity)) {
            ColorProperties props = ColorProperties.setProperties((EntityLivingBase) event.entity);
            
            if (ConfigurationHandler.spawnRandom && Math.random() < ConfigurationHandler.spawnRate && props.isValidTarget() && !props.isInitialized())
                props.setColorObject(new ColorObject(false));
        }
    }
    
    @SubscribeEvent
    public void onEntityJoinWorld (EntityJoinWorldEvent event) {
        
        if (event.entity instanceof EntityLivingBase && !event.entity.worldObj.isRemote && ColorProperties.hasProperties((EntityLivingBase) event.entity))
            ColorProperties.getProperties((EntityLivingBase) event.entity).sync();
    }
    
    @SubscribeEvent
    public void onMobDeath (LivingDropsEvent event) {
        
        if (ConfigurationHandler.dropPowder && ColorProperties.hasProperties(event.entityLiving)) {
            
            ColorProperties props = ColorProperties.getProperties(event.entityLiving);
            
            if (props.isDyed() && !props.getColorObj().isGenericWhite()) {
                
                ItemStack stack = new ItemStack(ContentHandler.itemPowder);
                props.getColorObj().writeToItemStack(stack);
                Utilities.dropStackInWorld(event.entityLiving.worldObj, event.entityLiving.posX, event.entityLiving.posY, event.entityLiving.posZ, stack, false);
                
                if (event.source.getEntity() instanceof EntityPlayer) {
                    
                    EntityPlayer player = (EntityPlayer) event.source.getEntity();
                    player.triggerAchievement(ContentHandler.achKillDyed);
                }
            }
        }
        
        if (event.entityLiving instanceof EntityPlayer && event.source.getEntity() instanceof EntityLivingBase) {
            
            EntityPlayer player = (EntityPlayer) event.entityLiving;
            EntityLivingBase base = (EntityLivingBase) event.source.getEntity();
            
            if (ColorProperties.hasProperties(base) && !ColorProperties.getProperties(base).getColorObj().isGenericWhite())
                player.triggerAchievement(ContentHandler.achColorDeath);
        }
    }
    
    public static class FMLEventHandler {
        
        @SubscribeEvent
        public void onRecipeCraft (ItemCraftedEvent event) {
            
            if (event.crafting != null && event.crafting.getItem() instanceof ItemColoredPowder) {
                
                ColorObject color = new ColorObject(event.crafting.getTagCompound());
                if (!color.isGenericWhite())
                    
                    event.player.triggerAchievement(ContentHandler.achCloneDye);
            }
        }
    }
}