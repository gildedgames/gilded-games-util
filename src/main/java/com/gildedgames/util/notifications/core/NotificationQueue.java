package com.gildedgames.util.notifications.core;

import java.util.LinkedList;
import java.util.Queue;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class NotificationQueue
{
	private Queue<INotification> notificationQueue = new LinkedList<INotification>();

	int ticks = 0;

	public void addNotification(INotification notification)
	{
		this.notificationQueue.add(notification);
	}

	public void tick()
	{
		this.ticks++;

		if (this.ticks % 100 == 0)
		{

		}

	}
}
