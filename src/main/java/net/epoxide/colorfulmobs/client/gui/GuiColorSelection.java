package net.epoxide.colorfulmobs.client.gui;

import net.darkhax.bookshelf.objects.ColorObject;
import net.epoxide.colorfulmobs.ColorfulMobs;
import net.epoxide.colorfulmobs.client.EntityUtil;
import net.epoxide.colorfulmobs.common.ColorProperties;
import net.epoxide.colorfulmobs.common.PacketColorSync;
import net.epoxide.colorfulmobs.lib.Constants;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class GuiColorSelection extends GuiScreen {

    private GuiTextField textR, textG, textB, textA;
    protected final int xSize = 177;
    protected final int ySize = 222;

    private EntityLivingBase entity;
    private int r = 255, g = 255, b = 255, a = 255;

    public GuiColorSelection(EntityLivingBase entity) {

        this.entity = entity;
    }

    protected void drawGuiContainerBackgroundLayer() {

        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(new ResourceLocation(Constants.MOD_ID + ":textures/gui/color.png"));
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);

        GL11.glColor4f(0.0F, 0.0F, 0.0F, 1.0F);

        this.drawTexturedModalRect(k + 32, l + 30, 20, 20, 110, 115);

        if (entity != null)
            drawEntityOnScreen(k + 87, l + 130, 40, entity);
    }

    @Override
    public void initGui() {

        super.initGui();

        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;

        buttonList.add(new GuiButton(1, k + 63, l + 190, 50, 20, "Confirm"));

        if (ColorProperties.hasColorProperties(entity)) {

            ColorObject obj = ColorProperties.getPropsFromEntity(entity).colorObj;
            r = (int) (obj.red * 255);
            g = (int) (obj.green * 255);
            b = (int) (obj.blue * 255);
            a = (int) (obj.alpha * 255);
        }

        this.textR = new GuiTextField(fontRendererObj, k + 17, l + 160, 30, 10);
        this.textR.setMaxStringLength(3);
        this.textR.setTextColor(16777215);
        this.textR.setText(String.valueOf(r));

        this.textG = new GuiTextField(fontRendererObj, k + 54, l + 160, 30, 10);
        this.textG.setMaxStringLength(3);
        this.textG.setTextColor(16777215);
        this.textG.setText(String.valueOf(g));

        this.textB = new GuiTextField(fontRendererObj, k + 91, l + 160, 30, 10);
        this.textB.setMaxStringLength(3);
        this.textB.setTextColor(16777215);
        this.textB.setText(String.valueOf(b));

        this.textA = new GuiTextField(fontRendererObj, k + 128, l + 160, 30, 10);
        this.textA.setMaxStringLength(3);
        this.textA.setTextColor(16777215);
        this.textA.setText(String.valueOf(a));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        drawGuiContainerBackgroundLayer();
        super.drawScreen(mouseX, mouseY, partialTicks);

        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        drawString(this.fontRendererObj, EnumChatFormatting.RED + StatCollector.translateToLocal("chat.colorfulmobs.red"), k + 21, l + 150, 0xffffff);
        drawString(this.fontRendererObj, EnumChatFormatting.GREEN + StatCollector.translateToLocal("chat.colorfulmobs.green"), k + 54, l + 150, 0xffffff);
        drawString(this.fontRendererObj, EnumChatFormatting.BLUE + StatCollector.translateToLocal("chat.colorfulmobs.blue"), k + 96, l + 150, 0xffffff);
        drawString(this.fontRendererObj, StatCollector.translateToLocal("chat.colorfulmobs.alpha"), k + 129, l + 150, 0xffffff);

        drawCenteredString(fontRendererObj, StatCollector.translateToLocal("chat.colorfulmobs.colormenu"), k + 87, l + 15, 0xffffff);

        this.textR.drawTextBox();
        this.textG.drawTextBox();
        this.textB.drawTextBox();
        this.textA.drawTextBox();
    }

    float zz = 0;

    public void drawEntityOnScreen(int x, int y, float scale, EntityLivingBase entity) {
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glPushMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        GL11.glTranslatef(x, y, 200);
        GL11.glScalef((-scale), scale, scale);
        GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
        GL11.glRotatef(zz, 0.0F, 1.0F, 0.0F);
        EntityUtil.renderEntityWithPosYaw(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
        GL11.glPopMatrix();
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);

        zz -= 1.25F;
    }

    @Override
    protected void actionPerformed(GuiButton button) {

        if (button.id == 1) {

            if (entity != null) {

                // send packet
                updateColor();
                PacketColorSync packetColorSync = new PacketColorSync(new ColorObject(r, g, b, a), entity);
                ColorfulMobs.network.sendToServer(packetColorSync);
            }

            // close gui
            this.mc.displayGuiScreen(null);
            this.mc.setIngameFocus();
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {

        super.mouseClicked(mouseX, mouseY, mouseButton);
        textR.mouseClicked(mouseX, mouseY, mouseButton);
        textG.mouseClicked(mouseX, mouseY, mouseButton);
        textB.mouseClicked(mouseX, mouseY, mouseButton);
        textA.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {

        super.keyTyped(typedChar, keyCode);

        if (textR.isFocused())
            addText(textR, typedChar, keyCode);

        else if (textG.isFocused())
            addText(textG, typedChar, keyCode);

        else if (textB.isFocused())
            addText(textB, typedChar, keyCode);

        else if (textA.isFocused())
            addText(textA, typedChar, keyCode);
    }

    /**
     * Updates all the color text and values.
     */
    public void updateColor() {

        String color = textR.getText();
        if (color != null && !color.equals(""))
            r = Integer.parseInt(color);

        color = textG.getText();
        if (color != null && !color.equals(""))
            g = Integer.parseInt(color);

        color = textB.getText();
        if (color != null && !color.equals(""))
            b = Integer.parseInt(color);

        color = textA.getText();
        if (color != null && !color.equals(""))
            a = Integer.parseInt(color);

        ColorProperties.setEntityColors(new ColorObject(r, g, b, a), entity);

    }

    /**
     * Adds a character to a textField. Used for typing the RGBA values.
     * 
     * @param textField: The GUITextField being added to.
     * @param typedChar: The character which is being added to the text field.
     * @param keyCode: A numeric value which represents the key being typed.
     * @return boolean: Will return true if the character was successfully added.
     */
    private boolean addText(GuiTextField textField, char typedChar, int keyCode) {

        if (typedChar >= '0' && typedChar <= '9') {

            textField.textboxKeyTyped(typedChar, keyCode);

            if (Integer.parseInt(textField.getText()) <= 255) {

                updateColor();
                return true;
            } else {

                textField.setText("255");
                updateColor();
                return true;
            }
        } else {

            switch (keyCode) {

            case 14:
                if (GuiScreen.isCtrlKeyDown()) {

                    textField.deleteWords(-1);
                    updateColor();
                } else {

                    textField.deleteFromCursor(-1);
                    updateColor();
                }

                return true;

            case 199:
                if (GuiScreen.isShiftKeyDown())
                    textField.setSelectionPos(0);

                else
                    textField.setCursorPositionZero();

                return true;

            case 203:
                if (GuiScreen.isShiftKeyDown()) {

                    if (GuiScreen.isCtrlKeyDown())
                        textField.setSelectionPos(textField.getNthWordFromPos(-1, textField.getSelectionEnd()));

                    else
                        textField.setSelectionPos(textField.getSelectionEnd() - 1);
                } else if (GuiScreen.isCtrlKeyDown())
                    textField.setCursorPosition(textField.getNthWordFromCursor(-1));

                else
                    textField.moveCursorBy(-1);

                return true;

            case 205:
                if (GuiScreen.isShiftKeyDown()) {

                    if (GuiScreen.isCtrlKeyDown())
                        textField.setSelectionPos(textField.getNthWordFromPos(1, textField.getSelectionEnd()));

                    else
                        textField.setSelectionPos(textField.getSelectionEnd() + 1);
                } else if (GuiScreen.isCtrlKeyDown())
                    textField.setCursorPosition(textField.getNthWordFromCursor(1));

                else
                    textField.moveCursorBy(1);

                return true;

            case 207:
                if (GuiScreen.isShiftKeyDown())
                    textField.setSelectionPos(textField.getText().length());

                else
                    textField.setCursorPositionEnd();

                return true;

            case 211:
                if (GuiScreen.isCtrlKeyDown()) {

                    textField.deleteWords(1);
                    updateColor();
                } else {

                    textField.deleteFromCursor(1);
                    updateColor();
                }

                return true;
            }
        }

        return false;
    }
}