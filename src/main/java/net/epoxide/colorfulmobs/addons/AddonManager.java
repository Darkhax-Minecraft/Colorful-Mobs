package net.epoxide.colorfulmobs.addons;

import net.epoxide.colorfulmobs.addons.luckyblock.AddonLuckyBlocks;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class AddonManager {

    public static void preInit (FMLPreInitializationEvent event) {

//        if (Loader.isModLoaded("Thaumcraft"))
//            new AddonThaumcraft();

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