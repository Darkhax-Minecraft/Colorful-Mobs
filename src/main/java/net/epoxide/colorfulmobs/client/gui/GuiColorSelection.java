package net.epoxide.colorfulmobs.client.gui;

import net.epoxide.colorfulmobs.ColorfulMobs;
import net.epoxide.colorfulmobs.client.EntityUtil;
import net.epoxide.colorfulmobs.common.ColorProperties;
import net.epoxide.colorfulmobs.common.PacketColorSync;
import net.epoxide.colorfulmobs.lib.ColorObject;
import net.epoxide.colorfulmobs.lib.Constants;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class GuiColorSelection extends GuiScreenBase {
    
    private GuiSlider sliderRed;
    private GuiSlider sliderGreen;
    private GuiSlider sliderBlue;
    private GuiSlider sliderAlpha;
    
    protected final int xSize = 177;
    protected final int ySize = 222;
    
    private EntityLivingBase entity;
    private EntityLivingBase tempEntity;
    
    private ColorProperties baseProps;
    private ColorProperties tempProps;
    
    private ColorObject initialColor;
    
    public GuiColorSelection(EntityLivingBase entity) {
    
        this.entity = entity;
        this.tempEntity = (EntityLivingBase) EntityList.createEntityByName(EntityList.getEntityString(entity), entity.worldObj);
        
        if (this.tempEntity == null)
            this.tempEntity = entity;
        
        NBTTagCompound compound = new NBTTagCompound();
        entity.writeEntityToNBT(compound);
        tempEntity.readEntityFromNBT(compound);
        this.baseProps = ColorProperties.getPropsFromEntity(entity);
        this.tempProps = ColorProperties.getPropsFromEntity(tempEntity);
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer () {
    
        ColorObject currentColor = new ColorObject(sliderRed.getSliderValue(), sliderGreen.getSliderValue(), sliderBlue.getSliderValue(), sliderAlpha.getSliderValue());
        tempProps.setColorObject(currentColor);
        
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(new ResourceLocation(Constants.MOD_ID + ":textures/gui/color.png"));
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        GL11.glColor4f(0.0F, 0.0F, 0.0F, 1.0F);
        this.drawTexturedModalRect(k + 32, l + 20, 20, 20, 110, 115);
        
        if (tempEntity != null)
            drawEntityOnScreen(k + 87, l + 120, 40, tempEntity);
    }
    
    @Override
    public void initGui () {
    
        super.initGui();
        
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        
        buttonList.add(new GuiButton(0, k + 63, l + 190, 50, 20, "Confirm"));
        
        if (ColorProperties.hasColorProperties(entity)) {
            
            this.initialColor = baseProps.getColorObj();
            tempProps.setColorObject(this.initialColor);
        }
        
        this.sliderRed = new GuiSlider(1, EnumChatFormatting.RED + StatCollector.translateToLocal("chat.colorfulmobs.red"), initialColor.getRed(), k + 25, l + 140, true, 255);
        buttonList.add(this.sliderRed);
        
        this.sliderGreen = new GuiSlider(2, EnumChatFormatting.GREEN + StatCollector.translateToLocal("chat.colorfulmobs.green"), initialColor.getGreen(), k + 95, l + 140, true, 255);
        buttonList.add(this.sliderGreen);
        
        this.sliderBlue = new GuiSlider(3, EnumChatFormatting.BLUE + StatCollector.translateToLocal("chat.colorfulmobs.blue"), initialColor.getBlue(), k + 25, l + 165, true, 255);
        buttonList.add(this.sliderBlue);
        
        this.sliderAlpha = new GuiSlider(4, EnumChatFormatting.WHITE + StatCollector.translateToLocal("chat.colorfulmobs.alpha"), initialColor.getAlpha(), k + 95, l + 165, true, 100, true);
        buttonList.add(this.sliderAlpha);
    }
    
    @Override
    public void drawScreen (int mouseX, int mouseY, float partialTicks) {
    
        drawGuiContainerBackgroundLayer();
        super.drawScreen(mouseX, mouseY, partialTicks);
        
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        drawCenteredString(fontRendererObj, StatCollector.translateToLocal("chat.colorfulmobs.colormenu"), xSize / 2, 10, 0xffffff);
    }
    
    float rotation = 0;
    
    public void drawEntityOnScreen (int x, int y, float scale, EntityLivingBase entity) {
    
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glPushMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        GL11.glTranslatef(x, y, 200);
        GL11.glScalef((-scale), scale, scale);
        GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
        GL11.glRotatef(rotation, 0.0F, 1.0F, 0.0F);
        EntityUtil.renderEntityWithPosYaw(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
        GL11.glPopMatrix();
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
        
        rotation -= 1.25F;
    }
    
    @Override
    protected void actionPerformed (GuiButton button) {
    
        ColorObject currentColor = new ColorObject(sliderRed.getSliderValue(), sliderGreen.getSliderValue(), sliderBlue.getSliderValue(), sliderAlpha.getSliderValue());
        if (button.id == 0) {
            
            if (entity != null) {
                
                PacketColorSync packetColorSync = new PacketColorSync(currentColor, entity);
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
                return StatCollector.translateToLocal("tooltip.colorfulmobs.selection.confirm");
            case 1:
                return StatCollector.translateToLocal("tooltip.colorfulmobs.selection.red");
            case 2:
                return StatCollector.translateToLocal("tooltip.colorfulmobs.selection.green");
            case 3:
                return StatCollector.translateToLocal("tooltip.colorfulmobs.selection.blue");
            case 4:
                return StatCollector.translateToLocal("tooltip.colorfulmobs.selection.alpha");
                
            default:
                return StatCollector.translateToLocal("tooltip.colorfulmobs.selection.default");
        }
    }
    
    @Override
    protected String getTooltipForMisc (int mouseX, int mouseY) {
    
        return null;
    }
    
}