package net.epoxide.colorfulmobs.handler;

import net.epoxide.colorfulmobs.ColorfulMobs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;

public class LootHandler {
    
    public LootHandler() {
    
        for (int pos = 0; pos < ConfigurationHandler.validLootLocations.size(); pos++)
            ChestGenHooks.addItem(ConfigurationHandler.validLootLocations.get(pos), new WeightedRandomChestContent(new ItemStack(ColorfulMobs.itemRainbowDust), 1, 1, ConfigurationHandler.rainbowDustRate));
    }
}
