package net.epoxide.colorfulmobs.client.gui;

import net.darkhax.bookshelf.objects.ColorObject;
import net.epoxide.colorfulmobs.ColorfulMobs;
import net.epoxide.colorfulmobs.client.EntityUtil;
import net.epoxide.colorfulmobs.common.ColorProperties;
import net.epoxide.colorfulmobs.common.PacketColorSync;
import net.epoxide.colorfulmobs.lib.Constants;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class GuiColorSelection extends GuiScreen {

    private GuiSlider sliderR, sliderG, sliderB, sliderA;
    protected final int xSize = 177;
    protected final int ySize = 222;

    private EntityLivingBase entity;
    private EntityLivingBase tempEntity;
    private int r = 255, g = 255, b = 255, a = 100;

    public GuiColorSelection(EntityLivingBase entity) {

        this.entity = entity;
        this.tempEntity = (EntityLivingBase) EntityList.createEntityByName(EntityList.getEntityString(entity), (World) null);
        this.tempEntity.setWorld(entity.worldObj);
    }

    protected void drawGuiContainerBackgroundLayer() {

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
            a = (int) (obj.alpha * 100);

            ColorProperties.setEntityColors(obj, tempEntity);
        }
        this.sliderR = new GuiSlider(2, k + 3, l + 140, 0, 255, r, EnumChatFormatting.RED + StatCollector.translateToLocal("chat.colorfulmobs.red"), this);
        buttonList.add(this.sliderR);

        this.sliderG = new GuiSlider(3, k + 60, l + 140, 0, 255, g, EnumChatFormatting.GREEN + StatCollector.translateToLocal("chat.colorfulmobs.green"), this);
        buttonList.add(this.sliderG);

        this.sliderB = new GuiSlider(4, k + 117, l + 140, 0, 255, b, EnumChatFormatting.BLUE + StatCollector.translateToLocal("chat.colorfulmobs.blue"), this);
        buttonList.add(this.sliderB);

        this.sliderA = new GuiSlider(5, k + 61, l + 165, 0, 100, a, EnumChatFormatting.WHITE + StatCollector.translateToLocal("chat.colorfulmobs.alpha"), this);
        buttonList.add(this.sliderA);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        drawGuiContainerBackgroundLayer();
        super.drawScreen(mouseX, mouseY, partialTicks);

        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;

        drawCenteredString(fontRendererObj, StatCollector.translateToLocal("chat.colorfulmobs.colormenu"), k + 87, l + 10, 0xffffff);

    }

    float rotation = 0;

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
    protected void actionPerformed(GuiButton button) {

        if (button.id == 1) {

            if (entity != null) {

                // send packet
                PacketColorSync packetColorSync = new PacketColorSync(new ColorObject(r, g, b, a), entity);
                ColorfulMobs.network.sendToServer(packetColorSync);
            }
            // close gui
            this.mc.displayGuiScreen(null);
            this.mc.setIngameFocus();
        } else {

            if (button instanceof GuiSlider) {

                updateColor();
            }
        }
    }

    /**
     * Updates all the color text and values.
     */
    public void updateColor() {

        float color = sliderR.getValue();
        r = (int) sliderG.denormalizeValue(color);

        color = sliderG.getValue();
        g = (int) sliderG.denormalizeValue(color);

        color = sliderB.getValue();
        b = (int) sliderB.denormalizeValue(color);

        color = sliderA.getValue();
        a = (int) sliderA.denormalizeValue(color);

        ColorProperties.setEntityColors(new ColorObject(r, g, b, a), tempEntity);
    }

}