package net.epoxide.colorfulmobs.client;

import net.minecraftforge.common.MinecraftForge;

import net.epoxide.colorfulmobs.common.CommonProxy;

public class ClientProxy extends CommonProxy {
    
    @Override
    public void registerSidedEvents () {
        
        MinecraftForge.EVENT_BUS.register(new RenderingHandler());
    }
}
