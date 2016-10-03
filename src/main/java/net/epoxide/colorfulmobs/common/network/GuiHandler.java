package net.epoxide.colorfulmobs.common.network;

import net.epoxide.colorfulmobs.client.gui.GuiColorSelection;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
    
    /**
     * The Entity to display in the color gui.
     */
    private static EntityLivingBase entitys;
    
    /**
     * Sets the entity being displayed in the color wand gui to this entity.
     * 
     * @param entity: The entity to display in the gui.
     */
    public static void setEntity (EntityLivingBase entity) {
        
        entitys = entity;
    }
    
    @Override
    public Object getServerGuiElement (int ID, EntityPlayer player, World world, int x, int y, int z) {
        
        return null;
    }
    
    @Override
    public Object getClientGuiElement (int ID, EntityPlayer player, World world, int x, int y, int z) {
        
        switch (ID) {
            
            case 0:
                return new GuiColorSelection(entitys);
        }
        
        return null;
    }
}