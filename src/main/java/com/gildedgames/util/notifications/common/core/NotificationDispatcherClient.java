package com.gildedgames.util.notifications.common.core;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.notifications.NotificationCore;
import com.gildedgames.util.notifications.common.networking.messages.PacketNotification;

@SideOnly(Side.CLIENT)
public class NotificationDispatcherClient extends NotificationDispatcher
{

	@Override
	public void sendNotification(INotification notification, EntityPlayer player)
	{
		//If the notification contains no message and is meant for the player,
		//don't send it to the server for a check. 
		if (notification.getMessage() == null && player == Minecraft.getMinecraft().thePlayer)
		{
			NotificationCore.locate().queueNotificationForDisplay(notification);
		}
		else
		{
			UtilCore.NETWORK.sendToServer(new PacketNotification(notification, NotificationCore.getPlayerNotifications(player)));
		}
	}

}
