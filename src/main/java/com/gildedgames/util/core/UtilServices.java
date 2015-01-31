package com.gildedgames.util.core;

import com.gildedgames.util.io_manager.IOManager;
import com.gildedgames.util.io_manager.util.nbt.NBTFile;
import com.gildedgames.util.menu.client.MenuClientEvents.MenuConfig;
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

			this.io.register(NBTFile.class, 0);//"NBTFile");
			//this.io.register(IWorldHookPool.class, 1); "IWorldHookPool"); Dunno who added this but you cannot instantiate Interfaces so this is useless :p
			this.io.register(WorldHookPool.class, 1);// "WorldHookPool");
			this.io.register(MenuConfig.class, 2);// "MenuConfig");
		}

		return this.io;
	}

}
