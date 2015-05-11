package net.epoxide.colorfulmobs.addons.luckyblock;

import java.util.List;
import java.util.Random;

import net.epoxide.colorfulmobs.ColorfulMobs;
import net.epoxide.colorfulmobs.common.ColorProperties;
import net.epoxide.colorfulmobs.common.PacketColorSync;
import net.epoxide.colorfulmobs.lib.ColorObject;
import net.epoxide.colorfulmobs.lib.Constants;
import net.epoxide.colorfulmobs.lib.Utilities;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityList.EntityEggInfo;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class WeightedPossibilities {
    
    private int weighting;
    
    public WeightedPossibilities(int weight) {
    
        this.weighting = weight;
        BlockColorfulLuckyBlock.outcomes.add(this);
    }
    
    /**
     * This method is triggered whenever this possibility is selected randomly. This is where
     * the code for this effect will be placed. This is called moments before the block is
     * actually broken.
     * 
     * @param player: The player who broken the block.
     * @param x: The x position of the block in world.
     * @param y: The y position of the block in world.
     * @param z: The z position of the block in world.
     */
    public boolean onBlockBroken (EntityPlayer player, int x, int y, int z) {
    
        return player.worldObj.setBlockToAir(x, y, z);
    }
    
    /**
     * Used to randomly select a possibility, and trigger it. This is where the randomizing
     * happens.
     * 
     * @param options: A List of all possible WeightedPossibilities. This is where our outcome
     *            is pulled from.
     * @param player: An instance of the player who triggered the event.
     * @param x: The x position where this event took place.
     * @param y: The y position where the event took place.
     * @param z: The z position where the event took place.
     */
    public static boolean triggerRandomEvent (List<WeightedPossibilities> options, EntityPlayer player, int x, int y, int z) {
    
        int total = 0;
        
        for (WeightedPossibilities weight : options)
            total += weight.weighting;
        
        int random = new Random().nextInt(total);
        int current = 0;
        
        for (WeightedPossibilities possibility : options) {
            
            current += possibility.weighting;
            
            if (random < current) {
                
                return possibility.onBlockBroken(player, x, y, z);
            }
        }
        
        return player.worldObj.setBlockToAir(x, y, z);
    }
    
    public static class PossibilityNothing extends WeightedPossibilities {
        
        public PossibilityNothing() {
        
            super(5);
        }
        
        @Override
        public boolean onBlockBroken (EntityPlayer player, int x, int y, int z) {
        
            return super.onBlockBroken(player, x, y, z);
        }
    }
    
    public static class PossibilityRevert extends WeightedPossibilities {
        
        public PossibilityRevert() {
        
            super(5);
        }
        
        @Override
        public boolean onBlockBroken (EntityPlayer player, int x, int y, int z) {
        
            player.worldObj.setBlock(x, y, z, Block.getBlockFromName("lucky:lucky_block"));
            return false;
        }
    }
    
    public static class PossibilityNew extends WeightedPossibilities {
        
        public PossibilityNew() {
        
            super(5);
        }
        
        @Override
        public boolean onBlockBroken (EntityPlayer player, int x, int y, int z) {
        
            return false;
        }
    }
    
    public static class PossibilityDyes extends WeightedPossibilities {
        
        public PossibilityDyes() {
        
            super(10);
        }
        
        @Override
        public boolean onBlockBroken (EntityPlayer player, int x, int y, int z) {
        
            for (int pos = 0; pos < Utilities.nextIntII(0, 12); pos++) {
                
                ItemStack colorStack = new ItemStack(ColorfulMobs.itemPowder);
                new ColorObject(false).writeToItemStack(colorStack);
                Utilities.dropStackInWorld(player.worldObj, x, y, z, colorStack, true);
            }
            
            return super.onBlockBroken(player, x, y, z);
        }
    }
    
    public static class PossibilityDyeExplosion extends WeightedPossibilities {
        
        public PossibilityDyeExplosion() {
        
            super(4);
        }
        
        @Override
        public boolean onBlockBroken (EntityPlayer player, int x, int y, int z) {
        
            for (int i = 0; i < player.worldObj.loadedEntityList.size(); i++) {
                
                Entity target = (Entity) player.worldObj.loadedEntityList.get(i);
                
                if (target instanceof EntityLivingBase && target != player && Utilities.isEntityWithinRange(target, x, y, z, 5.0d)) {
                    
                    ColorProperties props = ColorProperties.getPropsFromEntity((EntityLivingBase) target);
                    props.setColorObject(new ColorObject(false));
                    ColorfulMobs.network.sendToAll(new PacketColorSync(props.getColorObj(), (EntityLivingBase) target));
                }
            }
            
            return super.onBlockBroken(player, x, y, z);
        }
    }
    
    public static class PossibilityRandomMob extends WeightedPossibilities {
        
        public PossibilityRandomMob() {
        
            super(5);
        }
        
        @Override
        public boolean onBlockBroken (EntityPlayer player, int x, int y, int z) {
        
            if (!player.worldObj.isRemote) {
                
                Object[] values = EntityList.entityEggs.values().toArray();
                Object rndValue = values[Constants.RANDOM.nextInt(values.length)];
                
                if (rndValue instanceof EntityList.EntityEggInfo) {
                    
                    EntityEggInfo info = (EntityEggInfo) rndValue;
                    Entity entity = EntityList.createEntityByID(info.spawnedID, player.worldObj);
                    
                    if (entity instanceof EntityLivingBase) {
                        
                        entity.setLocationAndAngles(x, y, z, 0f, 0f);
                        ColorProperties props = ColorProperties.getPropsFromEntity((EntityLivingBase) entity);
                        props.setColorObject(new ColorObject(false));
                        player.worldObj.spawnEntityInWorld(entity);
                        return super.onBlockBroken(player, x, y, z);
                    }
                }
            }
            
            return super.onBlockBroken(player, x, y, z);
        }
    }
    
    public static class PossibilityEnjoyTheChickens extends WeightedPossibilities {
        
        public PossibilityEnjoyTheChickens() {
        
            super(999);
        }
        
        @Override
        public boolean onBlockBroken (EntityPlayer player, int x, int y, int z) {
        
            int max = Utilities.nextIntII(3, 50);
            for (int pos = 0; pos < max; pos++) {
                
                EntityChicken entity = (EntityChicken) EntityList.createEntityByName("Chicken", player.worldObj);
                
                if (entity != null) {
                    
                    ColorProperties props = ColorProperties.getPropsFromEntity(entity);
                    props.setColorObject(new ColorObject(false));
                    entity.setLocationAndAngles(x, y, z, 0, 0);
                    player.worldObj.spawnEntityInWorld(entity);
                    entity.motionX += 0.01;
                }
            }
            
            return super.onBlockBroken(player, x, y, z);
        }
    }
}