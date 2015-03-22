package com.gildedgames.util.notifications.common.networking.messages;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;

import com.gildedgames.util.core.CustomPacket;
import com.gildedgames.util.notifications.NotificationCore;
import com.gildedgames.util.notifications.common.core.INotificationMessage;
import com.gildedgames.util.notifications.common.player.PlayerNotification;

public class PacketRemoveMessage extends CustomPacket<PacketRemoveMessage>
{

	private INotificationMessage message;

	private String messageKey;

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

	@Override
	public void handleClientSide(PacketRemoveMessage message, EntityPlayer player)
	{
		PlayerNotification hook = NotificationCore.getPlayerNotifications(player);
		INotificationMessage noti = hook.getFromKey(message.messageKey);
		hook.removeNotification(noti);
	}

	@Override
	public void handleServerSide(PacketRemoveMessage message, EntityPlayer player)
	{
		// TODO Auto-generated method stub

	}

}
