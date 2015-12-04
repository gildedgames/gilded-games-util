package com.gildedgames.util.core;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import com.gildedgames.util.io_manager.IOCore;
import com.gildedgames.util.io_manager.io.IOSyncable.SyncSide;

public class UtilServerEvents
{

	private int tickCounter = 0;

	@SubscribeEvent(receiveCanceled = true)
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
	public void tickServer(TickEvent.ServerTickEvent event)
	{
		if (event.phase == TickEvent.Phase.START)
		{
			IOCore.io().dispatchDirtySyncables(SyncSide.SERVER);
			this.tickCounter++;
		}
		else if (event.phase == TickEvent.Phase.END)
		{
			if (this.tickCounter % (1200 * 3) == 0)
			{
				UtilCore.instance.flushData();
			}
		}
	}
	
}
