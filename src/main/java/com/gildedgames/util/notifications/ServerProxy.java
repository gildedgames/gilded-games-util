package com.gildedgames.util.notifications;

import net.minecraftforge.fml.relauncher.Side;

import com.gildedgames.util.core.SidedObject;

class ServerProxy
{
	public SidedObject<NotificationServices> createServices()
	{
		return new SidedObject<NotificationServices>(new NotificationServices(Side.CLIENT), new NotificationServices(Side.SERVER));
	}
}
