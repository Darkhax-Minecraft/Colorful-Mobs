package net.darkhax.colorfulmobs.client.gui;

import net.darkhax.bookshelf.objects.ColorObject;
import net.darkhax.colorfulmobs.ColorfulMobs;
import net.darkhax.colorfulmobs.common.ColorProperties;
import net.darkhax.colorfulmobs.common.PacketColorSync;
import net.darkhax.colorfulmobs.lib.Constants;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
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

    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(new ResourceLocation(Constants.MOD_ID + ":textures/gui/color.png"));
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);

        GL11.glColor4f(0.0F, 0.0F, 0.0F, 1.0F);
        this.drawTexturedModalRect(k + 32, l + 30, 20, 20, 110, 115);

        if (entity != null)
            drawEntityOnScreen(k + 87, l + 130, 40, (float) (k + 43 - p_146976_2_), (float) (l + 45 - 30 - p_146976_3_), entity);
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
        drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        super.drawScreen(mouseX, mouseY, partialTicks);

        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        drawString(this.fontRendererObj, "Red", k + 21, l + 150, 0xffffff);
        drawString(this.fontRendererObj, "Green", k + 54, l + 150, 0xffffff);
        drawString(this.fontRendererObj, "Blue", k + 96, l + 150, 0xffffff);
        drawString(this.fontRendererObj, "Alpha", k + 129, l + 150, 0xffffff);

        drawCenteredString(fontRendererObj, "Color Menu", k + 87, l + 15, 0xffffff);
        this.textR.drawTextBox();
        this.textG.drawTextBox();
        this.textB.drawTextBox();
        this.textA.drawTextBox();
    }

    public static void drawEntityOnScreen(int p_147046_0_, int p_147046_1_, int p_147046_2_, float p_147046_3_, float p_147046_4_, EntityLivingBase p_147046_5_) {
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        GL11.glPushMatrix();
        GL11.glTranslatef((float) p_147046_0_, (float) p_147046_1_, 50.0F);
        GL11.glScalef((float) (-p_147046_2_), (float) p_147046_2_, (float) p_147046_2_);
        GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
        float f2 = p_147046_5_.renderYawOffset;
        float f3 = p_147046_5_.rotationYaw;
        float f4 = p_147046_5_.rotationPitch;
        float f5 = p_147046_5_.prevRotationYawHead;
        float f6 = p_147046_5_.rotationYawHead;
        GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-((float) Math.atan((double) (p_147046_4_ / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
        p_147046_5_.renderYawOffset = (float) Math.atan((double) (p_147046_3_ / 40.0F)) * 20.0F;
        p_147046_5_.rotationYaw = (float) Math.atan((double) (p_147046_3_ / 40.0F)) * 40.0F;
        p_147046_5_.rotationPitch = -((float) Math.atan((double) (p_147046_4_ / 40.0F))) * 20.0F;
        p_147046_5_.rotationYawHead = p_147046_5_.rotationYaw;
        p_147046_5_.prevRotationYawHead = p_147046_5_.rotationYaw;
        GL11.glTranslatef(0.0F, p_147046_5_.yOffset, 0.0F);
        RenderManager.instance.playerViewY = 180.0F;
        RenderManager.instance.renderEntityWithPosYaw(p_147046_5_, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
        p_147046_5_.renderYawOffset = f2;
        p_147046_5_.rotationYaw = f3;
        p_147046_5_.rotationPitch = f4;
        p_147046_5_.prevRotationYawHead = f5;
        p_147046_5_.rotationYawHead = f6;
        GL11.glPopMatrix();
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }

    @Override
    protected void actionPerformed(GuiButton button) {

        // if
        if (button.id == 1) {
            if (entity != null) {

                String color = textR.getText();
                if (color != null && !color.equals("")) {
                    r = Integer.parseInt(color);
                }
                color = textG.getText();
                if (color != null && !color.equals("")) {
                    g = Integer.parseInt(color);
                }
                color = textB.getText();
                if (color != null && !color.equals("")) {
                    b = Integer.parseInt(color);
                }
                color = textA.getText();
                if (color != null && !color.equals("")) {
                    a = Integer.parseInt(color);
                }

                ColorObject colorObj = new ColorObject(r, g, b, a);
                ColorProperties.setEntityColors(colorObj, entity);

                // send packet
                ColorfulMobs.instance.network.sendToAll(new PacketColorSync(colorObj, entity));
                // clear text boxes
                this.textR.setText("");
                this.textG.setText("");
                this.textB.setText("");
                this.textA.setText("");
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
        if (textR.isFocused()) {
            boolean x = addText(textR, typedChar, keyCode);
        } else if (textG.isFocused()) {
            addText(textG, typedChar, keyCode);
        } else if (textB.isFocused()) {
            addText(textB, typedChar, keyCode);
        } else if (textA.isFocused()) {
            addText(textA, typedChar, keyCode);
        } else {


        }
    }

    private boolean addText(GuiTextField text, char typedChar, int keyCode) {
        if (typedChar >= '0' && typedChar <= '9') {
            if ((text.getText() == null || text.getText().length() == 0) && typedChar >= '0' && typedChar <= '2') {
                text.textboxKeyTyped(typedChar, keyCode);
                return true;
            } else if (text.getText().length() > 0 && typedChar >= '0' && typedChar <= '5') {
                text.textboxKeyTyped(typedChar, keyCode);
                return true;
            }
        } else {
            switch (keyCode) {
                case 14:
                    if (GuiScreen.isCtrlKeyDown()) {
                        text.deleteWords(-1);
                    } else {
                        text.deleteFromCursor(-1);
                    }

                    return true;
                case 199:
                    if (GuiScreen.isShiftKeyDown()) {
                        text.setSelectionPos(0);
                    } else {
                        text.setCursorPositionZero();
                    }

                    return true;
                case 203:
                    if (GuiScreen.isShiftKeyDown()) {
                        if (GuiScreen.isCtrlKeyDown()) {
                            text.setSelectionPos(text.getNthWordFromPos(-1, text.getSelectionEnd()));
                        } else {
                            text.setSelectionPos(text.getSelectionEnd() - 1);
                        }
                    } else if (GuiScreen.isCtrlKeyDown()) {
                        text.setCursorPosition(text.getNthWordFromCursor(-1));
                    } else {
                        text.moveCursorBy(-1);
                    }

                    return true;
                case 205:
                    if (GuiScreen.isShiftKeyDown()) {
                        if (GuiScreen.isCtrlKeyDown()) {
                            text.setSelectionPos(text.getNthWordFromPos(1, text.getSelectionEnd()));
                        } else {
                            text.setSelectionPos(text.getSelectionEnd() + 1);
                        }
                    } else if (GuiScreen.isCtrlKeyDown()) {
                        text.setCursorPosition(text.getNthWordFromCursor(1));
                    } else {
                        text.moveCursorBy(1);
                    }

                    return true;
                case 207:
                    if (GuiScreen.isShiftKeyDown()) {
                        text.setSelectionPos(text.getText().length());
                    } else {
                        text.setCursorPositionEnd();
                    }

                    return true;
                case 211:
                    if (GuiScreen.isCtrlKeyDown()) {
                        text.deleteWords(1);
                    } else {
                        text.deleteFromCursor(1);
                    }

                    return true;
            }
        }
        return false;
    }
}
