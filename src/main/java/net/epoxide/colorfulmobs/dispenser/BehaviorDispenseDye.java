package net.epoxide.colorfulmobs.dispenser;

import java.util.List;

import net.epoxide.colorfulmobs.common.ColorProperties;
import net.epoxide.colorfulmobs.common.ColorProperties.IColorHolder;
import net.epoxide.colorfulmobs.item.ItemColorSetter;
import net.epoxide.colorfulmobs.lib.ColorObject;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;

public class BehaviorDispenseDye extends BehaviorDefaultDispenseItem {
    
    @Override
    protected ItemStack dispenseStack (IBlockSource source, ItemStack stack) {
        
        EnumFacing enumfacing = (EnumFacing) source.func_189992_e().getValue(BlockDispenser.FACING);
        double posX = source.getX() + (double) ((float) enumfacing.getFrontOffsetX() * 1.125F);
        double posY = source.getY() + (double) ((float) enumfacing.getFrontOffsetY() * 1.125F);
        double posZ = source.getZ() + (double) ((float) enumfacing.getFrontOffsetZ() * 1.125F);
        AxisAlignedBB targetArea = new AxisAlignedBB((double) posX, (double) posY, (double) posZ, (double) (posX + 1), (double) (posY + 1), (double) (posZ + 1));
        
        List<EntityLivingBase> entityList = source.getWorld().getEntitiesWithinAABB(EntityLivingBase.class, targetArea);
        
        if (!entityList.isEmpty()) {
            
            EntityLivingBase living = (EntityLivingBase) entityList.get(0);
            
            if (!(living instanceof EntityPlayer) && stack != null && stack.getItem() instanceof ItemColorSetter && ColorProperties.hasProperties(living)) {
                
                ItemColorSetter item = (ItemColorSetter) stack.getItem();
                
                if (!source.getWorld().isRemote) {
                    
                    IColorHolder entProps = ColorProperties.getProperties(living);
                    ColorObject colorObj = item.getColorToApply(stack);
                    
                    if (entProps.isDyed())
                        colorObj = item.handleColorMerge(entProps.getColor(), colorObj);
                    
                    entProps.setColor(colorObj);
                    entProps.sync();
                }
                
                if (item.shouldConsumeItem(stack, living))
                    stack.stackSize--;
            }
            
            return stack;
        }
        
        else
            return stack;
    }
}