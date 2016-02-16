package com.gildedgames.util.core;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ServerProxy extends Module
{

	public EntityPlayer getPlayer()
	{
		return null;
	}

	public void addScheduledTask(Runnable runnable)
	{

	}

	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		UtilServerEvents events = new UtilServerEvents();
		
		UtilModule.registerEventHandler(events);
	}
}
