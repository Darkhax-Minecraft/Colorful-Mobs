package net.epoxide.colorfulmobs.addons.waila;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaEntityAccessor;
import mcp.mobius.waila.api.IWailaEntityProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.epoxide.colorfulmobs.common.ColorProperties;
import net.epoxide.colorfulmobs.items.ItemDataChecker;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class AddonWaila implements IWailaEntityProvider {
    
    @Override
    public Entity getWailaOverride (IWailaEntityAccessor data, IWailaConfigHandler cfg) {
    
        return data.getEntity();
    }
    
    @Override
    public List<String> getWailaHead (Entity entity, List<String> tip, IWailaEntityAccessor data, IWailaConfigHandler cfg) {
    
        return tip;
    }
    
    @Override
    public List<String> getWailaBody (Entity entity, List<String> tip, IWailaEntityAccessor data, IWailaConfigHandler cfg) {
    
        if (cfg.getConfig(showColor) && entity instanceof EntityLivingBase && ColorProperties.hasColorProperties((EntityLivingBase) entity)) {
            
            if (cfg.getConfig(requireChecker) && (data.getPlayer().getHeldItem() == null || !(data.getPlayer().getHeldItem().getItem() instanceof ItemDataChecker)))
                return tip;
            
            ColorProperties props = ColorProperties.getPropsFromEntity((EntityLivingBase) entity);
            
            if (props.getColorObj() != null && props.isDyed())
                tip.add(props.getColorObj().toString());
        }
        
        return tip;
    }
    
    @Override
    public List<String> getWailaTail (Entity entity, List<String> tip, IWailaEntityAccessor data, IWailaConfigHandler cfg) {
    
        return tip;
    }
    
    @Override
    public NBTTagCompound getNBTData (EntityPlayerMP player, Entity entity, NBTTagCompound tag, World world) {
    
        if (entity != null)
            entity.writeToNBT(tag);
        
        return tag;
    }
    
    public static void registerAddon (IWailaRegistrar register) {
    
        AddonWaila dataProvider = new AddonWaila();
        
        register.registerBodyProvider(dataProvider, EntityLivingBase.class);
        register.addConfig("Colorful Mobs", showColor);
        register.addConfig("Colorful Mobs", requireChecker);
    }
    
    private static String showColor = "waila.colorfulmobs.showcolors";
    private static String requireChecker = "waila.colorfulmobs.requirechecker";
}
