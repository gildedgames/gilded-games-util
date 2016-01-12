package com.gildedgames.util.notifications;

import com.gildedgames.util.core.SidedObject;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

public class CommonProxy
{
	public SidedObject<NotificationServices> createServices()
	{
		return new SidedObject<>(new NotificationServices(Side.CLIENT), new NotificationServices(Side.SERVER));
	}

	public void preInit(FMLPreInitializationEvent event)
	{

	}
}
