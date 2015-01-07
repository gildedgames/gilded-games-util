package com.gildedgames.util.worldhook;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;

import com.gildedgames.util.io_manager.IOManager;
import com.gildedgames.util.io_manager.io.IOFile;
import com.gildedgames.util.io_manager.util.nbt.NBTFile;
import com.gildedgames.util.worldhook.common.IWorldPool;
import com.gildedgames.util.worldhook.common.WorldPool;

public class WorldHookServices
{
	
	private List<IWorldPool> worldPools;
	
	private IOManager<NBTTagCompound, NBTTagCompound, IOFile<NBTTagCompound, NBTTagCompound>> io;

	public WorldHookServices()
	{
		
	}
	
	private void registerIOClasses()
	{
		this.io.register(NBTFile.class, 0);
		this.io.register(IWorldPool.class, 1);
		this.io.register(WorldPool.class, 2);
	}
	
	public IOManager<NBTTagCompound, NBTTagCompound, IOFile<NBTTagCompound, NBTTagCompound>> getIO()
	{
		if (this.io == null)
		{
			this.io = new IOManager<NBTTagCompound, NBTTagCompound, IOFile<NBTTagCompound, NBTTagCompound>>();
			
			this.registerIOClasses();
		}
		
		return this.io;
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
