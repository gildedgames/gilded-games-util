package com.gildedgames.util.core;

import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import com.gildedgames.util.io_manager.IOCore;
import com.gildedgames.util.io_manager.io.IOSyncable.SyncSide;

public class UtilEvents
{
	
	private static float partialTicks;
	
	public static float getPartialTicks()
	{
		return UtilEvents.partialTicks;
	}

	@SubscribeEvent
	public void tickStartClient(TickEvent.ClientTickEvent event)
	{
		if (event.phase == TickEvent.Phase.START)
		{
			IOCore.io().dispatchDirtySyncables(SyncSide.CLIENT);
		}
	}
	
	@SubscribeEvent
	public void tickStartServer(TickEvent.ServerTickEvent event)
	{
		if (event.phase == TickEvent.Phase.START)
		{
			IOCore.io().dispatchDirtySyncables(SyncSide.SERVER);
		}
	}
	
	@SubscribeEvent
	public void renderWorldLast(RenderWorldLastEvent event)
	{
		UtilEvents.partialTicks = event.partialTicks;
	}
	
}
