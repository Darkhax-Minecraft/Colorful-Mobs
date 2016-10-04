package net.epoxide.colorfulmobs.lib;

import java.awt.Color;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;

public class ColorObject {
    
    private float red;
    private float green;
    private float blue;
    private float alpha;
    
    public ColorObject(boolean alpha) {
        
        this(Constants.RANDOM.nextFloat(), Constants.RANDOM.nextFloat(), Constants.RANDOM.nextFloat(), (alpha) ? Constants.RANDOM.nextFloat() : 1f);
    }
    
    public ColorObject (ItemStack stack) {
        
        this (stack.getTagCompound());
    }
    
    public ColorObject(NBTTagCompound tag) {
        
        this(tag.getFloat("red"), tag.getFloat("green"), tag.getFloat("blue"), (tag.hasKey("alpha") ? tag.getInteger("alpha") : 1f));
    }
    
    public ColorObject(ByteBuf buf) {
        
        this(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat());
    }
    
    public ColorObject(Color color) {
        
        this(color.getRed(), color.getGreen(), color.getBlue(), 255);
    }
    
    public ColorObject(int red, int green, int blue, int alpha) {
        
        this((float) red / 255f, (float) green / 255f, (float) blue / 255f, (float) alpha / 255f);
    }
    
    public ColorObject(float red, float green, float blue) {
        
        this(red, green, blue, 1f);
    }
    
    public ColorObject(float red, float green, float blue, float alpha) {
        
        this.setRed(red);
        this.setGreen(green);
        this.setBlue(blue);
        this.setAlpha(alpha);
    }
    
    public void write (ItemStack stack) {
        
        if (!stack.hasTagCompound())
            stack.setTagCompound(new NBTTagCompound());
        
        this.write(stack.getTagCompound());
    }
    
    public void write (NBTTagCompound tag) {
        
        tag.setFloat("red", this.red);
        tag.setFloat("green", this.green);
        tag.setFloat("blue", this.blue);
        tag.setFloat("alpha", this.alpha);
    }
    
    public void write (ByteBuf buf) {
        
        buf.writeFloat(this.red);
        buf.writeFloat(this.green);
        buf.writeFloat(this.blue);
        buf.writeFloat(this.alpha);
    }
    
    public float getBlue () {
        
        return blue;
    }
    
    public void setBlue (float blue) {
        
        this.blue = blue;
    }
    
    public float getAlpha () {
        
        return alpha;
    }
    
    public void setAlpha (float alpha) {
        
        this.alpha = alpha;
    }
    
    public float getGreen () {
        
        return green;
    }
    
    public void setGreen (float green) {
        
        this.green = green;
    }
    
    public float getRed () {
        
        return red;
    }
    
    public void setRed (float red) {
        
        this.red = red;
    }
    
    public boolean isWhite () {
        
        return red == 1f && green == 1f && blue == 1f;
    }
    
    public Color toAWTColor() {
        
        return new Color((int) (this.red * 255f), (int) (this.green * 255f), (int) (this.blue * 255f));
    }
    
    @Override
    public String toString () {
        
        String output = TextFormatting.RED + "" + (int) (this.red * 255) + " " + TextFormatting.GREEN + (int) (this.getGreen() * 255) + " " + TextFormatting.BLUE + (int) (this.blue * 255);
        
        if (this.alpha < 1.0f)
            output += " " + TextFormatting.GRAY + (int) (100 - this.alpha * 100);
        
        return output;
    }
}
