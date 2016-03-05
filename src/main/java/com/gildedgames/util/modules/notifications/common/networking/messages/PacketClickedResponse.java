package com.gildedgames.util.modules.notifications.common.networking.messages;

import com.gildedgames.util.core.io.MessageHandlerServer;
import com.gildedgames.util.io_manager.util.IOUtil;
import com.gildedgames.util.modules.notifications.NotificationModule;
import com.gildedgames.util.modules.notifications.common.core.INotificationMessage;
import com.gildedgames.util.modules.notifications.common.core.INotificationResponse;
import com.gildedgames.util.modules.notifications.common.player.PlayerNotification;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import java.util.UUID;

public class PacketClickedResponse implements IMessage
{

	private String notificationKey;

	private int responseIndex;

	private UUID receiverId;

	public PacketClickedResponse() { }

	public PacketClickedResponse(INotificationMessage notification, int responseIndex)
	{
		this.notificationKey = notification.getKey();
		this.responseIndex = responseIndex;
		this.receiverId = notification.getReceiver();
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.receiverId = IOUtil.readUUID(buf);
		this.notificationKey = ByteBufUtils.readUTF8String(buf);
		this.responseIndex = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		IOUtil.writeUUID(this.receiverId, buf);
		ByteBufUtils.writeUTF8String(buf, this.notificationKey);
		buf.writeInt(this.responseIndex);
	}

	public static class HandlerServer extends MessageHandlerServer<PacketClickedResponse, PacketRemoveMessage>
	{
		@Override
		public PacketRemoveMessage onMessage(PacketClickedResponse message, EntityPlayer player)
		{
			PlayerNotification playerNotification = NotificationModule.getPlayerNotifications(message.receiverId);

			INotificationMessage notification = playerNotification.getFromKey(message.notificationKey);

			INotificationResponse response = notification.getResponses().get(message.responseIndex);

			if (response.onClicked(player))
			{
				playerNotification.removeNotification(notification);

				return new PacketRemoveMessage(notification);
			}

			return null;
		}
	}

}
