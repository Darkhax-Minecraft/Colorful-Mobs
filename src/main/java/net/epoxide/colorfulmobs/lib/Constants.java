package net.epoxide.colorfulmobs.lib;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Constants {
    
    public static final String MOD_ID = "colorfulmobs";
    public static final String MOD_NAME = "Colorful Mobs";
    public static final String VERSION_NUMBER = "2.0.0.0";
    public static final String CLIENT_PROXY_CLASS = "net.epoxide.colorfulmobs.client.ClientProxy";
    public static final String SERVER_PROXY_CLASS = "net.epoxide.colorfulmobs.common.CommonProxy";
    public static final String DEPENDENCIES = "required-after:bookshelf@[1.4.1.320,)";
    public static final Random RANDOM = new Random();
    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);
}