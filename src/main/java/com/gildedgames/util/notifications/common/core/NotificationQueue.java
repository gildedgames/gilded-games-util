package com.gildedgames.util.notifications.common.core;

import java.util.LinkedList;
import java.util.Queue;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.gildedgames.util.notifications.NotificationCore;
import com.gildedgames.util.notifications.common.player.PlayerNotification;

@SideOnly(Side.CLIENT)
public class NotificationQueue
{
	private Queue<INotification> notificationQueue = new LinkedList<INotification>();

	private int ticks = 0;

	private INotification active;

	public void addNotification(INotification notification)
	{
		this.notificationQueue.add(notification);
	}

	public void tick()
	{
		this.ticks++;

		if (this.ticks % 100 == 0)
		{
			this.active = this.notificationQueue.poll();
			if (this.active.getMessage() != null)
			{
				this.thePlayer().addNotification(this.active.getMessage());
			}
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
