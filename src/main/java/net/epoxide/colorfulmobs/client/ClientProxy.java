package net.epoxide.colorfulmobs.client;

import net.epoxide.colorfulmobs.common.CommonProxy;
import net.epoxide.colorfulmobs.handler.ContentHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {
    
    @Override
    public void preInit () {
        
        MinecraftForge.EVENT_BUS.register(new RenderingHandler());
        ModelLoader.setCustomModelResourceLocation(ContentHandler.itemRGBDust, 0, new ModelResourceLocation("colorfulmobs:rgb_dust", "inventory"));
        ModelLoader.setCustomModelResourceLocation(ContentHandler.itemAlphaDust, 0, new ModelResourceLocation("colorfulmobs:alpha_dust", "inventory"));
        ModelLoader.setCustomModelResourceLocation(ContentHandler.itemRainbowDust, 0, new ModelResourceLocation("colorfulmobs:rainbow_dust", "inventory"));
        ModelLoader.setCustomModelResourceLocation(ContentHandler.itemRainbowWand, 0, new ModelResourceLocation("colorfulmobs:wand_color", "inventory"));
        ModelLoader.setCustomModelResourceLocation(ContentHandler.itemDataChecker, 0, new ModelResourceLocation("colorfulmobs:data_checker", "inventory"));
        ModelLoader.setCustomModelResourceLocation(ContentHandler.itemRadiantDust, 0, new ModelResourceLocation("colorfulmobs:radiant_dust", "inventory"));
    }
    
    @Override
    public void init() {
        
        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new ColorHandler(), ContentHandler.itemRGBDust);
    }
}
