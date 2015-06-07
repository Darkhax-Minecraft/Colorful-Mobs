//package net.epoxide.colorfulmobs.addons.waila;
//
//import java.util.List;
//
//import mcp.mobius.waila.api.*;
//import net.epoxide.colorfulmobs.common.ColorProperties;
//import net.epoxide.colorfulmobs.items.ItemDataChecker;
//import net.minecraft.entity.Entity;
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.entity.player.EntityPlayerMP;
//import net.minecraft.nbt.NBTTagCompound;
//import net.minecraft.world.World;
//
//public class AddonWaila implements IWailaEntityProvider {
//
//    @Override
//    public Entity getWailaOverride (IWailaEntityAccessor data, IWailaConfigHandler cfg) {
//
//        return data.getEntity();
//    }
//
//    @Override
//    public ITaggedList.ITipList getWailaHead(Entity entity, ITaggedList.ITipList currenttip, IWailaEntityAccessor accessor, IWailaConfigHandler config) {
//        return currenttip;
//    }
//
//    @Override
//    public ITaggedList.ITipList getWailaBody(Entity entity, ITaggedList.ITipList currenttip, IWailaEntityAccessor accessor, IWailaConfigHandler config) {
//        if (config.getConfig(showColor) && entity instanceof EntityLivingBase && ColorProperties.hasColorProperties((EntityLivingBase) entity)) {
//
//            if (config.getConfig(requireChecker) && (accessor.getPlayer().getHeldItem() == null || !(accessor.getPlayer().getHeldItem().getItem() instanceof ItemDataChecker)))
//                return currenttip;
//
//            ColorProperties props = ColorProperties.getPropsFromEntity((EntityLivingBase) entity);
//
//            if (props.getColorObj() != null && props.isDyed())
//                currenttip.add(props.getColorObj().toString());
//        }
//
//        return currenttip;
//    }
//
//    @Override
//    public ITaggedList.ITipList getWailaTail(Entity entity, ITaggedList.ITipList currenttip, IWailaEntityAccessor accessor, IWailaConfigHandler config) {
//        return currenttip;
//    }
//
//    @Override
//    public NBTTagCompound getNBTData(Entity ent, NBTTagCompound tag, IWailaEntityAccessorServer accessor) {
//        if (ent != null)
//            ent.writeToNBT(tag);
//
//        return tag;
//    }
//
//    public static void registerAddon (IWailaRegistrar register) {
//
//        AddonWaila dataProvider = new AddonWaila();
//
//        register.registerBodyProvider(dataProvider, EntityLivingBase.class);
//        register.addConfig("Colorful Mobs", showColor);
//        register.addConfig("Colorful Mobs", requireChecker);
//    }
//
//    private static String showColor = "waila.colorfulmobs.showcolors";
//    private static String requireChecker = "waila.colorfulmobs.requirechecker";
//}
