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

	private int ticks = 0;

	private INotification active;

	public void addNotification(INotification notification)
	{
		this.notificationQueue.add(notification);
		this.thePlayer().addNotification(notification.getMessage());
	}

	public void tick()
	{
		this.ticks++;

		if (this.ticks % 100 == 0)
		{
			this.active = this.notificationQueue.poll();
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
}
