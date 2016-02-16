package com.gildedgames.util.core;

import com.gildedgames.util.io_manager.IOCore;
import com.gildedgames.util.io_manager.io.IOSyncable.SyncSide;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class UtilServerEvents
{

	private int tickCounter = 0;

	@SubscribeEvent
	public void tickServer(TickEvent.ServerTickEvent event)
	{
		if (event.phase == TickEvent.Phase.START)
		{
			IOCore.io().dispatchDirtySyncables(SyncSide.SERVER);
			this.tickCounter++;
		}
		else if (event.phase == TickEvent.Phase.END)
		{
			if (this.tickCounter % (1200 * 3) == 0)
			{
				UtilModule.instance.flushData();
			}
		}
	}
	
}
