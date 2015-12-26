package net.epoxide.colorfulmobs;

import net.minecraft.creativetab.CreativeTabs;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;

import net.epoxide.colorfulmobs.common.CommonProxy;
import net.epoxide.colorfulmobs.creativetab.CreativeTabColorfulMobs;
import net.epoxide.colorfulmobs.lib.Constants;

@Mod(modid = Constants.MOD_ID, name = Constants.MOD_NAME, version = Constants.VERSION_NUMBER, guiFactory = Constants.FACTORY, dependencies = Constants.DEPENDANCIES)
public class ColorfulMobs {
    
    @SidedProxy(clientSide = Constants.CLIENT_PROXY_CLASS, serverSide = Constants.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;
    
    @Mod.Instance(Constants.MOD_ID)
    public static ColorfulMobs instance;
    
    public static SimpleNetworkWrapper network;
    public static CreativeTabs tabColors = new CreativeTabColorfulMobs();
    
    @EventHandler
    public void preInit (FMLPreInitializationEvent pre) {
        
        network = NetworkRegistry.INSTANCE.newSimpleChannel("ColorfulMobs");
    }
}