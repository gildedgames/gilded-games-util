package com.gildedgames.util.notifications.common.networking.messages;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

import com.gildedgames.util.core.CustomPacket;
import com.gildedgames.util.core.nbt.NBTHelper;
import com.gildedgames.util.io_manager.util.IOUtil;
import com.gildedgames.util.notifications.NotificationCore;
import com.gildedgames.util.notifications.common.core.INotification;
import com.gildedgames.util.notifications.common.player.PlayerNotification;

public class PacketNotification extends CustomPacket<PacketNotification>
{

	private INotification notification;

	private PlayerNotification player;

	public PacketNotification(INotification notification, PlayerNotification player)
	{
		this.notification = notification;
		this.player = player;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.player = NotificationCore.getPlayerNotifications(IOUtil.readUUID(buf));
		this.notification = NBTHelper.readInputObject(buf);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		IOUtil.writeUUID(this.player.getProfile().getUUID(), buf);
		NBTHelper.writeOutputObject(this.notification, buf);
	}

	@Override
	public void handleClientSide(PacketNotification message, EntityPlayer player)
	{
		NotificationCore.locate().queueNotificationForDisplay(message.notification);
	}

	@Override
	public void handleServerSide(PacketNotification message, EntityPlayer player)
	{
		NotificationCore.sendNotification(message.notification);
	}

}
