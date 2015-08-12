package com.gildedgames.util.core;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.input.Keyboard;

import com.gildedgames.util.io_manager.IOCore;
import com.gildedgames.util.io_manager.io.IOSyncable.SyncSide;
import com.gildedgames.util.tab.common.TabAPI;


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

			if (ClientProxy.keyBindHopUniverse.isPressed())
			{
				TabAPI.getInventoryGroup().getSide(Side.CLIENT).setSelectedTab(ClientProxy.UNIVERSE_HOPPER_TAB);
				Minecraft.getMinecraft().thePlayer.openGui(UtilCore.instance, UtilGuiHandler.hopUniverseID, Minecraft.getMinecraft().theWorld, 0, 0, 0);
			}
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
	@SideOnly(Side.CLIENT)
	public void renderWorldLast(RenderWorldLastEvent event)
	{
		UtilEvents.partialTicks = event.partialTicks;
	}
	
}
