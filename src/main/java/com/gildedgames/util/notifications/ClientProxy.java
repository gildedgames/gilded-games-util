package com.gildedgames.util.notifications;

import com.gildedgames.util.core.SidedObject;

import net.minecraftforge.fml.relauncher.Side;

public class ClientProxy extends ServerProxy
{
	@Override
	public SidedObject<NotificationServices> createServices()
	{
		return new SidedObject<NotificationServices>(new NotificationServicesClient(), new NotificationServices(Side.SERVER));
	}
}
