package net.epoxide.colorfulmobs.handler;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.epoxide.colorfulmobs.lib.Constants;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ConfigurationHandler {

    public static boolean spawnRandom = true;
    public static double spawnRate = 0.04d;
    public static boolean dropPowder = true;
    public static boolean limitMobs = false;
    public static List<String> validMobs = null;
    public static boolean dyePlayer = false;
    public static boolean craftDye = true;
    public static boolean cloneDye = true;
    public static boolean craftBook = true;
    
    public static final String GENERAL = "general";

    public static Configuration config;

    public ConfigurationHandler(File file) {

        config = new Configuration(file);

        FMLCommonHandler.instance().bus().register(this);
        syncConfigData();
    }

    @SubscribeEvent
    public void onConfigChange(ConfigChangedEvent.OnConfigChangedEvent event) {

        if (event.modID.equals(Constants.MOD_ID))
            syncConfigData();
    }

    private void syncConfigData() {

        List<String> propOrder = new ArrayList<String>();
        Property prop;

        prop = config.get(GENERAL, "Spawned Colored Mobs", true);
        prop.comment = "Should mobs randomly be colored when they spawn?";
        prop.setLanguageKey("colorfulmobs.configGui.spawnRandom");
        spawnRandom = prop.getBoolean(true);
        propOrder.add(prop.getName());

        prop = config.get(GENERAL, "Dyed Spawn Chance", 0.04);
        prop.comment = "What percent chance should mobs spawn randomly colored?";
        prop.setLanguageKey("colorfulmobs.configGui.spawnRate");
        spawnRate = prop.getDouble();
        propOrder.add(prop.getName());

        prop = config.get(GENERAL, "Drop Dye Powder", true);
        prop.comment = "Should mobs drop dye powder on death?";
        prop.setLanguageKey("colorfulmobs.configGui.dropPowder");
        dropPowder = prop.getBoolean(true);
        propOrder.add(prop.getName());

        prop = config.get(GENERAL, "Dyeable Players", false);
        prop.comment = "Should players be able to dye each other?";
        prop.setLanguageKey("colorfulmobs.configGui.playerDye");
        dyePlayer = prop.getBoolean(false);
        propOrder.add(prop.getName());

        prop = config.get(GENERAL, "Limit To Valid Mobs", false);
        prop.comment = "Should only mobs on the valid list be able to spawn with random colors?";
        prop.setLanguageKey("colorfulmobs.configGui.limitMobs");
        limitMobs = prop.getBoolean(false);
        propOrder.add(prop.getName());
        
        prop = config.get(GENERAL, "Craft Blank Dye", true);
        prop.comment ="Should blank dye powder be craftable?";
        prop.setLanguageKey("colorfulmobs.configGui.craftDye");
        limitMobs = prop.getBoolean(true);
        propOrder.add(prop.getName());
        
        prop = config.get(GENERAL, "Craft Data Checker", true);
        prop.comment = "Should the Data Checker be craftable?";
        prop.setLanguageKey("colorfulmobs.configGui.dataChecker");
        limitMobs = prop.getBoolean(true);
        propOrder.add(prop.getName());
        
        prop = config.get(GENERAL, "Clone Dyes", true);
        prop.comment = "Should players be able to copy their dye colors?";
        prop.setLanguageKey("colorfulmobs.configGui.cloneDye");
        limitMobs = prop.getBoolean(true);
        propOrder.add(prop.getName());
        
        prop = config.get(GENERAL, "Valid Mob List", new String[] {});
        prop.comment = "If the Limit To Valid Mobs is set to true, only mobs on this list can spawn with random colors. Use the Data Checker to get valid entity names. ";
        prop.setLanguageKey("colorfulmobs.configGui.validMobs");
        validMobs = Arrays.asList(prop.getStringList());
        propOrder.add(prop.getName());

        config.setCategoryPropertyOrder(GENERAL, propOrder);

        if (config.hasChanged())
            config.save();
    }
}
