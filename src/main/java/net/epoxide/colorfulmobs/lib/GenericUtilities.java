package net.epoxide.colorfulmobs.lib;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class GenericUtilities {
    
    /**
     * Returns a random number between the min and max. The min and max are both possible to
     * get.
     * 
     * @param min : The smallest possible value for this number to be.
     * @param max : The largest possible number for this value to be.
     * @return int: A random int between the provided mina nd max.
     */
    public static float nextIntII (int min, int max) {
    
        return Constants.RANDOM.nextInt(max - min + 1) + min;
    }
    
    /**
     * Retrieves an instance of the player from the client side. This code only exists in
     * client side code and can not be used in server side code.
     * 
     * @return
     */
    @SideOnly(Side.CLIENT)
    public static EntityPlayer thePlayer () {
    
        return Minecraft.getMinecraft().thePlayer;
    }
    
    /**
     * Safely drops an ItemStack into the world as an EntityItem.
     * 
     * @param world : Instance of the world.
     * @param x : The x position for the item to spawn.
     * @param y : The y position for the item to spawn.
     * @param z : The z position for the item to spawn.
     * @param stack : The ItemStack being dropped into the world.
     * @param isTile : If the item being dropped into the world as a tile this should be set to
     *            true. This option allows for this code to adhere to the doTileDrops game
     *            rule.
     */
    public static void dropStackInWorld (World world, double posX, double posY, double posZ, ItemStack stack, boolean isTile) {
    
        boolean shouldDrop = (isTile) ? world.getGameRules().getGameRuleBooleanValue("doTileDrops") : true;
        if (!world.isRemote && shouldDrop) {
            
            float f = 0.7F;
            double d0 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
            double d1 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
            double d2 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
            EntityItem entityitem = new EntityItem(world, (double) posX + d0, (double) posY + d1, (double) posZ + d2, stack);
            entityitem.delayBeforeCanPickup = 10;
            world.spawnEntityInWorld(entityitem);
        }
    }
    
    /**
     * Used to get multiple of a single item. This can be used for various things, such as the
     * amount of an item used in a shapeless crafting recipe.
     * 
     * @param stack : The ItemStack to multiply.
     * @param multiplier : The amount of entries you want.
     * @return ItemStack[]: An array of ItemStack, which contains clones of the input
     *         ItemStack.
     */
    public static ItemStack[] multiplyItem (ItemStack stack, int multiplier) {
    
        ItemStack[] output = new ItemStack[multiplier];
        
        for (int i = 0; i < multiplier; i++)
            output[i] = stack.copy();
        
        return output;
    }
}
