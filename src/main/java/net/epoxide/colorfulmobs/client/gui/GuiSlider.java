package net.epoxide.colorfulmobs.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiSlider extends GuiButton {
    private float currentValue;
    public boolean field_146135_o;
    private final float valueMin;
    private final float valueMax;
    private final String color;
    private final float valueStep = 1;
    private GuiColorSelection gui;

    public GuiSlider(int id, int xPos, int yPos, float valueMin, float valueMax, float currentValue, String color, GuiColorSelection gui) {
        super(id, xPos, yPos, 55, 20, "");
        this.currentValue = 1.0F;
        this.valueMin = valueMin;
        this.valueMax = valueMax;
        this.currentValue = normalizeValue(currentValue);
        this.color = color;
        this.displayString = color + ": " + (int) currentValue;

        this.gui = gui;
    }

    @Override
    public int getHoverState(boolean p_146114_1_) {
        return 0;
    }

    @Override
    protected void mouseDragged(Minecraft p_146119_1_, int p_146119_2_, int p_146119_3_) {
        if (this.visible) {
            if (this.field_146135_o) {
                this.currentValue = (float) (p_146119_2_ - (this.xPosition + 4)) / (float) (this.width - 8);

                if (this.currentValue < 0.0F) {
                    this.currentValue = 0.0F;
                }

                if (this.currentValue > 1.0F) {
                    this.currentValue = 1.0F;
                }

                float f = denormalizeValue(this.currentValue);
                currentValue = f;
                this.currentValue = normalizeValue(f);
                this.displayString = color + ": " + (int) f;

                gui.updateColor();
            }

            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.drawTexturedModalRect(this.xPosition + (int) (this.currentValue * (float) (this.width - 8)), this.yPosition, 0, 66, 4, 20);
            this.drawTexturedModalRect(this.xPosition + (int) (this.currentValue * (float) (this.width - 8)) + 4, this.yPosition, 196, 66, 4, 20);
        }
    }

    @Override
    public boolean mousePressed(Minecraft p_146116_1_, int p_146116_2_, int p_146116_3_) {
        if (super.mousePressed(p_146116_1_, p_146116_2_, p_146116_3_)) {
            this.currentValue = (float) (p_146116_2_ - (this.xPosition + 4)) / (float) (this.width - 8);

            if (this.currentValue < 0.0F) {
                this.currentValue = 0.0F;
            }

            if (this.currentValue > 1.0F) {
                this.currentValue = 1.0F;
            }
            this.displayString = color + ": " + (int) denormalizeValue(this.currentValue);
            this.field_146135_o = true;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void mouseReleased(int p_146118_1_, int p_146118_2_) {
        this.field_146135_o = false;
        gui.updateColor();
    }

    public void setValue(float value) {
        this.currentValue += value;
    }

    public float getValue() {
        return this.currentValue;
    }

    public float normalizeValue(float value) {
        return MathHelper.clamp_float((this.snapToStepClamp(value) - this.valueMin) / (this.valueMax - this.valueMin), 0.0F, 1.0F);
    }

    public float denormalizeValue(float value) {
        return this.snapToStepClamp(this.valueMin + (this.valueMax - this.valueMin) * MathHelper.clamp_float(value, 0.0F, 1.0F));
    }

    private float snapToStepClamp(float value) {
        value = this.snapToStep(value);
        return MathHelper.clamp_float(value, this.valueMin, this.valueMax);
    }

    private float snapToStep(float value) {
        if (this.valueStep > 0.0F) {
            value = this.valueStep * (float) Math.round(value / this.valueStep);
        }

        return value;
    }
}