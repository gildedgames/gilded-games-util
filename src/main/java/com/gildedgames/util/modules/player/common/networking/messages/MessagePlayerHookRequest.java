package com.gildedgames.util.modules.player.common.networking.messages;

import com.gildedgames.util.core.UtilModule;
import com.gildedgames.util.modules.player.PlayerModule;
import com.gildedgames.util.modules.player.common.IPlayerHookPool;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.UUID;

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
		this.pool = PlayerModule.locate().getPool(ByteBufUtils.readUTF8String(buf));
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

				UtilModule.NETWORK.sendTo(new MessagePlayerHook(message.pool.get(message.uuid)), (EntityPlayerMP) player);
			}

			return null;
		}

	}

}
