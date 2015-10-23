package com.gildedgames.util.core.client;

import com.gildedgames.util.notifications.NotificationCore;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ClientTickHandler
{
	@SubscribeEvent
	public void tickEnd(TickEvent.RenderTickEvent event)
	{
		if (event.phase == TickEvent.Phase.END)
		{
			NotificationCore.locate().onRenderOverlay();
		}
	}
}
