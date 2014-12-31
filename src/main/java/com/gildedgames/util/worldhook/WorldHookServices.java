package com.gildedgames.util.worldhook;

import java.util.ArrayList;
import java.util.List;

import com.gildedgames.util.io_manager.IOManager;
import com.gildedgames.util.worldhook.common.IWorldPool;

public class WorldHookServices
{
	
	private List<IWorldPool> worldPools;

	public WorldHookServices()
	{
		
	}
	
	public List<IWorldPool> getPools()
	{
		if (this.worldPools == null)
		{
			this.worldPools = new ArrayList<IWorldPool>();
		}

		return this.worldPools;
	}
	
	public void registerWorldPool(IWorldPool worldPool)
	{
		this.getPools().add(worldPool);
	}
	
}
