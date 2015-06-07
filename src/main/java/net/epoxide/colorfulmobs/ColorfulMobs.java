package net.epoxide.colorfulmobs;

import java.util.Arrays;

import net.epoxide.colorfulmobs.addons.AddonManager;
import net.epoxide.colorfulmobs.common.CommonProxy;
import net.epoxide.colorfulmobs.common.PacketColorSync;
import net.epoxide.colorfulmobs.handler.AchievementHandler;
import net.epoxide.colorfulmobs.handler.ConfigurationHandler;
import net.epoxide.colorfulmobs.handler.ForgeEventHandler;
import net.epoxide.colorfulmobs.handler.GuiHandler;
import net.epoxide.colorfulmobs.handler.LootHandler;
import net.epoxide.colorfulmobs.items.ItemColorWand;
import net.epoxide.colorfulmobs.items.ItemColoredPowder;
import net.epoxide.colorfulmobs.items.ItemDataChecker;
import net.epoxide.colorfulmobs.items.ItemGhostDust;
import net.epoxide.colorfulmobs.items.ItemRainbowDust;
import net.epoxide.colorfulmobs.lib.Constants;
import net.epoxide.colorfulmobs.recipe.RecipeManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

import static net.minecraftforge.fml.common.Mod.*;

@Mod(modid = Constants.MOD_ID, name = Constants.MOD_NAME, version = Constants.VERSION_NUMBER, guiFactory = Constants.FACTORY, dependencies = Constants.DEPENDANCIES)
public class ColorfulMobs {

    public static SimpleNetworkWrapper network;

    @SidedProxy(clientSide = Constants.CLIENT_PROXY_CLASS, serverSide = Constants.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;

    @Mod.Instance(Constants.MOD_ID)
    public static ColorfulMobs instance;

    public static CreativeTabs tabColor = new CreativeTabColor();
    public static Item itemColorWand = new ItemColorWand();
    public static Item itemDataChecker = new ItemDataChecker();
    public static Item itemRainbowDust = new ItemRainbowDust();
    public static Item itemGhostDust = new ItemGhostDust();
    public static Item itemPowder = new ItemColoredPowder();

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        network = NetworkRegistry.INSTANCE.newSimpleChannel("ColorfulMobs");
        network.registerMessage(PacketColorSync.PacketColorSyncHandler.class, PacketColorSync.class, 0, Side.CLIENT);
        network.registerMessage(PacketColorSync.PacketColorSyncHandler.class, PacketColorSync.class, 0, Side.SERVER);

        setModInfo(event.getModMetadata());
        proxy.registerSidedEvents();

        new ConfigurationHandler(event.getSuggestedConfigurationFile());

        GameRegistry.registerItem(itemColorWand, "colorWand");
        GameRegistry.registerItem(itemDataChecker, "dataChecker");
        GameRegistry.registerItem(itemRainbowDust, "rainbowDust");
        GameRegistry.registerItem(itemGhostDust, "ghostDust");
        GameRegistry.registerItem(itemPowder, "colorPowder");

        MinecraftForge.EVENT_BUS.register(new ForgeEventHandler());
        FMLCommonHandler.instance().bus().register(new ForgeEventHandler.FMLEventHandler());
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());

        new LootHandler();
        new RecipeManager();
        new AchievementHandler();
        AddonManager.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {

        AddonManager.init(event);

        if (event.getSide() == Side.CLIENT) {
            RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();

            renderItem.getItemModelMesher().register(itemColorWand, 0, new ModelResourceLocation(Constants.MOD_ID + ":colorWand", "inventory"));
            renderItem.getItemModelMesher().register(itemDataChecker, 0, new ModelResourceLocation(Constants.MOD_ID + ":dataChecker", "inventory"));
            renderItem.getItemModelMesher().register(itemRainbowDust, 0, new ModelResourceLocation(Constants.MOD_ID + ":rainbowDust", "inventory"));
            renderItem.getItemModelMesher().register(itemGhostDust, 0, new ModelResourceLocation(Constants.MOD_ID + ":ghostDust", "inventory"));
            renderItem.getItemModelMesher().register(itemPowder, 0, new ModelResourceLocation(Constants.MOD_ID + ":colorPowder", "inventory"));

        }
    }

    /**
     * Sets the mod meta for a mod, this is the information displayed when looking at this
     * modification in the in-game mod list provided by MinecraftForge.
     *
     * @param meta : The ModMetadata object for this mod. This can be retrieved from the
     *             FMLPreInitializationEvent.
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