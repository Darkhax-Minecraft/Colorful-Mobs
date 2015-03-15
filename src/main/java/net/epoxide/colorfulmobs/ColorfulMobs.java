package net.epoxide.colorfulmobs;

import java.util.Arrays;

import net.epoxide.colorfulmobs.common.CommonProxy;
import net.epoxide.colorfulmobs.common.PacketColorSync;
import net.epoxide.colorfulmobs.handler.AchievementHandler;
import net.epoxide.colorfulmobs.handler.ConfigurationHandler;
import net.epoxide.colorfulmobs.handler.ForgeEventHandler;
import net.epoxide.colorfulmobs.handler.GuiHandler;
import net.epoxide.colorfulmobs.items.ItemColorWand;
import net.epoxide.colorfulmobs.items.ItemColoredPowder;
import net.epoxide.colorfulmobs.items.ItemDataChecker;
import net.epoxide.colorfulmobs.items.ItemGhostDust;
import net.epoxide.colorfulmobs.items.ItemRainbowDust;
import net.epoxide.colorfulmobs.lib.ColorObject;
import net.epoxide.colorfulmobs.lib.Constants;
import net.epoxide.colorfulmobs.recipe.RecipeDyePowder;
import net.epoxide.colorfulmobs.recipe.RecipeManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = Constants.MOD_ID, name = Constants.MOD_NAME, version = Constants.VERSION_NUMBER, guiFactory = Constants.FACTORY)
public class ColorfulMobs {

    public static SimpleNetworkWrapper network;

    @SidedProxy(clientSide = Constants.CLIENT_PROXY_CLASS, serverSide = Constants.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;

    @Mod.Instance(Constants.MOD_ID)
    public static ColorfulMobs instance;

    public static CreativeTabs tabColor = new CreativeTabColor();
    public static Item itemColorWand = new ItemColorWand();
    public static Item itemRainbowDust = new ItemRainbowDust();
    public static Item itemGhostDust = new ItemGhostDust();
    public static Item itemPowder = new ItemColoredPowder();
    public static Item itemDataChecker = new ItemDataChecker();

    @EventHandler
    public void preInit(FMLPreInitializationEvent pre) {

        network = NetworkRegistry.INSTANCE.newSimpleChannel("ColorfulMobs");
        network.registerMessage(PacketColorSync.PacketColorSyncHandler.class, PacketColorSync.class, 0, Side.CLIENT);
        network.registerMessage(PacketColorSync.PacketColorSyncHandler.class, PacketColorSync.class, 0, Side.SERVER);

        setModInfo(pre.getModMetadata());
        proxy.registerSidedEvents();

        new ConfigurationHandler(pre.getSuggestedConfigurationFile());

        GameRegistry.registerItem(itemColorWand, "colorWand");
        GameRegistry.registerItem(itemRainbowDust, "rainbowDust");
        GameRegistry.registerItem(itemGhostDust, "ghostDust");
        GameRegistry.registerItem(itemPowder, "colorPowder");
        GameRegistry.registerItem(itemDataChecker, "dataChecker");

        MinecraftForge.EVENT_BUS.register(new ForgeEventHandler());
        FMLCommonHandler.instance().bus().register(new ForgeEventHandler.FMLEventHandler());
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());

        new RecipeManager();
        new AchievementHandler();
    }

    /**
     * Sets the mod meta for a mod, this is the information displayed when looking at this modification
     * in the in-game mod list provided by MinecraftForge.
     * 
     * @param meta: The ModMetadata object for this mod. This can be retrieved from the
     *        FMLPreInitializationEvent.
     */
    void setModInfo(ModMetadata meta) {

        meta.authorList = Arrays.asList("Darkhax", "lclc98");
        meta.logoFile = "/assets/colorfulmobs_logo.png";
        meta.credits = "Maintained by Darkhax";
        meta.description = "";
        meta.url = "http://darkhax.net";
        meta.autogenerated = false;
    }
}