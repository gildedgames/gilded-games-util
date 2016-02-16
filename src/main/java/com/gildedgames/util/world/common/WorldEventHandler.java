package com.gildedgames.util.world.common;

import java.io.File;
import java.io.IOException;

import com.gildedgames.util.world.WorldModule;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;

import com.gildedgames.util.core.nbt.NBTFactory;
import com.gildedgames.util.core.nbt.NBTFile;
import com.gildedgames.util.io_manager.IOCore;

public class WorldEventHandler
{

	private File saveLocation;

	@SubscribeEvent
	public void onTick(WorldTickEvent event)
	{
		if (event.phase == Phase.END)
		{
			for (IWorldHookPool<?> pool : WorldModule.locate().getPools())
			{
				IWorldHook world = pool.get(event.world);

				world.onUpdate();
			}
		}
	}

	private File getWorldFolderPath(World world)
	{
		return new File(world.getSaveHandler().getMapFileFromName(MinecraftServer.getServer().getFolderName()).getAbsolutePath().replace(MinecraftServer.getServer().getFolderName() + ".dat", ""));
	}

	@SubscribeEvent
	public void onLoad(WorldEvent.Load event)
	{
		World world = event.world;

		if (world.isRemote)
		{
			return;
		}

		File worldFile = this.getWorldFolderPath(world);

		this.saveLocation = new File(worldFile, "hook\\world\\");

		for (IWorldHookPool<?> pool : WorldModule.locate().getPools())
		{
			IWorldHook worldHook = pool.get(event.world);

			try
			{
				File finalLocation = new File(this.saveLocation, "\\" + pool.getPoolName() + "\\DIM" + world.provider.getDimensionId() + ".dat");

				IOCore.io().readFile(finalLocation, new NBTFile(finalLocation, worldHook, worldHook.getClass()), new NBTFactory());
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}

			worldHook.onLoad();
		}
	}

	@SubscribeEvent
	public void onUnload(WorldEvent.Unload event)
	{
		World world = event.world;

		for (IWorldHookPool<?> pool : WorldModule.locate().getPools())
		{
			if (pool != null)
			{
				IWorldHook worldHook = pool.get(event.world);
				try
				{
					final File location = new File(this.saveLocation, "\\" + pool.getPoolName() + "\\DIM" + world.provider.getDimensionId() + ".dat");

					IOCore.io().writeFile(location, new NBTFile(location, worldHook, worldHook.getClass()), new NBTFactory());
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}

				worldHook.onUnload();

				//pool.clear();
			}
			else
			{
				/** TODO: error log here, manager should never be null **/
			}
		}
	}

	@SubscribeEvent
	public void onSave(WorldEvent.Save event)
	{
		World world = event.world;

		ISaveHandler saveHandler = world.getSaveHandler();
		File worldFile = saveHandler.getWorldDirectory();

		//TODO: Shouldn't it save here lol?

		for (IWorldHookPool<?> pool : WorldModule.locate().getPools())
		{
			IWorldHook worldHook = pool.get(world);

			worldHook.onSave();
		}
	}

}
