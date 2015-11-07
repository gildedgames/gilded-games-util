package com.gildedgames.util.notifications.common.networking.messages;

import com.gildedgames.util.core.CustomPacket;
import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.notifications.NotificationCore;
import com.gildedgames.util.notifications.common.core.INotificationMessage;
import com.gildedgames.util.notifications.common.player.PlayerNotification;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class PacketRemoveMessage extends CustomPacket<PacketRemoveMessage>
{

	private INotificationMessage message;

	private String messageKey;

	public PacketRemoveMessage()
	{

	}

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
		this.removeFromHook(message, player);
	}

	@Override
	public void handleServerSide(PacketRemoveMessage message, EntityPlayer player)
	{
		INotificationMessage noti = this.removeFromHook(message, player);
		UtilCore.NETWORK.sendTo(new PacketRemoveMessage(noti), (EntityPlayerMP) player);
	}

	private INotificationMessage removeFromHook(PacketRemoveMessage message, EntityPlayer player)
	{
		PlayerNotification hook = NotificationCore.getPlayerNotifications(player);
		INotificationMessage noti = hook.getFromKey(message.messageKey);
		hook.removeNotification(noti);
		return noti;
	}

}
