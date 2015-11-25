package net.epoxide.colorfulmobs.client.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

import net.darkhax.bookshelf.lib.util.MathsUtils;

public class GuiSlider extends GuiButton {
    
    private String sliderName;
    private float sliderValue;
    private boolean isDragging;
    private float minimum = 0.0f;
    private float maximum = 1.0f;
    private float valueStep;
    private boolean repAsInt;
    private int repValue;
    private boolean shouldInvert;
    
    public GuiSlider(int id, String title, float initialValue, int xPos, int yPos, boolean repAsInt, int repValue) {
        
        this(id, title, initialValue, xPos, yPos, repAsInt, repValue, false);
    }
    
    public GuiSlider(int id, String title, float initialValue, int xPos, int yPos, boolean repAsInt, int intValue, boolean invert) {
        
        super(id, xPos, yPos, 55, 20, "");
        sliderName = title;
        this.valueStep = intValue / 55;
        this.setSliderValue(initialValue);
        this.repAsInt = repAsInt;
        this.repValue = intValue;
        this.shouldInvert = invert;
    }
    
    @Override
    protected void mouseDragged (Minecraft mc, int mouseX, int mouseY) {
        
        if (this.visible) {
            
            if (this.isDragging) {
                
                this.sliderValue = (float) (mouseX - (this.xPosition + 4)) / (float) (this.width - 8);
                this.sliderValue = (this.sliderValue < 0f) ? 0f : (this.sliderValue > 1f) ? 1f : this.sliderValue;
            }
            
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.drawTexturedModalRect(this.xPosition + (int) (this.sliderValue * (float) (this.width - 8)), this.yPosition, 0, 66, 4, 20);
            this.drawTexturedModalRect(this.xPosition + (int) (this.sliderValue * (float) (this.width - 8)) + 4, this.yPosition, 196, 66, 4, 20);
            
            this.updateDisplay();
        }
    }
    
    @Override
    public boolean mousePressed (Minecraft mc, int mouseX, int mouseY) {
        
        if (super.mousePressed(mc, mouseX, mouseY)) {
            
            this.sliderValue = (float) (mouseX - (this.xPosition + 4)) / (float) (this.width - 8);
            this.sliderValue = (this.sliderValue < 0f) ? 0f : (this.sliderValue > 1f) ? 1f : this.sliderValue;
            this.isDragging = true;
            this.updateDisplay();
            
            return true;
        }
        
        else
            return false;
    }
    
    @Override
    public void mouseReleased (int mouseX, int mouseY) {
        
        this.isDragging = false;
        this.updateDisplay();
    }
    
    /**
     * Sets the value of the slider.
     *
     * @param value: The value to set.
     */
    public void setSliderValue (float value) {
        
        this.sliderValue = value / this.maximum;
        updateDisplay();
    }
    
    /**
     * Retrieves the value of the slider.
     *
     * @return
     */
    public float getSliderValue () {
        
        return this.sliderValue * this.maximum;
    }
    
    @Override
    public int getHoverState (boolean p_146114_1_) {
        
        return 0;
    }
    
    /**
     * Basic method for updating the display. If repAsInt is true, value will be an int. If
     * not, it will be the actual value rounded to 2 decimal places.
     */
    private void updateDisplay () {
        
        String value = (this.repAsInt) ? (this.shouldInvert) ? this.repValue - (int) (this.getSliderValue() * this.repValue) + "%" : "" + (int) (this.getSliderValue() * this.repValue) : "" + MathsUtils.round(this.getSliderValue(), 2);
        this.displayString = this.sliderName + ": " + value;
    }
}
