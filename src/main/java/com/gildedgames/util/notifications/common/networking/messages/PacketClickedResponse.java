package com.gildedgames.util.notifications.common.networking.messages;

import com.gildedgames.util.core.io.CustomPacket;
import com.gildedgames.util.core.UtilModule;
import com.gildedgames.util.io_manager.util.IOUtil;
import com.gildedgames.util.notifications.NotificationModule;
import com.gildedgames.util.notifications.common.core.INotificationMessage;
import com.gildedgames.util.notifications.common.core.INotificationResponse;
import com.gildedgames.util.notifications.common.player.PlayerNotification;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class PacketClickedResponse extends CustomPacket<PacketClickedResponse>
{

	private INotificationMessage notification;

	private int responseIndex;

	private PlayerNotification player;

	public PacketClickedResponse()
	{

	}

	public PacketClickedResponse(INotificationMessage notification, int responseIndex)
	{
		this.notification = notification;
		this.responseIndex = responseIndex;
		this.player = NotificationModule.getPlayerNotifications(notification.getReceiver());
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.player = NotificationModule.getPlayerNotifications(IOUtil.readUUID(buf));
		this.notification = this.player.getFromKey(ByteBufUtils.readUTF8String(buf));
		this.responseIndex = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		IOUtil.writeUUID(this.player.getProfile().getUUID(), buf);
		ByteBufUtils.writeUTF8String(buf, this.notification.getKey());
		buf.writeInt(this.responseIndex);
	}

	@Override
	public void handleClientSide(PacketClickedResponse message, EntityPlayer player)
	{
		throw new IllegalStateException();
	}

	@Override
	public void handleServerSide(PacketClickedResponse message, EntityPlayer player)
	{
		INotificationResponse response = message.notification.getResponses().get(message.responseIndex);
		if (response.onClicked(player))
		{
			message.player.removeNotification(message.notification);
			UtilModule.NETWORK.sendTo(new PacketRemoveMessage(message.notification), (EntityPlayerMP) player);
		}
	}

}
