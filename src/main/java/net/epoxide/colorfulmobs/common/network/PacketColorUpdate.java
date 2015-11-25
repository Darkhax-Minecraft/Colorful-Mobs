package net.epoxide.colorfulmobs.common.network;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import cpw.mods.fml.common.network.ByteBufUtils;

import net.darkhax.bookshelf.common.network.AbstractMessage;

import io.netty.buffer.ByteBuf;
import net.epoxide.colorfulmobs.common.ColorProperties;
import net.epoxide.colorfulmobs.lib.ColorObject;

public class PacketColorUpdate extends AbstractMessage<PacketColorUpdate> {
    private ColorObject colorObj;
    private int entityID;
    
    public PacketColorUpdate() {
    
    }
    
    public PacketColorUpdate(ColorObject colorObj, EntityLivingBase entity) {
        
        this.colorObj = colorObj;
        this.entityID = entity.getEntityId();
    }
    
    @Override
    public void handleClientMessage (PacketColorUpdate message, EntityPlayer player) {
    
    }
    
    @Override
    public void handleServerMessage (PacketColorUpdate packet, EntityPlayer player) {
        
        EntityLivingBase entity = (EntityLivingBase) player.getEntityWorld().getEntityByID(packet.entityID);
        ColorProperties.getProperties(entity).setColorObject(packet.colorObj);
    }
    
    @Override
    public void fromBytes (ByteBuf buf) {
        
        this.entityID = buf.readInt();
        NBTTagCompound colorTag = ByteBufUtils.readTag(buf);
        this.colorObj = (colorTag.hasKey("red")) ? new ColorObject(colorTag) : new ColorObject();
    }
    
    @Override
    public void toBytes (ByteBuf buf) {
        
        buf.writeInt(this.entityID);
        ColorObject colorToWrite = (this.colorObj != null) ? this.colorObj : new ColorObject();
        ByteBufUtils.writeTag(buf, colorToWrite.getTagFromColor());
    }
}
