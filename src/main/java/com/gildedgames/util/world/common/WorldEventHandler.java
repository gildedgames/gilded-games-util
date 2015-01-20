package com.gildedgames.util.world.common;

import java.io.File;
import java.io.IOException;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.io_manager.util.nbt.NBTFactory;
import com.gildedgames.util.io_manager.util.nbt.NBTFile;
import com.gildedgames.util.world.WorldCore;

public class WorldEventHandler
{

	private File saveLocation;

	@SubscribeEvent
	public void onTick(WorldTickEvent event)
	{
		if (event.phase == Phase.END)
		{
			for (IWorldHookPool<?> pool : WorldCore.locate().getPools())
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

		File worldFile = this.getWorldFolderPath(world);

		this.saveLocation = new File(worldFile, "hook\\world\\");

		for (IWorldHookPool<?> pool : WorldCore.locate().getPools())
		{
			IWorldHook worldHook = pool.get(event.world);

			try
			{
				File finalLocation = new File(this.saveLocation, "\\" + pool.getPoolName() + "\\DIM" + world.provider.getDimensionId() + ".dat");

				UtilCore.locate().getIO().readFile(finalLocation, new NBTFile(finalLocation, worldHook, worldHook.getClass()), new NBTFactory());
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

		for (IWorldHookPool<?> pool : WorldCore.locate().getPools())
		{
			if (pool != null)
			{
				IWorldHook worldHook = pool.get(event.world);
				try
				{
					final File location = new File(this.saveLocation, "\\" + pool.getPoolName() + "\\DIM" + world.provider.getDimensionId() + ".dat");

					UtilCore.locate().getIO().writeFile(location, new NBTFile(location, worldHook, worldHook.getClass()), new NBTFactory());
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
				/** TO-DO: error log here, manager should never be null **/
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

		for (IWorldHookPool<?> pool : WorldCore.locate().getPools())
		{
			IWorldHook worldHook = pool.get(world);

			worldHook.onSave();
		}
	}

}
