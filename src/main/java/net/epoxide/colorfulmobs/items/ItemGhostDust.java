package net.epoxide.colorfulmobs.items;

import java.util.List;

import net.darkhax.bookshelf.helper.NumericHelper;
import net.darkhax.bookshelf.objects.ColorObject;
import net.epoxide.colorfulmobs.ColorfulMobs;
import net.epoxide.colorfulmobs.common.ColorProperties;
import net.epoxide.colorfulmobs.common.PacketColorSync;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemGhostDust extends Item {

    public static IIcon rope;
    public static IIcon sack;

    public ItemGhostDust() {

        this.hasSubtypes = true;
        this.setCreativeTab(ColorfulMobs.tabColor);
        this.setUnlocalizedName("colorfulmobs.ghostdust");
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity) {

        if (!player.worldObj.isRemote && stack.hasTagCompound()) {

            ColorObject colorObj = ColorObject.getColorFromTag(stack.getTagCompound());
            ColorProperties props = ColorProperties.getPropsFromEntity(entity);
            if (props.hasColorProperties(entity) && !ColorObject.isGeneric(props.colorObj)) {

                float alpha = colorObj.alpha;
                colorObj = props.colorObj;
                colorObj.alpha = alpha;
            }

            ColorProperties.setEntityColors(colorObj, entity);
            ColorfulMobs.instance.network.sendToAll(new PacketColorSync(colorObj, entity));
        }

        stack.stackSize--;
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int pass) {

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

        return pass > 0 ? this.rope : this.sack;
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
    public void registerIcons(IIconRegister register) {

        super.registerIcons(register);
        this.rope = register.registerIcon("colorfulmobs:powder1");
        this.sack = register.registerIcon("colorfulmobs:powder2");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack) {

        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced) {

        if (stack.hasTagCompound()) {

            ColorObject colorObj = ColorObject.getColorFromTag(stack.getTagCompound());

            if (colorObj != null)
                list.add(NumericHelper.round(colorObj.alpha, 4) + "% " + StatCollector.translateToLocal("tooltip.colorfulmobs.transparency"));
        }
    }
}
