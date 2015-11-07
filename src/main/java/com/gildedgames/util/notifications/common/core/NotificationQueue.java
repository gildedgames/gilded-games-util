package com.gildedgames.util.notifications.common.core;

import java.util.LinkedList;
import java.util.Queue;

import com.gildedgames.util.notifications.NotificationCore;
import com.gildedgames.util.notifications.common.player.PlayerNotification;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class NotificationQueue
{
	private Queue<INotification> notificationQueue = new LinkedList<INotification>();

	private long notiTime = 0;

	private INotification active;

	public void addNotification(INotification notification)
	{
		this.notificationQueue.add(notification);
		if (notification.getMessage() != null)
		{
			this.thePlayer().addNotification(notification.getMessage());
		}
	}

	public void tick()
	{
		if (this.active == null || this.getNotiTime() >= 3000)
		{
			this.active = this.notificationQueue.poll();
			this.notiTime = Minecraft.getSystemTime();
		}
	}

	private PlayerNotification thePlayer()
	{
		return NotificationCore.getPlayerNotifications(Minecraft.getMinecraft().thePlayer);
	}

	public INotification activeNotification()
	{
		return this.active;
	}

	public long getNotiTime()
	{
		return Minecraft.getSystemTime() - this.notiTime;
	}
}
