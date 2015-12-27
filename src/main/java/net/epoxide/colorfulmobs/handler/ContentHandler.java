package net.epoxide.colorfulmobs.handler;

import net.minecraft.item.Item;

import cpw.mods.fml.common.registry.GameRegistry;

import net.epoxide.colorfulmobs.item.ItemAlphaDust;
import net.epoxide.colorfulmobs.item.ItemRGBDust;

public class ContentHandler {
    
    public static Item itemRGBDust;
    public static Item itemAlphaDust;
    public static Item itemRainbowDust;
    public static Item itemRainbowWand;
    public static Item itemDataChecker;
    
    public static void initItems () {
        
        itemRGBDust = new ItemRGBDust();
        GameRegistry.registerItem(itemRGBDust, "rgb_dust");
        
        itemAlphaDust = new ItemAlphaDust();
        GameRegistry.registerItem(itemAlphaDust, "alpha_dust");
    }
}
