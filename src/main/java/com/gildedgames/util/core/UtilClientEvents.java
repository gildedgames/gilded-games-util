package com.gildedgames.util.core;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.gildedgames.util.io_manager.IOCore;
import com.gildedgames.util.io_manager.io.IOSyncable.SyncSide;
import com.gildedgames.util.notifications.NotificationCore;
import com.gildedgames.util.tab.common.TabAPI;

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

			if (ClientProxy.keyBindHopUniverse.isPressed())
			{
				TabAPI.getInventoryGroup().getSide(Side.CLIENT).setSelectedTab(ClientProxy.UNIVERSE_HOPPER_TAB);
				Minecraft.getMinecraft().thePlayer.openGui(UtilCore.instance, UtilGuiHandler.hopUniverseID, Minecraft.getMinecraft().theWorld, 0, 0, 0);
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
		UtilClientEvents.partialTicks = event.partialTicks;
	}

}
