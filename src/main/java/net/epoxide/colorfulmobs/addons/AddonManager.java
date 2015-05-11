package net.epoxide.colorfulmobs.addons;

import net.epoxide.colorfulmobs.addons.thaumcraft.AddonThaumcraft;
import cpw.mods.fml.common.Loader;

public class AddonManager {
    
    public AddonManager() {
    
        if (Loader.isModLoaded("Thaumcraft"))
            new AddonThaumcraft();
        
        if (Loader.isModLoaded("Waila"))
            new AddonWaila();
    }
}