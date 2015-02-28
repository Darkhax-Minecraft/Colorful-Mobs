package net.epoxide.colorfulmobs.items;

import net.darkhax.bookshelf.objects.ColorObject;
import net.epoxide.colorfulmobs.ColorfulMobs;
import net.epoxide.colorfulmobs.common.ColorProperties;
import net.epoxide.colorfulmobs.common.PacketColorSync;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemColorSetter extends Item {

    public ItemColorSetter() {

        this.setCreativeTab(ColorfulMobs.tabColor);
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity) {
        if (!player.worldObj.isRemote) {

            ColorObject colorObj = getColorToApply(stack, entity);
            ColorProperties.setEntityColors(colorObj, entity);
            ColorfulMobs.network.sendToAll(new PacketColorSync(colorObj, entity));
        }

        stack.stackSize--;
        return true;
    }

    /**
     * Supplies the ColorObject being applied by this item to an entity. Can be overridden to change the
     * behavior of colors being set.
     *
     * @param stack:  The ItemStack for this item.
     * @param entity: The living entity which is having color applied to it.
     * @return ColorObject: A ColorObject to be applied by this item.
     */
    public ColorObject getColorToApply(ItemStack stack, EntityLivingBase entity) {

        return new ColorObject(1, 1, 1);
    }

    /**
     * Used to check if the item should be consumed after a single use.
     *
     * @param stack:  The ItemStack for this item.
     * @param entity: The living entity which this item was used on.
     * @return boolean: True by default. If true, the item will be consumed.
     */
    public boolean isConsumed(ItemStack stack, EntityLivingBase entity) {

        return true;
    }
}
