package net.epoxide.colorfulmobs.item;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.epoxide.colorfulmobs.handler.ContentHandler;
import net.epoxide.colorfulmobs.lib.ColorObject;
import net.epoxide.colorfulmobs.lib.Constants;
import net.epoxide.colorfulmobs.lib.VanillaColor;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemRGBDust extends ItemColorSetter {
    
    public ItemRGBDust() {
        
        this.hasSubtypes = true;
        this.setUnlocalizedName("colorfulmobs.powder");
        this.setRegistryName(new ResourceLocation(Constants.MOD_ID, "rgb_dust"));
    }
    
    @Override
    public ColorObject getColorToApply (ItemStack stack) {
        
        if (stack.hasTagCompound())
            return new ColorObject(stack.getTagCompound());
        
        return new ColorObject(1, 1, 1);
    }
    
    @Override
    public ColorObject handleColorMerge (ColorObject existingObj, ColorObject newObj) {
        
        return new ColorObject(newObj.getRed(), newObj.getGreen(), newObj.getBlue(), existingObj.getAlpha());
    }
    
    //TODO fix this
    @Override
    public boolean itemInteractionForEntity (ItemStack stack, EntityPlayer player, EntityLivingBase target, EnumHand hand) {
        
        if (!stack.hasTagCompound())
            stack.setTagCompound(new NBTTagCompound());
        
        ColorObject colorObj = new ColorObject(stack.getTagCompound());
        return super.itemInteractionForEntity(stack, player, target, hand);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems (Item item, CreativeTabs tab, List<ItemStack> itemList) {
        
        for (VanillaColor color : VanillaColor.values()) {
            
            ItemStack powderStack = new ItemStack(ContentHandler.itemRGBDust, 1);
            ColorObject colorObj = new ColorObject(color.getColor());
            colorObj.write(powderStack);
            itemList.add(powderStack);
        }
        
        for (int i = 0; i < 16; i++) {
            
            ItemStack stack = new ItemStack(this);
            new ColorObject(false).write(stack);
            stack.setStackDisplayName(ChatFormatting.DARK_AQUA + I18n.format("item.colorfulmobs.powder.random.name"));
            itemList.add(stack);
        }
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List<String> list, boolean advanced) {
        
        if (stack.hasTagCompound()) {
            
            ColorObject colorObj = new ColorObject(stack.getTagCompound());
            
            if (colorObj != null)
                list.add(colorObj.toString());
        }
    }
    
    /**
     * Generates an ItemStack of the RGB dust that represents the ColorObject passed.
     * 
     * @param obj: The ColorObject for the ItemStack to represent.
     * @return ItemStack: The newly created ItemStack.
     */
    public static ItemStack getStackFromColorObject (ColorObject obj) {
        
        ItemStack stack = new ItemStack(ContentHandler.itemRGBDust);
        obj.write(stack);
        return stack;
    }
}