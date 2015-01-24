package com.gildedgames.util.player.common.networking.messages;

import io.netty.buffer.ByteBuf;

import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.player.PlayerCore;
import com.gildedgames.util.player.common.IPlayerHookPool;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessagePlayerHookRequest implements IMessage
{

	private UUID uuid;

	private IPlayerHookPool<?> pool;

	public MessagePlayerHookRequest()
	{

	}

	public MessagePlayerHookRequest(IPlayerHookPool<?> pool, UUID uuid)
	{
		this.pool = pool;
		this.uuid = uuid;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.pool = PlayerCore.locate().getPool(ByteBufUtils.readUTF8String(buf));
		this.uuid = new UUID(buf.readLong(), buf.readLong());
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeUTF8String(buf, this.pool.getName());

		buf.writeLong(this.uuid.getMostSignificantBits());
		buf.writeLong(this.uuid.getLeastSignificantBits());
	}

	public static class Handler implements IMessageHandler<MessagePlayerHookRequest, IMessage>
	{

		@Override
		public IMessage onMessage(MessagePlayerHookRequest message, MessageContext ctx)
		{
			if (ctx.side.isServer())
			{
				EntityPlayer player = ctx.getServerHandler().playerEntity;

				UtilCore.NETWORK.sendTo(new MessagePlayerHook(message.pool.get(message.uuid)), (EntityPlayerMP) player);
			}

			return null;
		}

	}

}
