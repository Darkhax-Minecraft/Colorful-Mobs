package net.epoxide.colorfulmobs;

import net.epoxide.colorfulmobs.common.ColorProperties;
import net.epoxide.colorfulmobs.common.CommonProxy;
import net.epoxide.colorfulmobs.common.network.GuiHandler;
import net.epoxide.colorfulmobs.common.network.PacketSyncColor;
import net.epoxide.colorfulmobs.creativetab.CreativeTabColorfulMobs;
import net.epoxide.colorfulmobs.handler.ContentHandler;
import net.epoxide.colorfulmobs.lib.Constants;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = Constants.MOD_ID, name = Constants.MOD_NAME, version = Constants.VERSION_NUMBER, dependencies = Constants.DEPENDENCIES)
public class ColorfulMobs {
    
    @SidedProxy(clientSide = Constants.CLIENT_PROXY_CLASS, serverSide = Constants.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;
    
    @Instance(Constants.MOD_ID)
    public static ColorfulMobs instance;
    
    public static SimpleNetworkWrapper network;
    public static CreativeTabs tabColors = new CreativeTabColorfulMobs();
    
    @EventHandler
    public void preInit (FMLPreInitializationEvent pre) {
        
        network = NetworkRegistry.INSTANCE.newSimpleChannel("ColorfulMobs");
        network.registerMessage(PacketSyncColor.PacketColorSyncHandler.class, PacketSyncColor.class, 0, Side.CLIENT);
        network.registerMessage(PacketSyncColor.PacketColorSyncHandler.class, PacketSyncColor.class, 0, Side.SERVER);
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
        
        proxy.registerSidedEvents();
        ColorProperties.init();
        
        ContentHandler.initItems();
        ContentHandler.initRecipes();
        ContentHandler.initMisc();
    }
}