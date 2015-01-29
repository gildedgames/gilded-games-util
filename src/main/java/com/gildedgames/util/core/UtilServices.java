package com.gildedgames.util.core;

import com.gildedgames.util.io_manager.IOManager;
import com.gildedgames.util.io_manager.util.nbt.NBTFile;
import com.gildedgames.util.menu.client.MenuClientEvents.MenuConfig;
import com.gildedgames.util.world.common.IWorldHookPool;
import com.gildedgames.util.world.common.WorldHookPool;

public class UtilServices
{
	
	private IOManager io;

	public UtilServices()
	{
		
	}

	public IOManager getIO()
	{
		if (this.io == null)
		{
			this.io = new IOManager();
			
			this.io.register(NBTFile.class, "NBTFile");
			this.io.register(IWorldHookPool.class, "IWorldHookPool");
			this.io.register(WorldHookPool.class, "WorldHookPool");
			this.io.register(MenuConfig.class, "MenuConfig");
		}

		return this.io;
	}
	
}
