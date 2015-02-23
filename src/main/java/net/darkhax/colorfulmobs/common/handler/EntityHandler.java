package net.darkhax.colorfulmobs.common.handler;

import net.darkhax.bookshelf.helper.ItemHelper;
import net.darkhax.bookshelf.objects.ColorObject;
import net.darkhax.colorfulmobs.ColorfulMobs;
import net.darkhax.colorfulmobs.common.ColorProperties;
import net.darkhax.colorfulmobs.common.ConfigurationHandler;
import net.darkhax.colorfulmobs.common.PacketColorSync;
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

        if (event.entity instanceof EntityLiving && (!(event.entity instanceof EntityPlayer) || ConfigurationHandler.dyePlayer)) {

            ColorProperties.setPropsToEntity((EntityLivingBase) event.entity);

            if (ConfigurationHandler.spawnRandom && Math.random() > 0.30) {

                ColorProperties.setEntityColors(new ColorObject(false), (EntityLivingBase) event.entity);
            }
        }
    }

    @SubscribeEvent
    public void onEntityTracked(PlayerEvent.StartTracking event) {

        if (event.target instanceof EntityLiving && (!(event.target instanceof EntityPlayer) || ConfigurationHandler.dyePlayer) && !event.target.worldObj.isRemote) {

            ColorfulMobs.network.sendToAll(new PacketColorSync(ColorProperties.getPropsFromEntity((EntityLivingBase) event.target).colorObj, (EntityLivingBase) event.target));
        }
    }

    @SubscribeEvent
    public void onMobDeath(LivingDropsEvent event) {

        if (ColorProperties.hasColorProperties(event.entityLiving) && !ColorObject.isGeneric(ColorProperties.getPropsFromEntity(event.entityLiving).colorObj)) {

            ItemStack stack = new ItemStack(ColorfulMobs.itemPowder);
            stack.setTagCompound(ColorObject.getTagFromColor(ColorProperties.getPropsFromEntity(event.entityLiving).colorObj));
            ItemHelper.dropStackInWorld(event.entityLiving.worldObj, event.entityLiving.posX, event.entityLiving.posY, event.entityLiving.posZ, stack, false);
        }
    }
}