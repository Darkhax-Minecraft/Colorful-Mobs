package net.epoxide.colorfulmobs.client;

import com.sun.prism.paint.Color;

import net.epoxide.colorfulmobs.lib.ColorObject;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ColorHandler implements IItemColor {
    
    
    @Override
    public int getColorFromItemstack (ItemStack stack, int renderPass) {
        
        if (renderPass == 1 && stack.hasTagCompound()) {
            
            return new ColorObject(stack).toAWTColor().getRGB();
        }
        
        return 16777215;
    }
}
