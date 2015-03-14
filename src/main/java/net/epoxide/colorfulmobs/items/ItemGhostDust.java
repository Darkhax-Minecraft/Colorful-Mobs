package net.epoxide.colorfulmobs.items;

import java.util.List;

import net.darkhax.bookshelf.objects.ColorObject;
import net.epoxide.colorfulmobs.handler.AchievementHandler;
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
    public ColorObject getColorToApply(ItemStack stack) {

        return ColorObject.getColorFromTag(stack.getTagCompound());
    }
    
    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity) {
        
        super.itemInteractionForEntity(stack, player, entity);
        player.triggerAchievement(AchievementHandler.achGhost);
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int pass) {

        return ColorObject.getIntFromColor(new ColorObject(211, 211, 211));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List itemList) {

        for (int i = 0; i < 6; i++) {

            ItemStack stack = new ItemStack(this);
            stack.setTagCompound(ColorObject.getTagFromColor(new ColorObject(1.0f, 1.0f, 1.0f, (0.20f * i))));
            itemList.add(stack);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack par1ItemStack, int pass) {

        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced) {

        if (stack.hasTagCompound()) {

            ColorObject colorObj = ColorObject.getColorFromTag(stack.getTagCompound());

            if (colorObj != null)
                list.add((int) (colorObj.alpha * 100) + "% " + StatCollector.translateToLocal("chat.colorfulmobs.transparency"));
        }
    }
}
