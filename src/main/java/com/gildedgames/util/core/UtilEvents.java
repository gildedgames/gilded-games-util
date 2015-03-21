package com.gildedgames.util.core;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import com.gildedgames.util.io_manager.IOCore;
import com.gildedgames.util.io_manager.io.IOSyncable.SyncSide;

public class UtilEvents
{

	@SubscribeEvent
	public void tickStartClient(TickEvent.ClientTickEvent event)
	{
		IOCore.io().dispatchDirtySyncables(SyncSide.CLIENT);
	}
	
	@SubscribeEvent
	public void tickStartServer(TickEvent.ServerTickEvent event)
	{
		IOCore.io().dispatchDirtySyncables(SyncSide.SERVER);
	}
	
}
