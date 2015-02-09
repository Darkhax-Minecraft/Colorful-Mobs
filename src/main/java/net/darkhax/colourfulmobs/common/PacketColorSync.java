package net.darkhax.colourfulmobs.common;

import net.darkhax.bookshelf.helper.PlayerHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import io.netty.buffer.ByteBuf;
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
    public void fromBytes(ByteBuf buf) {

        this.entityID = ByteBufUtils.readVarInt(buf, 5);
        this.colorObj = ColorObject.getColorFromTag(ByteBufUtils.readTag(buf));
    }

    @Override
    public void toBytes(ByteBuf buf) {

        ByteBufUtils.writeVarInt(buf, entityID, 5);
        ByteBufUtils.writeTag(buf, ColorObject.getTagFromColor(this.colorObj));
    }
    
    public static class PacketColorSyncHandler implements IMessageHandler<PacketColorSync, IMessage> {
        
        @Override
        public IMessage onMessage(PacketColorSync packet, MessageContext ctx) {
            
            if (ctx.side == Side.CLIENT) {
               
                Entity entity = PlayerHelper.thePlayer().worldObj.getEntityByID(packet.entityID);
                if(entity instanceof EntityLivingBase)
                    ColorProperties.setEntityColors(packet.colorObj, (EntityLivingBase) entity);
            }
            
            return null;
        }
    }
}