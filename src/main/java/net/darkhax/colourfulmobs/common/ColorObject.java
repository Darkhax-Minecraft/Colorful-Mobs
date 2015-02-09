package net.darkhax.colourfulmobs.common;

import net.darkhax.bookshelf.helper.NumericHelper;
import net.minecraft.nbt.NBTTagCompound;

public class ColorObject {

    public float red;
    public float green;
    public float blue;
    public float alpha;

    public ColorObject(float red, float green, float blue, float alpha) {

        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }

    public ColorObject(float red, float green, float blue) {

        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = 1.0f;
    }

    public ColorObject(boolean doAlpha) {

        this.red = getRandomColor();
        this.green = getRandomColor();
        this.blue = getRandomColor();
        this.alpha = (doAlpha) ? getRandomColor() : 1.0f;
    }

    public static ColorObject getColorFromTag(NBTTagCompound tag) {

        return new ColorObject(tag.getFloat("red"), tag.getFloat("green"), tag.getFloat("blue"), tag.getFloat("alpha"));
    }

    public static NBTTagCompound getTagFromColor(ColorObject colorObj) {

        NBTTagCompound colorTag = new NBTTagCompound();
        colorTag.setFloat("red", colorObj.red);
        colorTag.setFloat("green", colorObj.green);
        colorTag.setFloat("blue", colorObj.blue);
        colorTag.setFloat("alpha", colorObj.alpha);
        return colorTag;
    }

    public static float getRandomColor() {

        return NumericHelper.nextIntII(1, 255) / 255;
    }
}
