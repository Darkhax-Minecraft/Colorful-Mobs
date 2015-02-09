package net.darkhax.colourfulmobs.common;

import java.util.HashMap;

import net.darkhax.bookshelf.helper.NumericHelper;
import net.darkhax.colourfulmobs.ColorfulMobs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class ColorProperties implements IExtendedEntityProperties {

    public static final String PROP_NAME = "ColorProperties";

    public EntityLivingBase entity;
    public ColorObject colorObj;

    public ColorProperties(EntityLivingBase living) {

        entity = living;
        colorObj = new ColorObject(1.0f, 1.0f, 1.0f, 1.0f);
    }

    @Override
    public void saveNBTData(NBTTagCompound compound) {

        compound.setTag(PROP_NAME, ColorObject.getTagFromColor(this.colorObj));
    }

    @Override
    public void loadNBTData(NBTTagCompound compound) {

        this.colorObj = ColorObject.getColorFromTag(compound.getCompoundTag(PROP_NAME));
        ColorfulMobs.instance.network.sendToAll(new PacketColorSync(colorObj, entity));
    }

    @Override
    public void init(Entity entity, World world) {

    }

    public static ColorProperties getPropsFromEntity(EntityLivingBase living) {

        return (ColorProperties) living.getExtendedProperties(PROP_NAME);
    }

    public static void setPropsToEntity(EntityLivingBase living) {

        living.registerExtendedProperties(PROP_NAME, new ColorProperties(living));
    }

    public static boolean hasColorProperties(EntityLivingBase living) {

        return getPropsFromEntity(living) != null;
    }

    public static void setEntityColors(ColorObject color, EntityLivingBase living) {

        if (!hasColorProperties(living))
            setPropsToEntity(living);

        ColorProperties props = getPropsFromEntity(living);
        props.colorObj = color;
    }
}