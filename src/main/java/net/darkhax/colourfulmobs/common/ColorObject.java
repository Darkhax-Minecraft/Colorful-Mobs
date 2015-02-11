package net.darkhax.colourfulmobs.common;

import net.darkhax.bookshelf.helper.NumericHelper;
import net.minecraft.nbt.NBTTagCompound;

public class ColorObject {

	public float red;
	public float green;
	public float blue;
	public float alpha;

	public ColorObject(boolean doAlpha) {

		this(getRandomColor(), getRandomColor(), getRandomColor(),
				(doAlpha) ? getRandomColor() : 1.0f);
	}

	public ColorObject(int red, int green, int blue) {

		this(red, green, blue, 255);
	}

	public ColorObject(int red, int green, int blue, int alpha) {

		this((float) red / 255, (float) green / 255, (float) blue / 255,
				(float) alpha / 255);
	}

	public ColorObject(float red, float green, float blue) {

		this(red, green, blue, 1.0f);
	}

	public ColorObject(float red, float green, float blue, float alpha) {

		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
	}

	public static ColorObject getColorFromTag(NBTTagCompound tag) {

		return new ColorObject(tag.getFloat("red"), tag.getFloat("green"),
				tag.getFloat("blue"), tag.getFloat("alpha"));
	}

	public static NBTTagCompound getTagFromColor(ColorObject colorObj) {

		NBTTagCompound colorTag = new NBTTagCompound();
		colorTag.setFloat("red", colorObj.red);
		colorTag.setFloat("green", colorObj.green);
		colorTag.setFloat("blue", colorObj.blue);
		colorTag.setFloat("alpha", colorObj.alpha);
		return colorTag;
	}

	public static int getIntFromColor(ColorObject colorObj) {

		int rgb = (int) (colorObj.red * 255);
		rgb = (rgb << 8) + (int) (colorObj.green * 255);
		rgb = (rgb << 8) + (int) (colorObj.blue * 255);
		return rgb;
	}

	public static ColorObject getObjectFromInt(int rgb) {

		float red = (float) (rgb >> 16 & 255) / 255.0F;
		float green = (float) (rgb >> 8 & 255) / 255.0F;
		float blue = (float) (rgb & 255) / 255.0F;
		return new ColorObject(red, green, blue);
	}

	public static float getRandomColor() {

		return NumericHelper.nextIntII(1, 255) / 255;
	}
}