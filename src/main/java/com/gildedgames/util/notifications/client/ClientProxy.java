package com.gildedgames.util.notifications.client;

import com.gildedgames.util.core.SidedObject;

import com.gildedgames.util.notifications.NotificationServices;
import com.gildedgames.util.notifications.NotificationServicesClient;
import com.gildedgames.util.notifications.CommonProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

public class ClientProxy extends CommonProxy
{
	@Override
	public SidedObject<NotificationServices> createServices()
	{
		return new SidedObject<NotificationServices>(new NotificationServicesClient(), new NotificationServices(Side.SERVER));
	}

	public void preInit(FMLPreInitializationEvent event)
	{

	}
}
