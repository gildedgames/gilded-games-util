package com.gildedgames.util.modules.notifications.common.networking.messages;

import com.gildedgames.util.core.io.CustomPacket;
import com.gildedgames.util.io_manager.util.IOUtil;
import com.gildedgames.util.modules.notifications.NotificationModule;
import com.gildedgames.util.modules.notifications.common.core.INotification;
import com.gildedgames.util.modules.notifications.common.player.PlayerNotification;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class PacketNotification extends CustomPacket<PacketNotification>
{

	private INotification notification;

	private PlayerNotification player;

	public PacketNotification()
	{

	}

	public PacketNotification(INotification notification, PlayerNotification player)
	{
		this.notification = notification;
		this.player = player;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.player = NotificationModule.getPlayerNotifications(IOUtil.readUUID(buf));
		this.notification = IOUtil.readIO(buf);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		IOUtil.writeUUID(this.player.getProfile().getUUID(), buf);
		IOUtil.writeIO(buf, this.notification);
	}

	@Override
	public void handleClientSide(PacketNotification message, EntityPlayer player)
	{
		NotificationModule.locate().queueNotificationForDisplay(message.notification);
	}

	@Override
	public void handleServerSide(PacketNotification message, EntityPlayer player)
	{
		NotificationModule.sendNotification(message.notification);
	}

}
