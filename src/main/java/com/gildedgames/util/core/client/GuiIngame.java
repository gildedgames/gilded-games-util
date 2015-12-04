package com.gildedgames.util.core.client;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GuiIngame
{
	private final Minecraft mc;

	public GuiIngame(Minecraft mc)
	{
		this.mc = mc;
	}

	@SubscribeEvent
	public void onRenderGui(RenderGameOverlayEvent.Post event)
	{
		if (event.isCancelable() || event.type != ElementType.TEXT)
		{
			return;
		}
		//NotificationCore.locate().onRenderOverlay();
	}

}
