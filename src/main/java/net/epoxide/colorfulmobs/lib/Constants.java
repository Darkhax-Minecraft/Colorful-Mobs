package net.epoxide.colorfulmobs.lib;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Constants {
    
    public static final String MOD_ID = "ColorfulMobs";
    public static final String MOD_NAME = "Colorful Mobs";
    public static final String VERSION_NUMBER = "1.1.0";
    public static final String CLIENT_PROXY_CLASS = "net.epoxide.colorfulmobs.client.ClientProxy";
    public static final String SERVER_PROXY_CLASS = "net.epoxide.colorfulmobs.common.CommonProxy";
    public static final String FACTORY = "net.epoxide.colorfulmobs.client.gui.GuiFactoryColorfulMobs";
    public static final String DEPENDANCIES = "after:Waila;after:lucky;after:Thaumcraft";
    public static final Random RANDOM = new Random();
    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);
}