package com.gildedgames.util.worldhook.common;

import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;

import com.gildedgames.util.worldhook.WorldHookCore;

public class WorldEventHandler
{

	@SubscribeEvent
	public void onTick(WorldTickEvent event)
	{
		if (event.phase == Phase.END)
		{
			for (IWorldPool pool : WorldHookCore.locate().getPools())
			{
				IWorldHook world = pool.get(event.world);

				world.onUpdate();
			}
		}
	}
	
	@SubscribeEvent
	public void onLoad(WorldEvent.Load event)
	{
		for (IWorldPool pool : WorldHookCore.locate().getPools())
		{
			IWorldHook world = pool.get(event.world);

			world.onLoad();
		}
	}
	
	@SubscribeEvent
	public void onUnload(WorldEvent.Unload event)
	{
		for (IWorldPool pool : WorldHookCore.locate().getPools())
		{
			IWorldHook world = pool.get(event.world);

			world.onUnload();
		}
	}
	
	@SubscribeEvent
	public void onSave(WorldEvent.Save event)
	{
		for (IWorldPool pool : WorldHookCore.locate().getPools())
		{
			IWorldHook world = pool.get(event.world);

			world.onSave();
		}
	}
	
}
