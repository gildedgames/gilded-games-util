package com.gildedgames.util.notifications;

import com.gildedgames.util.core.SidedObject;

import net.minecraftforge.fml.relauncher.Side;

public class ServerProxy
{
	public SidedObject<NotificationServices> createServices()
	{
		return new SidedObject<NotificationServices>(new NotificationServices(Side.CLIENT), new NotificationServices(Side.SERVER));
	}
}
