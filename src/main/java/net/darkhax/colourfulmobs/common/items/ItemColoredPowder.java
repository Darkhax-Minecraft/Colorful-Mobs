package net.darkhax.colourfulmobs.common.items;

import java.util.List;

import net.darkhax.colourfulmobs.ColorfulMobs;
import net.darkhax.colourfulmobs.common.ColorObject;
import net.darkhax.colourfulmobs.common.ColorProperties;
import net.darkhax.colourfulmobs.common.PacketColorSync;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemColoredPowder extends Item {

    public static IIcon rope;
    public static IIcon sack;

    public static ColorObject[] colors = { new ColorObject(211, 211, 211), new ColorObject(255, 0, 0), new ColorObject(255, 128, 0), new ColorObject(255, 255, 0), new ColorObject(128, 255, 0), new ColorObject(0, 153, 0), new ColorObject(0, 255, 0), new ColorObject(0, 255, 128), new ColorObject(0, 255, 255), new ColorObject(0, 128, 255), new ColorObject(0, 0, 255), new ColorObject(128, 0, 255), new ColorObject(255, 0, 255), new ColorObject(255, 0, 128), new ColorObject(204, 0, 204), new ColorObject(197, 179, 88), new ColorObject(128, 128, 128), new ColorObject(102, 51, 153), new ColorObject(255, 153, 153), new ColorObject(16, 145, 117) };

    public ItemColoredPowder() {

        this.hasSubtypes = true;
        this.setCreativeTab(ColorfulMobs.tabColor);
        this.setUnlocalizedName("colorfulmobs.powder");
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity) {

        if (!player.worldObj.isRemote && stack.hasTagCompound()) {

            ColorObject colorObj = ColorObject.getColorFromTag(stack.getTagCompound());
            ColorProperties.setEntityColors(colorObj, entity);
            ColorfulMobs.instance.network.sendToAll(new PacketColorSync(colorObj, entity));
        }

        stack.stackSize--;
        return true;
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

        return pass > 0 ? this.rope : this.sack;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List itemList) {

        for (int i = 0; i < colors.length; i++) {

            ItemStack stack = new ItemStack(this);
            stack.setTagCompound(ColorObject.getTagFromColor(colors[i]));
            itemList.add(stack);
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {

        super.registerIcons(register);
        this.rope = register.registerIcon("colorfulmobs:powder1");
        this.sack = register.registerIcon("colorfulmobs:powder2");
    }
}
