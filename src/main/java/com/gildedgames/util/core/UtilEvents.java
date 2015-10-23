package com.gildedgames.util.core;

import com.gildedgames.util.io_manager.IOCore;
import com.gildedgames.util.io_manager.io.IOSyncable.SyncSide;
import com.gildedgames.util.notifications.NotificationCore;
import com.gildedgames.util.tab.common.TabAPI;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class UtilEvents
{

	private static float partialTicks;

	private int tickCounter = 0;

	public static float getPartialTicks()
	{
		return UtilEvents.partialTicks;
	}

	@SubscribeEvent
	public void tickClient(TickEvent.ClientTickEvent event)
	{
		if (event.phase == TickEvent.Phase.START)
		{
			IOCore.io().dispatchDirtySyncables(SyncSide.CLIENT);

			if (ClientProxy.keyBindHopUniverse.isPressed())
			{
				TabAPI.getInventoryGroup().getSide(Side.CLIENT).setSelectedTab(ClientProxy.UNIVERSE_HOPPER_TAB);
				Minecraft.getMinecraft().thePlayer.openGui(UtilCore.instance, UtilGuiHandler.hopUniverseID, Minecraft.getMinecraft().theWorld, 0, 0, 0);
			}
		}
	}

	@SubscribeEvent
	public void tickServer(TickEvent.ServerTickEvent event)
	{
		if (event.phase == TickEvent.Phase.START)
		{
			IOCore.io().dispatchDirtySyncables(SyncSide.SERVER);
			this.tickCounter++;
		}
		if (event.phase == TickEvent.Phase.END)
		{
			if (this.tickCounter % (1200 * 3) == 0)
			{
				UtilCore.instance.flushData();
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onRenderGui(RenderGameOverlayEvent event)
	{
		NotificationCore.locate().onRenderOverlay();
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void renderWorldLast(RenderWorldLastEvent event)
	{
		UtilEvents.partialTicks = event.partialTicks;
	}

}
