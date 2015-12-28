package net.epoxide.colorfulmobs.common.network;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.*;
import cpw.mods.fml.relauncher.Side;

import net.darkhax.bookshelf.lib.ColorObject;
import net.darkhax.bookshelf.lib.util.PlayerUtils;

import io.netty.buffer.ByteBuf;
import net.epoxide.colorfulmobs.ColorfulMobs;
import net.epoxide.colorfulmobs.common.ColorProperties;

public class PacketSyncColor implements IMessage {
    
    public int entityID;
    public ColorObject colorObj;
    
    public PacketSyncColor() {
    
    }
    
    public PacketSyncColor(ColorObject color, EntityLivingBase living) {
        
        entityID = living.getEntityId();
        this.colorObj = color;
    }
    
    @Override
    public void fromBytes (ByteBuf buf) {
        
        this.entityID = buf.readInt();
        this.colorObj = new ColorObject(buf);
    }
    
    @Override
    public void toBytes (ByteBuf buf) {
        
        buf.writeInt(this.entityID);
        this.colorObj.writeToBuffer(buf);
    }
    
    public static class PacketColorSyncHandler implements IMessageHandler<PacketSyncColor, IMessage> {
        
        @Override
        public IMessage onMessage (PacketSyncColor packet, MessageContext ctx) {
            
            EntityPlayer player = (ctx.side == Side.CLIENT) ? PlayerUtils.getClientPlayer() : ctx.getServerHandler().playerEntity;
            Entity entity = player.worldObj.getEntityByID(packet.entityID);
            
            if (entity instanceof EntityLivingBase)
                ColorProperties.getProperties((EntityLivingBase) entity).setColorObject(packet.colorObj);
                
            if (ctx.side == Side.SERVER)
                ColorfulMobs.network.sendToAll(packet);
                
            return null;
        }
    }
}