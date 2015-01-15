package com.gildedgames.util.playerhook.common.networking.messages;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.gildedgames.util.playerhook.PlayerHookCore;
import com.gildedgames.util.playerhook.common.IPlayerHookPool;
import com.gildedgames.util.playerhook.common.player.IPlayerHook;

public class MessagePlayerHook implements IMessage
{

	public IPlayerHook playerHook;

	public int managerID;

	public ByteBuf buf;

	public MessagePlayerHook()
	{
	}

	public MessagePlayerHook(IPlayerHook playerHook)
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
		int poolID = PlayerHookCore.locate().getPoolID(this.playerHook.getParentPool());

		buf.writeInt(poolID);

		this.playerHook.getProfile().writeToClient(buf);
		this.playerHook.writeToClient(buf);
	}

	public static class Handler implements IMessageHandler<MessagePlayerHook, IMessage>
	{

		@Override
		public IMessage onMessage(MessagePlayerHook message, MessageContext ctx)
		{
			if (ctx.side.isClient())
			{
				IPlayerHookPool<?> manager = PlayerHookCore.locate().getPools().get(message.buf.readInt());

				IPlayerHook playerHook = manager.createEmpty();
				playerHook.getProfile().readFromServer(message.buf);
				playerHook.readFromServer(message.buf);
			}

			return null;
		}

	}

}
