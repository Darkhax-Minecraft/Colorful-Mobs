package net.epoxide.colorfulmobs.common;

import net.darkhax.bookshelf.lib.util.MathsUtils;
import net.epoxide.colorfulmobs.ColorfulMobs;
import net.epoxide.colorfulmobs.common.network.PacketSyncColor;
import net.epoxide.colorfulmobs.handler.ConfigurationHandler;
import net.epoxide.colorfulmobs.handler.ContentHandler;
import net.epoxide.colorfulmobs.lib.ColorObject;
import net.epoxide.colorfulmobs.lib.Constants;
import net.epoxide.colorfulmobs.lib.Utilities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ColorProperties {
    
    @CapabilityInject(IColorHolder.class)
    public static final Capability<IColorHolder> CUSTOM_DATA = null;
    
    public static void init () {
        
        CapabilityManager.INSTANCE.register(IColorHolder.class, new Storage(), Default.class);
        MinecraftForge.EVENT_BUS.register(new ColorProperties());
    }
    
    public static IColorHolder getProperties (EntityLivingBase entity) {
        
        if (entity.hasCapability(CUSTOM_DATA, EnumFacing.UP))
            return entity.getCapability(CUSTOM_DATA, EnumFacing.UP);
        
        return null;
    }
    
    public static boolean hasProperties (EntityLivingBase entity) {
        
        return entity.hasCapability(CUSTOM_DATA, EnumFacing.UP);
    }
    
    @SubscribeEvent
    public void attachCapabilities (AttachCapabilitiesEvent<Entity> event) {
        
        if (event.getObject() instanceof EntityLivingBase)
            event.addCapability(new ResourceLocation(Constants.MOD_ID, "Colors"), new Provider());
    }
    
    @SubscribeEvent
    public void onEntityJoinWorld (EntityJoinWorldEvent event) {
        
        if (event.getEntity() instanceof EntityLiving && ColorProperties.hasProperties((EntityLivingBase) event.getEntity())) {
            
            // TODO add a check to the mob
            IColorHolder props = ColorProperties.getProperties((EntityLiving) event.getEntity());
            if (ConfigurationHandler.spawnRandom && MathsUtils.tryPercentage(ConfigurationHandler.spawnRate) && props.isValidTarget(event.getEntity()))
                props.setColor(new ColorObject(false));
        }
    }
    
    @SubscribeEvent
    public void onEntityTracked (PlayerEvent.StartTracking event) {
        
        Entity target = event.getTarget();
        if (target instanceof EntityLivingBase && ColorProperties.hasProperties((EntityLivingBase) target) && !target.worldObj.isRemote && event.getEntityPlayer() instanceof EntityPlayerMP) {
            
            IColorHolder props = ColorProperties.getProperties((EntityLivingBase) target);
            
            if (props != null && (props.isDyed() || props.isRadiant()))
                ColorfulMobs.network.sendTo(new PacketSyncColor(props.getColor(), (EntityLivingBase) target, props.isRadiant()), (EntityPlayerMP) event.getEntityPlayer());
        }
    }
    
    @SubscribeEvent
    public void onMobDeath (LivingDropsEvent event) {
        
        EntityLivingBase entity = event.getEntityLiving();
        if (ConfigurationHandler.dropPowder && ColorProperties.hasProperties(entity)) {
            
            IColorHolder props = ColorProperties.getProperties(entity);
            
            if (props.isDyed() && !props.getColor().isWhite()) {
                
                ItemStack stack = new ItemStack(ContentHandler.itemRGBDust);
                props.getColor().write(stack);
                Utilities.dropStackInWorld(entity.worldObj, entity.posX, entity.posY, entity.posZ, stack, false);
            }
        }
    }
    
    public static interface IColorHolder {
        
        ColorObject getColor ();
        
        void setColor (ColorObject color);
        
        void sync ();
        
        boolean isDyed ();
        
        void setRadiant (boolean radiant);
        
        boolean isRadiant ();
        
        boolean isValidTarget (Entity entity);
    }
    
    public static class Default implements IColorHolder {
        
        private ColorObject color = new ColorObject(1f, 1f, 1f);
        private boolean radiant;
        
        @Override
        public ColorObject getColor () {
            
            return this.color;
        }
        
        @Override
        public void setColor (ColorObject color) {
            
            this.color = color;
        }
        
        @Override
        public boolean isDyed () {
            
            return color != null || !color.isWhite();
        }
        
        @Override
        public void sync () {
            // TODO Auto-generated method stub
            
        }
        
        @Override
        public void setRadiant (boolean radiant) {
            
            this.radiant = radiant;
        }
        
        @Override
        public boolean isRadiant () {
            
            return this.radiant;
        }
        
        @Override
        public boolean isValidTarget (Entity entity) {
            
            return !(entity instanceof EntityPlayer || (ConfigurationHandler.limitMobs && !Utilities.arrayContains(ConfigurationHandler.validMobs, EntityList.getEntityString(entity))));
        }
    }
    
    /**
     * Handles reand/write of custom data.
     */
    public static class Storage implements Capability.IStorage<IColorHolder> {
        
        @Override
        public NBTBase writeNBT (Capability<IColorHolder> capability, IColorHolder instance, EnumFacing side) {
            
            final NBTTagCompound tag = new NBTTagCompound();
            
            instance.getColor().write(tag);
            
            return tag;
        }
        
        @Override
        public void readNBT (Capability<IColorHolder> capability, IColorHolder instance, EnumFacing side, NBTBase nbt) {
            
            final NBTTagCompound tag = (NBTTagCompound) nbt;
            
            instance.setColor(new ColorObject(tag));
        }
    }
    
    /**
     * Handles all the checks and delegate methods for the capability.
     */
    public static class Provider implements ICapabilitySerializable<NBTTagCompound> {
        
        IColorHolder instance = CUSTOM_DATA.getDefaultInstance();
        
        @Override
        public boolean hasCapability (Capability<?> capability, EnumFacing facing) {
            
            return capability == CUSTOM_DATA;
        }
        
        @Override
        public <T> T getCapability (Capability<T> capability, EnumFacing facing) {
            
            return hasCapability(capability, facing) ? CUSTOM_DATA.<T> cast(instance) : null;
        }
        
        @Override
        public NBTTagCompound serializeNBT () {
            
            return (NBTTagCompound) CUSTOM_DATA.getStorage().writeNBT(CUSTOM_DATA, instance, null);
        }
        
        @Override
        public void deserializeNBT (NBTTagCompound nbt) {
            
            CUSTOM_DATA.getStorage().readNBT(CUSTOM_DATA, instance, null, nbt);
        }
    }
}
