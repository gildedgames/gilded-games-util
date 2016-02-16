package com.gildedgames.util.notifications.client;

import com.gildedgames.util.core.UtilModule;
import com.gildedgames.util.notifications.NotificationModule;
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
		NotificationModule.locate().onRenderOverlay();

		UtilModule.registerEventHandler(new NotificationClientHandler());
	}
}
