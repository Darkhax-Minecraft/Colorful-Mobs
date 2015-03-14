package net.epoxide.colorfulmobs.handler;

import net.darkhax.bookshelf.helper.ItemHelper;
import net.darkhax.bookshelf.objects.ColorObject;
import net.epoxide.colorfulmobs.ColorfulMobs;
import net.epoxide.colorfulmobs.common.ColorProperties;
import net.epoxide.colorfulmobs.common.PacketColorSync;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EntityHandler {

    @SubscribeEvent
    public void onEntityConstructed(EntityEvent.EntityConstructing event) {

        if (event.entity instanceof EntityLiving) {

            ColorProperties.setPropsToEntity((EntityLivingBase) event.entity);

            if (ConfigurationHandler.spawnRandom && Math.random() < ConfigurationHandler.spawnRate && ColorProperties.isValidMob((EntityLivingBase) event.entity) && !ColorProperties.getPropsFromEntity((EntityLivingBase) event.entity).hasInitialized)
                ColorProperties.setEntityColors(new ColorObject(false), (EntityLivingBase) event.entity);

            ColorProperties.getPropsFromEntity((EntityLivingBase) event.entity).hasInitialized = true;
        }
    }

    @SubscribeEvent
    public void onEntityTracked(PlayerEvent.StartTracking event) {

        if (event.target instanceof EntityLiving && ColorProperties.hasColorProperties((EntityLivingBase) event.target) && !event.target.worldObj.isRemote)
            ColorfulMobs.network.sendToAll(new PacketColorSync(ColorProperties.getPropsFromEntity((EntityLivingBase) event.target).colorObj, (EntityLivingBase) event.target));
    }

    @SubscribeEvent
    public void onMobDeath(LivingDropsEvent event) {

        if (ConfigurationHandler.dropPowder && ColorProperties.isEntityDyed(event.entityLiving)) {

            ItemStack stack = new ItemStack(ColorfulMobs.itemPowder);
            stack.setTagCompound(ColorObject.getTagFromColor(ColorProperties.getPropsFromEntity(event.entityLiving).colorObj));
            ItemHelper.dropStackInWorld(event.entityLiving.worldObj, event.entityLiving.posX, event.entityLiving.posY, event.entityLiving.posZ, stack, false);

            if (event.source.getEntity() instanceof EntityPlayer) {

                EntityPlayer player = (EntityPlayer) event.source.getEntity();
                player.triggerAchievement(AchievementHandler.achKillDyed);
            }
        }
    }

}