package net.epoxide.colorfulmobs.client.gui;

import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;
import net.epoxide.colorfulmobs.handler.ConfigurationHandler;
import net.epoxide.colorfulmobs.lib.Constants;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;

import java.util.ArrayList;
import java.util.List;

public class GuiConfigColorfulMobs extends GuiConfig {

    static Configuration cfg = ConfigurationHandler.config;
    static ConfigurationHandler cfgh;

    public GuiConfigColorfulMobs (GuiScreen parent) {

        super(parent, generateConfigList(), Constants.MOD_ID, false, false, GuiConfig.getAbridgedConfigPath(ConfigurationHandler.config.toString()));
    }

    public static List<IConfigElement> generateConfigList () {

        ArrayList<IConfigElement> elements = new ArrayList<IConfigElement>();
        elements.add(new ConfigElement(cfg.getCategory(cfgh.GENERAL)));
        return elements;
    }
}
