package net.epoxide.colorfulmobs.addons.thaumcraft;

import java.util.List;

import net.epoxide.colorfulmobs.ColorfulMobs;
import net.epoxide.colorfulmobs.common.ColorProperties;
import net.epoxide.colorfulmobs.lib.ColorObject;
import net.epoxide.colorfulmobs.lib.EnumVanillaColors;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.FocusUpgradeType;
import thaumcraft.api.wands.ItemFocusBasic;
import thaumcraft.codechicken.lib.vec.Vector3;
import thaumcraft.common.items.wands.ItemWandCasting;

public class ItemFocusPinkus extends ItemFocusBasic {
    
    private AspectList visCost = new AspectList().add(Aspect.FIRE, 100).add(Aspect.ORDER, 100);
    private ColorObject pink = EnumVanillaColors.PINK.colorObj;
    private IIcon depthIcon;
    
    public ItemFocusPinkus() {
    
        this.setCreativeTab(ColorfulMobs.tabColor);
        this.setUnlocalizedName("colorfulmobs.pinkus");
    }
    
    @Override
    public ItemStack onFocusRightClick (ItemStack itemstack, World world, EntityPlayer player, MovingObjectPosition mop) {
    
        ItemWandCasting wand = (ItemWandCasting) itemstack.getItem();
        if (wand.consumeAllVis(itemstack, player, getVisCost(itemstack), true, false)) {
            
            for (EntityLivingBase entity : getEntitiesInView(player, 5, 4.5d)) {
                
                if (!ColorProperties.hasColorProperties(entity))
                    ColorProperties.setPropsToEntity(entity);
                
                ColorProperties props = ColorProperties.getPropsFromEntity(entity);
                
                if (!props.getColorObj().equals(pink) && props.isValidTarget()) {
                    
                    props.setColorObject(pink);
                    break;
                }
            }
        }
        
        player.swingItem();
        return itemstack;
    }
    
    @Override
    public void registerIcons (IIconRegister ir) {
    
        depthIcon = ir.registerIcon("colorfulmobs:foci/focus_pinkus_depth");
        icon = ir.registerIcon("colorfulmobs:foci/focus_pinkus");
    }
    
    @Override
    public String getSortingHelper (ItemStack itemstack) {
    
        return "PINKUS";
    }
    
    @Override
    public FocusUpgradeType[] getPossibleUpgradesByRank (ItemStack itemstack, int rank) {
    
        return new FocusUpgradeType[] {};
    }
    
    @Override
    public int getFocusColor (ItemStack focusstack) {
    
        return pink.getIntFromColor();
    }
    
    @Override
    public IIcon getFocusDepthLayerIcon (ItemStack itemstack) {
    
        return depthIcon;
    }
    
    @Override
    public AspectList getVisCost (ItemStack itemstack) {
    
        return visCost.copy();
    }
    
    public List<EntityLivingBase> getEntitiesInView (EntityPlayer player, int range, double distance) {
    
        Vector3 targetPos = Vector3.fromEntityCenter(player);
        targetPos.add(new Vector3(player.getLookVec()).multiply(distance));
        targetPos.y += 0.5;
        
        return player.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(targetPos.x - range, targetPos.y - range, targetPos.z - range, targetPos.x + range, targetPos.y + range, targetPos.z + range));
    }
}