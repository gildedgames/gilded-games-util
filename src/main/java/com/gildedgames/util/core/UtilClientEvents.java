package com.gildedgames.util.core;

import net.minecraftforge.client.event.RenderWorldLastEvent;

import com.gildedgames.util.io_manager.IOCore;
import com.gildedgames.util.io_manager.io.IOSyncable.SyncSide;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class UtilClientEvents
{

	private static float partialTicks;

	public static float getPartialTicks()
	{
		return UtilClientEvents.partialTicks;
	}

	@SubscribeEvent
	public void tickClient(TickEvent.ClientTickEvent event)
	{
		if (event.phase == TickEvent.Phase.START)
		{
			IOCore.io().dispatchDirtySyncables(SyncSide.CLIENT);
		}
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void renderWorldLast(RenderWorldLastEvent event)
	{
		UtilClientEvents.partialTicks = event.partialTicks;
	}

}
