package net.epoxide.colorfulmobs.lib;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Utilities {
    
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
}
