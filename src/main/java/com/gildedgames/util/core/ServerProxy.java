package com.gildedgames.util.core;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import com.gildedgames.util.modules.entityhook.EntityHookModule;
import com.gildedgames.util.modules.instances.PlayerInstances;

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
		
		EntityHookModule.api().registerHookProvider(PlayerInstances.PROVIDER);
	}
}
