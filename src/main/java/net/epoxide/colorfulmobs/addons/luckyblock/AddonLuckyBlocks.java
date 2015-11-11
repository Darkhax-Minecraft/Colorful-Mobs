package net.epoxide.colorfulmobs.addons.luckyblock;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class AddonLuckyBlocks {

    public static Block blockColorfulLuckyBlocks = new BlockColorfulLuckyBlock();

    public AddonLuckyBlocks () {

        GameRegistry.registerBlock(blockColorfulLuckyBlocks, "colorfulluckyblock");
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockColorfulLuckyBlocks), "###", "#X#", "###", Character.valueOf('#'), "dye", Character.valueOf('X'), Block.blockRegistry.getObject("lucky:lucky_block")));
        new WeightedPossibilities.PossibilityDyeExplosion();
        new WeightedPossibilities.PossibilityDyes();
        new WeightedPossibilities.PossibilityNew();
        new WeightedPossibilities.PossibilityNothing();
        new WeightedPossibilities.PossibilityRandomMob();
        new WeightedPossibilities.PossibilityRevert();
        new WeightedPossibilities.PossibilityEnjoyTheChickens();
    }
}
