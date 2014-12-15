package com.gildedgames.playerhook.common.networking.messages;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

import com.gildedgames.playerhook.common.player.PlayerHook;

public class MessagePlayerInfoClient implements IMessage
{

	private PlayerHook playerHook;

	private ByteBuf dataInput;

	public MessagePlayerInfoClient()
	{

	}

	public MessagePlayerInfoClient(PlayerHook playerInfo)
	{
		this.playerHook = playerInfo;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.dataInput = buf;
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		this.playerHook.writeClientData(buf);
	}
	
	public static class Handler implements IMessageHandler<MessagePlayerInfoClient, IMessage>
	{
	        
        @Override
        public IMessage onMessage(MessagePlayerInfoClient message, MessageContext ctx)
        {
        	if (ctx.side == Side.SERVER)
        	{
        		EntityPlayer player = ctx.getServerHandler().playerEntity;
        		
        		PlayerHook.get(player).readClientData(message.dataInput);
        	}

        	return null;
        }
        
	}

}
