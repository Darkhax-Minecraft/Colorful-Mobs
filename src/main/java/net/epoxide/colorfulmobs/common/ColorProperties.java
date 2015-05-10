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
    
    private EntityLivingBase entity;
    private ColorObject colorObj;
    private boolean hasInitialized;
    
    public ColorProperties(EntityLivingBase living) {
    
        entity = living;
        colorObj = new ColorObject();
        hasInitialized = true;
    }
    
    @Override
    public void saveNBTData (NBTTagCompound compound) {
    
        compound.setTag(PROP_NAME, this.colorObj.getTagFromColor());
        compound.setBoolean(PROP_NAME + "Init", this.hasInitialized);
    }
    
    @Override
    public void loadNBTData (NBTTagCompound compound) {
    
        this.colorObj = new ColorObject(compound.getCompoundTag(PROP_NAME));
        this.hasInitialized = compound.getBoolean(PROP_NAME + "Init");
        ColorfulMobs.instance.network.sendToAll(new PacketColorSync(colorObj, entity));
    }
    
    @Override
    public void init (Entity entity, World world) {
    
    }
    
    /**
     * Creates an instance of ColorProperties from an instance of an entity.
     * 
     * @param living: The instance of the entity being read.
     * @return ColorProperties: An instance of ColorProperties, which holds all of the color
     *         data.
     */
    public static ColorProperties getPropsFromEntity (EntityLivingBase living) {
    
        return (ColorProperties) living.getExtendedProperties(PROP_NAME);
    }
    
    /**
     * Registers the properties in the entities data. This allows custom data to be read and
     * written.
     * 
     * @param living: An instance of the entity being set up.
     * @return ColorProperties: The newly created instance of ColorProperties.
     */
    public static ColorProperties setPropsToEntity (EntityLivingBase living) {
    
        living.registerExtendedProperties(PROP_NAME, new ColorProperties(living));
        return getPropsFromEntity(living);
    }
    
    /**
     * A basic check to see if an entity already has the data available.
     * 
     * @param living: An instance of the entity being checked.
     * @return boolean: True if the entity's property value is not null.
     */
    public static boolean hasColorProperties (EntityLivingBase living) {
    
        return getPropsFromEntity(living) != null;
    }
    
    /**
     * Retrieves a usable ColorObject instance from the properties instance.
     * 
     * @return ColorObject: An object containing all of the color data. If the one from the
     *         properties is not usable, a default one is provided.
     */
    public ColorObject getColorObj () {
    
        return (this.colorObj == null) ? new ColorObject() : this.colorObj;
    }
    
    /**
     * Sets a ColorObject to the ColorProperties for an entity.
     * 
     * @param newColor: The new color to set. If this value is null, a new ColorObject will be
     *            created.
     */
    public void setColorObject (ColorObject newColor) {
    
        this.colorObj = (newColor == null) ? new ColorObject() : newColor;
    }
    
    /**
     * Checks if the color for this entity has any abnormalities.
     * 
     * @return boolean: True if any of the color values are less than one.
     */
    public boolean isDyed () {
    
        return (this.colorObj.getRed() < 1f || this.colorObj.getGreen() < 1f || this.colorObj.getBlue() < 1f || this.colorObj.getAlpha() < 1f);
    }
    
    /**
     * Checks if the mob has been initialized. This is used to differentiate between
     * pre-existing mobs, and new ones.
     * 
     * @return boolean: True, if the mob has already been spawned into the world.
     */
    public boolean isInitialized () {
    
        return this.hasInitialized;
    }
    
    /**
     * Checks to see if the mob is a valid target for being colored. If the target mob is a
     * player, it will only be valid if the config option for dying players is set to true. If
     * the entity is not a player, it will be valid, unless it is on the prohibited mobs config
     * list.
     * 
     * @return boolean: True, but only if the mob is a valid target to be dyed.
     */
    public boolean isValidTarget () {
    
        if (this.entity instanceof EntityPlayer && ConfigurationHandler.dyePlayer)
            return true;
        
        if (ConfigurationHandler.limitMobs && !ConfigurationHandler.validMobs.contains(EntityList.getEntityString(entity)))
            return false;
        
        return true;
    }
}