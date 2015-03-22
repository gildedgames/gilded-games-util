package com.gildedgames.util.player.common.networking.messages;

import io.netty.buffer.ByteBuf;

import java.util.UUID;

import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.gildedgames.util.io_manager.io.IOSyncable.SyncSide;
import com.gildedgames.util.player.PlayerCore;
import com.gildedgames.util.player.common.IPlayerHookPool;
import com.gildedgames.util.player.common.player.IPlayerHook;

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
		ByteBufUtils.writeUTF8String(buf, this.playerHook.getParentPool().getName());
		UUID uuid = this.playerHook.getProfile().getUUID();
		buf.writeLong(uuid.getMostSignificantBits());
		buf.writeLong(uuid.getLeastSignificantBits());
		this.playerHook.getProfile().syncTo(buf, SyncSide.CLIENT);
		this.playerHook.syncTo(buf, SyncSide.CLIENT);
	}

	public static class Handler implements IMessageHandler<MessagePlayerHook, IMessage>
	{

		@Override
		public IMessage onMessage(MessagePlayerHook message, MessageContext ctx)
		{
			if (ctx.side.isClient())
			{
				IPlayerHookPool manager = PlayerCore.locate().getPool(ByteBufUtils.readUTF8String(message.buf));

				IPlayerHook playerHook = manager.get(new UUID(message.buf.readLong(), message.buf.readLong()));//manager.getFactory().create(profile, manager);

				playerHook.getProfile().syncFrom(message.buf, SyncSide.SERVER);
				playerHook.syncFrom(message.buf, SyncSide.SERVER);
			}

			return null;
		}
	}

}
