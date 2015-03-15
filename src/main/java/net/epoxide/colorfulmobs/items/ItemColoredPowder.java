package net.epoxide.colorfulmobs.items;

import java.util.List;

import net.epoxide.colorfulmobs.ColorfulMobs;
import net.epoxide.colorfulmobs.handler.AchievementHandler;
import net.epoxide.colorfulmobs.lib.ColorObject;
import net.epoxide.colorfulmobs.lib.EnumVanillaColors;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemColoredPowder extends ItemColorSetter {

    public static IIcon rope;
    public static IIcon sack;

    public ItemColoredPowder() {

        this.hasSubtypes = true;
        this.setUnlocalizedName("colorfulmobs.powder");
        this.setTextureName("colorfulmobs:powder1");
    }

    @Override
    public ColorObject getColorToApply(ItemStack stack) {

        if (stack.hasTagCompound())
            return ColorObject.getColorFromTag(stack.getTagCompound());

        return new ColorObject(1, 1, 1);
    }

    @Override
    public ColorObject applyMerger(ColorObject existingObj, ColorObject newObj) {

        return new ColorObject(newObj.red, newObj.blue, newObj.green, existingObj.alpha);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int pass) {

        if (pass == 0 && stack.hasTagCompound())
            return ColorObject.getIntFromColor(ColorObject.getColorFromTag(stack.stackTagCompound));

        else
            return 10511680;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {

        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(int damage, int pass) {

        return pass > 0 ? rope : sack;
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity) {

        ColorObject colorObj = ColorObject.getColorFromTag(stack.getTagCompound());

        if (ColorObject.isGeneric(colorObj))
            player.triggerAchievement(AchievementHandler.achPureDye);

        else
            player.triggerAchievement(AchievementHandler.achDyeMob);

        return super.itemInteractionForEntity(stack, player, entity);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List itemList) {

        int counter = 0;
        for (EnumVanillaColors color : EnumVanillaColors.values()) {

            ItemStack powderStack = new ItemStack(ColorfulMobs.itemPowder, 1);
            powderStack.setTagCompound(ColorObject.getTagFromColor(color.colorObj));
            itemList.add(powderStack);
            counter++;
        }

        for (int i = 0; i < 16; i++) {

            ItemStack stack = new ItemStack(this);
            stack.setTagCompound(ColorObject.getTagFromColor(new ColorObject(false)));
            stack.setStackDisplayName(EnumChatFormatting.DARK_AQUA + "Random Dye Powder");
            itemList.add(stack);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {

        super.registerIcons(register);
        rope = register.registerIcon("colorfulmobs:powder1");
        sack = register.registerIcon("colorfulmobs:powder2");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced) {

        if (stack.hasTagCompound()) {

            ColorObject colorObj = ColorObject.getColorFromTag(stack.getTagCompound());

            if (colorObj != null)
                list.add(EnumChatFormatting.RED + "" + (int) (colorObj.red * 255) + " " + EnumChatFormatting.GREEN + (int) (colorObj.green * 255) + " " + EnumChatFormatting.BLUE + (int) (colorObj.blue * 255));
        }
    }

    /**
     * A method to help get item stack representations of a color object. Mostly used for achievements.
     * 
     * @param obj: Desired color.
     * @return ItemStack: A new stack containing this item, and its color data.
     */
    public static ItemStack getStackFromColorObject(ColorObject obj) {

        ItemStack stack = new ItemStack(ColorfulMobs.itemPowder);
        stack.setTagCompound(ColorObject.getTagFromColor(obj));

        return stack;
    }
}