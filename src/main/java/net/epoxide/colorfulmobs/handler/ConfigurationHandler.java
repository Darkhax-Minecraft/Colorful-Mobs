package net.epoxide.colorfulmobs.handler;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.epoxide.colorfulmobs.lib.Constants;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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
    public static boolean craftGhostDust = true;
    public static boolean craftRainbowWand = true;
    public static boolean findRainbowDust = true;
    public static int rainbowDustRate = 10;
    public static List<String> validLootLocations = null;
    
    public static final String GENERAL = "general";
    
    public static Configuration config;
    
    public ConfigurationHandler(File file) {
    
        config = new Configuration(file);
        
        FMLCommonHandler.instance().bus().register(this);
        syncConfigData();
    }
    
    @SubscribeEvent
    public void onConfigChange (ConfigChangedEvent.OnConfigChangedEvent event) {
    
        if (event.modID.equals(Constants.MOD_ID))
            syncConfigData();
    }
    
    private void syncConfigData () {
    
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
        prop.comment = "Should blank dye powder be craftable?";
        prop.setLanguageKey("colorfulmobs.configGui.craftDye");
        craftDye = prop.getBoolean(true);
        propOrder.add(prop.getName());
        
        prop = config.get(GENERAL, "Craft Data Checker", true);
        prop.comment = "Should the Data Checker be craftable?";
        prop.setLanguageKey("colorfulmobs.configGui.dataChecker");
        cloneDye = prop.getBoolean(true);
        propOrder.add(prop.getName());
        
        prop = config.get(GENERAL, "Clone Dyes", true);
        prop.comment = "Should players be able to copy their dye colors?";
        prop.setLanguageKey("colorfulmobs.configGui.cloneDye");
        craftBook = prop.getBoolean(true);
        propOrder.add(prop.getName());
        
        prop = config.get(GENERAL, "Craft Ghost Dust", true);
        prop.comment = "Should players be able to craft ghost dust?";
        prop.setLanguageKey("colorfulmobs.configGui.ghostDust");
        craftGhostDust = prop.getBoolean(true);
        propOrder.add(prop.getName());
        
        prop = config.get(GENERAL, "Can Find Rainbow Dust", true);
        prop.comment = "Can rainbow dust be found in loot chests?";
        prop.setLanguageKey("colorfulmobs.configGui.canFindRainbow");
        findRainbowDust = prop.getBoolean();
        propOrder.add(prop.getName());
        
        prop = config.get(GENERAL, "Can Craft Rainbow Wand", true);
        prop.comment = "Should the rainbow wand be craftable?";
        prop.setLanguageKey("colorfulmobs.configGui.craftWand");
        craftRainbowWand = prop.getBoolean();
        propOrder.add(prop.getName());
        
        prop = config.get(GENERAL, "Rainbow Dust Spawn Rate", 10);
        prop.comment = "How often should rainbow dust be found in chests?";
        prop.setLanguageKey("colorfulmobs.configGui.rainbowRate");
        rainbowDustRate = prop.getInt();
        propOrder.add(prop.getName());
        
        prop = config.get(GENERAL, "Valid Chest List", new String[] { "dungeonChest", "pyramidJungleDispenser", "strongholdLibrary", "pyramidDesertyChest", "bonusChest" });
        prop.comment = "A list of valid chest types for chest loot to show up in.";
        prop.setLanguageKey("colorfulmobs.configGui.chestLoot");
        validLootLocations = Arrays.asList(prop.getStringList());
        propOrder.add(prop.getName());
        
        config.setCategoryPropertyOrder(GENERAL, propOrder);
        
        if (config.hasChanged())
            config.save();
    }
}
