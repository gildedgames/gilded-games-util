package com.gildedgames.util.modules.notifications.common.networking.messages;

import com.gildedgames.util.core.io.MessageHandlerClient;
import com.gildedgames.util.core.io.MessageHandlerServer;
import com.gildedgames.util.modules.notifications.NotificationModule;
import com.gildedgames.util.modules.notifications.common.core.INotificationMessage;
import com.gildedgames.util.modules.notifications.common.player.PlayerNotification;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketRemoveMessage implements IMessage
{

	private INotificationMessage message;

	private String messageKey;

	public PacketRemoveMessage() { }

	public PacketRemoveMessage(INotificationMessage message)
	{
		this.message = message;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.messageKey = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeUTF8String(buf, this.message.getKey());
	}

	private static INotificationMessage removeFromHook(PacketRemoveMessage message, EntityPlayer player)
	{
		PlayerNotification hook = NotificationModule.getPlayerNotifications(player);
		INotificationMessage noti = hook.getFromKey(message.messageKey);
		hook.removeNotification(noti);
		return noti;
	}

	public static class HandlerClient extends MessageHandlerClient<PacketRemoveMessage, IMessage>
	{
		@Override
		public IMessage onMessage(PacketRemoveMessage message, EntityPlayer player)
		{
			PacketRemoveMessage.removeFromHook(message, player);

			return null;
		}
	}

	public static class HandlerServer extends MessageHandlerServer<PacketRemoveMessage, PacketRemoveMessage>
	{
		@Override
		public PacketRemoveMessage onMessage(PacketRemoveMessage message, EntityPlayer player)
		{
			INotificationMessage noti = PacketRemoveMessage.removeFromHook(message, player);

			return new PacketRemoveMessage(noti);
		}
	}
}
