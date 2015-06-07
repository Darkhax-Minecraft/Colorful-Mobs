package net.epoxide.colorfulmobs.lib;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.text.WordUtils;


public class Utilities {
    
    /**
     * Checks if an entity is within X range of another entity.
     * 
     * @param source: The source entity.
     * @param target: The entity being compared.
     * @param range: The acceptable block range.
     * @return true if target is within the range of the source and not the same entity.
     */
    public static boolean isEntityWithinRange (Entity source, Entity target, double range) {
    
        if (isEntityWithinRange(target, new BlockPos(source.posX, source.posY, source.posZ), range)) {
            
            if (source != target) {
                
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Checks if an entity is within X range of given coordinates.
     * 
     * @param target: The target entity.
     * @param pos: The source coord.
     * @param range: Acceptable range of distance between entity and position.
     * @return true if entity is within distance.
     */
    public static boolean isEntityWithinRange (Entity target, BlockPos pos, double range) {
    
        double disX = Math.abs(pos.getX() - target.posX);
        double disY = Math.abs(pos.getY() - target.posY);
        double disZ = Math.abs(pos.getZ() - target.posZ);
        
        if ((disX + disY + disZ < range)) {
            
            return true;
        }
        
        return false;
    }
    
    /**
     * This method can be used to round a double to a certain amount of places.
     * 
     * @param value: The double being round.
     * @param places: The amount of places to round the double to.
     * @return double: The double entered however being rounded to the amount of places
     *         specified.
     */
    public static double round (double value, int places) {
    
        if (value >= 0 && places > 0) {
            
            BigDecimal bd = new BigDecimal(value);
            bd = bd.setScale(places, RoundingMode.HALF_UP);
            return bd.doubleValue();
        }
        
        return value;
    }
    
    /**
     * This method will take a string and break it down into multiple lines based on a provided
     * line length. The separate strings are then added to the list provided. This method is
     * useful for adding a long description to an item tool tip and having it wrap. This method
     * is similar to wrap in Apache WordUtils however it uses a List making it easier to use
     * when working with Minecraft.
     * 
     * @param string: The string being split into multiple lines. It's recommended to use
     *            StatCollector.translateToLocal() for this so multiple languages will be
     *            supported.
     * @param lnLength: The ideal size for each line of text.
     * @param wrapLongWords: If true the ideal size will be exact, potentially splitting words
     *            on the end of each line.
     * @param list: A list to add each line of text to. An good example of such list would be
     *            the list of tooltips on an item.
     * @return List: The same List instance provided however the string provided will be
     *         wrapped to the ideal line length and then added.
     */
    public static List<String> wrapStringToList (String string, int lnLength, boolean wrapLongWords, List<String> list) {
    
        String strings[] = WordUtils.wrap(string, lnLength, null, wrapLongWords).split(SystemUtils.LINE_SEPARATOR);
        list.addAll(Arrays.asList(strings));
        return list;
    }
    
    /**
     * Returns a random number between the min and max. The min and max are both possible to
     * get.
     * 
     * @param min : The smallest possible value for this number to be.
     * @param max : The largest possible number for this value to be.
     * @return int: A random int between the provided mina nd max.
     */
    public static int nextIntII (int min, int max) {
    
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
     * @param posX : The x position for the item to spawn.
     * @param posY : The y position for the item to spawn.
     * @param posZ : The z position for the item to spawn.
     * @param stack : The ItemStack being dropped into the world.
     * @param isTile : If the item being dropped into the world as a tile this should be set to
     *            true. This option allows for this code to adhere to the doTileDrops game
     *            rule.
     */
    public static void dropStackInWorld (World world, double posX, double posY, double posZ, ItemStack stack, boolean isTile) {
    
        boolean shouldDrop = (!isTile) || world.getGameRules().getGameRuleBooleanValue("doTileDrops");
        if (!world.isRemote && shouldDrop) {
            
            float f = 0.7F;
            double d0 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
            double d1 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
            double d2 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
            EntityItem entityitem = new EntityItem(world, posX + d0, posY + d1, posZ + d2, stack);
            entityitem.setDefaultPickupDelay();
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
