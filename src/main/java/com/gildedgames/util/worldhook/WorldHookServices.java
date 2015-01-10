package com.gildedgames.util.worldhook;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;

import com.gildedgames.util.io_manager.IOManager;
import com.gildedgames.util.io_manager.io.IOFile;
import com.gildedgames.util.io_manager.util.nbt.NBTFile;
import com.gildedgames.util.worldhook.common.IWorldPool;
import com.gildedgames.util.worldhook.common.WorldPool;
import com.gildedgames.util.worldhook.common.test.WorldTest;

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
