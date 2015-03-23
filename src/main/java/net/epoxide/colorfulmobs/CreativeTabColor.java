package net.epoxide.colorfulmobs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTabColor extends CreativeTabs {
    
    public CreativeTabColor() {
    
        super("colorfulmobs");
    }
    
    @Override
    public Item getTabIconItem () {
    
        return ColorfulMobs.itemColorWand;
    }
}
