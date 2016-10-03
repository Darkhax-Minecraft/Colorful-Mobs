package net.epoxide.colorfulmobs.creativetab;

import net.epoxide.colorfulmobs.handler.ContentHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTabColorfulMobs extends CreativeTabs {
    
    public CreativeTabColorfulMobs() {
        
        super("colorfulmobs");
    }
    
    @Override
    public Item getTabIconItem () {
        
        return ContentHandler.itemDataChecker;
    }
}