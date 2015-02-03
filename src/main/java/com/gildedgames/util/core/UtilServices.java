package com.gildedgames.util.core;

import com.gildedgames.util.core.nbt.NBTFile;
import com.gildedgames.util.io_manager.IOCore;
import com.gildedgames.util.io_manager.exceptions.IORegistryTakenException;
import com.gildedgames.util.io_manager.overhead.IOManager;
import com.gildedgames.util.io_manager.util.IOManagerDefault;
import com.gildedgames.util.io_manager.util.IORegistryDefault;
import com.gildedgames.util.menu.client.MenuClientEvents.MenuConfig;
import com.gildedgames.util.world.common.WorldHookPool;

public class UtilServices
{

	private IOManager io;

	private static final String REGISTRY_NAME = "GildedGamesUtil";
	
	public UtilServices()
	{

	}
	
	private void startIOManager()
	{
		this.io = new IOManagerDefault(new IORegistryDefault(REGISTRY_NAME));

		this.io.getRegistry().registerClass(NBTFile.class, 0);
		this.io.getRegistry().registerClass(WorldHookPool.class, 1);
		this.io.getRegistry().registerClass(MenuConfig.class, 2);
	}

	public IOManager getIOManager()
	{
		if (this.io == null)
		{
			this.startIOManager();
		}

		return this.io;
	}

}
