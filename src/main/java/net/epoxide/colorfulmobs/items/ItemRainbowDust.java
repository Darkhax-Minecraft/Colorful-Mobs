package net.epoxide.colorfulmobs.items;

import net.darkhax.bookshelf.objects.ColorObject;
import net.minecraft.item.ItemStack;

public class ItemRainbowDust extends ItemColorSetter {

    public ItemRainbowDust() {

        this.hasSubtypes = true;
        this.setTextureName("colorfulmobs:rainbowdust");
        this.setUnlocalizedName("colorfulmobs.rainbowdust");
    }

    @Override
    public ColorObject getColorToApply(ItemStack stack) {

        return new ColorObject(false);
    }
}