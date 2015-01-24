package com.gildedgames.util.universe.common.networking.messages;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;

import com.gildedgames.util.universe.common.UniverseAPI;
import com.gildedgames.util.universe.common.util.IUniverse;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;

public class MessageTravelUniverse implements IMessage
{

	private String universeID;

	public MessageTravelUniverse()
	{
		
	}
	
	public MessageTravelUniverse(IUniverse universe)
	{
		this.universeID = UniverseAPI.instance().getIDFrom(universe);
	}

	public MessageTravelUniverse(String universeID)
	{
		this.universeID = universeID;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.universeID = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeUTF8String(buf, this.universeID);
	}
	
	public static class Handler implements IMessageHandler<MessageTravelUniverse, IMessage>
	{
	        
        @Override
        public IMessage onMessage(MessageTravelUniverse message, MessageContext ctx)
        {
        	if (ctx.side == Side.SERVER)
        	{
        		IUniverse universe = UniverseAPI.instance().getFromID(message.universeID);
        		
        		EntityPlayerMP player = ctx.getServerHandler().playerEntity;
        		
        		if (universe != null)
        		{
        			UniverseAPI.instance().travelTo(universe, player);
        		}
        	}

            return null;
        }

	}

}
