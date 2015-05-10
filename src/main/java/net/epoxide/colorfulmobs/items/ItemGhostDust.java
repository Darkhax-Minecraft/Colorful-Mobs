package net.epoxide.colorfulmobs.items;

import java.util.List;

import net.epoxide.colorfulmobs.ColorfulMobs;
import net.epoxide.colorfulmobs.handler.AchievementHandler;
import net.epoxide.colorfulmobs.lib.ColorObject;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemGhostDust extends ItemColoredPowder {
    
    public ItemGhostDust() {
    
        this.hasSubtypes = true;
        this.setUnlocalizedName("colorfulmobs.ghostdust");
        this.setTextureName("colorfulmobs:powder1");
    }
    
    @Override
    public ColorObject getColorToApply (ItemStack stack) {
    
        return new ColorObject(stack.getTagCompound());
    }
    
    @Override
    public ColorObject applyMerger (ColorObject existingObj, ColorObject newObj) {
    
        return new ColorObject(existingObj.getRed(), existingObj.getGreen(), existingObj.getBlue(), newObj.getAlpha());
    }
    
    @Override
    public boolean itemInteractionForEntity (ItemStack stack, EntityPlayer player, EntityLivingBase entity) {
    
        super.itemInteractionForEntity(stack, player, entity);
        player.triggerAchievement(AchievementHandler.achGhost);
        return true;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack (ItemStack stack, int pass) {
    
        return new ColorObject(211, 211, 211).getIntFromColor();
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems (Item item, CreativeTabs tab, List itemList) {
    
        for (int i = 0; i < 6; i++) {
            
            ItemStack stack = new ItemStack(this);
            new ColorObject(1.0f, 1.0f, 1.0f, (0.20f * i)).writeToItemStack(stack);
            itemList.add(stack);
        }
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect (ItemStack par1ItemStack, int pass) {
    
        return true;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean advanced) {
    
        if (stack.hasTagCompound()) {
            
            ColorObject colorObj = new ColorObject(stack.getTagCompound());
            
            if (colorObj != null)
                list.add((100 - (int) (colorObj.getAlpha() * 100)) + "% " + StatCollector.translateToLocal("chat.colorfulmobs.transparency"));
        }
    }
    
    public static ItemStack getStackFromStrenght (float strength) {
    
        ItemStack stack = new ItemStack(ColorfulMobs.itemGhostDust);
        new ColorObject(1.0f, 1.0f, 1.0f, strength).writeToItemStack(stack);
        return stack;
    }
}
