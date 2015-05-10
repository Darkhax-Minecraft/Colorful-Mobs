package net.epoxide.colorfulmobs.client.gui;

import net.epoxide.colorfulmobs.lib.Utilities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

import org.lwjgl.opengl.GL11;

public class GuiSlider extends GuiButton {
    
    private String sliderName;
    private float sliderValue;
    private boolean isDragging;
    private float minimum;
    private float maximum;
    private float valueStep;
    private boolean repAsInt;
    private int intValue;
    
    public GuiSlider(int id, String title, float initialValue, int xPos, int yPos, boolean repAsInt, int intValue) {
    
        super(id, xPos, yPos, 55, 20, "");
        sliderName = title;
        this.minimum = 0.0f;
        this.maximum = 1.0f;
        this.valueStep = intValue / 55;
        this.setSliderValue(initialValue);
        this.repAsInt = repAsInt;
        this.intValue = intValue;
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
    
        this.sliderValue = (float) value / this.maximum;
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
    
        String value = (this.repAsInt) ? "" + (int) (this.getSliderValue() * this.intValue) : "" + Utilities.round(this.getSliderValue(), 2);
        this.displayString = this.sliderName + ": " + value;
    }
}
