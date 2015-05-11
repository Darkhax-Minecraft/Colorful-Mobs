package net.epoxide.colorfulmobs.addons;

import net.epoxide.colorfulmobs.addons.luckyblock.AddonLuckyBlocks;
import net.epoxide.colorfulmobs.addons.thaumcraft.AddonThaumcraft;
import net.epoxide.colorfulmobs.addons.waila.AddonWaila;
import cpw.mods.fml.common.Loader;

public class AddonManager {
    
    public AddonManager() {
    
        if (Loader.isModLoaded("Thaumcraft"))
            new AddonThaumcraft();
        
        if (Loader.isModLoaded("Waila"))
            new AddonWaila();
        
        if (Loader.isModLoaded("lucky"))
            new AddonLuckyBlocks();
    }
}