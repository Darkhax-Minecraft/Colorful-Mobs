package net.darkhax.colorfulmobs.client;

import net.darkhax.colorfulmobs.common.CommonProxy;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerSidedEvents() {

        MinecraftForge.EVENT_BUS.register(new RenderingHandler());
    }
}
