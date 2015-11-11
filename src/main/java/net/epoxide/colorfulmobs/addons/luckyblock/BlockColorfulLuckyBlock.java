package net.epoxide.colorfulmobs.addons.luckyblock;

import net.epoxide.colorfulmobs.ColorfulMobs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockColorfulLuckyBlock extends Block {

    public static List<WeightedPossibilities> outcomes = new ArrayList();

    protected BlockColorfulLuckyBlock () {

        super(Material.glass);
        this.setBlockName("colorfulmobs.colorfulblock");
        this.setBlockTextureName("colorfulmobs:lucky_block_colorful");
        this.setCreativeTab(ColorfulMobs.tabColor);
    }

    @Override
    public boolean removedByPlayer (World world, EntityPlayer player, int x, int y, int z, boolean willHarvest) {

        return WeightedPossibilities.triggerRandomEvent(outcomes, player, x, y, z);
    }

    @Override
    public int quantityDropped (Random rnd) {

        return 0;
    }
}