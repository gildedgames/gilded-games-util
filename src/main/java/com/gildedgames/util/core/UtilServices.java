package com.gildedgames.util.core;

import com.gildedgames.util.io_manager.util.nbt.NBTFile;
import com.gildedgames.util.io_manager.util.nbt.NBTManager;
import com.gildedgames.util.menu.client.MenuClientEvents.MenuConfig;

public class UtilServices
{
	
	private NBTManager io;

	public UtilServices()
	{
		
	}
	
	private void registerIOClasses()
	{
		this.io.register(NBTFile.class, 0);
		this.io.register(MenuConfig.class, 3);
	}

	public NBTManager getIO()
	{
		if (this.io == null)
		{
			this.io = new NBTManager();

			this.registerIOClasses();
		}

		return this.io;
	}
	
}
