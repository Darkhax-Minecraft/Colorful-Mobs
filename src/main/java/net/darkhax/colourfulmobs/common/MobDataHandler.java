package net.darkhax.colourfulmobs.common;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.darkhax.bookshelf.helper.NumericHelper;
import net.darkhax.colourfulmobs.ColorfulMobs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class MobDataHandler {

    @SubscribeEvent
    public void onStartTrackingMob(EntityJoinWorldEvent event) {

        System.out.println("Hello");
        if (event.entity instanceof EntityLiving && !(event.entity instanceof EntityPlayer) && ColorProperties.hasColorProperties((EntityLiving) event.entity)) {

            ColorfulMobs.instance.network.sendToAll(new PacketColorSync(ColorProperties.getPropsFromEntity((EntityLivingBase) event.entity).colorObj, (EntityLivingBase) event.entity));
        }
    }
}