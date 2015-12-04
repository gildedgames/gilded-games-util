package com.gildedgames.util.notifications.client;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.notifications.NotificationCore;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class NotificationClientHandler
{
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onRenderGui(RenderGameOverlayEvent event)
	{
		NotificationCore.locate().onRenderOverlay();

		UtilCore.registerEventHandler(new NotificationClientHandler());
	}
}
