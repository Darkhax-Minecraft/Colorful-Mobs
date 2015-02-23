package net.darkhax.colorfulmobs.common.handler;

import cpw.mods.fml.common.network.IGuiHandler;
import net.darkhax.colorfulmobs.client.gui.GuiColorSelection;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {

    private static EntityLivingBase entitys;

    public static void setEntity(EntityLivingBase entity) {
        entitys = entity;

    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case 0:
                return new GuiColorSelection(entitys);
        }
        return null;
    }
}
