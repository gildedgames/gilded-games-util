package com.gildedgames.util.core;

import com.gildedgames.util.core.nbt.NBTFile;
import com.gildedgames.util.io_manager.overhead.IOManager;
import com.gildedgames.util.io_manager.overhead.IORegistry;
import com.gildedgames.util.io_manager.util.IOManagerDefault;
import com.gildedgames.util.menu.client.MenuClientEvents.MenuConfig;
import com.gildedgames.util.world.common.WorldHookPool;

public class UtilServices
{

	private IOManager io;

	private static final String MANAGER_NAME = "GildedGamesUtil";

	public UtilServices()
	{

	}

	private void startIOManager()
	{
		this.io = new IOManagerDefault(MANAGER_NAME);

		IORegistry registry = this.io.getRegistry();
		registry.registerClass(NBTFile.class, 0);
		registry.registerClass(WorldHookPool.class, 1);
		registry.registerClass(MenuConfig.class, 2);
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
