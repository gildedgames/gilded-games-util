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

public class UtilEvents
{

	private static float partialTicks;

	private int tickCounter = 0;

	public static float getPartialTicks()
	{
		return UtilEvents.partialTicks;
	}

	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onEvent(PopulateChunkEvent.Post event)
	{
		Chunk chunk = event.world.getChunkFromChunkCoords(event.chunkX, event.chunkZ);
		    
	    Block fromBlock = Blocks.water;
	    Block toBlock = Blocks.brick_block;

	    for (ExtendedBlockStorage storage : chunk.getBlockStorageArray()) 
	    {
	        if (storage != null) 
	        {
	            for (int x = 0; x < 16; ++x)
	            {
	                for (int y = 0; y < 16; ++y)
	                {
	                    for (int z = 0; z < 16; ++z)
	                    {
	                    	if (storage.getBlockByExtId(x, y, z) != Blocks.air) 
	                        {
	                            //storage.set(x, y, z, toBlock.getDefaultState());
	                        }
	                    }
	                }
	            }
	        }
	    }
	    
	    chunk.setModified(true);
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
