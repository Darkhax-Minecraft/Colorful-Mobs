package net.epoxide.colorfulmobs.common;

import net.epoxide.colorfulmobs.ColorfulMobs;
import net.epoxide.colorfulmobs.handler.ConfigurationHandler;
import net.epoxide.colorfulmobs.lib.ColorObject;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class ColorProperties implements IExtendedEntityProperties {
    
    public static final String PROP_NAME = "ColorProperties";
    
    public EntityLivingBase entity;
    public ColorObject colorObj;
    public boolean hasInitialized;
    
    public ColorProperties(EntityLivingBase living) {
    
        entity = living;
        colorObj = new ColorObject(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @Override
    public void saveNBTData (NBTTagCompound compound) {
    
        compound.setTag(PROP_NAME, ColorObject.getTagFromColor(this.colorObj));
        compound.setBoolean(PROP_NAME + "Init", this.hasInitialized);
    }
    
    @Override
    public void loadNBTData (NBTTagCompound compound) {
    
        this.colorObj = ColorObject.getColorFromTag(compound.getCompoundTag(PROP_NAME));
        this.hasInitialized = compound.getBoolean(PROP_NAME + "Init");
        ColorfulMobs.instance.network.sendToAll(new PacketColorSync(colorObj, entity));
    }
    
    @Override
    public void init (Entity entity, World world) {
    
    }
    
    /**
     * Retrieves a ColorProperties object from a provided entity.
     * 
     * @param living : A living entity which extends EntityLivingBase, this is where the data
     *            comes from.
     * @return ColorProperties: A ColorProperties object unique to the specified living entity.
     */
    public static ColorProperties getPropsFromEntity (EntityLivingBase living) {
    
        return (ColorProperties) living.getExtendedProperties(PROP_NAME);
    }
    
    /**
     * Sets a new ColorProperties object to a living entity, this will override any existing
     * color data, and is mandatory to apply before setting colors to mobs.
     * 
     * @param living
     */
    public static void setPropsToEntity (EntityLivingBase living) {
    
        living.registerExtendedProperties(PROP_NAME, new ColorProperties(living));
    }
    
    /**
     * Checks to see if a living entity has color properties.
     * 
     * @param living : A living entity to check for colored properties.
     * @return boolean: True if the mob has a ColorProperties object.
     */
    public static boolean hasColorProperties (EntityLivingBase living) {
    
        return getPropsFromEntity(living) != null;
    }
    
    /**
     * Sets the color for an entity. Has built in check to ensure the mob actually has a
     * ColorProperties object to write to.
     * 
     * @param color : A ColorObject containing the color being set. This will override existing
     *            colors.
     * @param living : A living entity to have color data applied to.
     */
    public static void setEntityColors (ColorObject color, EntityLivingBase living) {
    
        if (!hasColorProperties(living))
            setPropsToEntity(living);
        
        ColorProperties props = getPropsFromEntity(living);
        props.colorObj = color;
    }
    
    /**
     * Checks to see if a mob is a valid target for working with color.
     * 
     * @param living : The entity being checked.
     * @return boolean: True if the mob is a player, false if the spawn limits are on but the
     *         mob is not in the allowed list, and true if no limits are placed.
     */
    public static boolean isValidMob (EntityLivingBase living) {
    
        if (living instanceof EntityPlayer && ConfigurationHandler.dyePlayer)
            return true;
        
        if (ConfigurationHandler.limitMobs && !ConfigurationHandler.validMobs.contains(EntityList.getEntityString(living)))
            return false;
        
        return true;
    }
    
    /**
     * Checks to see if an entity is dyed, this is an advanced version of hasColorProperties,
     * that excludes white as a valid dye color.
     * 
     * @param living : The entity being checked for color.
     * @return boolean: True if the mob has color data that is not white, false if it doesn't.
     */
    public static boolean isEntityDyed (EntityLivingBase living) {
    
        return (hasColorProperties(living) && !ColorObject.isGeneric(getPropsFromEntity(living).colorObj)) ? true : false;
    }
    
    /**
     * Simple method to make grabbing colors look nicer.
     * 
     * @param living : The entity to grab color from.
     * @return ColorObject: The color of that entity.
     */
    public static ColorObject getColorFromEntity (EntityLivingBase living) {
    
        return ColorProperties.getPropsFromEntity(living).colorObj;
    }
}