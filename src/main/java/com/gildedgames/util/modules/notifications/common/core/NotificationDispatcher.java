package com.gildedgames.util.modules.notifications.common.core;

import java.util.UUID;

import com.gildedgames.util.core.UtilModule;
import com.gildedgames.util.modules.notifications.NotificationModule;
import com.gildedgames.util.modules.notifications.common.networking.messages.PacketNotification;
import com.gildedgames.util.modules.notifications.common.player.PlayerNotification;

import net.minecraft.entity.player.EntityPlayerMP;

/**
 * Server sided notification sender.
 */
public class NotificationDispatcher
{

	public void sendNotification(INotification notification)
	{
		UUID player = notification.getReceiver();
		PlayerNotification playerHook = NotificationModule.getPlayerNotifications(player);

		INotificationMessage message = notification.getMessage();

		if (message != null)
		{
			if (!message.isRelevant())
			{
				return;
			}
			playerHook.addNotification(notification.getMessage());
		}
		UtilModule.NETWORK.sendTo(new PacketNotification(notification, playerHook), (EntityPlayerMP) playerHook.getProfile().getEntity());
	}

}
