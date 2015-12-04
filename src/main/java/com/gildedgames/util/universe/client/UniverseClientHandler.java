package com.gildedgames.util.universe.client;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.core.UtilGuiHandler;
import com.gildedgames.util.core.client.ClientProxy;
import com.gildedgames.util.tab.common.TabAPI;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

public class UniverseClientHandler
{
	@SubscribeEvent
	public void tickClient(TickEvent.ClientTickEvent event)
	{
		if (event.phase == TickEvent.Phase.START)
		{
			if (ClientProxy.keyBindHopUniverse.isPressed())
			{
				TabAPI.getInventoryGroup().getSide(Side.CLIENT).setSelectedTab(ClientProxy.UNIVERSE_HOPPER_TAB);

				Minecraft.getMinecraft().thePlayer.openGui(UtilCore.instance, UtilGuiHandler.hopUniverseID, Minecraft.getMinecraft().theWorld, 0, 0, 0);
			}
		}
	}
}
