package net.epoxide.colorfulmobs.lib;

import net.minecraft.nbt.NBTTagCompound;

public class ColorObject {
    
    public float red;
    public float green;
    public float blue;
    public float alpha;
    
    /**
     * Constructs a new ColorObject with completely random values for RGBA.
     * 
     * @param doAlpha : If true, the alpha value will also be randomized, by default it is 1.
     */
    public ColorObject(boolean doAlpha) {
    
        this(getRandomColor(), getRandomColor(), getRandomColor(), (doAlpha) ? getRandomColor() : 1.0f);
    }
    
    /**
     * Creates a new ColorObject using integers which will be converted back into floats. The
     * alpha value is automatically set to 1. (0-255)
     * 
     * @param red : The amount of red that makes up this color.
     * @param green : The amount of green that makes up this color.
     * @param blue : The amount of blue that makes up this color.
     * */
    public ColorObject(int red, int green, int blue) {
    
        this((float) red / 255, (float) green / 255, (float) blue / 255, 1.0f);
    }
    
    /**
     * Creates a new ColorObject using integers which will be converted back into floats.
     * (0-255)
     * 
     * @param red : The amount of red that makes up this color.
     * @param green : The amount of green that makes up this color.
     * @param blue : The amount of blue that makes up this color.
     * @param alpha : The transparency for this color. 0 is fully transparent, while 100 is
     *            completely solid.
     * */
    public ColorObject(int red, int green, int blue, int alpha) {
    
        this((float) red / 255, (float) green / 255, (float) blue / 255, (float) alpha / 100);
    }
    
    /**
     * Creates a new ColorObject using the standard RGB color values. Alpha is set to 1.
     * 
     * @param red : The amount of red that makes up this color.
     * @param green : The amount of green that makes up this color.
     * @param blue : The amount of blue that makes up this color.
     */
    public ColorObject(float red, float green, float blue) {
    
        this(red, green, blue, 1.0f);
    }
    
    /**
     * Creates a new ColorObject using the standard RGBA color values.
     * 
     * @param red : The amount of that makes up this color.
     * @param green : The amount of that makes up this color.
     * @param blue : The amount of that makes up this color.
     * @param alpha : The transparency of this color object. 0 is completely see through, 1 is
     *            completely solid.
     */
    public ColorObject(float red, float green, float blue, float alpha) {
    
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }
    
    /**
     * Creates a ColorObject from an NBTTagCompound, like the one created by getTagFromColor().
     * 
     * @param tag : An NBTTagCompound which contains a red green blue and alpha tag. If a
     *            specific piece of data is missing, it will be treated as a 0.
     * @return ColorObject: A ColorObject which contains all of the color data from the
     *         provided NBTTagCompound.
     */
    public static ColorObject getColorFromTag (NBTTagCompound tag) {
    
        return new ColorObject(tag.getFloat("red"), tag.getFloat("green"), tag.getFloat("blue"), tag.getFloat("alpha"));
    }
    
    /**
     * Creates a NBTTagCompound from a ColorObject.
     * 
     * @param colorObj : An instance of ColorObject to be converted into a NBTTagCompound.
     * @return NBTTagCompound: A NBTTagCompound containing the RGBA of the ColorObject.
     */
    public static NBTTagCompound getTagFromColor (ColorObject colorObj) {
    
        NBTTagCompound colorTag = new NBTTagCompound();
        colorTag.setFloat("red", colorObj.red);
        colorTag.setFloat("green", colorObj.green);
        colorTag.setFloat("blue", colorObj.blue);
        colorTag.setFloat("alpha", colorObj.alpha);
        return colorTag;
    }
    
    /**
     * Converts a ColorObject to a decimal color value. This is most notably used by Mojang for
     * item overlay colors.
     * 
     * @param colorObj : An instance of the ColorObject that is being converted.
     * @return int: An Integer which represents all of the color data.
     */
    public static int getIntFromColor (ColorObject colorObj) {
    
        int rgb = (int) (colorObj.red * 255);
        rgb = (rgb << 8) + (int) (colorObj.green * 255);
        rgb = (rgb << 8) + (int) (colorObj.blue * 255);
        return rgb;
    }
    
    /**
     * Converts a decimal color integer like the one produced in getIntFromColor back into a
     * ColorObject.
     * 
     * @param rgb : The decimal value which represents all of the color data.
     * @return ColorObject: A new ColorObject which contains all of the color data.
     */
    public static ColorObject getObjectFromInt (int rgb) {
    
        float red = (float) (rgb >> 16 & 255) / 255.0F;
        float green = (float) (rgb >> 8 & 255) / 255.0F;
        float blue = (float) (rgb & 255) / 255.0F;
        return new ColorObject(red, green, blue);
    }
    
    /**
     * Creates a random float value which represents a color.
     * 
     * @return float: A random float between 0 and 1.
     */
    public static float getRandomColor () {
    
        return GenericUtilities.nextIntII(1, 255) / 255;
    }
    
    /**
     * A simple method used to check if a ColorObject is generic. A generic ColorObject is
     * considered a ColorObject that represents White.
     * 
     * @param colorObj : A ColorObject to be checked.
     * @return boolean: If the ColorObject represents pure white, this method will return true.
     */
    public static boolean isGeneric (ColorObject colorObj) {
    
        if (colorObj == null)
            return false;
        
        return isGeneric(colorObj, 1.0f);
    }
    
    /**
     * A method which can be used to check if a ColorObject is generic. A Generic ColorObject,
     * as defined in this method, is a ColorObject which has the same value for R, G and B.
     * 
     * @param colorObj : A ColorObject to be check.
     * @param color : The value of the color being checked for the RGB.
     * @return boolean: If true, the ColorObject will be considered generic.
     */
    public static boolean isGeneric (ColorObject colorObj, float color) {
    
        return (colorObj.red >= color && colorObj.blue >= color && colorObj.green >= color);
    }
    
    /**
     * Creates a copy of the provided ColorObject, useful when you don't want to mess up
     * existing instances.
     * 
     * @param colorObj : A ColorObject to be cloned.
     * @return ColorObject: A clone of the provided ColorObject;
     */
    public static ColorObject clone (ColorObject colorObj) {
    
        ColorObject clone = new ColorObject(false);
        clone.red = colorObj.red;
        clone.green = colorObj.green;
        clone.blue = colorObj.blue;
        clone.alpha = colorObj.alpha;
        
        return clone;
    }
    
    /**
     * Merges two ColorObject together. The first object represents the primary colors, while
     * the second represents the alpha.
     * 
     * @param primary : The primary colors, only the RGB of this will be kept.
     * @param alpha : The alpha layer, only the alpha value of this color will be kept.
     * @return ColorObject: A new ColorObject which has the RGB of the primary color, and the A
     *         of the alpha color.
     */
    public static ColorObject mergeColors (ColorObject primary, ColorObject alpha) {
    
        ColorObject colorObj = primary;
        
        colorObj.alpha = alpha.alpha;
        return clone(colorObj);
    }
}