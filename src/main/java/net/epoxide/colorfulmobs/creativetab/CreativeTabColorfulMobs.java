package net.epoxide.colorfulmobs.creativetab;

import net.minecraft.init.Items;
import net.minecraft.item.Item;

import net.darkhax.bookshelf.creativetab.CreativeTabCached;

import net.epoxide.colorfulmobs.handler.ContentHandler;

public class CreativeTabColorfulMobs extends CreativeTabCached {
    
    public CreativeTabColorfulMobs() {
        
        super("colorfulmobs");
    }
    
    @Override
    public Item getTabIconItem () {
        
        return ContentHandler.itemDataChecker;
    }
}
