package net.epoxide.colorfulmobs.handler;

import java.io.File;
import java.util.*;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

import net.epoxide.colorfulmobs.lib.Constants;

public class ConfigurationHandler {
    
    public static boolean spawnRandom = true;
    public static float spawnRate = 0.04f;
    public static boolean dropPowder = true;
    public static boolean limitMobs = false;
    public static String[] validMobs = new String[] {};
    public static boolean craftDye = true;
    public static boolean cloneDye = true;
    public static boolean craftBook = true;
    public static boolean craftGhostDust = true;
    public static boolean craftRainbowWand = true;
    public static boolean findRainbowDust = true;
    public static int rainbowDustRate = 10;
    public static String[] validLootLocations = new String[] { "dungeonChest", "pyramidJungleDispenser", "strongholdLibrary", "pyramidDesertyChest", "bonusChest" };
    
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
        
        String category = "Settings";
        spawnRandom = config.getBoolean("spawnRandom", category, spawnRandom, "Should mobs randomly spawn with a color?");
        spawnRate = config.getFloat("spawnRate", category, spawnRate, 0.0f, 1.0f, "What percentage of mobs should spawn with a random color? 0.0f is 0%, 0.55f is 55% and 1.0 is 100%");
        dropPowder = config.getBoolean("dropPowder", category, dropPowder, "Should a mob drop their powder on death?");
        limitMobs = config.getBoolean("limitMobs", category, limitMobs, "Should only mobs on the list of valid mobs spawn with a random color?");
        craftDye = config.getBoolean("craftDyes", category, craftDye, "Should vanilla dye colors be craftable?");
        cloneDye = config.getBoolean("cloneDye", category, cloneDye, "Should players be able to copy a dye by mixing it with a pure white dye?");
        craftBook = config.getBoolean("craftBook", category, craftBook, "Should players be able to craft the data checker book?");
        craftGhostDust = config.getBoolean("craftGhostDust", category, craftGhostDust, "Should players be able to craft the ghost dust?");
        findRainbowDust = config.getBoolean("findRainbowDust", category, findRainbowDust, "Should players be able to find rainbow dusts in chests?");
        craftRainbowWand = config.getBoolean("craftRainbowWand", category, craftRainbowWand, "Should Rainbow Wands be craftable?");
        rainbowDustRate = config.getInt("rainbowDustWeight", category, rainbowDustRate, 0, 100, "How often should the rainbow dust show up in dungeon chests? Higher means more often.");
        validLootLocations = config.getStringList("validLootLocations", category, validLootLocations, "A list of valid loot locations for the rainbow dust and other loot to spawn in.");
        validMobs = config.getStringList("validMobs", category, validMobs, "A list of mobs that CAN spawn with a color effect on them. This is only requred is limitMobs is true.");
        
        if (config.hasChanged())
            config.save();
    }
}
