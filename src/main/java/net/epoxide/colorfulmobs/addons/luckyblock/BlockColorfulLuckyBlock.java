package net.epoxide.colorfulmobs.addons.luckyblock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.epoxide.colorfulmobs.ColorfulMobs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockColorfulLuckyBlock extends Block {
    
    public static List<WeightedPossibilities> outcomes = new ArrayList();
    
    protected BlockColorfulLuckyBlock() {
    
        super(Material.glass);
        this.setUnlocalizedName("colorfulmobs.colorfulblock");
        //this.setBlockTextureName("colorfulmobs:lucky_block_colorful");
        this.setCreativeTab(ColorfulMobs.tabColor);
    }
    
    @Override
    public boolean removedByPlayer(World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
    
        return WeightedPossibilities.triggerRandomEvent(outcomes, player, pos);
    }
    
    @Override
    public int quantityDropped (Random rnd) {
    
        return 0;
    }
}