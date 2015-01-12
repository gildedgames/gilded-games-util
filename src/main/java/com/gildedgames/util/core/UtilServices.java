package com.gildedgames.util.core;

import net.minecraft.nbt.NBTTagCompound;

import com.gildedgames.util.io_manager.IOManager;
import com.gildedgames.util.io_manager.io.IOFile;
import com.gildedgames.util.io_manager.util.nbt.NBTFile;
import com.gildedgames.util.menuhook.client.MenuClientEvents.MenuConfig;
import com.gildedgames.util.worldhook.common.IWorldHookPool;
import com.gildedgames.util.worldhook.common.WorldHookPool;

public class UtilServices
{
	
	private IOManager<NBTTagCompound, NBTTagCompound, IOFile<NBTTagCompound, NBTTagCompound>> io;

	public UtilServices()
	{
		
	}
	
	private void registerIOClasses()
	{
		this.io.register(NBTFile.class, 0);
		this.io.register(IWorldHookPool.class, 1);
		this.io.register(WorldHookPool.class, 2);
		this.io.register(MenuConfig.class, 3);
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
	
}
