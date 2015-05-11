package net.epoxide.colorfulmobs.addons.thaumcraft;

import net.minecraft.item.Item;
import cpw.mods.fml.common.registry.GameRegistry;

public class AddonThaumcraft {
    
    public Item focusPinkus = new ItemFocusPinkus();
    
    public AddonThaumcraft() {
    
        GameRegistry.registerItem(focusPinkus, "colorfulmobs.focuspinkus");
    }
}
