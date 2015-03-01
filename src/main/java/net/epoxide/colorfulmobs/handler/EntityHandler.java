package net.epoxide.colorfulmobs.handler;

import net.darkhax.bookshelf.helper.ItemHelper;
import net.darkhax.bookshelf.objects.ColorObject;
import net.epoxide.colorfulmobs.ColorfulMobs;
import net.epoxide.colorfulmobs.common.ColorProperties;
import net.epoxide.colorfulmobs.common.PacketColorSync;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EntityHandler {

    @SubscribeEvent
    public void onEntityConstructed(EntityEvent.EntityConstructing event) {

        if (event.entity instanceof EntityLiving && isValidMob(event.entity)) {

            ColorProperties.setPropsToEntity((EntityLivingBase) event.entity);

            if (ConfigurationHandler.spawnRandom && isMobDyed((EntityLivingBase) event.entity) && Math.random() > 0.30) {

                ColorProperties.setEntityColors(new ColorObject(false), (EntityLivingBase) event.entity);
            }
        }

        if (event.entity instanceof EntityLiving && isMobDyed((EntityLivingBase) event.entity))
            ColorProperties.setPropsToEntity((EntityLivingBase) event.entity);

    }

    @SubscribeEvent
    public void onEntityTracked(PlayerEvent.StartTracking event) {

        if (event.target instanceof EntityLiving && isValidMob(event.target) && !event.target.worldObj.isRemote) {

            ColorfulMobs.network.sendToAll(new PacketColorSync(ColorProperties.getPropsFromEntity((EntityLivingBase) event.target).colorObj, (EntityLivingBase) event.target));
        }
    }

    @SubscribeEvent
    public void onMobDeath(LivingDropsEvent event) {

        if (ConfigurationHandler.dropPowder && isMobDyed(event.entityLiving)) {

            ItemStack stack = new ItemStack(ColorfulMobs.itemPowder);
            stack.setTagCompound(ColorObject.getTagFromColor(ColorProperties.getPropsFromEntity(event.entityLiving).colorObj));
            ItemHelper.dropStackInWorld(event.entityLiving.worldObj, event.entityLiving.posX, event.entityLiving.posY, event.entityLiving.posZ, stack, false);
        }
    }

    public boolean isValidMob(Entity entity) {

        if (ConfigurationHandler.limitMobs)

            if (ConfigurationHandler.validMobs.contains(EntityList.getEntityString(entity)))

                return true;

        return true;
    }

    public boolean isMobDyed(EntityLivingBase entity) {

        return ColorProperties.hasColorProperties(entity) && !ColorObject.isGeneric(ColorProperties.getPropsFromEntity(entity).colorObj);
    }
}