package com.gildedgames.util.player.common.networking.messages;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

import com.gildedgames.util.player.PlayerCore;
import com.gildedgames.util.player.common.player.IPlayerHook;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessagePlayerHookClient implements IMessage
{

	private IPlayerHook playerHook;

	private ByteBuf buf;

	public MessagePlayerHookClient()
	{

	}

	public MessagePlayerHookClient(IPlayerHook playerHook)
	{
		this.playerHook = playerHook;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.buf = buf;
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		PlayerCore.locate().writeHookReference(this.playerHook, buf);
		
		this.playerHook.getProfile().writeToServer(buf);
		this.playerHook.writeToServer(buf);
	}

	public static class Handler implements IMessageHandler<MessagePlayerHookClient, IMessage>
	{
 
        @Override
        public IMessage onMessage(MessagePlayerHookClient message, MessageContext ctx)
        {
        	if (ctx.side.isServer())
        	{
        		EntityPlayer player = ctx.getServerHandler().playerEntity;
        		
        		IPlayerHook playerHook = PlayerCore.locate().readHookReference(player, message.buf);
        		
        		playerHook.getProfile().readFromClient(message.buf);
        		playerHook.readFromClient(message.buf);
        	}

        	return null;
        }

	}

}
