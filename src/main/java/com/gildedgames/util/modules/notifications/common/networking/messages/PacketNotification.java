package com.gildedgames.util.modules.notifications.common.networking.messages;

import com.gildedgames.util.core.io.MessageHandlerClient;
import com.gildedgames.util.core.io.MessageHandlerServer;
import com.gildedgames.util.io_manager.util.IOUtil;
import com.gildedgames.util.modules.notifications.NotificationModule;
import com.gildedgames.util.modules.notifications.common.core.INotification;
import com.gildedgames.util.modules.notifications.common.player.PlayerNotification;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import java.util.UUID;

public class PacketNotification implements IMessage
{

	private INotification notification;

	private UUID playerId;

	public PacketNotification() { }

	public PacketNotification(INotification notification, PlayerNotification player)
	{
		this.notification = notification;
		this.playerId = player.getUniqueId();
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.playerId = IOUtil.readUUID(buf);
		this.notification = IOUtil.readIO(buf);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		IOUtil.writeUUID(this.playerId, buf);
		IOUtil.writeIO(buf, this.notification);
	}

	public static class HandlerClient extends MessageHandlerClient<PacketNotification, IMessage>
	{
		@Override
		public IMessage onMessage(PacketNotification message, EntityPlayer player)
		{
			NotificationModule.locate().queueNotificationForDisplay(message.notification);

			return null;
		}
	}

	public static class HandlerServer extends MessageHandlerServer<PacketNotification, IMessage>
	{
		@Override
		public IMessage onMessage(PacketNotification message, EntityPlayer player)
		{
			NotificationModule.sendNotification(message.notification);

			return null;
		}
	}


}
