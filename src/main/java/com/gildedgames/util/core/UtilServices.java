package com.gildedgames.util.core;

import com.gildedgames.util.core.nbt.NBTFile;
import com.gildedgames.util.io_manager.IOCore;
import com.gildedgames.util.io_manager.exceptions.IORegistryTakenException;
import com.gildedgames.util.io_manager.overhead.IORegistry;
import com.gildedgames.util.io_manager.util.IORegistryDefault;
import com.gildedgames.util.menu.client.MenuClientEvents.MenuConfig;
import com.gildedgames.util.world.common.WorldHookPool;

public class UtilServices
{

	private IORegistry io;

	private static final String REGISTRY_NAME = "GildedGamesUtil";
	
	public UtilServices()
	{

	}
	
	private void startIORegistry()
	{
		this.io = new IORegistryDefault(REGISTRY_NAME);

		this.io.registerClass(NBTFile.class, 0);
		this.io.registerClass(WorldHookPool.class, 1);
		this.io.registerClass(MenuConfig.class, 2);
		
		try
		{
			IOCore.io().addRegistry(this.io);
		}
		catch (IORegistryTakenException e)
		{
			e.printStackTrace();
		}
	}

	public IORegistry getIO()
	{
		if (this.io == null)
		{
			this.startIORegistry();
		}

		return this.io;
	}

}
