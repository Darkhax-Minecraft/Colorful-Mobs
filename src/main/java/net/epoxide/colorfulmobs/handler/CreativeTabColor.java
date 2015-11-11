package net.epoxide.colorfulmobs.handler;

import net.darkhax.bookshelf.creativetab.CreativeTabCached;
import net.minecraft.item.Item;

public class CreativeTabColor extends CreativeTabCached {

    public CreativeTabColor () {

        super("colorfulmobs");
    }

    @Override
    public Item getTabIconItem () {

        return ContentHandler.itemColorWand;
    }
}
