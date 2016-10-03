package net.epoxide.colorfulmobs.common.network;

import io.netty.buffer.ByteBuf;
import net.darkhax.bookshelf.lib.util.PlayerUtils;
import net.epoxide.colorfulmobs.ColorfulMobs;
import net.epoxide.colorfulmobs.common.ColorProperties;
import net.epoxide.colorfulmobs.lib.ColorObject;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PacketSyncColor implements IMessage {
    
    public int entityID;
    public ColorObject colorObj;
    public boolean radiant;
    
    public PacketSyncColor() {
        
    }
    
    public PacketSyncColor(ColorObject color, EntityLivingBase living, boolean radiant) {
        
        this.entityID = living.getEntityId();
        this.colorObj = color;
        this.radiant = radiant;
    }
    
    @Override
    public void fromBytes (ByteBuf buf) {
        
        this.entityID = buf.readInt();
        this.colorObj = new ColorObject(buf);
        this.radiant = buf.readBoolean();
    }
    
    @Override
    public void toBytes (ByteBuf buf) {
        
        buf.writeInt(this.entityID);
        this.colorObj.write(buf);
        buf.writeBoolean(radiant);
    }
    
    public static class PacketColorSyncHandler implements IMessageHandler<PacketSyncColor, IMessage> {
        
        @Override
        public IMessage onMessage (PacketSyncColor packet, MessageContext ctx) {
            
            EntityPlayer player = (ctx.side == Side.CLIENT) ? PlayerUtils.getClientPlayer() : ctx.getServerHandler().playerEntity;
            Entity entity = player.worldObj.getEntityByID(packet.entityID);
            
            if (entity instanceof EntityLivingBase)
                ColorProperties.getProperties((EntityLivingBase) entity).setColor(packet.colorObj);
            
            if (ctx.side == Side.SERVER)
                ColorfulMobs.network.sendToAll(packet);
            
            return null;
        }
    }
}