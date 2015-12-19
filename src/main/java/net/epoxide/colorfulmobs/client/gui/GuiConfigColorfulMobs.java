package net.epoxide.colorfulmobs.client.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiScreen;

import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;

import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;

import net.epoxide.colorfulmobs.handler.ConfigurationHandler;
import net.epoxide.colorfulmobs.lib.Constants;

public class GuiConfigColorfulMobs extends GuiConfig {
    
    static Configuration cfg = ConfigurationHandler.config;
    static ConfigurationHandler cfgh;
    
    public GuiConfigColorfulMobs(GuiScreen parent) {
        
        super(parent, generateConfigList(), Constants.MOD_ID, false, false, GuiConfig.getAbridgedConfigPath(ConfigurationHandler.config.toString()));
    }
    
    /**
     * Generates a List of IConfigElement to use in a configuration GUI.
     * 
     * @return The List<IConfigElement> generated.
     */
    public static List<IConfigElement> generateConfigList () {
        
        ArrayList<IConfigElement> elements = new ArrayList<IConfigElement>();
        
        for (String name : cfg.getCategoryNames())
            elements.add(new ConfigElement(cfg.getCategory(name)));
            
        return elements;
    }
}
