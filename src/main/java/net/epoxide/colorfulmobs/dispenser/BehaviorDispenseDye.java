package net.epoxide.colorfulmobs.dispenser;

import java.util.List;

import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumFacing;

import net.darkhax.bookshelf.lib.ColorObject;
import net.darkhax.bookshelf.lib.util.ItemStackUtils;

import net.epoxide.colorfulmobs.common.ColorProperties;
import net.epoxide.colorfulmobs.item.ItemColorSetter;

public class BehaviorDispenseDye extends BehaviorDefaultDispenseItem {
    
    @Override
    protected ItemStack dispenseStack (IBlockSource source, ItemStack stack) {
        
        EnumFacing facing = BlockDispenser.func_149937_b(source.getBlockMetadata());
        int posX = source.getXInt() + facing.getFrontOffsetX();
        int posY = source.getYInt() + facing.getFrontOffsetY();
        int posZ = source.getZInt() + facing.getFrontOffsetZ();
        AxisAlignedBB targetArea = AxisAlignedBB.getBoundingBox((double) posX, (double) posY, (double) posZ, (double) (posX + 1), (double) (posY + 1), (double) (posZ + 1));
        
        List entityList = source.getWorld().getEntitiesWithinAABB(EntityLivingBase.class, targetArea);
        
        if (!entityList.isEmpty()) {
            
            EntityLivingBase living = (EntityLivingBase) entityList.get(0);
            
            if (!(living instanceof EntityPlayer) && ItemStackUtils.isValidStack(stack) && stack.getItem() instanceof ItemColorSetter && ColorProperties.hasProperties(living)) {
                
                ItemColorSetter item = (ItemColorSetter) stack.getItem();
                
                if (!source.getWorld().isRemote) {
                    
                    ColorProperties entProps = ColorProperties.getProperties(living);
                    ColorObject colorObj = item.getColorToApply(stack);
                    
                    if (entProps.isDyed())
                        colorObj = item.handleColorMerge(entProps.getColorObj(), colorObj);
                        
                    entProps.setColorObject(colorObj).sync();
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