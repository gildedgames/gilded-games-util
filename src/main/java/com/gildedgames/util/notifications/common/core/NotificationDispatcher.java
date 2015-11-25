package com.gildedgames.util.notifications.common.core;

import java.util.UUID;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.notifications.NotificationCore;
import com.gildedgames.util.notifications.common.networking.messages.PacketNotification;
import com.gildedgames.util.notifications.common.player.PlayerNotification;

import net.minecraft.entity.player.EntityPlayerMP;

/**
 * Server sided notification sender.
 */
public class NotificationDispatcher
{

	public void sendNotification(INotification notification)
	{
		UUID player = notification.getReceiver();
		PlayerNotification playerHook = NotificationCore.getPlayerNotifications(player);

		INotificationMessage message = notification.getMessage();

		if (message != null)
		{
			if (!message.isRelevant())
			{
				return;
			}
			playerHook.addNotification(notification.getMessage());
		}
		UtilCore.NETWORK.sendTo(new PacketNotification(notification, playerHook), (EntityPlayerMP) playerHook.getProfile().getEntity());
	}

}