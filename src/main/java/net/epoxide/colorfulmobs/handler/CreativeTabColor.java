package net.epoxide.colorfulmobs.handler;

import net.minecraft.item.Item;

import net.darkhax.bookshelf.creativetab.CreativeTabCached;

public class CreativeTabColor extends CreativeTabCached {
    
    public CreativeTabColor() {
        
        super("colorfulmobs");
    }
    
    @Override
    public Item getTabIconItem () {
        
        return ContentHandler.itemColorWand;
    }
}
