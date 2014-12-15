package com.gildedgames.playerhook.common.networking.messages;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

import com.gildedgames.playerhook.common.player.PlayerHook;

public class MessagePlayerInfo implements IMessage
{

	public String username;

	public PlayerHook playerInfo;

	public MessagePlayerInfo()
	{
	}

	public MessagePlayerInfo(PlayerHook playerHook)
	{
		this.username = playerHook.getUsername();
		this.playerInfo = playerHook;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.username = ByteBufUtils.readUTF8String(buf);

		PlayerHook playerHook = PlayerHook.get(Side.CLIENT, this.username);
		playerHook.readServerData(buf);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeUTF8String(buf, this.username);
		this.playerInfo.writeServerData(buf);
	}
	
	public static class Handler implements IMessageHandler<MessagePlayerInfo, IMessage>
	{
	        
        @Override
        public IMessage onMessage(MessagePlayerInfo message, MessageContext ctx)
        {
        	return null;
        }
        
	}

}
