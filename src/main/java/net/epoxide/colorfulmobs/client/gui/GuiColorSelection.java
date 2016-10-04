package net.epoxide.colorfulmobs.client.gui;

import org.lwjgl.opengl.GL11;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.darkhax.bookshelf.client.gui.GuiSlider;
import net.darkhax.bookshelf.lib.util.RenderUtils;
import net.epoxide.colorfulmobs.ColorfulMobs;
import net.epoxide.colorfulmobs.common.ColorProperties;
import net.epoxide.colorfulmobs.common.ColorProperties.IColorHolder;
import net.epoxide.colorfulmobs.common.network.PacketSyncColor;
import net.epoxide.colorfulmobs.lib.ColorObject;
import net.epoxide.colorfulmobs.lib.Constants;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class GuiColorSelection extends GuiScreenBase {
    
    private GuiSlider sliderRed;
    private GuiSlider sliderGreen;
    private GuiSlider sliderBlue;
    private GuiSlider sliderAlpha;
    
    protected final int xSize = 177;
    protected final int ySize = 222;
    
    private EntityLivingBase entity;
    private EntityLivingBase tempEntity;
    
    private IColorHolder baseProps;
    private IColorHolder tempProps;
    
    private ColorObject initialColor;
    
    public GuiColorSelection(EntityLivingBase entity) {
        
        this.entity = entity;
        this.tempEntity = (EntityLivingBase) EntityList.createEntityByName(EntityList.getEntityString(entity), entity.worldObj);
        
        if (this.tempEntity == null)
            this.tempEntity = entity;
        
        NBTTagCompound compound = new NBTTagCompound();
        entity.writeEntityToNBT(compound);
        tempEntity.readEntityFromNBT(compound);
        this.baseProps = ColorProperties.getProperties(entity);
        this.tempProps = ColorProperties.getProperties(tempEntity);
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer () {
        
        GlStateManager.pushMatrix();
        ColorObject currentColor = new ColorObject(sliderRed.getSliderValue(), sliderGreen.getSliderValue(), sliderBlue.getSliderValue(), sliderAlpha.getSliderValue());
        tempProps.setColor(currentColor);
        
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(new ResourceLocation(Constants.MOD_ID + ":textures/gui/color.png"));
        this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
        GlStateManager.color(0.0F, 0.0F, 0.0F, 1.0F);
        this.drawTexturedModalRect(x + 32, y + 20, 20, 20, 110, 115);
        GlStateManager.popMatrix();
        
        if (tempEntity != null)
            drawEntityOnScreen(x + 87, y + 120, 40, tempEntity);
    }
    
    @Override
    public void initGui () {
        
        super.initGui();
        
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        
        buttonList.add(new GuiButton(0, k + 63, l + 190, 50, 20, "Confirm"));
        
        if (ColorProperties.hasProperties(entity)) {
            
            this.initialColor = baseProps.getColor();
            tempProps.setColor(this.initialColor);
        }
        
        this.sliderRed = new GuiSlider(1, ChatFormatting.RED + I18n.format("chat.colorfulmobs.red"), initialColor.getRed(), k + 25, l + 140, true, 255);
        buttonList.add(this.sliderRed);
        
        this.sliderGreen = new GuiSlider(2, ChatFormatting.GREEN + I18n.format("chat.colorfulmobs.green"), initialColor.getGreen(), k + 95, l + 140, true, 255);
        buttonList.add(this.sliderGreen);
        
        this.sliderBlue = new GuiSlider(3, ChatFormatting.BLUE + I18n.format("chat.colorfulmobs.blue"), initialColor.getBlue(), k + 25, l + 165, true, 255);
        buttonList.add(this.sliderBlue);
        
        this.sliderAlpha = new GuiSlider(4, ChatFormatting.WHITE + I18n.format("chat.colorfulmobs.alpha"), initialColor.getAlpha(), k + 95, l + 165, true, 100, true);
        buttonList.add(this.sliderAlpha);
    }
    
    @Override
    public void drawScreen (int mouseX, int mouseY, float partialTicks) {
        
        drawGuiContainerBackgroundLayer();
        super.drawScreen(mouseX, mouseY, partialTicks);
        drawCenteredString(fontRendererObj, I18n.format("chat.colorfulmobs.colormenu"), xSize / 2, 10, 0xffffff);
    }
    
    float rotation = 0;
    
    public void drawEntityOnScreen (int x, int y, float scale, EntityLivingBase entity) {
        
        GlStateManager.pushMatrix();
        GlStateManager.disableBlend();
        GlStateManager.color(1f, 1f, 1f, 1f);
        GlStateManager.translate(x, y, 200);
        GlStateManager.scale((-scale), scale, scale);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(rotation, 0.0F, 1.0F, 0.0F);
        RenderUtils.renderEntity(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GlStateManager.popMatrix();
        rotation -= 1.25F;
    }
    
    @Override
    protected void actionPerformed (GuiButton button) {
        
        ColorObject currentColor = new ColorObject(sliderRed.getSliderValue(), sliderGreen.getSliderValue(), sliderBlue.getSliderValue(), sliderAlpha.getSliderValue());
        if (button.id == 0) {
            
            if (entity != null) {
                
                PacketSyncColor packetColorSync = new PacketSyncColor(currentColor, entity, false);
                ColorfulMobs.network.sendToServer(packetColorSync);
            }
            
            this.mc.displayGuiScreen(null);
            this.mc.setIngameFocus();
        }
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer () {
        
    }
    
    @Override
    protected String getTooltipForButton (int buttonId) {
        
        switch (buttonId) {
            
            case 0:
                return I18n.format("tooltip.colorfulmobs.selection.confirm");
            case 1:
                return I18n.format("tooltip.colorfulmobs.selection.red");
            case 2:
                return I18n.format("tooltip.colorfulmobs.selection.green");
            case 3:
                return I18n.format("tooltip.colorfulmobs.selection.blue");
            case 4:
                return I18n.format("tooltip.colorfulmobs.selection.alpha");
            
            default:
                return I18n.format("tooltip.colorfulmobs.selection.default");
        }
    }
    
    @Override
    protected String getTooltipForMisc (int mouseX, int mouseY) {
        
        return null;
    }
    
}