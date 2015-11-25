package net.epoxide.colorfulmobs.addons;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.*;

import net.epoxide.colorfulmobs.addons.luckyblock.AddonLuckyBlocks;

public class AddonManager {
    
    public static void preInit (FMLPreInitializationEvent event) {
        
        // if (Loader.isModLoaded("Thaumcraft"))
        // new AddonThaumcraft();
        
        if (Loader.isModLoaded("lucky"))
            new AddonLuckyBlocks();
    }
    
    public static void init (FMLInitializationEvent event) {
        
        if (Loader.isModLoaded("Waila"))
            FMLInterModComms.sendMessage("Waila", "register", "net.epoxide.colorfulmobs.addons.waila.AddonWaila.registerAddon");
    }
    
    public static void postInit (FMLPostInitializationEvent event) {
    
    }
}