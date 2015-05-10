package net.epoxide.colorfulmobs.common;

import io.netty.buffer.ByteBuf;
import net.epoxide.colorfulmobs.ColorfulMobs;
import net.epoxide.colorfulmobs.lib.ColorObject;
import net.epoxide.colorfulmobs.lib.Utilities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;

public class PacketColorSync implements IMessage {
    
    public int entityID;
    public ColorObject colorObj;
    
    public PacketColorSync() {
    
    }
    
    public PacketColorSync(ColorObject color, EntityLivingBase living) {
    
        entityID = living.getEntityId();
        this.colorObj = color;
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
    
    public static class PacketColorSyncHandler implements IMessageHandler<PacketColorSync, IMessage> {
        
        @Override
        public IMessage onMessage (PacketColorSync packet, MessageContext ctx) {
        
            EntityPlayer player = (ctx.side == Side.CLIENT) ? Utilities.thePlayer() : ctx.getServerHandler().playerEntity;
            Entity entity = player.worldObj.getEntityByID(packet.entityID);
            
            if (entity instanceof EntityLivingBase)
                ColorProperties.getPropsFromEntity((EntityLivingBase) entity).setColorObject(packet.colorObj);
            
            if (ctx.side == Side.SERVER)
                ColorfulMobs.network.sendToAll(packet);
            
            return null;
        }
    }
}