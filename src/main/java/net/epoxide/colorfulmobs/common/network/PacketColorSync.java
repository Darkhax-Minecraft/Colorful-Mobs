package net.epoxide.colorfulmobs.common.network;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import cpw.mods.fml.common.network.ByteBufUtils;

import net.darkhax.bookshelf.common.network.AbstractMessage;

import io.netty.buffer.ByteBuf;
import net.epoxide.colorfulmobs.common.ColorProperties;
import net.epoxide.colorfulmobs.lib.ColorObject;

public class PacketColorSync extends AbstractMessage<PacketColorSync> {
    
    public int entityID;
    public ColorObject colorObj;
    
    public PacketColorSync() {
    
    }
    
    public PacketColorSync(ColorProperties properties) {
        
        entityID = properties.entity.getEntityId();
        this.colorObj = properties.getColorObj();
    }
    
    @Override
    public void handleClientMessage (PacketColorSync packet, EntityPlayer player) {
        
        Entity entity = player.worldObj.getEntityByID(packet.entityID);
        
        if (entity instanceof EntityLivingBase)
            ColorProperties.getProperties((EntityLivingBase) entity).setColorObject(packet.colorObj);
    }
    
    @Override
    public void handleServerMessage (PacketColorSync message, EntityPlayer player) {
    
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