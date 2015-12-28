package net.epoxide.colorfulmobs.lib;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import net.darkhax.bookshelf.lib.ColorObject;
import net.darkhax.bookshelf.lib.Position;

public class Utilities {
    
    public static void spawnDyeParticles (ColorObject color, World world, Position pos, int amount) {
        
        int red = (int) (color.getRed() * 255);
        int green = (int) (color.getGreen() * 255);
        int blue = (int) (color.getBlue() * 255);
        
        for (int count = 0; count < amount; count++)
            world.spawnParticle("reddust", (double) pos.getX() + Constants.RANDOM.nextDouble(), (double) pos.getY() + Constants.RANDOM.nextDouble(), (double) pos.getZ() + Constants.RANDOM.nextDouble(), red, green, blue);
    }
    
    /**
     * Checks if an entity is within X range of given coordinates.
     *
     * @param target: The target entity.
     * @param x: The source X coord.
     * @param y: The source Y coord.
     * @param z: The source Z coord.
     * @param range: Acceptable range of distance between entity and position.
     * @return true if entity is within distance.
     */
    public static boolean isEntityWithinRange (Entity target, double x, double y, double z, double range) {
        
        double disX = Math.abs(x - target.posX);
        double disY = Math.abs(y - target.posY);
        double disZ = Math.abs(z - target.posZ);
        
        return (disX + disY + disZ < range);
        
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
        
        boolean shouldDrop = !isTile || world.getGameRules().getGameRuleBooleanValue("doTileDrops");
        if (!world.isRemote && shouldDrop) {
            
            float f = 0.7F;
            double d0 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
            double d1 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
            double d2 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
            EntityItem entityitem = new EntityItem(world, posX + d0, posY + d1, posZ + d2, stack);
            entityitem.delayBeforeCanPickup = 10;
            world.spawnEntityInWorld(entityitem);
        }
    }
    
    /**
     * Checks if an array of String contains a particular String.
     * 
     * @param array: The array of strings to search through.
     * @param entry: The string to search for.
     * @return boolean: Whether or not the array contains the String.
     */
    public static boolean arrayContains (String[] array, String entry) {
        
        for (String element : array)
            if (element.equals(entry))
                return true;
                
        return false;
    }
}
