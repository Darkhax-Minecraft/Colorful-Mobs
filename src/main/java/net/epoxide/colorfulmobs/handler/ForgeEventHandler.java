package net.epoxide.colorfulmobs.handler;

import net.epoxide.colorfulmobs.ColorfulMobs;
import net.epoxide.colorfulmobs.common.ColorProperties;
import net.epoxide.colorfulmobs.common.PacketColorSync;
import net.epoxide.colorfulmobs.items.ItemColoredPowder;
import net.epoxide.colorfulmobs.lib.ColorObject;
import net.epoxide.colorfulmobs.lib.Utilities;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;

public class ForgeEventHandler {
    
    @SubscribeEvent
    public void onEntityConstructed (EntityEvent.EntityConstructing event) {
    
        if (event.entity instanceof EntityLiving) {
            
            ColorProperties props = ColorProperties.setPropsToEntity((EntityLivingBase) event.entity);
            
            if (ConfigurationHandler.spawnRandom && Math.random() < ConfigurationHandler.spawnRate && props.isValidTarget() && !props.isInitialized())
                props.setColorObject(new ColorObject(false));
        }
    }
    
    @SubscribeEvent
    public void onEntityTracked (PlayerEvent.StartTracking event) {
    
        if (event.target instanceof EntityLiving && ColorProperties.hasColorProperties((EntityLivingBase) event.target) && !event.target.worldObj.isRemote)
            ColorfulMobs.network.sendToAll(new PacketColorSync(ColorProperties.getPropsFromEntity((EntityLivingBase) event.target).getColorObj(), (EntityLivingBase) event.target));
    }
    
    @SubscribeEvent
    public void onMobDeath (LivingDropsEvent event) {
    
        if (ConfigurationHandler.dropPowder && ColorProperties.hasColorProperties(event.entityLiving)) {
            
            ColorProperties props = ColorProperties.getPropsFromEntity(event.entityLiving);
            
            if (props.isDyed() && !props.getColorObj().isGenericWhite()) {
                
                ItemStack stack = new ItemStack(ColorfulMobs.itemPowder);
                props.getColorObj().writeToItemStack(stack);
                Utilities.dropStackInWorld(event.entityLiving.worldObj, event.entityLiving.posX, event.entityLiving.posY, event.entityLiving.posZ, stack, false);
                
                if (event.source.getEntity() instanceof EntityPlayer) {
                    
                    EntityPlayer player = (EntityPlayer) event.source.getEntity();
                    player.triggerAchievement(AchievementHandler.achKillDyed);
                }
            }
        }
        
        if (event.entityLiving instanceof EntityPlayer && event.source.getEntity() instanceof EntityLivingBase) {
            
            EntityPlayer player = (EntityPlayer) event.entityLiving;
            EntityLivingBase base = (EntityLivingBase) event.source.getEntity();
            
            if (ColorProperties.hasColorProperties(base) && !ColorProperties.getPropsFromEntity(base).getColorObj().isGenericWhite())
                player.triggerAchievement(AchievementHandler.achColorDeath);
        }
    }
    
    public static class FMLEventHandler {
        
        @SubscribeEvent
        public void onRecipeCraft (ItemCraftedEvent event) {
        
            if (event.crafting != null && event.crafting.getItem() instanceof ItemColoredPowder) {
                
                ColorObject color = new ColorObject(event.crafting.getTagCompound());
                
                if (color != null && !color.isGenericWhite())
                    event.player.triggerAchievement(AchievementHandler.achCloneDye);
            }
        }
    }
}