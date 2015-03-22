package com.gildedgames.util.notifications.core;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class NotificationDispatcherClient implements NotificationDispatcher
{

	private NotificationQueue queue = new NotificationQueue();

	@Override
	public void sendNotification(INotification notification, EntityPlayer player)
	{
		if (player.equals(Minecraft.getMinecraft().thePlayer))
		{
			this.queue.addNotification(notification);
		}
		else
		{

		}
	}

}
