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
	public void sendNotification(INotification notification)
	{
		//If the notification contains no message and is meant for the player,
		//don't send it to the server for a check. 
		EntityPlayer player = notification.getReceiver();
		EntityPlayer thePlayer = Minecraft.getMinecraft().thePlayer;
		if (notification.getMessage() == null && player.equals(thePlayer))
		{
			NotificationCore.locate().queueNotificationForDisplay(notification);
		}
		else if (notification.getSender() == null || notification.getSender().equals(thePlayer))
		{
			UtilCore.NETWORK.sendToServer(new PacketNotification(notification, NotificationCore.getPlayerNotifications(player)));
		}
	}

}
