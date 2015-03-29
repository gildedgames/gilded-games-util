package com.gildedgames.util.notifications;

import net.minecraftforge.fml.relauncher.Side;

import com.gildedgames.util.core.SidedObject;

class ClientProxy extends ServerProxy
{
	@Override
	public SidedObject<NotificationServices> createServices()
	{
		return new SidedObject<NotificationServices>(new NotificationServicesClient(), new NotificationServices(Side.SERVER));
	}
}
